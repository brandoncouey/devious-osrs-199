package io.ruin.model.skills.slayer;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.cache.NPCDef;
import io.ruin.cache.NpcID;
import io.ruin.model.World;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.item.actions.impl.jewellery.RingofEndlessRecoil;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.stat.StatType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Slayer {

    static {
        ItemDef.cached.values().stream().filter(def -> def.name.toLowerCase().contains("slayer helm")).forEach(def -> def.slayerHelm = true);
    }

    public static void reset(Player player) {
        player.slayerTask = null;
        player.slayerTaskType = null;
        player.slayerTaskName = null;
        player.slayerTaskRemaining = 0;
        player.slayerTaskDangerous = false;
        player.slayerTaskAmountAssigned = 0;
        player.taskLocation = 0;
    }

    public static void test(Player player) {
        List<SlayerTask> tasks = getPossibleTasks(player, SlayerTask.Type.HARD);
        tasks.forEach(it -> {
            player.sendMessage(it.name + " " + it.level);
        });
    }

    private static List<SlayerTask> getPossibleTasks(Player player, SlayerTask.Type type) {
        return Arrays.stream(type.tasks)
                .filter(task -> player.getStats().get(StatType.Slayer).fixedLevel >= task.level) // has level
                .filter(task -> !task.disable) // task not disabled
                .filter(task -> task.additionalRequirement == null || task.additionalRequirement.test(player)) // has additional req
                .filter(task -> task.unlockConfig == null || task.unlockConfig.get(player) == 1) // unlocked (if required)
                .filter(task -> !isBlockedTask(player, task))
                .collect(Collectors.toList());
    }

    public static void set(Player player, SlayerTask.Type type) {
        List<SlayerTask> tasks = getPossibleTasks(player, type);
        int totalWeight = tasks.stream().mapToInt(task -> task.weight).sum();
        SlayerTask task = null;
        int roll = Random.get(totalWeight);
        for (SlayerTask t : tasks) {
            roll -= t.weight;
            if (roll <= 0) {
                task = t;
                break;
            }
        }
        if (task == null) { // should never happen, but just in case..
            task = Random.get(tasks);
        }
        task.currentType = type;
        player.slayerTask = task;
        player.slayerTaskName = task.name;
        player.slayerTaskType = type;
        int randomAmount = Random.get(1, 35);
        int taskAmount = task.type[0] == SlayerTask.Type.BOSS ? randomAmount : Random.get(task.min, task.max);
        player.slayerTaskRemaining = taskAmount;
        player.slayerTaskAmountAssigned = taskAmount;
        if (task.extensionConfig != null && task.extensionConfig.get(player) == 1)
            player.slayerTaskRemaining *= 1.35;
        if (player.currentSlayerMaster == 7663)
            player.slayerTaskDangerous = true;
        if (player.currentSlayerMaster == NpcID.DURADEL)
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.DURADEL);
    }

    public static SlayerTask getTask(Player player) {
        if (player.slayerTask != null)
            return player.slayerTask;
        if (player.slayerTaskName == null)
            return null;
        SlayerTask task = SlayerTask.TASKS.get(player.slayerTaskName);
        if (task == null) {
            reset(player);
            return null;
        }
        player.slayerTask = task;
        task.currentType = player.slayerTaskType;
        return player.slayerTask;
    }

    public static boolean hasSlayerHelmEquipped(Player player) {
        ItemDef def = player.getEquipment().getDef(Equipment.SLOT_HAT);
        return def != null && def.slayerHelm;
    }

    private static double combatLevelDropRate(NPC npc) {
        NPCDef def = npc.getDef();
        if (def.combatLevel >= 500) {
            return 0.80;
        } else if (def.combatLevel >= 300) {
            return 0.85;
        } else if (def.combatLevel >= 200) {
            return 0.90;
        } else if (def.combatLevel >= 100) {
            return 0.95;
        }
        return 1;
    }

    public static void onNPCKill(Player player, NPC npc) {
        if (isTask(player, npc)) {
            if (player.slayerTaskType == SlayerTask.Type.WILDERNESS && player.wildernessLevel <= 0) {
                player.sendMessage("Your task is in the wilderness!");
                return;
            }

            if (player.currentSlayerMaster == 8623 && !KonarTaskLocations.TaskLocation.values()[player.taskLocation].inside(npc.getPosition().copy()) && player.slayerTaskType != SlayerTask.Type.WILDERNESS) {
                String location = KonarTaskLocations.TaskLocation.values()[player.taskLocation].getName();
                player.sendMessage("You must kill your slayer assignment at the " + location + " to receive experience!");
                return;
            }

            player.slayerTaskRemaining--;
            double xp = Wilderness.getLevel(npc.getPosition()) > 0 ? (npc.getCombat().getInfo().slayer_xp * 2.5d) : npc.getCombat().getInfo().slayer_xp;
            player.getStats().addXp(StatType.Slayer, xp, true);
            slayerDrops(player, npc);

            if (player.slayerTaskRemaining <= 0) {
                rollSlayerBox(player);
                int reward = 1;
                SlayerTask task = getTask(player);
                PlayerCounter.SLAYER_TASKS_COMPLETED.increment(player, 1);

                if (task != null) {
                    reward = getPointsReward(task, player.slayerTasksCompleted);
                }
                if (World.doubleSlayer)
                    reward *= 2;

                player.slayerExtendedCounter = 0;
                player.sendMessage("Your slayer task is now complete.");
                Config.SLAYER_POINTS.set(player, reward + Config.SLAYER_POINTS.get(player));
                player.sendMessage("You've completed a total of " + Color.RED.wrap(String.valueOf(PlayerCounter.SLAYER_TASKS_COMPLETED.get(player))) + " tasks, earning " + Color.RED.wrap(String.valueOf(reward)) + " points" +
                        ". You now have a total of " + Color.RED.wrap(String.valueOf(Config.SLAYER_POINTS.get(player))) + " points.");
                reset(player);
            }
        }
        if (Config.BIGGER_AND_BADDER.get(player) == 1) {
            if (isTask(player, npc)) {
                if (npc.getId() == NpcID.CRAWLING_HAND_448) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.CRUSHING_HAND).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.CAVE_CRAWLER_407) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.CHASM_CRAWLER).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.TWISTED_BANSHEE) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.SCREAMING_TWISTED_BANSHEE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.ROCKSLUG_422) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.GIANT_ROCKSLUG).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.COCKATRICE_419) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.COCKATHRICE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.PYREFIEND_434) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.FLAMING_PYRELORD).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.BASILISK_417) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.MONSTROUS_BASILISK_9287).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.INFERNAL_MAGE_444) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.COCKATHRICE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.BLOODVELD_485) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.INSATIABLE_BLOODVELD).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.MUTATED_BLOODVELD || npc.getId() == NpcID.MUTATED_BLOODVELD_9610 || npc.getId() == NpcID.MUTATED_BLOODVELD_9611) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.INSATIABLE_MUTATED_BLOODVELD).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.JELLY_438) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.VITREOUS_JELLY).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.WARPED_JELLY) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.VITREOUS_WARPED_JELLY).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.CAVE_HORROR_1048) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.CAVE_ABOMINATION).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.ABERRANT_SPECTRE_3) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.ABHORRENT_SPECTRE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.DEVIANT_SPECTRE) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.REPUGNANT_SPECTRE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.BASILISK_KNIGHT) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.BASILISK_SENTINEL).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.TUROTH_427) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.SPIKED_TUROTH).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.DUST_DEVIL_7249) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.CHOKE_DEVIL).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.WYRM) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.SHADOW_WYRM_10399).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.KURASK_410) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.KING_KURASK).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.NECHRYAEL_11) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NECHRYARCH).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.NECHRYAEL) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NECHRYARCH).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.GREATER_NECHRYAEL) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NECHRYARCH).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.DRAKE_8612) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.GUARDIAN_DRAKE).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.ABYSSAL_DEMON_7241 || npc.getId() == NpcID.ABYSSAL_DEMON || npc.getId() == NpcID.ABYSSAL_DEMON_415 || npc.getId() == NpcID.ABYSSAL_DEMON_416) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.GREATER_ABYSSAL_DEMON).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.DARK_BEAST) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NIGHT_BEAST).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.DARK_BEAST_7250) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NIGHT_BEAST).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.SMOKE_DEVIL_6639) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.NUCLEAR_SMOKE_DEVIL).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                } else if (npc.getId() == NpcID.HYDRA) {
                    if (Math.random() * 100 < 5) {
                        new NPC(NpcID.COLOSSAL_HYDRA).spawn(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 2).getCombat().setAllowRespawn(false);
                    }
                }
            }
        }
        for (int superiorNPC : SuperiorNPCS) {
            if (npc.getId() == superiorNPC) {
                if (RingofEndlessRecoil.wearingEndlessI(player)) {
                    player.getInventory().addOrDrop(6831, Random.get(5, 10));
                } else {
                    new GroundItem(new Item(6831, Random.get(5, 10))).position(npc.getPosition()).owner(player).spawn();
                }
            }
        }
    }

    public static int[] SuperiorNPCS = {10402, 7406, 7409, 7410, 10400, 7411, 10399, 7405, 7404, 10397, 9258, 7403, 7402, 7401, 7400, 7399, 7398, 7397, 7393, 9287, 7394, 7392, 7391, 7389, 7388};

    public static void rollSlayerBox(Player player) {
        if (player.slayerTaskType == SlayerTask.Type.EASY) {
            if (Random.rollDie(9, 1)) {
                player.getInventory().addOrDrop(6831, 1);
            }
        }
        if (player.slayerTaskType == SlayerTask.Type.MEDIUM) {
            if (Random.rollDie(7, 1)) {
                player.getInventory().addOrDrop(6831, 3);
            }
        }
        if (player.slayerTaskType == SlayerTask.Type.HARD) {
            if (Random.rollDie(5, 1)) {
                player.getInventory().addOrDrop(6831, 5);
            }
        }
        if (player.slayerTaskType == SlayerTask.Type.BOSS) {
            if (Random.rollDie(3, 1)) {
                player.getInventory().addOrDrop(6831, 7);
            }
        }
    }

    public static void slayerDrops(Player player, NPC npc) {
        NPCDef def = npc.getDef();
        if (isTask(player, npc)) {
            if (player.slayerTaskType == SlayerTask.Type.KONAR && KonarTaskLocations.TaskLocation.values()[player.taskLocation].inside(npc.getPosition().copy())) {
                player.getInventory().add(995, Random.get(15000, 20000));
                if (def.combatLevel >= 150) {
                    if (Random.rollDie(45, 1)) {
                        new GroundItem(23083, 1).owner(player).position(player.getPosition()).spawn();
                        player.sendMessage(Color.RED.wrap("Drop: Brimstone Key"));
                    }
                }
            }
            if (player.slayerTaskType == SlayerTask.Type.EASY) {
                player.getInventory().add(995, Random.get(2500, 7500));
                if (Random.rollDie(5, 1)) {
                    if (RingOfWealth.wearingRingOfWealth(player)) {
                        player.getInventory().add(30258, 1);
                    } else {
                        new GroundItem(30258, 1).owner(player).position(player.getPosition()).spawn(); // small tooth
                    }
                }
            }
            if (player.slayerTaskType == SlayerTask.Type.MEDIUM) {
                player.getInventory().add(995, Random.get(10000, 15000));
                if (Random.rollDie(5, 1)) {
                    if (RingOfWealth.wearingRingOfWealth(player)) {
                        player.getInventory().add(30259, 1);
                    } else {
                        new GroundItem(30259, 1).owner(player).position(player.getPosition()).spawn(); // medium tooth
                    }
                }
            }
            if (player.slayerTaskType == SlayerTask.Type.HARD) {
                player.getInventory().add(995, Random.get(15000, 20000));
                if (Random.rollDie(10, 1)) {
                    if (RingOfWealth.wearingRingOfWealth(player)) {
                        player.getInventory().add(30260, 1);
                    } else {
                        new GroundItem(30260, 1).owner(player).position(player.getPosition()).spawn(); // large tooth
                    }
                }
            }
            if (Wilderness.getLevel(npc.getPosition()) > 0) {
                player.getInventory().add(13307, Random.get(100, 500));
                if (!player.getPosition().inBounds(Wilderness.SLAYER_CAVE)) {
                    if (Random.get(1, 40) == 1) {
                        new GroundItem(23490, 1).owner(player).position(npc.getPosition()).spawn();
                    } else {
                        if (Random.get(1, 20) == 1) {
                            new GroundItem(23490, 1).owner(player).position(npc.getPosition()).spawn();
                        }
                    }
                }
            }
        }
    }

    public static boolean isTask(Player player, NPC npc) {
        SlayerTask playerTask = getTask(player);
        if (playerTask == null || npc.getCombat().getInfo().slayerTasks == null) {
            return false;
        }
        for (SlayerTask task : npc.getCombat().getInfo().slayerTasks) {
            if (task == playerTask)
                return true;
        }
        return false;
    }

    private static void finishTask(Player player) {
        int reward = 1;
        SlayerTask task = getTask(player);
        PlayerCounter.SLAYER_TASKS_COMPLETED.increment(player, 1);

        if (task != null) {
            reward = getPointsReward(task, player.slayerTasksCompleted);
        }
        if (World.doubleSlayer)
            reward *= 2;

        player.slayerExtendedCounter = 0;
        player.sendMessage("Your slayer task is now complete.");
        Config.SLAYER_POINTS.set(player, reward + Config.SLAYER_POINTS.get(player));
        player.sendMessage("You've completed a total of " + Color.RED.wrap(String.valueOf(PlayerCounter.SLAYER_TASKS_COMPLETED.get(player))) + " tasks, earning " + Color.RED.wrap(String.valueOf(reward)) + " points" +
                ". You now have a total of " + Color.RED.wrap(String.valueOf(Config.SLAYER_POINTS.get(player))) + " points.");
        reset(player);
    }

    public static int getPointsReward(SlayerTask task, int tasks) {
        int base = 45;
        if (tasks % 1000 == 0)
            return base * 50;
        else if (tasks % 250 == 0)
            return base * 35;
        else if (tasks % 100 == 0)
            return base * 25;
        else if (tasks % 50 == 0)
            return base * 15;
        else if (tasks % 10 == 0)
            return base * 5;
        return base;
    }


    static void sendTaskInfo(Player player) {
        SlayerTask task = getTask(player);
        if (task != null) {
            if (task.type[0] == SlayerTask.Type.BOSS) {
                Config.SLAYER_TASK_1.set(player, 98);
                Config.SLAYER_TASK_2.set(player, task.key);
            } else {
                Config.SLAYER_TASK_1.set(player, task.key);
            }
        }
        Config.SLAYER_TASK_AMOUNT.set(player, player.slayerTaskRemaining);
    }

    public static void sendRewardInfo(Player player) {
        Config.UNLOCK_BLOCK_TASK_SIX.set(player, 1);
        if (player.slayerBlockedTasks != null) {
            for (int i = 0; i < player.slayerBlockedTasks.size(); i++) {
                SlayerTask blocked = SlayerTask.TASKS.get(player.slayerBlockedTasks.get(i));
                Config.BLOCKED_TASKS[i].set(player, blocked != null ? blocked.key : 0);
            }
        }
    }

    public static boolean isBlockedTask(Player player, SlayerTask task) {
        if (player.slayerBlockedTasks != null) {
            for (int i = 0; i < player.slayerBlockedTasks.size(); i++) {
                SlayerTask blocked = SlayerTask.TASKS.get(player.slayerBlockedTasks.get(i));
                if (blocked == task) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void check(Player player) {
        String location = KonarTaskLocations.TaskLocation.values()[player.taskLocation].getName();
        SlayerTask task = Slayer.getTask(player);
        if (task == null) {
            player.sendMessage("You need something new to hunt.");
        } else if (player.currentSlayerMaster == 8623 && player.slayerTaskType != SlayerTask.Type.WILDERNESS) {
            player.sendMessage("Your currently bringing balance to " + task.name + ". Only " + player.slayerTaskRemaining + " left to kill in " + location);
        } else {
            player.sendMessage("You're assigned to kill " + task.name + ". Only " + player.slayerTaskRemaining + " left to go.");
        }
    }
}
