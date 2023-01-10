package io.ruin.model.entity.player;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.inter.dialogue.NPCDialogue;
import lombok.Getter;

public enum XpMode {

    EASY("Easy", 200, 200, 200, 0, 1),
    NORMAL("Normal", 100, 100, 100, 6, 4),
    MEDIUM("Medium", 25, 25, 25, 8, 3),
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

    public static void changeMode(Player player, NPC npc) {
        /*if (player.xpMode.ordinal() < 2) {
            XpMode newMode = EASY;

            if (isXpMode(player, HARD))
                newMode = MEDIUM;

            player.dialogue(
                    new NPCDialogue(npc, "You are about to go from<br>" +
                            "<col=ff0000>"+player.xpMode.getName()+" mode</col> to<col=ff0000>"+newMode.getName()+" mode</col>.<br>" +
                            "Combat XP: "+newMode.getCombatRate()+"x. Skilling XP: "+newMode.getSkillRate()+"x."),
                    new OptionsDialogue("<col=ff0000>THIS CANNOT BE UNDONE! ARE YOU SURE?<col>",
                            new Option("YES!", () -> {
                                XpMode newMode1 = EASY;
                                if (isXpMode(player, HARD))
                                    newMode1 = MEDIUM;
                                player.xpMode = newMode1;
                                player.sendMessage("Your XP Mode has been set to "+player.xpMode.getName());
                            }),
                            new Option("On second thought...", player::closeDialogue))
            );
        } else {*/
        player.dialogue(
                new NPCDialogue(npc, "You're already on the Easiest XP Mode.<br>" +
                        "It doesn't get any easier than that...")
        );
        //     }
    }

    public static void setXpMode(Player player, XpMode xpMode) {
        player.xpMode = xpMode;
    }

}
