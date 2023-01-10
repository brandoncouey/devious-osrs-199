package io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.NPCDialogue;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.map.object.actions.ObjectAction;

public class TheGauntlet {
    public static DynamicMap map = new DynamicMap();
    //public static final Bounds GAUNTLET_BOUNDS = new Bounds(new Position(1975, 5682, 1), 13);
    public static final Bounds GAUNTLET_BOUNDS = new Bounds(1903, 5665, 1919, 5694, -1);

    static {
        ObjectAction.register(36081, "enter", (player, obj) -> toGauntlet(player));
        ObjectAction.register(36062, "exit", (player, obj) -> leaveGauntlet(player));
        ObjectAction.register(37340, 1, (player, obj) -> enterGauntlet(player));
    }

    public static void toGauntlet(Player player) {
        player.lock();
//        player.getPacketSender().priffadeOut();
        player.dialogue(
                new MessageDialogue("<col=880000>Welcome to The Gauntlet.")
        );
        player.getMovement().teleport(3036, 6124, 1);
        //        player.getPacketSender().priffadeIn();
        player.unlock();
    }

    public static void leaveGauntlet(Player player) {
        player.lock();
//        player.getPacketSender().priffadeOut();
        player.dialogue(
                new MessageDialogue("Welcome to the Almodd District")
        );
        map.destroy();
        player.getMovement().teleport(3228, 6116, 0);
        //        player.getPacketSender().priffadeIn();
        player.unlock();
    }

    public static void enterGauntlet(Player player) {
        /*if (player.getInventory().isNotEmpty() || player.getEquipment().isNotEmpty()) {
            player.dialogue(
                    new MessageDialogue("You can't bring your own items into The Gauntlet.")
            );
            return;
        }*/

        player.dialogue(new NPCDialogue(2108, "Unfortunately our Gauntlet isn't ready just yet, check discord for updates!"));

/*        player.startEvent(event -> {
            player.lock();
    //        player.getPacketSender().priffadeOut();
            player.dialogue(
                    new MessageDialogue("Welcome to the Gauntlet.")
            );
//            player.getPacketSender().priffadeIn();

            map.build(GAUNTLET_BOUNDS);
            NPC gwn = new NPC(9021).spawn(map.swRegion.baseX + 15, map.swRegion.baseY + 20, 1, Direction.SOUTH, 0);
            map.addNpc(gwn);
            player.getMovement().teleport(map.swRegion.baseX + 18, map.swRegion.baseY + 8, 1);
            map.assignListener(player).onExit((p, logout) -> {
                if (logout)
                    p.getMovement().teleport(2210, 3057, 0);
                p.deathEndListener = null;
            });
            player.unlock();
        });*/
    }
}