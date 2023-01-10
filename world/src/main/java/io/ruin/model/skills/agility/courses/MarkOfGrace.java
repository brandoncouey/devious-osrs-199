package io.ruin.model.skills.agility.courses;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SecondaryGroup;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.ground.GroundItem;

import java.util.List;

public class MarkOfGrace {

    public static void rollMark(Player player, int levelReq, List<Position> spawns) {
        if (spawns == null)
            return;
        double chance = levelReq / 2 / 100.0;
        if (Random.get() <= chance) {
            Position spawn = Random.get(spawns);
            new GroundItem(new Item(11849, (Random.get(1, 4) + markOfGraceDonatorIncrease(player)))).owner(player).position(spawn).spawn(2);
        }
    }

    private static int markOfGraceDonatorIncrease(Player player) {
        if (player.isGroups(SecondaryGroup.DIAMOND)) {
            return 7;
        } else if (player.isGroups(SecondaryGroup.RUBY)) {
            return 6;
        } else if (player.isGroups(SecondaryGroup.EMERALD)) {
            return 5;
        } else if (player.isGroups(SecondaryGroup.SAPPHIRE)) {
            return 4;
        } else if (player.isGroups(SecondaryGroup.RED_TOPAZ)) {
            return 3;
        } else if (player.isGroups(SecondaryGroup.JADE)) {
            return 2;
        } else if (player.isGroups(SecondaryGroup.OPAL)) {
            return 1;
        } else {
            return 0;
        }
    }
}
