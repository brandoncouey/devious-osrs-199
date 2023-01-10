package io.ruin.model.item.containers;

import io.ruin.cache.ItemDef;
import io.ruin.model.activities.gambling.FlowerPoker;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.item.Item;

import java.util.Arrays;

public class GambleInterface {

    private static Inventory playerOneOfferedItems;
    private static Inventory playerTwoOfferedItems;
    private static Player playerOne;
    private static Player playerTwo;
    private final String gameType;
    private int stage;
    private boolean playerOneAccepted;
    private boolean playerTwoAccepted;

    public GambleInterface(Player playerOne, Player playerTwo, final String gameTypeTitle) {
        playerOneOfferedItems = new Inventory();
        playerTwoOfferedItems = new Inventory();
        playerOneOfferedItems.init(28, true);
        playerTwoOfferedItems.init(28, true);
        GambleInterface.playerOne = playerOne;
        GambleInterface.playerTwo = playerTwo;
        playerOneAccepted = false;
        playerTwoAccepted = false;
        this.gameType = gameTypeTitle;
        playerOne.gambleInterface = this;
        playerTwo.gambleInterface = this;
        open(playerOne);
        open(playerTwo);
        stage = 1;
    }

    public void open(Player player) {
        player.openInterface(InterfaceType.MAIN, Interface.GAMBLE_INTERFACE);
        player.openInterface(InterfaceType.INVENTORY, Interface.GAMBLE_INVENTORY);
        playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=b31a1a>You have not accepted the bet yet.</col>");
        playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerTwo.getName()
                + " <col=b31a1a>has not accepted the bet yet.");
        playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=b31a1a>You have not accepted the bet yet.");
        playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerOne.getName()
                + " <col=b31a1a>has not accepted the bet yet.");
        player.getPacketSender().sendItems(10002, playerOneOfferedItems.getItems());
        player.getPacketSender().sendItems(10003, playerTwoOfferedItems.getItems());
        player.getPacketSender().sendItems(10005, player.getInventory().getItems());

        player.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 14, gameType);
        player.getPacketSender().sendAccessMask(Interface.GAMBLE_INVENTORY, 0, 0,
                27, 1181694);
        player.getPacketSender().sendAccessMask(Interface.GAMBLE_INTERFACE, 56, 0, 27, 1181694);
        player.setGambleMonitorOpen(true);
    }

    // red = b31a1a
    // green = 35960f

    private void changePlayerAcceptedState(final Player player) {
        if (isPlayerOne(player) && !isPlayerOneAccepted()) {
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=35960f>You have accepted the bet.</col>");
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerOne.getName()
                    + " <col=35960f>has accepted the bet.");
            setPlayerOneAccepted(true);
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 20, "Decline");
        } else if (isPlayerOne(player) && isPlayerOneAccepted()) {
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=b31a1a>You have not accepted the bet yet.</col>");
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerOne.getName()
                    + " <col=b31a1a>has not accepted the bet yet.");
            setPlayerOneAccepted(false);
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 20, "Accept");
        } else if (!isPlayerOne(player) && !isPlayerTwoAccepted()) {
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=35960f>You have accepted the bet.</col>");
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerTwo.getName()
                    + " <col=35960f>has accepted the bet.");
            setPlayerTwoAccepted(true);
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 20, "Decline");
        } else if (!isPlayerOne(player) && isPlayerTwoAccepted()) {
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 22, "<col=b31a1a>You have not accepted the bet yet.</col>");
            playerOne.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 23, "<col=ffffff>" + playerTwo.getName()
                    + " <col=b31a1a>has not accepted the bet yet.");
            setPlayerTwoAccepted(false);
            playerTwo.getPacketSender().sendString(Interface.GAMBLE_INTERFACE, 20, "Accept");
        }

        if (playerOneAccepted && playerTwoAccepted) {
            if (playerOne.getFlowerPoker() != null && playerTwo.getFlowerPoker() != null
                    && playerTwo == playerOne.getFlowerPoker().getPlayerTwo()) {
                stage = 2;
                FlowerPoker flowerPoker = player.getFlowerPoker();
                FlowerPoker.setPlayerOneBet(Arrays.asList(playerOneOfferedItems.getItems()));
                FlowerPoker.setPlayerTwoBet(Arrays.asList(playerTwoOfferedItems.getItems()));

                for (Item item : flowerPoker.getPlayerOneBet()) {
                    if (item != null) {
                        playerOne.unlock();
                        playerOne.getInventory().remove(item);
                        playerOne.lock(LockType.FULL);//try
                    }
                }

                for (Item item : flowerPoker.getPlayerTwoBet()) {
                    if (item != null) {
                        playerTwo.unlock();
                        playerTwo.getInventory().remove(item);
                        playerTwo.lock(LockType.FULL);
                    }
                }

                flowerPoker.startGame();
            }
        }
    }

    public static void disbandAndRefund() {

        System.out.println("Stat list Init STACK TRACE: " + Thread.currentThread().getStackTrace()[2].getMethodName());
        playerOne.setInGambleParty(false);
        playerTwo.setInGambleParty(false);
        playerOne.sendMessage("The battle has been canceled.");//This is getting called, which addback the items, and its only in 2 spots
        playerTwo.sendMessage("The battle has been canceled.");
        for (Item item : playerOneOfferedItems.getItems()) {
            if (item != null) {
                playerOne.getInventory().add(item);
            }
        }
        for (Item item : playerTwoOfferedItems.getItems()) {
            if (item != null) {
                playerTwo.getInventory().add(item);
            }
        }
        playerOne.gambleInterface = null;
        playerTwo.gambleInterface = null;
        playerOne.closeInterfaces();
        playerTwo.closeInterfaces();
    }

    public void closeInterfaces(boolean disbandAndRefund) {
        playerOne.closeInterface(InterfaceType.MAIN);
        playerOne.closeInterface(InterfaceType.INVENTORY);
        playerTwo.closeInterface(InterfaceType.MAIN);
        playerTwo.closeInterface(InterfaceType.INVENTORY);
        playerOne.setGambleMonitorOpen(false);
        playerTwo.setGambleMonitorOpen(false);
        if (disbandAndRefund)
            disbandAndRefund();
    }

    private static boolean isPlayerOne(final Player player) {
        return player == playerOne;
    }

    private static void remove(final Player player, final int amount, final int slot, final int itemId) {
        Inventory inventory = isPlayerOne(player) ? playerOneOfferedItems : playerTwoOfferedItems;

        if (slot >= 0 && slot <= 27) {
            int amountToBeRemoved = amount;

            for (int index = 0; index <= 27; index++) {
                Item currentItem = inventory.getItems()[index];

                if (null != currentItem && currentItem.getId() == itemId) {
                    int possibleToRemove = currentItem.getAmount();

                    if (possibleToRemove > amount) {
                        possibleToRemove = amount;
                    }

                    Inventory playerInventory = player.getInventory();
                    ItemDef itemDef = ItemDef.get(itemId);
                    if (itemDef != null && itemDef.stackable) {
                        if (playerInventory.contains(itemId)) {
                            int inventorySlot = playerInventory.getSlot(itemId);
                            int amountInInventoryForSlot = playerInventory.get(inventorySlot).getAmount();

                            if (amountInInventoryForSlot + possibleToRemove < Integer.MAX_VALUE) {
                                playerInventory.add(currentItem.getId(), possibleToRemove);
                                amountToBeRemoved -= possibleToRemove;
                            } else {
                                possibleToRemove = Integer.MAX_VALUE - amountInInventoryForSlot;
                                playerInventory.add(currentItem.getId(), possibleToRemove);
                                amountToBeRemoved -= possibleToRemove;
                            }
                        } else if (playerInventory.getFreeSlots() >= 1) {
                            playerInventory.add(currentItem.getId(), possibleToRemove);
                            amountToBeRemoved -= possibleToRemove;
                        } else {
                            player.sendMessage("You don't have any free inventory space.");
                            return;
                        }
                    } else {
                        int freeSlots = playerInventory.getFreeSlots();
                        if (freeSlots < possibleToRemove) {
                            possibleToRemove = freeSlots;

                            if (possibleToRemove == 0) {
                                player.sendMessage("You don't have any free inventory space.");
                                return;
                            }
                        }
                        amountToBeRemoved -= possibleToRemove;
                        playerInventory.add(currentItem.getId(), possibleToRemove);
                    }

                    if (inventory.getItems()[index].getAmount() - possibleToRemove <= 0) {
                        inventory.getItems()[index] = null;
                    } else {
                        inventory.getItems()[index].setAmount(inventory.getItems()[index].getAmount() - possibleToRemove);
                    }
                }
                if (amountToBeRemoved == 0) {
                    break;
                }
            }
        }
        sortOfferedItems();
        player.getPacketSender().sendItems(10005, player.getInventory().getItems());
    }

    private static void add(final Player player, int itemId, int amount) {
        Inventory playerInventory = player.getInventory();
        int amountInserted;
        int amountInInventory = playerInventory.getAmount(itemId);

        if (amountInInventory < amount) {
            amount = amountInInventory;
        }

        if (isPlayerOne(player)) {
            amountInserted = addItemToPlayerGambleItems(playerOneOfferedItems.getItems(), itemId, amount);
        } else {
            amountInserted = addItemToPlayerGambleItems(playerTwoOfferedItems.getItems(), itemId, amount);
        }

        if (amountInserted > 0) {
            playerInventory.remove(itemId, amountInserted);
            updateInterface(playerOneOfferedItems.getItems(), playerTwoOfferedItems.getItems());
        }
        player.getPacketSender().sendItems(10005, player.getInventory().getItems());
    }

    private static int addItemToPlayerGambleItems(Item[] items, final int itemId, int amount) {
        int amountFinished = 0;

        for (int index = 0; index < 28; index++) {

            ItemDef itemDef = ItemDef.get(itemId);
            if (itemDef != null && itemDef.stackable) {
                if (items[index] == null) {
                    items[index] = new Item(itemId, amount);
                    return amount;
                } else if (items[index].getId() == itemId) {
                    int newTotal = items[index].getAmount() + amount;
                    items[index] = new Item(itemId, newTotal);
                    return amount;
                }
            } else {
                if (items[index] == null) {
                    items[index] = new Item(itemId, 1);
                    amountFinished++;
                    amount--;
                }
            }

            if (amount <= 0) {
                return amountFinished;
            }
        }
        return amountFinished;
    }

    private static void sortOfferedItems() {
        Item[] filteredItemsPlayerOne = new Item[28];
        Item[] filteredItemsPlayerTwo = new Item[28];
        int freeIndex = 0;

        for (int index = 0; index <= 27; index++) {
            Item item = playerOneOfferedItems.getItems()[index];
            if (item != null) {
                filteredItemsPlayerOne[freeIndex] = item;
                freeIndex++;
            }
        }

        freeIndex = 0;
        for (int index = 0; index <= 27; index++) {
            Item item = playerTwoOfferedItems.getItems()[index];
            if (item != null) {
                filteredItemsPlayerTwo[freeIndex] = item;
                freeIndex++;
            }
        }

        for (int index = 0; index <= 27; index++) {
            Item item = filteredItemsPlayerOne[index];
            playerOneOfferedItems.getItems()[index] = item;
        }

        for (int index = 0; index <= 27; index++) {
            Item item = filteredItemsPlayerTwo[index];
            playerTwoOfferedItems.getItems()[index] = item;
        }

        updateInterface(playerOneOfferedItems.getItems(), playerTwoOfferedItems.getItems());
    }

    private static void updateInterface(Item[] playerOneOfferedItems, Item[] playerTwoOfferedItems) {
        playerOne.getPacketSender().sendItems(10002, playerOneOfferedItems);
        playerOne.getPacketSender().sendItems(10003, playerTwoOfferedItems);

        playerTwo.getPacketSender().sendItems(10002, playerTwoOfferedItems);
        playerTwo.getPacketSender().sendItems(10003, playerOneOfferedItems);
    }

    public boolean isPlayerOneAccepted() {
        return playerOneAccepted;
    }

    public boolean isPlayerTwoAccepted() {
        return playerTwoAccepted;
    }

    public void setPlayerOneAccepted(final boolean playerOneAccepted) {
        this.playerOneAccepted = playerOneAccepted;
    }

    public void setPlayerTwoAccepted(final boolean playerTwoAccepted) {
        this.playerTwoAccepted = playerTwoAccepted;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    static {
        InterfaceHandler.register(Interface.GAMBLE_INVENTORY, h ->
                h.actions[0] = (GambleAction) (p, option, slot, itemId) -> {
                    if (option == 1) { //1
                        add(p, itemId, 1);
                    } else if (option == 2) { //5
                        add(p, itemId, 5);
                    } else if (option == 3) { //10
                        add(p, itemId, 10);
                    } else if (option == 4) { //X
                        p.integerInput("Enter amount:", amount -> add(p, itemId, amount));
                    } else if (option == 5) { //all
                        add(p, itemId, Integer.MAX_VALUE);
                    }
                }
        );
        InterfaceHandler.register(Interface.GAMBLE_INTERFACE, h -> {
            h.actions[56] = (GambleAction) (p, option, slot, itemId) -> {
                if (option == 1) { //1
                    remove(p, 1, slot, itemId);
                } else if (option == 2) { //5
                    remove(p, 5, slot, itemId);
                } else if (option == 3) { //10
                    remove(p, 10, slot, itemId);
                } else if (option == 4) { //X
                    p.integerInput("Enter amount:", amount -> remove(p, amount, slot, itemId));
                } else if (option == 5) { //examine
                    remove(p, Integer.MAX_VALUE, slot, itemId);
                }
            };
            h.actions[13] = (SimpleGambleAction) p -> {
                p.gambleInterface.closeInterfaces(true);//If this button is the accept button then lmao
                System.out.println("Pressing from interface");
            };
            h.actions[20] = (GambleAction) (p, option, slot, itemId) -> {
                if (option == 1) {
                    p.gambleInterface.changePlayerAcceptedState(p);
                }
            };
        });
    }

    private interface GambleAction extends InterfaceAction {

        void handle(Player player, int option, int slot, int itemId);

        default void handleClick(Player player, int option, int slot, int itemId) {
            handle(player, option, slot, itemId);
        }
    }

    private interface SimpleGambleAction extends InterfaceAction {

        void handle(Player player);

        default void handleClick(Player player, int option, int slot, int itemId) {
            handle(player);
        }
    }

}
