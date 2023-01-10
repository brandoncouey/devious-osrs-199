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

public class LuxuryBox {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(11808, 1, 7).broadcast(Broadcast.WORLD), //Zamorak godsword
            new LootItem(11806, 1, 7).broadcast(Broadcast.WORLD), //Saradomin godsword
            new LootItem(11802, 1, 7).broadcast(Broadcast.WORLD), //Armadyl godsword
            new LootItem(11804, 1, 7).broadcast(Broadcast.WORLD), //Bandos godsword
            new LootItem(12929, 1, 7).broadcast(Broadcast.WORLD), //Serpentine helm
            new LootItem(13235, 1, 7).broadcast(Broadcast.WORLD), //Eternal boots
            new LootItem(13237, 1, 7).broadcast(Broadcast.WORLD), //Pegasian boots
            new LootItem(13239, 1, 7).broadcast(Broadcast.WORLD), //Primordial boots
            new LootItem(11785, 1, 4).broadcast(Broadcast.WORLD), //Armadyl crossbow
            new LootItem(13576, 1, 4).broadcast(Broadcast.WORLD), //Dragon warhammer
            new LootItem(11832, 1, 4).broadcast(Broadcast.WORLD), // Bandos chestplate
            new LootItem(11834, 1, 4).broadcast(Broadcast.WORLD), // Bandos tassets
            new LootItem(22477, 1, 4).broadcast(Broadcast.WORLD), // Avernic Defender Hilt
            new LootItem(11828, 1, 4).broadcast(Broadcast.WORLD), // Armadyl chestplate
            new LootItem(11830, 1, 4).broadcast(Broadcast.WORLD), // Armadyl chainskirt
            new LootItem(12924, 1, 3).broadcast(Broadcast.WORLD), // Toxic blowpipe (empty)
            new LootItem(19547, 1, 3).broadcast(Broadcast.WORLD), // Necklace of Anguish
            new LootItem(19553, 1, 3).broadcast(Broadcast.WORLD), // Amulet of Torture
            new LootItem(13652, 1, 3).broadcast(Broadcast.WORLD), // Dragon Claws
            new LootItem(962, 1, 2).broadcast(Broadcast.WORLD), // Christmas Cracker
            new LootItem(11862, 1, 2).broadcast(Broadcast.WORLD), // Black partyhat
            new LootItem(11863, 1, 2).broadcast(Broadcast.WORLD), // Rainbow partyhat
            new LootItem(786, 1, 3).broadcast(Broadcast.WORLD), // Toxic blood scroll
            new LootItem(1037, 1, 2).broadcast(Broadcast.WORLD), // Bunny ears
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
        ItemAction.registerInventory(290, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward;
            reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Luxury Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(290, "gift", LuxuryBox::gift);
    }
}

