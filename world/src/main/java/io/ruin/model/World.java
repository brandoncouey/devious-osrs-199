package io.ruin.model;

import com.google.common.collect.Maps;
import io.ruin.Server;
import io.ruin.api.protocol.world.WorldFlag;
import io.ruin.api.protocol.world.WorldSetting;
import io.ruin.api.protocol.world.WorldStage;
import io.ruin.api.protocol.world.WorldType;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.activities.leagues.LeagueType;
import io.ruin.model.activities.pvminstances.PVMInstance;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.EntityList;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.GameMode;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.object.owned.OwnedObject;
import io.ruin.model.object.owned.impl.DwarfCannon;
import io.ruin.model.skills.events.ShootingStars;
import io.ruin.process.event.EventWorker;
import io.ruin.services.LatestUpdate;
import io.ruin.services.discord.DiscordConnection;
import io.ruin.utility.Broadcast;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class World extends EventWorker {
    public static Player player;
    public static int id;

    public static String name;

    public static WorldStage stage;

    public static WorldType type;

    public static WorldFlag flag;

    public static int settings;
    public static WorldSetting worldType;
    public static String address;

    public static int port;

    private static String centralAddress;

    public static LeagueType leagueType;

    public static boolean isDev() {
        return stage == WorldStage.DEV;
    }

    public static boolean isBeta() {
        return stage == WorldStage.BETA;
    }

    public static boolean isLive() {
        return stage == WorldStage.LIVE;
    }

    public static boolean isPVP() {
        return type == WorldType.PVP;
    }
    public static boolean isPVPWorld() {
        return settings == 0x5;
    }

    public static boolean isDevWorld() {
        return id == 3;
    }

    public static boolean isEco() {
        return type == WorldType.ECO;
    }

    public static final int spawnableOffset = 100000;

    public static final Bounds HOME_BOUNDS = new Bounds(3535, 3118, 3546, 3124, 1);
    //public static final Position HOME = new Position(3541, 3123, 1);
    public static final Position HOME = new Position(3087, 3493, 0);
    public static final Position FUN_PK = new Position(3568, 3122, 1);
    public static final Position HOMEPVP = new Position(3095, 3497, 0);
    public static final Position DZ = new Position(3811, 2845, 1);
    public static final Position SZ = new Position(3122, 3473, 0);
    public static final Position EDGEHOME = new Position(3087, 3490, 0);
    public static final Position DEATHS_DOMAIN = new Position(3174, 5727, 0);

    /**
     * Players
     */
    public static final EntityList<Player> players = new EntityList<>(new Player[1000]);

    public static Stream<Player> getPlayerStream() {
        return StreamSupport.stream(players.spliterator(), false)
                .filter(Objects::nonNull);
    }

    public static Player getPlayer(int index) {
        return players.get(index);
    }

    public static Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }

    public static Player getPlayer(int userId, boolean onlineReq) {
        if (onlineReq) {
            for (Player player : players) {
                if (player != null && player.getUserId() == userId)
                    return player;
            }
        } else {
            for (Player player : players.entityList) {
                if (player != null && player.getUserId() == userId)
                    return player;
            }
        }
        return null;
    }

    public static void sendSupplyChestBroadcast(String message) {
        players.forEach(p -> {
            if (p.broadcastSupplyChest)
                p.sendNotification(message);
        });
    }

    public static void sendGraphics(int id, int height, int delay, Position dest) {
        sendGraphics(id, height, delay, dest.getX(), dest.getY(), dest.getZ());
    }

    public static void sendGraphics(int id, int height, int delay, int x, int y, int z) {
        for (Player p : Region.get(x, y).players)
            p.getPacketSender().sendGraphics(id, height, delay, x, y, z);
    }

    /**
     * Npcs
     */
    public static final EntityList<NPC> npcs = new EntityList<>(new NPC[1000]);

    public static NPC getNpc(int index) {
        return npcs.get(index);
    }

    /**
     * PLAYER SAVERS
     */
    public static boolean doubleDrops;

    public static boolean doublePkp;

    public static boolean doubleSlayer;

    public static boolean doublePest;

    public static boolean doubleWintertodt;

    public static boolean doublePVP;

    public static boolean doubleRaids;

    public static double xpMultiplier = 0;

    public static int playerModifier = 0;

    public static int bmMultiplier = 0;

    public static boolean weekendExpBoost = false;

    public static void toggleDoubleDrops() {
        doubleDrops = !doubleDrops;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double drops have been " + (doubleDrops ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleRaids() {
        doubleRaids = !doubleRaids;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double raids have been " + (doubleRaids ? "enabled" : "disabled") + ".");
    }
    public static void toggleDoublePkp() {
        doublePkp = !doublePkp;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Pkp has been " + (doublePkp ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleSlayer() {
        doubleSlayer = !doubleSlayer;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Slayer Points has been " + (doubleSlayer ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoublePest() {
        doublePest = !doublePest;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Pest Control Points has been " + (doublePest ? "enabled" : "disabled") + ".");
    }

    public static void toggleDoubleWintertodt() {
        doubleWintertodt = !doubleWintertodt;
        Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Double Wintertodt Points has been " + (doubleWintertodt ? "enabled" : "disabled") + ".");
    }

    public static void boostXp(double multiplier) {
        xpMultiplier = multiplier;
        if (xpMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now normal. (x1)");
        else if (xpMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being doubled! (x2)");
        else if (xpMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being tripled! (x3)");
        else if (xpMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Experience is now boosted! (x" + multiplier + ")");
    }

    /*
     * Sets the base amount of blood money user can get per kill
     */
    public static void setBaseBloodMoney(int baseBloodMoney) {
        Killer.BASE_BM_REWARD = baseBloodMoney;
    }

    public static void toggleWeekendExpBoost() {
        weekendExpBoost = !weekendExpBoost;
        if (weekendExpBoost) {
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "The 25% weekend experience boost is now activated!");
        } else {
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "The 25% weekend experience boost is now deactivated!");
        }
    }

    public static void boostBM(int multiplier) {
        bmMultiplier = multiplier;
        if (bmMultiplier == 1)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now normal. (x1)");
        else if (bmMultiplier == 2)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being doubled! (x2)");
        else if (bmMultiplier == 3)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being tripled! (x3)");
        else if (bmMultiplier == 4)
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now being quadrupled! (x4)");
        else
            Broadcast.WORLD.sendNews(Icon.RED_INFO_BADGE, "Blood money drops from player kills are now boosted! (x" + multiplier + ")");
    }

    public static void sendLoginMessages(Player player) {
       if (World.isPVPWorld())
            player.sendMessage("The PVP World gives you a chance to receive a Deadman Key on player kill!");
        if (doubleDrops)
            player.sendMessage(Color.ORANGE_RED.tag() + "Npc drops are currently being doubled!");
        if (xpMultiplier == 2)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being doubled! (x2)");
        else if (xpMultiplier == 3)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being tripled! (x3)");
        else if (xpMultiplier == 4)
            player.sendMessage(Color.ORANGE_RED.tag() + "Experience is currently being quadrupled! (x4)");
    }

    public static boolean wildernessDeadmanKeyEvent = false;

    public static void toggleDmmKeyEvent() {
        wildernessDeadmanKeyEvent = !wildernessDeadmanKeyEvent;
    }

    public static boolean wildernessKeyEvent = false;

    public static void toggleWildernessKeyEvent() {
        wildernessKeyEvent = !wildernessKeyEvent;
    }

    public static Optional<Player> getPlayerByUid(int userId) {
        return getPlayerStream().filter(plr -> plr.getUserId() == userId).findFirst();
    }

    public static Optional<Player> getPlayerByName(String name) {
        return getPlayerStream().filter(plr -> plr.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Getter
    protected static final Map<String, OwnedObject> ownedObjects = Maps.newConcurrentMap();

    public static void registerOwnedObject(OwnedObject object) {
        ownedObjects.put(object.getOwnerUID() + ":" + object.getIdentifier(), object);
    }

    public static OwnedObject getOwnedObject(Player owner, String identifier) {
        return ownedObjects.get(owner.getName() + ":" + identifier);
    }

    public static void deregisterOwnedObject(OwnedObject object) {
        ownedObjects.remove(object.getOwnerUID() + ":" + object.getIdentifier());
    }

    /**
     * Updating
     */
    public static boolean updating = false;

    public static boolean update(int minutes) {
        if (minutes <= 0) {
            updating = false;
            for (Player player : players)
                player.getPacketSender().sendSystemUpdate(0);
            System.out.println("System Update Cancelled");
            return true;
        }
        if (updating)
            return false;
        updating = true;
        Broadcast.GLOBAL.sendNews(Color.RED.wrap("[WORLD " + World.id +  "] is now being Updated!"));
        System.out.println("System Update: " + minutes + " minutes");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("The Server is now being updated.");
        eb.setDescription("The Server is now being updated.");
        eb.setColor(new java.awt.Color(0xB00D03));
        eb.addField("Notification!", "<@&991831182310379601>", true);
        DiscordConnection.jda.getTextChannelById("994599921980289055").sendMessageEmbeds(eb.build()).queue();
        Guild guild = DiscordConnection.jda.getGuildById("991831182310379601"); //Guild you got from a listener, or from the JDA pool
        if (guild != null) {
            guild.findMembers(member -> {
                if (member.getRoles().contains(guild.getRoleById("991831182310379601"))) {
                    member.getUser().openPrivateChannel().submit().thenCompose(privateChannel -> privateChannel.sendMessageEmbeds(eb.build()).submit()).whenComplete((message, error) -> {
                        if (error != null)
                            System.out.println(member.getEffectiveName() + " Has blocked me and I cannot send them a message!");
                        else System.out.println("Private messsage sent");
                    });
                    return true;
                }
                return false;
            });
        }
        for (Player player : players)
            player.getPacketSender().sendSystemUpdate(minutes * 60);
        startEvent(e -> {
            int ticks = minutes * 100;
            while (updating) {
                if (--ticks <= 0 && removeBots() && removePlayers())
                    return;
                e.delay(1);
            }
        });
        return true;
    }

    public static boolean removePlayers() {
        int pCount = players.count();
        if (pCount > 0) {
            System.out.println("Attempting to remove " + pCount + " players...");
            for (Player player : players) {
                if (World.getOwnedObject(player, DwarfCannon.IDENTIFIER) != null) {
                    player.cannonLost = true;
                }
                player.forceLogout();
            }
            return false;
        }
        PVMInstance.destroyAll();
        try {
            new ProcessBuilder("sh /home/Server/run.sh").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Players removed from world successfully!");
        return true;
    }

    private static boolean removeBots() {
        for (Player p : World.players) {
            if (p.getChannel().id() == null)
                p.logoutStage = -1;
        }
        return true;
    }

    /**
     * Holiday themes
     */
    public static boolean halloween;

    public static boolean isHalloween() {
        return halloween;
    }

    public static boolean christmas;

    public static boolean isChristmas() {
        return christmas;
    }

    /*
     * Save event
     */
    static {
        startEvent(e -> {
            while (true) {
                e.delay(100); //every 1 minute just in case..
                for (Player player : players) {
                    PlayerFile.save(player, -1);
                }
            }
        });
//        startEvent(e -> {
//            while(true) {
//                e.delay(1);
//                TimedEventManager.INSTANCE.tick();
//            }
//        });
    }

    /*
     * Announcement event
     */
    static {
        Server.afterData.add(() -> {
            List<String> announcements;
            announcements = Arrays.asList(
                    "Need help? Join the \"help\" cc!",
                    "Make sure to vote to gain access to exclusive items!",
                    "Looking to support Devious? Type ::store ingame!",
                    "Please take the time to vote for us. It helps us out and takes two seconds! ::vote",
                    "Join ::discord to get closer to the community!"
            );

            Collections.shuffle(announcements);
            startEvent(e -> {
                int offset = 0;
                while (true) {
                    e.delay(500); //5 minutes
                    Broadcast.WORLD.sendNews(Icon.ANNOUNCEMENT, "Announcements", announcements.get(offset));
                    if (++offset >= announcements.size())
                        offset = 0;
                }
            });
        });
    }

    public static String getCentralAddress() {
        return centralAddress;
    }

    public static void setCentralAddress(String centralAddress) {
        World.centralAddress = centralAddress;
    }

    @SneakyThrows
    public static void parse(Properties properties) {
        World.id = Integer.parseInt(properties.getProperty("world_id"));
        World.name = properties.getProperty("world_name");
        World.stage = WorldStage.valueOf(properties.getProperty("world_stage"));
        World.type = WorldType.valueOf(properties.getProperty("world_type"));
        World.flag = WorldFlag.valueOf(properties.getProperty("world_flag"));
        World.leagueType = LeagueType.valueOf(properties.getProperty("league_type"));
        World.halloween = Boolean.parseBoolean(properties.getProperty("halloween"));
        World.christmas = Boolean.parseBoolean(properties.getProperty("christmas"));
        String worldSettings = properties.getProperty("world_settings");
        for (String s : worldSettings.split(",")) {
            if (s == null || (s = s.trim()).isEmpty())
                continue;
            WorldSetting setting;
            try {
                setting = WorldSetting.valueOf(s);
            } catch (Exception e) {
                log.error("INVALID WORLD SETTING: " + s, e);
                continue;
            }
            World.settings |= setting.mask;
        }
        String address = properties.getProperty("world_address");
        String[] split = address.split(":");
        String host = split[0].trim();
        port = Integer.valueOf(split[1]);
        World.address = host + ":" + port;

        World.setCentralAddress(properties.getProperty("central_address"));
    }

    public static List<Player> getPlayersForNames(List<String> playerNames) {
        return playerNames
                .stream()
                .distinct()
                .filter(Objects::nonNull)
                .filter(name -> !name.isEmpty())
                .map(World::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}