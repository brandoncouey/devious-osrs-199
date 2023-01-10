package io.ruin.model.item.actions.impl.petperk.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.petperk.Perk;
import io.ruin.model.item.actions.impl.petperk.PerkType;

public class BonusPointsPerk implements Perk {

    @Override
    public PerkType perkType() {
        return PerkType.BONUS_POINTS;
    }

    public int getBonusPoints(Player player, BonusPoints bonusPoints, Object... object) {
        return 0;
    }

    public enum BonusPoints {
        SLAYER
    }

}
