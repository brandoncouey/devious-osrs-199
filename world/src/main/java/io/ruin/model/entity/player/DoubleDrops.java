package io.ruin.model.entity.player;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.item.actions.impl.pets.Pets;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/11/2020
 */
public class DoubleDrops {

    /*
     * Math to retrieve loot rolls after a kill
     */
    public static int getRolls(Player player) {
        int doubleDropChance = getChance(player);
        int rolls = 1;
        if (World.doubleDrops)
            rolls++;
        if (World.isPVPWorld())
            rolls++;

        if (player.amountDonated > 0) {
            if (Random.get(1, 100) <= doubleDropChance) {
                rolls++;
            }
        }

        return rolls;
    }

    public static int doubleAmount(Player player) {
        int doubleDropChance = getChance(player);
        int amount = 1;
        if (World.doubleDrops)
            amount++;
        if (World.isPVPWorld())
            amount++;

        if (player.amountDonated > 0) {
            if (Random.get(1, 100) <= doubleDropChance) {
                amount++;
            }
        }

        return amount;
    }

    /*
     * Method to display a visual chance at rolling multiple drops
     */
    public static int getChance(Player player) {
        int chance = 1;
        if (World.doubleDrops)
            chance += 100;
        if (World.isPVPWorld())
            chance += 100;
        if(player.pet == Pets.BLOODHOUND)
            chance += 50;
        if (player.xpMode == XpMode.NORMAL)
        chance += XpMode.NORMAL.getDropBonus();
        if (player.xpMode == XpMode.REALISTIC)
        chance += XpMode.REALISTIC.getDropBonus();
        if (player.xpMode == XpMode.MEDIUM)
        chance += XpMode.MEDIUM.getDropBonus();
        if (player.xpMode == XpMode.HARD)
        chance += XpMode.HARD.getDropBonus();
        if (player.amountDonated > 0)
            chance += getDoubleDropChance(player);
        return chance;
    }

    public static int getDoubleDropChance(Player player) {
        int doubleDropChance = player.getPrimaryGroup().getDoubleDropChance();
        if (player.isGroups(SecondaryGroup.DIAMOND)) {
            doubleDropChance += SecondaryGroup.DIAMOND.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.RUBY)) {
            doubleDropChance += SecondaryGroup.RUBY.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.EMERALD)) {
            doubleDropChance += SecondaryGroup.EMERALD.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.SAPPHIRE)) {
            doubleDropChance += SecondaryGroup.SAPPHIRE.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.RED_TOPAZ)) {
            doubleDropChance += SecondaryGroup.RED_TOPAZ.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.JADE)) {
            doubleDropChance += SecondaryGroup.JADE.getDoubleDropChance();
        } else if (player.isGroups(SecondaryGroup.OPAL)) {
            doubleDropChance += SecondaryGroup.OPAL.getDoubleDropChance();
        }
        return doubleDropChance;
    }
}
