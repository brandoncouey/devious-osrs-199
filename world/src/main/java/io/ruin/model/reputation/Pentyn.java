package io.ruin.model.reputation;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;

public class Pentyn {

    private static final int PENTYN = 3568;

    static {
        NPCAction.register(PENTYN, "talk-to", Pentyn::pentynDialogue);
    }

    private static void pentynDialogue(Player player, NPC npc) {
        if (PlayerCounter.SIR_KAY.get(player) == 2) {
            player.dialogue(
                    new PlayerDialogue("Hello, Pentyn. Sir Kay has sent me to assist you in any way I can."),
                    new NPCDialogue(npc, "Hello traveler. It is great news that Sir Kay has sent reinforcements! The region of Kebos is of great tactical importance" +
                            " to the kingdom of Great Kourend, one of our strongest allies. It is our duty to assistance them however"),
                    new NPCDialogue(npc, "we can in order to gain an advantage in the warfront! The biggest problem we currently face are the Lizardmen. " +
                            "They swarm these lands and keep destroying our caches of food and supplies being sent to the north and west."),
                    new NPCDialogue(npc, "So tell me, hero. Are you ready to begin your first task here in Kebos?"),
                    new OptionsDialogue("Help Pentyn?",
                            new Option("Yes, I am ready.", () -> stageOne(player, npc)),
                            new Option("Nevermind", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 1) {
            player.dialogue(new MessageDialogue("Kill 75 lizardman shamans, then return to Pentyn."));
        }
        if (PlayerCounter.PENTYN.get(player) == 2) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, it is done. I have slain 75 Lizardman Shaman."),
                    new NPCDialogue(npc, "Wonderful! This will surely help get some of our supplies through. Accept this as a reward."),
                    new MessageDialogue("You receive 150 Kebos Reputation and a mystery box."),
                    new ActionDialogue(() -> {
                        stageThree(player, npc);
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 3) {
            player.dialogue(
                    new NPCDialogue(npc, "Are you ready for your next task, hero?"),
                    new OptionsDialogue("Continue?",
                            new Option("Yes, I am ready", () -> stageFour(player, npc)),
                            new Option("No, not yet", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 4) {
            player.dialogue(new MessageDialogue("Travel to Mount Karuulm and slay 50 wyrms."));
        }
        if (PlayerCounter.PENTYN.get(player) == 5) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, I have finished killing 50 wyrms."),
                    new NPCDialogue(npc, "Excellent. This should surely make it easier to gain intel on the area. Please accept this reward!"),
                    new MessageDialogue("You receive 150 Kebos Reputation and a mystery box."),
                    new ActionDialogue(() -> {
                        stageSix(player, npc);
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 6) {
            player.dialogue(
                    new NPCDialogue(npc, "Hero, you come at a convenient time. Sir Kay has asked for an update on our successes here in Kebos. " +
                            "If you could, I need for you to travel back to Edgeville and deliver this report to Sir Kay. Can you do this for me, hero?"),
                    new OptionsDialogue("Continue?",
                            new Option("Yes, I can do this", () -> stageSeven(player, npc)),
                            new Option("Not right now", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 7) {
            player.dialogue(new MessageDialogue("Bring the battle plans to Sir Kay in Edgeville."));
        }
        if (PlayerCounter.SIR_KAY.get(player) == 6 && player.getInventory().contains(10585, 1)) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, I have returned. Sir Kay has sent me to deliver the plans for our next assault."),
                    new NPCDialogue(npc, "Ah, hero, am I glad to see you! We have been running into quite a few... problems. Please, hand over the battle plans " +
                            "so I can assess our next plan of action!"),
                    new MessageDialogue("You hand the plans over to Pentyn."),
                    new ActionDialogue(() -> {
                        stageNine(player, npc);
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 10) {
            player.dialogue(new MessageDialogue("Speak with Councillor Andrews in the Great Kourend Castle."));
        }
        if (PlayerCounter.COUNCILLOR_ANDREWS.get(player) == 3) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, I have returned with unfortunate news.."),
                    new PlayerDialogue("The Councillor had me check for clues inside of a cave near the Chambers of Xeric. Once inside, I discovered an altar and," +
                            " once touched, I felt a wave of evil magic flowing through me. Councillor"),
                    new PlayerDialogue("Andrews is convinced that Xerics' followers are using this altar to conjour Xerics' ancient powers. He told me this news was of the " +
                            "utmost importance to deliver to you."),
                    new NPCDialogue(npc, "The Councillor is correct, this new development could be catastrophic for us. This is going to require guidance from Sir Kay" +
                            " to dictate our next move. Return to Sir Kay and deliver this news to him, hero. Godspeed."),
                    new MessageDialogue("Deliver the news to Sir Kay in Edgeville."),
                    new ActionDialogue(() -> {
                        PlayerCounter.PENTYN.increment(player, 1);//stage 11
                        player.kebosRep += 100;
                        player.sendMessage("+100 Kebos Reputation");
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 11) {
            player.dialogue(new MessageDialogue("Deliver the news to Sir Kay in Edgeville."));
        }
        if (PlayerCounter.SIR_KAY.get(player) == 10) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, Sir Kay has sent me to let you know that I've traveled to the Chambers of Xeric and defeated Tekton."),
                    new NPCDialogue(npc, "Tekton? You're telling me that foul beast was still alive? He was one of Xerics' top guardians, thought to be dead long ago." +
                            " This could mean that other guardians are still lingering about in the Chambers. Hero, if you could venture back to the"),
                    new NPCDialogue(npc, "Chambers and search for Muttadile and, if alive, slay the beast as well. Muttadile is another important guardian to Xeric and" +
                            " must be taken out. Please return to me once you have completed this daunting task. With haste, hero."),
                    new MessageDialogue("Defeat Muttadile in the Chambers of Xeric, then return to Pentyn."),
                    new ActionDialogue(() -> {
                        PlayerCounter.PENTYN.increment(player, 1);//stage 12
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 12) {
            player.dialogue(new MessageDialogue("Defeat Muttadile in the Chambers of Xeric, then return to Pentyn."));
        }
        if (PlayerCounter.PENTYN.get(player) == 13) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, I located Muttadile and defeated him."),
                    new NPCDialogue(npc, "Excellent news! We are making great progress in landing heavy blows to Xerics return. We sent a scout into the Chamber and he" +
                            " reported back that Vasa Nistirio has also been spotted guarding the temple. Vasa is known for his"),
                    new NPCDialogue(npc, "dark magic and surely plays an important role in restoring Xerics' powers. He must be stopped. We must ask too much of you " +
                            "once again hero, please, travel back to the Chambers and defeat Vasa."),
                    new MessageDialogue("Defeat Vasa Nistirio in the Chambers of Xeric."),
                    new ActionDialogue(() -> {
                        PlayerCounter.PENTYN.increment(player, 1);//stage 14
                        player.kebosRep += 500;
                        player.sendMessage("+500 Kebos Reputation");
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 14) {
            player.dialogue(new MessageDialogue("Defeat Vasa Nistirio in the Chambers of Xeric, then return to Pentyn."));
        }
        if (PlayerCounter.PENTYN.get(player) == 15) {
            player.dialogue(
                    new PlayerDialogue("Pentyn, Vasa Nistirio has been eliminated."),
                    new NPCDialogue(npc, "You never cease to let us down, hero. We will never be able to repay the debts that we owe for your bravery. Vasa's defeat" +
                            " is a tremendous victory for us. I'd like you to return to Sir Kay and report our progress here in Kebos."),
                    new MessageDialogue("Return to Sir Kay in Edgeville."),
                    new ActionDialogue(() -> {
                        PlayerCounter.PENTYN.increment(player, 1);//stage 16
                        player.kebosRep += 500;
                        player.sendMessage("+500 Kebos Reputation");
                    })
            );
        }
        if (PlayerCounter.PENTYN.get(player) == 16) {
            player.dialogue(new MessageDialogue("Return to Sir Kay in Edgeville."));
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

    public static void stageThree(Player player, NPC npc) {
        player.kebosRep += 150;
        player.sendMessage("+150 Kebos Reputation");
        player.getInventory().add(6199, 1);
        PlayerCounter.PENTYN.increment(player, 1);//stage 3
        PlayerCounter.SIR_KAY.increment(player, 1);//stage 3
    }

    public static void stageFour(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 4
        player.dialogue(
                new PlayerDialogue("I am ready to assist you, Pentyn."),
                new NPCDialogue(npc, "Perfect. Your next task will require you to travel north to Mount Karuulm. One of our expedition teams ventured there to gather " +
                        "intel on the area, but only one from the group has returned alive. The rest of the team was slain"),
                new NPCDialogue(npc, "by nasty beasts called Wyrms. I will need you to travel into Mount Karuulm and slay 50 Wyrms and then return to me." +
                        " I must warn you, these beasts will not be easy to defeat. Return to me once this task is finished."),
                new MessageDialogue("Travel to Mount Karuulm and slay 50 wyrms.")
        );
    }

    public static void stageSix(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 6
        player.kebosRep += 150;
        player.sendMessage("+150 Kebos Reputation");
        player.getInventory().add(6199, 1);
    }

    public static void stageSeven(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 7
        player.getInventory().add(10585, 1);
        player.dialogue(
                new PlayerDialogue("Yes, Pentyn. I'd be glad to deliver the report."),
                new NPCDialogue(npc, "Excellent! With haste, hero. The clock is ticking.")
        );
    }

    public static void stageNine(Player player, NPC npc) {
        PlayerCounter.PENTYN.increment(player, 1);//stage 9
        player.getInventory().remove(10585, 1);
        player.kebosRep += 100;
        player.sendMessage("+100 Kebos Reputation");
        player.dialogue(
                new NPCDialogue(npc, "Hmm, very interesting. We will come back to these plans shortly. Right now, we have a bigger problem at hand; " +
                        "There have been major disruptions going on west of here, near Mount Quidamortem. We"),
                new NPCDialogue(npc, "have caught wind about followers of Xeric coming and going near the area. I need you to travel to the castle of " +
                        "Great Kourend and speak with Councillor Andrews and deliver this news to him. He will know"),
                new NPCDialogue(npc, "the importance of this and advise us on our next steps. With haste, hero, time is of the utmost importance for this mission."),
                new MessageDialogue("Speak with Councillor Andrews in the Great Kourend Castle."),
                new ActionDialogue(() -> {
                    PlayerCounter.PENTYN.increment(player, 1);//stage 10
                })
        );
    }

}