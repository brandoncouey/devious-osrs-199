package io.ruin.model.entity.npc.actions;

import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;

public class XmasQuest {

    static {
        NPCAction.register(1361, "talk-to", (player, npc) -> {
            if (PlayerCounter.SANTA.get(player) == 0) {
                player.dialogue(
                        new NPCDialogue(npc, "Ho..ho...ho..."),
                        new PlayerDialogue("What's wrong, Santa?"),
                        new NPCDialogue(npc, "Well, I was on my way to deliver presents to all of the lovely people of Devious, but my sleigh malfunctioned mid air and" +
                                "all of the presents were scattered across the world!"),
                        new NPCDialogue(npc, "And now that the Anti-Santa got word of this, he has vowed to ruin Christmas by stealing all of the lost presents before" +
                                " we can collect them in time!"),
                        new PlayerDialogue("Wow, that does sound awful. We can't let this ruin Christmas. What can I do to help, Santa?"),
                        new NPCDialogue(npc, "I need you to collect presents during your adventures across Devious! You should be able to find them while doing any" +
                                " activities you normally do, like"),
                        new NPCDialogue(npc, "slayer tasks, skilling, bossing, and even tournaments! If you can bring me back 100 presents, I'll make sure to give you" +
                                " your Christmas present early!"),
                        new PlayerDialogue("100 presents. You got it, Santa!"),
                        new ActionDialogue(() -> {
                            PlayerCounter.SANTA.increment(player, 1);//stage 1
                        })
                );
            }
            if (PlayerCounter.SANTA.get(player) == 1 && !player.getInventory().contains(6855, 100)) {
                player.dialogue(
                        new MessageDialogue("Return to Santa when you have 100 presents!")
                );
            }
            if (PlayerCounter.SANTA.get(player) == 1 && player.getInventory().contains(6855, 100)) {
                player.dialogue(
                        new PlayerDialogue("Santa, great news! I've returned with 100 presents!"),
                        new NPCDialogue(npc, "That is great news indeed! Christmas has been saved, thanks to your brave heroics! I know you humans like to dress up" +
                                " as me this time of year, so please"),
                        new NPCDialogue(npc, "accept this custom made Santa Outfit I had made just for you! Merry Christmas, and Happy Holidays, hero!"),
                        new MessageDialogue("You have saved Christmas and receive a Santa Outfit!"),
                        new ActionDialogue(() -> {
                            PlayerCounter.SANTA.increment(player, 1);//stage 2
                            player.getInventory().addOrDrop(12887, 1);
                            player.getInventory().addOrDrop(12888, 1);
                            player.getInventory().addOrDrop(12889, 1);
                            player.getInventory().addOrDrop(12890, 1);
                            player.getInventory().addOrDrop(12891, 1);
                        })
                );
            }
            if (PlayerCounter.SANTA.get(player) == 2) {
                player.dialogue(
                        new MessageDialogue("You've already helped save Christmas!")
                );
            }

        });
    }
}