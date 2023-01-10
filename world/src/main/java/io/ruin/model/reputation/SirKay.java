package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class SirKay {

    private static final int SIRKAY = 3521;

    static {
        NPCAction.register(SIRKAY, "talk-to", SirKay::kayDialogue);
    }

    private static void kayDialogue(Player player, NPC npc) {
        if (PlayerCounter.SIR_KAY.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(npc, "Greetings, hero. I see you've gained the required reputation to receive tasks from me! I am in charge of the " +
                            "Kings contacts outside of Devious. Are you ready to begin your first journey for me?"),
                    new OptionsDialogue("Help Sir Kay?",
                            new Option("Yes, I am ready", () -> stageOne(player, npc)),
                            new Option("Nevermind", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 2) {
            player.dialogue(
                    new MessageDialogue("Seek out Pentyn in Kebos Lowlands.")
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 7 && !player.getInventory().contains(10585, 1)) {
            player.dialogue(
                    new MessageDialogue("You need the battle plans handy while you speak to Sir Kay.")
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 7 && player.getInventory().contains(10585, 1)) {
            player.dialogue(
                    new PlayerDialogue("Sir Kay, I come with great news. I am here to deliver the report sent by Pentyn."),
                    new NPCDialogue(npc, "Ah, yes. This information comes at a crucial time for us. Please, take this reward, hero."),
                    new MessageDialogue("You receive 200 Devious Reputation and a mystery box."),
                    new ActionDialogue(() -> {
                        stageFour(player, npc);
                    })
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 4) {
            player.dialogue(
                    new NPCDialogue(npc, "Greetings, hero."),
                    new OptionsDialogue("Continue?",
                            new Option("I am looking for another adventure", () -> stageFive(player, npc)),
                            new Option("Nevermind", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 6) {
            player.dialogue(new MessageDialogue("Bring the battle plans to Pentyn."));
        }
        if (PlayerCounter.PENTYN.get(player) == 11) {
            player.dialogue(
                    new PlayerDialogue("Sir Kay, I come bearing news from Kebos."),
                    new PlayerDialogue("Councillor Andrews of the Kourend Castle tasked mewith investigating disturbing behavior from who we believe to be followers of Xeric," +
                            " near the Chambers of Xeric. Inside of a hidden cave, I found a strange"),
                    new PlayerDialogue("altar and, upon placing my hand on it, filled my vision with dark, ancient magic. The Councillor believes that the followers are" +
                            " using this altar to conjure the powers that Xeric once held, and to unleash them as a weapon."),
                    new NPCDialogue(npc, "Thank you for the update, hero. This news is very concerning, and could end life as we know it if we do nothing to stop it. " +
                            "I will need to discuss this with King Arthur so that we may formulate a plan. Please take this reward, hero."),
                    new MessageDialogue("You receive 300 Devious Reputation and a Berserker Ring."),
                    new ActionDialogue(() -> {
                        PlayerCounter.SIR_KAY.increment(player, 1);//stage 7
                        PlayerCounter.PENTYN.increment(player, 1);//stage 12
                        player.edgevilleRep += 300;
                        player.sendMessage("+300 Devious Reputation");
                    })
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 7) {
            player.dialogue(
                    new NPCDialogue(npc, "Hero, I must ask too much of you for your next task. We have received intel that has informed us more of the evils " +
                            "lurking inside of the Chambers of Xeric. I need you to venture there and search for a foul beast inside named"),
                    new NPCDialogue(npc, "Tekton. Once you have located him, slay the beast and then return to me."),
                    new MessageDialogue("Kill Tekton in the Chambers of Xeric, then return to Sir Kay."),
                    new ActionDialogue(() -> {
                        PlayerCounter.SIR_KAY.increment(player, 1);//stage 8
                    })
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 8) {
            player.dialogue(new MessageDialogue("Kill Tekton in the Chambers of Xeric, then return to Sir Kay."));
        }
        if (PlayerCounter.SIR_KAY.get(player) == 9) {
            player.dialogue(
                    new PlayerDialogue("Sir, I've successfully slain the beast, Tekton."),
                    new NPCDialogue(npc, "I am glad to see you've returned in one piece. We had grown worried, as you were gone longer than expected." +
                            " It is excellent news that you bring with you. Report back to Kebos and deliver the news to Pentyn."),
                    new MessageDialogue("Report back to Pentyn in Kebos."),
                    new ActionDialogue(() -> {
                        PlayerCounter.SIR_KAY.increment(player, 1);//stage 10
                        player.edgevilleRep += 500;
                        player.kebosRep += 300;
                        player.sendMessage("+500 Devious Reputation");
                        player.sendMessage("+300 Kebos Reputation");
                    })
            );
        }
        if (PlayerCounter.SIR_KAY.get(player) == 10) {
            player.dialogue(new MessageDialogue("Report back to Pentyn in Kebos."));
        }
    }

    public static void stageOne(Player player, NPC npc) {
        PlayerCounter.SIR_KAY.increment(player, 1);//stage 1
        player.dialogue(
                new PlayerDialogue("Yes, Sir Kay. I am ready to do what you ask of me."),
                new NPCDialogue(npc, "Good. Your first assignment is going to require you to travel to Kebos. You will meet a correspondent of the King there, " +
                        "named Pentyn, who will require your assistance. On behalf of myself and the King, I ask"),
                new NPCDialogue(npc, "you to provide any aid or assistance that Pentyn may require. It is of great importance that we secure the Kebos area. " +
                        "With haste, hero."),
                new MessageDialogue("Seek out Pentyn in Kebos Lowlands."),
                new ActionDialogue(() -> {
                    PlayerCounter.SIR_KAY.increment(player, 1);//stage 2
                })
        );
    }

    public static void stageFour(Player player, NPC npc) {
        player.edgevilleRep += 200;
        player.getInventory().remove(10585, 1);
        player.sendMessage("+200 Devious Reputation");
        player.getInventory().add(6199, 1);
        PlayerCounter.SIR_KAY.increment(player, 1);//stage 4
        PlayerCounter.PENTYN.increment(player, 1);//stage 8
    }

    public static void stageFive(Player player, NPC npc) {
        PlayerCounter.SIR_KAY.increment(player, 1);//stage 5
        player.dialogue(
                new PlayerDialogue("I am ready for my next task, Sir Kay."),
                new NPCDialogue(npc, "Excellent. You are making great strides to help us in our ongoing conflicts, hero. I will need you to return to Pentyn " +
                        "and deliver these battle plans to him. He will then inform you of your next assignment."),
                new MessageDialogue("Sir Kay hands you the battle plans."),
                new ActionDialogue(() -> {
                    PlayerCounter.SIR_KAY.increment(player, 1);//stage 6
                })
        );
    }

}