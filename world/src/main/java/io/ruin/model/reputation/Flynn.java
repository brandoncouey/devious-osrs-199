package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class Flynn {

    private static final int FLYNN = 5896;

    static {
        NPCAction.register(FLYNN, "talk-to", Flynn::flynnDialogue);
    }

    private static void flynnDialogue(Player player, NPC npc) {
        if (PlayerCounter.FLYNN_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Do you need something wanderer?"),
                    new OptionsDialogue("Choose an option",
                            new Option("Do you have any quests for me?", () -> getQuest(player, npc)),
                            new Option("Nevermind")
                    )
            );
        }
        if (PlayerCounter.FLYNN_REP.get(player) == 1 && !player.getInventory().contains(7418, 1)) {
            player.dialogue(
                    new MessageDialogue("Bring Flynn a mole skin")
            );
        }
        if (PlayerCounter.FLYNN_REP.get(player) == 1 && player.getInventory().contains(7418, 1)) {
            player.dialogue(
                    new NPCDialogue(npc, "" + player.getName() + ", you've returned! have you retrieved a mole skin yet?"),
                    new PlayerDialogue("Yes, I have retrieved the mole skin!"),
                    new MessageDialogue("You hand the mole skin over to Flynn"),
                    new ActionDialogue(() -> {
                        player.getInventory().remove(7418, 1);
                        finishQuest(player, npc);
                    })
            );
        }
        if (PlayerCounter.FLYNN_REP.get(player) == 2) {
            player.dialogue(
                    new NPCDialogue(npc, "Thank you again for your help, adventurer!")
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(npc, "As a matter of fact, I do. I have been trying to get my hands on some mole skin, however, nobody here is brave enough " +
                        "to face the giant moles in their lair! If you are as brave as you look, I would ask of you to"),
                new NPCDialogue(npc, "venture into the moles lair and slay those foul beasts until you retreive a mole skin! If you could do this for me, " +
                        "adventurer, I would be willing to reward you handsomly!"),
                new PlayerDialogue("I'm not afraid of any moles, I'd be happy to help!"),
                new NPCDialogue(npc, "Splendid! Return to me once you have retreived a mole skin!"),
                new MessageDialogue("Bring Flynn a mole skin"),
                new ActionDialogue(() -> {
                    PlayerCounter.FLYNN_REP.increment(player, 1);//stage 1
                })
        );
    }

    public static void finishQuest(Player player, NPC npc) {
        PlayerCounter.FLYNN_REP.increment(player, 1);//stage 2
        player.faladorRep += 200;
        player.sendMessage("+200 Falador Reputation");
        player.dialogue(
                new NPCDialogue(npc, "I knew I could count on you, hero! Because of your noble deed, I will be able to use this skin as testing for a new type " +
                        "of armor! Please, accept this reward as a token of my gratitude!"),
                new MessageDialogue("You receive 200 Falador Reputation and a mystery box."),
                new ActionDialogue(() -> {
                    player.getInventory().add(6199, 1);
                })
        );
    }

}