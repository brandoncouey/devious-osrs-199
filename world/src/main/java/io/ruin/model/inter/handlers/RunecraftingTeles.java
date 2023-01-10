package io.ruin.model.inter.handlers;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.OptionAction;

public class RunecraftingTeles {

    static {
        InterfaceHandler.register(33, h -> {
            h.actions[87] = (OptionAction) (player, option) -> teleport(player, 2574, 4849, 0);//Fire
            h.actions[89] = (OptionAction) (player, option) -> teleport(player, 2726, 4832, 0);//Water
            h.actions[90] = (OptionAction) (player, option) -> teleport(player, 2655, 4830, 0);//Earth
            h.actions[91] = (OptionAction) (player, option) -> teleport(player, 2841, 4830, 0);//Air
            h.actions[92] = (OptionAction) (player, option) -> teleport(player, 2792, 4827, 0);//Mind
            h.actions[93] = (OptionAction) (player, option) -> teleport(player, 2523, 4842, 0);//Body
            h.actions[94] = (OptionAction) (player, option) -> teleport(player, 2400, 4835, 0);//Nature
            h.actions[95] = (OptionAction) (player, option) -> teleport(player, 2281, 4837, 0);//Chaos
            h.actions[96] = (OptionAction) (player, option) -> teleport(player, 2464, 4818, 0);//Law
            h.actions[97] = (OptionAction) (player, option) -> teleport(player, 2145, 4833, 0);//Cosmic
            h.actions[98] = (OptionAction) (player, option) -> teleport(player, 1727, 3825, 0);//Blood
            h.actions[99] = (OptionAction) (player, option) -> teleport(player, 1820, 3862, 0);//Soul
            h.actions[116] = (OptionAction) (player, option) -> teleport(player, 2208, 4830, 0);//Death
            h.actions[117] = (OptionAction) (player, option) -> teleport(player, 2335, 4826, 0);//Wrath
        });
        NPCAction.register(2581, "talk-to", (player, npc) -> player.openInterface(InterfaceType.MAIN, 33));
    }

    public static void teleport(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.lock();
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(x, y, z);
            player.getPacketSender().clearFade();
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

}