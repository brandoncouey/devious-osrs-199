package io.ruin.model.item.actions.impl.boxes.mystery;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.Icon;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.loot.LootItem;
import io.ruin.model.item.loot.LootTable;
import io.ruin.utility.Broadcast;

public class PVMBox {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(995, Random.get(100_000, 150_000), 80), //COINS CUNT
            new LootItem(995, Random.get(75_000, 300_000), 40), //COINS CUNT
            new LootItem(995, Random.get(75_000, 400_000), 30), //COINS CUNT
            new LootItem(995, Random.get(75_000, 500_000), 20), //COINS CUNT
            new LootItem(224, Random.get(40, 70), 40), //Secondaries
            new LootItem(232, Random.get(40, 70), 40), //Secondaries
            new LootItem(240, Random.get(40, 70), 40), //Secondaries
            new LootItem(537, 25, 35),      // Dragon Bones
            new LootItem(12696, 10, 20, 25), //Super combat potions
            new LootItem(13442, 50, 75, 25), //Anglerfish
            new LootItem(12696, 10, 20, 25), //Super combat potions
            new LootItem(13442, 50, 75, 25), //Anglerfish
            new LootItem(2435, 15, 15, 25), //Anglerfish
            new LootItem(990, 3, 20),     // Crystal Key
            new LootItem(30256, 3, 30),      // Vote Multipass
            new LootItem(24361, 3, 30),      // Vote Multipass
            new LootItem(24362, 3, 27),      // Vote Multipass
            new LootItem(24363, 3, 26),      // Vote Multipass
            new LootItem(24364, 3, 26),      // Vote Multipass
            new LootItem(24365, 3, 25),      // Vote Multipass
            new LootItem(24366, 3, 24),      // Vote Multipass
            new LootItem(11237, 200, 24),
            new LootItem(30306, 1, 3).broadcast(Broadcast.WORLD), //Seasonal box
            new LootItem(22625, 1, 2).broadcast(Broadcast.GLOBAL), // Statius Helm
            new LootItem(22628, 1, 2).broadcast(Broadcast.GLOBAL), // Statius Plate
            new LootItem(22631, 1, 2).broadcast(Broadcast.GLOBAL), // Statius Plate Legs
            new LootItem(22638, 1, 2).broadcast(Broadcast.GLOBAL), // Morrigans Coif
            new LootItem(22641, 1, 2).broadcast(Broadcast.GLOBAL), // Morrigans Body
            new LootItem(22644, 1, 2).broadcast(Broadcast.GLOBAL),  // Morrigans Legs
            new LootItem(30307, 1, 10).broadcast(Broadcast.GLOBAL) //$10 Bond
    );

    private static void gift(Player player, Item box) {
        int boxId = box.getId();
        player.stringInput("Enter player's display name:", name -> {
            if (!player.getInventory().hasId(boxId))
                return;
            name = name.replaceAll("[^a-zA-Z0-9\\s]", "");
            name = name.substring(0, Math.min(name.length(), 12));
            if (name.isEmpty()) {
                player.retryStringInput("Invalid username, try again:");
                return;
            }
            if (name.equalsIgnoreCase(player.getName())) {
                player.retryStringInput("Cannot gift yourself, try again:");
                return;
            }
            Player target = World.getPlayer(name);
            if (target == null) {
                player.retryStringInput("Player cannot be found, try again:");
                return;
            }
            if (target.getGameMode().isIronMan()) {
                player.retryStringInput("That player is an ironman and can't receive gifts!");
                return;
            }
            player.stringInput("Enter a message for " + target.getName() + ":", message -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Gift your " + box.getDef().name + " to " + target.getName() + "?", box, () -> {
                    if (!player.getInventory().hasId(boxId))
                        return;
                    player.getInventory().remove(boxId, 1);
                    if (!target.getInventory().isFull())
                        target.getInventory().add(boxId, 1);
                    else
                        target.getBank().add(boxId, 1);
                    target.sendMessage("<img=91> " + Color.DARK_RED.wrap(player.getName() + " has just gifted you " + box.getDef().descriptiveName + "!"));
                    player.sendMessage("<img=91> " + Color.DARK_RED.wrap("You have successfully gifted your " + box.getDef().name + " to " + target.getName() + "."));
                    if (!message.isEmpty())
                        target.sendMessage("<img=91> " + Color.DARK_RED.wrap("[NOTE] " + message));
                }));
            });
        });
    }

}
