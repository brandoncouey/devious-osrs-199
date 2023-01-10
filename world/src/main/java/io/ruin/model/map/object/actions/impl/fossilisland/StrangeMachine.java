package io.ruin.model.map.object.actions.impl.fossilisland;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class StrangeMachine {
    private static void inspectMachine(Player player, GameObject machine) {
        player.dialogue(
                new PlayerDialogue("I wonder if I can use Wyvern Visage<br>" +
                        "with this machine...")
        );
    }

    private static void craftShield(Player player, Item item, GameObject object) {
        player.dialogue(new ItemDialogue().one(21637, "You are about to turn your<br>" +
                        "Wyvern Visage into an Ancient<br>" +
                        "Wyvern Shield"),
                new OptionsDialogue("Would you like to continue?",
                        new Option("No thanks."),
                        new Option("Yes!", () -> {
                            object.animate(7734);
                            player.getInventory().remove(21637, 1);
                            player.getInventory().add(21633, 1);
                            player.dialogue(
                                    new ItemDialogue().one(21633, "You have successfully created<br>" +
                                            "Ancient Wyvern Shield!"));
                            object.animate(7733);
                        })));
    }

    private static void revertShield(Player player, Item item, GameObject object) {
        player.dialogue(new ItemDialogue().one(21633, "You are about to rever your<br>" +
                        "Ancient Wyvern Shield<br>" +
                        "Wyvern Visage"),
                new OptionsDialogue("Would you like to continue?",
                        new Option("No thanks."),
                        new Option("Yes!", () -> {
                            player.getInventory().remove(21633, 1);
                            player.getInventory().add(21637, 1);
                            object.animate(7734);
                            player.dialogue(
                                    new ItemDialogue().one(21637, "You have successfully reclaimed<br>" +
                                            "your Wyvern Visage!"));
                        })));
    }

    static {
        ObjectAction.register(30944, "Investigate", StrangeMachine::inspectMachine);
        ItemObjectAction.register(21637, 30944, StrangeMachine::craftShield);
        ItemObjectAction.register(21633, 30944, StrangeMachine::revertShield);
    }
}