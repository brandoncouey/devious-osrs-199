package io.ruin.model.entity.npc.actions;

import io.ruin.model.diaries.pvp.PvPDiaryEntry;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;

public class WizardCromperty {

    static {
        NPCAction.register(8480, "talk-to", (player, npc) -> {
            player.getMovement().startTeleport(-1, event -> {
                player.animate(2140);
                event.delay(1);
                event.delay(1);
                player.animate(714);
                player.graphics(111, 110, 0);
                event.delay(3);
                player.sendMessage("The wizard teleports you to the essence mine.");
                player.getMovement().teleport(2910, 4830, 0);
                player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.TELEPORT_ESSENCE_ARD);
            });
        });
        NPCAction.register(8480, "teleport", (player, npc) -> {
            player.getMovement().startTeleport(-1, event -> {
                player.animate(2140);
                event.delay(1);
                event.delay(1);
                player.animate(714);
                player.graphics(111, 110, 0);
                event.delay(3);
                player.sendMessage("The wizard teleports you to the essence mine.");
                player.getMovement().teleport(2910, 4830, 0);
                player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.TELEPORT_ESSENCE_ARD);
            });
        });
        NPCAction.register(8481, "talk-to", (player, npc) -> {
            player.getMovement().startTeleport(-1, event -> {
                player.animate(2140);
                event.delay(1);
                event.delay(1);
                player.animate(714);
                player.graphics(111, 110, 0);
                event.delay(3);
                player.sendMessage("The wizard teleports you to the essence mine.");
                player.getMovement().teleport(2910, 4830, 0);
                player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.TELEPORT_ESSENCE_ARD);
            });
        });
        NPCAction.register(4913, "talk-to", (player, npc) -> {
            player.getMovement().startTeleport(-1, event -> {
                player.animate(2140);
                event.delay(1);
                event.delay(1);
                player.animate(714);
                player.graphics(111, 110, 0);
                event.delay(3);
                player.sendMessage("Brimstail teleports you to the essence mine.");
                player.getMovement().teleport(2910, 4830, 0);
                player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.TELEPORT_ESSENCE_MINE);
            });
        });
        NPCAction.register(4913, "teleport", (player, npc) -> {
            player.getMovement().startTeleport(-1, event -> {
                player.animate(2140);
                event.delay(1);
                event.delay(1);
                player.animate(714);
                player.graphics(111, 110, 0);
                event.delay(3);
                player.sendMessage("Brimstail teleports you to the essence mine.");
                player.getMovement().teleport(2910, 4830, 0);
                player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.TELEPORT_ESSENCE_MINE);
            });
        });
    }

}