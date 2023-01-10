package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

public class GiveSpecial extends Spell {

    public GiveSpecial() {
        Item[] runes = {
                Rune.LAW.toItem(2),
                Rune.ASTRAL.toItem(3),
                Rune.NATURE.toItem(1)
        };
        registerEntity(91, runes, (player, entity) -> {
            if (DuelRule.NO_MAGIC.isToggled(player)) {
                player.sendMessage("Magic has been disabled for this duel!");
                return false;
            }
            if (Config.SPECIAL_ENERGY.get(entity.player) == 1000) {
                player.sendMessage("This player already has full special");
                return false;
            }
            if (Config.SPECIAL_ENERGY.get(player) < 1000) {
                player.sendMessage("You must have full Special attack to use this spell.");
                return false;
            }
            if (player.getStats().get(StatType.Defence).currentLevel < 40) {
                player.sendMessage("You need at least 40 defence to cast Restore Special.");
                return false;
            }
            player.resetActions(true, true, false);
            player.animate(6294);
            entity.player.graphics(732);
            Config.SPECIAL_ENERGY.set(player, 0);
            entity.player.getCombat().restoreSpecial(100);
            return true;
        });
    }

}