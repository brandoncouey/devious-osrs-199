package io.ruin.model.activities.tasks;

import io.ruin.model.activities.tasks.DailyTask.EasyTasks;
import io.ruin.model.activities.tasks.DailyTask.HardTasks;
import io.ruin.model.activities.tasks.DailyTask.MediumTasks;
import io.ruin.model.entity.player.Player;


public class DailyTaskKills {

    public static void kills(Player player, int npcId) {
        switch (npcId) {
            case 100:
            case 102:
                DailyTask.increase(player, EasyTasks.ROCK_CRABS);
                break;
                // 3185, 7259, 7260, 5943
            case 3185:
            case 7259:
            case 7260:
            case 5943:
            case 5942:
                DailyTask.increase(player, EasyTasks.DAGANNOTHS);
                break;
            case 7251:
            case 7252:
            case 2075:
            case 2076:
            case 2077:
            case 2078:
            case 2079:
            case 2080:
            case 2081:
            case 2082:
            case 2083:
            case 2084:
                DailyTask.increase(player, EasyTasks.FIRE_GIANTS);
                break;
            case 2098:
            case 2099:
            case 2100:
            case 2101:
            case 2102:
            case 2103:
            case 7261:
            case 12269:
                DailyTask.increase(player, EasyTasks.HILL_GIANTS);
                break;
                //3049, 3286, 3287, 3288, 3289, 4805
            case 3049:
            case 3286:
            case 3287:
            case 3288:
            case 3289:
            case 4805:
            case 3050:
            case 2241:
                DailyTask.increase(player, EasyTasks.HOBGOBLINS);
                break;
            case 241:
            case 242:
            case 243:
                DailyTask.increase(player, EasyTasks.BABY_BLUE_DRAGONS);
                break;
            case 891:
            case 2090:
            case 2091:
            case 2092:
            case 2093:
            case 3851:
            case 3852:
            case 7262:
            case 8736:
                DailyTask.increase(player, EasyTasks.MOSS_GIANTS);
                break;
                //2005, 2006, 2007, 2008, 2018, 3982, 7656, 7657, 7664
            //7247, 7865, 7866, 7867
            case 7248:
            case 2005:
            case 2008:
            case 2007:
            case 2018:
            case 3982:
            case 7656:
            case 7657:
            case 7664:
            case 7247:
            case 7865:
            case 7866:
            case 7867:
            case 2006:
                DailyTask.increase(player, EasyTasks.LESSER_DEMONS);
                break;
            case 6593:
                DailyTask.increaseMedium(player, MediumTasks.LAVA_DRAGONS);
                break;
            case 5779:
            case 6499:
                DailyTask.increaseMedium(player, MediumTasks.GIANT_MOLE);
                break;
            case 8031:
            case 8027:
            case 8091:
                DailyTask.increaseMedium(player, MediumTasks.RUNE_DRAGON);
                break;
            case 1047:
            case 1048:
            case 1049:
            case 1050:
            case 1051:
                DailyTask.increaseMedium(player, MediumTasks.CAVE_HORROR);
                break;
            case 239:
                DailyTask.increaseHard(player, HardTasks.KING_BLACK_DRAGON);
                //Achievements.Achievement.increase(player, AchievementType._13, 1);
                break;
            case 7881:
            case 7931:
            case 7932:
            case 7933:
            case 7934:
            case 7935:
            case 7936:
            case 7937:
            case 7938:
            case 7939:
            case 7940:
                //Achievements.Achievement.increase(player, AchievementType._18, 1);
                break;
            case 8061:
                //Achievements.Achievement.increase(player, AchievementType._22, 1);
                break;
            case 8615:
            case 8616:
            case 8617:
            case 8618:
            case 8619:
            case 8620:
                //Achievements.Achievement.increase(player, AchievementType._21, 1);
                break;
            case 6611:
            case 6612:
                DailyTask.increaseHard(player, HardTasks.VETION);
                break;
            case 5862:
            case 5863:
                //Achievements.Achievement.increase(player, AchievementType._30, 1);
                break;
            case 2042:
            case 2043:
            case 2044:
                //Achievements.Achievement.increase(player, AchievementType._29, 1);
                DailyTask.increaseHard(player, HardTasks.ZULRAH);
                break;
            case 7144:
            case 7145:
            case 7146:
            case 7147:
            case 7148:
            case 7149:
            case 7152:
                DailyTask.increaseHard(player, HardTasks.DEMONIC_GORILLAS);
                break;
            case 7286: //skot
                DailyTask.increaseHard(player, HardTasks.SKOTIZO);
                break;
            case 252:
            case 253:
            case 254:
            case 255:
            case 256:
            case 257:
            case 258:
            case 7275:
            case 7861:
            case 7862:
            case 7863:
            case 259: // black drag
                DailyTask.increaseMedium(player, MediumTasks.BLACK_DRAGONS);
                break;
            case 265:
            case 266:
            case 267:
            case 269:
            case 5878:
            case 5879:
            case 5880:
            case 5881:
            case 5882:
            case 268: //blue drag
                DailyTask.increaseMedium(player, MediumTasks.BLUE_DRAGONS);
                break;
            case 7410:
            case 416:
            case 7241:
            case 415: //abyssal demon
                DailyTask.increaseHard(player, HardTasks.ABYSSAL_DEMONS);
                break;
            case 4005: //dark beast
            case 7250:
                DailyTask.increaseHard(player, HardTasks.DARK_BESTS);
                break;
            case 2215: //bandos
                DailyTask.increaseHard(player, HardTasks.GENERAL_GRAARDOR);
                //Achievements.Achievement.increase(player, AchievementType._26, 1);
                break;
            case 3162: //arma
                DailyTask.increaseHard(player, HardTasks.KREE_ARRA);
                break;
            case 3129: //zamorak
                DailyTask.increaseHard(player, HardTasks.TSUTSAROTH);
                break;
            case 2205: //saradomin
                DailyTask.increaseHard(player, HardTasks.ZILYANA);
                break;
            case 2919: //mith dragon
            case 8088:
            case 8089:
                DailyTask.increaseMedium(player, MediumTasks.MITHRIL_DRAGONS);
                break;
        }
    }
}
