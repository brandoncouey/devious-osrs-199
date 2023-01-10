package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class DoorMan {

    private static final int DOORMAN = 5419;

    static {
        NPCAction.register(DOORMAN, "talk-to", DoorMan::doormanDialogue);
    }

    private static void doormanDialogue(Player player, NPC npc) {
        if (PlayerCounter.DOORMAN_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Psst! Hey, you! Yeah, I'm talking to you! You look awfully brave.. up for an.. adventure?"),
                    new OptionsDialogue("Choose an option",
                            new Option("Yes, I am!", () -> getQuest(player, npc)),
                            new Option("Not now.")
                    )
            );
        }
        if (PlayerCounter.DOORMAN_REP.get(player) == 1 && player.giantmoleRep < 50) {
            player.dialogue(
                    new MessageDialogue("Kill 50 giant moles, then return to the Doorman. You currently have " + player.giantmoleRep + "/50 kills.")
            );
        }
        if (PlayerCounter.DOORMAN_REP.get(player) == 2 && player.giantmoleRep >= 50) {
            player.dialogue(
                    new PlayerDialogue("I think I've taken care of your rodent problem."),
                    new NPCDialogue(npc, "You sure have, brave adventurer! I've already sold 4 homes to- I mean, the town looks better already! " +
                            "Here, take this, it isn't much but it is all I can offer currently!"),
                    new MessageDialogue("You receive a reward for your 'heroics'."),
                    new ActionDialogue(() -> {
                        finishQuest(player, npc);
                    })
            );
        }
        if (PlayerCounter.DOORMAN_REP.get(player) == 3) {
            player.dialogue(
                    new NPCDialogue(npc, "Thank you again for your help, adventurer!")
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("That depends.. What is the adventure, and what's the catch?"),
                new NPCDialogue(npc, "No catch! Well, maybe putting yourself in the face of danger is a catch, but anyways.. You know those pesky moles in " +
                        "the park over there? They are really driving down real estate here in Falador, no"),
                new NPCDialogue(npc, "one wants to buy homes here because of them! What do'ya say you head on over there and show those rodents who the boss is?" +
                        " I promise to reward ya handsomly!"),
                new PlayerDialogue("Sure, I'm up for the challenge!"),
                new NPCDialogue(npc, "Perfect! Return to me once you've finished roughin' them up!"),
                new MessageDialogue("Kill 50 giant moles, then return to the Doorman."),
                new ActionDialogue(() -> {
                    PlayerCounter.DOORMAN_REP.increment(player, 1);//stage 1
                })
        );
    }

    public static void finishQuest(Player player, NPC npc) {
        PlayerCounter.DOORMAN_REP.increment(player, 1);//stage 3
        player.faladorRep += 200;
        player.sendMessage("+200 Falador Reputation");
    }

}