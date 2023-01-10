package io.ruin.model.activities.poll;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.XMLController;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PollBooth {

    public static Poll poll;

    public static final int VOTE_WIDGET = 400;
    public static final int POLL_WIDGET = 345;
    public static final ImmutableList<Integer> BOOTHS = ImmutableList.of(26815, 8720);
    public static Gson gson = new Gson();

    public PollBooth(Gson gson) {
        PollBooth.gson = gson;
    }

    public static void openPollInterface(@Nonnull Player player) {
        player.openInterface(InterfaceType.MAIN, POLL_WIDGET);
        player.getPacketSender().sendClientScript(917, "ii", -1, -2);
        player.getPacketSender().sendClientScript(603, "sIIIIs", "Devious Content Poll #" + poll.getPollNumber() + ": " + poll.getPollTitle(),
                22609922, 22609932, 22609931, 22609930, "Building...");
        player.getPacketSender().sendClientScript(609, "siidfiiI", poll.getPollDescription() + "|" + poll.getEndDate(),
                16750623, 0, 495, 495, 12, 5, 22609931);
        player.getPacketSender().sendClientScript(610, "ssI", "Click here to visit the forums.", "https://deviousps.com/forums", 22609931);
        player.getPacketSender().sendClientScript(609, "siidfiiI",
                "Votes: " + poll.getTotalVotes(), 16777215, 1, 496, 496, 12, 5, 22609931);

        generateQuestionScript(player, false);

        //
        player.getPacketSender().sendClientScript(609, "siidfiiI",
                "", 16750623, 0, 495, 495, 12, 5, 22609931);
        player.getPacketSender().sendClientScript(618, "III1",
                22609931, 22609932, 22609930, 1);
        player.getPacketSender().sendClientScript(604, "IIs", 22609928, 22609929, "History");
        player.getPacketSender().sendClientScript(604, "IIs", 22609926, 22609927, "Refresh");
        player.getPacketSender().sendClientScript(604, "IIs", 22609924, 22609925, "Vote");
    }

    public static void openVoteInterface(@Nonnull Player player) {
        player.openInterface(InterfaceType.MAIN, VOTE_WIDGET);
        player.getPacketSender().sendAccessMask(1, VOTE_WIDGET, 0, 1, 3199);
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.getPacketSender().sendClientScript(603, "sIIIIs",
                "Devious Poll #" + poll.getPollNumber() + ": " + poll.getPollTitle(), 26214403, 26214413, 26214410, 26214409, "Building...");
        player.getPacketSender().sendClientScript(609, "siidfiiI",
                poll.getPollDescription() + "|" + poll.getEndDate(), 16750623, 0, 495, 495, 12, 5, 26214410);
        player.getPacketSender().sendClientScript(610, "ssI", "", "", 26214410);

        generateQuestionScript(player, true);

        player.getPacketSender().sendClientScript(618, "III1", 26214410, 26214413, 26214409, 1);
        player.getPacketSender().sendClientScript(604, "IIs", 26214405, 26214406, "Clear");
        player.getPacketSender().sendClientScript(604, "IIs", 26214407, 26214408, "Cancel");
        player.getPacketSender().sendClientScript(604, "IIs", 26214411, 26214412, "Vote");

        player.setBallot(new PlayerBallot(player));
    }

    public static void generateQuestionScript(@Nonnull Player player, boolean voting) {
        for (int i = 0; i < poll.getQuestions().size(); i++) {
            PollQuestion question = poll.getQuestions().get(i);

            if (voting) {
                player.getPacketSender().sendClientScript(619, "isi",
                        getQuestionValue(question, i), ("Question " + (i + 1) + "|" + question.getQuestion() + generateAnswers(question)), 0);
            } else {
                Object[] parameters = generateParameters(question, i);
                player.getPacketSender().sendClientScript(624, "iisiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii", parameters);
            }
        }
    }

    public static Object[] generateParameters(PollQuestion question, int index) {
        List<Integer> parms = new ArrayList<>();
        for (int i = 0; i < question.getVotes().size(); i++) {
            parms.add(question.getVotes().get(i));
        }
        if (question.getVotes().size() < 6) {
            for (int i = 0; i < 6 - question.getVotes().size(); i++) {
                parms.add(0);
            }
        }
        return new Object[]{
                (index + 1), -1, ("Question " + (index + 1) + "|" + question.getQuestion() + generateAnswers(question)),
                question.getAnswers().size(), 0, parms.get(0), parms.get(1), parms.get(2),
                parms.get(3), parms.get(4), parms.get(5),
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0

        };

//        return new Object[]{
//                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (Integer) parms.get(5), (Integer) parms.get(4), (Integer) parms.get(3), (Integer) parms.get(2),
//                (Integer) parms.get(1), (Integer) parms.get(0), 0, (Integer) question.getAnswers().size(), ("Question " + (index + 1) + "|" + question.getQuestion() + generateAnswers(question)),
//                -1, (index + 1)
//        };
    }


    public static String generateAnswers(PollQuestion question) {
        return "|||" + Joiner.on("|").join(question.getAnswers());
    }

    public static int getQuestionValue(PollQuestion question, int index) {
        return (256 * index) + (4 * question.getAnswers().size());
    }

    public static int getQuestion(int option) {
        return option / 30;
    }

    public static boolean hasVoted(String UUID) {
        try {
            final List<String> votedUUIDS = XMLController.readXML(new File("data//poll/pollVotes" + poll.getPollNumber() + ".xml"));
            if (votedUUIDS != null) {
                for (String uuid : votedUUIDS) {
                    if (uuid.equalsIgnoreCase(UUID)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void init() {
        final File pollFile = new File("data//poll/poll.json");
        if (!pollFile.exists()) {
            return;
        }
        try {
            try (final BufferedReader parse = new BufferedReader(
                    new FileReader(pollFile))) {
                poll = gson.fromJson(parse, Poll.class);
                System.out.println(poll.getTotalVotes());
                List<String> pollVotes = XMLController.readXML(new File("data//poll/pollVotes" + poll.getPollNumber() + ".xml"));
                if (poll != null && pollVotes != null && !pollVotes.isEmpty()) {
                    poll.getVotedUids().addAll(pollVotes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void submitBallot(PlayerBallot playerBallot) {
        for (int i = 0; i < poll.getQuestions().size(); i++) {
            List<Integer> votes = poll.getQuestions().get(i).getVotes();
            int amount = poll.getQuestions().get(i).getVotes().get(playerBallot.getAnswers().get(i));
            votes.set(playerBallot.getAnswers().get(i), ++amount);
        }
        poll.incrementTotalVotes();
        save();
        try {
            File file = new File("data//poll/pollVotes" + poll.getPollNumber() + ".xml");
            List<String> votedUsers = XMLController.readXML(file);
            votedUsers.add(playerBallot.getPlayer().getName());
            XMLController.writeXML(votedUsers, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        openPollInterface(playerBallot.getPlayer());
    }

    public static void save() {
        final File file = new File("data//poll/poll.json");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(poll, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshInterface(@Nonnull Player player, @Nonnull PlayerBallot ballot) {
        player.getPacketSender().sendClientScript(917, "ii", -1, -1);
        player.openInterface(InterfaceType.MAIN, VOTE_WIDGET);
        player.getPacketSender().sendAccessMask(1, VOTE_WIDGET, 1, 0, 3199);
        player.getPacketSender().sendClientScript(603, "sIIIIs",
                "Devious Poll #" + poll.getPollNumber() + ": " + poll.getPollTitle(),
                26214403, 26214413, 26214410, 26214409, "Building...");
        player.getPacketSender().sendClientScript(609, "siidfiiI",
                poll.getPollDescription() + "|" + poll.getEndDate(), 16750623, 0, 495, 495, 12, 5, 26214410);
        player.getPacketSender().sendClientScript(610, "ssI", "", "", 26214410);

        player.getPacketSender().sendClientScript(618, "III1", 26214410, 26214413, 26214409, 0);
        //   player.getPacketSender().sendClientScript(400, 3, "Cast your vote.");
        player.getPacketSender().sendClientScript(604, "IIs", 26214405, 26214406, "Clear");
        player.getPacketSender().sendClientScript(604, "IIs", 26214407, 26214408, "Cancel");
        player.getPacketSender().sendClientScript(604, "IIs", 26214411, 26214412, "Vote");
    }

    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    static {

        LoginListener.register(p -> {
            init();
        });

        ObjectAction.register(8720, 1, (player, obj) -> {
            if (poll == null) {
                player.sendMessage("A poll is not currently running.");
                return;
            }
            openPollInterface(player);

        });

        ObjectAction.register(26813, 1, (player, obj) -> {
            if (poll == null) {
                player.sendMessage("A poll is not currently running.");
                return;
            }
            openPollInterface(player);

        });

        InterfaceHandler.register(POLL_WIDGET, h -> {
            h.actions[5] = (DefaultAction) (player, option, slot, itemId) -> {
                openVoteInterface(player);
            };
            h.actions[7] = (DefaultAction) (player, option, slot, itemId) -> {
                int questionIndex = getQuestion(option);
                if (player.getBallot() == null) {
                    player.setBallot(new PlayerBallot(player));
                }
                PlayerBallot playerBallot = player.getBallot();
                playerBallot.getAnswers().clear();
                refreshInterface(player, playerBallot);
                openPollInterface(player);
            };
            h.actions[8] = (DefaultAction) (player, option, slot, itemId) -> {
                player.sendMessage("This button is not working yet.");
            };
        });

        InterfaceHandler.register(VOTE_WIDGET, h -> {
            h.actions[6] = (DefaultAction) (player, option, slot, itemId) -> {
                int questionIndex = getQuestion(option);
                if (player.getBallot() == null) {
                    player.setBallot(new PlayerBallot(player));
                }
                PlayerBallot playerBallot = player.getBallot();
                playerBallot.getAnswers().clear();
                refreshInterface(player, playerBallot);
            };
            h.actions[8] = (DefaultAction) (player, option, slot, itemId) -> {
                openPollInterface(player);
                player.setBallot(null);
            };
            //Answers
            int[] answerSlotIds = {0, 1,/**/32, 33, 34, 35,/**/64, 65,/**/96, 97,/**/128, 129,/**/160, 161,/**/192, 193, 194};
            String[] chosenAnswers = new String[8];
            h.actions[2] = (DefaultAction) (player, option, slot, itemId) -> {
                if (contains(answerSlotIds, slot)) {
                    int questionNumber = 1;
                    switch (slot) {
                        case 0: {
                            chosenAnswers[0] = "Yes";
                            questionNumber = 1;
                            break;
                        }
                        case 1: {
                            chosenAnswers[0] = "No";
                            questionNumber = 1;
                            break;
                        }
                        case 32: {
                            chosenAnswers[1] = "Fix bugs";
                            questionNumber = 2;
                            break;
                        }
                        case 33: {
                            chosenAnswers[1] = "New bosses";
                            questionNumber = 2;
                            break;
                        }
                        case 34: {
                            chosenAnswers[1] = "New minigame";
                            questionNumber = 2;
                            break;
                        }
                        case 35: {
                            chosenAnswers[1] = "Doesn't matter";
                            questionNumber = 2;
                            break;
                        }
                        case 64: {
                            chosenAnswers[2] = "Yes";
                            questionNumber = 3;
                            break;
                        }
                        case 65: {
                            chosenAnswers[2] = "No";
                            questionNumber = 3;
                            break;
                        }
                        case 96: {
                            chosenAnswers[3] = "Yes";
                            questionNumber = 4;
                            break;
                        }
                        case 97: {
                            chosenAnswers[3] = "No";
                            questionNumber = 4;
                            break;
                        }
                        case 128: {
                            chosenAnswers[4] = "Yes";
                            questionNumber = 5;
                            break;
                        }
                        case 129: {
                            chosenAnswers[4] = "No";
                            questionNumber = 5;
                            break;
                        }
                        case 160: {
                            chosenAnswers[5] = "Yes";
                            questionNumber = 6;
                            break;
                        }
                        case 161: {
                            chosenAnswers[5] = "No";
                            questionNumber = 6;
                            break;
                        }
                        case 192: {
                            chosenAnswers[6] = "Detailed";
                            questionNumber = 7;
                            break;
                        }
                        case 193: {
                            chosenAnswers[6] = "Basic";
                            questionNumber = 7;
                            break;
                        }
                        case 194: {
                            chosenAnswers[6] = "Doesn't matter";
                            questionNumber = 7;
                            break;
                        }
                    }
                    int questionIndex = getQuestion(option);
                    int answer = option - (30 * questionIndex) - (2 * questionIndex);
                    player.getBallot().getChosenAnswers().put(questionNumber, chosenAnswers[questionNumber - 1]);
                    player.sendMessage(Color.DARK_RED, "Voted for question " + questionNumber + " answer: " + chosenAnswers[questionNumber - 1] + " answer id was " + answer);
                }
            };
            h.actions[12] = (DefaultAction) (player, option, slot, itemId) -> {
                int questionIndex = getQuestion(option);
                if (player.getBallot() == null) {
                    player.setBallot(new PlayerBallot(player));
                }
                PlayerBallot playerBallot = player.getBallot();
                playerBallot.getAnswers().clear();
                refreshInterface(player, playerBallot);
                if (/*poll.isLimitVote() &&*/ hasVoted(player.getName())) {
                    player.sendMessage("This poll is limited to 1 vote per computer, It appears you've already voted.");
                    return;
                }
                if (playerBallot.getChosenAnswers().size() < poll.getQuestions().size() || playerBallot.getChosenAnswers().isEmpty()) {
                    player.sendMessage("Please answer all questions on the ballot to cast your vote.");
                    return;
                }

                player.getBallot().getChosenAnswers().forEach((question, answer) -> {
                    player.sendMessage(Color.DARK_RED, question + " " + Color.DARK_GREEN.wrap(answer));
                });

                int answer = option - (30 * questionIndex) - (2 * questionIndex);
                playerBallot.getAnswers().put(questionIndex, answer);

                submitBallot(playerBallot);
                player.sendMessage("Thank you for voting!");

                refreshInterface(player, playerBallot);

            };
        });

    }
}