package io.ruin.model.quests;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.*;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class DoricsQuest {

    private static final int DORIC = 3893;

    static {
        NPCAction.register(DORIC, "talk-to", DoricsQuest::doricDialogue);
    }

    private static void doricDialogue(Player player, NPC npc) {
        if (PlayerCounter.DORICS_QUEST.get(player) == 0) {
            player.dialogue(new NPCDialogue(npc, "Hello traveller, what brings you to my humble smithy?"),
                    new OptionsDialogue(
                            new Option("I wanted to use your anvils", () -> player.dialogue(
                                    new PlayerDialogue("I wanted to use your anvils"),
                                    new NPCDialogue(npc, "My anvils get enough work with my own use. I make pickaxes, and it takes a lot of hard work." +
                                            " If you could get me some more materials, then I could let you use them."),
                                    new OptionsDialogue("Bring Doric the materials?",
                                            new Option("Sure", () -> acceptQuest(player, npc)),
                                            new Option("Nevermind", () -> {
                                                player.dialogue(new PlayerDialogue("Not right now."));
                                            })
                                    )
                            )),
                            new Option("I wanted to use your whetstone", () -> whetStone(player, npc)),

                            new Option("Nevermind", () -> player.dialogue(new PlayerDialogue("Nevermind.")))
                    ));
        }
        if (PlayerCounter.DORICS_QUEST.get(player) == 1) {
            if (player.getInventory().contains(434, 12) && player.getInventory().contains(436, 6) && player.getInventory().contains(440, 8)) {
                hasItems(player, npc);
            } else {
                player.dialogue(new NPCDialogue(npc, "Have you got my materials yet, traveller?"),
                        new PlayerDialogue("Sorry, I don't have them all yet."),
                        new NPCDialogue(npc, "Not to worry, stick at it. Remember, I need 12 clay, 6 copper ore, and 8 iron ore."));
            }
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 3 && PlayerCounter.DORICS_QUEST.get(player) == 3) {
            player.dialogue(
                    new NPCDialogue(DORIC, "Good to see ya again, adventurer! What can I do for ye'?"),
                    new PlayerDialogue("I come on behalf of the Wise Old Man of Draynor. He mentioned you are in possession of an item I am after. The Black Key."),
                    new MessageDialogue("Your request puts a frightened look on Dorics face."),
                    new NPCDialogue(DORIC, "Don't tell me you need the key to enter Vorkaths lair! That monstorous place only brings death!"),
                    new PlayerDialogue("Unfortunately, yes. I am on a mission from the Myths' Guild to save a historian who has been trying to uncover the history of Dragons. " +
                            "It is believed he made it to Vorkath and hasn't returned."),
                    new NPCDialogue(DORIC, "Sigh. Why must there always be a hero in this situation. I'll give you the key laddy, but I'll need something in return."),
                    new PlayerDialogue("Ok, what is it?"),
                    new NPCDialogue(DORIC, "I've been supplying weapons for the war of the Gods, and both sides have put in a large order for poisoned weapons." +
                            " I need you to bring me 50 weapon poison+ potions. Then you'll get my key."),
                    new PlayerDialogue("50 weapon poison+ potions. Got it. I will return soon, Doric!"),
                    new NPCDialogue(DORIC, "Hurry, hero. We both are on very limited time!"),
                    new MessageDialogue("Bring Doric 50 weapon poison+ potions."),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 4
                    }
                    ));
        }
        if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 4 && player.getInventory().contains(5938, 50)) {
            player.dialogue(
                    new PlayerDialogue("I've got the potions here, Doric!"),
                    new NPCDialogue(DORIC, "Aye, you are a man of your word, as am I. Here is the Black Key."),
                    new MessageDialogue("Doric hands you the Black Key."),
                    new ActionDialogue(() -> {
                        PlayerCounter.DRAGON_SLAYER_2.increment(player, 1);//stage 5
                        player.getInventory().remove(5938, 50);
                        player.getInventory().add(3463, 1);
                    }
                    ));
        } else if (PlayerCounter.DRAGON_SLAYER_2.get(player) == 4 && !player.getInventory().contains(5938, 50)) {
            player.dialogue(
                    new MessageDialogue("Bring Doric 50 weapon poison+ potions.")
            );
        }
    }

    private static void hasItems(Player player, NPC npc) {
        player.getInventory().remove(434, 12);
        player.getInventory().remove(436, 6);
        player.getInventory().remove(440, 8);
        PlayerCounter.DORICS_QUEST.increment(player, 1);
        player.dialogue(new PlayerDialogue("I have everything you need!"),
                new NPCDialogue(npc, "Many thanks. Pass them here, please. Take this pickaxe for your trouble," +
                        " and please use my anvils any time you want!"),
                new ActionDialogue(() -> {
                    PlayerCounter.DORICS_QUEST.increment(player, 1);
                    player.getInventory().add(1275, 1);
                    player.getStats().addXp(StatType.Mining, 1363, true);
                    player.questComplete("You have completed Doric's Quest", "30,000 Mining Xp", "1x Rune Pickaxe");
                    //send quest complete interface
                }));
    }

    private static void whetStone(Player player, NPC npc) {
        player.dialogue(new PlayerDialogue("I wanted to use your whetstone."),
                new NPCDialogue(npc, "The whetstone is for more advanced smithing, but I could let you use it as well " +
                        "as my anvils if you could get me some more materials."));
    }

    private static void acceptQuest(Player player, NPC npc) {
        player.getInventory().add(1265, 1);
        PlayerCounter.DORICS_QUEST.increment(player, 1);
        player.dialogue(new PlayerDialogue("Sure, what do you need?"),
                new NPCDialogue(npc, "Clay is what I use more than anything, to make casts. Could you get me 12 clay, 6 copper ore, and 8 iron ore, " +
                        "please? I could pay a little, and let you use my anvils. Take this pickaxe with you just in case you need it."),
                new PlayerDialogue("Where can I find those?"),
                new NPCDialogue(npc, "You'll be able to find all those ores in the rocks just inside the Dwarven Mine. " +
                        "Use the teleport crystal at home, then under skilling, select the mining guild teleport!"));

    }

    public static void display(Player player) {
        if (PlayerCounter.DORICS_QUEST.get(player) == 0) {
            player.sendScroll("<col=8B0000>Doric's Quest",
                    "",
                    "Speak to <col=8B0000>Doric</col> north-west of falador to begin.");
        }
        if (PlayerCounter.DORICS_QUEST.get(player) == 1) {
            player.sendScroll("<col=8B0000>Doric's Quest",
                    "",
                    "<str>Speak to <col=8B0000>Doric</col> north-west of falador to begin.</str>",
                    "",
                    "<col=8B0000>Doric</col> has asked me to bring him 12 clay, 6 copper ore,",
                    "and 8 iron ore in exchange for access to his anvil.");
        }
        if (PlayerCounter.DORICS_QUEST.get(player) == 1
                && player.getInventory().contains(434, 12)
                && player.getInventory().contains(436, 6)
                && player.getInventory().contains(440, 8)) {
            player.sendScroll("<col=8B0000>Doric's Quest",
                    "",
                    "<str>Speak to <col=8B0000>Doric</col> north-west of falador to begin.</str>",
                    "",
                    "<str><col=8B0000>Doric</col> has asked me to bring him 12 clay, 6 copper ore,</str>",
                    "<str>and 8 iron ore in exchange for access to his anvil.</str>",
                    "",
                    "I should return to <col=8B0000>Doric</col> with all of the supplies.");
        }
        if (PlayerCounter.DORICS_QUEST.get(player) == 3) {
            player.sendScroll("<col=8B0000>Doric's Quest",
                    "",
                    "<str>Speak to <col=8B0000>Doric</col> north-west of falador to begin.</str>",
                    "",
                    "<str><col=8B0000>Doric</col> has asked me to bring him 12 clay, 6 copper ore,</str>",
                    "<str>and 8 iron ore in exchange for access to his anvil.</str>",
                    "",
                    "<str>I should return to <col=8B0000>Doric</col> with all of the supplies.</str>",
                    "<col=8B0000>QUEST COMPLETE!");
        }
    }

}
