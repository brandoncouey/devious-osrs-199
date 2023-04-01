package io.ruin.model.inter.combatachievements;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.inter.utils.Config;

/**
 * @author Greco
 */
public class CombatAchievements {

    /* combat achievements boss info interface */
    public static int BOSS_INFO_INTERFACE_ID = 713;

    /* combat achievement rewards interface */
    public static int REWARDS_INTERFACE_ID = 714;

    /* combat achievement tasks interface */
    public static int TASKS_INTERFACE_ID = 715;

    /* combat achievement bosses interface */
    public static int BOSSES_INTERFACE_ID = 716;

    /* combat achievement overview interface */
    public static int OVERVIEW_INTERFACE_ID = 717;

    /* open combat achievement tasks */
    public static void openBossInfo(Player player) {

        /* combat achievements boss info interface */
        player.openInterface(InterfaceType.MAIN, BOSS_INFO_INTERFACE_ID);

        getTasksCompleted(player);

        player.getPacketSender().sendAccessMask(BOSS_INFO_INTERFACE_ID, 22, 0, 22, 2);
        player.getPacketSender().sendAccessMask(BOSS_INFO_INTERFACE_ID, 27, 0, 16, 2);

        //12862 to change values
    }

    /* open combat achievement rewards */
    public static void openRewards(Player player) {

        /* combat achievements rewards interface */
        player.openInterface(InterfaceType.MAIN, REWARDS_INTERFACE_ID);

        getTasksCompleted(player);

        player.getPacketSender().sendAccessMask(REWARDS_INTERFACE_ID, 14, 10, 16, 2);
    }

    /* open combat achievement tasks */
    public static void openTasks(Player player) {

        /* combat achievements tasks interface */
        player.openInterface(InterfaceType.MAIN, TASKS_INTERFACE_ID);

        getTasksCompleted(player);

        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 9, 0, 410, 2);
        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 25, 10, 16, 2);
        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 31, 1, 8, 2);
        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 32, 1, 55, 2);
        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 30, 1, 8, 2);
        player.getPacketSender().sendAccessMask(TASKS_INTERFACE_ID, 33, 1, 4, 2);
    }

    /* open combat achievement bosses */
    public static void openBosses(Player player) {

        /* combat achievements bosses interface */
        player.openInterface(InterfaceType.MAIN, BOSSES_INTERFACE_ID);

        getTasksCompleted(player);

        player.getPacketSender().sendAccessMask(BOSSES_INTERFACE_ID, 23, 10, 16, 2);
    }

    /* open combat achievement overview */
    public static void openOverview(Player player) {

        /* combat achievements overview interface */
        player.openInterface(InterfaceType.MAIN, OVERVIEW_INTERFACE_ID);

        getTasksCompleted(player);

        player.getPacketSender().sendAccessMask(OVERVIEW_INTERFACE_ID, 16, 10, 16, 2);
    }

    public static void handleTaskFilterTier(Player player, int slot) {//child id 30 slots 1-7
        switch (slot) {
            case 1://all

                return;
            case 2://easy

                return;
            case 3://medium

                return;
            case 4://hard

                return;
            case 5://elite

                return;
            case 6://master

                return;
            case 7://grandmaster
                return;
        }
    }

    public static void handleTaskFilterType(Player player, int slot) {//child id 31 slot 1-7
        switch (slot) {
            case 1://all

                return;
            case 2://stamina

                return;
            case 3://perfection

                return;
            case 4://killcount

                return;
            case 5://mechanical

                return;
            case 6://restriction

                return;
            case 7://speed
                return;
        }
    }

    public static void handleTaskFilterCompleted(Player player, int slot) {//child id 33 slot 1-4
        switch (slot) {
            case 1://all

                return;
            case 2://incomplete

                return;
            case 3://complete

                return;
        }
    }

    public static void handleTaskFilterMonster(Player player, int slot) {//child 32 slot 1-50
        switch (slot) {
            case 1://all
                return;
            case 2://abyssal sire
                return;
            case 3://alchemical hydra
                return;
            case 4://barrows
                return;
            case 5://bryophyta
                return;
            case 6://callisto
                return;
            case 7://cerberus
                return;
            case 8://chaos elemental
                return;
            case 9://chaos fanatic
                return;
            case 10://crazy archeaologist
                return;
            case 11://CoX
                return;
            case 12://CoX:Challenge mode
                return;
            case 13://corp beast
                return;
            case 14://commander zilyana
                return;
            case 15://dagannoth prime
                return;
            case 16://dagannoth rex
                return;
            case 17://dagannoth supreme
                return;
            case 18://deranged archaeologist
                return;
            case 19://crystalline hunllef
                return;
            case 20://corrupted hunllef
                return;
            case 21://general graardor
                return;
            case 22://giant mole
                return;
            case 23://grotesque guardians
                return;
            case 24://hespori
                return;
            case 25://kalphite queen
                return;
            case 26://kdb
                return;
            case 27://kraken
                return;
            case 28://kree'arra
                return;
            case 29://k'ril tsutsaroth
                return;
            case 30://the mimic
                return;
            case 31://nightmare
                return;
            case 32://phosanis nightmare
                return;
            case 33://obor
                return;
            case 34://sarachnis
                return;
            case 35://scorpia
                return;
            case 36://skotizo
                return;
            case 37://tempoross
                return;
            case 38://ToB:Entry mode
                return;
            case 39://ToB
                return;
            case 40://ToB:Hard mode
                return;
            case 41://thermonuclear smoke devil
                return;
            case 42://tzhaar ket rak's challenges
                return;
            case 43://tzkal-zul
                return;
            case 44://tztok-jad
                return;
            case 45://venenatis
                return;
            case 46://vet'ion
                return;
            case 47://vorkath
                return;
            case 48://wintertodt
                return;
            case 49://zalcano
                return;
            case 50://zulrah
                return;
        }
    }

    /* handle combat achievement reward options */
    public static void handleRewardsSlots(Player player, int slot) {
        switch (slot) {
            case 10://overview
                openOverview(player);
                return;
            case 12://tasks
                openTasks(player);
                return;
            case 14://bosses
                openBosses(player);
                return;
            case 16://rewards
                openRewards(player);
                return;
        }
    }

    /* handle combat achievement overview slots */
    public static void handleOverviewSlots(Player player, int slot) {
        switch (slot) {
            case 10://overview
                openOverview(player);
                return;
            case 12://tasks
                openTasks(player);
                return;
            case 14://bosses
                openBosses(player);
                return;
            case 16://rewards
                openRewards(player);
                return;
        }
    }

    public static void handleOverviewSlots1(Player player, int slot) {
        switch (slot) {
            case 0://overview
                openOverview(player);
                return;
            case 1://tasks
                openTasks(player);
                return;
            case 2://bosses
                openBosses(player);
                return;
            case 3://rewards
                openRewards(player);
                return;
            case 4://rewards
                openRewards(player);
                return;
            case 5://rewards
                openRewards(player);
                return;
        }
    }

    /* handle combat achievement task slots */
    public static void handleTaskSlots(Player player, int slot) {
        switch (slot) {
            case 10://overview
                openOverview(player);
                return;
            case 12://tasks
                openTasks(player);
                return;
            case 14://bosses
                openBosses(player);
                return;
            case 16://rewards
                openRewards(player);
                return;
        }
    }

    public static void handleBossInfoSlots1(Player player, int slot) {
        switch (slot) {
            case 10://overview
                openOverview(player);
                return;
            case 12://tasks
                openTasks(player);
                return;
            case 14://bosses
                openBosses(player);
                return;
            case 16://rewards
                openRewards(player);
                return;
        }
    }

    public static void handleBossInfoSlots(Player player, int slot) {
        switch (slot) {

        }
    }

    public static void handleBossInfoSlots2(Player player, int slot) {
        switch (slot) {
            case 12:
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 0);
                openBosses(player);
                return;
        }
    }

    /* handle combat achievement bosses slots */
    public static void handleBossesSlots2(Player player, int slot) {
        switch (slot) {
            case 10://overview
                openOverview(player);
                return;
            case 12://tasks
                openTasks(player);
                return;
            case 14://bosses
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 0);
                openBosses(player);
                return;
            case 16://rewards
                openRewards(player);
                return;
        }
    }


    //TODO MAKE THESE INCREMENT WITH EACH TASK DONE IN EACH CATAGORY
    /* handle combat achievement boss options */
    public static void handleBossesSlots(Player player, int slot) {
        switch (slot) {
            case 1://abyssal sire
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 1);
                openBossInfo(player);
                //   Config.ABYSSAL_SIRE.set(player, 1);
                return;
            case 2://alchemical hydra
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 2);
                openBossInfo(player);
                //   Config.ALCHEMICAL_HYDRA.set(player, 1);
                return;
            case 3://barrows
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 3);
                openBossInfo(player);
                //  Config.BARROWS.set(player, 1);
                return;
            case 4://bryophyta
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 4);
                openBossInfo(player);
                //  Config.BRYOPHYTA.set(player, 1);
                return;
            case 5://callisto
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 5);
                openBossInfo(player);
                return;
            case 6://cerberus
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 6);
                openBossInfo(player);
                return;
            case 7://chaos elemental
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 7);
                openBossInfo(player);
                return;
            case 8://chaos fanatic
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 8);
                openBossInfo(player);
                return;
            case 9://crazy archeaologist
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 9);
                openBossInfo(player);
                return;
            case 10://CoX
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 10);
                openBossInfo(player);
                return;
            case 11://CoX:Challenge mode
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 11);
                openBossInfo(player);
                return;
            case 12://corp beast
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 12);
                openBossInfo(player);
                return;
            case 13://commander zilyana
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 13);
                openBossInfo(player);
                return;
            case 14://dagannoth prime
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 14);
                openBossInfo(player);
                return;
            case 15://dagannoth rex
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 15);
                openBossInfo(player);
                return;
            case 16://dagannoth supreme
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 16);
                openBossInfo(player);
                return;
            case 17://deranged archaeologist
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 17);
                openBossInfo(player);
                return;
            case 18://crystalline hunllef
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 18);
                openBossInfo(player);
                return;
            case 19://corrupted hunllef
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 19);
                openBossInfo(player);
                return;
            case 20://general graardor
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 20);
                openBossInfo(player);
                return;
            case 21://giant mole
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 21);
                openBossInfo(player);
                return;
            case 22://grotesque guardians
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 22);
                openBossInfo(player);
                return;
            case 23://hespori
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 23);
                openBossInfo(player);
                return;
            case 24://kalphite queen
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 24);
                openBossInfo(player);
                return;
            case 25://kdb
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 25);
                openBossInfo(player);
                return;
            case 26://kraken
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 26);
                openBossInfo(player);
                return;
            case 27://kree'arra
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 27);
                openBossInfo(player);
                return;
            case 28://k'ril tsutsaroth
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 28);
                openBossInfo(player);
                return;
            case 29://the mimic
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 29);
                openBossInfo(player);
                return;
            case 30://nightmare
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 30);
                openBossInfo(player);
                return;
            case 31://phosanis nightmare
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 31);
                openBossInfo(player);
                return;
            case 32://obor
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 32);
                openBossInfo(player);
                return;
            case 33://sarachnis
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 33);
                openBossInfo(player);
                return;
            case 34://scorpia
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 34);
                openBossInfo(player);
                return;
            case 35://skotizo
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 35);
                openBossInfo(player);
                return;
            case 36://tempoross
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 36);
                openBossInfo(player);
                return;
            case 37://ToB:Entry mode
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 37);
                openBossInfo(player);
                return;
            case 38://ToB
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 38);
                openBossInfo(player);
                return;
            case 39://ToB:Hard mode
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 39);
                openBossInfo(player);
                return;
            case 40://thermonuclear smoke devil
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 40);
                openBossInfo(player);
                return;
            case 41://tzhaar ket rak's challenges
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 41);
                openBossInfo(player);
                return;
            case 42://tzkal-zul
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 42);
                openBossInfo(player);
                return;
            case 43://tztok-jad
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 43);
                openBossInfo(player);
                return;
            case 44://venenatis
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 44);
                openBossInfo(player);
                return;
            case 45://vet'ion
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 45);
                openBossInfo(player);
                return;
            case 46://vorkath
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 46);
                openBossInfo(player);
                return;
            case 47://wintertodt
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 47);
                openBossInfo(player);
                return;
            case 48://zalcano
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 48);
                openBossInfo(player);
                return;
            case 49://zulrah
                Config.COMBAT_ACHIVEMENTS_REWARDS.set(player, 49);
                openBossInfo(player);
                return;
        }
    }

    /* set total tasks completed */
    public static void setTasksCompleted(Player player, int amount) {
        // Config.COMBAT_ACHIVEMENTS_TASKS_COMPLETED.set(player, amount);
    }

    /* get total tasks completed */
    public static void getTasksCompleted(Player player) {
        //Config.COMBAT_ACHIVEMENTS_TASKS_COMPLETED.get(player);
    }

    static {

        InterfaceHandler.register(REWARDS_INTERFACE_ID, h -> {

            /* handles the rewards button slots */
            h.actions[14] = (DefaultAction) (player, childId, option, slot, itemId) -> handleRewardsSlots(player, slot);

        });

        InterfaceHandler.register(TASKS_INTERFACE_ID, h -> {

            /* handles the tasks button slots */
            h.actions[25] = (DefaultAction) (player, childId, option, slot, itemId) -> handleTaskSlots(player, slot);

            h.actions[30] = (DefaultAction) (player, childId, option, slot, itemId) -> handleTaskFilterTier(player, slot);

            h.actions[31] = (DefaultAction) (player, childId, option, slot, itemId) -> handleTaskFilterType(player, slot);

            h.actions[32] = (DefaultAction) (player, childId, option, slot, itemId) -> handleTaskFilterMonster(player, slot);

            h.actions[33] = (DefaultAction) (player, childId, option, slot, itemId) -> handleTaskFilterCompleted(player, slot);

        });

        InterfaceHandler.register(BOSSES_INTERFACE_ID, h -> {

            /* handles the boss button slots */
            h.actions[15] = (DefaultAction) (player, childId, option, slot, itemId) -> handleBossesSlots(player, slot);

            /* handles the boss button slots */
            h.actions[23] = (DefaultAction) (player, childId, option, slot, itemId) -> handleBossesSlots2(player, slot);

        });

        InterfaceHandler.register(BOSS_INFO_INTERFACE_ID, h -> {

            /* handles the boss button slots */
            h.actions[27] = (DefaultAction) (player, childId, option, slot, itemId) -> handleBossInfoSlots1(player, slot);

            h.actions[22] = (DefaultAction) (player, childId, option, slot, itemId) -> handleBossInfoSlots2(player, slot);

        });

        InterfaceHandler.register(OVERVIEW_INTERFACE_ID, h -> {

            /* handles the boss button slots */
            h.actions[16] = (DefaultAction) (player, childId, option, slot, itemId) -> handleOverviewSlots(player, slot);
            h.actions[7] = (DefaultAction) (player, childId, option, slot, itemId) -> handleOverviewSlots1(player, slot);

        });
    }

}
