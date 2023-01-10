package io.ruin.central.model;

import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.filestore.utility.Huffman;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.utils.Random;
import io.ruin.central.CentralServer;
import io.ruin.central.model.social.SocialList;
import io.ruin.central.model.social.SocialMember;
import io.ruin.central.model.social.clan.ClanChat;
import io.ruin.central.model.world.World;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.List;

public class Player {

    public final int userId;
    public final String name;
    public final World world;
    public final boolean admin;
    public final SocialList socialList;

    public Player(int userId, String name, List<Integer> groupIds, World world) {
        this.userId = userId;
        this.name = name;
        this.admin = groupIds.contains(5 | 4 | 9 | 3 | 8 | 100);
        this.world = world;
        this.socialList = SocialList.get(name);
    }

    public ClanChat getClanChat() {
        return this.socialList.cc;
    }

    public ClanChat getActiveClanChat() {
        return this.socialList.cc.active;
    }

    public void destroy() {
        this.socialList.offline(this);
    }

    public void process() {
        this.socialList.process(this);
    }

    public void write(OutBuffer out) {
        out.encode(null);
        this.world.sendClientPacket(this.userId, out.toByteArray());
    }

    public void sendMessage(String message) {
        this.sendMessage(message, 0);
    }

    public void sendMessage(String message, int type) {
        this.write(Protocol.messagePacket(message, null, type));
    }

    public void sendPrivacy(int privacy) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(12).addByte(privacy);
        this.write(out);
    }

    public void sendSocial(boolean friendType, SocialMember... list) {
        if (friendType) {
            this.sendFriends(list);
        } else {
            this.sendIgnores(list);
        }
    }

    private void sendFriends(SocialMember ... friends) {
        OutBuffer out = new OutBuffer(friends == null ? 3 : 255).sendVarShortPacket(17);
        if (friends != null) {
            for (SocialMember friend : friends) {
                if (friend == null) continue;
                out.addByte(friend.sendNewName() ? 1 : 0);
                out.addString(friend.name);
                out.addString(friend.lastName);
                out.addShort(friend.worldId);
                out.addByte(friend.rank == null ? -1 : friend.rank.id);
                out.addByte(0);
                if (friend.worldId > 0) {
                    out.skip(6);
                }
                out.skip(1);
            }
        }
        this.write(out);
    }

    private void sendIgnores(SocialMember ... ignores) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(7);
        if (ignores != null) {
            for (SocialMember ignore : ignores) {
                if (ignore == null) continue;
                out.addByte(ignore.sendNewName() ? 1 : 0);
                out.addString(ignore.name);
                out.addString(ignore.lastName);
                out.skip(1);
            }
        }
        this.write(out);
    }

    public void sendPM(String toUsername, String message) {
        this.write(Protocol.outgoingPm(toUsername, message));
    }

    public void sendReceivePM(String fromName, int fromRank, String message) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(21)
                .addString(fromName);
        for (int i = 0; i < 5; ++i) {
            out.addByte(Random.get(255));
        }
        out.addByte(fromRank);
        Huffman.encrypt(out, message);
        this.write(out);
    }


    public static String load(String username, World world) throws IOException {
        File file = Player.getSaveFile(username, world);
        return file.exists() ? new String(Files.readAllBytes(file.toPath())) : "";
    }

    public static boolean save(String username, World world, String json) {
        try {
            File file = Player.getSaveFile(username, world);
            Files.write(file.toPath(), json.getBytes(), new OpenOption[0]);
            return true;
        }
        catch (IOException e) {
            CentralServer.logError(e.getMessage());
            return false;
        }
    }

    private static File getSaveFile(String username, World world) throws IOException {
        File folder = new File("data//_saved/players/" + world.stage.name().toLowerCase() + "/eco");
        if (!(folder.exists() || folder.mkdir() || folder.mkdirs())) {
            throw new IOException("Failed to make player saves folder for world: " + world.id + " (" + world.activity + ")");
        }
        return new File("data//_saved/players/" + world.stage.name().toLowerCase() + "/eco" + "/" + username + ".json");
    }
}

