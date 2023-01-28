package io.ruin.model.content;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;

/*
 * @project Kronos
 * @author Patrity - https://github.com/Patrity
 * Created on - 6/7/2020
 */
public class PvmPoints {

    public static void setPvmPoints(Player player, int value) {
        player.PvmPoints = value;
        player.sendMessage("<col=8B0000>You now have " + player.PvmPoints + " PVM Points!");
    }

    public static void addPoints(Player player, NPC npc) {
        int points = 0;
        if (npc.getDef().combatLevel > 274 && !(npc.getDef().name.contains("brutal") || npc.getDef().name.contains("Adamant Dragon") || npc.getDef().name.contains("Rune Dragon"))) {
            points = Random.get(40, 60);
        } else if (npc.getDef().combatLevel > 100 && !npc.getDef().name.contains("maniacal")) {
            points = Random.get(10, 30);
        } else if (npc.getDef().combatLevel > 100 && npc.getDef().name.contains("maniacal")) {
            points = Random.get(1, 2);
        } else if (npc.getDef().combatLevel < 50) {
            if (Random.get(2, 8) == 1) {
                points = 1;
            }
        } else {
            points = 1;
        }
        if (player.isADonator())
            points = points * 2;
        player.PvmPoints += points;
        if (player.PvmPoints < (points + player.PvmPoints)) {
            player.sendMessage(Color.ORANGE_RED, "You received " + points + " PVM Points.");
            player.sendMessage("You now have " + player.PvmPoints + " PVM Points!");
        }

    }
}
