package io.ruin.model.activities.gambling;

import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Direction;
import io.ruin.model.map.MapListener;
import io.ruin.model.map.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GambleManager {
    private static List<FlowerPokerRoom> flowerPokerRooms;
    private static int freeFlowerPokerRooms;

    private static List<GambleQeueSpot> flowerPokerQueue;

    private static final Position playerOneFinalPosition = new Position(3120, 3473, 0);
    private static final Position playerTwoFinalPosition = new Position(3121, 3473, 0);

    private static void exited(Player player, boolean b) {
        player.setAction(1, null);
    }

    private static void entered(Player player) {
        // player.setAction(1, PlayerAction.GAMBLE);
    }

    public static void startFlowerPokerGame(Player playerOne, Player playerTwo) {
        flowerPokerQueue.add(new GambleQeueSpot(playerOne, playerTwo));
        boolean freeRoom = getFreeFlowerPokerRoom().isPresent();
        if (freeRoom) {
            sendPendingMessage(playerOne, playerTwo, "Your game will begin shortly. Please do not leave the gamble zone.");
        } else if (freeRoom) {
            sendPendingMessage(playerOne, playerTwo, "Currently there is no free flower poker room. Your game will begin shortly. Please do not leave the gamble zone.");
        }
    }

    private static Optional<FlowerPokerRoom> getFreeFlowerPokerRoom() {
        return flowerPokerRooms.stream().filter(room -> !room.isUsed()).findFirst();
    }

    private static void serializeRooms() {
        freeFlowerPokerRooms = 2;
        flowerPokerRooms = new ArrayList<>();

        flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3120, 3486, 0), new Position(3121, 3486, 0), false, Direction.SOUTH));
        flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3125, 3486, 0), new Position(3126, 3486, 0), false, Direction.SOUTH));
        //  flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3084, 3482, 0), new Position(3083, 3482, 0), false, Direction.SOUTH));
        //  flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3080, 3482, 0), new Position(3079, 3482, 0), false, Direction.SOUTH));
        //  flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3094, 3474, 0), new Position(3093, 3474, 0), false, Direction.SOUTH));
        //   flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3090, 3474, 0), new Position(3089, 3474, 0), false, Direction.SOUTH));
        //  flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3086, 3474, 0), new Position(3085, 3474, 0), false, Direction.SOUTH));
        //  flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3082, 3474, 0), new Position(3081, 3474, 0), false, Direction.SOUTH));
        // flowerPokerRooms.add(new FlowerPokerRoom(5, new Position(3078, 3474, 0), new Position(3077, 3474, 0), false, Direction.SOUTH));
    }

    public static Position getPlayerOneFinalPosition() {
        return playerOneFinalPosition;
    }

    public static Position getPlayerTwoFinalPosition() {
        return playerTwoFinalPosition;
    }

    private static void sendPendingMessage(Player playerOne, Player playerTwo, String message) {
        playerOne.sendFilteredMessage(message);
        playerTwo.sendFilteredMessage(message);
    }

    public static void process() {
        try {
            cleanUpScrewedParties();

            if (flowerPokerQueue.size() > 0 && freeFlowerPokerRooms >= 1) {
                Optional<FlowerPokerRoom> freeRoom = getFreeFlowerPokerRoom();

                if (freeRoom.isPresent()) {
                    GambleQeueSpot game = flowerPokerQueue.remove(0);
                    freeRoom.get().setUsed(true);
                    new FlowerPoker(game.getPlayerOne(), game.getPlayerTwo(), freeRoom.get());
                    notifyOtherPlayers();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cleanUpScrewedParties() {
        List<GambleQeueSpot> tempFlowerPokerGames = new ArrayList<>();

        for (GambleQeueSpot game : flowerPokerQueue) {
            if (!inGambleZone(game.getPlayerOne().getPosition())) {
                game.getPlayerOne().sendFilteredMessage("Your gamble party has been disbanded since you left the gamble zone.");
                game.getPlayerTwo().sendFilteredMessage("Your gamble party has been disbanded since your partner left the gamble zone.");
                continue;
            } else if (!inGambleZone(game.getPlayerTwo().getPosition())) {
                game.getPlayerOne().sendFilteredMessage("Your gamble party has been disbanded since your partner left the gamble zone.");
                game.getPlayerTwo().sendFilteredMessage("Your gamble party has been disbanded since you left the gamble zone.");
                continue;
            }
            tempFlowerPokerGames.add(game);
        }
        flowerPokerQueue = tempFlowerPokerGames;
    }

    private static boolean inGambleZone(Position position) {
        return position.inBounds(new Bounds(3118, 3471, 3127, 3487, 0));
    }

    private static void notifyOtherPlayers() {
        int flowerPokerGamesToGo = flowerPokerQueue.size();

        for (GambleQeueSpot game : flowerPokerQueue) {
            game.getPlayerOne().sendFilteredMessage("You are currently number " + flowerPokerGamesToGo
                    + " in the flower poker queue.");
            game.getPlayerTwo().sendFilteredMessage("You are currently number " + flowerPokerGamesToGo
                    + " in the flower poker queue.");
        }
    }

    public static void showGambleDialogue(Player playerOne, Player playerTwo) {
        proposeGambling(playerOne, playerTwo);
    }

    public static void proposeGambling(Player playerOne, Player playerTwo) {
        playerOne.sendMessage("An invite to play flower poker has been sent to " + playerTwo.getName() + ".");
        World.startEvent(gamebleEvent -> playerTwo.dialogue(
                new OptionsDialogue("Do you accept to do a flower poker battle with "
                        + Color.DARK_RED.wrap(playerOne.getName()),
                        new Option("Yes", () -> {
                            startFlowerPokerGame(playerOne, playerTwo);
                            playerOne.setGambleRequestCooldown(0);
                            playerTwo.setGambleRequestCooldown(0);
                        }),
                        new Option("No Thanks", () -> playerOne.sendFilteredMessage(playerTwo.getName() + " has refused to gamble with you.")))
        ));
    }

    static {
        serializeRooms();
        flowerPokerQueue = new ArrayList<>();

        MapListener.registerBounds(new Bounds(3118, 3471, 3127, 3487, 0))
                .onEnter(GambleManager::entered)
                .onExit(GambleManager::exited);
    }
}
