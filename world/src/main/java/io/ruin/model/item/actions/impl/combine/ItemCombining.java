package io.ruin.model.item.actions.impl.combine;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ItemDialogue;
import io.ruin.model.inter.dialogue.YesNoDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;

public enum ItemCombining {

    FROZEN_ABYSSAL_WHIP(4151, 12769, 12774, false),
    AMULET_OF_BLOOD_FURY(6585, 24777, 24780, true),
    VOLCANIC_ABYSSAL_WHIP(4151, 12771, 12773, false),
    BLUE_BOW_MIX(12757, 11235, 12766, false),
    GREEN_BOW_MIX(12759, 11235, 12765, false),
    YELLOW_BOW_MIX(12761, 11235, 12767, false),
    WHITE_BOW_MIX(12763, 11235, 12768, false),
    DEVOUT_BOOTS(12598, 22960, 22954, false),
    ODIUM_WARD(12802, 11926, 12807, true),
    MALEDICTION_WARD(12802, 11924, 12806, true),
    AMULET_OF_FURY(6585, 12526, 12436, true),
    GRANITE_MAUL(12849, 4153, 12848, true),
    DRAGON_DEFENDER(12954, 20143, 19722, true),
    DRAGON_SCIMITAR(4587, 20002, 20000, true),
    DRAGON_PICKAXE(11920, 12800, 12797, true),
    AMULET_OF_TORTURE(19553, 20062, 20366, true),
    TORMENTED_BRACELET(19544, 23348, 23444, true),
    GOLD_LANCE(22978, 30378, 30361, true),
    SEA_LANCE(22978, 30384, 30365, true),
    GREEN_LANCE(22978, 30383, 30360, true),
    RED_LANCE(22978, 30382, 30364, true),
    PINK_LANCE(22978, 30381, 30362, true),
    CYAN_LANCE(22978, 30380, 30354, true),
    PURPL_LANCE(22978, 30379, 30363, true),
    GOLD_TBOW(20997, 30378, 30368, true),
    SEA_TBOW(20997, 30384, 30371, true),
    GREEN_TBOW(20997, 30383, 30367, true),
    RED_TBOW(20997, 30382, 30370, true),
    CYAN_TBOW(20997, 30380, 30366, true),
    PURPL_TBOW(20997, 30379, 30369, true),
    ARMADYL_GODSWORD(11802, 20068, 20368, true),
    BANDOS_GODSWORD(11804, 20071, 20370, true),
    ZAMORAK_GODSWORD(11808, 20077, 20374, true),
    SARADOMIN_GODSWORD(11806, 20074, 20372, true),
    DRAGON_CHAINBODY(2513, 12534, 12414, true),
    GUTHIX_SCIMITAR(1333, 23321, 23330, true),
    ZAMORAK_SCIMITAR(1333, 23327, 23334, true),
    SARADOMIN_SCIMITAR(1333, 23324, 23332, true),
    OCCULT_NECKLACE(12002, 20065, 19720, true),
    DRAGONFIRE_SHIELD(11286, 1540, 11283, false),
    ANCIENT_WYVERN_SHIELD(21637, 2890, 21634, false),
    DRAGONFIRE_WARD(22006, 1540, 22003, false),
    BERSERKER_NECKLACE(23237, 11128, 23240, true),
    TZHAAR_KET_OM(6528, 23232, 23235, true),
    DRAGON_CHAIN(3140, 12534, 12414, true),
    DRAGON_PLATEBODY(21892, 22236, 22242, true),
    DRAGON_PLATELEGS(4087, 12536, 12415, true),
    DRAGON_SKIRT(4585, 12536, 12416, true),
    DRAGON_FULL_HELM(11335, 12538, 12417, true),
    DRAGON_SQUARE(1187, 12532, 12418, true),
    DRAGON_KITE(21895, 22239, 22244, true),
    RUNE_DEFENDER(8850, 23227, 23230, true),
    NORMAL_BATTLESTAFF(11787, 12798, 12795, true),
    MYSTIC_BATTLESTAFF(11789, 12798, 12796, true),
    DARK_INFINITY_HAT(12528, 6918, 12457, true),
    DARK_INFINITY_TOP(12528, 6916, 12458, true),
    DARK_INFINITY_BOTTOMS(12528, 6924, 12459, true),
    LIGHT_INFINITY_HAT(12530, 6918, 12419, true),
    LIGHT_INFINITY_TOP(12530, 6916, 12420, true),
    LIGHT_INFINITY_BOTTOMS(12530, 6924, 12421, true),
    NEITIZNOT_FACEGUARD(24268, 10828, 24271, true),
    AVERNIC_DEFENDER(12954, 22477, 22322, true),
    DRAGON_BOOTS(11840, 22231, 22234, true),
    SARADOMINS_LIGHT(11791, 13256, 22296, false),
    NECKLACE_OF_ANGUISH(19547, 22246, 22249, true),
    BONECRUSHER_NECKLACE(22988, 13116, 22986, false),
    DRAGON_HUNTER_LANCE(22966, 11889, 22978, false),
    BRIMSTONE_BOOTS(23037, 22957, 22951, false),
    KODAI_WAND(21043, 6914, 21006, false),
    VOLATILE_STAFF(24422, 24514, 24424, true),
    ELDRITCH_STAFF(24422, 24517, 24425, true),
    HARMONISED_STAFF(24422, 24511, 24423, true),
    LAVA_BATTLESTAFF(3053, 21202, 21198, false),
    BLOOD_FURY(6585, 24777, 24780, true),
    ANCIENT_WYVERNSHIELD(21637, 2890, 21633, false),
    SLAYER_STAFF_E(4170, 21257, 21255, false),
    SANGSCYTHE(25744, 22486, 25741, true),
    HOLYSCYTHE(25742, 22486, 25738, true),
    HOLYRAPIER(25742, 22324, 25734, true),
    HOLYSANG(22481, 25742, 25733, true),
    ELDERMAUL_OR(21003, 30324, 30325, true),
    BOW_OF_FAERDHINEN_BLUE(25865, 23941, 25896, false),
    BOW_OF_FAERDHINEN_YELLOW(25865, 23935, 25892, false),
    BOW_OF_FAERDHINEN_BLACK(25865, 23929, 25886, false),
    BOW_OF_FAERDHINEN_WHITE(25865, 23927, 25884, false),
    BOW_OF_FAERDHINEN_GREEN(25865, 23933, 25890, false),
    BOW_OF_FAERDHINEN_PURPLE(25865, 23931, 25888, false),
    BOW_OF_FAERDHINEN_RED(25865, 23939, 25867, false),
    BOW_OF_FAERDHINEN_CYAN(25865, 23937, 25894, false),
    BLADE_OF_SAL_BLUE(23995, 23941, 25882, false),
    BLADE_OF_SAL_YELLOW(23995, 23935, 25878, false),
    BLADE_OF_SAL_BLACK(23995, 23929, 25872, false),
    BLADE_OF_SAL_GREEN(23995, 23933, 25876, false),
    BLADE_OF_SAL_PURPLE(23995, 23931, 25874, false),
    BLADE_OF_SAL_RED(23995, 23939, 24551, false),
    BLADE_OF_SAL_CYAN(23995, 23937, 23995, false);



    public final int primaryId, secondaryId, combinedId;
    public final boolean reversible;

    ItemCombining(int primaryId, int secondaryId, int combinedId, boolean reversible) {
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
        this.combinedId = combinedId;
        this.reversible = reversible;
        ItemDef.get(combinedId).combinedFrom = this;
    }

    private static void make(Player player, Item item, Item kit, int resultID, boolean reversible) {
        String message;
        if (reversible)
            message = "Combine the " + item.getDef().name + " and " + kit.getDef().name + "?";
        else
            message = "Combining these items will be irreversible";
        player.dialogue(
                new YesNoDialogue("Are you sure you want to do this?", message, resultID, 1, () -> {
                    player.animate(713);
                    Item result = new Item(resultID, 1);
                    item.remove();
                    kit.remove();
                    player.getInventory().add(result);
                    new ItemDialogue().one(resultID, "You apply the " + item.getDef().name + " to the " + kit.getDef().name + ".");
                })
        );
    }

    private static void revert(Player player, Item kit, int primary, int revert) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.sendMessage("You don't have enough inventory space to do this.");
            return;
        }
        Item item = new Item(primary, 1);
        if (kit.getId() == BLOOD_FURY.combinedId) {
            player.dialogue(
                    new YesNoDialogue("Are you sure you want to do this?", "Revert the item back to its normal form and lose the blood shard?", primary, 1, () -> {
                        kit.setId(primary);
                        new ItemDialogue().one(primary, "You remove the " + kit.getDef().name + " from the " + item.getDef().name + ".");
                    })
            );
        } else {
            player.dialogue(
                    new YesNoDialogue("Are you sure you want to do this?", "Revert the item back to its normal form and get the kit back?", primary, 1, () -> {
                        player.getInventory().add(item);
                        kit.setId(revert);
                        new ItemDialogue().one(primary, "You remove the " + kit.getDef().name + " from the " + item.getDef().name + ".");
                    })
            );
        }
    }

    private static final int[] boots = {13239, 13237, 13235};

    static {
        for (ItemCombining kit : values()) {
            ItemItemAction.register(kit.primaryId, kit.secondaryId, (player, primary, secondary) -> make(player, primary, secondary, kit.combinedId, kit.reversible));
            ItemAction.registerInventory(kit.combinedId, "dismantle", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            ItemAction.registerInventory(kit.combinedId, "revert", (player, item) -> revert(player, item, kit.primaryId, kit.secondaryId));
            int combinedProtect = ItemDef.get(kit.combinedId).protectValue;
            int componentsProtect = Math.max(ItemDef.get(kit.primaryId).protectValue, ItemDef.get(kit.secondaryId).protectValue);
            if (combinedProtect < componentsProtect)
                ItemDef.get(kit.combinedId).protectValue = componentsProtect;
        }
        ItemItemAction.register(11335, 19677, (player, primary, secondary) -> {
            if (secondary.getAmount() < 250) {
                player.sendFilteredMessage("You don't have enough ancient shards.");
                return;
            }
            player.animate(713);
            secondary.remove(250);
            primary.setId(30278);
        });

        ItemItemAction.register(21892, 19677, (player, primary, secondary) -> {
            if (secondary.getAmount() < 1000) {
                player.sendFilteredMessage("You don't have enough ancient shards.");
                return;
            }
            player.animate(713);
            secondary.remove(1000);
            primary.setId(30279);
        });

        ItemItemAction.register(4087, 19677, (player, primary, secondary) -> {
            if (secondary.getAmount() < 750) {
                player.sendFilteredMessage("You don't have enough ancient shards.");
                return;
            }
            player.animate(713);
            secondary.remove(750);
            primary.setId(30280);
        });

        ItemItemAction.register(4151, 19677, (player, primary, secondary) -> {
            if (secondary.getAmount() < 500) {
                player.sendFilteredMessage("You don't have enough ancient shards.");
                return;
            }
            if (player.getInventory().count(12004) < 3) {
                player.sendFilteredMessage("You don't have enough tentacles.");
                return;
            }
            if (!player.getInventory().contains(primary.getId(), 3)) {
                player.sendFilteredMessage("You don't have enough whips.");
                return;
            }
            player.getInventory().remove(12004, 3);
            player.animate(713);
            secondary.remove(500);
            primary.remove(3);
            player.getInventory().add(30276, 1);
        });

        for (int boot : boots)
            ItemItemAction.register(boot, 19677, (player, primary, secondary) -> {
                if (secondary.getAmount() < 500) {
                    player.sendFilteredMessage("You don't have enough ancient shards.");
                    return;
                }
                if (!player.getInventory().contains(13239)) {
                    player.sendMessage("You need a pair of " + ItemDef.get(13239).name);
                    return;
                }
                if (!player.getInventory().contains(13237)) {
                    player.sendMessage("You need a pair of " + ItemDef.get(13237).name);
                    return;
                }
                if (!player.getInventory().contains(13235)) {
                    player.sendMessage("You need a pair of " + ItemDef.get(13235).name);
                    return;
                }

                if (player.getInventory().count(995) < 25_000_000) {
                    player.sendFilteredMessage("You need 25m to create the boots");
                    return;
                }

                player.animate(713);
                player.getInventory().remove(995, 25_000_000);
                secondary.remove(500);
                player.getInventory().remove(13239, 1);
                player.getInventory().remove(13237, 1);
                player.getInventory().remove(13235, 1);
                player.getInventory().add(30285, 1);
            });


        ItemItemAction.register(24271, 24777, (player, primary, secondary) -> {
            if (player.getInventory().count(secondary.getId()) < 5) {
                player.sendFilteredMessage("You need 5 shards to upgrade this item.");
                return;
            }
//faceguard
            player.getInventory().remove(secondary.getId(), 5);
            primary.setId(30275);
        });
        ItemItemAction.register(22547, 24777, (player, primary, secondary) -> {
            if (player.getInventory().count(secondary.getId()) < 5) {
                player.sendFilteredMessage("You need 5 shards to upgrade this item.");
                return;
            }
//bow
            player.getInventory().remove(secondary.getId(), 5);
            primary.setId(30265);
        });
        ItemItemAction.register(22545, 24777, (player, primary, secondary) -> {
            if (player.getInventory().count(secondary.getId()) < 5) {
                player.sendFilteredMessage("You need 5 shards to upgrade this item.");
                return;
            }
            //mace
            player.getInventory().remove(secondary.getId(), 5);
            primary.setId(30266);
        });
        ItemItemAction.register(22542, 24777, (player, primary, secondary) -> {
            if (player.getInventory().count(secondary.getId()) < 5) {
                player.sendFilteredMessage("You need 5 shards to upgrade this item.");
                return;
            }
            //mace
            player.getInventory().remove(secondary.getId(), 5);
            primary.setId(30266);
        });
    }


}