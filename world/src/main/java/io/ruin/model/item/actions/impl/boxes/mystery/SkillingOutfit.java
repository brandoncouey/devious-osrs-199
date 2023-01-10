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

public class SkillingOutfit {

    private static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(995, Random.get(10_000, 50_000), 80), //COINS CUNT
            new LootItem(995, Random.get(10_000, 100_000), 40), //COINS CUNT
            new LootItem(222, Random.get(1, 3), 20), //Secondaries
            new LootItem(226, Random.get(1, 3), 20), //Secondaries
            new LootItem(224, Random.get(1, 3), 20), //Secondaries
            new LootItem(232, Random.get(1, 3), 20), //Secondaries
            new LootItem(240, Random.get(1, 3), 20), //Secondaries
            new LootItem(12696, 1, 23), //1 Super combat potions
            new LootItem(13442, 1, 23), //1 Anglerfish
            new LootItem(537, 1, 20),      // Dragon Bones
            new LootItem(990, 1, 20),     // Crystal Key
            //Skilling Outfits
            new LootItem(775, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(776, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(1580, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(24872, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(24874, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(24876, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(24878, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13646, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13642, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13643, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13644, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(12013, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(12014, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(12015, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(12016, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25549, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25551, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25553, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25555, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13258, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13259, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13260, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(13261, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25992, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25594, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25596, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(25598, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(5554, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(5553, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(5555, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(5556, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(5557, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(10941, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(10939, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(10940, 1, 5).broadcast(Broadcast.WORLD),
            new LootItem(10933, 1, 5).broadcast(Broadcast.WORLD)

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
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Gift your Skilling Box to " + target.getName() + "?", box, () -> {
                    if (!player.getInventory().hasId(boxId))
                        return;
                    player.getInventory().remove(boxId, 1);
                    if (!target.getInventory().isFull())
                        target.getInventory().add(boxId, 1);
                    else
                        target.getBank().add(boxId, 1);
                    target.sendMessage("<img=91> " + Color.DARK_RED.wrap(player.getName() + " has just gifted you a Skilling Box!"));
                    player.sendMessage("<img=91> " + Color.DARK_RED.wrap("You have successfully gifted your Skilling Box to " + target.getName() + "."));
                    if (!message.isEmpty())
                        target.sendMessage("<img=91> " + Color.DARK_RED.wrap("[NOTE] " + message));
                }));
            });
        });
    }

    static {
        ItemAction.registerInventory(25651, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            if (Random.get() >= 0.5) {
                Item reward;
                reward = MYSTERY_BOX_TABLE.rollItem();
                item.remove(1);
                player.getInventory().add(reward);
                if (reward.lootBroadcast != null)
                    Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Skilling Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            } else {
                int amount = Random.get(50, 100);
                player.sendFilteredMessage("You got " + amount + " AFK Points from your Skilling Box >:D...");
                player.afkPoints += amount;
                item.remove(1);
            }
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(25651, "gift", SkillingOutfit::gift);
    }
}
