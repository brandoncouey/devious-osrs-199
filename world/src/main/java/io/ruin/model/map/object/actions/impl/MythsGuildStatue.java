package io.ruin.model.map.object.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemObjectAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.runecrafting.Altars;
import io.ruin.model.skills.runecrafting.Essence;
import io.ruin.model.stat.StatType;

import java.util.ArrayList;
import java.util.Map;

public class MythsGuildStatue {

    static {
        ObjectAction.register(31626, 1, (c, obj) -> enter(c, 1937, 9009));
        ObjectAction.register(32205, 1, (player, obj) -> climb(player, 2457, 2849, 0));
        ObjectAction.register(31627, 1, (player, obj) -> climb(player, (Random.get(1, 2) > 1 ? 2456 : 2458), 2839, 1));
        ObjectAction.register(32206, 1, (player, obj) -> climb(player, 2457, 2839, 0));
        ObjectAction.register(31609, 1, (player, obj) -> climb(player, 2452, 2847, 2));
        ObjectAction.register(31610, 1, (player, obj) -> climb(player, 2449, 2847, 1));

        ItemObjectAction.register(7936, 34746, (player, item, obj) -> {
            player.startEvent(event -> {
                int essenceCount = 0, fromPouches = 0;
                ArrayList<Item> essences;
                essences = player.getInventory().collectItems(Essence.PURE.id);
                int essenceFromPouches = player.runeEssencePouches.entrySet().stream()
                        .filter(e -> player.getInventory().contains(e.getKey().getItemId(), 1))
                        .mapToInt(Map.Entry::getValue)
                        .sum();
                essenceCount += (fromPouches = essenceFromPouches);
                if (essences != null) {
                    for (Item ess : essences)
                        ess.remove();
                    essenceCount += essences.size();
                }
                if (fromPouches > 0) {
                    player.runeEssencePouches.entrySet().stream()
                            .filter(e -> player.getInventory().contains(e.getKey().getItemId(), 1))
                            .forEach(e -> e.setValue(0));
                }
                if (essenceCount == 0) {
                    player.dialogue(new MessageDialogue("You do not have any pure essence to bind."));
                    return;
                }
                int runesPerEssence = 1;

                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.animate(791);
                player.graphics(186, 100, 0);
                event.delay(4);
                int amount = essenceCount * runesPerEssence;
                player.getInventory().add(Altars.WRATH.runeID, amount);
                player.getStats().addXp(StatType.Runecrafting, essenceCount * Altars.WRATH.experience, true);
                PlayerCounter.CRAFTED_WRATH.increment(player, amount);
                player.unlock();
            });
        });
    }


    private static void climb(Player player, int x, int y, int z) {
        player.resetActions(true, true, true);
        player.lock(); //keep lock outside of event!
        player.startEvent(e -> {
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(new Position(x, y, z));
            player.getPacketSender().clearFade();

            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    private static void enter(Player player, int x, int y) {
        player.startEvent(event -> {
            player.lock();
            player.getMovement().teleport(x, y, 1);
            player.unlock();
        });
    }

}