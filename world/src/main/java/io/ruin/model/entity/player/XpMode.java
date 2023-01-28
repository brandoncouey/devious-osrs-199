package io.ruin.model.entity.player;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.inter.dialogue.NPCDialogue;
import lombok.Getter;

public enum XpMode {

    EASY("Easy", 150, 150, 150, 0, 1),
    NORMAL("Normal", 100, 100, 100, 6, 4),
    MEDIUM("Medium", 20, 20, 20, 8, 3),
    HARD("Hard", 10, 10, 5, 10, 2),
    REALISTIC("Realistic", 5, 5, 3, 15, 5),

    ;

    @Getter
    private final String name;

    @Getter
    private final int combatRate, skillRate, after99Rate, dropBonus, Modetype;

    XpMode(String name, int combatRate, int skillRate, int after99Rate, int dropBonus, int Modetype) {
        this.name = name;
        this.combatRate = combatRate;
        this.skillRate = skillRate;
        this.after99Rate = after99Rate;
        this.dropBonus = dropBonus;
        this.Modetype = Modetype;
    }

    public static boolean isXpMode(Player player, XpMode mode) {
        return player.xpMode == mode;
    }


    public static void setXpMode(Player player, XpMode xpMode) {
        player.xpMode = xpMode;
    }

}
