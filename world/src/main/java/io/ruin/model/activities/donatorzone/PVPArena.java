package io.ruin.model.activities.donatorzone;

import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerAction;
import io.ruin.model.entity.shared.StepType;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

import static io.ruin.model.activities.wilderness.Wilderness.players;

public class PVPArena {

    public static final Bounds pvparena = new Bounds(3807, 2843, 3813, 2849, 3);

    public static void entered(Player player) {
        player.pvpAttackZone = true;
        player.attackPlayerListener = PVPArena::allowAttack;
        //player.getPacketSender().setHidden(90, 57, true);
        player.setAction(1, PlayerAction.ATTACK);
        players.add(player);
        player.getPacketSender().sendDiscordPresence("PVP Instance");
        player.getPacketSender().sendVarp(20003, 1); //custom to set client to think pvp world
        player.getCombat().skullHighRisk();
        player.openInterface(InterfaceType.WILDERNESS_OVERLAY, Interface.WILDERNESS_OVERLAY);
    }

    private static void exited(Player player, boolean logout) {
        if (!logout) {
            if (player.pvpAttackZone) {
                player.pvpAttackZone = false;
                player.attackPlayerListener = null;
                player.setAction(1, null);
                player.getCombat().resetKillers();
            }
            player.pvpInstancePosition = null;
            player.getCombat().skullNormal();
            player.closeInterface(InterfaceType.WILDERNESS_OVERLAY);
        }
        player.getPacketSender().sendDiscordPresence("Idle");
        players.remove(player);
    }

    private static void BarrierNSRoomOne(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if (player.getAbsX() == 3814) {
                player.stepAbs(player.getAbsX() - 1, player.getAbsY(), StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Cage.");
            } else {
                if (player.getAbsX() == 3813) {
                    player.stepAbs(player.getAbsX() + 1, player.getAbsY(), StepType.FORCE_WALK);
                    player.sendFilteredMessage("You pass through the Cage.");
                }
            }
        });
    }

    private static void BarrierNSRoomTWO(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if (player.getAbsY() == 2842) {
                player.stepAbs(player.getAbsX(), player.getAbsY() + 1, StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Cage.");
            } else {
                if (player.getAbsY() == 2843) {
                    player.stepAbs(player.getAbsX(), player.getAbsY() - 1, StepType.FORCE_WALK);
                    player.sendFilteredMessage("You pass through the Cage.");
                }
            }
        });
    }

    private static void BarrierNSRoomthree(Player player, GameObject barrier) {
        player.startEvent(event -> {
            if (player.getAbsY() == 2850) {
                player.stepAbs(player.getAbsX(), player.getAbsY() - 1, StepType.FORCE_WALK);
                player.sendFilteredMessage("You pass through the Cage.");
            } else {
                if (player.getAbsY() == 2849) {
                    player.stepAbs(player.getAbsX(), player.getAbsY() + 1, StepType.FORCE_WALK);
                    player.sendFilteredMessage("You pass through the Cage.");
                }
            }
        });
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

    static {
        ObjectAction.register(3782, "open", PVPArena::BarrierNSRoomOne);
        ObjectAction.register(3782, "open", PVPArena::BarrierNSRoomTWO);
        ObjectAction.register(3782, "open", PVPArena::BarrierNSRoomthree);
        MapListener.registerBounds(pvparena)
                .onEnter(PVPArena::entered)
                .onExit(PVPArena::exited);
    }
}



