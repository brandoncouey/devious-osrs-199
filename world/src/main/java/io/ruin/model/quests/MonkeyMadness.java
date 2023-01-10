package io.ruin.model.quests;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class MonkeyMadness {

    private static final int NARNODE = 1423;

    static {
        NPCAction.register(NARNODE, "talk-to", MonkeyMadness::narnodeDialogue);
    }

    private static void narnodeDialogue(Player player, NPC npc) {
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 0 && player.getStats().get(StatType.Attack).fixedLevel >= 60) {
            player.dialogue(
                    new PlayerDialogue("King, you look worried. Is anything the matter?"),
                    new NPCDialogue(npc, "Nothing in particular... Well actually, yes, there is."),
                    new PlayerDialogue("What is it?"),
                    new NPCDialogue(npc, "The 10th Squad, my envoy of Royal Guards, were attacked and slaughtered by a dangerous demon." +
                            " I require the assistance of a brave warrior to slay this demon and save my people! Will you help us?"),
                    new OptionsDialogue("Help the King?",
                            new Option("Yes, of course!", () -> acceptQuest(player, npc)),
                            new Option("Sounds too scary for me.", () -> {
                                player.dialogue(new PlayerDialogue("Not right now."));
                            })
                    )
            );
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 1) {
            player.dialogue(
                    new PlayerDialogue("What am I supposed to do again?"),
                    new NPCDialogue(npc, "Enter the trapdoor next to us, find my squadron and slay the demon."),
                    new PlayerDialogue("Got it.")
            );
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 2) {
            player.dialogue(
                    new PlayerDialogue("King Narnode!"),
                    new NPCDialogue(npc, "Yes? How is the mission going? It has been quite some time since I sent you on your way."),
                    new PlayerDialogue("It's over - it's finally over."),
                    new NPCDialogue(npc, "Over? Alright, report on what happened."),
                    new PlayerDialogue("I found your sergeant in the cave, injured, but alive, and I defeated the demon!"),
                    new NPCDialogue(npc, "Splendid! No service such as what you have done for me goes unrewarded in my kingdom." +
                            " I personally made a visit to the Royal Treasury to withdraw your reward!"),
                    new ActionDialogue(() -> {
                        PlayerCounter.MONKEY_MADNESS.increment(player, 1);//stage 3
                        player.getInventory().add(4587, 1);
                        player.getStats().addXp(StatType.Slayer, 1160, true);
                        player.questComplete("You have completed Monkey Madness",
                                "25,500 Slayer Xp", "1x Dragon Scimitar");
                    }
                    ));
        }
    }

    private static void acceptQuest(Player player, NPC npc) {
        PlayerCounter.MONKEY_MADNESS.increment(player, 1);//stage 1
        player.dialogue(
                new PlayerDialogue("Yes, I am brave enough to face this demon!"),
                new NPCDialogue(npc, "Thank you hero, you are brave indeed! Use the trapdoor next to us to teleport to the demon!"),
                new MessageDialogue("Use the trapdoor to find the demon below.")
        );
    }

    public static void display(Player player) {
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 0 && player.getStats().get(StatType.Attack).fixedLevel >= 60) {
            player.sendScroll("<col=8B0000>Monkey Madness",
                    "<col=8B0000>Requirements",
                    "<str>60 attack</str>",
                    "",
                    "I should find <col=8B0000>King Narnode</col> at the Grand Tree to",
                    "begin this quest.");
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 0 && player.getStats().get(StatType.Attack).fixedLevel < 60) {
            player.sendScroll("<col=8B0000>Monkey Madness",
                    "<col=8B0000>Requirements",
                    "60 attack",
                    "",
                    "I should find <col=8B0000>King Narnode</col> at the Grand Tree to",
                    "begin this quest.");
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 1) {
            player.sendScroll("<col=8B0000>Monkey Madness",
                    "<col=8B0000>Requirements",
                    "<str>60 attack</str>",
                    "",
                    "<str>I should find <col=8B0000>King Narnode</col> at the Grand Tree to</str>",
                    "<str>begin this quest.</str>",
                    "",
                    "The King has asked me to slay the demon dwelling",
                    "underneath the Grand Tree.");
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 2) {
            player.sendScroll("<col=8B0000>Monkey Madness",
                    "<col=8B0000>Requirements",
                    "<str>60 attack</str>",
                    "",
                    "<str>I should find <col=8B0000>King Narnode</col> at the Grand Tree to</str>",
                    "<str>begin this quest.</str>",
                    "",
                    "<str>The King has asked me to slay the demon dwelling</str>",
                    "<str>underneath the Grand Tree.</str>",
                    "",
                    "I have slayed the demon. I should see if the King needs",
                    "anymore help.");
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) == 3) {
            player.sendScroll("<col=8B0000>Monkey Madness",
                    "<col=8B0000>Requirements",
                    "<str>60 attack</str>",
                    "",
                    "<str>I should find <col=8B0000>King Narnode</col> at the Grand Tree to</str>",
                    "<str>begin this quest.</str>",
                    "",
                    "<str>The King has asked me to slay the demon dwelling</str>",
                    "<str>underneath the Grand Tree.</str>",
                    "",
                    "<str>I have slayed the demon. I should see if the King needs</str>",
                    "<str>anymore help.</str>",
                    "<col=8B0000>QUEST COMPLETE!");
        }
    }

}
