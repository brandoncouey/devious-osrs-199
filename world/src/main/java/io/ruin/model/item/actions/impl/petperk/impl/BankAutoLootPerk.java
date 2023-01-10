package io.ruin.model.item.actions.impl.petperk.impl;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.petperk.Perk;
import io.ruin.model.item.actions.impl.petperk.PerkType;

public class BankAutoLootPerk implements Perk {
    @Override
    public PerkType perkType() {
        return PerkType.BANK_AUTO_LOOT;
    }

    public boolean sentBank(Player player, NPC victim, Item drop) {
        return false;
    }

}
