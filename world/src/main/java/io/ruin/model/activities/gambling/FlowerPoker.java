package io.ruin.model.activities.gambling;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.GambleInterface;
import io.ruin.model.map.Direction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FlowerPoker {

    private static Player playerOne;
    private static Player playerTwo;

    private static List<Item> playerOneBet;
    private static List<Item> playerTwoBet;

    private static List<GameObject> playerOneFlowerObjects;
    private static List<GameObject> playerTwoFlowerObjects;

    private List<Integer> playerOneFlowers;
    private List<Integer> playerTwoFlowers;
    private static FlowerPokerRoom flowerPokerRoom;

    private boolean replay = false;
    private boolean restart = false;
    private static GambleLogger gambleLogger;

    public FlowerPoker(Player playerOne, Player playerTwo, FlowerPokerRoom room) {
        FlowerPoker.playerOne = playerOne;
        FlowerPoker.playerTwo = playerTwo;
        flowerPokerRoom = room;
        playerOneBet = new ArrayList<>();
        playerTwoBet = new ArrayList<>();
        playerOneFlowers = new ArrayList<>();
        playerTwoFlowers = new ArrayList<>();
        playerOneFlowerObjects = new ArrayList<>();
        playerTwoFlowerObjects = new ArrayList<>();
        gambleLogger = new GambleLogger();
        playerOne.setFlowerPoker(this);
        playerTwo.setFlowerPoker(this);
        new GambleInterface(playerOne, playerTwo, "Flower Poker - Best combination wins");
    }

    public void startGame() {
        if (!Files.exists(Paths.get("data/gambling/" + playerOne.getName() + "_" + playerTwo.getName() + ".txt"))) {
            gambleLogger.createFile(playerOne, playerTwo);
        }
        playerOne.setInGambleParty(true);
        playerTwo.setInGambleParty(true);
        movePlayers();
        World.startEvent(gamebleEvent -> {
            gamebleEvent.delay(4);
            plant(this);
        });
    }

    private static void plant(FlowerPoker flowerPoker) {
        Player playerOne = flowerPoker.getPlayerOne();
        Player playerTwo = flowerPoker.getPlayerTwo();

        World.startEvent(gamebleEvent -> {
            playerOne.lock(LockType.FULL);
            playerTwo.lock(LockType.FULL);
            playerOne.forceText("Starting a FlowerPoker duel against " + playerTwo.getName() + ".");
            gamebleEvent.delay(4);

            do {
                if (flowerPokerRoom.getUsedSteps() + 5 > flowerPokerRoom.getTotalSteps() && (flowerPoker.isReplay()
                        || flowerPoker.isRestart())) {
                    gamebleEvent.delay(2);
                    removeFlowers();
                    movePlayers();
                    flowerPokerRoom.setUsedSteps(0);
                    gamebleEvent.delay(5);
                }

                flowerPoker.getPlayerOneFlowers().clear();
                flowerPoker.getPlayerTwoFlowers().clear();
                playerOneFlowerObjects.clear();
                playerTwoFlowerObjects.clear();
                flowerPoker.setReplay(false);
                flowerPoker.setRestart(false);

                for (int plantedFlowers = 0; plantedFlowers < 5; plantedFlowers++) {
                    GameObject playerOneFlower = plantFlower(flowerPoker, playerOne);
                    GameObject playerTwoFlower = plantFlower(flowerPoker, playerTwo);
                    playerOneFlowerObjects.add(playerOneFlower);
                    playerTwoFlowerObjects.add(playerTwoFlower);
                    gamebleEvent.delay(2);
                    if (flowerPokerRoom.getFaceDirection() == Direction.NORTH) {
                        playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(),
                                playerOne.getPosition().getY() + 1, false, true);
                        playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(),
                                playerTwo.getPosition().getY() + 1, false, true);
                        gamebleEvent.delay(3);
                    } else {
                        playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(),
                                playerOne.getPosition().getY() - 1, false, true);
                        playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(),
                                playerTwo.getPosition().getY() - 1, false, true);
                        gamebleEvent.delay(3);
                    }
                    flowerPokerRoom.increaseUsedSteps();

                    if (playerOneFlower.id == Flower.BLACK.getObjId() || playerTwoFlower.id == Flower.BLACK.getObjId()
                            || playerOneFlower.id == Flower.WHITE.getObjId() || playerTwoFlower.id == Flower.WHITE.getObjId()) {
                        flowerPoker.setRestart(true);
                        break;
                    }
                }


                if (flowerPoker.isRestart()) {
                    playerOne.forceText("A White or Black flower means RESTART !!");
                    playerTwo.sendMessage(Color.DARK_RED.wrap("A white or Black flower means RESTART !!"));
                    gambleLogger.appendToFile(playerOne, playerTwo, "The planted flower is a BLACK or WHITE flower. This means REPLAY.");
                    flowerPoker.setReplay(true);
                    gamebleEvent.delay(2);
                    if (flowerPokerRoom.getFaceDirection() == Direction.NORTH) {
                        playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(), playerOne.getPosition().getY() + 1, false, true);
                        playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(), playerTwo.getPosition().getY() + 1, false, true);
                        gamebleEvent.delay(3);
                    } else {
                        playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(), playerOne.getPosition().getY() - 1, false, true);
                        playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(), playerTwo.getPosition().getY() - 1, false, true);
                        gamebleEvent.delay(3);
                    }
                    flowerPokerRoom.increaseUsedSteps();
                } else {
                    sortFlowers(flowerPoker);
                    sortFlowers(flowerPoker);

                    int playerOneScore = calculateScore(flowerPoker.getPlayerOneFlowers());
                    int playerTwoScore = calculateScore(flowerPoker.getPlayerTwoFlowers());

                    if (playerOneScore == playerTwoScore) {
                        playerOne.forceText("The result is a Tie!! REPLAY !!");
                        playerTwo.sendMessage(Color.DARK_RED.wrap("The result is a Tie!! REPLAY !!"));
                        gambleLogger.appendToFile(playerOne, playerTwo, "Both players had the same score. Replaying !!");
                        gamebleEvent.delay(2);
//                        if (flowerPokerRoom.getFaceDirection() == Direction.NORTH) {
//                            playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(), playerOne.getPosition().getY() + 1, false, true);
//                            playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(), playerTwo.getPosition().getY() + 1, false, true);
//                            gamebleEvent.delay(3);
//                        } else {
//                            playerOne.getRouteFinder().routeAbsolute(playerOne.getPosition().getX(), playerOne.getPosition().getY() - 1, false, true);
//                            playerTwo.getRouteFinder().routeAbsolute(playerTwo.getPosition().getX(), playerTwo.getPosition().getY() - 1, false, true);
//                            gamebleEvent.delay(3);
//                        }
//                        flowerPokerRoom.increaseUsedSteps();
                        flowerPoker.setReplay(true);
                    } else {
                        if (playerOneScore > playerTwoScore) {
                            flowerPoker.setReplay(false);
                            sendResultMessage(playerOne, playerOneScore, playerTwo, playerTwoScore);
                            payOutWinner(playerOne);
                            showResultAnimation(playerOne, playerTwo);
                            gamebleEvent.delay(3);
                            showResultAnimation(playerOne, playerTwo);
                        } else {
                            flowerPoker.setReplay(false);
                            sendResultMessage(playerTwo, playerTwoScore, playerOne, playerOneScore);
                            payOutWinner(playerTwo);
                            showResultAnimation(playerTwo, playerOne);
                            gamebleEvent.delay(3);
                            showResultAnimation(playerTwo, playerOne);
                        }
                        removeFlowers();
                        playerOne.setInGambleParty(false);
                        playerTwo.setInGambleParty(false);
                        gamebleEvent.delay(2);
                        playerOne.setGambleRequestCooldown(0);
                        playerTwo.setGambleRequestCooldown(0);
                        playerOne.unlock();
                        playerTwo.unlock();
                        teleport(playerOne, GambleManager.getPlayerOneFinalPosition());
                        teleport(playerTwo, GambleManager.getPlayerTwoFinalPosition());
                        gamebleEvent.delay(2);

                        flowerPokerRoom.setUsed(false);
                        playerOne.gambleInterface = null;
                        playerTwo.gambleInterface = null;
                    }
                }
            } while (flowerPoker.isReplay());
        });
    }

    private static void removeFlowers() {
        playerOneFlowerObjects.forEach(GameObject::remove);
        playerTwoFlowerObjects.forEach(GameObject::remove);
    }

    private static void payOutWinner(Player winningPlayer) {
        for (Item item : playerOneBet) {
            if (item != null)
                winningPlayer.getInventory().addOrDrop(item.getId(), item.getAmount());
        }

        for (Item item : playerTwoBet) {
            if (item != null)
                winningPlayer.getInventory().addOrDrop(item.getId(), item.getAmount());
        }
    }

    private static void showResultAnimation(Player winner, Player loser) {
        winner.animate(2400);
        loser.animate(532);
    }

    private static void sendResultMessage(Player winner, int winnerScore, Player loser, int loserPoints) {
        winner.forceText("I won the flower poker duel!! I " + getResultHand(winnerScore));
        loser.forceText("I lost the flower poker duel!! I " + getResultHand(loserPoints));
        gambleLogger.appendToFile(playerOne, playerTwo, winner.getName() + " won the duel. " + winner.getName() + getResultHand(winnerScore) + " and " + loser.getName() + getResultHand(loserPoints));
    }

    private static GameObject plantFlower(FlowerPoker flowerPoker, Player player) {
        Flower flower = getRandomFlower();
        if (player == flowerPoker.getPlayerOne()) {
            flowerPoker.getPlayerOneFlowers().add(flower.getObjId());
        } else {
            flowerPoker.getPlayerTwoFlowers().add(flower.getObjId());
        }
        player.sendMessage("You plant the seed.");
        player.getInventory().remove(299, 1);
        GameObject obj = GameObject.spawn(flower.getObjId(), player.getAbsX(), player.getAbsY(), player.getHeight(), 10, 0);

        player.animate(2697);
        return obj;
    }

    static void startObjectRemovalEvent(GameObject obj, boolean forced) {
        if (forced) {
            obj.remove();
        } else {
            World.startEvent(worldEvent -> {
                worldEvent.delay(30);
                if (!obj.isRemoved())
                    obj.remove();
            });
        }
    }

    private static String getResultHand(int score) {
        return score == 6 ? "had a 5 Oak" : score == 5 ? "had a 4 Oak" : score == 4 ? "had a Full House" : score == 3 ?
                "had a 3 Oak" : score == 2 ? "had 2 Pair" : score == 1 ? "had 1 Pair" : "Busted";
    }

    private static void sortFlowers(FlowerPoker flowerPoker) {
        flowerPoker.getPlayerOneFlowers().sort(Comparator.reverseOrder());
        flowerPoker.getPlayerTwoFlowers().sort(Comparator.reverseOrder());
    }

    private static int calculateScore(List<Integer> flowers) {
        int blueFlowers = 0;
        int redFlowers = 0;
        int yellowFlowers = 0;
        int orangeFlowers = 0;
        int purpleFlowers = 0;
        int mixedFlowers = 0;
        int assortedFlowers = 0;

        for (Integer flower : flowers) {
            if (flower == Flower.BLUE.getObjId()) {
                blueFlowers++;
            } else if (flower == Flower.RED.getObjId()) {
                redFlowers++;
            } else if (flower == Flower.YELLOW.getObjId()) {
                yellowFlowers++;
            } else if (flower == Flower.ORANGE.getObjId()) {
                orangeFlowers++;
            } else if (flower == Flower.PURPLE.getObjId()) {
                purpleFlowers++;
            } else if (flower == Flower.MIXED.getObjId()) {
                mixedFlowers++;
            } else if (flower == Flower.ASSORTED.getObjId()) {
                assortedFlowers++;
            }
        }

        if (blueFlowers == 5 || redFlowers == 5 || yellowFlowers == 5 || orangeFlowers == 5 || purpleFlowers == 5
                || mixedFlowers == 5 || assortedFlowers == 5) {
            return 6;
        } else if (blueFlowers == 4 || redFlowers == 4 || yellowFlowers == 4 || orangeFlowers == 4 || purpleFlowers == 4
                || mixedFlowers == 4 || assortedFlowers == 4) {
            return 5;
        } else {
            boolean pairNoRed = blueFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2 || purpleFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2;
            boolean pairNoYewllow = blueFlowers == 2 || redFlowers == 2 || orangeFlowers == 2 || purpleFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2;
            boolean pairNoOrange = blueFlowers == 2 || yellowFlowers == 2 || redFlowers == 2 || purpleFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2;
            boolean pairNoPurple = blueFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2 || redFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2;
            boolean pairNoMixed = blueFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2 || purpleFlowers == 2 || redFlowers == 2 || assortedFlowers == 2;
            boolean pairNoAssorted = blueFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2 || purpleFlowers == 2 || mixedFlowers == 2 || redFlowers == 2;
            boolean pairNoBlue = redFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2 || purpleFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2;

            if ((blueFlowers == 3 && pairNoBlue) || (redFlowers == 3 && pairNoRed) || (yellowFlowers == 3 && pairNoYewllow)
                    || (orangeFlowers == 3 && pairNoOrange) || (purpleFlowers == 3 && pairNoPurple)
                    || (mixedFlowers == 3 && pairNoMixed) || (assortedFlowers == 3 && pairNoAssorted)) {
                return 4;
            } else if (blueFlowers == 3 || redFlowers == 3 || yellowFlowers == 3 || orangeFlowers == 3
                    || purpleFlowers == 3 || mixedFlowers == 3 || assortedFlowers == 3) {
                return 3;
            } else if ((blueFlowers == 2 && pairNoBlue) || (redFlowers == 2 && pairNoRed) || (yellowFlowers == 2 && pairNoYewllow)
                    || (orangeFlowers == 2 && pairNoOrange) || (purpleFlowers == 2 && pairNoPurple)
                    || (mixedFlowers == 2 && pairNoMixed) || (assortedFlowers == 2 && pairNoAssorted)) {
                return 2;
            } else if (blueFlowers == 2 || redFlowers == 2 || yellowFlowers == 2 || orangeFlowers == 2
                    || purpleFlowers == 2 || mixedFlowers == 2 || assortedFlowers == 2) {
                return 1;
            }
        }
        return 0;
    }


    static Flower getRandomFlower() {
        int random = Random.get(1, 7050);
        if (random > 7000) {
            return Random.get(1, 2) == 1 ? Flower.BLACK : Flower.WHITE;
        } else {
            if (random > 6000) {
                return Flower.ORANGE;
            } else if (random > 5000) {
                return Flower.PURPLE;
            } else if (random > 4000) {
                return Flower.YELLOW;
            } else if (random > 3000) {
                return Flower.RED;
            } else if (random > 2000) {
                return Flower.MIXED;
            } else if (random > 1000) {
                return Flower.ASSORTED;
            } else {
                return Flower.BLUE;
            }
        }
    }

    public static void setPlayerOneBet(final List<Item> playerOneBet) {
        FlowerPoker.playerOneBet = playerOneBet;
    }

    public static void setPlayerTwoBet(final List<Item> playerTwoBet) {
        FlowerPoker.playerTwoBet = playerTwoBet;
    }

    private static void movePlayers() {
        teleport(playerOne, flowerPokerRoom.getPlayerOnePosition());
        teleport(playerTwo, flowerPokerRoom.getPlayerTwoPosition());
    }

    public List<Integer> getPlayerOneFlowers() {
        return playerOneFlowers;
    }

    public List<Integer> getPlayerTwoFlowers() {
        return playerTwoFlowers;
    }

    private static void teleport(Player player, Position newPosition) {
        player.getMovement().startTeleport(event -> {
            player.animate(3864);
            player.graphics(1039);
            player.privateSound(200, 0, 10);
            event.delay(2);
            player.face(flowerPokerRoom.getFaceDirection());
            player.getMovement().teleport(newPosition);
        });
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public List<Item> getPlayerOneBet() {
        return playerOneBet;
    }

    public List<Item> getPlayerTwoBet() {
        return playerTwoBet;
    }

    public void setPlayerOneFlowers(List<Integer> playerOneFlowers) {
        this.playerOneFlowers = playerOneFlowers;
    }

    public void setPlayerTwoFlowers(List<Integer> playerTwoFlowers) {
        this.playerTwoFlowers = playerTwoFlowers;
    }

    public boolean isReplay() {
        return replay;
    }

    public void setReplay(boolean replay) {
        this.replay = replay;
    }

    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }
}
