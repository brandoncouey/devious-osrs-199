package io.ruin.model.skills.woodcutting;

import io.ruin.api.utils.NumberUtils;
import io.ruin.api.utils.Random;
import io.ruin.cache.ItemDef;
import io.ruin.model.World;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.contracts.woodcutting.WoodcuttingContract;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.diaries.minigames.MinigamesDiaryEntry;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.BirdNest;
import io.ruin.model.item.actions.impl.skillcapes.WoodcuttingSkillCape;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.Region;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.map.route.RouteFinder;
import io.ruin.model.skills.firemaking.Burning;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import io.ruin.process.event.EventConsumer;

import java.util.function.Supplier;

public class Woodcutting {

    private static void chop(Tree treeData, Player player, GameObject tree, int treeStump) {
        chop(treeData, player, () -> tree.id == treeStump, worldEvent -> {
            WoodcuttingContract.advanceWoodcuttingContract(player);
            tree.setId(treeStump);
            player.publicSound(2734, 1, 0);
            worldEvent.delay(treeData.respawnTime);
            tree.setId(tree.originalId);
        });
    }

    /**
     * This method was modified so farmed trees could share the same chopping code as normal trees in the world. Only differences should be in the parameters specified below
     *
     * @param treeDeadCheck  Checks if the tree was removed during the time in between the actions of the given player. Only relevant if the tree can lose logs through means other than the given player chopping it
     * @param treeDeadAction What to do when the given player chops the tree's last logs. Should include changing the object into a stump and handle the respawning.
     */
    public static void chop(Tree treeData, Player player, Supplier<Boolean> treeDeadCheck, EventConsumer treeDeadAction) {
        Region region;
        region = player.getPosition().getRegion();
        Hatchet hatchet = Hatchet.find(player);

        if (hatchet == null) {
            player.sendMessage("You need an axe to chop down this tree.");
            player.sendMessage("You do not have an axe which you have the woodcutting level to use.");
            player.privateSound(2277);
            return;
        }

        Stat stat = player.getStats().get(StatType.Woodcutting);
        if (stat.currentLevel < treeData.levelReq) {
            player.sendMessage("You need a Woodcutting level of " + treeData.levelReq + " to chop down this tree.");
            player.privateSound(2277);
            return;
        }

        if (player.getInventory().isFull()) {
            player.sendMessage("Your inventory is too full to hold any logs.");
            player.privateSound(2277);
            return;
        }

        player.startEvent(event -> {
            int attempts = 0;
            int id = treeData.log;
            while (true) {
                int effectiveLevel = getEffectiveLevel(player, treeData, hatchet);
                if (player.debug) {
                    double chance = chance(effectiveLevel, treeData, hatchet) / 100;
                    double logsPerTick = chance / 2;
                    double logsPerHour = 100 * 60 * logsPerTick;
                    double xpPerTick = logsPerTick * treeData.experience * StatType.Woodcutting.defaultXpMultiplier;
                    double xpPerHour = 100 * 60 * xpPerTick;
                    player.sendMessage("difficulty=" + treeData.difficulty + ", chance=" + NumberUtils.formatTwoPlaces(chance) + ", xp/tick=" + NumberUtils.formatNumber((long) xpPerTick) + "");
                    player.sendMessage("logsPerHour=" + NumberUtils.formatTwoPlaces(logsPerHour) + ", xpPerHour=" + NumberUtils.formatNumber((long) xpPerHour));
                }
                if (player.getInventory().isFull()) {
                    player.sendMessage("Your inventory is too full to hold any more logs.");
                    player.resetAnimation();
                    return;
                }
                if (treeDeadCheck.get()) {
                    player.resetAnimation();
                    return;
                }

                if (attempts == 0) {
                    player.animate(hatchet.animationId);
                    player.sendFilteredMessage("You swing your axe at the tree.");
                    event.delay(1);
                }
                if (attempts % 2 == 0 && successfullyCutTree(effectiveLevel, treeData, hatchet)) {
                    if (hatchet == Hatchet.INFERNAL && (Random.rollDie(3, 1) || player.infernalAxeSpecial > 0)) {
                        Burning burning = Burning.get(treeData.log);
                        if (burning != null) {
                            player.sendFilteredMessage("The infernal axe incinerates some logs.");
                            player.graphics(580, 50, 0);
                            player.getStats().addXp(StatType.Firemaking, burning.exp, true);
                            //TODO: take away an item charge?
                        }
                    } else {
                        player.sendFilteredMessage("You get some " + treeData.treeName + ".");
                        if (player.isADonator()) {
                            player.getInventory().add(ItemDef.get(id).notedId, 1);
                            if (treeData.log == Tree.TEAK.log && player.getPosition().regionId() == 13872) {
                                player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.CHOP_TEAK);
                            }
                            if (treeData.log == Tree.MAHOGANY.log && player.getPosition().regionId() == 10300) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_MAHOGANY);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 10805) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CUT_MAGIC_SEERS);
                            }
                            if (treeData.log == Tree.WILLOW.log && player.getPosition().regionId() == 12338) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_WILLOW_DRAY);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 13363) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_MAGIC_AL);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 12605) {
                                player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MAGIC_LOG_WILD);
                            }
                            if (treeData.log == Tree.YEW.log && player.getPosition().regionId() == 6198) {
                               // player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_YEW);
                            }
                            if (treeData.log == Tree.YEW.log && player.getPosition().regionId() == 6454) {
                               // player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_YEW);
                            }
                            if (player.isADonator() && Random.rollPercent(50)) {
                                player.getInventory().add(ItemDef.get(treeData.log).notedId, 1);
                                player.sendMessage("You receive some noted logs due to your donator rank!");
                            }
                            if (Random.rollDie(nestChance(player), 1)) {
                                new GroundItem(BirdNest.getRandomNest(treeData), 1)
                                        .owner(player).position(RouteFinder.findWalkable(player.getPosition()))
                                        .spawn();
                                player.sendFilteredMessage("A bird's nest falls out of the tree.");
                                PlayerCounter.ACQUIRED_BIRDS_NESTS.increment(player, 1);
                            }
                            if (player.dragonAxeSpecial > 0 && Random.rollPercent(50)) {
                                player.sendFilteredMessage("Your axe's buff allows you to chop some additional logs!");
                                player.getInventory().add(ItemDef.get(id).notedId, 1);
                                player.getStats().addXp(StatType.Woodcutting, treeData.experience / 2.0, true);
                            }
                        } else {
                            player.getInventory().add(treeData.log, 1);
                            if (treeData.log == Tree.TEAK.log && player.getPosition().regionId() == 13872) {
                                player.getDiaryManager().getMinigamesDiary().progress(MinigamesDiaryEntry.CHOP_TEAK);
                            }
                            if (treeData.log == Tree.MAHOGANY.log && player.getPosition().regionId() == 10300) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_MAHOGANY);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 10805) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CUT_MAGIC_SEERS);
                            }
                            if (treeData.log == Tree.WILLOW.log && player.getPosition().regionId() == 12338) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_WILLOW_DRAY);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 13363) {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_MAGIC_AL);
                            }
                            if (treeData.log == Tree.MAGIC.log && player.getPosition().regionId() == 12605) {
                                player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.MAGIC_LOG_WILD);
                            }
                            if (treeData.log == Tree.YEW.log && player.getPosition().regionId() == 6198) {
                                //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_YEW);
                            }
                            if (treeData.log == Tree.YEW.log && player.getPosition().regionId() == 6454) {
                               // player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CHOP_YEW);
                            }
                            if (player.isADonator() && Random.rollPercent(50)) {
                                player.getInventory().add(ItemDef.get(treeData.log).notedId, 1);
                                player.sendMessage("You receive some noted logs due to your donator rank!");
                            }
                            if (Random.rollDie(nestChance(player), 1)) {
                                new GroundItem(BirdNest.getRandomNest(treeData), 1)
                                        .owner(player).position(RouteFinder.findWalkable(player.getPosition()))
                                        .spawn();
                                player.sendFilteredMessage("A bird's nest falls out of the tree.");
                                PlayerCounter.ACQUIRED_BIRDS_NESTS.increment(player, 1);
                            }
                            if (player.dragonAxeSpecial > 0 && Random.rollPercent(50)) {
                                player.sendFilteredMessage("Your axe's buff allows you to chop some additional logs!");
                                player.getInventory().add(treeData.log, 1);
                                player.getStats().addXp(StatType.Woodcutting, treeData.experience / 2.0, true);
                            }
                        }
                    }
                    if(DailyTask.hasEasyTask(player, DailyTask.EasyTasks.LOGS)){
                        DailyTask.increase(player, DailyTask.EasyTasks.LOGS);
                    }
                    treeData.counter.increment(player, 1);

                    double xp = treeData.experience;
                    if (player.infernalAxeSpecial > 0)
                        xp *= 1.1;
                    player.getStats().addXp(StatType.Woodcutting, xp * LumberjackSet(player), true);
                    if (treeData.single || Random.get(10) == 3) {
                        player.resetAnimation();
                        World.startEvent(treeDeadAction);
                        return;
                    }
                }
                if (attempts++ % 4 == 0)
                    player.animate(hatchet.animationId);

                event.delay(1);
            }
        });
    }

    private static int nestChance(Player player) {
        int chance = 200;
        if (player.isSecondaryGroup(SecondaryGroup.DIAMOND)) {
            chance = 170;
        } else if (player.isSecondaryGroup(SecondaryGroup.RUBY)) {
            chance = 175;
        } else if (player.isSecondaryGroup(SecondaryGroup.EMERALD)) {
            chance = 180;
        } else if (player.isSecondaryGroup(SecondaryGroup.SAPPHIRE)) {
            chance = 185;
        } else if (player.isSecondaryGroup(SecondaryGroup.RED_TOPAZ)) {
            chance = 190;
        } else if (player.isSecondaryGroup(SecondaryGroup.JADE)) {
            chance = 195;
        } else if (player.isSecondaryGroup(SecondaryGroup.OPAL)) {
            chance = 197;
        }

        if (WoodcuttingSkillCape.wearsWoodcuttingCape(player)) {
            chance += chance / 10;
        }

        return chance;
    }


    public static double LumberjackSet(Player player) {
        double bonus = 1.0;
        Item hood = player.getEquipment().get(Equipment.SLOT_HAT);
        Item garb = player.getEquipment().get(Equipment.SLOT_CHEST);
        Item robe = player.getEquipment().get(Equipment.SLOT_LEGS);
        Item boots = player.getEquipment().get(Equipment.SLOT_FEET);

        if (hood != null && hood.getId() == 10941)
            bonus += 0.9;
        if (garb != null && garb.getId() == 10939)
            bonus += 0.9;
        if (robe != null && robe.getId() == 10940)
            bonus += 0.9;
        if (boots != null && boots.getId() == 10933)
            bonus += 0.9;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 3.0)
            bonus += 0.6;

        return bonus;
    }

    private static int getEffectiveLevel(Player player, Tree treeData, Hatchet hatchet) {
        int base = player.getStats().get(StatType.Woodcutting).currentLevel;
        if (WoodcuttingGuild.hasBoost(player))
            base += 7;
        return base;
    }

    private static boolean successfullyCutTree(int level, Tree type, Hatchet hatchet) {
        return Random.get(100) <= chance(level, type, hatchet);
    }

    private static double chance(int level, Tree type, Hatchet hatchet) {
        double points = ((level - type.levelReq) + 1 + (double) hatchet.points);
        double denominator = type.difficulty;
        return (Math.min(0.95, points / denominator) * 100);
    }

    static {
        ObjectAction.register(1278, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(1276, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(2091, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1342));
        ObjectAction.register(1286, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1351));
        ObjectAction.register(1282, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1347));
        ObjectAction.register(1383, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1358));
        ObjectAction.register(1289, "chop down", (player, obj) -> chop(Tree.REGULAR, player, obj, 1353));
        ObjectAction.register(2023, "chop", (player, obj) -> chop(Tree.ACHEY, player, obj, 1355));
        ObjectAction.register(10820, "chop down", (player, obj) -> chop(Tree.OAK, player, obj, 1356));
        ObjectAction.register(10819, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10833, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10831, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(10829, "chop down", (player, obj) -> chop(Tree.WILLOW, player, obj, 9711));
        ObjectAction.register(15062, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 9037));
        ObjectAction.register(9036, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 9037));
        ObjectAction.register(27499, "chop down", (player, obj) -> chop(Tree.JUNIPER, player, obj, 27500));
        ObjectAction.register(10832, "chop down", (player, obj) -> chop(Tree.MAPLE, player, obj, 9712));
        ObjectAction.register(10822, "chop down", (player, obj) -> chop(Tree.YEW, player, obj, 9714));
        ObjectAction.register(1754, "chop down", (player, obj) -> chop(Tree.YEW, player, obj, 9714));
        ObjectAction.register(10834, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(1761, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(1762, "chop down", (player, obj) -> chop(Tree.MAGIC, player, obj, 9713));
        ObjectAction.register(29668, "cut", (player, obj) -> chop(Tree.REDWOOD, player, obj, 29669));
        ObjectAction.register(29670, "cut", (player, obj) -> chop(Tree.REDWOOD, player, obj, 29671));
        ObjectAction.register(29763, "chop", (player, obj) -> chop(Tree.SAPLING, player, obj, 29764));
        ObjectAction.register(9034, "chop down", (player, obj) -> chop(Tree.MAHOGANY, player, obj, 9035));
        ObjectAction.register(36688, "chop down", (player, obj) -> chop(Tree.MAHOGANY, player, obj, 36689));
        ObjectAction.register(36686, "chop down", (player, obj) -> chop(Tree.TEAK, player, obj, 36687));
        //ObjectAction.register(50008, "chop down", (player, obj) -> chop(Tree.CORRUPT_TREE, player, obj, 50009));
    }

}
