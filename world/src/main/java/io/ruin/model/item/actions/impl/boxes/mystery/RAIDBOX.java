package io.ruin.model.item.actions.impl.boxes.mystery;

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

public class RAIDBOX {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
                    new LootItem(22477, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(22326, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(22327, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(22328, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(22324, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(30350, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(25744, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(25742, 1, 1).broadcast(Broadcast.WORLD),
                    new LootItem(22481, 1, 1).broadcast(Broadcast.WORLD),
            new LootItem(21034, 1, 1).broadcast(Broadcast.WORLD), // dexterous scroll
            new LootItem(21079, 1, 1).broadcast(Broadcast.WORLD), // arcane scroll
            new LootItem(21000, 1, 1).broadcast(Broadcast.WORLD), // twisted buckler
            new LootItem(21012, 1, 1).broadcast(Broadcast.WORLD), // dragon hunter crossbow
            new LootItem(21015, 1, 1).broadcast(Broadcast.WORLD), // dinh's bulwark
            new LootItem(21018, 1, 1).broadcast(Broadcast.WORLD), // ancestral hat
            new LootItem(21021, 1, 1).broadcast(Broadcast.WORLD), // ancestral top
            new LootItem(21024, 1, 1).broadcast(Broadcast.WORLD), // ancestral bottom
            new LootItem(13652, 1, 1).broadcast(Broadcast.WORLD), // dragon claws
            new LootItem(30376, 1, 1).broadcast(Broadcast.WORLD), // lightbearer
            new LootItem(21003, 1, 1).broadcast(Broadcast.WORLD), // elder maul
            new LootItem(21043, 1, 1).broadcast(Broadcast.WORLD), // kodai insignia
            new LootItem(20997, 1, 1).broadcast(Broadcast.WORLD), // twisted bow
            new LootItem(30385, 1, 1).broadcast(Broadcast.WORLD), // elder maul
            new LootItem(30386, 1, 1).broadcast(Broadcast.WORLD), // elder maul
            new LootItem(30387, 1, 1).broadcast(Broadcast.WORLD), // elder maul
            new LootItem(24444, 1, 1).broadcast(Broadcast.WORLD),
            new LootItem(30324, 1, 1).broadcast(Broadcast.WORLD),
            new LootItem(20997, 1, 1).broadcast(Broadcast.WORLD), // Twisted Bow
            new LootItem(22486, 1, 1).broadcast(Broadcast.WORLD) // Scythe of Vitur (uncharged)
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
        ItemAction.registerInventory(30424, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward;
            reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Raid Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(30424, "gift", RAIDBOX::gift);
    }
}

