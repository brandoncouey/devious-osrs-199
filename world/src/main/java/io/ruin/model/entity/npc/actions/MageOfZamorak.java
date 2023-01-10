package io.ruin.model.entity.npc.actions;

import io.ruin.api.utils.Random;
import io.ruin.model.diaries.wilderness.WildernessDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.skills.runecrafting.Abyss;

public class MageOfZamorak {

    private static final int MAGE_OF_ZAMORAK = 2581;

    static {
        NPCAction.register(MAGE_OF_ZAMORAK, "talk-to", (player, npc) -> player.dialogue(new NPCDialogue(npc, "Hello, " + player.getName() + ". How can I be of assistance?"),
                new OptionsDialogue(
                        new Option("Teleport to abyss", () -> {
                            player.startEvent(event -> {
                                        player.lock();
                                        npc.lock();
                                        npc.faceTemp(player);
                                        npc.forceText("Veniens! Sallakar! Rinnesset!");
                                        npc.animate(1818, 1);
                                        npc.graphics(343, 100, 1);
                                        event.delay(2);
                                        player.unlock();
                                        player.getCombat().skullNormal();
                                        player.getPrayer().drain(1000); //1000 is just a safe "drain all"
                                        Abyss.randomize(player);
                                        player.getMovement().teleport(Random.get(Abyss.OUTER_TELEPORTS));
                                        player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.ABYSS_TELEPORT);
                                        player.resetAnimation();
                                        npc.unlock();
                                    }
                            );
                        }),
                        new Option("Nevermind"
                        ))));
        NPCAction.register(MAGE_OF_ZAMORAK, "teleport", ((player, npc) ->

                player.startEvent(event -> {
                            player.lock();
                            npc.lock();
                            npc.faceTemp(player);
                            npc.forceText("Veniens! Sallakar! Rinnesset!");
                            npc.animate(1818, 1);
                            npc.graphics(343, 100, 1);
                            event.delay(2);
                            player.unlock();
                            player.getCombat().skullNormal();
                            player.getPrayer().drain(1000); //1000 is just a safe "drain all"
                            Abyss.randomize(player);
                            player.getMovement().teleport(Random.get(Abyss.OUTER_TELEPORTS));
                            player.getDiaryManager().getWildernessDiary().progress(WildernessDiaryEntry.ABYSS_TELEPORT);
                            player.resetAnimation();
                            npc.unlock();
                        }
                )));
    }
}
