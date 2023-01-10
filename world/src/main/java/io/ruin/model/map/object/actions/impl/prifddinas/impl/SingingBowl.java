package io.ruin.model.map.object.actions.impl.prifddinas.impl;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.skill.SkillDialogue;
import io.ruin.model.inter.dialogue.skill.SkillItem;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

import java.util.Objects;


public enum SingingBowl {

    CRYSTAL_HELM(70, 70, 2500, 23971, 50),
    CRYSTAL_LEGS(72, 72, 5000, 23979, 100),
    CRYSTAL_BODY(74, 74, 7500, 23975, 150),
    CRYSTAL_AXE(76, 76, 6000, 23673, 120),
    CRYSTAL_HARPOON(76, 76, 6000, 23762, 120),
    CRYSTAL_PICKAXE(76, 76, 6000, 23680, 120),
    CRYSTAL_BOW(78, 78, 10000, 23983, 40),
    CRYSTAL_HALBERD(78, 78, 10000, 23987, 40),
    CRYSTAL_SHIELD(78, 78, 10000, 23991, 40),
    ENHANCED_CKEY(80, 80, 12000, 23951, 10),
    BLADE_OF_SAELDOR(82, 82, 5000, 23995, 100),
    BOW_OF_FAERDHINEN(82, 82, 5000, 25865, 100),

    ;
//    ETERNAL_CRYSTAL(80, 80, 12000, 23946, 100);

    public final int levelRequired;

    public final int levelRequired2;

    public final double experience;

    public final int product;

    public final int shardAmount;

    SingingBowl(int levelRequired, int levelRequired2, double experience, int product, int shardAmount) {
        this.levelRequired = levelRequired;
        this.levelRequired2 = levelRequired2;
        this.experience = experience;
        this.product = product;
        this.shardAmount = shardAmount;
        Objects.requireNonNull(ItemDef.get(product)).singingBowl = this;
    }

    private static void open(Player player) {
        SkillDialogue.make(
                player,
                (p, item) -> makeItem(player, item.getDef()),
                new SkillItem(23971),
                new SkillItem(23979),
                new SkillItem(23975),
                new SkillItem(23673),
                new SkillItem(23762),
                new SkillItem(23680),
                new SkillItem(23983),
                new SkillItem(23987),
                new SkillItem(23991),
                new SkillItem(23951)
        );
    }

    private static void openEnhanced(Player player) {
        SkillDialogue.make(
                player,
                (p, item) -> makeItem(player, item.getDef()),
                new SkillItem(23995),
                new SkillItem(30372)
        );
    }

    private static void makeItem(Player player, ItemDef def /*SingingBowl singingBowl*/) {
        player.closeInterface(InterfaceType.CHATBOX);
        SingingBowl singingBowl = def.singingBowl;
        ItemObjectAction.register(989, 36552, (player1, item, obj) -> {
            for (int i = 0; i < player.getInventory().count(989); i++) {
                player.getInventory().remove(989, 1);
                player.getInventory().remove(23962, singingBowl.shardAmount);
                player.animate(899);
                player.getInventory().add(23951, 1);
                double xp = singingBowl.experience;
                player.getStats().addXp(StatType.Crafting, xp, false);
                player.getStats().addXp(StatType.Smithing, xp, false);
            }
        });
        player.startEvent(event -> {
            if (!player.getStats().check(StatType.Crafting, singingBowl.levelRequired)) {
                player.sendMessage("<col=880000>You need atleast level " + singingBowl.levelRequired + " Crafting to continue");
                return;
            }
            if (!player.getStats().check(StatType.Smithing, singingBowl.levelRequired2)) {
                player.sendMessage("<col=880000>You need atleast level " + singingBowl.levelRequired2 + " Smithing to continue");
                return;
            }
            if (!player.getInventory().contains(23962, singingBowl.shardAmount)) {
                player.sendMessage("<col=880000>You don't have the required items to continue.");
                return;
            }

            switch (singingBowl) {
                case CRYSTAL_HELM: {
                    if (!player.getInventory().contains(23956)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23956, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_BODY: {
                    if (!player.getInventory().contains(23956, 3)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23956, 3);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_LEGS: {
                    if (!player.getInventory().contains(23956, 2)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23956, 2);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_BOW:
                case CRYSTAL_HALBERD:
                case CRYSTAL_SHIELD: {
                    if (!player.getInventory().contains(4207)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(4207, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case ENHANCED_CKEY: {
                    if (!player.getInventory().contains(989)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(989, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_AXE: {
                    if (!player.getInventory().contains(23953) && !player.getInventory().contains(6739)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23953, 1);
                        player.getInventory().remove(6739, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_HARPOON: {
                    if (!player.getInventory().contains(23953) && !player.getInventory().contains(21028)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23953, 1);
                        player.getInventory().remove(21028, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case CRYSTAL_PICKAXE: {
                    if (!player.getInventory().contains(23953) && !player.getInventory().contains(11920)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(23953, 1);
                        player.getInventory().remove(11920, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }
                case BLADE_OF_SAELDOR:
                case BOW_OF_FAERDHINEN: {
                    if (!player.getInventory().contains(30370)) {
                        player.sendMessage("<col=880000>You don't have the required items to continue.");
                    } else {
                        player.getInventory().remove(30370, 1);
                        player.getInventory().remove(23962, singingBowl.shardAmount);
                        player.animate(899);
                        player.getInventory().add(singingBowl.product, 1);
                        double xp = singingBowl.experience;
                        player.getStats().addXp(StatType.Crafting, xp, false);
                        player.getStats().addXp(StatType.Smithing, xp, false);
                        return;
                    }
                    return;
                }

            }
        });
    }

    static {
        ObjectAction.register(36552, "sing-crystal", (player, obj) -> open(player));
        ObjectAction.register(36552, "enhance-crystal", (player, obj) -> openEnhanced(player));
    }

}
