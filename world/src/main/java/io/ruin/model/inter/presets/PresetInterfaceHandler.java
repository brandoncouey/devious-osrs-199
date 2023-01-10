package io.ruin.model.inter.presets;

import io.ruin.cache.Color;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.SpellBook;

public class PresetInterfaceHandler {
    static {
        InterfaceHandler.register(1019, h -> {
            h.actions[124] = (DefaultAction) (player, option, slot, itemId) -> {
                player.openDialogue(false,
                        new OptionsDialogue(
                                new Option("Set my current equipment to this preset", () -> {
                                    Preset currentPreset = player.getPresetManager()
                                            .getPreset(player.getPresetManager().getSelectedPreset());
                                    if (currentPreset.isLocked()) {
                                        player.openDialogue(false,
                                                new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                                        + Color.RED.wrap("locked") + " and can't be modified right now."));
                                        return;
                                    }
                                    updatePresetEquipment(player, currentPreset);
                                    updatePresetView(player);
                                }),
                                new Option("Set my current inventory to this preset", () -> {
                                    Preset currentPreset = player.getPresetManager()
                                            .getPreset(player.getPresetManager().getSelectedPreset());
                                    if (currentPreset.isLocked()) {
                                        player.openDialogue(false,
                                                new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                                        + Color.RED.wrap("locked") + " and can't be modified right now."));
                                        return;
                                    }
                                    updatePresetInventory(player, currentPreset);
                                    updatePresetView(player);
                                }),
                                new Option("Set my current equipment and inventory to this preset", () -> {
                                    Preset currentPreset = player.getPresetManager()
                                            .getPreset(player.getPresetManager().getSelectedPreset());
                                    if (currentPreset.isLocked()) {
                                        player.openDialogue(false,
                                                new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                                        + Color.RED.wrap("locked") + " and can't be modified right now."));
                                        return;
                                    }
//                                    updatePresetStats(player, currentPreset);
                                    updatePresetInventory(player, currentPreset);
                                    updatePresetEquipment(player, currentPreset);
                                    updatePresetView(player);
                                }),
                                new Option("Change the name of this preset", plr -> {//Not sure how to do yet... Need player textinput
                                    player.stringInput("What would you like to call this preset?", name -> {
                                        Preset currentPreset = player.getPresetManager()
                                                .getPreset(player.getPresetManager().getSelectedPreset());
                                        if (currentPreset.isLocked()) {
                                            player.openDialogue(false,
                                                    new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                                            + Color.RED.wrap("locked") + " and can't be modified right now."));
                                            return;
                                        }
                                        updatePresetName(player, name);
                                        updatePresetView(player);
                                    });
                                })
                        )
                );
            };
//            for (int child = 129; child <= 153; child += 4) {
//                int finalChild = child;
//                h.actions[child] = (DefaultAction) (player, option, slot, itemId) -> {
//                    Preset currentPreset = player.getPresetManager()
//                            .getPreset(player.getPresetManager().getSelectedPreset());
//                    if (currentPreset != null) {
//                        player.integerInput("Set your level to:", input -> {
//                            int level = input;
//                            if(finalChild == 129) {
//                                if (input < 10) {
//                                    level = 10;
//                                } else if(level > 99) {
//                                    level = 99;
//                                }
//                            } else {
//                                if (input < 1) {
//                                    level = 1;
//                                } else if(level > 99) {
//                                    level = 99;
//                                }
//                            }
//
//                            if (finalChild == 129) {
//                                currentPreset.setHitpointsLevel(level);
//                            } else if (finalChild == 133) {
//                                currentPreset.setAttackLevel(level);
//                            } else if (finalChild == 137) {
//                                currentPreset.setStrengthLevel(level);
//                            } else if (finalChild == 141) {
//                                currentPreset.setDefenceLevel(level);
//                            } else if (finalChild == 145) {
//                                currentPreset.setMagicLevel(level);
//                            } else if (finalChild == 149) {
//                                currentPreset.setRangedLevel(level);
//                            } else if (finalChild == 153) {
//                                currentPreset.setPrayerLevel(level);
//                            }
//                            updatePresetView(player);
//                        });
//                    } else {
//                        player.sendMessage("Broken code, notify a staff member please.");
//                        return;
//                    }
//                };
//            }

            h.actions[71] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(0);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[74] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(1);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            // how does this even not cause issues??
            h.actions[77] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(2);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[80] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(3);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[83] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(4);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[86] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(5);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[89] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(6);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[92] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(7);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[95] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(8);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[98] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(9);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[101] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(10);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[104] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(11);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[107] = (DefaultAction) (player, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(12);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[109] = (DefaultAction) (player, option, slot, itemId) -> {
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                if (currentPreset.isLocked()) {
                    player.openDialogue(false,
                            new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                    + Color.RED.wrap("locked") + " and can't be modified right now."));
                    return;
                }
                activatePreset(player, currentPreset);
            };
            h.actions[48] = (DefaultAction) (player, option, slot, itemId) -> {
                showSpellbookDialogue(player, player.getPresetManager().getSelectedPreset());
            };
            h.actions[131] = (DefaultAction) (player, option, slot, itemId) -> {
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                if (currentPreset.isLocked()) {
                    player.openDialogue(false,
                            new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                    + Color.RED.wrap("locked") + " and can't be modified right now."));
                    return;
                }
                player.openDialogue(false, new OptionsDialogue("Are you sure about this?",
                        new Option("Yes! Clear this preset!",
                                () -> clearPreset(player, currentPreset)),
                        new Option("Nevermind")
                ));
            };
            h.actions[63] = (DefaultAction) (player, option, slot, itemId) -> {
                int selectedPreset = player.getPresetManager().getSelectedPreset();
                Preset currentPreset = player.getPresetManager().getPreset(selectedPreset);
                player.openDialogue(false, new OptionsDialogue(Color.RED.wrap(currentPreset.getName())
                        + " is currently " + Color.RED.wrap(currentPreset.isLocked() ? "Locked" : "Unlocked"),
                        new Option("Yes! " + (currentPreset.isLocked() ? "Unlock " : "Lock ")
                                + Color.RED.wrap(currentPreset.getName()),
                                () -> lockPreset(player, currentPreset)),
                        new Option("Nevermind")
                ));
            };
        });
    }

    private static void lockPreset(Player player, Preset selectedPreset) {
        if (selectedPreset.isLocked()) {
            selectedPreset.setLocked(false);
            player.sendMessage("You unlocked this preset and can modify it from now on.");
        } else {
            selectedPreset.setLocked(true);
            player.sendMessage("You locked this preset and can no longer modify it from now on.");
        }
    }

    private static void clearPreset(Player player, Preset currentPreset) {
        currentPreset.setName("Preset #" + currentPreset.getPresetId());
        currentPreset.setEquipment(new PresetItem[14]);
        currentPreset.setInventory(new PresetItem[28]);
        currentPreset.setSpellBook(SpellBook.MODERN);
        updatePresetView(player);
    }

    private static void showSpellbookDialogue(Player player, int selectedPreset) {
        Preset currentPreset = player.getPresetManager().getPreset(selectedPreset);
        if (currentPreset.isLocked()) {
            player.openDialogue(false,
                    new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                            + Color.RED.wrap("locked") + " and can't be modified right now."));
            return;
        }
        player.openDialogue(false, new OptionsDialogue("Which spellbook would you like to set?",
                new Option("Modern Spellbook", () -> setSpellbook(player, currentPreset, SpellBook.MODERN)),
                new Option("Ancient Spellbook", () -> setSpellbook(player, currentPreset, SpellBook.ANCIENT)),
                new Option("Lunar Spellbook", () -> setSpellbook(player, currentPreset, SpellBook.LUNAR)),
                new Option("Nevermind", () -> updatePresetView(player))
        ));
    }

    private static void setSpellbook(Player player, Preset currentPreset, SpellBook bookType) {
        currentPreset.setSpellBook(bookType);
        updatePresetView(player);
    }

    private static void updatePresetName(Player player, String input) {
        Preset currentPreset = player.getPresetManager().getPreset(player.getPresetManager().getSelectedPreset());
        if (currentPreset.isLocked()) {
            player.openDialogue(false,
                    new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                            + Color.RED.wrap("locked") + " and can't be modified right now."));
            return;
        }
        currentPreset.setName(input);
    }

    private static void updatePresetInventory(Player player, Preset currentPreset) {
        currentPreset.setInventory(PresetItem.itemsToPresetItems(player.getInventory().getItems(), 28));
    }

    private static void updatePresetEquipment(Player player, Preset currentPreset) {
        currentPreset.setEquipment(PresetItem.itemsToPresetItems(player.getEquipment().getItems(), 14));
    }

//    private static void updatePresetStats(Player player, Preset currentPreset) {
//        currentPreset.setHitpointsLevel(player.getStats().get(StatType.Hitpoints).fixedLevel);
//        currentPreset.setAttackLevel(player.getStats().get(StatType.Attack).fixedLevel);
//        currentPreset.setStrengthLevel(player.getStats().get(StatType.Strength).fixedLevel);
//        currentPreset.setDefenceLevel(player.getStats().get(StatType.Defence).fixedLevel);
//        currentPreset.setRangedLevel(player.getStats().get(StatType.Ranged).fixedLevel);
//        currentPreset.setMagicLevel(player.getStats().get(StatType.Magic).fixedLevel);
//        currentPreset.setPrayerLevel(player.getStats().get(StatType.Prayer).fixedLevel);
//    }

    private static void activatePreset(Player player, Preset currentPreset) {
        player.openDialogue(false, new OptionsDialogue("Do you really want to activate the preset?",
                new Option("Yes, activate my preset", () -> {
                    PresetManager.activatePreset(player, currentPreset);
                    updatePresetView(player);
                    player.sendMessage("Your preset has been successfully loaded.");
                }),
                new Option("No thanks", () -> updatePresetView(player))
        ));
    }

    public static void updatePresetView(Player player) {
        if (player.wildernessLevel > 0) {
            player.sendMessage("You cannot use this feature inside the wilderness.");
            return;
        }
        Preset preset = player.getPresetManager().getPreset(player.getPresetManager().getSelectedPreset());
        Item[] eq = PresetItem.presetItemsToItems(preset.getEquipment(), 14);
        Item[] inv = PresetItem.presetItemsToItems(preset.getInventory(), 28);
        player.getPacketSender().sendItems(633, eq);
        player.getPacketSender().sendItems(634, inv);
        int bookId;
        if (preset.getSpellBook() == SpellBook.MODERN) {
            bookId = 1;
        } else if (preset.getSpellBook() == SpellBook.ANCIENT) {
            bookId = 2;
        } else {
            bookId = 3;
        }
        player.getPacketSender().sendClientScript(11321, "isi", preset.getPresetId(), preset.getName(), bookId);
        player.getPacketSender().sendClientScript(11320);
        refreshPresets(player);
        player.openInterface(InterfaceType.MAIN, 1019);
    }

    public static void refreshPresets(Player player) {
        Preset preset1 = player.getPresetManager().getPreset(0);
        Preset preset2 = player.getPresetManager().getPreset(1);
        Preset preset3 = player.getPresetManager().getPreset(2);
        Preset preset4 = player.getPresetManager().getPreset(3);
        Preset preset5 = player.getPresetManager().getPreset(4);
        Preset preset6 = player.getPresetManager().getPreset(5);
        Preset preset7 = player.getPresetManager().getPreset(6);
        Preset preset8 = player.getPresetManager().getPreset(7);
        Preset preset9 = player.getPresetManager().getPreset(8);
        Preset preset10 = player.getPresetManager().getPreset(9);
        Preset preset11 = player.getPresetManager().getPreset(10);
        Preset preset12 = player.getPresetManager().getPreset(11);
        Preset preset13 = player.getPresetManager().getPreset(12);

        player.getPacketSender().sendClientScript(11319, "sssssssssssss", preset1.getName(), preset2.getName(), preset3.getName(), preset4.getName(), preset5.getName(),
                preset6.getName(), preset7.getName(), preset8.getName(), preset9.getName(), preset10.getName(), preset11.getName(), preset12.getName(), preset13.getName());
    }

}
