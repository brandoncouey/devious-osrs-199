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

public class TierTwoDungeon {

    private static final Bounds TierTwoSpawns = new Bounds(1673, 4692, 1677, 4695, 0);
    private static final Bounds TierTwoSpawns1 = new Bounds(1673, 4710, 1677, 4713, 0);
    private static final Bounds TierTwoSpawns2 = new Bounds(1689, 4692, 1693, 4695, 0);
    private static final Bounds TierTwoSpawns3 = new Bounds(1692, 4712, 1688, 4709, 0);

    static {
        ObjectAction.register(31621, 1, (player, obj) -> {
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
        if (player.storeAmountSpent < 100) {
            player.dialogue(new NPCDialogue(2108, "Unfortunately your store amount is to low to enter this portal"));
            return;
        }

        player.startEvent(e -> {
            DynamicMap map = new DynamicMap().build(6729, 1);
            for (int i = 0; i < 8; i++) {
                NPC gwn = new NPC(2244).spawn(map.convertPosition(TierTwoSpawns.randomPosition()));
                NPC gwn1 = new NPC(7241).spawn(map.convertPosition(TierTwoSpawns1.randomPosition()));
                NPC gwn2 = new NPC(1047).spawn(map.convertPosition(TierTwoSpawns2.randomPosition()));
                NPC gwn3 = new NPC(412).spawn(map.convertPosition(TierTwoSpawns3.randomPosition()));
                map.addNpc(gwn);
                map.addNpc(gwn1);
                map.addNpc(gwn2);
                map.addNpc(gwn3);
                gwn.setIgnoreMulti(true);
                gwn.getDef().ignoreOccupiedTiles = true;
                gwn.getCombat().setAllowRespawn(true);
                gwn1.setIgnoreMulti(true);
                gwn1.getDef().ignoreOccupiedTiles = true;
                gwn1.getCombat().setAllowRespawn(true);
                gwn2.setIgnoreMulti(true);
                gwn2.getDef().ignoreOccupiedTiles = true;
                gwn2.getCombat().setAllowRespawn(true);
                gwn3.setIgnoreMulti(true);
                gwn3.getDef().ignoreOccupiedTiles = true;
                gwn3.getCombat().setAllowRespawn(true);
                gwn.respawnListener = npc -> {
                    npc.setIgnoreMulti(true);
                    npc.getCombat().isAggressive();
                    npc.getDef().ignoreOccupiedTiles = true;
                };
                gwn1.respawnListener = npc -> {
                    npc.setIgnoreMulti(true);
                    npc.getCombat().isAggressive();
                    npc.getDef().ignoreOccupiedTiles = true;
                };
                gwn2.respawnListener = npc -> {
                    npc.setIgnoreMulti(true);
                    npc.getCombat().isAggressive();
                    npc.getDef().ignoreOccupiedTiles = true;
                };
                gwn3.respawnListener = npc -> {
                    npc.setIgnoreMulti(true);
                    npc.getCombat().isAggressive();
                    npc.getDef().ignoreOccupiedTiles = true;
                };
            }

            player.addEvent(event -> {
                player.lock();
                event.delay(2);
                player.DonatorSlayerDungeons = true;
                player.getMovement().teleport(map.convertX(1683), map.convertY(4702), 0);
                map.assignListener(player).onExit((p, logout) -> {
                    if (logout) {
                        p.getMovement().teleport(3086, 3499, 0);
                    }
                    p.DonatorSlayerDungeons = false;
                    map.destroy();
                });
                event.delay(1);
                player.unlock();
            });
        });
    }
}
