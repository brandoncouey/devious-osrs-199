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
        InterfaceHandler.register(1033, h -> {



            h.actions[24] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(0).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(0);
               updatePresetView(player);
            };
            h.actions[29] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(1).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(1);
               updatePresetView(player);
            };
            h.actions[34] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(2).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(2);
               updatePresetView(player);
            };
            h.actions[39] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isOpalDonator()) {
                    player.sendMessage("You must be a opal donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(3).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(3);
               updatePresetView(player);
            };
            h.actions[45] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isJadeDonator()) {
                    player.sendMessage("You must be a jade donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(4).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(4);
               updatePresetView(player);
            };
            h.actions[51] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isRedTopazDonator()) {
                    player.sendMessage("You must be a red topaz donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(5).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(5);
                updatePresetView(player);
            };
            h.actions[57] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isSapphireDonator()) {
                    player.sendMessage("You must be a sapphire donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(6).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(6);
               updatePresetView(player);
            };
            h.actions[63] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isEmeraldDonator()) {
                    player.sendMessage("You must be a emerald donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(7).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(7);
               updatePresetView(player);
            };
            h.actions[69] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isRubyDonator()) {
                    player.sendMessage("You must be a ruby donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(8).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(8);
               updatePresetView(player);
            };
            h.actions[75] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isRubyDonator()) {
                    player.sendMessage("You must be a ruby donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(9).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(9);
               updatePresetView(player);
            };
            h.actions[81] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isDiamondDonator()) {
                    player.sendMessage("You must be a diamond donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(10).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(10);
               updatePresetView(player);
            };
            h.actions[87] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                if (!player.isDiamondDonator()) {
                    player.sendMessage("You must be a diamond donator to use this preset.");
                    return;
                }
                if (option == 2) {
                    player.stringInput("Name your preset", s -> {
                        player.getPresetManager().getPreset(11).setName(s);
                        refreshPresets(player);
                    });
                    return;
                }
                player.getPresetManager().setSelectedPreset(11);
               updatePresetView(player);
            };

            //Save
            h.actions[287] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().save(player);
            };
            h.actions[293] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().delete(player);
            };
            h.actions[299] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                PresetManager.equipPreset(player);
            };

           /* h.actions[124] = (DefaultAction) (player, childId, option, slot, itemId) -> {
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

            h.actions[71] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(0);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[74] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(1);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            // how does this even not cause issues??
            h.actions[77] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(2);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[80] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(3);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[83] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(4);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[86] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(5);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[89] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(6);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[92] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(7);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[95] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(8);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[98] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(9);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[101] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(10);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[104] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(11);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[107] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                player.getPresetManager().setSelectedPreset(12);
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                updatePresetView(player);
            };
            h.actions[299] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                Preset currentPreset = player.getPresetManager()
                        .getPreset(player.getPresetManager().getSelectedPreset());
                if (currentPreset.isLocked()) {
                    player.openDialogue(false,
                            new MessageDialogue("The preset " + Color.RED.wrap(currentPreset.getName()) + " is "
                                    + Color.RED.wrap("locked") + " and can't be modified right now."));
                    return;
                }
                equipPreset(player, currentPreset);
            };
            h.actions[48] = (DefaultAction) (player, childId, option, slot, itemId) -> {
                showSpellbookDialogue(player, player.getPresetManager().getSelectedPreset());
            };
            h.actions[131] = (DefaultAction) (player, childId, option, slot, itemId) -> {
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
            h.actions[63] = (DefaultAction) (player, childId, option, slot, itemId) -> {
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
        });*/
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

    private static void equipPreset(Player player, Preset currentPreset) {
        /*player.openDialogue(false, new OptionsDialogue("Do you really want to activate the preset?",
                new Option("Yes, activate my preset", () -> {
                    PresetManager.equipPreset(player, currentPreset);
                    updatePresetView(player);
                    player.sendMessage("Your preset has been successfully loaded.");
                }),
                new Option("No thanks", () -> updatePresetView(player))
        ));*/
    }

    public static void updatePresetView(Player player) {
        if (player.wildernessLevel > 0) {
            player.sendMessage("You cannot use this feature inside the wilderness.");
            return;
        }
        Preset preset = player.getPresetManager().getPreset(player.getPresetManager().getSelectedPreset());
        Item[] eq = PresetItem.presetItemsToItems(preset.getEquipment(), 14);
        player.getPacketSender().sendItems(633, eq);
        int bookId;
        if (preset.getSpellBook() == SpellBook.MODERN) {
            bookId = 1;
        } else if (preset.getSpellBook() == SpellBook.ANCIENT) {
            bookId = 2;
        } else {
            bookId = 3;
        }
        player.getPacketSender().sendClientScript(11321, "isi", preset.getPresetId(), preset.getName(), bookId);
        refreshPresets(player);
        if (player.isOpalDonator()) {
            player.getPacketSender().sendString(1033, 40, "Preset #4");
            player.getPacketSender().setHidden(1033, 38, true);
        }
        if (player.isJadeDonator()) {
            player.getPacketSender().sendString(1033, 46, "Preset #5");
            player.getPacketSender().setHidden(1033, 44, true);
        }
        if (player.isRedTopazDonator()) {
            player.getPacketSender().sendString(1033, 52, "Preset #6");
            player.getPacketSender().setHidden(1033, 50, true);
        }
        if (player.isSapphireDonator()) {
            player.getPacketSender().sendString(1033, 58, "Preset #7");
            player.getPacketSender().setHidden(1033, 56, true);
        }
        if (player.isEmeraldDonator()) {
            player.getPacketSender().sendString(1033, 64, "Preset #8");
            player.getPacketSender().setHidden(1033, 62, true);
        }
        if (player.isRubyDonator()) {
            player.getPacketSender().sendString(1033, 70, "Preset #9");
            player.getPacketSender().sendString(1033, 76, "Preset #10");
            player.getPacketSender().setHidden(1033, 68, true);
            player.getPacketSender().setHidden(1033, 74, true);
        }
        if (player.isDiamondDonator()) {
            player.getPacketSender().sendString(1033, 82, "Preset #11");
            player.getPacketSender().sendString(1033, 88, "Preset #12");
            player.getPacketSender().setHidden(1033, 80, true);
            player.getPacketSender().setHidden(1033, 86, true);
        }
        player.openInterface(InterfaceType.MAIN, 1033);
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
        player.getPacketSender().sendClientScript(11319, "ssssssssssss", preset1.getName(), preset2.getName(), preset3.getName(), preset4.getName(), preset5.getName(),
                preset6.getName(), preset7.getName(), preset8.getName(), preset9.getName(), preset10.getName(), preset11.getName(), preset12.getName());
    }

}
