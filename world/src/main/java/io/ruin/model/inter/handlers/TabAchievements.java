package io.ruin.model.inter.handlers;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.actions.DefaultAction;


public class TabAchievements {

    static {
        InterfaceHandler.register(Interface.ACHIEVEMENT, h -> {
            h.actions[2] = (DefaultAction) (player, option, slot, itemId) -> handleSlots(player, slot);
        });
    }

    public static void handleSlots(Player player, int slot) {
        switch (slot) {
            case 0:
                player.getDiaryManager().getKaramjaDiary().display();
                return;
            case 1:
                player.getDiaryManager().getArdougneDiary().display();
                return;
            case 2:
                player.getDiaryManager().getFaladorDiary().display();
                return;
            case 3:
                player.getDiaryManager().getFremennikDiary().display();
                return;
            case 4:
                player.getDiaryManager().getKandarinDiary().display();
                return;
            case 5:
                player.getDiaryManager().getDesertDiary().display();
                return;
            case 6:
                player.getDiaryManager().getLumbridgeDraynorDiary().display();
                return;
            case 7:
                player.getDiaryManager().getMorytaniaDiary().display();
                return;
            case 8:
                player.getDiaryManager().getVarrockDiary().display();
                return;
            case 9:
                player.getDiaryManager().getWildernessDiary().display();
                return;
            case 10:
                player.getDiaryManager().getWesternDiary().display();
                return;
            case 11:
                player.getDiaryManager().getKourendDiary().display();
        }
    }
}
