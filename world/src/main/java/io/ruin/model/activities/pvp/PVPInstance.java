package io.ruin.model.activities.pvp;

import io.ruin.api.protocol.world.WorldSetting;
import io.ruin.data.impl.teleports;
import io.ruin.model.World;
import io.ruin.model.activities.duelarena.DuelArena;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.containers.bank.BankActions;
import io.ruin.model.map.*;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.magic.spells.HomeTeleport;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

public class PVPInstance {

    public static ArrayList<Player> players = new ArrayList<>(500);
    public static final Bounds EDGE_BANK = new Bounds(3091, 3488, 3098, 3499, 0);
    public static final Bounds NEW_HOME = new Bounds(3091, 3488, 3098, 3499, 0);
    public static final Bounds EDGE_CHESTS = new Bounds(3091, 3506, 3098, 3511, 0);
    public static final Bounds CAMELOT = new Bounds(2724, 3487, 2727, 3497, 0);
    public static final Bounds CAMELOT2 = new Bounds(2721, 3490, 2723, 3497, 0);
    public static final Bounds CAMELOT3= new Bounds(2728, 3490, 2730, 3497, 0);
    public static final Bounds DZ_UPPER = new Bounds(3777, 2816, 3843, 2879, 1);
    public static final Bounds DEATH_OUTSIDE = new Bounds(3090, 3474, 3097, 3482, 0);
    public static final Bounds DEATH_INSIDE = new Bounds(3168, 5722, 3182, 5730, 0);
    public static final Bounds HOME_MIDDLE = new Bounds(3520, 3096, 3583, 3131, 1);
    public static final Bounds HOME_MIDDLE_BANK = new Bounds(3534, 3118, 3548, 3131, 1);

    public static final Bounds HOME_LOWER = new Bounds(3520, 3096, 3583, 3131, 0);
    public static final Bounds HOME_UPPER = new Bounds(3520, 3096, 3583, 3131, 2);
    private static void spawnNpc(DynamicMap map, int id, int x, int y, int z, Direction direction, int walkRange) {
        x = map.convertX(x);
        y = map.convertY(y);
        new NPC(id).spawn(x, y, z, direction, walkRange);
        if (walkRange == 0)
            Tile.get(x, y, z, true).flagUnmovable();
    }

    private static void spawnObj(DynamicMap map, int id, int x, int y, int z, int type, int direction) {
        GameObject obj = GameObject.spawn(id, map.convertX(x), map.convertY(y), z, type, direction);
        BankActions.markTiles(obj);
    }

    private static void removeObj(DynamicMap map, int id, int x, int y, int z) {
        GameObject.forObj(id, map.convertX(x), map.convertY(y), z, obj -> obj.setId(-1));
    }

    private static void register(DynamicMap map, String teleportName, int portalX, int portalY, int portalDestX, int portalDestY, Bounds... safeZones) {
        /**
         * Override teleport
         */
        for (teleports.Category c : teleports.CATEGORIES) {
            String cName = c.name.replaceAll("<[^>]*>", "");
            if (!cName.equalsIgnoreCase("wilderness"))
                continue;
            for (teleports.Subcategory s : c.subcategories) {
                String sCName = s.name.replaceAll("<[^>]*>", "");
                if (!sCName.equalsIgnoreCase("Instances"))
                    continue;
                for (teleports.Teleport t : s.teleports) {
                    String tName = t.name.replaceAll("<[^>]*>", "");
                    if (!tName.equalsIgnoreCase(teleportName))
                        continue;
                    t.x = map.convertX(t.x);
                    t.y = map.convertY(t.y);
                }
            }
        }
        /**
         * Create portals
         */
        //TODO:: Fix instance portal
        //GameObject sourcePortal = GameObject.spawn(14845, portalX, portalY, 0, 10, 0);
        //GameObject instancePortal = GameObject.spawn(14844, map.convertX(portalX), map.convertY(portalY), 0, 10, 0);
        int instanceDestX = map.convertX(portalDestX);
        int instanceDestY = map.convertY(portalDestY);
       /* ObjectAction.register(sourcePortal, "use", (player, obj) -> {
            player.dialogue(
                    new MessageDialogue("<col=FF0000>Warning:</col> This portal will take you to a PvP instance of " + teleportName + ".<br>Do you wish to proceed?").lineHeight(24),
                    new OptionsDialogue(
                            new Option("Yes, proceed to " + teleportName + "'s PvP instance.", () -> teleports.teleport(player, instanceDestX, instanceDestY, 0)),
                            new Option("No, stay where I am.")
                    )
            );
        });

        ObjectAction.register(instancePortal, "use", (player, obj) -> teleports.teleport(player, portalDestX, portalDestY, 0));
*/
        /**
         * Set safe zones
         */
        Bounds[] convertedSafeZones = new Bounds[safeZones.length];
        for (int i = 0; i < safeZones.length; i++) {
            Bounds b = safeZones[i];
            convertedSafeZones[i] = new Bounds(map.convertX(b.swX), map.convertY(b.swY), map.convertX(b.neX), map.convertY(b.neY), b.z);
        }
        Bounds[] convertedSafeZones2 = new Bounds[safeZones.length];
        for (int i = 0; i < safeZones.length; i++) {
            Bounds b = safeZones[i];
            convertedSafeZones[i] = new Bounds(map.convertX(b.swX), map.convertY(b.swY), map.convertX(b.neX), map.convertY(b.neY), b.z);
        }
        /**
         * Set the safe zones so we can prevent players from entering the safe zones without clicking
         * (ppl trade following, random following with animation delays, etc)
         */
        for (Bounds safeAreas : convertedSafeZones)
            Wilderness.setSafePVPInstance(safeAreas, true);


        /**
         * Register listener
         */
        if (World.isPVPWorld()) {
            MapListener.register(player -> checkActive(player))
                    .onEnter(PVPInstance::entered)
                    .onExit(PVPInstance::exited);
        } else {
            MapListener.register(player -> checkActive(map, player, convertedSafeZones))
                    .onEnter(PVPInstance::entered)
                    .onExit(PVPInstance::exited);
            /**
             * Register home tele override
             */
            HomeTeleport.registerHomeTeleportOverride(map::isIn, new Position(instanceDestX, instanceDestY, 0));
        }
    }


    private static boolean checkActive(DynamicMap map, Player player, Bounds... safeZones) {
        if (!map.isIn(player))
            return false;
        int combatLevel = player.getCombat().getLevel();
        if (player.pvpCombatLevel != combatLevel) {
            player.pvpCombatLevel = combatLevel;
            //player.getPacketSender().sendClientScript(389, "i", combatLevel);
        }
        boolean safeZone = false;
        for (Bounds b : safeZones) {
            if (player.getPosition().inBounds(b)) {
                safeZone = true;
                break;
            }
        }
        if (safeZone) {
            if (!player.pvpAttackZone)
                return true;
            player.pvpAttackZone = false;
            player.attackPlayerListener = null;
            player.getCombat().resetTb();
            player.getCombat().resetKillers(); //important
            player.getPacketSender().setHidden(90, 47, false);
            player.setAction(1, null);
            players.remove(player);
        } else {
            if (player.pvpAttackZone)
                return true;
            player.pvpAttackZone = true;
            player.attackPlayerListener = PVPInstance::allowAttack;
            player.getPacketSender().setHidden(90, 47, true);
            player.setAction(1, PlayerAction.ATTACK);
            players.add(player);
        }
        return true;
    }

    private static void entered(Player player) {
        int kills = Config.PVP_KILLS.get(player);
        int deaths = Config.PVP_DEATHS.get(player);
        if (World.isPVPWorld()) {
            if (player.getBountyHunter().target != null)
                player.getBountyHunter().skip(false);
            if (player.pvpInstancePosition == null)
                player.pvpInstancePosition = player.getPosition().copy();
            player.getPacketSender().sendDiscordPresence("PVP Instance");
            player.getPacketSender().sendVarp(20003, 1); //custom to set client to think pvp world
            player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
            player.getPacketSender().sendString(90, 26,  "Kills: " + Config.PVP_KILLS.get(player) + "<br>Deaths: " + Config.PVP_DEATHS.get(player) + "<br>K/D Ratio: " + String.format("%.2f", ((double) kills / deaths)));
            player.getPacketSender().setHidden(Interface.WILDERNESS_OVERLAY, 47, true); //hide wilderness level
        }
    }

    public static String getKdr(Player player) {
        int kills = Config.PVP_KILLS.get(player);
        int deaths = Config.PVP_DEATHS.get(player);

        return (kills + "/" + deaths + "/" + (deaths == 0 ? "0,00" : String.format("%.2f", ((double) kills / deaths))));
    }

    private static boolean checkActive(Player player) {
        int combatLevel = player.getCombat().getLevel();
        if (player.pvpCombatLevel != combatLevel) {
            player.pvpCombatLevel = combatLevel;
            //player.getPacketSender().sendClientScript(389, "i", combatLevel);
        }
      boolean safeZone = player.getPosition().inBounds(PVPInstance.EDGE_BANK) || player.getPosition().inBounds(PVPInstance.EDGE_CHESTS)
                || player.getPosition().inBounds(PVPInstance.CAMELOT) || player.getPosition().inBounds(PVPInstance.CAMELOT2)
                || player.getPosition().inBounds(PVPInstance.CAMELOT3) || player.getPosition().inBounds(PVPInstance.DZ_UPPER)
                || player.getPosition().inBounds(PVPInstance.DEATH_OUTSIDE) || player.getPosition().inBounds(PVPInstance.DEATH_INSIDE)
                || player.getPosition().inBounds(PVPInstance.HOME_MIDDLE)
                || player.getPosition().inBounds(PVPInstance.HOME_LOWER)
                || player.getPosition().inBounds(PVPInstance.HOME_UPPER);
        if (safeZone) {
            if (!player.pvpAttackZone)
                return true;
            player.pvpAttackZone = false;
            player.attackPlayerListener = null;
            player.getCombat().resetTb();
            player.getCombat().resetKillers(); //important
            player.getPacketSender().setHidden(90, 47, false);
            player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
            player.setAction(1, null);
            players.remove(player);
        } else {
            if (player.pvpAttackZone)
                return true;
            player.pvpAttackZone = true;
            player.attackPlayerListener = PVPInstance::allowAttack;
            player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
            player.getPacketSender().setHidden(90, 47, true);
            player.setAction(1, PlayerAction.ATTACK);
            players.add(player);
        }
        return true;
    }

    private static void exited(Player player, boolean logout) {
        if (World.isPVPWorld()) {
            if (!logout) {
                if (player.pvpAttackZone) {
                    player.pvpAttackZone = false;
                    player.attackPlayerListener = null;
                    player.setAction(1, null);
                    player.getCombat().resetKillers();
                }
                player.pvpInstancePosition = null;
                player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
            }
            player.getPacketSender().sendDiscordPresence("Idle");
            players.remove(player);
        }
    }

    private static boolean allowAttack(Player player, Player pTarget, boolean message) {
        if (!player.pvpAttackZone) {
            if (message)
                player.sendMessage("You can't attack from a safe zone.");
            return false;
        }
        if (!pTarget.pvpAttackZone) {
            if (message)
                player.sendMessage("You can't attack players who are in safe zones.");
            return false;
        }
        if (Math.abs(player.getCombat().getLevel() - pTarget.getCombat().getLevel()) > 15) {
            if (message)
                player.sendMessage("You can't attack " + pTarget.getName() + " - your level difference is too great.");
            return false;
        }
        if (!pTarget.inMulti()) {
            int last = pTarget.getCombat().lastPlayerAttacked;
            if (last != -1 && last != player.getUserId() && pTarget.getCombat().isAttacking(15)) {
                if (message)
                    player.sendMessage(pTarget.getName() + " is fighting another player.");
                return false;
            }
        }
        return true;
    }

    public static void init(List<Region> emptyRegions) {
        /*
         * Camelot
         */
        DynamicMap camelot = new DynamicMap();
        camelot.swRegion = emptyRegions.get(0);
        camelot.buildSw(10806, 3).buildSe(11062, 3);
        spawnNpc(camelot, 3343, 2756, 3481, 0, Direction.SOUTH, 0); //healer
        spawnNpc(camelot, 315, 2757, 3481, 0, Direction.SOUTH, 0); //emblem trader
        spawnNpc(camelot, 4159, 2758, 3481, 0, Direction.SOUTH, 0); //wizard
        spawnNpc(camelot, 2882, 2759, 3481, 0, Direction.SOUTH, 0); //horvik
        spawnObj(camelot, 2693, 2755, 3479, 0, 10, 1); //bank chest
        removeObj(camelot, 576, 2756, 3481, 0); //statue on horvik tile
        removeObj(camelot, 576, 2759, 3481, 0); //statue on emblem trader tile
        removeObj(camelot, 7415, 2755, 3479, 0); //pillar on bank chest tile
        register(
                camelot, "Camelot",
                2755, 3480, 2756, 3480,
                new Bounds(2755, 3476, 2760, 3482, 0), //teleport zone
                new Bounds(2721, 3490, 2730, 3493, -1), //bank (pt 1)
                new Bounds(2724, 3487, 2727, 3489, -1) //bank (pt 2)
        );
        /**
         * Devious
         */
        DynamicMap edgeville = new DynamicMap();
        edgeville.swRegion = emptyRegions.get(1);
        edgeville.buildSw(12342, 1);
        spawnNpc(edgeville, 3343, 3094, 3499, 0, Direction.SOUTH, 0); //healer
        spawnNpc(edgeville, 315, 3095, 3499, 0, Direction.SOUTH, 0); //rustin
        spawnNpc(edgeville, 4159, 3096, 3499, 0, Direction.SOUTH, 0); //wizard
        spawnNpc(edgeville, 2882, 3097, 3499, 0, Direction.SOUTH, 0); //horvik
        removeObj(edgeville, 29241, 3094, 3509, 0); //rejuv pool
        removeObj(edgeville, 31858, 3099, 3508, 0); //occult altar
        removeObj(edgeville, 27288, 3094, 3507, 0); //deadmanchest
        removeObj(edgeville, 2693, 3094, 3513, 0); //bank chest
        removeObj(edgeville, 409, 3100, 3511, 0); //altar
        removeObj(edgeville, 15481, 3076, 3491, 0); //house portal
        removeObj(edgeville, 6882, 3091, 3482, 0); //tournament
        removeObj(edgeville, 6882, 3090, 3482, 0); //tournament
        removeObj(edgeville, 2693, 3095, 3513, 0); //bank chest
        removeObj(edgeville, 26761, 3091, 3506, 0); //bank chest
        removeObj(edgeville, 31675, 3090, 3499, 0); //bank chest
        register(
                edgeville, "Edgeville",
                3091, 3497, 3092, 3497,
                new Bounds(3091, 3488, 3098, 3499, 0) //bank
        );

        /*
         * Falador
         */
        DynamicMap falador = new DynamicMap();
        falador.swRegion = emptyRegions.get(2);
        falador.buildSw(11572, 3).buildSe(11828, 3);
        spawnNpc(falador, 3343, 2943, 3372, 0, Direction.EAST, 0); //healer
        spawnNpc(falador, 315, 2943, 3371, 0, Direction.EAST, 0); //emblem trader
        spawnNpc(falador, 4159, 2943, 3370, 0, Direction.EAST, 0); //wizard
        spawnNpc(falador, 2882, 2943, 3369, 0, Direction.EAST, 0); //horvik
        register(
                falador, "Falador",
                2947, 3371, 2946, 3371,
                new Bounds(2943, 3368, 2947, 3373, 0), //west bank (pt1)
                new Bounds(2948, 3368, 2949, 3369, 0) //west bank (pt2)
        );
        /*
         * Lumbridge
         */
        DynamicMap lumbridge = new DynamicMap();
        lumbridge.swRegion = emptyRegions.get(3);
        lumbridge.buildSw(12850, 2);
        spawnNpc(lumbridge, 3343, 3221, 3217, 0, Direction.EAST, 0); //healer
        spawnNpc(lumbridge, 315, 3221, 3218, 0, Direction.EAST, 0); //emblem trader
        spawnNpc(lumbridge, 4159, 3221, 3219, 0, Direction.EAST, 0); //wizard
        spawnNpc(lumbridge, 2882, 3221, 3220, 0, Direction.EAST, 0); //horvik
        spawnObj(lumbridge, 2693, 3223, 3216, 0, 10, 0); //bank chest
        removeObj(lumbridge, 29094, 3223, 3217, 0); //south bush
        removeObj(lumbridge, 29715, 3223, 3220, 0); //north bush
        removeObj(lumbridge, 1124, 3223, 3217, 0); //north bush
        removeObj(lumbridge, 10778, 3223, 3217, 0); //north bush
        register(
                lumbridge, "Lumbridge",
                3217, 3215, 3218, 3215,
                new Bounds(3216, 3209, 3226, 3228, 0),
                new Bounds(3227, 3217, 3229, 3220, 0)
        );
    }

}