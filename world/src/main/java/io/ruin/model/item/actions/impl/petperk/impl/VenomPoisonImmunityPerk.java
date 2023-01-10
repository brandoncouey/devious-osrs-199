package io.ruin.model.item.actions.impl.petperk.impl;

import io.ruin.model.item.actions.impl.petperk.Perk;
import io.ruin.model.item.actions.impl.petperk.PerkType;

public class VenomPoisonImmunityPerk implements Perk {
    @Override
    public PerkType perkType() {
        return PerkType.VENOM_POISON_IMMUNITY;

    }
}
