package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;

public class XpCounter {

    public static void select(Player player, int option) {
        if (option == 1) {
            /**
             * Toggle
             */
            boolean enabled = Config.XP_COUNTER_SHOWN.toggle(player) == 1;
            int childId = player.isFixedScreen() ? 16/*16*/ : 7/*7*/;
            if (enabled)
                player.getPacketSender().sendInterface(Interface.EXPERIENCE_COUNTER, player.getGameFrameId(), childId, 1);
            else
                player.getPacketSender().removeInterface(player.getGameFrameId(), childId);
        } else {
            /**
             * Setup
             */
            player.getPacketSender().sendVarp(638, 0); //selected stat << 23 | selected tracker << 28
            player.getPacketSender().sendVarp(261, 0); //start point
            player.getPacketSender().sendVarp(262, 0); //end point
            player.getPacketSender().sendClientScript(917, "ii", -1, -1);
            player.openInterface(InterfaceType.MAIN, 137);
            player.getPacketSender().sendAccessMask(137, 51, 1, 3, 2);
            player.getPacketSender().sendAccessMask(137, 52, 1, 3, 2);
            player.getPacketSender().sendAccessMask(137, 53, 1, 4, 2);
            player.getPacketSender().sendAccessMask(137, 54, 1, 32, 2);
            player.getPacketSender().sendAccessMask(137, 55, 1, 32, 2);
            player.getPacketSender().sendAccessMask(137, 56, 1, 8, 2);
            player.getPacketSender().sendAccessMask(137, 57, 1, 2, 2);
            player.getPacketSender().sendAccessMask(137, 58, 1, 3, 2);
            player.getPacketSender().sendAccessMask(137, 59, 1, 3, 2);
            player.getPacketSender().sendAccessMask(137, 17, 0, 24, 2);
        }
    }

    static {
        InterfaceHandler.register(137, h -> {
            h.actions[51] = (SlotAction) (player, slot) -> Config.XP_COUNTER_POSITION.set(player, slot - 1);
            h.actions[52] = (SlotAction) (player, slot) -> Config.XP_COUNTER_SIZE.set(player, slot - 1);
            h.actions[58] = (SlotAction) (player, slot) -> Config.XP_COUNTER_SPEED.set(player, slot - 1);
            h.actions[53] = (SlotAction) (player, slot) -> Config.XP_COUNTER_DURATION.set(player, slot - 1);
            h.actions[54] = (SlotAction) (player, slot) -> Config.XP_COUNTER_COUNTER.set(player, slot - 1);
            h.actions[55] = (SlotAction) (player, slot) -> Config.XP_COUNTER_PROGRESS_BAR.set(player, slot - 1);
            h.actions[56] = (SlotAction) (player, slot) -> Config.XP_COUNTER_COLOUR.set(player, slot - 1);
            h.actions[57] = (SlotAction) (player, slot) -> Config.XP_COUNTER_GROUP.set(player, slot - 1);
            h.actions[59] = (SlotAction) (player, slot) -> {
                if (slot == 2) {
                    player.sendMessage("Fake XP Drops Toggle Off!");
                    Config.XP_FAKE_DROPS.set(player, 1);
                    player.showHitAsExperience = false;
                } else if (slot == 1) {
                    player.sendMessage("Fake XP Drops Toggle On!");
                    Config.XP_FAKE_DROPS.set(player, 0);
                    player.showHitAsExperience = true;
                }
            };
        });
    }

}