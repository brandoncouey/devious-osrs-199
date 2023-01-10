package io.ruin.model.item.actions.impl.petperk.impl;

import io.ruin.model.item.actions.impl.petperk.Perk;
import io.ruin.model.item.actions.impl.petperk.PerkType;

public class DragonFireImmunityPerk implements Perk {
    @Override
    public PerkType perkType() {
        return PerkType.DRAGON_FIRE_IMMUNITY;

    }
}