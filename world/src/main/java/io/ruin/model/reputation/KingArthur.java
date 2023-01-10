package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class KingArthur {

    private static final int KING = 3531;

    static {
        NPCAction.register(KING, "talk-to", io.ruin.model.reputation.KingArthur::kingDialogue);
    }

    private static void kingDialogue(Player player, NPC npc) {
        if (PlayerCounter.KING_ARTHUR.get(player) == 0) {
            player.dialogue(new NPCDialogue(npc, "Greetings hero, welcome to Devious."),
                    new NPCDialogue(npc, "Your arrival is very timely. There are many tasks that need completed and even more evils to be over-come in Devious." +
                            " These tasks will require great bravery and courage, but yield great rewards for those"),
                    new NPCDialogue(npc, "deemed worthy. Are you man enough to serve your King and begin your journey here and now?"),
                    new OptionsDialogue(
                            new Option("Yes, let us begin.", () -> player.dialogue(
                                    new PlayerDialogue("Yes, your highness. I am ready to begin my journey."),
                                    new NPCDialogue(npc, "Noble indeed. We will see if you are as brave as you seem to be. You will begin with an easy task." +
                                            " A group of goblins have been spotted just east of Edgeville, across the river. Venture"),
                                    new NPCDialogue(npc, "over across the bridge and slay 12 goblins. Return to me once you have completed the task."),
                                    new ActionDialogue(() -> {
                                        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 1
                                        new MessageDialogue("Cross the bridge outside of the castle and kill 12 goblins, then return to King Arthur.");
                                    })
                            )),
                            new Option("No, I am not ready.", () -> player.dialogue(
                                    new PlayerDialogue("I'm not ready. Maybe later.")
                            ))
                    ));
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 1) {
            player.dialogue(new MessageDialogue("Return to King Arthur after you've killed 12 goblins east of the castle."));
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 2) {
            player.dialogue(new PlayerDialogue("My King, the task is finished. I have slain 12 goblins."),
                    new NPCDialogue(npc, "Indeed you have! You have helped cleanse Devious of the goblin filth, and proven yourself worthy of this reward," +
                            " and my next task for you."),
                    new MessageDialogue("King Arthur awards you +100 Devious Reputation"),
                    new ActionDialogue(() -> {
                        stageFour(player, npc);
                    })
            );
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 4 && player.varrockRep < 500) {
            player.dialogue(new MessageDialogue("Speak to King Roald in the Varrock castle and earn 500 Varrock Reputation before returning to King Arthur."));
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 4 && player.varrockRep >= 500) {
            player.dialogue(new PlayerDialogue("I have earned 500 reputation with Varrock, your highness."),
                    new NPCDialogue(npc, "Well done, hero. King Roald has spoken highly of you. I award you with 200 Devious reputation."),
                    new MessageDialogue("King Arthur awards you +200 Devious Reputation"),
                    new ActionDialogue(() -> {
                        stageSix(player, npc);
                    })
            );
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 6) {
            player.dialogue(new PlayerDialogue("Greetings, your highness. I am in search of a new assignment!"),
                    new NPCDialogue(npc, "Very well. I will need you to next travel to the Kingdom of Falador and speak with Sir Rebral in the Falador castle courtyard" +
                            " and begin assisting them in their fight and earning reputation with Falador."),
                    new NPCDialogue(npc, "Return to me once you have earned 500 reputation with Falador. Godspeed, hero."),
                    new MessageDialogue("Earn 500 reputation in Falador and then return to King Arthur."),
                    new ActionDialogue(() -> {
                        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 7
                    })
            );
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 7 && player.faladorRep < 500) {
            player.dialogue(new MessageDialogue("Speak to Sir Rebral in the Falador castle and earn 500 Falador Reputation before returning to King Arthur."));
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 7 && player.faladorRep >= 500) {
            player.dialogue(new PlayerDialogue("Your highness, I have begun assisting Falador and have earned 500 reputation there."),
                    new NPCDialogue(npc, "You are doing a fine job making a name for yourself hero. Sir Amik, the leader of the White Knights, has sent word that you" +
                            " are already making an impact. Please, accept this reward for your recent heroics in Falador"),
                    new MessageDialogue("King Arthur awards you +200 Devious Reputation"),
                    new ActionDialogue(() -> {
                        stageEight(player, npc);
                    })
            );
        }
        if (PlayerCounter.KING_ARTHUR.get(player) == 8) {
            player.dialogue(new MessageDialogue("King Arthur does not currently have any more quests available. Please come back later."));
        }
    }

    public static void stageFour(Player player, NPC npc) {
        player.edgevilleRep += 100;
        player.sendMessage("+100 Devious Reputation");
        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 3
        player.dialogue(new NPCDialogue(npc, "As a token of gratitude on behalf of Devious, I award you 100 Devious Reputation." +
                        " What is reputation, you wonder? Whenever you complete tasks assigned to you in any major area of Devious,"),
                new NPCDialogue(npc, "you will be rewarded with an amount of reputation. Once you achieve certain amounts of reputation in those areas," +
                        " you will unlock access to great rewards based on your reputation amount. You can track your"),
                new NPCDialogue(npc, "amounts using the reputation section of your quest tab and clicking the city or area name."),
                new NPCDialogue(npc, "I will now assign you your next task. I want you to travel to Varrock and report to King Roald in the Varrock Castle." +
                        " He will begin assigning you tasks that will earn you reputation with Varrock. Return to me"),
                new NPCDialogue(npc, "for your next assignment after you have earned 500 Varrock Reputation."),
                new MessageDialogue("Speak to King Roald in the Varrock castle and earn 500 Varrock Reputation before returning to King Arthur."),
                new ActionDialogue(() -> {
                    PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 4
                })
        );
    }

    public static void stageSix(Player player, NPC npc) {
        player.edgevilleRep += 200;
        player.sendMessage("+200 Devious Reputation");
        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 5
        player.dialogue(
                new NPCDialogue(npc, "You have proven an understanding of the importance of earning reputation, and you will now be able to receive tasks from Sir Kay," +
                        " Lancelot, and Percival. We are in a time of despair, it is crucial that you"),
                new NPCDialogue(npc, "continue assisting us with your heroics to push back the perils of evil and keep Edgeville safe." +
                        " There is much work to be completed, so when ready, speak to any four of us for new assignements."),
                new MessageDialogue("You may now receive new tasks from Sir Kay."),
                new ActionDialogue(() -> {
                    PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 6
                })
        );
    }

    public static void stageEight(Player player, NPC npc) {
        player.edgevilleRep += 200;
        player.sendMessage("+200 Devious Reputation");
        PlayerCounter.KING_ARTHUR.increment(player, 1);//stage 8
        //This is where the questline ends atm
    }

}