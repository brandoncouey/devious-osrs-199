package io.ruin.central.model.social;

import com.google.gson.annotations.Expose;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.central.CentralServer;
import io.ruin.central.model.Player;
import io.ruin.central.model.social.clan.ClanChat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;

public class SocialList extends SocialContainer {

    public String username;
    private boolean sent;
    @ Expose public int privacy;
    @ Expose public ClanChat cc;
    private static final HashMap<String, SocialList> LOADED = new HashMap();
    private static final File social_folder = new File("data//_saved/social/");

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

    public void offline(Player player) {
        this.sent = false;
        this.cc.leave(player, true);
        SocialList.save(this);
    }

    public void process(Player player) {
        if (!this.sent) {
            this.sent = true;
            this.update(null);
            player.sendSocial(true, this.friends);
            player.sendSocial(false, this.ignores);
            player.sendPrivacy(this.privacy);
            this.cc.init(player);
            return;
        }
        this.update(player);
    }

    private void update(Player sendFor) {
        if (this.friends != null) {
            for (SocialMember friend : this.friends) {
                if (friend == null) continue;
                int updatedWorldId = 0;
                Player pFriend = CentralServer.getPlayer(friend.name);
                if (pFriend != null) {
                    boolean hidden;
                    friend.checkName(pFriend);
                    SocialList fList = SocialList.get(friend.name);
                    boolean bl = hidden = fList.privacy == 2 || fList.privacy == 1 && !fList.isFriend(this.username) || fList.isIgnored(this.username);
                    if (!hidden) {
                        updatedWorldId = pFriend.world.id;
                    }
                }
                if (friend.worldId == updatedWorldId && !friend.newName) continue;
                friend.worldId = updatedWorldId;
                if (sendFor == null) continue;
                sendFor.sendSocial(true, friend);
            }
        }
        if (this.ignores != null) {
            for (SocialMember ignore : this.ignores) {
                if (ignore == null) continue;
                Player pIgnore = CentralServer.getPlayer(ignore.name);
                if (pIgnore != null) {
                    ignore.checkName(pIgnore);
                }
                if (ignore.worldId == 0 && !ignore.newName) continue;
                ignore.worldId = 0;
                if (sendFor == null) continue;
                sendFor.sendSocial(false, ignore);
            }
        }
    }

    private void add(Player player, String name, boolean ignore) {
        name = capitalize(name);
        String type = ignore ? "ignore" : "friend";
        SocialMember member = new SocialMember(name, ignore ? null : SocialRank.FRIEND);
        if (ignore) {
            this.addIgnore(member);
        } else if (this.addFriend(member) && this.cc.inClan(member.name)) {
            this.cc.update(false);
        }
    }

    private void delete(String name) {
        name = capitalize(name);
        String username = this.deleteFriend(name);
        if (!username.equals("") && this.cc.inClan(name)) {
            this.cc.update(false);
        }
    }

    public static void handle(Player player, String name, int requestType) {
        name = capitalize(name);
        if (requestType == 1) {
            player.socialList.add(player, name, false);
        } else if (requestType == 2) {
            player.socialList.delete(name);
        } else if (requestType == 3) {
            player.socialList.add(player, name, true);
        } else if (requestType == 4) {
            player.socialList.deleteIgnore(name);
        }
    }

    public static void sendPrivateMessage(Player player, int rankId, String name, String message) {
        Player receiver = CentralServer.getPlayer(name);
        if (receiver == null) {
            return;
        }
        message = StringUtils.fixCaps(message);
        player.sendPM(name, message);
        receiver.sendReceivePM(player.name, rankId, message);
    }

    public static SocialList get(String username) {
        SocialList loaded = LOADED.get(username);
        if (loaded == null) {
            File file = new File(social_folder, username + ".json");
            if (file.exists()) {
                try {
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    String json = new String(bytes);
                    loaded = JsonUtils.GSON_EXPOSE.fromJson(json, SocialList.class);
                } catch (Exception e) {
                    CentralServer.logError(e.getMessage());
                }
            }
            if (loaded == null) {
                loaded = new SocialList();
            }
        }
        if (loaded.cc == null) {
            loaded.cc = new ClanChat();
        }
        loaded.cc.parent = loaded;
        loaded.username = username;
        LOADED.put(loaded.username, loaded);
        return loaded;
    }

    private static void save(SocialList list) {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(list);
            if(!social_folder.exists() && !social_folder.mkdirs())
                throw new IOException("social directory could not be created!");
            Files.write(new File(social_folder, list.username + ".json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            CentralServer.logError(e.getMessage());
        }
    }
}