package io.ruin.model.entity.npc.actions;

import io.ruin.cache.Color;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCAction;

public class PetDog {

    static {
        NPCAction.register(111, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(2902, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(10760, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(1760, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(2922, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(2922, "shoo-away", ((player, npc) -> {
            player.sendMessage(Color.RED, "Don't be so god damn mean!");
            npc.forceText("WOOF!");
            player.sendMessage(Color.RED, "The dog bites you!");
            player.hit(new Hit().randDamage(1, 10));
            player.forceText("I'm sorry!");
        }));

        NPCAction.register(2902, "shoo-away", ((player, npc) -> {
            player.sendMessage(Color.RED, "Don't be so god damn mean!");
            npc.forceText("WOOF!");
            player.sendMessage(Color.RED, "The dog bites you!");
            player.hit(new Hit().randDamage(1, 10));
            player.forceText("I'm sorry!");
        }));

        NPCAction.register(8041, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });

        NPCAction.register(7771, "pet", (player, npc) -> {
            player.animate(827);
            player.sendMessage("She barks in appreciation!");
            player.forceText("Who's a good doggy!");
            npc.forceText("Woof!");
        });


    }
}
