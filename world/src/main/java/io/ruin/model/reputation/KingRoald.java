package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class KingRoald {

    private static final int KING = 5215;

    static {
        NPCAction.register(KING, "talk-to", KingRoald::kingDialogue);
    }

    private static void kingDialogue(Player player, NPC npc) {
        if (PlayerCounter.KING_ROALD.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Greetings, adventurer. What brings you to the Kingdom of Varrock?"),
                    new PlayerDialogue("I am in search of quests, your highness."),
                    new NPCDialogue(npc, "There is always work to be done here in Varrock! The biggest issue we are currently facing are the green dragons " +
                            "to the north! They continue burning our crops and eating our livestock. If"),
                    new NPCDialogue(npc, "you would be brave enough to face these beastsand save us from their tyranny, you would become the hero of Varrock" +
                            " and be rewarded greatly! Will you help us, adventurer?"),
                    new OptionsDialogue("Assist King Roald?",
                            new Option("Assist Varrock by slaying 100 Green Dragons", () -> acceptDragons(player, npc)),
                            new Option("Nevermind")
                    )
            );
        }
        if (PlayerCounter.KING_ROALD.get(player) == 1 && player.greendragonRep < 100) {
            player.dialogue(
                    new MessageDialogue("Return to the king once you have slain 100 green dragons.")
            );
        }
        if (PlayerCounter.KING_ROALD.get(player) == 2 && player.greendragonRep >= 100) {
            player.dialogue(
                    new NPCDialogue(npc, "Adventurer, you have returned! You have freed Varrock from the grip of the dragons. Varrock will sing stories " +
                            "of your heroics for years to come. Please, take this reward and allow me to declare"),
                    new NPCDialogue(npc, "you as an honored Knight of Varrock! There is yet more work to be done here. When you are ready, please seek me out" +
                            " for more work, adventurer!"),
                    new MessageDialogue("You receive 200 Varrock Reputation"),
                    new ActionDialogue(() -> {
                        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 2
                        player.varrockRep = +200;
                        player.sendMessage("+200 Varrock Reputation");
                    })
            );
        }
        if (PlayerCounter.KING_ROALD.get(player) == 3 && !player.getInventory().contains(10585, 1)) {
            player.dialogue(
                    new MessageDialogue("Return to King Roald with the battle plans.")
            );
        }
        if (PlayerCounter.KING_ROALD.get(player) == 3 && player.getInventory().contains(10585, 1)) {
            player.dialogue(
                    new NPCDialogue(npc, "Greetings, hero. Have you recovered the Black Knights attack plans?"),
                    new PlayerDialogue("Yes, my king. I have recovered the plans!"),
                    new MessageDialogue("You hand the attack plans over to King Roald."),
                    new ActionDialogue(() -> {
                        player.getInventory().remove(10585, 1);
                        rewardThree(player, npc);
                    })
            );
        }
        if (PlayerCounter.KING_ROALD.get(player) == 4) {
            new MessageDialogue("The king does not currently have any quests available for you.");
        }
    }

    public static void acceptDragons(Player player, NPC npc) {
        PlayerCounter.KING_ROALD.increment(player, 1);//stage 1
        player.dialogue(
                new PlayerDialogue("I will help you my King!"),
                new NPCDialogue(npc, "You are a brave soul, adventurer. Your heroics will be celebrated for years to come! Return to me once the task is finished," +
                        " and be careful of the dragons fiery breath!")
        );
    }

    public static void acceptKnights(Player player, NPC npc) {
        PlayerCounter.KING_ROALD.increment(player, 1);//stage 3
        player.dialogue(
                new PlayerDialogue("You can count on me, my King."),
                new NPCDialogue(npc, "As brave as ever. You bring honor to the Kingdom of Varrock. Return to me once you have completed your task.")
        );
    }

    public static void rewardThree(Player player, NPC npc) {
        player.varrockRep += 500;
        player.sendMessage("+500 Varrock Reputation");
        player.getInventory().add(6199, 1);
        PlayerCounter.KING_ROALD.increment(player, 1);//stage 4
        player.dialogue(
                new NPCDialogue(npc, "Alas, you have once again saved many lives in Varrock with your heroism, " + player.getName() + "! We will forever be" +
                        " indebted to your acts of valor. Please, accept this reward as a token of gratitude.")

        );
    }
}