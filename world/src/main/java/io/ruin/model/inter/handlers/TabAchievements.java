package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;


public class TabAchievements {

    static {
        InterfaceHandler.register(Interface.ACHIEVEMENT, h -> {
            h.actions[2] = (DefaultAction) (player, childId, option, slot, itemId) -> handleSlots(player, slot);
        });
    }

    public static void handleSlots(Player player, int slot) {
        switch (slot) {
            case 1:
                player.getDiaryManager().getDeviousDiary().display("EASY");
                return;
            case 4:
                player.getDiaryManager().getMinigamesDiary().display("EASY");
                return;
            case 2:
                player.getDiaryManager().getPvmDiary().display("EASY");
                return;
            case 3:
                player.getDiaryManager().getPvpDiary().display("EASY");
                return;
            case 5:
                player.getDiaryManager().getSkillingDiary().display("EASY");
                return;
            case 0:
                player.getDiaryManager().getWildernessDiary().display("EASY");
        }
        player.currentAchievementViewing = slot;
    }
}
