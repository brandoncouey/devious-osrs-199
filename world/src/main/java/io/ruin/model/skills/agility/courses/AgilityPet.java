package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.pets.Pets;

public class AgilityPet {

    public static void rollForPet(Player player, int chance) {
        if (Random.rollDie(chance))
            Pets.GIANT_SQUIRREL.unlock(player);
    }

}
