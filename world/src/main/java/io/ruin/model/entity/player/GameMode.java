package io.ruin.model.entity.player;

import io.ruin.api.utils.NumberUtils;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.Broadcast;

public enum GameMode {
    STANDARD(-1),
    IRONMAN(20),
    ULTIMATE_IRONMAN(21),
    HARDCORE_IRONMAN(22),
    GROUP_IRONMAN(23),
    HARDCORE_GROUP_IRONMAN(24);

    public final int groupId;

    GameMode(int groupId) {
        this.groupId = groupId;
    }

    /**
     * NOTE: this returns true if this game mode is ANY of the 3 iron man modes, not only the regular ironman mode!
     */
    public boolean isIronMan() {
        return this != STANDARD;
    }

    public boolean isRegular() {
        return this == STANDARD;
    }

    public boolean isUltimateIronman() {
        return this == ULTIMATE_IRONMAN;
    }

    public boolean isHardcoreIronman() {
        return this == HARDCORE_IRONMAN;
    }

    public boolean isGroupIronman() {
        return this == GROUP_IRONMAN;
    }

    public boolean isHardcoreGroupIronman() {
        return this == HARDCORE_GROUP_IRONMAN;
    }


    public static void hardcoreDeath(Player player, Hit killHit) {
        Config.IRONMAN_MODE.set(player, 1);
//        changeForumsGroup(player, IRONMAN.groupId);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 100) {
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            if (killHit == null) {
                Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman with a total level of " + overall + "!"));
            } else if (killHit.attacker != null) {
                if (killHit.attacker instanceof Player) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman with a total level of " + overall + ", losing a fight to " + killHit.attacker.player.getName() + "!"));
                } else {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman with a total level of " + overall + ", brutally executed by " + killHit.attacker.npc.getDef().descriptiveName + "!"));
                }
            } else {
                if (killHit.type == HitType.POISON) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has been poisoned to death as a Hardcore Ironman with a total level of " + overall + "!"));
                } else if (killHit.type == HitType.VENOM) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has succumbed to venom as a Hardcore Ironman with a total level of " + overall + "!"));
                } else { // not sure if this can happen? can't think of anything
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman with a total level of " + overall + "!"));
                }
            }
        }
    }

    public static void hardcoreGroupDeath(Player player, Hit killHit) {
        Config.IRONMAN_MODE.set(player, 4);
//        changeForumsGroup(player, IRONMAN.groupId);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Group Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 100) {
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            if (killHit == null) {
                Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Group Ironman with a total level of " + overall + "!"));
            } else if (killHit.attacker != null) {
                if (killHit.attacker instanceof Player) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Group Ironman with a total level of " + overall + ", losing a fight to " + killHit.attacker.player.getName() + "!"));
                } else {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Group Ironman with a total level of " + overall + ", brutally executed by " + killHit.attacker.npc.getDef().descriptiveName + "!"));
                }
            } else {
                if (killHit.type == HitType.POISON) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has been poisoned to death as a Hardcore Ironman with a total level of " + overall + "!"));
                } else if (killHit.type == HitType.VENOM) {
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has succumbed to venom as a Hardcore Group Ironman with a total level of " + overall + "!"));
                } else { // not sure if this can happen? can't think of anything
                    Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Group Ironman with a total level of " + overall + "!"));
                }
            }
        }
    }

    public static void hardcoreGroupDeathByNPC(Player player, String npcName) {
        Config.IRONMAN_MODE.set(player, 4);
//        changeForumsGroup(player, IRONMAN.groupId);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Group Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 1) { // hmm
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Group Ironman with a total level of " + overall + ", brutally executed by " + npcName + "!"));
        }
    }

    public static void hardcoreDeathByNPC(Player player, String npcName) {
        Config.IRONMAN_MODE.set(player, 1);
//        changeForumsGroup(player, IRONMAN.groupId);
        player.sendMessage(Color.RED.wrap("You have fallen as a Hardcore Ironman, your Hardcore status has been revoked."));
        if (player.getStats().totalLevel >= 1) { // hmm
            String overall = NumberUtils.formatNumber(player.getStats().totalLevel);
            Broadcast.WORLD.sendPlain(Color.RED.wrap(Icon.HCIM_DEATH.tag() + player.getName() + " has died as a Hardcore Ironman with a total level of " + overall + ", brutally executed by " + npcName + "!"));
        }
    }

    public static void openSelection(Player player) {
        player.openInterface(InterfaceType.MAIN, 215);
        player.getPacketSender().setHidden(215, 16, true);
        player.getPacketSender().setHidden(215, 17, true);
    }

//    public static void changeForumsGroup(Player player, int mode) {
//        CompletableFuture.runAsync(() -> {
//            Map<Object, Object> map = new HashMap<>();
//            map.put("userId", player.getUserId());
//            map.put("type", "ironman_mode");
//            map.put("groupId", mode);
//            XenPost.post("add_group", map);
//        });
//    }

    static {
        InterfaceHandler.register(215, h -> {
            h.actions[7] = (SimpleAction) p -> Config.IRONMAN_MODE.set(p, 0);
            h.actions[9] = (SimpleAction) p -> {
                Config.IRONMAN_MODE.set(p, 1);
//                changeForumsGroup(p, IRONMAN.groupId);
            };
            h.actions[10] = (SimpleAction) p -> {
                Config.IRONMAN_MODE.set(p, 3);
//                changeForumsGroup(p, HARDCORE_IRONMAN.groupId);
            };
            h.actions[11] = (SimpleAction) p -> {
                Config.IRONMAN_MODE.set(p, 2);
//                changeForumsGroup(p, ULTIMATE_IRONMAN.groupId);
            };

            h.actions[13] = (SimpleAction) p -> {
                Config.IRONMAN_MODE.set(p, 4);
//                changeForumsGroup(p, GROUP_IRONMAN.groupId);
                XpMode.setXpMode(p, XpMode.HARD);

            };
            h.actions[31] = (SimpleAction) p -> {
                Config.IRONMAN_MODE.set(p, 5);
//                changeForumsGroup(p, HARDCORE_GROUP_IRONMAN.groupId);
                XpMode.setXpMode(p, XpMode.HARD);
            };

        });
    }
}
