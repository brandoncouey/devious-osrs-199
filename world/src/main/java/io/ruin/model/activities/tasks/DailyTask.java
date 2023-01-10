package io.ruin.model.activities.tasks;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DailyTask {

    public enum PossibleTasksHard {

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

        PossibleTasksHard(DailyTaskDifficulty type, int amount, String message) {
            this.type = type;
            this.amount = amount;
            this.message = message;
        }
    }

    public enum PossibleTasksMedium {

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

        PossibleTasksMedium(DailyTaskDifficulty type, int amount, String message) {
            this.type = type;
            this.amount = amount;
            this.message = message;
        }
    }

    public enum PossibleTasksEasy {
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

        PossibleTasksEasy(DailyTaskDifficulty type, int amount, String message) {

            this.type = type;
            this.amount = amount;
            this.message = message;

        }
    }

    public static PossibleTasksEasy getEasyTask(int taskId) {
        return PossibleTasksEasy.values()[taskId];
    }

    public static PossibleTasksMedium getMediumTask(int taskId) {
        return PossibleTasksMedium.values()[taskId];
    }

    public static PossibleTasksHard getHardTask(int taskId) {
        return PossibleTasksHard.values()[taskId];
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

    /*private void complete(Player player) {
        PlayerCounter.DAILY_TASKS_COMPLETED.increment(player, 1);
        player.getInventory().addOrDrop(13307, difficulty().getBmReward());
        player.dailyTaskPoints += difficulty().getPointsReward();
        player.sendMessage(Color.DARK_GREEN.wrap("Congratulations, you have completed the task \"" + shortDescription() + "\"! You receive " + NumberUtils.formatNumber(difficulty().getBmReward()) + " blood money and " + difficulty().getPointsReward() + " task points. You now have a total of " + NumberUtils.formatNumber(player.dailyTaskPoints) + " task points."));
        if (isComplete(player, 0) && isComplete(player, 1) && isComplete(player, 2)) {
            player.sendMessage("You have completed all of today's tasks and receive 6 bonus task points. Visit the guide in Edgeville to spend them!");
            player.dailyTaskPoints += 6;
        } else {
            player.sendMessage("Complete the remaining tasks for the day to receive 6 bonus task points.");
        }
    }*


    /**
     * Assigns a task to a player when it has daily's enabled and has no current task.
     *
     * @param player
     */

    public static void assignTask(Player player) {
            if (player.currentTaskEasy != null || player.currentTaskMedium != null || player.currentTaskHard != null) {
                player.sendMessage("<col=0000FF>You already have tasks. You can check your current tasks from your Task Book");
        }
        if (player.currentTaskEasy == null && player.currentTaskMedium == null && player.currentTaskHard == null) {
            player.currentTaskEasy = getRandomTask(DailyTaskDifficulty.EASY);
            player.dailyEasyTask = player.currentTaskEasy.ordinal();
            player.currentTaskMedium = getRandomTaskMedium(DailyTaskDifficulty.MEDIUM);
            player.dailyMediumTask = player.currentTaskMedium.ordinal();
            player.currentTaskHard = getRandomTaskHard(DailyTaskDifficulty.HARD);
            player.dailyHardTask = player.currentTaskHard.ordinal();
            player.dailyTaskDate = getTodayDate();
            player.sendMessage("<col=7F00FF>New Easy Task: " + player.currentTaskEasy.message);
            player.sendMessage("<col=7F00FF>New Medium Task: " + player.currentTaskMedium.message);
            player.sendMessage("<col=7F00FF>New Hard Task: " + player.currentTaskHard.message);
            //Achievements.Achievement.increase(player, AchievementType._4, 1);
        }
    }

    public static void autoReset(Player player) {
        if (player.dailyTaskDate != getTodayDate()) {
            player.dailyTaskCompletePoints = 0;
            player.completedDailyTask = false;
            player.completedDailyTaskMedium = false;
            player.completedDailyTaskHard = false;
            player.currentTaskEasy = null;
            player.currentTaskMedium = null;
            player.currentTaskHard = null;
            player.totalDailyDone = 0;
            player.totalDailyMediumDone = 0;
            player.totalDailyHardDone = 0;
            player.claimedDailyCache = false;
            if(player.dailyTaskDate != 0) {
                player.sendMessage("Daily tasks have been reset.");
                player.dailyTaskDate = 0;
            } else {
                player.sendMessage("<col=7F00FF>You currently have no daily tasks, talk to Dave at home for a task.");
            }
        } else {
            if(player.getCurrentTaskEasy() == null) {
                if(player.dailyEasyTask > -1) {
                    player.setCurrentTaskEasy(getEasyTask(player.dailyEasyTask));
                    player.sendMessage(Color.BLUE.wrap("Your daily easy task is: " + player.getCurrentTaskEasy().message));
                } else {
                    player.sendMessage(Color.RED.wrap("Failed to load the daily easy task!"));
                }
            } else {
                player.sendMessage("easy task: " + player.getCurrentTaskEasy().message);
            }
            if(player.getCurrentTaskMedium() == null) {
                if(player.dailyMediumTask > -1) {
                    player.setCurrentTaskMedium(getMediumTask(player.dailyMediumTask));
                    player.sendMessage(Color.BLUE.wrap("Your daily medium task is: " + player.getCurrentTaskMedium().message));
                } else {
                    player.sendMessage(Color.RED.wrap("Failed to load the daily medium task!"));
                }
            } else {
                player.sendMessage("medium task: " + player.getCurrentTaskMedium().message);
            }
            if(player.getCurrentTaskHard() == null) {
                if(player.dailyHardTask > -1) {
                    player.setCurrentTaskHard(getHardTask(player.dailyHardTask));
                    player.sendMessage(Color.BLUE.wrap("Your daily hard task is: " + player.getCurrentTaskHard().message));
                } else {
                    player.sendMessage(Color.RED.wrap("Failed to load the daily hard task!"));
                }
            } else {
                player.sendMessage("hard task: " + player.getCurrentTaskHard().message);
            }
        }
    }

    public static void open(Player player) {
        if (player.currentTaskEasy == null && player.currentTaskMedium == null && player.currentTaskHard == null) {
            player.sendScroll("<col=8B0000>Daily Task Manager",
                    "Tasks reset after claiming reward from Manager",
                    "<col=8B0000>---------------- Easy Task ----------------",
                    "<col=0000FF>No task assigned",
                    "",
                    "<col=8B0000>---------------- Medium Task ----------------",
                    "<col=0000FF>No task assigned",
                    "",
                    "<col=8B0000>---------------- Hard Task ----------------",
                    "<col=0000FF>No task assigned"

            );
        } else {
            if (player.currentTaskEasy != null ) {
                player.sendMessage("<col=7F00FF>Current Easy Task: " + player.currentTaskEasy.message + " remaining: " + player.totalDailyDone + "/" + player.currentTaskEasy.amount);
            } else {
                player.sendMessage( "<col=0000FF>Current Easy Task: COMPLETED");
            }
            if (player.currentTaskMedium != null ) {
                player.sendMessage("<col=7F00FF>Current Medium Task: " + player.currentTaskMedium.message + " remaining: " + player.totalDailyMediumDone + "/" + player.currentTaskMedium.amount);
            } else {
                player.sendMessage( "<col=0000FF>Current Medium Task: COMPLETED");
            }
            if (player.currentTaskHard != null ) {
                player.sendMessage("<col=7F00FF>Current Hard Task: " + player.currentTaskHard.message + " remaining: " + player.totalDailyHardDone + "/" + player.currentTaskHard.amount);
            } else {
                player.sendMessage( "<col=0000FF>Current Hard Task: COMPLETED");
            }
        }
    }

    public static void complete(Player player) {
        if (player.totalDailyDone >= player.currentTaskEasy.amount) {
            player.totalDailyDone = 0;
            player.dailyCount++;
            player.completedDailyTask = true;
            player.sendMessage("<col=0000FF>You have completed your easy task: " + Misc.capitalize(player.currentTaskEasy.name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 5 Daily Task Points");
            player.dailyTaskPoints += 5;
            player.dailyTaskCompletePoints += 1;
            player.currentTaskEasy = null;
        }
    }

    public static void completeMedium(Player player) {
        if (player.totalDailyMediumDone >= player.currentTaskMedium.amount) {
            player.totalDailyMediumDone = 0;
            player.completedDailyTaskMedium = true;
            player.sendMessage("<col=0000FF>You have completed your medium task: " + Misc.capitalize(player.currentTaskMedium.name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 10 daily task points");
            player.dailyTaskPoints += 10;
            player.dailyCount++;
            player.dailyTaskCompletePoints += 1;
            player.currentTaskMedium = null;
        }
    }

    public static void completeHard(Player player) {
        if (player.totalDailyHardDone >= player.currentTaskHard.amount) {
            player.totalDailyHardDone = 0;
            player.completedDailyTaskHard = true;
            player.sendMessage("<col=0000FF>You have completed your hard task: " + Misc.capitalize(player.currentTaskHard.name().toLowerCase().replace("_", " ")) + "");
            player.sendMessage("<col=0000FF>You receive 15 daily task points");
            player.dailyTaskPoints += 15;
            player.dailyTaskCompletePoints += 1;
            player.dailyCount++;
            player.currentTaskHard = null;
        }
    }

    public static void increase(Player player, PossibleTasksEasy task) {
        if (player.currentTaskEasy == null)
            return;
        if (player.currentTaskEasy.equals(task)) {
            player.totalDailyDone++;
            if (player.totalDailyDone % 5 == 0)
                player.sendMessage("<col=8d008d>" + player.totalDailyDone + "/" + player.currentTaskEasy.amount + " of your easy task completed.");
            complete(player);
        }
    }

    public static void increaseMedium(Player player, PossibleTasksMedium task) {
        if (player.currentTaskMedium == null)
            return;
        if (player.currentTaskMedium.equals(task)) {
            player.totalDailyMediumDone += 1;
            if (player.totalDailyMediumDone % 5 == 0)
                player.sendMessage("<col=8d008d>" + player.totalDailyMediumDone + "/" + player.currentTaskMedium.amount + " of your medium task completed.");
            completeMedium(player);
        }
    }

    public static void increaseHard(Player player, PossibleTasksHard task) {
        if (player.currentTaskHard == null)
            return;
        if (player.currentTaskHard.equals(task)) {
            player.totalDailyHardDone++;
            if (player.totalDailyHardDone % 5 == 0)
                player.sendMessage("<col=8d008d>" + player.totalDailyHardDone + "/" + player.currentTaskHard.amount + " of your hard task completed.");
            completeHard(player);
        }
    }

    private static PossibleTasksEasy getRandomTask(DailyTaskDifficulty type) {
        ArrayList<PossibleTasksEasy> possibleTasks = new ArrayList<PossibleTasksEasy>();
        for (PossibleTasksEasy tasks : PossibleTasksEasy.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }

    private static PossibleTasksMedium getRandomTaskMedium(DailyTaskDifficulty type) {
        ArrayList<PossibleTasksMedium> possibleTasks = new ArrayList<PossibleTasksMedium>();
        for (PossibleTasksMedium tasks : PossibleTasksMedium.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }

    private static PossibleTasksHard getRandomTaskHard(DailyTaskDifficulty type) {
        ArrayList<PossibleTasksHard> possibleTasks = new ArrayList<PossibleTasksHard>();
        for (PossibleTasksHard tasks : PossibleTasksHard.values())
            if (tasks.type.equals(type))
                possibleTasks.add(tasks);
        return Misc.randomTypeOfList(possibleTasks);
    }


}