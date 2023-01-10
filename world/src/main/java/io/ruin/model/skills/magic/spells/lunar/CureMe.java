package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class CureMe extends Spell {

    public CureMe() {
        Item[] runes = {
                Rune.LAW.toItem(1),
                Rune.ASTRAL.toItem(2),
                Rune.COSMIC.toItem(2)
        };
        registerClick(68, 112.0, false, runes, CureMe::cast);
    }

    public static boolean cast(Player player, Integer i) {
        player.resetActions(true, true, false);
        player.animate(4411);
        player.graphics(738, 95, 0);
        player.curePoison((90 * 1000) / 600);
        return true;
    }

}