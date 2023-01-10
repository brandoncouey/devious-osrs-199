package io.ruin.model.quests;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.stat.StatType;

public class SheepShearer {

    static {
        NPCAction.register(732, "talk-to", (player, npc) -> talkTo(player));
    }

    public static void talkTo(Player player) {
        if (PlayerCounter.SHEEP_SHEARER.get(player) == 0) {
            player.dialogue(
                    new NPCDialogue(732, "What are you doing on my land? You're not the one who keeps leaving all my gates open and letting out all my sheep, are you?"),
                    new OptionsDialogue("Select an option",
                            new Option("I'm looking for a quest.", () -> StartConvo(player))
                    )
            );
        } else if (PlayerCounter.SHEEP_SHEARER.get(player) == 1) {
            if (player.getInventory().contains(1759, 20)) {
                player.dialogue(
                        new PlayerDialogue("I need to talk to you about shearing these sheep!"),
                        new NPCDialogue(732, "Oh. How are you doing getting those balls of wool?"),
                        new PlayerDialogue("I have them all right here!"),
                        new NPCDialogue(732, "Give 'em here then."),
                        new PlayerDialogue("That's the last of them."),
                        new NPCDialogue(732, "I guess I'd better pay you then."),
                        new ActionDialogue(() -> {
                            player.getInventory().remove(1759, 20);
                            player.questPoints += 1;
                            player.sendMessage("You've completed Sheep Shearer. You now have " + player.questPoints + " quest points.");
                            PlayerCounter.SHEEP_SHEARER.increment(player, 1);
                            PlayerCounter.QUESTS_COMPLETED.increment(player, 1);
                            player.getInventory().add(995, 500000);
                            player.getStats().addXp(StatType.Crafting, 3409, true);
                            player.openInterface(InterfaceType.MAIN, 153);
                            player.getPacketSender().sendString(153, 4, "You have completed Sheep Shearer!!");
                            player.getPacketSender().sendString(153, 9, "1 Quest Point");
                            player.getPacketSender().sendString(153, 10, "500,000 Gold");
                            player.getPacketSender().sendString(153, 11, "100,000 Crafting Experience");
                            player.getPacketSender().sendString(153, 12, "");
                            player.getPacketSender().sendString(153, 13, "");
                            player.getPacketSender().sendString(153, 14, "");
                            player.getPacketSender().sendString(153, 15, "");
                            player.getPacketSender().sendString(153, 6, "Total Quest Points: " + player.questPoints);
                            player.getPacketSender().sendModel(153, 5, 2620);
                        })
                );
            } else {
                player.dialogue(
                        new PlayerDialogue("I need to talk to you about shearing these sheep!"),
                        new NPCDialogue(732, "Oh. How are you doing getting those balls of wool?"),
                        new PlayerDialogue("I still need to collect some more."),
                        new NPCDialogue(732, "Well less talking and more shearing!")
                );
            }
        } else if (PlayerCounter.SHEEP_SHEARER.get(player) == 2) {
            player.dialogue(
                    new NPCDialogue(732, "Thanks for the help " + player.getName()),
                    new PlayerDialogue("You're welcome")
            );
        }
    }

    public static void StartConvo(Player player) {
        player.dialogue(
                new NPCDialogue(732, "You're after a quest, you say? Actually, I could do with a bit of help."),
                new NPCDialogue(732, "My sheep are getting mighty woolly. I'd be much obliged if you could shear them. And while you're at it, spin the wool for me too."),
                new NPCDialogue(732, "Yes, that's it. Bring me 20 balls of wool. And I'm sure I could sort out some sort of payment. Of course, there's the small matter of The Thing."),
                new PlayerDialogue("What do you mean, The Thing?"),
                new NPCDialogue(732, "Well now, no one has ever seen The Thing. That's why we call it The Thing, 'cos we don't know what it is."),
                new NPCDialogue(732, "Some say it's a black hearted shapeshifter, hungering for the souls of hard working decent folk like me. Others say it's just a sheep."),
                new NPCDialogue(732, "Well I don't have all day to stand around and gossip. Are you going to shear my sheep or what!"),
                new OptionsDialogue("Start the Sheap Shearer quest?",
                        new Option("Yes, okay. I can do that.", () -> StartQuest(player)),
                        new Option("Nevermind."))
        );
    }

    public static void StartQuest(Player player) {
        player.dialogue(
                new ActionDialogue(() -> {
                    PlayerCounter.SHEEP_SHEARER.increment(player, 1);
                }),
                new NPCDialogue(732, "Good! Now one more thing, do you actually know how to shear a sheep?"),
                new PlayerDialogue("Err. No, I don't know actually."),
                new NPCDialogue(732, "Well it's simple, simply get a pair of shearers and use them on a sheep to shear it!"),
                new PlayerDialogue("That's all I have to do?"),
                new NPCDialogue(732, "Well once you've collected some wool you'll need to spin it into balls.")
        );
    }
}

