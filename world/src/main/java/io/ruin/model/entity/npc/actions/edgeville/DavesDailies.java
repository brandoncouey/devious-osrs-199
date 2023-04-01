package io.ruin.model.entity.npc.actions.edgeville;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.tasks.DailyTask;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class DavesDailies {
    static {
        NPCAction.register(15109, "talk-to", (player, npc) -> {
            player.dialogue(new NPCDialogue(npc, "Hello, adventurer. How may I help you?"),
                    new OptionsDialogue(
                            new Option("What are daily tasks?", () -> {
                                player.dialogue(
                                        new NPCDialogue(npc, "Daily tasks are assignments given to you each day to complete within 24 hours. Finishing all three of your" +
                                                "tasks awards you with points you can spend in my shop. Tasks reset every day at 12am server time.")
                                );
                            }),
                            new Option("I need new daily tasks", plr -> DailyTask.assignTask(player)),
                            new Option("I've finished all of my dailies", plr -> finishedTasks(player, npc)),
                            new Option("What are my current tasks?", () -> DailyTask.openDailyTasksViewer(player))
                    ));
        });
        NPCAction.register(15109, "view-tasks", (player, npc) -> {
            DailyTask.openDailyTasksViewer(player);
        });
        NPCAction.register(15109, "open-shop", (player, npc) -> {
            ShopManager.openIfExists(player, "dailypointshop");
        });
    }

    public static void finishedTasks(Player player, NPC npc) {
        int amount = 5;
        if (player.amountDonated >= 10) {
            amount = 5 + Random.get(5, 10);
        }
        if (player.dailyCount < 3) {
            player.dialogue(
                    new MessageDialogue("You have not yet finished all three daily tasks.")
            );
        }
        if (player.dailyCount > 2) {
            int finalAmount = amount;
            player.dialogue(
                    new NPCDialogue(npc, "Ah, yes, you have completed all three tasks asigned to you for today! Please accept this Daily Task Cache as a reward," +
                            " as well as 5 extra daily task points!"),
                    new MessageDialogue("You receive a daily task cache and 5 daily task points!"),
                    new ActionDialogue(() -> {
                        player.dailyCount = 0;
                        player.dailyTaskPoints += finalAmount;
                        player.getInventory().add(6199, 1);
                    }
                    ));
        }
    }
}