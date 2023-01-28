package io.ruin.model.PrestigeSystem;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;

import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.stat.StatType;


public class PrestigeScroll {
static {
    ItemAction.registerInventory(30353, 1, (player, item) -> {
        DailyTask.open(player);
    });
    ItemAction.registerInventory(30353, 2, (player, item) -> {
        DailyTask.assignTask(player);
    });
    ItemAction.registerInventory(30423, "Open", (player, item) -> {
        openPrestige(player);
    });
}


    public static void openPrestige(Player player) {
        OptionScroll.open(player, "<col=8B0000>Select a stat to prestige",
                new Option("Attack, Current prestige: " + player.attackPrestige, () -> prestigeAttack(player)),
                new Option("Strength, Current prestige: " + player.strengthPrestige, () -> prestigeStrength(player)),
                new Option("Defence, Current prestige: " + player.defencePrestige, () -> prestigeDefence(player)),
                new Option("Hitpoints, Current prestige: " + player.hitpointsPrestige, () -> prestigeHitpoints(player)),
                new Option("Range, Current prestige: " + player.rangePrestige, () -> prestigeRange(player)),
                new Option("Magic, Current prestige: " + player.magicPrestige, () -> prestigeMage(player)),
                new Option("Prayer, Current prestige: " + player.prayerPrestige, () -> prestigePrayer(player)),
                new Option("Runecrafting, Current prestige: " + player.rcprestige, () -> prestigeRC(player)),
                new Option("Construction, Current prestige: " + player.constructionPrestige, () -> prestigeConstruction(player)),
                new Option("Agility, Current prestige: " + player.agilityPrestige, () -> prestigeAgility(player)),
                new Option("Herblore, Current prestige: " + player.herblorePrestige, () -> prestigeHerblore(player)),
                new Option("Thieving, Current prestige: " + player.thievingPrestige, () -> prestigeThieving(player)),
                new Option("Crafting, Current prestige: " + player.craftingPrestige, () -> prestigeCrafting(player)),
                new Option("Fletching, Current prestige: " + player.fletchingPrestige, () -> prestigeFletching(player)),
                new Option("Slayer, Current prestige: " + player.slayerPrestige, () -> prestigeSlayer(player)),
                new Option("Hunter, Current prestige: " + player.hunterPrestige, () -> prestigeHunter(player)),
                new Option("Mining, Current prestige: " + player.minePrestige, () -> prestigeMining(player)),
                new Option("Smithing, Current prestige: " + player.smithPrestige, () -> prestigeSmithing(player)),
                new Option("Fishing, Current prestige: " + player.fishingPrestige, () -> prestigeFishing(player)),
                new Option("Cooking, Current prestige: " + player.cookingPrestige, () -> prestigeCooking(player)),
                new Option("Firemaking, Current prestige: " + player.fmPrestige, () -> prestigeFM(player)),
                new Option("Woodcutting, Current prestige: " + player.wcPrestige, () -> prestigeWC(player)),
                new Option("Farming, Current prestige: " + player.farmPrestige, () -> prestigeFarming(player)));
    }

    public static void addPrestigePoints(Player player) {
                int points = 1;

                if (player.xpMode == XpMode.REALISTIC) {
                    points += 20;
                }
        if (player.xpMode == XpMode.MEDIUM) {
            points += 20;
        }
        if (player.xpMode == XpMode.HARD) {
            points += 20;
        }
        if (player.isADonator()) {
            points += 5;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.DIAMOND) {
            points += 50;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.RUBY) {
            points += 40;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.EMERALD) {
            points += 30;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.SAPPHIRE) {
            points += 20;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.RED_TOPAZ) {
            points += 15;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.JADE) {
            points += 10;
        }
        if(player.getSecondaryGroup() == SecondaryGroup.OPAL) {
            points += 5;
        }

        player.prestigePoints += points;
        player.sendMessage("You now have " + player.prestigePoints + " prestige points.");
    }

    public static void prestigeAttack(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
    if (player.attackPrestige >= 5) {
        player.sendMessage("You are a max prestige for this stat of 5!");
        return;
    }
        if (player.getStats().get(StatType.Attack).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Attack);
        player.sendMessage("You have prestiged your attack level!");
      player.attackPrestige += 1;
      addPrestigePoints(player);
    }

    public static void prestigeHitpoints(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.hitpointsPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Hitpoints).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Hitpoints);
        player.sendMessage("You have prestiged your hitpoints level!");
        player.hitpointsPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeStrength(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.strengthPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Strength).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Strength);
        player.sendMessage("You have prestiged your strength level!");
        player.strengthPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeDefence(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.defencePrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Defence).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Defence);
        player.sendMessage("You have prestiged your defence level!");
        player.defencePrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeRange(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.rangePrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Ranged).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Ranged);
        player.sendMessage("You have prestiged your range level!");
        player.rangePrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeMage(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.magicPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }

        if (player.getStats().get(StatType.Magic).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Magic);
        player.sendMessage("You have prestiged your magic level!");
        player.magicPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigePrayer(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.prayerPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Prayer).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Prayer);
        player.sendMessage("You have prestiged your prayer level!");
        player.prayerPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeRC(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.rcprestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Runecrafting).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Runecrafting);
        player.sendMessage("You have prestiged your runecrafting level!");
        player.rcprestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeConstruction(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.constructionPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Construction).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Construction);
        player.sendMessage("You have prestiged your construction level!");
        player.constructionPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeAgility(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.agilityPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Agility).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Agility);
        player.sendMessage("You have prestiged your agility level!");
        player.agilityPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeHerblore(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.herblorePrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Herblore).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Herblore);
        player.sendMessage("You have prestiged your herblore level!");
        player.herblorePrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeThieving(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.thievingPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Thieving).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Thieving);
        player.sendMessage("You have prestiged your thieving level!");
        player.thievingPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeCrafting(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.craftingPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Crafting).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Crafting);
        player.sendMessage("You have prestiged your crafting level!");
        player.craftingPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeFletching(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.fletchingPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Fletching).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Fletching);
        player.sendMessage("You have prestiged your fletching level!");
        player.fletchingPrestige += 1;
        addPrestigePoints(player);
    }

    public static void prestigeSlayer(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.slayerPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Slayer).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Slayer);
        player.sendMessage("You have prestiged your slayer level!");
        player.slayerPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeHunter(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.hunterPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Hunter).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Hunter);
        player.sendMessage("You have prestiged your hunter level!");
        player.hunterPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeMining(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.minePrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Mining).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        resetLevel(player, StatType.Mining);
        player.sendMessage("You have prestiged your mining level!");
        player.minePrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeSmithing(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.smithPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Smithing).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Smithing);
        player.sendMessage("You have prestiged your smithing level!");
        player.smithPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeFishing(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.fishingPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Fishing).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Fishing);
        player.sendMessage("You have prestiged your fishing level!");
        player.fishingPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeCooking(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.fletchingPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Cooking).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Cooking);
        player.sendMessage("You have prestiged your cooking level!");
        player.cookingPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeFM(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.fmPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Firemaking).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Firemaking);
        player.sendMessage("You have prestiged your firemaking level!");
        player.fmPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeWC(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.wcPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Woodcutting).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Woodcutting);
        player.sendMessage("You have prestiged your woodcutting level!");
        player.wcPrestige += 1;
        addPrestigePoints(player);
    }
    public static void prestigeFarming(Player player) {
        if (!player.getEquipment().isEmpty()) {
            player.sendMessage("You must remove all equipment before resetting levels.");
            return;
        }
        if (player.farmPrestige >= 5) {
            player.sendMessage("You are a max prestige for this stat of 5!");
            return;
        }
        if (player.getStats().get(StatType.Farming).fixedLevel < 99) {
            player.sendMessage("You can only prestige once reaching level 99");
            return;
        }
        if (player.getInventory().count(995) < 10_000_000) {
            player.sendMessage("You require 10M GP To prestige your stat!");
            return;
        }
        player.getInventory().remove(995, 10_000_000);
        resetLevel(player, StatType.Farming);
        player.sendMessage("You have prestiged your farming level!");
        player.farmPrestige += 1;
        addPrestigePoints(player);
    }


    private static void resetLevel(Player player, StatType stat) {
                    if (!player.getEquipment().isEmpty()) {
                        player.sendMessage("You must remove all equipment before resetting levels.");
                        return;
                    }
                    player.getPrayer().deactivateAll();
                    if (stat.equals(StatType.Hitpoints)) {
                        player.getStats().set(stat, 10);
                    } else {
                        player.getStats().get(stat).resetTo1();
                    }
                    player.getCombat().updateLevel();
                    player.sendMessage("Your " + stat.name() + " level has been reset.");
    }


}
