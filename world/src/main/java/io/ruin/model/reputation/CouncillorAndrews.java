package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class CouncillorAndrews {

    private static final int ANDREWS = 7924;

    static {
        NPCAction.register(ANDREWS, "talk-to", CouncillorAndrews::councillorDialogue);
    }

    private static void councillorDialogue(Player player, NPC npc) {
        if (PlayerCounter.PENTYN.get(player) == 10) {
            player.dialogue(
                    new PlayerDialogue("Sir, Pentyn has sent me to deliver important news."),
                    new PlayerDialogue("He didn't give much detail, but he assigned me to report to you that there has been extreme movement " +
                            "of Xeric followers in the Mount Quidamortem area."),
                    new NPCDialogue(npc, "Oh, my.. this is.. this is terrible news. Could it be? Could he.. could he be returning?" +
                            " We had believed he was long gone, but this can't be corr-"),
                    new PlayerDialogue("Pardon me, sir, but what is the problem here?"),
                    new NPCDialogue(npc, "Xeric has been dead for over a thousand years. If his followers are gathering near the Chambers of Xeric," +
                            " this could only mean that they are attempting to resurrect him, or to discover his magical powers."),
                    new NPCDialogue(npc, "We must investigate and try to get ahead of this. Hero, I need you to travel to Mount Quidamortem and search " +
                            "for any clues you can find. I believe there is a hidden cave, just west of the Chambers entrance, down"),
                    new NPCDialogue(npc, "the mountain trail a bit. Once you have found any sort of clues, report back to me. Godspeed, hero."),
                    new MessageDialogue("Search for clues in a crevice near the Chambers entrance."),
                    new ActionDialogue(() -> {
                        PlayerCounter.COUNCILLOR_ANDREWS.increment(player, 1);//stage 1
                    })
            );
        }
        if (PlayerCounter.COUNCILLOR_ANDREWS.get(player) == 1) {
            player.dialogue(
                    new MessageDialogue("Search for clues in a crevice near the Chambers entrance.")
            );
        }
        if (PlayerCounter.COUNCILLOR_ANDREWS.get(player) == 2) {
            player.dialogue(
                    new NPCDialogue(npc, "Hero, I am glad to see you have returned! Please, tell me what you have discovered!"),
                    new PlayerDialogue("Darkness. I discovered an ancient altar within the cave, and when approached, I felt darkness and old magic flowing through me." +
                            " It felt as if it were pulling me towards death."),
                    new NPCDialogue(npc, "This is worse than I had thought was true. We must get ahead of this before it is too late. Xeric's followers are likely" +
                            " using the altar to gain the dark magic of their ancient lord. The results could be very"),
                    new NPCDialogue(npc, "catastrophic. I need you to take this new development back to Pentyn and report your findings to him. With haste, hero. " +
                            "We don't have much time until Xerics' followers know we are on to them."),
                    new MessageDialogue("Return to Pentyn and deliver the news."),
                    new ActionDialogue(() -> {
                        PlayerCounter.COUNCILLOR_ANDREWS.increment(player, 1);//stage 3
                        player.kebosRep += 250;
                        player.sendMessage("+250 Kebos Reputation");
                    })
            );
        }
        if (PlayerCounter.COUNCILLOR_ANDREWS.get(player) == 3) {
            player.dialogue(
                    new MessageDialogue("Return to Pentyn and deliver the news.")
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 4) {

        }
        if (PlayerCounter.PENTYN.get(player) == 5) {

        }
        if (PlayerCounter.PENTYN.get(player) == 6) {

        }
        if (PlayerCounter.PENTYN.get(player) == 7) {

        }
        if (PlayerCounter.PENTYN.get(player) == 10) {

        }
    }

    public static void stageOne(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 1
        player.dialogue(
                new PlayerDialogue("Yes, Pentyn. I am ready to do whatever it is that you ask of me."),
                new NPCDialogue(npc, "Excellent! These will be no easy tasks, hero. I hope you are ready! Your first task is going to be to travel to the " +
                        "Lizardman Shaman encampment and slay 75 of those foul beasts. Return to me once this is complete."),
                new MessageDialogue("Kill 75 lizardman shamans, then return to Pentyn.")
        );
    }

    public static void stageSix(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 6
        player.kebosRep += 150;
        player.sendMessage("+150 Kebos Reputation");
        player.getInventory().add(6199, 1);
    }

}