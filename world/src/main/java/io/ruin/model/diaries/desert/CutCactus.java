package io.ruin.model.diaries.desert;

import io.ruin.model.map.object.actions.ObjectAction;

public class CutCactus {

    static {
        ObjectAction.register(2670, 1, (player, obj) -> {
            if (player.getInventory().contains(1831)) {
                player.getInventory().remove(1831, 1);
                player.getInventory().add(1823, 1);
                player.sendMessage("You top up your skin with water from the cactus");
                player.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.CUT_CACTUS);
            }
        });
    }

}