package io.ruin.model.item.actions.impl.upgrade.impl;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.impl.upgrade.Upgrade;
import io.ruin.model.item.actions.impl.upgrade.UpgradeSkillRequirements;

import java.util.LinkedList;
import java.util.List;

public class PetPerksUpgrade {

    public static final List<Upgrade> UPGRADES = new LinkedList<>();

    static {

        UPGRADES.add(new Upgrade() {
            @Override
            public Item getUpgradeItem() {
                return null;
            }

            @Override
            public Item[] getMaterials() {
                return new Item[0];
            }

            @Override
            public UpgradeSkillRequirements[] getSkillRequirements() {
                return new UpgradeSkillRequirements[0];
            }

            @Override
            public String getAdditionalRequirements(Player player) {
                return null;
            }

            @Override
            public int getSuccessChance(Player player) {
                return 0;
            }

            @Override
            public void onPurchase(Player player) {

            }

            @Override
            public void onSuccessfulUpgrade(Player player) {

            }
        });
    }
}
