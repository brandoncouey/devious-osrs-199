package io.ruin.model.inter.battlepass;

import io.ruin.model.World;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.actions.SimpleAction;

public class BattlePassInterface {

    static {
        InterfaceHandler.register(804, h -> {
            int levelIndex = 1;
            for(int buttonId = 42; buttonId <= 98; buttonId += 8) {
                int finalLevelIndex = levelIndex;
                h.actions[buttonId] = (DefaultAction) (player, option, slot, itemId) -> {
                    player.getBattlePass().claim(player, finalLevelIndex);
                };
                levelIndex++;
            }

            h.actions[106] = (DefaultAction) (player, option, slot, itemId) ->
                    player.getBattlePass().previousPage(player)
            ;

            h.actions[108] = (DefaultAction) (player, option, slot, itemId) ->
                    player.getBattlePass().nextPage(player)
            ;
            h.actions[32] = (SimpleAction) p -> p.openUrl(World.type.getWorldName() + " Store", World.type.getWebsiteUrl() + "store");

        });
    }
}

