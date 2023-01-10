package io.ruin.model.activities.bosses.nightmare;

import io.ruin.model.World;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;

public class EnergyBarrier {
    static {
        ObjectAction.register(37730, "Pass-through", (player, obj) -> exit(player));
    }

    private static void exit(Player player) {
        player.dialogue(new MessageDialogue("Would you like to exit?"),
                new OptionsDialogue(
                        new Option("Yes.", () -> {
                            player.getMovement().startTeleport(event -> {
                                player.getPacketSender().nightmarefadeOut();
                                if (player.getPosition().getRegion().players.size() <= 1) {
                                    for (NPC npc : World.npcs) {
                                        if (npc.getPosition().getRegion() == player.getPosition().getRegion()) {
                                            npc.remove();
                                        }
                                    }
                                }
                                event.delay(2);
                                player.getMovement().teleport(3808, 9749, 1);
                                player.getPacketSender().nightmarefadeIn();
                                NightmareEvent.leaveInstance(player);
                                player.unlock();
                            });
                        }),
                        new Option("No.")));
        return;
    }

}
