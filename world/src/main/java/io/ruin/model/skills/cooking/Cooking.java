package io.ruin.model.skills.cooking;

import io.ruin.api.utils.Random;
import io.ruin.model.diaries.kandarin.KandarinDiaryEntry;
import io.ruin.model.diaries.kourend.KourendDiaryEntry;
import io.ruin.model.diaries.varrock.VarrockDiaryEntry;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.CapePerks;
import io.ruin.model.stat.StatType;

public class Cooking {

    public static final int COOKING_GAUNLETS = 775;

    private static void cook(Player player, Food food, GameObject obj, int anim, boolean fire) {
        if (!player.getStats().check(StatType.Cooking, food.levelRequirement, "cook " + food.descriptiveName))
            return;
        SkillItem i = new SkillItem(food.rawID).name(food.rawName).
                addAction((p, amount, event) -> startCooking(p, food, obj, amount, anim, fire));
        //System.out.println(food.name());
        if (food.equals(Food.RAW_MEAT) || food.equals(Food.SINEW)) {
            SkillItem sinew = new SkillItem(Food.SINEW.cookedID).name(Food.SINEW.itemName).
                    addAction((p, amount, event) -> startCooking(p, Food.SINEW, obj, amount, anim, fire));
            SkillDialogue.make(player, i, sinew);
        } else {
            if (player.getInventory().hasMultiple(food.rawID))
                SkillDialogue.cook(player, i);
            else
                startCooking(player, food, obj, 1, anim, fire);
        }
    }

    private static void startCooking(Player player, Food food, GameObject obj, int amountToCook, int anim, boolean fire) {
        player.startEvent(e -> {
            int amount = amountToCook;
            while (amount-- > 0) {
                Item rawFood = player.getInventory().findItem(food.rawID);
                if (rawFood == null) {
                    player.sendMessage("You don't have any more " + food.itemNamePlural + " to cook.");
                    break;
                }
                if (obj == null)
                    break;
                if (rawFood.getId() == 3150) {
                    player.startEvent(ev -> {
                        for (int i = 0; i < player.getInventory().count(3150); i++) {
                            if (player.getInventory().getFreeSlots() <= 0) {
                                player.sendMessage("You've ran out of room in your inventory.");
                                return;
                            }
                            player.animate(anim);
                            ev.delay(4);
                            if (cookedFood(player, food, fire)) {
                                player.getInventory().add(food.cookedID);
                                rawFood.remove(1);
                                player.getStats().addXp(StatType.Cooking, food.experience * bonus(player, fire), true);
                                player.sendFilteredMessage(cookingMessage(food));
                                PlayerCounter.COOKED_FOOD.increment(player, 1);
                                if (Random.rollDie(50, 1)) {
                                    player.getInventory().addOrDrop(6828, 1);
                                    player.sendMessage("You've discovered a Skilling box. It's been added to your inventory.");
                                }
                            } else {
                                player.getInventory().add(food.burntID);
                                rawFood.remove(1);
                                player.sendFilteredMessage("You accidentally burn the " + food.itemName + ".");
                                PlayerCounter.BURNT_FOOD.increment(player, 1);
                            }
                        }
                    });
                    return;
                }
                player.animate(anim);
                if (cookedFood(player, food, fire)) {
                    rawFood.setId(food.cookedID);
                    player.getStats().addXp(StatType.Cooking, food.experience * bonus(player, fire), true);
                    player.sendFilteredMessage(cookingMessage(food));
                    PlayerCounter.COOKED_FOOD.increment(player, 1);
                } else {
                    rawFood.setId(food.burntID);
                    player.sendFilteredMessage("You accidentally burn the " + food.itemName + ".");
                    PlayerCounter.BURNT_FOOD.increment(player, 1);
                }
                if (player.getPosition().regionId() == 11317 && player.getEquipment().contains(775) && food.cookedID == 385) {
                    player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.COOK_SHARKS);
                }
                if (player.getPosition().regionId() == 9273 && food.cookedID == 7946) {
                    player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.COOK_MONK);
                }
                if (fire)
                    PlayerCounter.COOKED_ON_FIRE.increment(player, 1);

                if (food.cookedID == 7218 && player.getPosition().inBounds(new Bounds(3138, 3444, 3148, 3453, 0))) {
                    player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.SUMMER_PIE);
                }
                if (food.cookedID == 379 && player.getPosition().regionId() == 6713) {
                    player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.COOK_LOBSTER);
                }
                e.delay(4);
            }
        });
    }

    private static double bonus(Player player, boolean fire) {
        double bonus = 1.0;
        return bonus;
    }


    private static String cookingMessage(Food food) {
        if (food == Food.RAW_LOBSTER)
            return "You roast a lobster.";
        else if (food == Food.PIE_MEAT)
            return "You successfully bake a tasty meat pie.";
        else if (food == Food.REDBERRY_PIE)
            return "You successfully bake a delicious redberry pie.";
        else if (food == Food.SEAWEED)
            return "You burn the seaweed to soda ash.";
        else if (food == Food.RAW_HARPOONFISH)
            return "You successfully cook a harpoonfish! hardening it!";
        else
            return "You successfully cook " + food.descriptiveName + ".";
    }

    private static boolean cookedFood(Player player, Food food, Boolean fire) {
        if (food.burntID == -1)
            return true;
        if (CapePerks.wearsCookingCape(player))
            return true;
        double burnBonus = 0.0;
        int levelReq = food.levelRequirement;
        int burnStop = getBurnStop(player, food, fire);
        if (!fire)
            burnBonus = 3.0;
        double burnChance = (55.0 - burnBonus);
        double cookingLevel = player.getStats().get(StatType.Cooking).currentLevel;
        double randNum = Random.get() * 100.0;

        burnChance -= ((cookingLevel - levelReq) * (burnChance / (burnStop - levelReq)));
        return burnChance <= randNum;
    }

    private static int getBurnStop(Player player, Food food, Boolean cookingOnRange) {
        Item gloves = player.getEquipment().get(Equipment.SLOT_HANDS);
        if (gloves != null && gloves.getId() == COOKING_GAUNLETS)
            return food.burnLevelCookingGauntlets;
        return cookingOnRange ? food.burnLevelRange : food.burnLevelFire;
    }

    static {

        ObjectAction.register("Range", "cook", (player, obj) -> {
            for (Food food : Food.values()) {
                if (player.getInventory().contains(food.rawID)) {
                    cook(player, food, obj, 896, false);
                }
            }
        });
        for (Food food : Food.values()) {
            ItemObjectAction.register(food.rawID, "range", (player, item, obj) -> Cooking.cook(player, food, obj, 896, false));
            ItemObjectAction.register(food.rawID, "cooking range", (player, item, obj) -> Cooking.cook(player, food, obj, 896, false));
            ItemObjectAction.register(food.rawID, "fire", (player, item, obj) -> Cooking.cook(player, food, obj, 897, true));
            ItemObjectAction.register(food.rawID, "stove", (player, item, obj) -> Cooking.cook(player, food, obj, 896, false));
            ItemObjectAction.register(food.rawID, "sulphur vent", (player, item, obj) -> Cooking.cook(player, food, obj, 896, false));
            ItemObjectAction.register(food.rawID, "cooking pot", (player, item, obj) -> Cooking.cook(player, food, obj, 897, true));
            ItemObjectAction.register(food.rawID, 5249, (player, item, obj) -> Cooking.cook(player, food, obj, 897, true));
        }
    }
}
