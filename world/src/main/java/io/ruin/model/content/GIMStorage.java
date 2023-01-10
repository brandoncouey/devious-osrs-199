package io.ruin.model.content;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceAction;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.OptionAction;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.ItemContainerG;
import io.ruin.model.item.attributes.AttributeExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

import static io.ruin.model.inter.AccessMasks.*;

/**
 * @author Greco
 * @since 12/09/2021
 */
public class GIMStorage extends ItemContainerG<GIMItem> {

    public GroupIronman GIM;

    public static final int BLANK_ID = -1, FILLER_ID = 20594;

    public boolean asNote;

    public boolean sortSkip, sortRequired;

    public void open() {
        if (player.getGameMode().isUltimateIronman()) {
            player.sendMessage("Ultimate ironmen cannot access the bank.");
            return;
        }
        if (player.getBankPin().requiresVerification(p -> open()))
            return;

        player.openInterface(InterfaceType.MAIN, 724);
        //    player.openInterface(InterfaceType.INVENTORY, 725);
        player.openInterface(InterfaceType.INVENTORY, Interface.BANK_INVENTORY);

        player.getPacketSender().sendClientScript(101, "i", 11);
        player.getPacketSender().sendClientScript(3407, "i", 983047);
        player.getPacketSender().sendClientScript(5474, "iiiiHlc", 50597019, 9471, 10, 2, 15, 16765184, 0);
        player.getPacketSender().sendClientScript(4217, "siiiiiiigII", "Loading group storage...", 3, 0, 1024, 512, 3000, 1000, 10, 3787, 19202049, 19202050);
        player.getPacketSender().sendClientScript(917, "ii", -1, -2);
        player.getPacketSender().sendAccessMask(724, 10, 0, 79, 1181182);
        //    player.getPacketSender().sendAccessMask(725, 0, 0, 27, 1180926);

        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 3, 0, 27, 1181694);
        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 10, 0, 27, ClickOp1, ClickOp2, ClickOp3, ClickOp4, ClickOp10);
        player.getPacketSender().sendAccessMask(Interface.BANK_INVENTORY, 13, 0, 27, ClickOp1);

        player.getPacketSender().sendClientScript(5474, "iiiiHlc", 50597019, 9471, 10, 2, 15, 16765184, 0);

        sendAll = true;
    }

    public void sendWornItemBonuses() {
        EquipmentStats.update(player, Interface.BANK, 89);
    }

    @Override
    public int add(int id, int amount, Map<String, String> attributes) {
        if (player.getGameMode().isUltimateIronman()) {
            return 0;
        }
        ItemDef def = ItemDef.get(id);
        if (def == null || def.isPlaceholder())
            return -1;

        if (!def.tradeable) {
            player.sendMessage("You can't store " + def.name + ", this item isn't tradeable!");
        } else {

            int freeSlot = -1;
            GIMItem blankItem = null;
            int hash = AttributeExtensions.hashAttributes(attributes);
            for (int slot = 0; slot < items.length; slot++) {
                GIMItem item = items[slot];
                if (item == null) {
                    if (freeSlot == -1)
                        freeSlot = slot;
                    continue;
                }
                if (hash == 0) {
                    if (item.getId() == id && !item.hasAttributes()) {
                        item.incrementAmount(amount);
                        return amount;
                    }
                }
            }
            if (freeSlot != -1) {
                set(freeSlot, new GIMItem(id, amount, attributes));
                sort();
                return amount;
            }
        }
        return 0;
    }

    @Override
    protected GIMItem newItem(int id, int amount, Map<String, String> attributes) {
        return new GIMItem(id, amount, attributes);
    }

    @Override
    protected GIMItem[] newArray(int size) {
        return new GIMItem[size];
    }


    /**
     * Sort
     */

    public void sort() {
        if (sortSkip)
            sortRequired = true;
        else
            doSort();
    }

    private void sortAfter(Runnable runnable) {
        sortSkip = true;
        runnable.run();
        sortSkip = false;
        if (sortRequired) {
            sortRequired = false;
            doSort();
        }
    }

    private void doSort() {
        ArrayList<GIMItem> list = new ArrayList<>();
        for (int slot = 0; slot < items.length; slot++) {
            GIMItem item = items[slot];
            if (item != null) {
                set(slot, null);
                list.add(item);
            }
        }
        list.sort((item1, item2) -> {
            int tab1 = 0;
            int tab2 = 0;
            if (tab1 == tab2) {
                int slot1 = item1.sortSlot == -1 ? item1.getSlot() : item1.sortSlot;
                int slot2 = item2.sortSlot == -1 ? item2.getSlot() : item2.sortSlot;
                return Integer.compare(slot1, slot2);
            }
            return Integer.compare(tab1, tab2);
        });
        int slot = 0;
        for (GIMItem item : list) {
            item.sortSlot = -1;
            set(slot++, item);
        }
        sendAll = true;
    }

    /**
     * Rearranging
     */

    private void rearrange(int fromSlot, int toSlot) {
        if (fromSlot == toSlot)
            return;
        GIMItem fromItem = getSafe(fromSlot);
        if (fromItem == null)
            return;
        GIMItem toItem = getSafe(toSlot);
        if (toItem == null)
            return;
        if (Config.BANK_INSERT_MODE.get(player) == 1) {
            if (fromItem.getSlot() > toItem.getSlot()) {
                /* insert left */
                fromItem.sortSlot = toItem.getSlot();
                toItem.sortSlot = toItem.getSlot() + 1;
            } else {
                /* insert right */
                fromItem.sortSlot = toItem.getSlot();
                toItem.sortSlot = toItem.getSlot() - 1;
            }
            sort();
        }
        GIMRepository.save();
    }

    /**
     * Depositing
     */

    public int deposit(Item item, int amount, boolean message) {
        ItemDef def = item.getDef();
        int moved = item.move(def.isNote() ? def.notedId : def.id, amount, this);
        if (moved == 0 && message) {
            player.sendMessage("You don't have enough space in your group ironman storage.");
        }
        GIMRepository.save();

        return moved;
    }

    public void deposit(ItemContainer container, boolean message) {
        if (container.isEmpty()) {
            if (message)
                player.sendMessage("You have nothing to deposit.");
            return;
        }
        sortAfter(() -> {
            boolean deposited = false;
            for (Item item : container.getItems()) {
                if (item != null && deposit(item, item.getAmount(), false) > 0) {
                    deposited = true;
                    GIMRepository.save();
                }
                if (!deposited && message)
                    player.sendMessage("Your group ironman storage cannot hold your items.");
            }
        });
    }

    /**
     * Withdrawing
     */

    private void withdraw(@NotNull GIMItem item, int amount) {
        if (item.getId() == BLANK_ID)
            return;
        ItemDef def = item.getDef();
        if (amount == -1) {
            amount = item.getAmount();
        }
        int itemId = item.getId();

        if (!def.tradeable) {
            player.sendMessage("You can't deposit this item to the group ironman storage.");
        } else {
            if (asNote) {
                if (def.notedId != -1)
                    itemId = def.notedId;
                else
                    player.sendMessage("This item cannot be withdrawn as a note.");
            }
            int moved = item.getDef().bankWithdrawListener(player, item, amount);
            moved += item.move(itemId, amount - moved, player.getInventory());
            if (moved <= 0) {
                player.sendMessage("You don't have enough inventory space.");
                return;
            }
            //if (moved < amount) {
            /* kinda pointless but rs does it so hey */
            //    player.sendMessage("You don't have enough inventory space to withdraw that many.");
            //   }

            if (item.getAmount() <= 0) {
                doSort();
            }
        }
        GIMRepository.save();
    }


    public boolean sendUpdates() {
        return sendUpdates(null);
    }


    static {

        /* inventory */
        InterfaceHandler.register(725, h -> {

        });

        /* gim storage interface */
        InterfaceHandler.register(724, h -> {

            h.actions[9] = (DefaultAction) (player, option, slot, itemId) -> player.getBank().open();


            h.actions[12] = (DefaultAction) (player, option, slot, itemId) -> player.getGIMStorage().deposit(player.getEquipment(), true);

            h.actions[16] = (DefaultAction) (player, option, slot, itemId) -> player.getGIMStorage().deposit(player.getInventory(), true);

            h.action(10, new InterfaceAction() {
                @Override
                public void handleClick(Player player, int option, int slot, int itemId) {
                    GIMItem item = player.getGIMStorage().get(slot, itemId);
                    if (item == null)
                        return;
                    if (option == 1) {
                        switch (Config.BANK_DEFAULT_QUANTITY.get(player)) {
                            case 0:
                                player.getGIMStorage().withdraw(item, 1);
                                break;
                            case 1:
                                player.getGIMStorage().withdraw(item, 5);
                                break;
                            case 2:
                                player.getGIMStorage().withdraw(item, 10);
                                break;
                            case 3:
                                if (Config.BANK_LAST_X.get(player) > 0) {
                                    player.getGIMStorage().withdraw(item, Config.BANK_LAST_X.get(player));
                                } else {
                                    player.integerInput("Enter amount:", amount -> {
                                        player.getGIMStorage().withdraw(item, amount);
                                        Config.BANK_LAST_X.set(player, amount);
                                    });
                                }
                                break;
                            case 4:
                                player.getGIMStorage().withdraw(item, Integer.MAX_VALUE);
                                break;
                            default:
                                player.getGIMStorage().withdraw(item, 1);
                                break;
                        }
                        return;
                    }
                    if (option == 2) {
                        player.getGIMStorage().withdraw(item, 1);
                        return;
                    }
                    if (option == 3) {
                        player.getGIMStorage().withdraw(item, 5);
                        return;
                    }
                    if (option == 4) {
                        player.getGIMStorage().withdraw(item, 10);
                        return;
                    }
                    if (option == 5) {
                        player.getGIMStorage().withdraw(item, Config.BANK_LAST_X.get(player));
                        return;
                    }
                    if (option == 6) {
                        player.integerInput("Enter amount:", amount -> {
                            player.getGIMStorage().withdraw(item, amount);
                            Config.BANK_LAST_X.set(player, amount);
                        });
                        return;
                    }
                    if (option == 7) {
                        player.getGIMStorage().withdraw(item, item.getAmount());
                        return;
                    }
                    if (option == 8) {
                        player.getGIMStorage().withdraw(item, item.getAmount() - 1);
                        return;
                    }
                    if (option == 9) {
                        player.getGIMStorage().withdraw(item, -1);
                        return;
                    }
                    item.examine(player);
                }
            });

            h.simpleAction(21, p -> Config.BANK_INSERT_MODE.set(p, 0));
            h.simpleAction(23, p -> Config.BANK_INSERT_MODE.set(p, 1));
            h.simpleAction(27, p -> p.getGIMStorage().asNote = false);
            h.simpleAction(29, p -> p.getGIMStorage().asNote = true);
            h.simpleAction(35, p -> Config.BANK_DEFAULT_QUANTITY.set(p, 0));
            h.simpleAction(37, p -> Config.BANK_DEFAULT_QUANTITY.set(p, 1));
            h.simpleAction(39, p -> Config.BANK_DEFAULT_QUANTITY.set(p, 2));
            h.actions[41] = (OptionAction) (p, option) -> {
                switch (option) {
                    case 1:
                        Config.BANK_DEFAULT_QUANTITY.set(p, 3);
                        if (Config.BANK_LAST_X.get(p) <= 0) {
                            p.integerInput("Enter amount:", amount -> Config.BANK_LAST_X.set(p, amount));
                        }
                        break;
                    case 2:
                        p.integerInput("Enter amount:", amount -> Config.BANK_LAST_X.set(p, amount));
                        break;
                }
            };
            h.simpleAction(43, p -> Config.BANK_DEFAULT_QUANTITY.set(p, 4));

        });
    }


}
