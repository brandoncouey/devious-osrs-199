package io.ruin.model.quests;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class CooksAssistant {

    private static final int COOK = 4626;

    static {
        NPCAction.register(COOK, "talk-to", CooksAssistant::cookDialogue);
    }

    private static void cookDialogue(Player player, NPC npc) {
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 0) {
            player.dialogue(new NPCDialogue(npc, "What am I to do?"),
                    new OptionsDialogue(
                            new Option("What's wrong?", () -> player.dialogue(
                                    new PlayerDialogue("What's wrong?"),
                                    new NPCDialogue(npc, "Oh dear, oh dear, oh dear, I'm in a terrible, terrible mess!" +
                                            " It's the Duke's birthday today, and I should be making him a lovely, big birthday cake"),
                                    new NPCDialogue(npc, "I've forgotten to get the ingredients. I'll never get Them in time now." +
                                            " He'll sack me! What will I do? I have our children and a goat to look after. Would you help me? Please?"),
                                    new OptionsDialogue("Help the cook?",
                                            new Option("I'm always happy to help a cook in distress.", () -> acceptQuest(player, npc)),
                                            new Option("I can't right now, Maybe later.", () -> {
                                                player.dialogue(new PlayerDialogue("No, I don't feel like it. Maybe later."));
                                            })
                                    )
                            )),
                            new Option("Can you make me a cake?", () -> player.dialogue(
                                    new PlayerDialogue("You're a cook, why don't you bake me a cake?"),
                                    new NPCDialogue(npc, "*sniff* Don't talk to me about cakes..."),
                                    new ActionDialogue(() -> {
                                        whatsWong(player, npc);
                                    })
                            )),
                            new Option("You don't look very happy.", () -> player.dialogue(
                                    new PlayerDialogue("You don't look very happy.."),
                                    new NPCDialogue(npc, "No' I'm not. The whole world is caving around me."),
                                    new ActionDialogue(() -> {
                                        whatsWong(player, npc);
                                    })
                            )),

                            new Option("Nice hat!", () -> player.dialogue(
                                    new PlayerDialogue("Nice hat!"),
                                    new NPCDialogue(npc, "Err thank you. It's a pretty ordinary cooks hat really."),
                                    new PlayerDialogue("Still suits you. The trousers are pretty special too."),
                                    new NPCDialogue(npc, "It's all standard cook's issue uniform..."),
                                    new PlayerDialogue("The whole hat, apron, stripey trousers ensemble it works. It makes you look like a real cook."),
                                    new NPCDialogue(npc, "I am a real cook! I haven't got time to be chatting about Culinary Fashion." +
                                            " I'm in desperate need for help!"),
                                    new ActionDialogue(() -> {
                                        whatsWong(player, npc);
                                    })
                            ))

                    ));
        }
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 1) {
            if (player.getInventory().contains(1927, 1) && player.getInventory().contains(1933, 1) && player.getInventory().contains(1944, 1)) {
                player.dialogue(new NPCDialogue(npc, "How are you getting on with finding the ingredients?"),
                        new ActionDialogue(() -> {
                            hasItems(player, npc);
                        }));
            } else {
                player.dialogue(new NPCDialogue(npc, "How are you getting on with finding the ingredients?"),
                        new PlayerDialogue("I haven't got them yet, I'm still looking."),
                        new NPCDialogue(npc, "Please get the ingredients quickly. I'm running out of time! The Duke will throw me into the streets!"));
            }
        }
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 3) {
            player.dialogue(new NPCDialogue(npc, "Many thanks again for assisting me!"));
        }
    }

    private static void hasItems(Player player, NPC npc) {
        PlayerCounter.COOKS_ASSISTANT.increment(player, 1);
        player.getInventory().remove(1927, 1);
        player.getInventory().remove(1933, 1);
        player.getInventory().remove(1944, 1);
        player.dialogue(new PlayerDialogue("Here's a bucket of milk, a pot of flour, and a fresh egg."),
                new NPCDialogue(npc, "You've brought me everything I need! I am saved! Thank you!"),
                new PlayerDialogue("So, do I get to go to the Duke's party?"),
                new NPCDialogue(npc, "I'm afraid not, only the big cheeses get to dine with the Duke."),
                new PlayerDialogue("Well, maybe one day I'll be important enough to sit on the Duke's table."),
                new NPCDialogue(npc, "Maybe, but I won't be holding my breath."),
                new ActionDialogue(() -> {
                    PlayerCounter.COOKS_ASSISTANT.increment(player, 1);
                    player.getStats().addXp(StatType.Cooking, 1250, true);
                    player.getInventory().addOrDrop(995, 75000);
                    player.questComplete("You have completed Cook's Assistant!",
                            "20,000 Cooking Xp",
                            "75,000 Coins",
                            "",
                            "",
                            "",
                            "",
                            "");
                    //inter 63?
                })
        );
    }

    private static void whatsWong(Player player, NPC npc) {
        player.dialogue(new PlayerDialogue("What's wrong?"),
                new NPCDialogue(npc, "Oh dear, oh dear, oh dear, I'm in a terrible, terrible mess!" +
                        " It's the Duke's birthday today, and I should be making him a lovely, big birthday cake"),
                new NPCDialogue(npc, "I've forgotten to get the ingredients. I'll never get them in time now." +
                        " He'll sack me! What will I do? I have our children and a goat to look after. Would you help me? Please?"),
                new OptionsDialogue("Help the cook?",
                        new Option("I'm always happy to help a cook in distress.", () -> acceptQuest(player, npc)),
                        new Option("I can't right now, Maybe later.", () -> {
                            player.dialogue(new PlayerDialogue("No, I don't feel like it. Maybe later."));
                        })
                ));
    }

    public static void ingredientQuestions(Player player, NPC npc) {
        player.dialogue(
                new OptionsDialogue(
                        new Option("Where do I find some flour?", () -> player.dialogue(
                                new PlayerDialogue("Where do I find some flour?"),
                                new NPCDialogue(npc, "There is a Mill fairly close, go North and then West. Mill Lane Mill is just off the road to Draynor." +
                                        " I usually get my flour from there."),
                                new ActionDialogue(() -> {
                                    ingredientQuestions(player, npc);
                                })
                        )),
                        new Option("How about milk?", () -> player.dialogue(
                                new PlayerDialogue("How about milk?"),
                                new NPCDialogue(npc, "There is a cattle field on the other side of the river, just across the road from Groats Farm."),
                                new NPCDialogue(npc, "Talk to Gillie Groats, she look after the dairy Cows -" +
                                        " she'll tell you everything you need to know about milking cows!"),
                                new ActionDialogue(() -> {
                                    ingredientQuestions(player, npc);
                                })
                        )),
                        new Option("And eggs? Where are they found?", () -> player.dialogue(
                                new PlayerDialogue("And eggs? Where are they found?"),
                                new NPCDialogue(npc, "I normally get my eggs from the Groats' farm, on the other side of the river."),
                                new ActionDialogue(() -> {
                                    ingredientQuestions(player, npc);
                                })
                        )),
                        new Option("Actually, I know where to find this stuff.", () -> player.dialogue(
                                new PlayerDialogue("I've got all the information I need. Thanks")
                        ))
                )
        );
    }

    private static void acceptQuest(Player player, NPC npc) {
        PlayerCounter.COOKS_ASSISTANT.increment(player, 1);
        player.dialogue(new PlayerDialogue("Yes, I'll help you."),
                new NPCDialogue(npc, "Oh thank you, thank you. I need milk, an egg and flour. I'd be very grateful if you can get them for me."),
                new PlayerDialogue("So where do I find these ingredients then?"),
                new ActionDialogue(() -> {
                    ingredientQuestions(player, npc);
                }));
    }

    public static void display(Player player) {

        String title = "<col=8B0000>Cook's Assistant";

        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 0) {
            player.sendScroll(title,
                    "",
                    "Speak to the <col=8B0000>Cook</col> in the Lumbridge Castle kitchen.");
        }
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 1) {
            player.sendScroll(title,
                    "",
                    "<str>Speak to the <col=8B0000>Cook</col> in the Lumbridge Castle kitchen.</str>",
                    "",
                    "The <col=8B0000>Cook</col> needs me to gather a bucket of milk,",
                    "a pot of flour, and an egg.");
        }
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 1
                && player.getInventory().contains(1927, 1)
                && player.getInventory().contains(1933, 1)
                && player.getInventory().contains(1944, 1)) {
            player.sendScroll(title,
                    "",
                    "<str>Speak to the <col=8B0000>Cook</col> in the Lumbridge Castle kitchen.</str>",
                    "",
                    "<str>The <col=8B0000>Cook</col> needs me to gather a bucket of milk,</str>",
                    "<str>a pot of flour, and an egg.</str>",
                    "",
                    "I should return to the <col=8B0000>Cook</col> with all of the supplies.");
        }
        if (PlayerCounter.COOKS_ASSISTANT.get(player) == 3) {
            player.sendScroll(title,
                    "",
                    "<str>Speak to the <col=8B0000>Cook</col> in the Lumbridge Castle kitchen.</str>",
                    "",
                    "<str>The <col=8B0000>Cook</col> needs me to gather a bucket of milk,</str>",
                    "<str>a pot of flour, and an egg.</str>",
                    "",
                    "<str>I should return to the <col=8B0000>Cook</col> with all of the supplies.</str>",
                    "<col=8B0000>QUEST COMPLETE!");
        }
    }

}
