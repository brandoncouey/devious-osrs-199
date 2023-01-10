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

public class VoteBox {

    public static final LootTable VOTE_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(995, Random.get(50_000, 500_000), 100), //COINS CUNT
            new LootItem(995, Random.get(500_000, 1_000_000), 40), //COINS
            new LootItem(995, Random.get(500_000, 2_000_000), 30), //COINS
            new LootItem(995, Random.get(500_000, 3_000_000), 20), //COINS
            new LootItem(19473, 10, 20), //Bag Full of Gems
            new LootItem(11738, 10, 20), //Herb Box
            new LootItem(30256, 5, 15), //Vote Multipass
            new LootItem(6199, 1, 5), //Mystery Box
            new LootItem(290, 1, 3), //Super Mystery Box
            new LootItem(11237, 200, 24),
            new LootItem(30306, 1, 2).broadcast(Broadcast.WORLD), //Seasonal box
            new LootItem(1053, 1, 1).broadcast(Broadcast.WORLD), //Green halloween mask
            new LootItem(1055, 1, 1).broadcast(Broadcast.WORLD), //Blue halloween mask
            new LootItem(1057, 1, 1).broadcast(Broadcast.WORLD) //Red halloween mask
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
        ItemAction.registerInventory(6829, "open", (player, item) -> {
            Item reward = VOTE_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Vote Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
        });

        /*
         * box gifting
         */
        ItemAction.registerInventory(6829, "gift", VoteBox::gift);
    }

}
