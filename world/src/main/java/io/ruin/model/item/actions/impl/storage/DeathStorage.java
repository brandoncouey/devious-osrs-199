package io.ruin.model.item.actions.impl.storage;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.model.combat.Killer;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.IKOD;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.inter.utils.Unlock;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.utility.Utils;

import static io.ruin.cache.ItemID.BLOOD_MONEY;
import static io.ruin.cache.ItemID.COINS_995;

public class DeathStorage extends ItemContainer {

    static {
        // death storage chest -- maybe find something that fits the area better?
        ObjectAction.register(31675, 1, (p, obj) -> {
            if (p.getDeathStorage().isEmpty()) {
                p.sendMessage("The chest is currently empty. Should you die, you may retrieve your lost items from it.");
            } else {
                p.getDeathStorage().open();
                p.getPacketSender().resetHintIcon(false);
            }
        });
    }

    static {
        //Deaths domain NPC DEATH
        NPCAction.register(9855, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "I've collected your items would you like to buy them back?"),
                    new OptionsDialogue(
                            new Option("Yes, please.", () -> {
                                player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.SPEAK_TO_DEATH);
                                player.getDeathStorage().open();
                            }),
                            new Option("No thanks.", () -> {
                            })
                    ));
        });
        NPCAction.register(9855, "collect", (player, npc) -> {
            player.getDeathStorage().open();
        });
    }

    static {
        InterfaceHandler.register(Interface.DEATH_STORAGE, h -> {
            h.actions[3] = (DefaultAction) (p, option, slot, itemId) -> {
                if (option == 2)
                    p.getDeathStorage().take(slot);
                else if (option == 9)
                    p.getDeathStorage().value(slot);
                else
                    p.getDeathStorage().examine(slot);
            };
            h.actions[6] = (SimpleAction) p -> {
                if (p.getDeathStorage().isUnlocked())
                    p.getDeathStorage().takeAll();
                else
                    p.getDeathStorage().unlock();
            };
            h.actions[8] = (SimpleAction) p -> p.getDeathStorage().discardAll();
            h.closedAction = (p, integer) -> {
                if (p.getDeathStorage().isUnlocked() && !p.getDeathStorage().isEmpty()) {
                    p.sendMessage(Color.RED.wrap("WARNING:") + " Should you die again, all items currently in death storage will be gone forever!");
                }
            };
        });

        ObjectAction.register(39550, "sacrifice", (player, obj) -> {

            player.dialogue(new MessageDialogue("You currently have " + Utils.formatMoneyString(Config.SACRIFICE_ITEM_PRICE.get(player)) + " coins stored!"),
                    new OptionsDialogue("Would you like to deposit more coins?",
                            new Option("Yes!", () -> {
                                player.integerInput("How much would you like to deposit?", amount -> {
                                    if (Config.SACRIFICE_ITEM_PRICE.get(player) + amount > 2_147_483_000) {
                                        player.sendMessage(Color.RED, "[DEATH COFFER] You can't deposit that amount!");
                                    } else {
                                        if (player.getInventory().count(995) < amount) {
                                            int coins = player.getInventory().count(995);
                                            Config.SACRIFICE_ITEM_PRICE.increment(player, coins);
                                            player.getInventory().remove(995, coins);
                                            player.sendMessage(Color.BABY_BLUE, "[DEATH COFFER] You have deposited all of your coins!");
                                        } else {
                                            Config.SACRIFICE_ITEM_PRICE.increment(player, amount);
                                            player.getInventory().remove(995, amount);
                                            player.sendMessage(Color.BABY_BLUE, "[DEATH COFFER] You have deposited " + Utils.formatMoneyString(amount) + " coins, to your deaths coffer!");
                                        }
                                    }
                                });
                            }), new Option("No!")));
        });
    }


    @Expose
    private boolean unlocked = false;

    private void discardAll() {
        player.dialogue(new MessageDialogue("Are you sure you want to discard all items?<br><br>They will be lost forever!"),
                new OptionsDialogue("Discard all items?",
                        new Option("Yes", () -> {
                            reset();
                            player.closeInterface(InterfaceType.MAIN);
                            player.sendMessage("The storage has been cleared.");
                        }),
                        new Option("No", this::open)
                )
        );
    }

    private void take(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        if (!unlocked && item.getId() != BLOOD_MONEY && item.getId() != COINS_995) {
            player.sendMessage("You must first unlock your items. Click the padlock icon to pay the fee.");
            return;
        }
        if (item.move(item.getId(), item.getAmount(), player.getInventory()) == 0)
            player.sendMessage("Not enough space in your inventory.");
        sendUpdates();
    }

    private void value(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        player.sendMessage(item.getDef().name + ": " + Utils.formatMoneyString(item.getDef().value * item.getAmount()) + " coins.");
    }

    private void examine(int slot) {
        Item item = get(slot);
        if (item == null)
            return;
        item.examine(player);
    }

    private void unlock() {
        if (unlocked)
            return;
        /*
         * Donator Benefit: [Free death chest recovery]
         */
        if (player.isRedTopazDonator()) {
            unlocked = true;
            update();
            player.sendMessage("Your donator rank unlocks the death chest.");
            return;
        }

        int cost = getUnlockCost();
        if (cost == 0) {
            unlocked = true;
            update();
            return;
        }
        if (Config.SACRIFICE_ITEM_PRICE.get(player) < cost) {
            player.sendMessage("You do not have enough funds in your coffer to pay the balance!");
            return;
        }
        Config.SACRIFICE_ITEM_PRICE.increment(player, -cost);
        unlocked = true;
        update();
        player.sendMessage("You may now collect your items.");
    }

    private void takeAll() {
        for (Item item : getItems()) {
            if (item != null) {
                if (item.move(item.getId(), item.getAmount(), player.getInventory()) != item.getAmount()) {
                    break;
                }
            }
        }
        sendUpdates();
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void open() {
        send(player);
        player.openInterface(InterfaceType.MAIN, Interface.DEATH_STORAGE);
        update();
    }

    private void update() {
        Config.DEATH_STORAGE_TYPE.set(player, unlocked ? 4 : 3);
        new Unlock(602, 3).children(0, 100).unlockMultiple(player, 1, 8, 9);
        int cost = getUnlockCost();
        if (!unlocked && cost != -1) {
            player.addEvent(event -> {
                event.delay(1);
                player.getPacketSender().sendString(602, 11, "Fee to unlock:<br><col=ffffff>" + Utils.formatMoneyString(cost));
            });
        }
    }

    private int getUnlockCost() {
        return 0;
    }

    public void reset() {
        clear();
        unlocked = getUnlockCost() == 0;
    }

    public void death(Killer killer) {
        reset();
        IKOD.forLostItem(player, killer, this::add);
        if (!isEmpty()) {
            //   player.getPacketSender().sendHintIcon(3090, 3492); //edge home
        }
    }
}
