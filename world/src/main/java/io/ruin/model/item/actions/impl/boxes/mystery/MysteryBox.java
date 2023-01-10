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

public class MysteryBox {
    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(384, 100, 30), //Raw Sharks
            new LootItem(1514, 50, 30), //Magic Logs
            new LootItem(1516, 100, 30), //Yew Logs
            new LootItem(2364, 50, 30), //Rune Bars
            new LootItem(2362, 100, 30), //Adamant Bars
            new LootItem(1618, 50, 30), //Uncut Diamonds
            new LootItem(1620, 100, 30), //Uncut Rubies
            new LootItem(4708, 1, 30), //Ahrim's hood
            new LootItem(4712, 1, 30), //Ahrim's robetop
            new LootItem(4714, 1, 30), //Ahrim's robeskirt
            new LootItem(4716, 1, 30), //Dharok's helm
            new LootItem(4720, 1, 30), //Dharok's platebody
            new LootItem(11237, 200, 24),
            new LootItem(4722, 1, 30), //Dharok's platelegs
            new LootItem(4718, 1, 30), //Dharok's greataxe
            new LootItem(4736, 1, 30), //Karil's Leathertop
            new LootItem(4738, 1, 30), //Karil's Bottom
            new LootItem(4734, 1, 30), //Karil's Cross Bow
            new LootItem(4732, 1, 30), //Karil's Coif
            new LootItem(4728, 1, 30), //Guthan's Body
            new LootItem(4730, 1, 30), //Guthan's Skirt
            new LootItem(4726, 1, 30), //Guthan's Spear
            new LootItem(4724, 1, 30), //Guthan's Helm
            new LootItem(4753, 1, 30), //Verac Helm
            new LootItem(4757, 1, 30), //Verac Body
            new LootItem(4759, 1, 30), //Verac Skirt
            new LootItem(4755, 1, 30), //Verac flail
            new LootItem(4745, 1, 30), //Torag Helm
            new LootItem(4749, 1, 30), //Torag Body
            new LootItem(4751, 1, 30), //Torag Legs
            new LootItem(4747, 1, 30), //Torag hammer
            new LootItem(12696, 30, 27), //30 Super combat potions
            new LootItem(13442, 50, 27), //50 Anglerfish
            new LootItem(6922, 1, 15).broadcast(Broadcast.WORLD), //Infinity gloves
            new LootItem(6918, 1, 15).broadcast(Broadcast.WORLD), //Infinity hat
            new LootItem(6916, 1, 15).broadcast(Broadcast.WORLD), //Infinity top
            new LootItem(6924, 1, 15).broadcast(Broadcast.WORLD), //Infinity bottoms
            new LootItem(4151, 1, 5).broadcast(Broadcast.WORLD), //Abyssal whip
            new LootItem(2581, 1, 5).broadcast(Broadcast.WORLD), //Robin hood hat
            new LootItem(6585, 1, 5).broadcast(Broadcast.WORLD), //Amulet of fury
            new LootItem(11283, 1, 3).broadcast(Broadcast.WORLD), //Dragonfire shield
            new LootItem(11235, 1, 3).broadcast(Broadcast.WORLD), //Dark bow
            new LootItem(2577, 1, 1).broadcast(Broadcast.WORLD), //Ranger boots
            new LootItem(6920, 1, 1).broadcast(Broadcast.WORLD) //Infinity boots
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
        ItemAction.registerInventory(6199, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward;
            reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Champion Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(6199, "gift", MysteryBox::gift);
    }
}
