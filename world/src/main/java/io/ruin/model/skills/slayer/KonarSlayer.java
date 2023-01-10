package io.ruin.model.skills.slayer;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.ActionDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.shop.ShopManager;

public class KonarSlayer {

    public static final int KONAR = 8623;

    static {
        NPCAction.register(KONAR, "Talk-to", (player, npc) -> player.dialogue(
                new NPCDialogue(npc, "Bringer of death, have you come to serve the balance?"),
                new OptionsDialogue(
                        player.slayerTask == null ?
                                new Option("I need another assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("I need another assignment."),
                                            new ActionDialogue(() -> assignKonar(player, npc))
                                    );
                                }) :
                                new Option("Tell me about my Slayer assignment.", () -> {
                                    player.dialogue(
                                            new PlayerDialogue("Tell me about my Slayer assignment."),
                                            new ActionDialogue(() -> assignKonar(player, npc))
                                    );
                                }),
                        new Option("Do you have anything to trade?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Do you have anything to trade?"),
                                    new NPCDialogue(npc, "I have a wide variety of Slayer equipment for sale! Have a look..").lineHeight(28),
                                    new ActionDialogue(() -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"))
                            );
                        }),
                        new Option("Have you any rewards for me?", () -> {
                            player.dialogue(
                                    new PlayerDialogue("Have you any rewards for me?"),
                                    new NPCDialogue(npc, "I have quite a few rewards you can earn!<br>Take a look..").lineHeight(28),
                                    new ActionDialogue(() -> rewards(player))
                            );
                        }),
                        new Option("Er... Nothing...", () -> player.dialogue(new PlayerDialogue("Er... Nothing...").animate(588)))
                )
        ));
        NPCAction.register(KONAR, "Assignment", KonarSlayer::assignKonar);
        NPCAction.register(KONAR, "Trade", (player, npc) -> ShopManager.openIfExists(player, "06bf1d5a-e4d8-49f5-9aac-6229a965b98a"));
        NPCAction.register(KONAR, "Rewards", (player, npc) -> rewards(player));
    }

    private static void assignKonar(Player player, NPC npc) {
        if (player.getCombat().getLevel() < 75) {
            player.dialogue(new NPCDialogue(npc, "You need atleast 75 combat before i can assign you a task."));
            return;
        }
        if (Slayer.getTask(player) == null) {
            Slayer.set(player, SlayerTask.Type.KONAR);
            player.currentSlayerMaster = KONAR;
            KonarTaskLocations.assignLocation(player, player.slayerTaskName);
            String location = KonarTaskLocations.TaskLocation.values()[player.taskLocation].getName();
            player.dialogue(new NPCDialogue(npc, "Go bring balance to " + player.slayerTaskRemaining + " " + player.slayerTaskName + " in " + location).lineHeight(28));
            return;
        }
        if (Slayer.getTask(player) != null && player.currentSlayerMaster == KONAR) {
            String location = KonarTaskLocations.TaskLocation.values()[player.taskLocation].getName();
            player.dialogue(new NPCDialogue(npc, "You are still bringing balance to " + player.slayerTaskName + " you have " + player.slayerTaskRemaining + " to go in " + location + " Come back when you're finished."));
        } else if (Slayer.getTask(player) != null) {
            player.dialogue(new NPCDialogue(npc, "You already have a task, Go kill " + player.slayerTaskRemaining + " more " + player.slayerTaskName + " Speak to Turael for an easier task."));
        }
    }

    /**
     * Rewards
     */

    private static void rewards(Player player) {
        SlayerUnlock.openRewards(player);
    }
}
