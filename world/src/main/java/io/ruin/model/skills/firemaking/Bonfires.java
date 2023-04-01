package io.ruin.model.skills.firemaking;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.stat.StatType;

public class Bonfires {

    static {
        ObjectAction.register(26185, "Add-log", (player, obj) -> {
            Bonfires.addLog(player);
        });
    }

    public static void addLog(Player player) {
        int logs = 0;
        for (Item item : player.getInventory().getItems()) {
            if (item != null && item.getDef().name.toLowerCase().contains("logs"))
                logs++;

        }
        if (logs == 0) {
            player.sendMessage("You do not have any more logs to burn.");
            return;
        }
       burn(player, logs);
    }

    public static void burn(Player player, int logs) {
        player.startEvent(e -> {
            int amount = logs;
            while (amount-- > 0) {
                Item log = null;
                for (Item item : player.getInventory().getItems()) {
                    if (item != null && item.getDef().name.toLowerCase().contains("logs")) {
                        log = item;
                        break;
                    }
                }
                if (log == null) {
                    player.sendMessage("You don't have anymore logs to burn.");
                    return;
                }
                Burning burning = Burning.get(log.getId());
                if (burning != null) {
                    player.sendFilteredMessage("You add a log to the fire.");
                    player.getStats().addXp(StatType.Firemaking, ((burning.exp * Burning.pyromancerBonus(player)) / 2.5), true);
                    burning.counter.increment(player, 1);
                    player.getInventory().remove(log);
                    e.delay(8);
                }
            }
        });
    }
}
