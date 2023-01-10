package io.ruin.model.skills.magic;

import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.skills.magic.rune.RuneRemoval;
import io.ruin.model.stat.StatType;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class Spell {

    public BiConsumer<Player, Integer> clickAction;

    public BiConsumer<Player, Item> itemAction;

    public BiConsumer<Player, GameObject> objectAction;

    public BiConsumer<Player, Entity> entityAction;

    public int BlightedSack = -1;

    public void setBlightedSack(int id) {
        this.BlightedSack = id;
    }

    public final void registerClick(int lvlReq, double xp, boolean useXpMultiplier, Item[] runeItems, BiPredicate<Player, Integer> check) {
        clickAction = (p, i) -> {
            if (p.isLocked())
                return;
            if (!p.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                return;
            RuneRemoval r = null;
            if (runeItems != null && (r = RuneRemoval.get(p, runeItems)) == null && !p.getInventory().hasItem(BlightedSack, 1)) {
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (check.test(p, i)) {
                if (BlightedSack != -1 && p.getInventory().hasItem(BlightedSack, 1)) {
                    p.getInventory().remove(BlightedSack, 1);
                } else if (r != null) {
                    r.remove();
                }
                p.getStats().addXp(StatType.Magic, xp, useXpMultiplier);
            }
        };
    }

    public final void registerBountyClick(int lvlReq, double xp, boolean useXpMultiplier, BiPredicate<Player, Integer> check) {
        clickAction = (p, i) -> {
            if (p.isLocked())
                return;
            if (!p.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                return;
            if (p.getInventory().getAmount(24615) <= 0) {
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (check.test(p, i)) {
                if (p.getInventory().getAmount(24615) >= 1)
                    p.getInventory().remove(24615, 1);
                p.getStats().addXp(StatType.Magic, xp, useXpMultiplier);
            }
        };
    }

    public final void registerItem(int lvlReq, double xp, boolean useXpMultiplier, Item[] runeItems, BiPredicate<Player, Item> check) {
        itemAction = (p, i) -> {
            if (!p.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                return;
            RuneRemoval r = null;
            if (runeItems != null && (r = RuneRemoval.get(p, runeItems)) == null) {
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (check.test(p, i)) {
                if (r != null)
                    r.remove();
                p.getStats().addXp(StatType.Magic, xp, useXpMultiplier);
            }
        };
    }

    public final void registerEntity(int lvlReq, Item[] runeItems, BiPredicate<Player, Entity> check) {
        entityAction = (p, e) -> {
            if (!p.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                return;
            RuneRemoval r = null;
            if (runeItems != null && (r = RuneRemoval.get(p, runeItems)) == null) {
                p.getCombat().lastAutoCastIndex = -1;
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (check.test(p, e)) {
                if (r != null)
                    r.remove();
            }
        };
    }

    public final void registerObject(int lvlReq, Item[] runeItems, BiPredicate<Player, GameObject> check) {
        objectAction = (p, o) -> {
            if (!p.getStats().check(StatType.Magic, lvlReq, "cast this spell"))
                return;
            RuneRemoval r = null;
            if (runeItems != null && (r = RuneRemoval.get(p, runeItems)) == null) {
                p.sendMessage("You don't have enough runes to cast this spell.");
                return;
            }
            if (check.test(p, o)) {
                if (r != null)
                    r.remove();
            }
        };
    }

}