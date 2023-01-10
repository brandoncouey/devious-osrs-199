package io.ruin.model.activities.bosses.nightmare;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NightmareEvent {

    private static final Map<String, NightmareEvent> INSTANCES = new HashMap<String, NightmareEvent>();

    private static DynamicMap map;

    private static ArrayList<Player> players;

    private boolean inited = false;

    private static Nightmare nightmare;

    private final Position base;

    private NightmareEvent(ArrayList<Player> players) {
        NightmareEvent.players = players;
        map = new DynamicMap().build(15515, 3);
        base = new Position(map.convertX(3840), map.convertY(9936), 3);
        nightmare = new Nightmare(9430, base);
        nightmare.transform(9430);
        nightmare.setTotems(new TotemPlugin[]{new TotemPlugin(9434, base.translated(23, 6, 0)), new TotemPlugin(9437, base.translated(39, 6, 0)), new TotemPlugin(9440, base.translated(23, 22, 0)), new TotemPlugin(9443, base.translated(39, 22, 0))});
        for (TotemPlugin t : nightmare.getTotems()) {
            t.setNightmare(nightmare);
        }
        nightmare.spawn(base.translated(30, 13, 0));
        nightmare.addEvent(e -> {
            if (inited) {
                return;
            }
            nightmare.getMasks()[0].reset();
            nightmare.toggleShield();
            e.delay(1);
            for (Player p : NightmareEvent.players) {
                p.inMulti();
                p.getMovement().teleport(base.translated(32, 12));
            }
            nightmare.transform(9430);
            //nightmare.animate(8611);
            for (Player p : NightmareEvent.players) {
                p.sendMessage("<col=ff0000>The Nightmare has awoken!");
            }
            inited = true;
            nightmare.setStage(0);
            e.delay(8);
            nightmare.transform(9425);
            nightmare.animate(-1);
        });
    }

    public static NightmareEvent createInstance(ArrayList<Player> players) {
        NightmareEvent nme = new NightmareEvent(players);
        INSTANCES.put(players.get(0).getName().toLowerCase(), nme);
        return nme;
    }


    public static NightmareEvent joinInstance(String key, Player player) {
        NightmareEvent nme = INSTANCES.get(key.toLowerCase());
        if (nme == null) {
            player.sendMessage(key + " has no registered instances.");
        } else {
            nme.add(player);
        }
        return nme;
    }

    public static void hmSize() {
    }

    public static void hmClear() {
        INSTANCES.clear();
    }

    public static void leaveInstance(Player player) {
        if (players.size() <= 1) {
            map.destroy();
        }
        players.remove(player);
        INSTANCES.remove(player.getName());

    }

    private void add(Player player) {
        players.add(player);
        player.getMovement().teleport(base.translated(32, 12));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
