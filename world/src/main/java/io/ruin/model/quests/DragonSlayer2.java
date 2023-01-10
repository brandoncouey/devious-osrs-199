package io.ruin.model.quests;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class DragonSlayer2 {

    private static final int ALEC_KINCADE = 7950;
    private static final int DALLAS = 8100;
    private static final int WISEOLDMAN = 8154;
    private static final int HISTORIAN = 8163;
    private static final int ZORGOTH = 8136;
    private static final int DORIC = 3893;
    private static final int NARNODE = 8019;

    static {
        NPCAction.register(ALEC_KINCADE, "talk-to", DragonSlayer2::alecDialogue);
        NPCAction.register(DALLAS, "talk-to", DragonSlayer2::dallasDialogue);
        NPCAction.register(WISEOLDMAN, "talk-to", DragonSlayer2::wiseDialogue);
        NPCAction.register(HISTORIAN, "talk-to", DragonSlayer2::historianDialogue);
        //NPCAction.register(DORIC, "talk-to", DragonSlayer2::doricDialogue);
        // NPCAction.register(NARNODE, "talk-to", DragonSlayer2::narnodeDialogue);
        ObjectAction.register(32212, 1, (player, obj) -> climbStairs(player));
    }

    private static void alecDialogue(Player player, NPC npc) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(ALEC_KINCADE, "Greetings adventurer. Welcome to the Myth's Guild."),
                    new OptionsDialogue(
                            new Option("Tell me about the Guild.", () -> aboutGuild(player, npc)),
                            new Option("How can I gain access to the Guild?", () -> accessGuild(player, npc)),
                            new Option("Nevermind")
                    )
            );
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) > 0) {
            player.sendMessage("Alec is quite busy right now.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 12) {
            player.dialogue(
                    new NPCDialogue(ALEC_KINCADE, "You've done it, hero! You've defeated the second mightiest dragon in all of Devious!"),
                    new PlayerDialogue("What do you mean the SECOND mightiest dragon?! I thought Vorkath was the fiercest of them all?"),
                    new NPCDialogue(ALEC_KINCADE, "Well.. During our historians travels, he found that was not the case. There is one more no warrior dares " +
                            "to face, the mighty Galvek, a name that brings fear to many. He is the king of dragons, thought to be long dead."),
                    new NPCDialogue(ALEC_KINCADE, "You now have access to the Myths Guild, where knowledge and adventure awaits only the mightiest and " +
                            "most capable heros such as yourself!"),
                    new NPCDialogue(ALEC_KINCADE, "Thank you again for saving not only our historian, but also Edgeville as well! We are eternally indebted to you. " +
                            "Welcome to the Myths Guild!"),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 13
                        player.getInventory().add(995, 2000000);
                        player.getStats().addXp(StatType.Slayer, 3409, true);
                        player.questComplete("You have completed Dragon Slayer II",
                                "Access to the Myths Guild", "75,000 Slayer Experience", "2,000,000 Coins");
                    }
                    ));
        }
    }

    private static void dallasDialogue(Player player, NPC npc) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 0) {
            player.sendMessage("Dallas seems quite busy at the moment.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 1) {
            player.dialogue(
                    new NPCDialogue(DALLAS, "Hello adventurer. How can I assist you?"),
                    new PlayerDialogue("I have been sent by Alec Kincade. One of the Myths' Guild historians has gone missing. Alec mentioned you may know of his whereabouts?"),
                    new NPCDialogue(DALLAS, "Ah yes, he had come to me seeking the knowledge for how to locate the historic dragon, Vorkath. I know the location " +
                            "of this dragon, however it requires a certain set of keys to enter the lair."),
                    new NPCDialogue(DALLAS, "I'd be glad to assist you, traveller, however I must warn you that this will certainly be an unsafe adventure!" +
                            " One which you may not return from."),
                    new PlayerDialogue("I'm aware of the risks. I promised Alec that I would find the missing historian, and that's what I shall do!" +
                            " Where can I find these keys you speak of?"),
                    new NPCDialogue(DALLAS, "Brilliant! The keys are in different locations. The last I heard, King Narnode of the Grand Tree has the Bronze key, " +
                            "Doric of Falador holds the Black Key, and the Wise Old Man of Draynor holds the Ancient Key. You must obtain all"),
                    new NPCDialogue(DALLAS, "3 in order to enter the lair! You should first seek out the Wise Old Man in Draynor. Speak to me once you have " +
                            "obtained the keys."),
                    new MessageDialogue("Speak to the Wise Old Man in Draynor"),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 2
                    }
                    ));
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 2) {
            player.dialogue(
                    new PlayerDialogue("Where can I find the keys at again?"),
                    new NPCDialogue(DALLAS, "The keys are in different locations. The last I heard, King Narnode of the Grand Tree has the Bronze key, Doric of " +
                            "Falador holds the Black Key, and the Wise Old Man of Draynor holds the Ancient Key.")
            );
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 8) {
            player.dialogue(
                    new NPCDialogue(DALLAS, "How goes the search for the keys, hero?"),
                    new PlayerDialogue("The Wise Old Man of Draynor enchanted all 3 keys into the Ancient Key. I am ready to travel to Vorkaths Lair!"),
                    new NPCDialogue(DALLAS, "Wonderful! If you are all set then we shall be on our way now!"),
                    new OptionsDialogue(
                            new Option("Yes, I am ready!", () -> dallasTravel(player, npc)),
                            new Option("Not yet")
                    )
            );
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 10) {
            player.dialogue(
                    new PlayerDialogue("Dallas, do you know the location of the mountain that Vorkath is residing? I must defeat him to save Edgeville!"),
                    new NPCDialogue(DALLAS, "Aye, I know the location. Vorkath will not be happy to be awoken from a deep sleep. Best you gear up and return" +
                            " to me when ready to be teleported there."),
                    new OptionsDialogue("Travel to Vorkath?",
                            new Option("Yes, I am ready!", () -> teleVork(player, npc)),
                            new Option("Not yet")
                    )
            );
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 11) {

        }
    }

    private static void wiseDialogue(Player player, NPC npc) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) < 2) {
            player.sendMessage("The Wise Old Man seems quite busy at the moment");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 2) {
            player.dialogue(
                    new NPCDialogue(WISEOLDMAN, "Hello, traveler. What brings you to my humble abode? Do you seek knowledge?"),
                    new PlayerDialogue("Knowledge, no.. There is an artifact of yours that I seek. The Ancient Key."),
                    new NPCDialogue(WISEOLDMAN, "Ah yes, one of three keys to Vorkaths Lair. What adventure would be taking you there?"),
                    new PlayerDialogue("I am assisting Alec Kincade. One of his historians hasn't returned from a venture to Vorkaths Lair, and he has tasked me with finding him."),
                    new NPCDialogue(WISEOLDMAN, "Normally I wouldn't give up an artifact such as the ancient key, however, Alec is a dear friend of mine. " +
                            "I would be happy to help. You will need to bring me the other 2 keys so that I can enchant them first."),
                    new PlayerDialogue("Ok, I will go obtain the two other keys!"),
                    new MessageDialogue("Obtain the other 2 keys from Doric and King Narnode."),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 3
                    }
                    ));
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 6 && player.getInventory().contains(3463, 1) && player.getInventory().contains(19566, 1)) {
            player.dialogue(
                    new PlayerDialogue("I have obtained the other 2 keys!"),
                    new NPCDialogue(WISEOLDMAN, "Brilliant! Hand them to me, if you would!"),
                    new MessageDialogue("You hand the keys to the Wise Old Man, as he starts enchanting them in Wizard tongue."),
                    new NPCDialogue(WISEOLDMAN, "Ok, hero.  I have enchanted all three keys and they have combined into one master key. This will grant you access" +
                            " to Vorkaths Lair. However, you must speak to Dallas Jones to learn the"),
                    new NPCDialogue(WISEOLDMAN, "location of the lair, as I have yet to travel there in my journeys. I bid you farewell and good luck, traveller!"),
                    new MessageDialogue("The Wise Old Man hands you the Ancient Key. Speak to Dallas Jones to travel to Vorkaths Lair."),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 8
                        player.getInventory().remove(3463, 1);
                        player.getInventory().remove(19566, 1);
                        player.getInventory().add(6792, 1);
                    }
                    ));
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) >= 8) {
            player.sendMessage("The Wise Old Man seems quite busy at the moment.");
        }
    }

    private static void doricDialogue(Player player, NPC npc) {

    }

    private static void historianDialogue(Player player, NPC npc) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 9) {
            player.dialogue(
                    new NPCDialogue(HISTORIAN, "Thank goodness you are here! I thought I was never going to make it out! Erm, how did you get in here, though?"),
                    new PlayerDialogue("Alec Kincade tasked me with finding you. With the help of Dallas Jones, I was able to retrieve the Ancestral Key to enter the lair." +
                            " If I have the key, how were you able to enter?"),
                    new NPCDialogue(HISTORIAN, "During my travels studying the history of dragons, I discovered a portal that led me straight to Vorkaths Lair! " +
                            "I have found that he no longer resides here. Instead, he is in a deep sleep on"),
                    new NPCDialogue(HISTORIAN, "A remote, icy mountain for the past 100 years. Also, I - what is that?!"),
                    new MessageDialogue("The lair starts to tremor and shake"),
                    new NPCDialogue(HISTORIAN, "Quickly, we must leave now! I hope your ship is still intact!"),
                    new MessageDialogue("Exit the lair and return to Dallas on karamja"),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 10
                    }
                    ));
        }
    }

    private static void teleVork(Player player, NPC npc) {
        player.getMovement().teleport(2272, 4048, 0);
        player.dialogue(
                new MessageDialogue("Defeat vorkath, then return to Alec Kincade at the Myths Guild.")
        );
    }

    private static void dallasTravel(Player player, NPC npc) {
        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 9
        player.getMovement().teleport(1746, 5327, 0);
        player.dialogue(
                new MessageDialogue("Dallas teleports you both to Vorkaths Lair"),
                new NPCDialogue(DALLAS, "Come along, Vorkaths Lair is just up these steps!")
        );
    }

    private static void climbStairs(Player player) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) >= 9) {
            PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 9
            player.getMovement().teleport(1744, 5321, 1);
            player.dialogue(
                    new NPCDialogue(DALLAS, "Come along, Vorkaths Lair is just up these steps!")
            );
        }
    }

    private static void aboutGuild(Player player, NPC npc) {
        player.dialogue(
                new NPCDialogue(ALEC_KINCADE, "The Myths Guild is a place that only allows entry to the noblest of heros. Those who can enter will find many benefits, " +
                        "such as the metal dragon vault, our own shops, and much more."),
                new OptionsDialogue(
                        new Option("Tell me about the Guild.", () -> aboutGuild(player, npc)),
                        new Option("How can I gain access to the Guild?", () -> accessGuild(player, npc)),
                        new Option("Nevermind")
                )
        );
    }

    private static boolean hasReqs(Player player) {
        return player.getStats().get(StatType.Herblore).fixedLevel >= 73 && PlayerCounter.MONKEY_MADNESS.get(player) >= 3;
    }

    private static void accessGuild(Player player, NPC npc) {
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) > 0) {
            player.dialogue(
                    new NPCDialogue(ALEC_KINCADE, "You are already on a mission to gain access to the guild.")
            );
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 0 && hasReqs(player) == true) {
            player.dialogue(
                    new NPCDialogue(ALEC_KINCADE, "Only the most worthy are allowed access into the Myth's Guild. If you want entry, you'll need to prove yourself."),
                    new PlayerDialogue("How can I do that?"),
                    new NPCDialogue(ALEC_KINCADE, "As time passes, that which was once fact, becomes myth. Some tales are just that, tales. " +
                            "However, some tales are something more."),
                    new NPCDialogue(ALEC_KINCADE, "Here at the Myths' Guild, we live to tell the difference between the two. If you wish to join us, you must do the same."),
                    new PlayerDialogue("So what myth would you like me to investigate?"),
                    new NPCDialogue(ALEC_KINCADE, "For as long as history remembers, dragons have roamed these lands. But despite how long they've been here, " +
                            "much of their history remains hidden."),
                    new NPCDialogue(ALEC_KINCADE, "One of our historians went searching to uncover this history, but he has not returned after quite some time away." +
                            " I ask you to find our historian, and answer these questions for us. Do you feel up for the challenge?"),
                    new OptionsDialogue(
                            new Option("Yes, I will help!", () -> acceptQuest(player, npc)),
                            new Option("Nevermind")
                    )
            );
        }
        if (hasReqs(player) == false) {
            player.dialogue(
                    new NPCDialogue(ALEC_KINCADE, "Well, first you need to gain a Herblore level of 73. Then, you must complete the Monkey Madness quest. Then, " +
                            "you may return to me and learn how to enter the guild.")
            );
        }
    }

    private static void acceptQuest(Player player, NPC npc) {
        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 1
        player.dialogue(
                new PlayerDialogue("Yes, I would be honored to help. Where should I begin?"),
                new NPCDialogue(ALEC_KINCADE, "Head to Karamja Wine, Spirits, and Beers. There, you will find Dallas Jones. He was the last " +
                        "known person to see our missing Historian.")
        );
    }

    public static void display(Player player) {
        String herbloreReq = "";
        String mmCompelte = "";
        if (player.getStats().get(StatType.Herblore).fixedLevel >= 73) {
            herbloreReq = "<str>73 Herblore</str>";
        }
        if (player.getStats().get(StatType.Herblore).fixedLevel < 73) {
            herbloreReq = "73 Herblore";
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) < 3) {
            mmCompelte = "Complete Monkey Madness";
        }
        if (PlayerCounter.MONKEY_MADNESS.get(player) >= 3) {
            mmCompelte = "<str>Complete Monkey Madness</str>";
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 0) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "I can start this quest by speaking to <col=8B0000>Alec Kincade",
                    "who can be found at the <col=8B0000>Myths' Guild.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 1) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to",
                    "help me find the missing historian.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 2) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "I need to obtain 3 keys in order to enter the dragon lair.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 3) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The Wise Old Man needs the other two keys before he",
                    "can enchant all three.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 4) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<col=8B0000>Doric</col> will give me the Black Key in exchange for",
                    "<col=8B0000>50</col> weapon poison+ potions.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 5) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 6) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col>",
                    "until I retrieve the Kings key.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 7) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col>",
                    "until I retrieve the Kings key.",
                    "",
                    "I've given both keys to the Wise Old Man. I should",
                    "seek out <col=8B0000>Dallas Jones</col> on <col=8B0000>Karamja</col> now.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 8 || PlayerCounter.DRAGON_SLAYER_2.get(player) == 9) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "<str>I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col></str>",
                    "<str>until I retrieve the Kings key.</str>",
                    "",
                    "<str>I've given both keys to the Wise Old Man. I should</str>",
                    "<str>seek out <col=8B0000>Dallas Jones</col> on <col=8B0000>Karamja</col> now.</str>",
                    "",
                    "I should speak with <col=8B0000>Dallas</col> to travel to the lair.");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 10) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "<str>I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col></str>",
                    "<str>until I retrieve the Kings key.</str>",
                    "",
                    "<str>I've given both keys to the Wise Old Man. I should</str>",
                    "<str>seek out <col=8B0000>Dallas Jones</col> on <col=8B0000>Karamja</col> now.</str>",
                    "",
                    "<str>I should speak with <col=8B0000>Dallas</col> to travel to the lair.</str>",
                    "",
                    "We have located the historian, and should return",
                    "to the Myhts Guild");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 11) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "<str>I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col></str>",
                    "<str>until I retrieve the Kings key.</str>",
                    "",
                    "<str>I've given both keys to the Wise Old Man. I should</str>",
                    "<str>seek out <col=8B0000>Dallas Jones</col> on <col=8B0000>Karamja</col> now.</str>",
                    "",
                    "<str>I should speak with <col=8B0000>Dallas</col> to travel to the lair.</str>",
                    "",
                    "<str>We have located the historian, and should return</str>",
                    "<str>to the Myhts Guild</str>",
                    "",
                    "Defeat Vorkath");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 12) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<str>I can start this quest by speaking to <col=8B0000>Alec Kincade</str>",
                    "<str>who can be found at the <col=8B0000>Myths' Guild.</str>",
                    "",
                    "<str>Alec suggested I speak with <col=8B0000>Dallas Jones</col> in <col=8B0000>Karamja</col> to</str>",
                    "<str>help me find the missing historian.</str>",
                    "",
                    "<str>I need to obtain 3 keys in order to enter the dragon lair.</str>",
                    "",
                    "The <col=8B0000>Wise Old Man</col> needs the other two keys before he",
                    "can enchant all three.",
                    "",
                    "<str><col=8B0000>Doric</col> will give me the Black Key in exchange for</str>",
                    "<str><col=8B0000>50</col> weapon poison+ potions.</str>",
                    "",
                    "<str>I need to kill dragons in the <col=8B0000>Metal Dragon Vault</col></str>",
                    "<str>until I retrieve the Kings key.</str>",
                    "",
                    "<str>I've given both keys to the Wise Old Man. I should</str>",
                    "<str>seek out <col=8B0000>Dallas Jones</col> on <col=8B0000>Karamja</col> now.</str>",
                    "",
                    "<str>I should speak with <col=8B0000>Dallas</col> to travel to the lair.</str>",
                    "",
                    "<str>We have located the historian, and should return</str>",
                    "<str>to the Myhts Guild</str>",
                    "",
                    "<str>Defeat Vorkath</str>",
                    "",
                    "I should speak with <col=8B0000>Alec Kincade</col> again");
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 13) {
            player.sendScroll("<col=8B0000>Dragon Slayer II",
                    "<col=8B0000>Requirements",
                    "" + herbloreReq + "",
                    "" + mmCompelte + "",
                    "",
                    "<col=8B0000>Quest Complete!");
        }
    }

}
