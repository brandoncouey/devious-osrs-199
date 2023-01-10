package io.ruin.model.item.actions.impl.petperk.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.petperk.Perk;
import io.ruin.model.item.actions.impl.petperk.PerkType;

public class BonusSmitePerk implements Perk {
    @Override
    public PerkType perkType() {
        return PerkType.BONUS_SMITE;
    }

    public double getBonusSmitePercentage(Player player) {
        return 0;
    }

}
