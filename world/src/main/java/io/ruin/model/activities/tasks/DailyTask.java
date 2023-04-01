package io.ruin.model.activities.tasks;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.Misc;

import java.util.*;

public class DailyTask {

    public enum HardTasks {

        BARROWS_CHESTS(DailyTaskDifficulty.HARD, 20, "Loot 20 barrows chests"),
        SKOTIZO(DailyTaskDifficulty.HARD, 5, "Kill skotizo 5 times"),
        KING_BLACK_DRAGON(DailyTaskDifficulty.HARD, 10, "Kill king black dragon 10 times"),
        ABYSSAL_DEMONS(DailyTaskDifficulty.HARD, 200, "Kill 200 abyssal demons"),
        DARK_BESTS(DailyTaskDifficulty.HARD, 50, "Kill 50 dark beasts"),
        GENERAL_GRAARDOR(DailyTaskDifficulty.HARD, 10, "Kill general graardor 10 times"),
        KREE_ARRA(DailyTaskDifficulty.HARD, 10, "Kill kree'Arra 10 times"),
        TSUTSAROTH(DailyTaskDifficulty.HARD, 10, "Kill tsutsaroth 10 times"),
        ZILYANA(DailyTaskDifficulty.HARD, 10, "Kill zilyana 10 times"),
        VETION(DailyTaskDifficulty.HARD, 10, "Kill vet'ion 10 times"),
        ZULRAH(DailyTaskDifficulty.HARD, 10, "Kill zulrah 10 times"),
        DEMONIC_GORILLAS(DailyTaskDifficulty.HARD, 50, "Kill 50 demonic gorillas"),

        SEERS_COURSE(DailyTaskDifficulty.HARD, 30, "Run 30 laps on the seers rooftop course"),
        PRAYER_POTIONS(DailyTaskDifficulty.HARD, 600, "Make 600 potions"),
        MASTER_FARMER(DailyTaskDifficulty.HARD, 250, "Pickpocket any npc 250 times"),
        ;

        public DailyTaskDifficulty type;
        public int amount;
        String message;

        HardTasks(DailyTaskDifficulty type, int amount, String message) {
            this.type = type;
            this.amount = amount;
            this.message = message;
        }
    }

    public enum MediumTasks {

        LAVA_DRAGONS(DailyTaskDifficulty.MEDIUM, 30, "Kill 30 lava dragons"),
        GIANT_MOLE(DailyTaskDifficulty.MEDIUM, 30, "Kill 30 giant moles"),
        BLACK_DRAGONS(DailyTaskDifficulty.MEDIUM, 50, "Kill 50 black dragons"),
        BLUE_DRAGONS(DailyTaskDifficulty.MEDIUM, 50, "Kill 50 blue dragons"),
        RUNE_DRAGON(DailyTaskDifficulty.MEDIUM, 20, "Kill 20 rune dragons"),
        MITHRIL_DRAGONS(DailyTaskDifficulty.MEDIUM, 30, "Kill 30 mithril dragons"),
        CAVE_HORROR(DailyTaskDifficulty.MEDIUM, 50, "Kill 50 cave horrors"),

        VARROCK_COURSE(DailyTaskDifficulty.MEDIUM, 10, "Run 10 laps on the varrock rooftop course"),
        SAPPHIRES(DailyTaskDifficulty.MEDIUM, 100, "Cut 100 gems"),
        COAL(DailyTaskDifficulty.MEDIUM, 200, "Mine 200 ores"),
        ;

        public DailyTaskDifficulty type;
        public int amount;
        String message;

        MediumTasks(DailyTaskDifficulty type, int amount, String message) {
            this.type = type;
            this.amount = amount;
            this.message = message;
        }
    }

    public enum EasyTasks {
        ROCK_CRABS(DailyTaskDifficulty.EASY, 50, "Kill 50 rock crabs"),
        DAGANNOTHS(DailyTaskDifficulty.EASY, 30, "Kill 30 dagannoths"),
        FIRE_GIANTS(DailyTaskDifficulty.EASY, 30, "Kill 30 fire giants"),
        HILL_GIANTS(DailyTaskDifficulty.EASY, 30, "Kill 30 hill giants"),
        HOBGOBLINS(DailyTaskDifficulty.EASY, 50, "Kill 50 hobgoblins"),
        BABY_BLUE_DRAGONS(DailyTaskDifficulty.EASY, 20, "Kill 20 baby blue dragons"),
        MOSS_GIANTS(DailyTaskDifficulty.EASY, 30, "Kill 30 moss giants"),
        LESSER_DEMONS(DailyTaskDifficulty.EASY, 30, "Kill 30 lesser demons"),

        EASY_CLUE(DailyTaskDifficulty.EASY, 1, "Complete 1 easy clue scroll"),
        CRAFT_STALL(DailyTaskDifficulty.EASY, 50, "Steal from the crafting stall 50 times"),
        GNOME_COURSE(DailyTaskDifficulty.EASY, 10, "Run 10 laps at the gnome agility course"),
        LOGS(DailyTaskDifficulty.EASY, 100, "Cut 100 logs"),
        LIGHT_LOGS(DailyTaskDifficulty.EASY, 100, "Light 100 logs"),
        SHORTBOWS(DailyTaskDifficulty.EASY, 100, "String 100 bows"),
        CRYSTAL_CHEST(DailyTaskDifficulty.EASY, 1, "Open 1 crystal key chest"),
        GUAMS(DailyTaskDifficulty.EASY, 25, "Harvest 25 herbs"),
        FLAX(DailyTaskDifficulty.EASY, 175, "Pick 175 flax"),
        ;

        public DailyTaskDifficulty type;
        public int amount;
        String message;

        EasyTasks(DailyTaskDifficulty type, int amount, String message) {

            this.type = type;
            this.amount = amount;
            this.message = message;

        }
    }

    public static EasyTasks getEasyTask(int taskId) {
        return EasyTasks.values()[taskId];
    }

    public static MediumTasks getMediumTask(int taskId) {
        return MediumTasks.values()[taskId];
    }

    public static HardTasks getHardTask(int taskId) {
        return HardTasks.values()[taskId];
    }

    /**
     * Gets today's date
     *
     * @return
     */

    private static int getTodayDate() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        return (month * 100 + day);
    }


    /**
     * Assigns a task to a player when it has daily's enabled and has no current task.
     *
     * @param player
     */

    public static void assignTask(Player player) {
        if (!player.easyTasks.isEmpty() || !player.mediumTasks.isEmpty() || !player.hardTasks.isEmpty()) {
            player.sendMessage("<col=0000FF>You already have tasks. You can check your current tasks from your Task Book");
            return;
        }
        while (player.easyTasks.size() < 3 && player.mediumTasks.size() < 3 && player.hardTasks.size() < 3) {
            if (player.easyTasks.size() < 3)
                player.easyTasks.put(getRandomTask(DailyTaskDifficulty.EASY), 0);
            if (player.mediumTasks.size() < 3)
                player.mediumTasks.put(getRandomTaskMedium(DailyTaskDifficulty.MEDIUM), 0);
            if (player.hardTasks.size() < 3)
                player.hardTasks.put(getRandomTaskHard(DailyTaskDifficulty.HARD), 0);
        }
        openDailyTasksViewer(player);
        player.sendMessage("<col=03b329>Your dailies have been reset.");
    }

    public static void assignTaskNoWarning(Player player) {
        if (!player.easyTasks.isEmpty() || !player.mediumTasks.isEmpty() || !player.hardTasks.isEmpty()) {
            return;
        }
        while (player.easyTasks.size() < 3 && player.mediumTasks.size() < 3 && player.hardTasks.size() < 3) {
            if (player.easyTasks.size() < 3)
                player.easyTasks.put(getRandomTask(DailyTaskDifficulty.EASY), 0);
            if (player.mediumTasks.size() < 3)
                player.mediumTasks.put(getRandomTaskMedium(DailyTaskDifficulty.MEDIUM), 0);
            if (player.hardTasks.size() < 3)
                player.hardTasks.put(getRandomTaskHard(DailyTaskDifficulty.HARD), 0);
        }
        openDailyTasksViewer(player);
        player.sendMessage("<col=03b329>Your dailies have been reset.");
    }

    public static boolean assignTaskNoOpen(Player player) {
        if (!player.easyTasks.isEmpty() || !player.mediumTasks.isEmpty() || !player.hardTasks.isEmpty()) {
            player.sendMessage("<col=0000FF>You already have tasks. You can check your current tasks from your Task Book");
            return false;
        }
        while (player.easyTasks.size() < 3 && player.mediumTasks.size() < 3 && player.hardTasks.size() < 3) {
            if (player.easyTasks.size() < 3)
                player.easyTasks.put(getRandomTask(DailyTaskDifficulty.EASY), 0);
            if (player.mediumTasks.size() < 3)
                player.mediumTasks.put(getRandomTaskMedium(DailyTaskDifficulty.MEDIUM), 0);
            if (player.hardTasks.size() < 3)
                player.hardTasks.put(getRandomTaskHard(DailyTaskDifficulty.HARD), 0);
        }
        player.sendMessage("<col=03b329>Your dailies have been reset.");
        return true;
    }

    public static boolean hasEasyTask(Player player, DailyTask.EasyTasks task) {
        for (DailyTask.EasyTasks tasks : player.easyTasks.keySet()) {
            if (tasks == task)
                return true;
        }
        return false;
    }

    public static boolean hasMediumTask(Player player, DailyTask.MediumTasks task) {
        for (DailyTask.MediumTasks tasks : player.mediumTasks.keySet()) {
            if (tasks == task)
                return true;
        }
        return false;
    }

    public static boolean hasHardTask(Player player, DailyTask.HardTasks task) {
        for (DailyTask.HardTasks tasks : player.hardTasks.keySet()) {
            if (tasks == task)
                return true;
        }
        return false;
    }

    public static void openDailyTasksViewer(Player player) {
        List<EasyTasks> easyTasksList = new LinkedList<>();
        List<Integer> easyTasksValuesList = new LinkedList<>();

        for (Map.Entry<DailyTask.EasyTasks, Integer> easyTasks : player.easyTasks.entrySet()) {
            easyTasksList.add(easyTasks.getKey());
            easyTasksValuesList.add(easyTasks.getValue());
        }


        for (int i = 0; i < 3; i++) {
            if (easyTasksList.size() >= (i + 1)) {
                EasyTasks task = easyTasksList.get(i);
                if (easyTasksValuesList.get(i) >= task.amount) {
                    player.getPacketSender().sendString(1047, 31 + (i * 9), "<col=0000ff>Completed.");
                    player.getPacketSender().setHidden(1047, 35 + (i * 9), false);
                } else {
                    player.getPacketSender().setHidden(1047, 35 + (i * 9), true);
                    player.getPacketSender().sendString(1047, 31 + (i * 9), task.message);
                }
            } else {
                player.getPacketSender().sendString(1047, 31 + (i * 9), "Task Completed.");
                player.getPacketSender().setHidden(1047, 35 + (i * 9), true);
            }
        }


        //Medium
        List<MediumTasks> mediumTasksList = new LinkedList<>();
        List<Integer> mediumTasksValuesList = new LinkedList<>();

        for (Map.Entry<DailyTask.MediumTasks, Integer> mediumTasks : player.mediumTasks.entrySet()) {
            mediumTasksList.add(mediumTasks.getKey());
            mediumTasksValuesList.add(mediumTasks.getValue());
        }

        for (int i = 0; i < 3; i++) {
            if (mediumTasksList.size() >= (i + 1)) {
                MediumTasks task = mediumTasksList.get(i);
                if (mediumTasksValuesList.get(i) >= task.amount) {
                    player.getPacketSender().sendString(1047, 65 + (i * 9), "<col=0000ff>Completed.");
                    player.getPacketSender().setHidden(1047, 69 + (i * 9), false);
                } else {
                    player.getPacketSender().setHidden(1047, 69 + (i * 9), true);
                    player.getPacketSender().sendString(1047, 65 + (i * 9), task.message);
                }
            } else {
                player.getPacketSender().sendString(1047, 65 + (i * 9), "Task Completed.");
                player.getPacketSender().setHidden(1047, 69 + (i * 9), true);
            }
        }

        //Hard
        List<HardTasks> hardTasksList = new LinkedList<>();
        List<Integer> hardTasksValuesList = new LinkedList<>();

        for (Map.Entry<DailyTask.HardTasks, Integer> hardTasks : player.hardTasks.entrySet()) {
            hardTasksList.add(hardTasks.getKey());
            hardTasksValuesList.add(hardTasks.getValue());
        }

        for (int i = 0; i < 3; i++) {
            if (hardTasksList.size() >= (i + 1)) {
                HardTasks task = hardTasksList.get(i);
                if (hardTasksValuesList.get(i) >= task.amount) {
                    player.getPacketSender().sendString(1047, 99 + (i * 9), "<col=0000ff>Completed.");
                    player.getPacketSender().setHidden(1047, 103 + (i * 9), false);
                } else {
                    player.getPacketSender().setHidden(1047, 103 + (i * 9), true);
                    player.getPacketSender().sendString(1047, 99 + (i * 9), task.message);
                }
            } else {
                player.getPacketSender().sendString(1047, 99 + (i * 9), "Task Completed.");
                player.getPacketSender().setHidden(1047, 103 + (i * 9), true);
            }
        }
        player.openInterface(InterfaceType.MAIN, 1047);
    }

    public static void complete(Player player, Map.Entry<DailyTask.EasyTasks, Integer> task) {
        if (task.getValue() >= task.getKey().amount) {
            player.easyTasks.remove(task.getKey());
            player.dailyCount++;
            player.sendMessage("<col=0000FF>You have completed an easy task: " + Misc.formatStringFormal(task.getKey().name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 5 Daily Task Points");
            player.dailyTaskPoints += 5;
            player.dailyTaskCompletePoints += 1;
        }
    }

    public static void completeMedium(Player player, Map.Entry<DailyTask.MediumTasks, Integer> task) {
        if (task.getValue() >= task.getKey().amount) {
            player.mediumTasks.remove(task.getKey());
            player.sendMessage("<col=0000FF>You have completed your medium task: " + Misc.formatStringFormal(task.getKey().name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 10 daily task points");
            player.dailyTaskPoints += 10;
            player.dailyCount++;
            player.dailyTaskCompletePoints += 1;
        }
    }

    public static void completeHard(Player player, Map.Entry<DailyTask.HardTasks, Integer> task) {
        if (task.getValue() >= task.getKey().amount) {
            player.hardTasks.remove(task.getKey());
            player.sendMessage("<col=0000FF>You have completed your hard task: " + Misc.formatStringFormal(task.getKey().name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 15 daily task points");
            player.dailyTaskPoints += 15;
            player.dailyTaskCompletePoints += 1;
            player.dailyCount++;
        }
    }

    public static void increase(Player player, EasyTasks task) {
        for (Map.Entry<DailyTask.EasyTasks, Integer> tasks : player.easyTasks.entrySet()) {
            if (tasks.getKey().equals(task)) {
                int amount = tasks.getValue() + 1;
                player.easyTasks.put(tasks.getKey(), amount);
                if (amount % 5 == 0)
                    player.sendMessage("<col=8d008d>" + amount + "/" + tasks.getKey().amount + " of your easy task completed.");
                complete(player, tasks);
            }
        }

    }

    public static void increaseMedium(Player player, MediumTasks task) {
        for (Map.Entry<DailyTask.MediumTasks, Integer> tasks : player.mediumTasks.entrySet()) {
            if (tasks.getKey().equals(task)) {
                int amount = tasks.getValue() + 1;
                player.mediumTasks.put(tasks.getKey(), amount);
                if (amount % 5 == 0)
                    player.sendMessage("<col=8d008d>" + amount + "/" + tasks.getKey().amount + " of your medium task completed.");
                completeMedium(player, tasks);
            }
        }
    }

    public static void increaseHard(Player player, HardTasks task) {
        for (Map.Entry<DailyTask.HardTasks, Integer> tasks : player.hardTasks.entrySet()) {
            if (tasks.getKey().equals(task)) {
                int amount = tasks.getValue() + 1;
                player.hardTasks.put(tasks.getKey(), amount);
                if (amount % 5 == 0)
                    player.sendMessage("<col=8d008d>" + amount + "/" + tasks.getKey().amount + " of your hard task completed.");
                completeHard(player, tasks);
            }
        }
    }

    private static EasyTasks getRandomTask(DailyTaskDifficulty type) {
        ArrayList<EasyTasks> possibleTasks = new ArrayList<EasyTasks>();
        for (EasyTasks tasks : EasyTasks.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }

    private static MediumTasks getRandomTaskMedium(DailyTaskDifficulty type) {
        ArrayList<MediumTasks> possibleTasks = new ArrayList<>();
        for (MediumTasks tasks : MediumTasks.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }

    private static HardTasks getRandomTaskHard(DailyTaskDifficulty type) {
        ArrayList<HardTasks> possibleTasks = new ArrayList<HardTasks>();
        for (HardTasks tasks : HardTasks.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }

    public static boolean claimEasyReward(Player player, int slot, boolean warning) {
        if (player.easyTasks.isEmpty()) return false;
        final List<EasyTasks> tasks = new LinkedList<>(player.easyTasks.keySet());
        final List<Integer> tasksValues = new LinkedList<>(player.easyTasks.values());
        if (tasks.size() == 0 || tasksValues.size() == 0 || (tasks.size() - 1) < slot)  {
            return false;
        }
        final DailyTask.EasyTasks task = tasks.get(slot);
        final int amount = tasksValues.get(slot);
        if (amount < task.amount) {
            if (warning)
                player.sendMessage("You have not completed this task.");
            return false;
        }
        player.easyTasks.remove(task);
        DailyTask.openDailyTasksViewer(player);
        return true;
    }
    public static boolean claimMediumReward(Player player, int slot, boolean warning) {
        if (player.mediumTasks.isEmpty()) return false;
        final List<MediumTasks> tasks = new LinkedList<>(player.mediumTasks.keySet());
        final List<Integer> tasksValues = new LinkedList<>(player.mediumTasks.values());
        if (tasks.size() == 0 || tasksValues.size() == 0 || (tasks.size() - 1) < slot)  {
            return false;
        }
        final DailyTask.MediumTasks task = tasks.get(slot);
        final int amount = tasksValues.get(slot);
        if (amount < task.amount) {
            if (warning)
                player.sendMessage("You have not completed this task.");
            return false;
        }
        player.mediumTasks.remove(task);
        DailyTask.openDailyTasksViewer(player);
        return true;
    }
    public static boolean claimHardReward(Player player, int slot, boolean warning) {
        if (player.hardTasks.isEmpty()) return false;
        final List<HardTasks> tasks = new LinkedList<>(player.hardTasks.keySet());
        final List<Integer> tasksValues = new LinkedList<>(player.hardTasks.values());
        if (tasks.size() == 0 || tasksValues.size() == 0 || (tasks.size() - 1) < slot)  {
            return false;
        }
        final DailyTask.HardTasks task = tasks.get(slot);
        final int amount = tasksValues.get(slot);
        if (amount < task.amount) {
            if (warning)
                player.sendMessage("You have not completed this task.");
            return false;
        }
        player.hardTasks.remove(task);
        DailyTask.openDailyTasksViewer(player);
        return true;
    }

    static {
        InterfaceHandler.register(1047, actions -> {

            actions.actions[18] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimEasyReward(player, 2, false);
                DailyTask.claimMediumReward(player, 2, false);
                DailyTask.claimHardReward(player, 2, false);

                DailyTask.claimEasyReward(player, 1, false);
                DailyTask.claimMediumReward(player, 1, false);
                DailyTask.claimHardReward(player, 1, false);

                DailyTask.claimEasyReward(player, 0, false);
                DailyTask.claimMediumReward(player, 0, false);
                DailyTask.claimHardReward(player, 0, false);
            };


            actions.actions[35] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimEasyReward(player, 0, true);
            };
            actions.actions[44] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimEasyReward(player, 1, true);
            };
            actions.actions[53] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimEasyReward(player, 2, true);
            };

            actions.actions[69] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimMediumReward(player, 0, true);
            };
            actions.actions[78] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimMediumReward(player, 1, true);
            };
            actions.actions[87] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimMediumReward(player, 2, true);
            };

            actions.actions[103] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimHardReward(player, 0, true);
            };
            actions.actions[112] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimHardReward(player, 1, true);
            };
            actions.actions[121] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                DailyTask.claimHardReward(player, 2, true);
            };



        });
    }


}