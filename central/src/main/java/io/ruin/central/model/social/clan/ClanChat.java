package io.ruin.central.model.social.clan;

import com.google.gson.annotations.Expose;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.utils.Random;
import io.ruin.api.utils.StringUtils;
import io.ruin.central.CentralServer;
import io.ruin.central.model.Player;
import io.ruin.central.model.social.SocialList;
import io.ruin.central.model.social.SocialMember;
import io.ruin.central.model.social.SocialRank;

import java.util.HashMap;

public class ClanChat extends ClanContainer {

    public SocialList parent;
    private String owner;
    @ Expose private String lastName = "";
    @ Expose public String name;
    @ Expose public SocialRank enterRank = null;
    @ Expose public SocialRank talkRank = null;
    @ Expose public SocialRank kickRank = SocialRank.CORPORAL;
    public ClanChat active;
    private boolean joining;
    private HashMap<Integer, Long> kicked;

    public void init(Player player) {
        this.sendSettings(player);
        if (!this.lastName.equals("")) {
            this.join(player, this.lastName);
        }
    }

    public void sendString(Player player, int interfaceId, int childId, String string) {
        OutBuffer out = new OutBuffer(3 + 4 + Protocol.strLen(string))
                .sendVarShortPacket(23)
                .writeStringCp1252NullTerminated(string)
                .addInt(interfaceId << 16 | childId);
        player.write(out);
    }

    private static final String[] settingsActions = {"Anyone", "Any friends", "Recruit+", "Corporal+", "Sergeant+", "Lieutenant+", "Captain+", "General+", "Only me"};

    public void sendSettings(Player player) {
        sendNameSetting(player);
        sendEnterRank(player);
        sendTalkRank(player);
        sendKickRank(player);
    }

    public void sendNameSetting(Player player) {
        String name = this.name == null ? "Chat disabled" : this.name;
        sendString(player, 94, 10, name);
    }

    public void sendEnterRank(Player player) {
        int enterRank = this.enterRank == null ? -1 : this.enterRank.id;
        sendString(player, 94, 13, settingsActions[enterRank == -1 ? 0 : enterRank + 1]);
    }

    public void sendTalkRank(Player player) {
        int talkRank = this.talkRank == null ? -1 : this.talkRank.id;
        sendString(player, 94, 16, settingsActions[talkRank == -1 ? 0 : talkRank + 1]);
    }

    public void sendKickRank(Player player) {
        int kickRank = this.kickRank.id;
        sendString(player, 94, 19, settingsActions[kickRank + 1]);
    }

    public void update(boolean settingsOnly) {
        if (this.ccMembersCount == 0) {
            return;
        }
        OutBuffer out = settingsOnly ? this.getSettingsBuffer() : this.getChannelBuffer();
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = CentralServer.getPlayer(member.name);
            if (pMember == null) continue;
            pMember.write(out);
        }
    }

    public void disable() {
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = CentralServer.getPlayer(member.name);
            if (pMember == null) continue;
            pMember.getClanChat().setActive(pMember, null);
            pMember.sendMessage("The clan chat channel you were in has been disabled.", 11);
        }
        if (this.kicked != null) {
            this.kicked.clear();
            this.kicked = null;
        }
    }

    public boolean isDisabled() {
        return this.name == null;
    }

    private void setActive(Player player, ClanChat newActive) {
        if (this.active == newActive) {
            return;
        }
        if (newActive == null) {
            this.active.removeClanMember(player.name);
            player.write(this.active.getLeaveBuffer());
            if (!this.active.isDisabled()) {
                this.active.update(false);
            }
        } else {
            this.lastName = newActive.parent.username;
            newActive.update(false);
        }
        this.active = newActive;
    }

    public static String capitalize(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("_", " ");
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i+1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i+2));
                }
            }
        }
        return s;
    }

    public void join(Player player, String name) {
        name = capitalize(name);
        if (this.active != null) {
            player.sendMessage("You are already in a channel!", 11);
            return;
        }
        SocialList ownerList = SocialList.get(name);
        if (ownerList.isIgnored(player.name)) {
            player.sendMessage("You are blocked from joining this channel.");
            return;
        }
        ClanChat joinCc = ownerList.cc;
        joinCc.owner = name;
        if (joinCc.addMember(player)) {
            this.setActive(player, joinCc);
            player.sendMessage("Now talking in clan chat channel " + joinCc.name + ".", 11);
            player.sendMessage("To talk, start each line of chat with the / symbol.", 11);
        }
    }

    public void leave(Player player, boolean logout) {
        if (this.active == null) {
            return;
        }
        this.lastName = logout ? this.active.parent.username : "";
        this.setActive(player, null);
    }

    public void kick(Player kickedBy, String kickName) {
        if (this.isDisabled()) {
            return;
        }
        SocialRank kickerRank = this.getRank(kickedBy);
        if (!ClanChat.meetsRank(this.kickRank, kickerRank)) {
            kickedBy.sendMessage("You are not a high enough rank to kick from this channel.", 11);
            return;
        }
        Player toKick = CentralServer.getPlayer(kickName);
        if (toKick == null || toKick.getActiveClanChat() != this) {
            kickedBy.sendMessage(kickName + " is not active in this channel.", 11);
            return;
        }
        SocialRank toKickRank = this.getRank(toKick);
        if (toKickRank == SocialRank.OWNER) {
            kickedBy.sendMessage("You can't kick this channel's owner!", 11);
            return;
        }
        if (!ClanChat.meetsRank(toKickRank, kickerRank)) {
            kickedBy.sendMessage("You are not a high enough rank to kick this member.", 11);
            return;
        }
        if (this.kicked == null) {
            this.kicked = new HashMap();
        }
        this.kicked.put(toKick.userId, System.currentTimeMillis() + 3600000L);
        toKick.getClanChat().leave(toKick, false);
        toKick.sendMessage("You have been kicked from the channel.", 11);
    }

    private boolean addMember(Player pMember) {
        Long kickedUntil;
        if (this.isDisabled()) {
            pMember.sendMessage("The channel you tried to join is currently disabled.", 11);
            return false;
        }
        if (this.kicked != null && (kickedUntil = this.kicked.get(pMember.userId)) != null) {
            if (kickedUntil > System.currentTimeMillis()) {
                pMember.sendMessage("You cannot join this channel because you are currently banned from it.", 11);
                return false;
            }
            this.kicked.remove(pMember.userId);
        }
       // if (!ClanChat.meetsRank(this.enterRank, this.getRank(pMember))) {
       //     pMember.sendMessage("You are not a high enough rank to enter this channel.", 11);
      //      return false;
    //    }
        if (!this.addClanMember(new SocialMember(pMember.userId, pMember.name, pMember.world.id))) {
            pMember.sendMessage("The channel you tried to join is full.", 11);
            return false;
        }
        return true;
    }

    public void message(Player sender, int rankId, String message) {
        if (this.isDisabled()) {
            return;
        }
        SocialRank senderRank = this.getRank(sender);
        if (!ClanChat.meetsRank(this.talkRank, senderRank)) {
            sender.sendMessage("You are not a high enough rank to talk in this channel.", 11);
            return;
        }
        message = StringUtils.fixCaps(message);
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            Player pMember = CentralServer.getPlayer(member.name);
            if (pMember == null) continue;
            pMember.write(this.getMessageBuffer(sender.name, rankId, message));
        }
    }

    private OutBuffer getMessageBuffer(String senderName, int rankId, String message) {
        OutBuffer out = new OutBuffer(255).sendVarBytePacket(22);
        out.addString(senderName);
        out.addLong(StringUtils.stringToLong(this.name));
        for (int i = 0; i < 5; ++i) {
            out.addByte(Random.get(255));
        }
        out.addByte(rankId);
        Huffman.encrypt(out, message);
        return out;
    }

    private OutBuffer getBuffer(int type) {
        if (type == 0) {
            return new OutBuffer(3).sendVarShortPacket(42);
        }
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(42).
                addString(this.owner).
                addLong(StringUtils.stringToLong(this.name)).//addString(this.name).
                        addByte(ClanChat.getRankId(this.kickRank));
        if (type == 2) {
            out.addByte(255);
            return out;
        }
        out.addByte(this.ccMembersCount);
        for (int i = 0; i < this.ccMembersCount; ++i) {
            SocialMember member = this.ccMembers[i];
            out.addString(member.name);
            out.addShort(member.worldId);
            out.addByte(this.getRankId(member.name));
            out.addByte(0);
        }
        return out;
    }

    private OutBuffer getLeaveBuffer() {
        return this.getBuffer(0);
    }

    private OutBuffer getChannelBuffer() {
        return this.getBuffer(1);
    }

    private OutBuffer getSettingsBuffer() {
        return this.getBuffer(2);
    }

    private SocialRank getRank(Player player) {
        if (player.name.equalsIgnoreCase(this.parent.username)) {
            return SocialRank.OWNER;
        }
        if (player.admin) {
            return SocialRank.ADMIN;
        }
        SocialMember friend = this.parent.getFriend(player.name);
        return friend == null ? null : friend.rank;
    }

    private int getRankId(String username) {
        if (username.equalsIgnoreCase(this.parent.username)) {
            return SocialRank.OWNER.id;
        }
        Player player = CentralServer.getPlayer(username);
        if (player != null && player.admin) {
            return SocialRank.ADMIN.id;
        }
        if (player != null) {
            SocialMember friend = this.parent.getFriend(player.name);
            return friend == null ? -1 : friend.rank.id;
        }
        return SocialRank.FRIEND.id;
    }

    private static int getRankId(SocialRank rank) {
        return rank == null ? -1 : rank.id;
    }

    private static boolean meetsRank(SocialRank reqRank, SocialRank checkRank) {
        return reqRank == null || checkRank != null && checkRank.id >= reqRank.id;
    }
}