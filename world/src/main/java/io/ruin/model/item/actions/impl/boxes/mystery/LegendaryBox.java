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

public class LegendaryBox {

    public static final LootTable MYSTERY_BOX_TABLE = new LootTable().addTable(1,
            new LootItem(11828, 1, 35), //Armadyl chestplate
            new LootItem(11830, 1, 35), //Armadyl chainskirt
            new LootItem(11826, 1, 35), //Armadyl helmet
            new LootItem(11834, 1, 35), //Bandos tassets
            new LootItem(11832, 1, 35), //Bandos chestplate
            new LootItem(12006, 1, 30), //Abyssal tentacle
            new LootItem(12902, 1, 30), //Toxic staff of the dead
            new LootItem(13271, 1, 25), //Abyssal dagger
            new LootItem(11785, 1, 25), //Armadyl crossbow
            new LootItem(13576, 1, 25), //Dragon warhammer
            new LootItem(22981, 1, 20), //Ferocious gloves
            new LootItem(22975, 1, 20), //Brimstone ring
            new LootItem(22978, 1, 20), //Dragon hunter lance
            new LootItem(11832, 1, 15), //Bandos chestplate
            new LootItem(11808, 1, 15), //Zamorak godsword
            new LootItem(11806, 1, 15), //Saradomin godsword
            new LootItem(11804, 1, 15), //Bandos godsword
            new LootItem(13652, 1, 10).broadcast(Broadcast.WORLD), //Dragon Claws
            new LootItem(19553, 1, 10).broadcast(Broadcast.WORLD), //Amulet of torture
            new LootItem(19547, 1, 10).broadcast(Broadcast.WORLD), //Amulet of Anguish
            new LootItem(19544, 1, 10).broadcast(Broadcast.WORLD), //Tormented Bracelet
            new LootItem(19550, 1, 10).broadcast(Broadcast.WORLD), //Ring of Suffering
            new LootItem(30249, 1, 10).broadcast(Broadcast.WORLD), //$25 Bond
            new LootItem(30251, 1, 2).broadcast(Broadcast.WORLD), //$50 Bond
            new LootItem(12821, 1, 1).broadcast(Broadcast.WORLD), //Spectral Spirit Shield
            new LootItem(12825, 1, 1).broadcast(Broadcast.WORLD), //Ancestral Spirit Shield
            new LootItem(13343, 1, 1).broadcast(Broadcast.WORLD), //Black Santa
            new LootItem(11847, 1, 1).broadcast(Broadcast.WORLD), //Black H'ween
            new LootItem(11862, 1, 1).broadcast(Broadcast.WORLD), //Black Party Hat
            new LootItem(21034, 1, 1).broadcast(Broadcast.WORLD), //Dex Scroll
            new LootItem(21079, 1, 1).broadcast(Broadcast.WORLD) //Arcane Scroll
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
        ItemAction.registerInventory(6828, "open", (player, item) -> {
            player.lock();
            player.closeDialogue();
            Item reward;
            reward = MYSTERY_BOX_TABLE.rollItem();
            item.remove(1);
            player.getInventory().add(reward);
            if (reward.lootBroadcast != null)
                Broadcast.WORLD.sendNews(Icon.MYSTERY_BOX, "Mega Box", "" + player.getName() + " just received " + reward.getDef().descriptiveName + "!");
            player.unlock();
        });

        /*
         * Mystery box gifting
         */
        ItemAction.registerInventory(6828, "gift", LegendaryBox::gift);
    }

}
