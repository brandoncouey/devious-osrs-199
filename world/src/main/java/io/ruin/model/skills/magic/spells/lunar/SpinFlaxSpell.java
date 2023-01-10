package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

import java.util.List;

public class SpinFlaxSpell extends Spell {

    public SpinFlaxSpell() {
        Item[] runes = {
                Rune.ASTRAL.toItem(1),
                Rune.NATURE.toItem(2),
                Rune.AIR.toItem(5)
        };
        registerClick(76, 78, true, runes, (p, i) -> {
            List<Item> items = p.getInventory().collectItems(1779);
            if (items == null || items.size() == 0) {
                p.sendMessage("You don't have any flax to spin.");
                return false;
            }
            p.startEvent(event -> {
                p.lock();
                p.animate(4413);
                p.graphics(748, 96, 0);
                items.forEach(item -> item.setId(1777));
                p.getStats().addXp(StatType.Crafting, 8 * items.size(), true);
                event.delay(1);
                p.unlock();
            });
            return true;
        });
    }
}
