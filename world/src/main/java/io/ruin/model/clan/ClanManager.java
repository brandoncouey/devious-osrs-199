package io.ruin.model.clan;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.utility.Misc;
import io.ruin.utility.SerializableFilesManager;
import io.ruin.utility.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ClanManager {

    private Clan clan;
    private final ArrayList<Player> channelPlayers = new ArrayList<>();
    private final ArrayList<Player> membersOnline = new ArrayList<>();

    private static HashMap<String, ClanManager> cachedClans;

    private ClanMember.Ranks talkInChatRank = null;

    private ClanMember.Ranks withdrawlFromBankRank= ClanMember.Ranks.LEADER;

    private ClanMember.Ranks banMembersRank = ClanMember.Ranks.LEADER;
    private ClanMember.Ranks kickMembersRank = ClanMember.Ranks.LEADER;

    private ClanMember.Ranks inviteRank = ClanMember.Ranks.RECRUIT;

    private boolean allowGuestsInChat;



    public static void init() {
        EmptyClanRemover.initalize();
        cachedClans = new HashMap<>();
    }

    public boolean hasRank(Player player, ClanMember.Ranks rank) {
        if (rank == null) return true;//Really used for everyone
        return getMemberByUsername(player.getName()).getRank().ordinal() >= rank.ordinal();
    }

    public static void createClan(Player player, String clanName) {
        clanName = Misc.formatStringFormal(clanName);
        if (player.getClanManager() != null) {
            return;
        }
        synchronized (cachedClans) {
            if (SerializableFilesManager.containsClan(clanName)) {
                player.sendMessage("The clan name you tried already exists.");
                return;
            }
            Clan clan = new Clan(clanName, player);
            SerializableFilesManager.saveClan(clan);
            linkClanMember(player, clan);
        }
    }

    public static void joinClan(Player player, Player inviter) {
        if (inviter == null)
            return;
        ClanManager manager = inviter.getClanManager();
        if (manager == null)
            return;
        synchronized (manager) {
            manager.clan.addMember(player, ClanMember.Ranks.RECRUIT);
            linkClanMember(player, manager.clan);
            inviter.getClanManager().sendMessage(player.getName() + " has just joined the clan.");
        }
    }

    public static void linkClanMember(Player player, Clan clan) {
        if (clan == null) {
            player.sendMessage("Unable to join clan...");
            return;
        }
        player.setClanName(clan.getName());
        player.setConnectedClanChannel(true);
        player.sendMessage("You have joined the clan, so you are now part of " + clan.getName() + ".");
        connectToClan(player, clan);
    }

    public static boolean connectToClan(Player player, String clanName) {
        Clan clan = SerializableFilesManager.loadClan(clanName);
        if (clan == null) return false;
        connectToClan(player, clan);
        return true;
    }

    public static boolean connectToClan(Player player, Clan clan) {
        String clanName = Misc.formatStringFormal(clan.getName());
        ClanManager manager = player.getClanManager();
        if (manager != null && player.getClanName() != null && clanName.equalsIgnoreCase(player.getClanName())) {
            return false;
        }
        synchronized (cachedClans) {
            manager = cachedClans.get(clanName); // grabs clan
            boolean created = manager != null;
            if (!created) { // not loaded
                if (!SerializableFilesManager.containsClan(clanName)) {
                    //TODO player.getPackets().sendIComponentText(1110, 70, "Could not find a clan named " + clanName + ". Please check the name and try again.");
                    return false;
                }
                manager = new ClanManager();
                manager.clan = clan;
            }
            synchronized (manager) {
                if (!manager.isMember(player)) {
                    player.sendMessage("You have been kicked from the clan.");
                    return false;
                }
                if (!created)
                    cachedClans.put(clanName, manager);
                manager.connect(player);
                return true;
            }
        }
    }

    public static void clear(Player player) {
        player.getPacketSender().sendString(1049, 3, "Currently not in a clan.");
        for (int i = 0; i < 63; i++) {
            player.getPacketSender().setHidden(1049, (12 + (i * 5)), true);
        }
    }

    public void connect(Player player) {
        synchronized (this) {
            membersOnline.add(player);
            if (player.isConnectedClanChannel()) {
                connectChannel(player);
                createClanClient(player);
            }
            linkClan(player);
            ClanManager.refreshChannelForClan(player.getClanManager());
            player.sendMessage("Connected to clan channel " + clan.getName() + ", to talk start each line with // symbol.");
        }
    }

    public void linkClan(Player player) {
        player.setClanManager(this);
    }

    public void connectChannel(Player player) {
        synchronized (this) {
            channelPlayers.add(player);
            ClanManager.refreshChannel(player);
        }
    }

    public static void refreshChannelForClan(ClanManager manager) {
        if (manager == null)  {
            return;
        }
        for (Player member : manager.membersOnline) {
            ClanManager.refreshChannel(member);
        }
    }

    public static void refreshChannel(Player player) {
        if (player.getClanManager() == null) {
            for (int i = 0; i < 63; i++) {
                player.getPacketSender().setHidden(1049, (12 + (i * 5)), true);
            }
            player.getPacketSender().sendString(1049, 3, "Unavailable");
            setButtonsClanActive(player, false);
            return;
        }
        setButtonsClanActive(player, true);
        player.getClanManager().refreshClanChannel(player);
    }

    private void refreshClanChannel(Player player) {
        synchronized (this) {
            for (int i = 0; i < 63; i++) {
                player.getPacketSender().setHidden(1049, (12 + (i * 5)), true);
            }
            int members = 0;
            player.getPacketSender().sendString(1049, 3, clan.getName());
            setButtonsClanActive(player, true);
            for (Player clanMember : membersOnline) {
                if (clanMember == null) continue;
                player.getPacketSender().setHidden(1049, (12 + (members * 5)), false);
                player.getPacketSender().sendString(1049, (15 + (members * 5)), clanMember.getName());
                if (getMemberByUsername(player.getName()) != null) {//Shouldn't but checking
                    player.getPacketSender().setSprite(1049, (14 + (members * 5)), getMemberByUsername(clanMember.getName()).getRank().getSpriteId());
                } else {
                    player.getPacketSender().setSprite(1049, (14 + (members * 5)), ClanMember.Ranks.RECRUIT.getSpriteId());
                }
                members++;
            }
        }
    }

    public static void setButtonsClanActive(Player player, boolean active) {
        player.getPacketSender().setHidden(1049, 334, active);
        player.getPacketSender().setHidden(1049, 333, !active);
        player.getPacketSender().setHidden(1049, 332, !active);
        player.getPacketSender().setHidden(1049, 331, !active);
        player.getPacketSender().setHidden(1049, 331, !active);
        ClanManager manager = player.getClanManager();
        boolean canInvite = active && manager != null && manager.hasRank(player, manager.inviteRank);
        boolean canKick = active && manager != null && manager.hasRank(player, manager.kickMembersRank);
        player.getPacketSender().setHidden(1049, 329, !canInvite);
        player.getPacketSender().setHidden(1049, 330, !canKick);
    }

    public boolean isMember(Player player) {
        synchronized (this) {
            for (ClanMember member : clan.getMembers())
                if (member.getUsername().equals(player.getName()))
                    return true;
            return false;
        }
    }

    public void viewInformation(Player player) {
        player.getPacketSender().setHidden(1050, 979, false);
        player.getPacketSender().setHidden(1050, 1028, true);
        for (int i = 0; i < 63; i++) {
            player.getPacketSender().setHidden(1050, (21 + (i * 5)), true);
        }
        int members = 0;
        for (ClanMember clanMember : player.getClanManager().clan.getMembers()) {
            player.getPacketSender().setHidden(1050, (21 + (members * 5)), false);
            player.getPacketSender().sendString(1050, (24 + (members * 5)), clanMember.getUsername());
            members++;
        }
        player.getPacketSender().sendString(1050, 987, "");//Top Text (uint64)
        player.getPacketSender().sendString(1050, 988, "");//Bottom Text (Your clan slogan here)
        player.getPacketSender().sendString(1050, 1000, clan.getClanLeaderUsername());
        player.getPacketSender().sendString(1050, 1005, clan.getDateCreated());
        player.getPacketSender().sendString(1050, 1011, Integer.toString(clan.getMembers().size()));
        player.getPacketSender().sendString(1050, 1021, Integer.toString(clan.getLevel()));//Level
        player.openInterface(InterfaceType.MAIN, 1050);
    }

    public void viewSettings(Player player) {
        player.getPacketSender().setHidden(1050, 979, true);
        player.getPacketSender().setHidden(1050, 1028, false);
        if (player.getClanManager().allowGuestsInChat) {
            player.getPacketSender().setHidden(1050, 1053, true);
            player.getPacketSender().setHidden(1050, 1054, false);
        } else {
            player.getPacketSender().setHidden(1050, 1053, false);
            player.getPacketSender().setHidden(1050, 1054, true);
        }
        player.getPacketSender().sendString(1050, 1037, talkInChatRank == null ? "Everyone" : Misc.formatStringFormal(talkInChatRank.name()) + "+");
        player.getPacketSender().sendString(1050, 1041, withdrawlFromBankRank == null ? "Everyone" : Misc.formatStringFormal(withdrawlFromBankRank.name()) + "+");
        player.getPacketSender().sendString(1050, 1045, banMembersRank == null ? "Everyone" : Misc.formatStringFormal(banMembersRank.name()) + "+");
        player.getPacketSender().sendString(1050, 1049, inviteRank == null ? "Everyone" : Misc.formatStringFormal(inviteRank.name()) + "+");
        for (int i = 0; i < 63; i++) {
            player.getPacketSender().setHidden(1050, (21 + (i * 5)), true);
        }
        int members = 0;
        for (ClanMember clanMember : clan.getMembers()) {
            player.getPacketSender().setHidden(1050, (21 + (members * 5)), false);
            player.getPacketSender().sendString(1050, (24 + (members * 5)), clanMember.getUsername());
            members++;
        }
        player.openInterface(InterfaceType.MAIN, 1050);
    }

    public static void kickClanmate(Player player) {
        /*ClanManager manager = player.getClanManager();
        if (manager == null)
            return;
        ClanMember member = (ClanMember) player.getTemporaryAttributtes().remove("editclanmatedetails");
        if (member == null)
            return;
        if (member.getUsername().equals(player.getUsername())) {
            player.sm("You can't kick yourself!");
            return;
        }
        if (member.getRank() == Clan.LEADER) {
            player.sm("You can't kick leader!");
            return;
        }
        manager.kickPlayer(member);*/
    }

    public ClanMember getMemberByUsername(String username) {
        synchronized (this) {
            for (ClanMember member : clan.getMembers())
                if (member.getUsername().equalsIgnoreCase(username))
                    return member;
            return null;
        }
    }

    private void disconnectChannel(Player player) {
        synchronized (this) {
            channelPlayers.remove(player);
            membersOnline.remove(player);
            ClanManager.refreshChannelForClan(player.getClanManager());
        }
    }

    public void disconnect(Player player) {
        synchronized (cachedClans) {
            synchronized (this) {
                disconnectChannel(player);
                player.setClanManager(null);
                destroyIfEmpty();
            }
        }
    }

    private void destroyIfEmpty() {
        if (empty()) {
            SerializableFilesManager.saveClan(clan);
            cachedClans.remove(clan.getName());
        }
    }

    public void invite(Player player, String name) {
        Player target = World.getPlayer(name);
        if (target == null) {
            player.sendMessage(name + " is currently offline.");
            return;
        }
        if (target.getClanManager() != null) {
            player.sendMessage(name + " is already in a clan.");
            return;
        }
        target.clanInviter = player.getName();
        target.getPacketSender().sendString(1051, 13, player.getName() + " has invited you!");
        target.getPacketSender().sendString(1051, 19, player.getClanName());
        target.getPacketSender().sendString(1051, 24, Utils.formatMoneyString(player.getClanManager().clan.getLevel()));
        target.getPacketSender().sendString(1051, 29, Integer.toString(player.getClanManager().clan.getMembers().size()));
        target.openInterface(InterfaceType.MAIN, 1051);
    }

    public void kickPlayer(ClanMember mate) {
        synchronized (cachedClans) {
            synchronized (this) {
                clan.getMembers().remove(mate);
                Player player = World.getPlayer(mate.getUsername());
                if (player != null) {
                    player.setClanName(null);
                    player.getClanManager().disconnect(player);
                    player.sendMessage("You're no longer part of a clan.");
                    setButtonsClanActive(player, false);
                }
                if (clan.getMembers().isEmpty()) {
                    // kickAllChannelPlayers();
                    SerializableFilesManager.deleteClan(clan);
                } else {
                    if (mate.getRank() == ClanMember.Ranks.LEADER) {
                        //ClanMember newLeader = getHighestRank();
                        //if (newLeader != null) {
                        //newLeader.setRank(Clan.LEADER);
                        //clan.setClanLeaderUsername(newLeader);
                        // generateClanChannelDataBlock();
                        ClanManager.refreshChannelForClan(this);
                        setButtonsClanActive(player, false);
                        //sendGlobalMessage("<col=7E2217>" + Misc.formatPlayerNameForDisplay(newLeader.getUsername()) + " has been appointed as new leader!");
                        //}
                    }
                    //generateClanSettingsDataBlock();
                    //refreshClanSettings();
                }
            }
        }
    }

    public static void leaveClanCompletly(Player player) {
        ClanManager manager = player.getClanManager();
        if (manager == null)
            return;
        ClanMember mate = manager.getMemberByUsername(player.getName());
        if (mate == null)
            return;
        manager.kickPlayer(mate);
        ClanManager.refreshChannelForClan(manager);
        setButtonsClanActive(player, false);
    }

    public void createClanClient(Player player) {
        OutBuffer buffer = new OutBuffer(255).sendVarShortPacket(59);
        buffer.addByte(0);
        buffer.addByte(0);
        buffer.addString(clan.getName());
        buffer.addShort(clan.getMembers().size());
        for (ClanMember member : clan.getMembers()) {
            buffer.addString(member.getUsername());
            buffer.addByte(0);
        }
        player.getPacketSender().write(buffer);
    }

    public void sendMessage(String message) {
        for (Player player : membersOnline) {
            if (player == null) continue;
            player.sendMessage(message);
        }
    }

    public void sendClanMessage(Player player, String message) {
        if (!hasRank(player, talkInChatRank)) {
            player.sendMessage("You do not currently have a sufficient rank to be able to talk in the clan channel.");
            return;
        }
        for (Player p : membersOnline) {
            OutBuffer buffer = new OutBuffer(255).sendVarShortPacket(67);
            buffer.addString(player.getName());
            buffer.addByte(player.getClanManager().getMemberByUsername(player.getName()).getRank().getSpriteGroupId());
            buffer.addByte(player.getClientGroupId());
            buffer.addString(message);
            p.getPacketSender().write(buffer);
        }
    }

    public static ClanMember.Ranks getRankForOption(int option) {
        switch (option) {
            case 2:
                return ClanMember.Ranks.RECRUIT;
            case 3:
                return ClanMember.Ranks.CORPORAL;
            case 4:
                return ClanMember.Ranks.SERGEANT;
            case 5:
                return ClanMember.Ranks.LIEUTENANT;
            case 6:
                return ClanMember.Ranks.CAPTAIN;
            case 7:
                return ClanMember.Ranks.GENERAL;
            case 8:
                return ClanMember.Ranks.LEADER;
        }
        return null;
    }

    public static String formatRank(ClanMember.Ranks rank) {
        if (rank == null)
            return "Everyone";
        if (rank == ClanMember.Ranks.LEADER)
            return "Leader";
        return Misc.formatStringFormal(rank.name()) + "+";
    }

    public boolean empty() {
        return membersOnline.size() == 0 && channelPlayers.size() == 0;
    }

    static {
        InterfaceHandler.register(1050, h -> {
            h.actions[1054] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                player.getClanManager().allowGuestsInChat = false;
                player.getClanManager().viewSettings(player);
            };
            h.actions[1053] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                player.getClanManager().allowGuestsInChat = true;
                player.getClanManager().viewSettings(player);
            };
            //Talk in chat
            h.actions[1034] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                ClanMember.Ranks rank = getRankForOption(option);
                player.dialogue(new OptionsDialogue("Are you sure to make talk in chat to " + formatRank(rank) + "?",
                        new Option("Yes", () ->  {
                            player.getClanManager().talkInChatRank = rank;
                            player.getClanManager().viewSettings(player);
                        }),
                        new Option("No"))
                );
            };
            //Withdraw from bank
            h.actions[1038] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                ClanMember.Ranks rank = getRankForOption(option);
                player.dialogue(new OptionsDialogue("Are you sure to make the withdraw from bank rank to " + formatRank(rank) + "?",
                        new Option("Yes", () ->  {
                            player.getClanManager().withdrawlFromBankRank = rank;
                            player.getClanManager().viewSettings(player);
                            ClanManager.refreshChannelForClan(player.getClanManager());
                        }),
                        new Option("No"))
                );
            };
            //Ban members
            h.actions[1042] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                ClanMember.Ranks rank = getRankForOption(option);
                player.dialogue(new OptionsDialogue("Are you sure to make the ban members rank to " + formatRank(rank) + "?",
                        new Option("Yes", () ->  {
                            player.getClanManager().banMembersRank = rank;
                            player.getClanManager().viewSettings(player);
                            ClanManager.refreshChannelForClan(player.getClanManager());
                        }),
                        new Option("No"))
                );
            };
            //Invite to clan
            h.actions[1046] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                if (!player.getClanManager().hasRank(player, ClanMember.Ranks.LEADER)) {
                    player.sendMessage("You do not have sufficient ranks to modify these settings.");
                    return;
                }
                ClanMember.Ranks rank = getRankForOption(option);
                player.dialogue(new OptionsDialogue("Are you sure to make the invite rank to " + formatRank(rank) + "?",
                        new Option("Yes", () ->  {
                            player.getClanManager().inviteRank = rank;
                            player.getClanManager().viewSettings(player);
                            ClanManager.refreshChannelForClan(player.getClanManager());
                        }),
                        new Option("No"))
                );
            };
            h.actions[970] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                player.getClanManager().viewInformation(player);
            };
            h.actions[973] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    return;
                }
                //TODO check permissions
                player.getClanManager().viewSettings(player);
            };
        });
        InterfaceHandler.register(1051, h -> {
            h.actions[31] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.clanInviter.equals("")) {
                    Player inviter = World.getPlayer(player.clanInviter);
                    if (inviter == null) return;
                    ClanManager.joinClan(player, inviter);
                }
                player.closeInterface(InterfaceType.MAIN);
                player.clanInviter = "";
            };
            h.actions[33] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.clanInviter.equals("")) {
                    Player target = World.getPlayer(player.clanInviter);
                    if (target != null) {
                        target.sendMessage(player.getName() + " has declined your invitation.");
                    }
                }
                player.closeInterface(InterfaceType.MAIN);
                player.clanInviter = "";
            };
        });
        InterfaceHandler.register(1049, h -> {
            h.actions[329] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    player.sendMessage("You must be in a clan to invite another player.");
                    return;
                }
                ClanMember member = player.getClanManager().getMemberByUsername(player.getName());
                if (member == null) {
                    return;
                }
                if (member.getRank().ordinal() < player.getClanManager().inviteRank.ordinal()) {
                    player.sendMessage("You do not have the sufficient rank to invite to the clan.");
                    return;
                }
                player.stringInput("Enter the player's name you wish to invite", name -> {
                    player.getClanManager().invite(player, name);
                });
            };
            h.actions[330] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    player.sendMessage("You must be in a clan to remove another player.");
                    return;
                }
                //TODO check rank
                player.stringInput("Enter the player's name you wish to kick", name -> {
                    player.getClanManager().kickPlayer(player.getClanManager().getMemberByUsername(name));
                });
            };
            h.actions[331] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    player.sendMessage("You must be in a clan to view clan information.");
                    return;
                }
                player.getClanManager().viewInformation(player);
            };
            h.actions[332] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    player.sendMessage("You must be in a clan to view clan settings.");
                    return;
                }
                //TODO check permissions
                player.getClanManager().viewSettings(player);
            };
            h.actions[333] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() == null) {
                    player.sendMessage("You are already not in a clan");
                    return;
                }
                player.dialogue(new OptionsDialogue("Are you sure you wish to leave your clan?",
                        new Option("Yes", () -> ClanManager.leaveClanCompletly(player)),
                        new Option("No"))
                );
            };
            h.actions[334] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (player.getClanManager() != null) {
                    player.sendMessage("You are already in a clan.");
                    return;
                }
                player.stringInput("Enter a clan name", s -> {
                    if (SerializableFilesManager.containsClan(s)) {
                        player.sendMessage("That clan name is already in use.");
                        return;
                    }
                    ClanManager.createClan(player, s);
                });
            };
        });
    }

}
