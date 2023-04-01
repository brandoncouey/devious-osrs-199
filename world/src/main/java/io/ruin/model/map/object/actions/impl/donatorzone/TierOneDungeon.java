package io.ruin.model.map.object.actions.impl.donatorzone;

import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.prayer.Prayer;

public class TierOneDungeon {

    private static final Bounds TierOneSpawns = new Bounds(1673, 4692, 1693, 4713, 0);

    static {
        ObjectAction.register(31622, 1, (player, obj) -> {
            if (player.getPrayer().isActive(Prayer.PROTECT_FROM_MELEE)) {
                enterDZInstance(player);
                return;
            }
            player.dialogue(new OptionsDialogue("Are you sure you're ready to enter(Protect Melee is Advised)",
                    new Option("Yes", () -> enterDZInstance(player)),
                    new Option("No!", player::closeDialogue)));
        });
        ObjectAction.register(27096, 1, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(3865);
                event.delay(3);
                player.getMovement().teleport(3810, 2844, 1);
                player.animate(-1);
                player.unlock();
            });
        });
        ObjectAction.register(27096, 2, (player, obj) -> {
            player.startEvent(event -> {
                player.lock();
                player.animate(3865);
                event.delay(3);
                player.getMovement().teleport(3810, 2844, 1);
                player.animate(-1);
                player.unlock();
            });
        });
    }

    public static void enterDZInstance(Player player) {
        if (player.amountDonated < 10) {
            player.dialogue(new NPCDialogue(2108, "Unfortunately your store amount is to low to enter this portal"));
            return;
        }

        player.startEvent(e -> {
            DynamicMap map = new DynamicMap().build(6729, 1);
            for (int i = 0; i < 20; i++) {
                NPC gwn = new NPC(5282).spawn(map.convertPosition(TierOneSpawns.randomPosition()));
                map.addNpc(gwn);
                gwn.setIgnoreMulti(true);
                gwn.getDef().ignoreOccupiedTiles = true;
                gwn.getCombat().setAllowRespawn(true);
                gwn.walkRange = 10;
                gwn.respawnListener = npc -> {
                    npc.setIgnoreMulti(true);
                    npc.getCombat().isAggressive();
                    gwn.getDef().ignoreOccupiedTiles = true;
                };
            }

            player.addEvent(event -> {
                player.lock();
                event.delay(2);
                player.getMovement().teleport(map.convertX(1683), map.convertY(4702), 0);
                player.DonatorSlayerDungeons = true;
                map.assignListener(player).onExit((p, logout) -> {
                    if (logout) {
                        p.getMovement().teleport(3086, 3499, 0);
                    }
                    map.destroy();
                    p.DonatorSlayerDungeons = false;
                });
                event.delay(1);
                player.unlock();
            });
        });
    }
}
