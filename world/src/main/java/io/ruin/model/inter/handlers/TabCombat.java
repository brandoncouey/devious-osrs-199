package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.spells.TargetSpell;

import java.util.Arrays;
import java.util.List;

public class TabCombat {

    static {
        InterfaceHandler.register(Interface.COMBAT_OPTIONS, h -> {
            h.actions[4] = (SimpleAction) p -> p.getCombat().changeAttackSet(0);
            h.actions[8] = (SimpleAction) p -> p.getCombat().changeAttackSet(1);
            h.actions[12] = (SimpleAction) p -> p.getCombat().changeAttackSet(2);
            h.actions[16] = (SimpleAction) p -> p.getCombat().changeAttackSet(3);
            h.actions[21] = (SimpleAction) p -> openAutocast(p, true);
            h.actions[26] = (SimpleAction) p -> openAutocast(p, false);
            h.actions[30] = (SimpleAction) Config.AUTO_RETALIATE::toggle;
            h.actions[36] = (SimpleAction) p -> p.getCombat().toggleSpecial();
        });
        InterfaceHandler.register(Interface.AUTOCAST_SELECTION, h -> {
            h.actions[1] = (SlotAction) TabCombat::selectAutocast;
        });
    }

    private static void open(Player player, int interfaceId) {//meehhhh (Todo better interface positioning system..)
        final int parentId = player.getGameFrameId();
        final int childId = parentId == Interface.FIXED_SCREEN ? 75 : 79;
        player.getPacketSender().sendInterface(interfaceId, parentId, childId, 1);
    }

    private static void openAutocast(Player player, boolean defensive) {
        Integer autocastId = getAutocastId(player);
        if (autocastId == null) {
            player.sendMessage("Your staff can't autocast with that spellbook.");
            return;
        }
        open(player, Interface.AUTOCAST_SELECTION);
        player.getPacketSender().sendAccessMask(Interface.AUTOCAST_SELECTION, 1, 0, 52, 2);
        Config.AUTOCAST_SET.set(player, autocastId);
        Config.DEFENSIVE_CAST.set(player, defensive ? 1 : 0);
    }

    private static final int[] staff = {1379, 1381, 1383, 1385, 1387, 1389, 1391, 1393, 1395,
            1397, 1399, 1401, 1403, 1405, 1407, 1409, 1410, 2415,
            2416, 2417, 3053, 3054, 4170, 9084, 11787, 11789, 11998,
            12000, 12658, 12795, 12796, 20730, 20733, 20736, 20739, 21198, 21200};


    public static void updateAutocast(Player player, boolean login) {
        if (login) {
            int index = Config.AUTOCAST.get(player);
            player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[index];
        } else {
            if (player.isVisibleInterface(Interface.AUTOCAST_SELECTION))
                open(player, Interface.COMBAT_OPTIONS);
            resetAutocast(player);
        }
    }

    public static void resetAutocast(Player player) {
        if (player.getCombat().autocastSpell != null) {
            player.getCombat().autocastSpell = null;
            Config.AUTOCAST.set(player, 0);
            player.getCombat().updateCombatLevel();
        }
    }

    private static void selectAutocast(Player player, int slot) {
        if (slot < 0 || slot >= TargetSpell.AUTOCASTS.length)
            return;
        if (slot != 0) {
            player.getCombat().autocastSpell = TargetSpell.AUTOCASTS[slot];
            player.getCombat().lastAutoCastIndex = slot;
            Config.AUTOCAST.set(player, slot);
        }
        open(player, Interface.COMBAT_OPTIONS);
        player.getCombat().updateWeapon(true);
        player.getCombat().updateCombatLevel();
    }

    public static Integer getAutocastId(Player player) {
        Item item = player.getEquipment().get(Equipment.SLOT_WEAPON);
        int staffId = item == null ? -1 : player.getEquipment().getId(Equipment.SLOT_WEAPON);
        int amuletId = item == null ? -1 : player.getEquipment().getId(Equipment.SLOT_AMULET);
        if (staffId == -1) //shouldn't happen
            return null;
        if (staffId == 4675) //ancient staff
            return SpellBook.ANCIENT.isActive(player) ? 4675 : null;
        if (amuletId == 12853 && staffId == 4710) //ahrims ancients
            return SpellBook.ANCIENT.isActive(player) ? 4675 : staffId;
        if (staffId == 6914 || staffId == 20560) { //master wand
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        if (staffId == 22647) { //Zuriel's Staff
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        if (staffId == 22555) { //Thammaron's Sceptre
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        if (staffId == 22552) { //Thammaron's Sceptre Uncharged
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        //
        if (staffId == 11791 || staffId == 12904) { //staff of the dead
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        if (staffId == 2415 || staffId == 2416 || staffId == 2417) { //God Staffs
            if (SpellBook.MODERN.isActive(player)) {
                return 11791;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
            return null;
        }
        if ((staffId == 21006 || staffId == 30181
                || staffId == 24422 || staffId == 25517
                || staffId == 24425 || staffId == 24423
                || staffId == 24424) && SpellBook.ANCIENT.isActive(player)) //kodai wand
            return 4675;
        if (staffId == 1409 || staffId == 12658) //ibans staff
            return SpellBook.MODERN.isActive(player) ? 1409 : null;
        if (staffId == 4170)
            return SpellBook.MODERN.isActive(player) ? 4170 : null;
        if (staffId == 22296) { //staff of light
            if (SpellBook.MODERN.isActive(player)) {
                return 22296;
            } else if (SpellBook.ANCIENT.isActive(player)) {
                return 4675;
            }
        }
        return SpellBook.MODERN.isActive(player) ? -1 : null;
    }

    public static SpellBook getSpellBookForWeapon(Player player) {
        int staffId = player.getEquipment().getId(Equipment.SLOT_WEAPON);
        if (Config.MAGIC_BOOK.get(player) == SpellBook.ANCIENT.ordinal()) {
            if (staffId == 4675) //ancient staff
                return SpellBook.ANCIENT;
            if (staffId == 6914) //master wand
                return SpellBook.ANCIENT;
            if (staffId == 4170) // ahrim staff
                return SpellBook.ANCIENT;
            if (staffId == 11791 || staffId == 12904 || staffId == 4170 || staffId == 21255) //staff of the dead
                return SpellBook.MODERN;
            if (staffId == 21006) //kodai wand
                return SpellBook.ANCIENT;
            if (staffId == 24422) //Nightmare staff
                return SpellBook.ANCIENT;
            if (staffId == 12904) //Nightmare staff
                return SpellBook.ANCIENT;
            if (staffId == 24423) //Harmonised staff
                return SpellBook.ANCIENT;
            if (staffId == 24425) //Eldritch staff
                return SpellBook.ANCIENT;
            if (staffId == 24424) //Volatile Staff
                return SpellBook.ANCIENT;
            return null;
        } else {
            if (staffId == 1409 || staffId == 12658) //ibans staff
                return SpellBook.MODERN;
            if (staffId == 22296) //staff of light
                return SpellBook.MODERN;
            if (staffs.stream().anyMatch(s -> s.intValue() == staffId))
                return SpellBook.MODERN;
            return null;
        }
    }

    private static final List<Integer> staffs = Arrays.asList(1379, 1381, 1383, 1385, 1387, 1389, 1391, 1393, 1395, 1397, 1399, 1401, 1403, 1405, 1407, 1409, 2415, 2416, 2417, 3053, 3054,
            6562, 6563, 9084, 21006, 4170, 4710, 12902, 12904, 20730, 20733, 20736, 20739, 21198, 21200, 22296, 24144, 24422, 24423, 24425, 24424, 11998); //These are staffs that can save autocasts

}