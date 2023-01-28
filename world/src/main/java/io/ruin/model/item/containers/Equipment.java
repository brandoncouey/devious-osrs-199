package io.ruin.model.item.containers;

import io.ruin.cache.Color;
import io.ruin.cache.ItemDef;
import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.combat.RangedWeapon;
import io.ruin.model.diaries.pvp.PvPDiaryEntry;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.handlers.EquipmentStats;
import io.ruin.model.item.Item;
import io.ruin.model.item.ItemContainer;
import io.ruin.model.item.actions.impl.CompletionistCape;
import io.ruin.model.item.actions.impl.MaxCape;
import io.ruin.model.item.actions.impl.chargable.Blowpipe;
import io.ruin.model.item.actions.impl.combine.SlayerHelm;
import io.ruin.model.skills.construction.actions.CombatRoom;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

import java.util.Map;

public class Equipment extends ItemContainer {

    public static final int SLOT_HAT = 0, SLOT_CAPE = 1, SLOT_AMULET = 2, SLOT_WEAPON = 3, SLOT_CHEST = 4,
            SLOT_SHIELD = 5, SLOT_LEGS = 7, SLOT_HANDS = 9, SLOT_FEET = 10, SLOT_RING = 12, SLOT_AMMO = 13;

    public int[] bonuses = new int[16];

    public double weight;

    public void equip(Item selectedItem) {
        ItemDef selectedDef = selectedItem.getDef();
        int equipSlot = selectedDef.equipSlot;
        if(equipSlot == -1 || selectedDef.equipOption == -1) {
            player.sendMessage("You can't wear this item.");
            return;
        }
        if(selectedDef.equipReqs != null) {
            for(int req : selectedDef.equipReqs) {
                int statId = req >> 8;
                int lvl = req & 0xff;
                Stat stat = player.getStats().get(statId);
                if(stat.fixedLevel < lvl) {
                    player.sendMessage("You need " + StatType.get(statId).descriptiveName + " level of " + lvl + " to equip this item.");
                    return;
                }
            }
        }
        if (selectedItem.getId() == 1409) {
            player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.IBANS_STAFF);
        }
        if (!player.isOwner()) {
            if (selectedDef.maxType && !MaxCape.unlocked(player)) {
                player.sendMessage("You don't have the required stats to wear this.");
                return;
            }
            if (selectedDef.compType && !CompletionistCape.doneAchieves(player)) {
                player.sendMessage("You don't have the required stats to wear this.");
                return;
            }
        }

        if(player.getDuel().isBlocked(selectedDef)) {
            player.sendMessage("That item cannot be equipped in this duel!");
            return;
        }

        if(player.getDuel().isToggled(DuelRule.NO_WEAPON_SWITCH) && equipSlot == Equipment.SLOT_WEAPON) {
            player.sendMessage("Weapon switching is disabled for this fight!");
            return;
        }

        if(!CombatRoom.allowEquip(player, selectedDef)) {
            return;
        }

        if(selectedDef.achievement != null && !selectedDef.achievement.isFinished(player) && (!selectedDef.achievementReqIsIronmanOnly || player.getGameMode().isIronMan())) {
            player.sendMessage("You must complete the " + Color.RED.wrap(selectedDef.achievement.getListener().name()) + " achievement to equip this item.");
            return;
        }

        if(selectedDef.name.toLowerCase().contains("goblin mail")) {
            player.sendMessage("You can't wear this item.");
            return;
        }

        Item addLast = null;
        Inventory inventory = player.getInventory();
        if(equipSlot == SLOT_SHIELD) {
            Item weapon = get(SLOT_WEAPON);
            if(weapon != null) {
                if(weapon.getDef().twoHanded) {
                    if(inventory.getFreeSlots() == 0 && get(SLOT_SHIELD) != null) {
                        player.sendMessage("You don't have enough free inventory space to do that.");
                        return;
                    }
                    addLast = weapon;
                    set(SLOT_WEAPON, null);
                }
            }
        } else if(equipSlot == SLOT_WEAPON) {
            Item shield = get(SLOT_SHIELD);
            if(shield != null) {
                if(selectedDef.twoHanded) {
                    if(inventory.getFreeSlots() == 0 && get(SLOT_WEAPON) != null) {
                        player.sendMessage("You don't have enough free inventory space to do that.");
                        return;
                    }
                    addLast = shield;
                    set(SLOT_SHIELD, null);
                }
            }
        }
        Item worn = get(equipSlot);
        if (worn == null) {
            selectedItem.remove();
            set(equipSlot, selectedItem);
        } else {
            int selectedId = selectedItem.getId();
            int selectedAmount = selectedItem.getAmount();
            Map<String, String> attributeCopy = selectedItem.copyOfAttributes();
            if(worn.getId() == selectedId && selectedDef.stackable) {
                selectedItem.remove();
                worn.incrementAmount(selectedAmount);
            } else {
                Item inventoryStack = null;
                Map<String, String> attributes = worn.copyOfAttributes();
                if (worn.getDef().stackable)
                    inventoryStack = inventory.findItem(worn.getId());
                if (inventoryStack != null) {
                    selectedItem.remove();
                    inventoryStack.incrementAmount(worn.getAmount());
                } else {
                    selectedItem.setId(worn.getId());
                    selectedItem.setAmount(worn.getAmount());
                    worn.clearAttributes();
                    worn.putAttributes(selectedItem.copyOfAttributes());
                }
                worn.setId(selectedId);
                worn.setAmount(selectedAmount);
                worn.putAttributes(attributeCopy);
                selectedItem.clearAttributes();
                selectedItem.putAttributes(attributes);
            }
        }
        if(addLast != null)
            inventory.add(addLast);
        if(!player.recentlyEquipped.isDelayed() && equipSlot == Equipment.SLOT_WEAPON) {
            player.recentlyEquipped.delay(1);
            // player.resetAnimation();
        }
        player.closeDialogue();
    }

    public boolean unequip(Item equipped) {
        Inventory inventory = player.getInventory();
        Item inventoryStack = null;
        if(equipped.getDef().stackable)
            inventoryStack = inventory.findItem(equipped.getId());
        if(inventoryStack != null) {
            equipped.remove();
            inventoryStack.incrementAmount(equipped.getAmount());
        } else {
            int freeSlot = inventory.freeSlot();
            if(freeSlot == -1) {
                player.sendMessage("You don't have enough free space to do that.");
                return false;
            }
            equipped.remove();
            inventory.set(freeSlot, equipped);
        }
        return true;
    }
    @Override
    public boolean sendUpdates() {
        int updatedAppearanceSlots = updatedCount;
        if(updatedSlots[SLOT_RING])
            updatedAppearanceSlots--;
        if(updatedSlots[SLOT_AMMO])
            updatedAppearanceSlots--;
        if(updatedSlots[SLOT_WEAPON])
            player.getCombat().updateWeapon(false);
        if(updatedAppearanceSlots > 0)
            player.getAppearance().update();
        if(!super.sendUpdates())
            return false;
        /**
         * Reset bonuses/weight
         */
        for(int i = 0; i < bonuses.length; i++)
            bonuses[i] = 0;
        weight = 0.0;
        /**
         * Calculate bonuses/weight
         */
        boolean ignoreRangedAmmoStr = false;
        Item wep = get(SLOT_WEAPON);
        if (wep != null) {
            if (wep.getId() == 30315) { //blowpipe
                Blowpipe.Dart dart = Blowpipe.getDart(wep);
                if (dart != Blowpipe.Dart.NONE) //should always be true
                    bonuses[EquipmentStats.RANGED_STRENGTH] += ItemDef.get(dart.id).equipBonuses[EquipmentStats.RANGED_STRENGTH];
                ignoreRangedAmmoStr = true;
            } else {
                if (wep.getId() == 12926) { //blowpipe
                    Blowpipe.Dart dart = Blowpipe.getDart(wep);
                    if (dart != Blowpipe.Dart.NONE) //should always be true
                        bonuses[EquipmentStats.RANGED_STRENGTH] += ItemDef.get(dart.id).equipBonuses[EquipmentStats.RANGED_STRENGTH];
                    ignoreRangedAmmoStr = true;
                } else {
                    RangedWeapon rangedWep = wep.getDef().rangedWeapon;
                    ignoreRangedAmmoStr = rangedWep != null && rangedWep.allowedAmmo == null;
                }
            }
        }
        for(Item item : getItems()) {
            if(item != null) {
                ItemDef def = item.getDef();
                if(def.equipBonuses != null) {
                    boolean wilderness = def.wilderness; //If its pvp armor
                    boolean inWilderness = player.wildernessLevel > 0 || player.tournament != null;
                    for(int i = 0; i < def.equipBonuses.length; i++) {
                        int bonus = def.equipBonuses[i];
                        if (bonus == 0)
                            continue;
                        if(ignoreRangedAmmoStr && def.equipSlot == SLOT_AMMO && i == EquipmentStats.RANGED_STRENGTH)
                            continue;
                        if (wilderness && !inWilderness)
                            bonus *= .75;
                        bonuses[i] += bonus;
                    }
                }
                weight += def.weightEquipment;
            }
        }
        /**
         * Update equipment stats interface
         */
        if(player.isVisibleInterface(Interface.EQUIPMENT_STATS))
            EquipmentStats.update(player, Interface.EQUIPMENT_STATS, 24);
        return true;
    }

    public boolean wearsAhrimRobes() {
        int hatId = getItemIdForSlot(SLOT_HAT);
        int bodyId = getItemIdForSlot(SLOT_CHEST);
        int legsId = getItemIdForSlot(SLOT_LEGS);
        return (hatId == 4708 || hatId == 4856 || hatId == 4857 || hatId == 4858 || hatId == 4859) &&
                (bodyId == 4712 || bodyId == 4868 || bodyId == 4869 || bodyId == 4870 || bodyId == 4871) &&
                (legsId == 4717 || legsId == 4874 || legsId == 4875 || legsId == 4876 || legsId == 4877);
    }

    private int getItemIdForSlot(int slot) {
        Item item = get(slot);
        return item == null ? -1 : item.getId();
    }

    public boolean wearsAmuletOfTheDamned() {
        return getItemIdForSlot(SLOT_AMULET) == 12851 || getItemIdForSlot(SLOT_AMULET) == 12853;
    }

    public boolean wearsTormentedBracelet() {
        return getItemIdForSlot(SLOT_HANDS) == 19544 || getItemIdForSlot(SLOT_HANDS) == 23444;
    }

    public boolean wearsStaffOfTheDead() {
        return getItemIdForSlot(SLOT_WEAPON) == 11791 || getItemIdForSlot(SLOT_WEAPON) == 12904;
    }

    public boolean wearsStaffOfLight() {
        return getItemIdForSlot(SLOT_WEAPON) == 22296;
    }

    public boolean wearsStaffOfBalance() {
        return getItemIdForSlot(SLOT_WEAPON) == 24144;
    }

    public boolean wearsAncestralHat() {
        return getItemIdForSlot(SLOT_HAT) == 24664 || getItemIdForSlot(SLOT_HAT) == 21018;
    }

    public boolean wearsAncestralTop() {
        return getItemIdForSlot(SLOT_CHEST) == 21021 || getItemIdForSlot(SLOT_CHEST) == 24666;
    }

    public boolean wearsLavaDragonHideChest() {
        return getItemIdForSlot(SLOT_CHEST) == 30262;
    }
    public boolean wearsLavaDragonHideLegs() {
        return getItemIdForSlot(SLOT_LEGS) == 30263;
    }
    public boolean wearsLavaDragonHideHelm() {
        return getItemIdForSlot(SLOT_HAT) == 30261;
    }


    public boolean wearsDrakoSet() {
        return (getItemIdForSlot(SLOT_CHEST) == 30279 || getItemIdForSlot(SLOT_CHEST) == 30297) && (getItemIdForSlot(SLOT_LEGS) == 30280 || getItemIdForSlot(SLOT_LEGS) == 30298) && (getItemIdForSlot(SLOT_FEET) == 30285 || getItemIdForSlot(SLOT_FEET) == 30299) && (getItemIdForSlot(SLOT_HAT) == 30278 || getItemIdForSlot(SLOT_HAT) == 30296) && (getItemIdForSlot(SLOT_WEAPON) == 30276 || getItemIdForSlot(SLOT_WEAPON) == 30293);
    }

    public boolean wearsAncestralBottom() {
        return getItemIdForSlot(SLOT_LEGS) == 21024 || getItemIdForSlot(SLOT_LEGS) == 24668;
    }

    public boolean wearsSlayerHelm() {
        int currentHelm = getItemIdForSlot(SLOT_HAT);
        for(int helmId : SlayerHelm.ALL_BOOST_HELMS) {
            if(currentHelm == helmId) {
                return true;
            }
        }
        return false;
    }

    public boolean wearsOcculNecklace() {
        return getItemIdForSlot(SLOT_AMULET) == 12002 || getItemIdForSlot(SLOT_AMULET) == 19720;
    }

    public boolean wearsArcaneStreamNecklace() {
        return getItemIdForSlot(SLOT_AMULET) == 27502;
    }

    public boolean wearsSmokeBattleStaff() {
        return getItemIdForSlot(SLOT_WEAPON) == 11998;
    }

    public boolean wearsKodaiWand() {
        return getItemIdForSlot(SLOT_WEAPON) == 21006;
    }

    public boolean wearsAhrimStaff() {
        int weaponId = getItemIdForSlot(SLOT_WEAPON);
        return weaponId == 4710 || weaponId == 4862 || weaponId == 4863 || weaponId == 4864 || weaponId == 4865;
    }

    public boolean wearsEliteVoid() {
        return (getItemIdForSlot(SLOT_CHEST) == 13072 || getItemIdForSlot(SLOT_CHEST) == 24178) &&
                (getItemIdForSlot(SLOT_LEGS) == 13073 || getItemIdForSlot(SLOT_LEGS) == 24180);
    }

    public boolean wearsImbuedGodCape() {
        int capeId = getItemIdForSlot(SLOT_CAPE);
        return capeId == 21776 || capeId == 21780 || capeId == 21784
                || capeId == 21791 || capeId == 21793 || capeId == 21795;
    }
    //(player.getEquipment().hasId(23983) || player.getEquipment().hasId(30372)
    //                || player.getEquipment().hasId(25867) || player.getEquipment().hasId(25869) ||
    //                player.getEquipment().hasId(25884) || player.getEquipment().hasId(25886) ||
    //                player.getEquipment().hasId(25888) || player.getEquipment().hasId(25890) ||
    //                player.getEquipment().hasId(25892) || player.getEquipment().hasId(25894) || player.getEquipment().hasId(25896)

    public boolean wearsBofa() {
        int weaponId = getItemIdForSlot(SLOT_WEAPON);
        return weaponId == 23983 || weaponId == 30372 || weaponId == 25867
                || weaponId == 25869 || weaponId == 25884 || weaponId == 25886 || weaponId == 25888 || weaponId == 25890 || weaponId == 25892
                || weaponId == 25894 || weaponId == 25896;
    }

    public boolean wearsNightmareStaff() {
        int weaponId = getItemIdForSlot(SLOT_WEAPON);
        return weaponId == 24422 || weaponId == 24423 || weaponId == 24424
                || weaponId == 24425;
    }

    @Override
    public boolean hasId(int id) {
        ItemDef def = ItemDef.get(id);
        return def != null && getId(def.equipSlot) == id;
    }

}