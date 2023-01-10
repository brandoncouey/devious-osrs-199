package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class WayneRep {

    private static final int WAYNE = 5897;

    static {
        NPCAction.register(WAYNE, "talk-to", WayneRep::wayneDialogue);
    }

    private static void wayneDialogue(Player player, NPC npc) {
        if (PlayerCounter.WAYNE_REP.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Hello there, adventurer."),
                    new OptionsDialogue("Choose an option",
                            new Option("I'm looking for a quest", () -> getQuest(player, npc)),
                            new Option("Nevermind")

                    )
            );
        }
        if (PlayerCounter.WAYNE_REP.get(player) == 1 && player.getInventory().contains(22103, 1)) {
            player.dialogue(
                    new PlayerDialogue("I've brought you a dragon metal lump, Wayne!"),
                    new NPCDialogue(npc, "Sir Amik will be undoubtedly pleased! Take this as a token of gratitude, hero."),
                    new ActionDialogue(() -> {
                        player.getInventory().remove(22103, 1);
                        player.getInventory().add(6199, 1);
                        player.varrockRep = +100;
                        player.sendMessage("+100 Falador Reputation");
                    })
            );
        }
    }

    public static void getQuest(Player player, NPC npc) {
        player.dialogue(
                new PlayerDialogue("I am looking for my next adventure!"),
                new NPCDialogue(npc, "Aye, I have just the thing for you. As you know, I make chain mail armor of the highest quality in all of Gielinor. " +
                        "Sir Amik has tasked me with crafting even stronger chain armor for his troops. However,"),
                new NPCDialogue(npc, "this can only be done by using lumps of Dragon Metal. Through the grapevine I have discovered that you can obtain these " +
                        "Dragon Metal Lumps from Rune Dragons. If you could bring me as many as possible, you would bring"),
                new NPCDialogue(npc, "great honor to Sir Amik and the White Knight's of Falador! Be warned, hero.. These dragons are not as weak as the others, " +
                        "they are quite dangerous."),
                new PlayerDialogue("I would be honored to assist the White Knights."),
                new NPCDialogue(npc, "You are very brave, hero. Bring me as many as you can find, I will need them all!"),
                new MessageDialogue("Bring Wayne a dragon metal lump. This can be repeated."),
                new ActionDialogue(() -> {
                    PlayerCounter.WAYNE_REP.increment(player, 1);//stage 1
                })
        );
    }

}