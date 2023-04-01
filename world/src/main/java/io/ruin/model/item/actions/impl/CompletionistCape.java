package io.ruin.model.item.actions.impl;

import io.ruin.cache.Icon;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;

public class CompletionistCape {

    public static void check(Player player) {
        if (doneAchieves(player))
            return;
        Item hood = player.getEquipment().get(Equipment.SLOT_HAT);
        if (hood != null && hood.getDef().compType)
            remove(player, hood);
        Item cape = player.getEquipment().get(Equipment.SLOT_CAPE);
        if (cape != null && cape.getDef().compType)
            remove(player, cape);
    }

    public static boolean wearing(Player player) {
        Item capeId = player.getEquipment().get(Equipment.SLOT_CAPE);
        return capeId != null && capeId.getDef().compType;
    }

    private static void remove(Player player, Item item) {
        int id = item.getId();
        item.remove();
        if (player.getInventory().add(id, 1) == 1) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and added to your inventory because you no longer meet the requirements to wear it.");
            return;
        }
        if (player.getBank().add(id, 1) == 1) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and added to your bank because you no longer meet the requirements to wear it.");
            return;
        }
        new GroundItem(id, 1).spawn();
        player.sendMessage("<col=aa0000>Your " + item.getDef().name + " has been unequipped and dropped to the floor because you no longer meet the requirements to wear it and have no space in your inventory or bank to store it.");
    }
    public static boolean doneAchieves(Player player) {
        return player.getDiaryManager().getDeviousDiary().hasDoneAll() && player.getDiaryManager().getPvpDiary().hasDoneAll() &&
                player.getDiaryManager().getSkillingDiary().hasDoneAll() &&
                player.getDiaryManager().getPvmDiary().hasDoneAll()
                && player.getDiaryManager().getWildernessDiary().hasDoneAll() && player.getStats().total99s >= 22;
    }

    public static void compDone(Player player) {
            player.achievedComp = true;
            player.sendMessage("You've unlocked the Completionist Cape, Go to Mac to purchase. You've also unlocked a new Title!");
            player.titleId = 6;
            completedComps++;
            Broadcast.GLOBAL.sendNews(Icon.NORMAL, "<col=ff0000>[" + player.xpMode.getCombatRate() + "x] </col>" + player.getName() + " has just unlocked the Completionist Cape! ");
            Broadcast.GLOBAL.sendNews(Icon.NORMAL, completedComps + " players have now achieved the Completionist Cape!");

    }
    public static int completedComps = 0;

    public enum CompletionistCapes {

        FIRE(6570, 30174, 30176),
        SARADOMIN(2412, 30127, 30129),
        SARADOMIN_IMBUED(21791, 30131, 30133),
        ZAMORAK(2414, 30137, 30139),
        ZAMORAK_IMBUED(21795, 30141, 30143),
        GUTHIX(2413, 30147, 30149),
        GUTHIX_IMBUED(21793, 30151, 30153),
        AVA(10499, 30157, 30159),
        ASSEMBLER(22109, 30161, 30163),
        ARDOUGNE(13124, 30166, 30168),
        INFERNAL(21295, 30179, 30181);

        public final int secondaryId, newHoodId, newCapeId;

        CompletionistCapes(int secondaryId, int newHoodId, int newCapeId) {
            this.secondaryId = secondaryId;
            this.newHoodId = newHoodId;
            this.newCapeId = newCapeId;
        }
    }

    static {
        for (CompletionistCapes cape : CompletionistCapes.values()) {
            ItemItemAction.register(30125, cape.secondaryId, (player, primary, secondary) -> {
                player.dialogue(new YesNoDialogue("Are you sure you want to do this?", "Combine these capes to create " + ItemDef.get(cape.newCapeId).descriptiveName + "?", primary, () -> {
                    Item hoodItem = player.getInventory().findItem(30123);
                    if (hoodItem == null) {
                        player.dialogue(new ItemDialogue().one(30123, "In order for your completionist cape to absorb this item, you will also need a completionist hood in your inventory."));
                        return;
                    }
                    primary.remove();
                    hoodItem.setId(cape.newHoodId);
                    secondary.setId(cape.newCapeId);
                    player.dialogue(new ItemDialogue().one(cape.newCapeId, "You infuse the items together to produce " + ItemDef.get(cape.newCapeId).descriptiveName + "."));
                }));
            });
            ItemAction.registerInventory(cape.newCapeId, "revert", (player, item) -> {
                if (player.getInventory().getFreeSlots() < 3) {
                    player.dialogue(new MessageDialogue("You need at least 2 inventory slots to do this."));
                    return;
                }
                player.dialogue(new OptionsDialogue("Are you use you want to revert your completionist cape?",
                        new Option("Yes", () -> player.startEvent(event -> {
                            player.lock();
                            item.setId(cape.secondaryId);
                            player.getInventory().add(30125, 1);
                            player.getInventory().add(30123, 1);
                            player.dialogue(new ItemDialogue().two(cape.secondaryId, 30125, "You rip the " + ItemDef.get(cape.secondaryId).name + " from your completionist cape."));
                            player.unlock();
                        })),
                        new Option("No", player::closeDialogue)
                ));
            });
        }
        LoginListener.register(p -> p.addEvent(e -> {
            e.delay(1);
            check(p);
        }));
        ItemDef.forEach(def -> def.compType = def.name.toLowerCase().contains("completionist cape") || def.name.toLowerCase().contains("completionist hood") || def.name.toLowerCase().contains("imbued zamorak completionist")  || def.name.toLowerCase().contains("imbued guthix completionist") || def.name.toLowerCase().contains("imbued saradomin completionist"));
    }

}
