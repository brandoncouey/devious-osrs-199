package io.ruin.model.skills.crafting;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.stat.StatType;

import java.util.concurrent.atomic.AtomicInteger;

public enum Mould {
    //Rings
    GOLD_RING(1592, 1635, -1, 5, 15.0, 7),
    SAPPHIRE_RING(1592, 1637, 1607, 20, 40.0, 8),
    EMERALD_RING(1592, 1639, 1605, 27, 55.0, 9),
    RUBY_RING(1592, 1641, 1603, 34, 70.0, 10),
    DIAMOND_RING(1592, 1643, 1601, 43, 85.0, 11),
    DRAGONSTONE_RING(1592, 1645, 1615, 55, 100.0, 12),
    ONYX_RING(1592, 6575, 6573, 67, 115.0, 13),
    ZENYTE_RING(1592, 19538, 19493, 89, 150.0, 14),
    SLAYER_RING(1592, 11866, 4155, 75, 15.0, 15),

    //Necklaces
    GOLD_NECKLACES(1597, 1654, -1, 6, 20.0, 21),
    SAPPHIRE_NECKLACES(1597, 1656, 1607, 22, 55.0, 22),
    EMERALD_NECKLACES(1597, 1658, 1605, 29, 60.0, 23),
    RUBY_NECKLACES(1597, 1660, 1603, 40, 75.0, 24),
    DIAMOND_NECKLACES(1597, 1662, 1601, 56, 90.0, 25),
    DRAGONSTONE_NECKLACES(1597, 1664, 1615, 72, 105.0, 26),
    ONYX_NECKLACES(1597, 6577, 6573, 82, 120.0, 27),
    ZENYTE_NECKLACE(1597, 19535, 19493, 92, 165.0, 28),

    //Amulets
    GOLD_AMULETS(1595, 1673, -1, 8, 30.0, 34),
    SAPPHIRE_AMULETS(1595, 1675, 1607, 24, 65.0, 35),
    EMERALD_AMULETS(1595, 1677, 1605, 31, 70.0, 36),
    RUBY_AMULETS(1595, 1679, 1603, 50, 85.0, 37),
    DIAMOND_AMULETS(1595, 1681, 1601, 70, 100.0, 38),
    DRAGONSTONE_AMULETS(1595, 1683, 1615, 80, 150.0, 39),
    ONYX_AMULETS(1595, 6579, 6573, 90, 165.0, 40),
    ZENYTE_AMULETS(1595, 19501, 19493, 98, 200.0, 41),

    //Bracelets
    GOLD_BRACELET(11065, 11068, -1, 7, 25.0, 46),
    SAPPHIRE_BRACELET(11065, 11071, 1607, 23, 60.0, 48),
    EMERALD_BRACELET(11065, 11076, 1605, 30, 65.0, 49),
    RUBY_BRACELET(11065, 11085, 1603, 42, 80.0, 50),
    DIAMOND_BRACELET(11065, 11092, 1601, 58, 95.0, 51),
    DRAGONSTONE_BRACELET(11065, 11115, 1615, 74, 110.0, 52),
    ONYX_BRACELET(11065, 11130, 6573, 84, 125.0, 53),
    ZENYTE_BRACELET(11065, 19532, 19493, 95, 180.0, 54);

    public final int mould, jewellery, gem, levelReq, child;
    public final double exp;

    Mould(int mould, int jewellery, int gem, int levelReq, double exp, int child) {
        this.mould = mould;
        this.jewellery = jewellery;
        this.gem = gem;
        this.levelReq = levelReq;
        this.exp = exp;
        this.child = child;
    }

    private static final int GOLD_BAR = 2357;
    private static final int EMPTY_BUCKET = 3727;
    private static final int SODA_ASH = 1781;
    private static final int MOLTEN_GLASS = 1775;
    private static final int BUCKET_OF_SAND = 1783;

    private static void craft(Player player, Mould mould, int amount) {
        player.closeInterfaces();
        if (!player.getStats().check(StatType.Crafting, mould.levelReq, "make this"))
            return;
        if (mould == Mould.SLAYER_RING && Config.RING_BLING.get(player) == 0) {
            player.sendMessage("You haven't unlocked the ability to craft this ring.");
            return;
        }
        player.startEvent(event -> {
            int amt = amount;
            while (amt-- > 0) {
                Item gem = player.getInventory().findItem(mould.gem);
                if (mould.gem != -1 && gem == null)
                    return;
                Item goldBar = player.getInventory().findItem(GOLD_BAR);
                if (goldBar == null)
                    return;
                Item mouldItem = player.getInventory().findItem(mould.mould);
                if (mouldItem == null)
                    return;
                player.animate(899);
                event.delay(3);
                if (gem != null)
                    gem.remove();
                goldBar.remove();
                player.getInventory().add(mould.jewellery, 1);
                player.getStats().addXp(StatType.Crafting, mould.exp, true);
                event.delay(1);
            }
        });
    }

    private static void option(Player player, Mould mould, int amount) {
        if (amount < 1) {
            player.sendMessage("You need to select an amount");
            return;
        }
        if (amount == 1) {
            craft(player, mould, 1);
        } else if (amount == 5) {
            craft(player, mould, 5);
        } else if (amount == 10) {
            craft(player, mould, 10);
        } else if (amount == 1000) {
            craft(player, mould, Integer.MAX_VALUE);
        } else {
            craft(player, mould, amount);
        }
    }

    static {
        /*
         * Gold bar
         */
        ItemObjectAction.register(GOLD_BAR, "furnace", (player, item, object) -> player.sendMessage("You need to use a mould on the furnace in order to smelt jewelry."));

        /*
         * Moulds
         */
        Integer[] moulds = {1592, 1595, 1597, 11065};
        for (int id : moulds) {
            ItemObjectAction.register(id, "furnace", (player, item, obj) -> player.openInterface(InterfaceType.MAIN, Interface.MOULD));
        }

        /*
         * Molten glass
         */
        SkillItem moltenGlass = new SkillItem(MOLTEN_GLASS).addAction((player, amount, event) -> {
            while (amount-- > 0) {
                Item sodaAsh = player.getInventory().findItem(SODA_ASH);
                if (sodaAsh == null) {
                    player.sendMessage("You need some soda ash to make glass.");
                    break;
                }
                Item bucketOfSand = player.getInventory().findItem(BUCKET_OF_SAND);
                if (bucketOfSand == null) {
                    player.sendMessage("You need some sand to make glass.");
                    break;
                }
                if (!player.discardBuckets && !player.getInventory().hasRoomFor(MOLTEN_GLASS)) {
                    player.sendMessage("Not enough space in your inventory.");
                    player.sendMessage("You can avoid this by toggling on the \"Discard Buckets\" setting.");
                    break;
                }
                player.animate(899);
                event.delay(2);
                sodaAsh.remove();
                if (player.discardBuckets)
                    bucketOfSand.setId(MOLTEN_GLASS);
                else {
                    bucketOfSand.setId(EMPTY_BUCKET);
                    player.getInventory().add(MOLTEN_GLASS, 1);
                }
                player.getStats().addXp(StatType.Crafting, 10, true);
                event.delay(2);
            }
            player.resetAnimation();
        });
        ItemObjectAction.register(BUCKET_OF_SAND, "furnace", (player, item, obj) -> {
            SkillDialogue.make(player, moltenGlass);
        });
        ItemObjectAction.register(SODA_ASH, "furnace", (player, item, obj) -> {
            SkillDialogue.make(player, moltenGlass);
        });

        /*
         * Mould interface options
         */
        InterfaceHandler.register(Interface.MOULD, h -> {
            AtomicInteger amount = new AtomicInteger();
            h.actions[56] = (SimpleAction) (p) -> amount.set(1);
            h.actions[57] = (SimpleAction) (p) -> amount.set(5);
            h.actions[58] = (SimpleAction) (p) -> amount.set(10);
            h.actions[59] = (SimpleAction) (p) -> p.integerInput("How many would you like to smith!", amount::set);
            h.actions[60] = (SimpleAction) (p) -> amount.set(1000);
            /*
             * Rings
             */
            h.actions[GOLD_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, GOLD_RING, amount.get());
            h.actions[SAPPHIRE_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, SAPPHIRE_RING, amount.get());
            h.actions[EMERALD_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, EMERALD_RING, amount.get());
            h.actions[RUBY_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, RUBY_RING, amount.get());
            h.actions[DIAMOND_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DIAMOND_RING, amount.get());
            h.actions[DRAGONSTONE_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DRAGONSTONE_RING, amount.get());
            h.actions[ONYX_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ONYX_RING, amount.get());
            h.actions[ZENYTE_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ZENYTE_RING, amount.get());
            h.actions[SLAYER_RING.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, SLAYER_RING, amount.get());
            /*
             * Necklaces
             */
            h.actions[GOLD_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, GOLD_RING, amount.get());
            h.actions[SAPPHIRE_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, SAPPHIRE_NECKLACES, amount.get());
            h.actions[EMERALD_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, EMERALD_NECKLACES, amount.get());
            h.actions[RUBY_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, RUBY_NECKLACES, amount.get());
            h.actions[DIAMOND_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DIAMOND_NECKLACES, amount.get());
            h.actions[DRAGONSTONE_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DRAGONSTONE_NECKLACES, amount.get());
            h.actions[ONYX_NECKLACES.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ONYX_NECKLACES, amount.get());
            h.actions[ZENYTE_NECKLACE.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ZENYTE_NECKLACE, amount.get());
            /*
             * Amulets
             */
            h.actions[GOLD_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, GOLD_AMULETS, amount.get());
            h.actions[SAPPHIRE_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, SAPPHIRE_AMULETS, amount.get());
            h.actions[EMERALD_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, EMERALD_AMULETS, amount.get());
            h.actions[RUBY_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, RUBY_AMULETS, amount.get());
            h.actions[DIAMOND_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DIAMOND_AMULETS, amount.get());
            h.actions[DRAGONSTONE_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DRAGONSTONE_AMULETS, amount.get());
            h.actions[ONYX_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ONYX_AMULETS, amount.get());
            h.actions[ZENYTE_AMULETS.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ZENYTE_AMULETS, amount.get());
            /*
             * Bracelets
             */
            h.actions[GOLD_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, GOLD_BRACELET, amount.get());
            h.actions[SAPPHIRE_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, SAPPHIRE_BRACELET, amount.get());
            h.actions[EMERALD_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, EMERALD_BRACELET, amount.get());
            h.actions[RUBY_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, RUBY_BRACELET, amount.get());
            h.actions[DIAMOND_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DIAMOND_BRACELET, amount.get());
            h.actions[DRAGONSTONE_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, DRAGONSTONE_BRACELET, amount.get());
            h.actions[ONYX_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ONYX_BRACELET, amount.get());
            h.actions[ZENYTE_BRACELET.child] = (DefaultAction) (p, childId, option, slot, itemId) -> option(p, ZENYTE_BRACELET, amount.get());
        });
    }
}
