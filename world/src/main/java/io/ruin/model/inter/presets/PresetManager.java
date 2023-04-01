package io.ruin.model.inter.presets;

import com.google.gson.annotations.Expose;
import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.bank.BankItem;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.Stat;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class PresetManager {
    @Getter
    @Setter
    @Expose
    private Preset[] presets = new Preset[13];

    @Getter
    @Setter
    private int selectedPreset = 0;

    @Getter
    @Setter
    @Expose
    private int previousSelectedPreset = -1;

    private boolean modifiedInventory;

    private boolean modifiedEquipment;


    // TODO: Add modifying levels to edit actions

    public PresetManager() {
        for (int i = 0; i < 13; i++) {
            presets[i] = getEmptyPreset(i);
        }
    }

    public Preset getEmptyPreset(int id) {
        return new Preset(id, new PresetItem[14], new PresetItem[28],
                "Preset " + id, SpellBook.MODERN, false);
    }

    public void delete(Player player) {
        presets[selectedPreset] = getEmptyPreset(selectedPreset);
        PresetInterfaceHandler.updatePresetView(player);
    }


    public void save(Player player) {
        Preset preset = presets[selectedPreset];
        if (preset == null) {
            preset = getEmptyPreset(selectedPreset);
        }
        //Saves Inventory
        for (int i = 0; i < preset.getInventory().length; i++) {
            if (player.getInventory().get(i) == null) {
                preset.getInventory()[i] = new PresetItem(-1, 0);
            } else {
                preset.getInventory()[i] = new PresetItem(player.getInventory().get(i).getId(), player.getInventory().get(i).getAmount(), player.getInventory().get(i).copyOfAttributes());
            }
        }
        //Saves Equipment
        for (int i = 0; i < preset.getEquipment().length; i++) {
            if (player.getEquipment().get(i) == null) {
                preset.getEquipment()[i] = new PresetItem(-1, 0);
            } else {
                preset.getEquipment()[i] = new PresetItem(player.getEquipment().get(i).getId(), player.getEquipment().get(i).getAmount(), player.getEquipment().get(i).copyOfAttributes());
            }
        }
        preset.setSpellBook(SpellBook.values()[Config.MAGIC_BOOK.get(player)]);
        PresetInterfaceHandler.updatePresetView(player);
    }

    public static void equipPreset(Player player) {
        Preset preset = player.getPresetManager().getPreset(player.getPresetManager().getSelectedPreset());
        player.getBank().deposit(player.getInventory(), true, true);
        player.getBank().deposit(player.getEquipment(), true, true);

        player.getPresetManager().modifiedEquipment = false;
        player.getPresetManager().modifiedInventory = false;

        if (!itemContainerEmpty(player.getInventory().getItems())) {
            player.sendFilteredMessage("Your inventory has not been fully banked so your preset has not been loaded.");
            player.sendFilteredMessage("You probably did not have enough bank space.");
            return;
        }


        if (!itemContainerEmpty(player.getEquipment().getItems())) {
            player.sendFilteredMessage("Your equipment has not been full banked so your preset has not been loaded.");
            player.sendFilteredMessage("You probably did not have enough bank space.");
            return;
        }

        loadInventory(player, preset.getInventory());
        loadEquipment(player, preset.getEquipment());

        if (player.getPresetManager().modifiedEquipment) {
            player.sendMessage("Your preset equipment has been modified since you did not have all items in your bank.");
        }

        if (player.getPresetManager().modifiedInventory) {
            player.sendMessage("Your preset inventory has been modified since you did not have all items in your bank.");
        }

        preset.getSpellBook().setActive(player);
    }

    public static boolean itemContainerEmpty(Item[] items) {
        for (Item item : items) {
            if (item != null) {
                return false;
            }
        }

        return true;
    }

    public Preset getPreset(int index) {
        return presets[index];
    }

    public static PresetItem[] loadInventory(Player player, PresetItem[] presetInventory) {
        PresetItem[] updatedItems = new PresetItem[28];
        for (int slot = 0; slot < presetInventory.length; slot++) {
            PresetItem item = presetInventory[slot];

            if (item == null || item.getId() == -1) {
                updatedItems[slot] = new PresetItem(-1, 1, null);
                player.getInventory().set(slot, null);
                continue;
            }

            int originalId = item.getId();
            BankItem foundItem = getBankItem(player, new PresetItem(ItemDef.get(originalId).isNote() ? item.getId() - 1 : item.getId(), item.getAmount(), item.getAttributes()));

            /*if (foundItem != null && item.hasAttributes()) {
                if (!foundItem.hasAttributes()) {
                    item.setAttributes(new HashMap<>());
                } else {
                    for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (foundItem.getAttributeInt(key) != Integer.parseInt(value)) {
                            item.setAttributes(foundItem.copyOfAttributes());
                            break;
                        }
                    }
                }
            }*/

            if (foundItem == null) {
                updatedItems[slot] = new PresetItem(-1, 1, null);
                player.getPresetManager().modifiedInventory = true;
            } else {
                int amountInBank = foundItem.getAmount();
                if (amountInBank < item.getAmount()) {
                    item.setAmount(amountInBank);
                }
                updatedItems[slot] = item;

                ItemDef itemDef = ItemDef.get(item.getId());
                if (amountInBank - item.getAmount() < 1 && Config.BANK_ALWAYS_PLACEHOLDERS.get(player) == 1) {
                    if (itemDef != null && itemDef.hasPlaceholder()) {
                        foundItem.setId(itemDef.placeholderMainId);
                        foundItem.setAmount(0);
                    } else {
                        player.getBank().remove(foundItem.getId(), item.getAmount(), false, foundItem.copyOfAttributes());
                    }
                } else {
                    player.getBank().remove(foundItem.getId(), item.getAmount(), false, foundItem.copyOfAttributes());
                }
                player.getBank().sort();
                player.getInventory().set(slot, new Item(originalId, item.getAmount(), item.getAttributes()));
            }


        }
        return updatedItems;
    }

    public static PresetItem[] loadEquipment(Player player, PresetItem[] presetEquipment) {
        PresetItem[] updatedItems = new PresetItem[14];
        for (int slot = 0; slot < presetEquipment.length; slot++) {
            PresetItem item = presetEquipment[slot];

            if (item == null || item.getId() == -1) {
                updatedItems[slot] = new PresetItem(-1, 1, null);
                continue;
            }

            BankItem foundItem = getBankItem(player, item);

            if (foundItem != null && item.hasAttributes()) {
                if (!foundItem.hasAttributes()) {
                    item.setAttributes(new HashMap<>());
                } else {
                    for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (foundItem.getAttributeInt(key) != Integer.parseInt(value)) {
                            item.setAttributes(foundItem.copyOfAttributes());
                            break;
                        }
                    }
                }
            }

            if (foundItem == null) {
                updatedItems[slot] = new PresetItem(-1, 1, null);
                player.getPresetManager().modifiedEquipment = true;
            } else {
                ItemDef itemDef = ItemDef.get(foundItem.getId());

                boolean canEquip = true;
                if (itemDef != null && itemDef.equipReqs != null) {
                    for (int req : itemDef.equipReqs) {
                        int statId = req >> 8;
                        int lvl = req & 0xff;
                        Stat stat = player.getStats().get(statId);
                        if (stat.fixedLevel < lvl) {
                            player.sendMessage("You do not meet the requirements for " + itemDef.name + ". So it has been removed from the preset.");
                            updatedItems[slot] = new PresetItem(-1, 1, null);
                            canEquip = false;
                            player.getPresetManager().modifiedEquipment = true;
                        }
                    }
                }

                if (canEquip) {
                    int amountInBank = foundItem.getAmount();
                    if (amountInBank < item.getAmount()) {
                        item.setAmount(amountInBank);
                    }
                    updatedItems[slot] = item;

                    if (amountInBank - item.getAmount() < 1 && Config.BANK_ALWAYS_PLACEHOLDERS.get(player) == 1) {
                        if (itemDef != null && itemDef.hasPlaceholder()) {
                            foundItem.setId(itemDef.placeholderMainId);
                            foundItem.setAmount(0);
                        } else {
                            player.getBank().remove(item.getId(), item.getAmount(), false, foundItem.copyOfAttributes());
                        }
                    } else {
                        player.getBank().remove(item.getId(), item.getAmount(), false, foundItem.copyOfAttributes());
                    }

                    player.getBank().sort();
                    player.getEquipment().equip(new Item(item.getId(), item.getAmount(), item.getAttributes()));
                }
            }
        }
        return updatedItems;
    }

    private static BankItem getBankItem(Player player, PresetItem item) {
        BankItem backupBankItem = null;
        for (BankItem bankItem : player.getBank().getItems()) {
            if (bankItem == null) {
                continue;
            }
            if (bankItem.getId() == item.getId()) {
                if (item.hasAttributes()) {
                    if (bankItem.hasAttributes()) {
                        boolean equalAttributes = true;
                        for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            if (bankItem.getAttributeInt(key) != Integer.parseInt(value)) {
                                equalAttributes = false;
                            }
                        }
                        if (equalAttributes) {
                            return bankItem;
                        } else {
                            backupBankItem = bankItem;
                        }
                    } else {
                        backupBankItem = bankItem;
                    }
                } else {
                    return bankItem;
                }
            }
        }
        return backupBankItem;
    }

//    public static PresetItem[] loadEquipment(Player player, PresetItem[] presetEquipment) {
//        PresetItem[] updatedItems = new PresetItem[14];
//        for (int slot = 0; slot < presetEquipment.length; slot++) {
//            PresetItem item = presetEquipment[slot];
//
//            if(item == null || item.getId() == -1) {
//                updatedItems[slot] = new PresetItem(-1 ,1, null);
//                continue;
//            }
//
//            BankItem foundItem = getBankItem(player, item);
//
//            if(foundItem != null && item.hasAttributes()) {
//                if(!foundItem.hasAttributes()) {
//                    item.setAttributes(new HashMap<>());
//                } else {
//                    for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
//                        String key = entry.getKey();
//                        String value = entry.getValue();
//                        if(foundItem.getAttributeInt(key) != Integer.parseInt(value)) {
//                            item.setAttributes(foundItem.copyOfAttributes());
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if(foundItem == null) {
//                updatedItems[slot] = new PresetItem(-1 ,1, null);
//                modifiedEquipment = true;
//            } else {
//                ItemDef itemDef = ItemDef.get(foundItem.getId());
//
//                boolean canEquip = true;
//                if(itemDef != null && itemDef.equipReqs != null) {
//                    for(int req : itemDef.equipReqs) {
//                        int statId = req >> 8;
//                        int lvl = req & 0xff;
//                        Stat stat = player.getStats().get(statId);
//                        if(stat.fixedLevel < lvl) {
//                            player.sendMessage("You do not meet the requirements for " + itemDef.name + ". So it has been removed from the preset.");
//                            updatedItems[slot] = new PresetItem(-1 ,1, null);
//                            canEquip = false;
//                            modifiedEquipment = true;
//                        }
//                    }
//                }
//
//                if(canEquip) {
//                    int amountInBank = foundItem.getAmount();
//                    if(amountInBank < item.getAmount()) {
//                        item.setAmount(amountInBank);
//                    }
//                    updatedItems[slot] = item;
//
//                    if(amountInBank - item.getAmount() < 1 && Config.BANK_ALWAYS_PLACEHOLDERS.get(player) == 1) {
//                        if (itemDef != null && itemDef.hasPlaceholder()) {
//                            foundItem.setId(itemDef.placeholderMainId);
//                            foundItem.setAmount(0);
//                        } else {
//                            player.getBank().remove(item.getId(), item.getAmount());
//                        }
//                    } else {
//                        player.getBank().remove(item.getId(), item.getAmount());
//                    }
//
//                    player.getBank().sort();
//                    player.getEquipment().equip(new Item(item.getId(), item.getAmount(), item.getAttributes()));
//                }
//            }
//        }
//        return updatedItems;
//    }

//    public static PresetItem[] loadEquipment(Player player, PresetItem[] presetEquipment) {
//        PresetItem[] updatedItems = new PresetItem[14];
//        for (int slot = 0; slot < presetEquipment.length; slot++) {
//            PresetItem item = presetEquipment[slot];
//
//            if(item == null || item.getId() == -1) {
//                updatedItems[slot] = new PresetItem(-1 ,1, null);
//                continue;
//            }
//
//            BankItem foundItem = getBankItem(player, item);
//
//            if(foundItem != null && item.hasAttributes()) {
//                if(!foundItem.hasAttributes()) {
//                    item.setAttributes(new HashMap<>());
//                } else {
//                    for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
//                        String key = entry.getKey();
//                        String value = entry.getValue();
//                        if(foundItem.getAttributeInt(key) != Integer.parseInt(value)) {
//                            item.setAttributes(foundItem.copyOfAttributes());
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if(foundItem == null) {
//                updatedItems[slot] = new PresetItem(-1 ,1, null);
//                modifiedEquipment = true;
//            } else {
//                ItemDef itemDef = ItemDef.get(foundItem.getId());
//
//                boolean canEquip = true;
//                if(itemDef != null && itemDef.equipReqs != null) {
//                    for(int req : itemDef.equipReqs) {
//                        int statId = req >> 8;
//                        int lvl = req & 0xff;
//                        Stat stat = player.getStats().get(statId);
//                        if(stat.fixedLevel < lvl) {
//                            player.sendMessage("You do not meet the requirements for " + itemDef.name + ". So it has been removed from the preset.");
//                            updatedItems[slot] = new PresetItem(-1 ,1, null);
//                            canEquip = false;
//                            modifiedEquipment = true;
//                        }
//                    }
//                }
//
//                if(canEquip) {
//                    int amountInBank = foundItem.getAmount();
//                    if(amountInBank < item.getAmount()) {
//                        item.setAmount(amountInBank);
//                    }
//                    updatedItems[slot] = item;
//
//                    if(amountInBank - item.getAmount() < 1 && Config.BANK_ALWAYS_PLACEHOLDERS.get(player) == 1) {
//                        if (itemDef != null && itemDef.hasPlaceholder()) {
//                            foundItem.setId(itemDef.placeholderMainId);
//                            foundItem.setAmount(0);
//                        } else {
//                            player.getBank().remove(item.getId(), item.getAmount());
//                        }
//                    } else {
//                        player.getBank().remove(item.getId(), item.getAmount());
//                    }
//
//                    player.getBank().sort();
//                    player.getEquipment().equip(new Item(item.getId(), item.getAmount(), item.getAttributes()));
//                }
//            }
//        }
//        return updatedItems;
//    }

//    private static BankItem getBankItem(Player player, PresetItem item) {
//        BankItem backupBankItem = null;
//        for(BankItem bankItem : player.getBank().getItems()) {
//            if(bankItem == null) {
//                continue;
//            }
//            if(bankItem.getId() == item.getId()) {
//                if(item.hasAttributes()) {
//                    if(bankItem.hasAttributes()) {
//                        boolean equalAttributes = true;
//                        for (Map.Entry<String, String> entry : item.getAttributes().entrySet()) {
//                            String key = entry.getKey();
//                            String value = entry.getValue();
//                            if(bankItem.getAttributeInt(key) != Integer.parseInt(value)) {
//                                equalAttributes = false;
//                            }
//                        }
//                        if (equalAttributes) {
//                            return bankItem;
//                        } else {
//                            backupBankItem = bankItem;
//                        }
//                    } else {
//                        backupBankItem = bankItem;
//                    }
//                } else {
//                    return bankItem;
//                }
//            }
//        }
//        return backupBankItem;
//    }
}
