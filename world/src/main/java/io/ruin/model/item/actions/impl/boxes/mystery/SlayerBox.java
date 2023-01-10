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

public class SlayerBox {
    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(995, Random.get(50_000, 100_000), 80), //COINS CUNT
            new LootItem(995, Random.get(50_000, 300_000), 40), //COINS CUNT
            new LootItem(995, Random.get(50_000, 400_000), 30), //COINS CUNT
            new LootItem(995, Random.get(50_000, 500_000), 20), //COINS CUNT
            new LootItem(222, Random.get(3, 10), 40), //Secondaries
            new LootItem(226, Random.get(3, 10), 40), //Secondaries
            new LootItem(224, Random.get(3, 10), 40), //Secondaries
            new LootItem(232, Random.get(3, 10), 40), //Secondaries
            new LootItem(240, Random.get(3, 10), 40), //Secondaries
            new LootItem(12696, 10, 20, 25), //Super combat potions
            new LootItem(13442, 50, 75, 25), //Anglerfish
            new LootItem(12696, 10, 20, 25), //Super combat potions
            new LootItem(13442, 50, 75, 25), //Anglerfish
            new LootItem(2435, 15, 15, 25), //Anglerfish
            new LootItem(20360, 5, 8, 25), // Medium clues
            new LootItem(30256, 3, 30),      // Vote Multipass
            new LootItem(24361, 3, 30),      // Vote Multipass
            new LootItem(24362, 3, 27),      // Vote Multipass
            new LootItem(24363, 3, 26),      // Vote Multipass
            new LootItem(24364, 3, 26),      // Vote Multipass
            new LootItem(24365, 3, 25),      // Vote Multipass
            new LootItem(24366, 3, 24),      // Vote Multipass
            new LootItem(11237, 200, 24),
            new LootItem(386, 50, 20), //Sharks
            new LootItem(2435, 5, 15),// Prayer Potions
            new LootItem(23083, 3, 7), // BrimStone Key
            new LootItem(23490, 3, 7), // Larrans Key
            new LootItem(12831, 1, 5),// Blessed Spirit shield
            new LootItem(30306, 1, 3).broadcast(Broadcast.WORLD), //Seasonal box
            new LootItem(22616, 1, 1).broadcast(Broadcast.GLOBAL), // Vesta's Plate
            new LootItem(22619, 1, 1).broadcast(Broadcast.GLOBAL), // Vesta's Plate Skirt
            new LootItem(22610, 1, 1).broadcast(Broadcast.GLOBAL), // Vesta's Spear
            new LootItem(30307, 1, 10).broadcast(Broadcast.WORLD), //$10 Bond
            new LootItem(20724, 1, 1).broadcast(Broadcast.GLOBAL), // Imbued Heart
            new LootItem(21268, 1, 1).broadcast(Broadcast.GLOBAL) // Eternal Slayer Ring
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

    static {
        ItemAction.registerInventory(6831, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Slayer Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(6831, "gift", SlayerBox::gift);
    }

}
