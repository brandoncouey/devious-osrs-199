package io.ruin.model.entity.npc.actions;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.npc.actions.edgeville.Decanter;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.herblore.Herb;
import io.ruin.model.skills.herblore.Potion;

public class HerbCleaner {

    public static void HerbCleaning(Player player) {
        if (player.getInventory().count(995) >= coincounter) {
            for (Herb herb : Herb.values()) {
                if (player.getInventory().contains(herb.grimyId)) {
                    player.getInventory().remove(herb.grimyId, herbcounter);
                    player.getInventory().add(herb.cleanId, herbcounter);
                    player.getInventory().remove(995, coincounter);
                } else if (player.getInventory().contains(ItemDef.get(herb.grimyId).notedId)) {
                    player.getInventory().remove(ItemDef.get(herb.grimyId).notedId, herbcounter);
                    player.getInventory().add(ItemDef.get(herb.cleanId).notedId, herbcounter);
                    player.getInventory().remove(995, coincounter);
                }
            }
        } else {
            player.sendMessage("Sorry you don't have enough coins!");
        }
    }

    public static void MakeUnfPotions(Player player) {
        if (player.getInventory().count(995) >= coincounter) {
            for (Herb herb : Herb.values())
                if (player.getInventory().contains(ItemDef.get(herb.cleanId).notedId) && player.getInventory().contains(ItemDef.get(227).notedId)) {
                    player.getInventory().remove(995, coincounter);
                    vialcounter = player.getInventory().count(ItemDef.get(227).notedId);
                    herbcounter = player.getInventory().count(ItemDef.get(herb.cleanId).notedId);
                    if (vialcounter >= herbcounter) {
                        player.getInventory().remove(ItemDef.get(herb.cleanId).notedId, herbcounter);
                        player.getInventory().remove(ItemDef.get(227).notedId, herbcounter);
                        player.getInventory().add(ItemDef.get(herb.unfId).notedId, herbcounter);
                    } else {
                        player.getInventory().remove(ItemDef.get(herb.cleanId).notedId, vialcounter);
                        player.getInventory().remove(ItemDef.get(227).notedId, vialcounter);
                        player.getInventory().add(ItemDef.get(herb.unfId).notedId, vialcounter);
                    }
                } else if (player.getInventory().contains(herb.cleanId) && player.getInventory().contains(227)) {
                    player.getInventory().remove(995, coincounter);
                    vialcounter = player.getInventory().count((227));
                    herbcounter = player.getInventory().count(herb.cleanId);
                    if (vialcounter >= herbcounter) {
                        player.getInventory().remove(herb.cleanId, herbcounter);
                        player.getInventory().remove(227, herbcounter);
                        player.getInventory().add(herb.unfId, herbcounter);
                    } else {
                        player.getInventory().remove(herb.cleanId, vialcounter);
                        player.getInventory().remove(227, vialcounter);
                        player.getInventory().add(herb.unfId, vialcounter);
                    }

                }
        } else {
            player.sendMessage("Sorry you don't have enough coins!");
        }
    }

    public static int vialcounter;
    public static int coincounter;
    public static int herbcounter;

    static {
        NPCAction.register(8532, "clean", (player, npc) -> {
            for (Herb herb : Herb.values())
                if (player.getInventory().contains(herb.grimyId)) {
                    herbcounter = player.getInventory().count(herb.grimyId);
                    coincounter = herbcounter * 200;
                } else if (player.getInventory().contains(ItemDef.get(herb.grimyId).notedId)) {
                    herbcounter = player.getInventory().count(ItemDef.get(herb.grimyId).notedId);
                    coincounter = herbcounter * 200;
                }
            player.dialogue(new OptionsDialogue("Are you sure you want to spend " + coincounter + " Coins! to clean " + herbcounter + " herbs!",
                    new Option("yes", () -> HerbCleaning(player)),
                    new Option("no"))
            );
        });
        NPCAction.register(8532, "make unf potion(s)", (player, npc) -> {
            for (Herb herb : Herb.values())
                if (player.getInventory().contains(herb.cleanId)) {
                    herbcounter = player.getInventory().count(herb.cleanId);
                    coincounter = herbcounter * 200;
                } else if (player.getInventory().contains(ItemDef.get(herb.cleanId).notedId)) {
                    herbcounter = player.getInventory().count(ItemDef.get(herb.cleanId).notedId);
                    coincounter = herbcounter * 200;
                }
            player.dialogue(new OptionsDialogue("Are you sure you want to spend " + coincounter + " Coins! to make " + herbcounter + " unf potions!",
                    new Option("yes", () -> MakeUnfPotions(player)),
                    new Option("no"))
            );
        });
        NPCAction.register(4753, "decant", Decanter::decantPotions);
        InterfaceHandler.register(Interface.POTION_DECANTING, h -> {
            h.actions[3] = (SimpleAction) p -> Potion.decant(p, 1);
            h.actions[4] = (SimpleAction) p -> Potion.decant(p, 2);
            h.actions[5] = (SimpleAction) p -> Potion.decant(p, 3);
            h.actions[6] = (SimpleAction) p -> Potion.decant(p, 4);
        });
    }
}
