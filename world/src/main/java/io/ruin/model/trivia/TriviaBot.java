package io.ruin.model.trivia;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.utility.Broadcast;
import io.ruin.utility.Misc;

import java.util.ArrayList;
import java.util.List;

public class TriviaBot {

    public static final int TIMER = 1800;

    public static int botTimer = TIMER;

    public static int answerCount;

    public static String currentQuestion;

    private static String currentAnswer;

    private static List<String> winners = new ArrayList<String>(3);

    public static boolean didSend = false;

    public static void pulse() {

        if (--botTimer <= 1) {
            botTimer = TIMER;
            didSend = false;
            askQuestion();
        }
    }

    public static List<String> getWinners() {
        return winners;
    }

    public static void resetForNextQuestion() {
        if (!winners.isEmpty()) {
            winners.clear();
        }
        answerCount = 0;
    }

    public static void attemptAnswer(Player p, String attempt) {

        if (hasAnswered(p)) {
            p.sendMessage("You have already answered the trivia question!");
            return;
        }

        if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) {
            final boolean doublePoints =  false;/*World.getBonusEvent().isActive() || PlayerRights.isDiamondDonator(p);*/
            if (answerCount == 0) {

                answerCount++;
                int points = doublePoints ? 20 : 10;
                Config.TRIVIA_POINTS.increment(p, points);
                p.sendMessage("<shad=000000><col=488cc7>You receive " + points + (doublePoints ? " (+ " + points / 2 + " bonus)" : "") + " trivia points for answering first.");
               //EliteAchievements.doProgress(p, EliteAchievements.EliteAchievementData.SAME_SOME_FOR_THE_REST_OF_US, 10);
                winners.add(p.getName());
                return;
            }
            if (answerCount == 1) {

                answerCount++;
                int points = doublePoints ? 12 : 6;
                Config.TRIVIA_POINTS.increment(p, points);
                p.sendMessage("<shad=000000><col=488cc7>You receive " + points + (doublePoints ? " (+ " + points / 2 + " bonus)" : "") + " trivia points for answering second.");
               // EliteAchievements.doProgress(p, EliteAchievements.EliteAchievementData.SAME_SOME_FOR_THE_REST_OF_US, 6);
                winners.add(p.getName());
                return;

            }
            if (answerCount == 2) {
                int points = doublePoints ? 8 : 4;
                Config.TRIVIA_POINTS.increment(p, points);
                p.sendMessage("<shad=000000><col=488cc7>You receive " + points + (doublePoints ? " (+ " + points / 2 + " bonus)" : "") + " trivia points for answering third.");
               // EliteAchievements.doProgress(p, EliteAchievements.EliteAchievementData.SAME_SOME_FOR_THE_REST_OF_US, 4);
                winners.add(p.getName());


                if (winners.size() > 0) {
                    StringBuilder triviaBuilder = new StringBuilder();
                    triviaBuilder.append("<img=15><col=488cc7>[Trivia] First Place: <col=2776bb>").append(winners.get(0)).append("<col=488cc7>");
                    if (winners.size() > 1) {
                        triviaBuilder.append(", Second Place: <col=2776bb>").append(winners.get(1)).append("<col=488cc7>");
                    } else {
                        triviaBuilder.append("!");
                    }
                    if (winners.size() > 2) {
                        triviaBuilder.append(", Third Place: <col=2776bb>").append(winners.get(2)).append("<col=488cc7>");
                    } else {
                        triviaBuilder.append("!");
                    }
                    Broadcast.WORLD.sendMessage(triviaBuilder.toString());
                }
                resetForNextQuestion();
                currentQuestion = "";
                didSend = false;
                botTimer = TIMER;
                answerCount = 0;
            }

        } else {
            if (attempt.contains("question") || attempt.contains("repeat")) {
                p.sendMessage("<col=ff0000>" + (currentQuestion));
                return;
            }
            p.sendMessage("<col=ff0000>[Trivia] You entered the answer wrong for question: " + (currentQuestion));
        }
    }

    private static boolean hasAnswered(Player p) {
        for (var i = 0; i < winners.size(); i++) {
            if (winners.get(i).equalsIgnoreCase(p.getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean acceptingQuestion() {
        return currentQuestion != null && !currentQuestion.equals("");
    }

    private static void askQuestion() {

        for (var i = 0; i < TRIVIA_DATA.length; i++) {
            if (Misc.random(TRIVIA_DATA.length - 1) == i) {
                if (!didSend) {
                    didSend = true;
                    currentQuestion = TRIVIA_DATA[i][0];
                    currentAnswer = TRIVIA_DATA[i][1];
                    resetForNextQuestion();
                    Broadcast.WORLD.sendMessage(currentQuestion);

                }
            }
        }
    }

    private static final String[][] TRIVIA_DATA = {
            { "<img=15><shad=000000><col=488cc7>[Trivia] Rune Armour was originally going to be what color in 'Runescape <shad=000000><col=488cc7>Classic'  <shad=000000><col=488cc7>prior to release?", "purple" },
            { "<img=15><shad=000000><col=488cc7>[Trivia] Teleportation no longer works at what level in the wilderness?", "20" },
            { "<img=15><shad=000000><col=488cc7>[Trivia] What year did Oldschool Runescape come out?", "2013" },
            { "<img=15><shad=000000><col=488cc7>[Trivia] What was the original name of Runescape?", "devious mud", "deviousmud" },
            //{ "<img=15><shad=000000><col=488cc7>[Trivia] What was the Wizard's Tower originally called?", "warlocks tower" },
            { "<img=15><shad=000000><col=488cc7>[Trivia] The H.A.M trap door has a picture of what on it?", "ham", "a ham" },
    };
}
