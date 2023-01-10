package io.ruin.model.inter.battlepass;

import com.google.gson.annotations.Expose;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.SeasonPass.SeasonPassParameters;
import io.ruin.model.entity.player.SeasonPass.SeasonPassTasks;
import io.ruin.model.entity.player.SeasonPass.SeasonRewards;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;

import java.util.ArrayList;
import java.util.List;

public class BattlePass {

    static int levelupExp = 5000;

    @Expose
    public int version = 0;
    @Expose
    private static int maxLevel;
    @Expose
    private final int startLevel = 0;
    @Expose
    private static int currentLevel;
    @Expose
    private static int currentxp;
    @Expose
    private boolean unlocked;

    private static String endsIn;
    private static int currentPage;
    @Expose
    public boolean hasPaidFor;

    public BattlePass() {
        hasPaidFor = false;
        currentLevel = 0;
        currentxp = 0;
        endsIn = ("Days Remaining: " + (BattlePassManager.daysleft));
        currentPage = 1;
    }

    public void purchaseBattlePass() {
        hasPaidFor = true;
    }

    public boolean ownsBattlePass() {
        return hasPaidFor;
    }

    public static int getCurrentLevel(Player player) {
        return player.currentLevel;
    }

    public int getXP(Player player) {
        return player.currentxp;
    }

    public static void initLevels(Player player) {
        player.levels.add(new Level(1, 537, 25, 0));                // Dragon bones
        player.levels.add(new Level(2, 224, 100, 0));            // Red Spider Eggs
        player.levels.add(new Level(3, 995, 1000000, 0));        // Coins
        player.levels.add(new Level(4, 11944, 50, 0));            // Lava Dragon bones
        player.levels.add(new Level(5, 13442, 100, 0));            // Angler Fish
        player.levels.add(new Level(6, 12696, 15, 0));            // Super Combat Potion
        player.levels.add(new Level(7, 9342, 250, 0));            // Onyx Bolts
        player.levels.add(new Level(8, 290, 1, 0));                // Luxury Box
        player.levels.add(new Level(9, 30307, 1, 0));            // $5 bond
        player.levels.add(new Level(10, 20360, 5, 0));            // Medium Clues
        player.levels.add(new Level(11, 4151, 1, 0));            // Medium Clues
        player.levels.add(new Level(12, 560, 3000, 0));            // Death rune
        player.levels.add(new Level(13, 995, 1000000, 0));        // Coins
        player.levels.add(new Level(14, 565, 3000, 0));            // Blood rune
        player.levels.add(new Level(15, 555, 20000, 0));            // Water rune
        player.levels.add(new Level(16, 30306, 1, 0));            // Seasonal Box
        player.levels.add(new Level(17, 30256, 1, 0));            // $10 bond
        player.levels.add(new Level(18, 30290, 5, 0));            // PvP Box
        player.levels.add(new Level(19, 995, 150000, 0));        // Coins
        player.levels.add(new Level(20, 30256, 5, 0));            // Vote Multipass
        player.levels.add(new Level(21, 22125, 50, 0));            // Superior Dragon bones
        player.levels.add(new Level(22, 452, 50, 0));            // Runite ore
        player.levels.add(new Level(23, 30256, 10, 0));            // Vote multipass
        player.levels.add(new Level(24, 7937, 1500, 0));            // Pure Essence
        player.levels.add(new Level(25, 290, 1, 0));                // Luxury Box
        player.levels.add(new Level(26, 11232, 1500, 0));        // Dragon dart tip
        player.levels.add(new Level(27, 30250, 1, 0));            // $10 bond
        player.levels.add(new Level(28, 20360, 10, 0));            // Medium clue
        player.levels.add(new Level(29, 11944, 100, 0));            // Lava dragon bone
        player.levels.add(new Level(30, 1632, 100, 0));            // Uncut DragonStone
        player.levels.add(new Level(31, 30306, 2, 0));            // Seasonal Box
        player.levels.add(new Level(32, 12945, 10, 0));            // Magic Sappling
        player.levels.add(new Level(33, 2364, 300, 0));            // Runite Bar
        player.levels.add(new Level(34, 30256, 3, 0));            // Vote multipass
        player.levels.add(new Level(35, 995, 2000000, 0));        // Coins
        player.levels.add(new Level(36, 30256, 1, 0));            // $10 bond
        player.levels.add(new Level(37, 22330, 25, 0));            // Pvm Box
        player.levels.add(new Level(38, 6829, 3, 0));            // Vote Box
        player.levels.add(new Level(39, 6199, 1, 0));            // Mystery Box
        player.levels.add(new Level(40, 22125, 50, 0));            // Superior dragon bone
        player.levels.add(new Level(41, 13440, 300, 0));            // Angler Fish
        player.levels.add(new Level(42, 30256, 6, 0));            // Vote multipass
        player.levels.add(new Level(43, 20360, 20, 0));            // Medium clue
        player.levels.add(new Level(44, 565, 5000, 0));            // Blood rune
        player.levels.add(new Level(45, 560, 5000, 0));            // Death rune
        player.levels.add(new Level(46, 555, 20000, 0));            // Water rune
        player.levels.add(new Level(47, 30307, 1, 0));            // $5 bond
        player.levels.add(new Level(48, 30256, 1, 0));            // $10 bond
        player.levels.add(new Level(49, 1514, 300, 0));            // Magic logs
        player.levels.add(new Level(50, 30306, 3, 0));            // Seasonal Box
        player.levels.add(new Level(51, 25527, 1000, 0));        // Stardust
        player.levels.add(new Level(52, 20360, 15, 0));            // Medium clue
        player.levels.add(new Level(53, 30256, 10, 0));            // Vote multipass
        player.levels.add(new Level(54, 11232, 1000, 0));        // Dragon dart tip
        player.levels.add(new Level(55, 995, 5000000, 0));        // Coins
        player.levels.add(new Level(56, 6828, 1, 0));
    }

    public List<Level> getLevels(Player player) {
        return player.levels;
    }

    public Level getLevel(Player player, int level) {
        return player.levels.get(level);
    }

    public void claim(Player player, int level) {
        if (!hasPaidFor) {
            return;
        }
        int actualLevel = level + ((currentPage - 1) * 8);
        boolean claimed = false;
        Level clickedLevel = player.levels.stream().filter(l -> l.getRewardLevel() == actualLevel)
                .findFirst().orElse(null);
        if (clickedLevel == null) {
            System.err.println("Level " + level + " causes a null pointer!");
            return;
        }
        if (clickedLevel.getRewardState() == 1) {
            clickedLevel.setRewardState(2);
            player.levels.set(actualLevel - 1, clickedLevel);
            claimed = true;
        }
        if (claimed) {
            player.getInventory().addOrDrop(clickedLevel.getRewardItemId(), clickedLevel.getRewardItemAmount());
            player.sendMessage("You successfully claimed your battle pass (lvl " + actualLevel + ") reward. ");
        }
        updateInterface(player);
    }

    public static void unlock(Player player, int level) {
        Level clickedLevel = player.levels.stream().filter(l -> l.getRewardLevel() == level).findFirst().orElse(null);
        if (clickedLevel == null) {
            System.err.println("Level " + level + " causes a null pointer!");
            return;
        }
        if (clickedLevel.getRewardState() == 0) {
            clickedLevel.setRewardState(1);
            player.levels.set(level - 1, clickedLevel);
        }
        updateInterface(player);
    }

    /**
     * Tasks!
     */

    @Expose
    public SeasonPassTasks tasks;

    public BattlePass(Player player) {
        player.levels.clear();
        player.claimedPass = true;
        maxLevel = SeasonPassParameters.maxLevel;
        version = SeasonPassParameters.version;
        player.seasonpassv = SeasonPassParameters.version;
        player.currentLevel = 0;
        player.currentxp = 0;
        player.takenRewards = new ArrayList<>();
        initLevels(player);
        for (int i = 0; i < maxLevel; i++) {
            player.takenRewards.add(false);
        }
        tasks = new SeasonPassTasks(player);
    }

    public void update(Player player) {
        if (tasks == null) {
            tasks = new SeasonPassTasks(player);
        }
        tasks.update(player);
    }

    public static int expNextLevel(Player player) {
        int exp = 0;
        for (int i = 0; i < getCurrentLevel(player) + 1; i++) {
            exp += 5000 * Math.pow(2, i / 10);
        }
        return exp;
    }

    public static void sendLevelMessage(Player plr) {
        plr.sendMessage(Color.COOL_BLUE.tag() + "Congratulations on achieving BattlePass level " + plr.currentLevel + " out of 56!");
    }

    public void giveReward(int level, Player player) {
        if (rewardAvailible(player, level)) {
            int nrOf = getNrOfRewardItems(level);
            player.sendMessage("Reward available: " + nrOf);
            if (nrOf > 0) {
                Item[] reward = getRewards(level);
                for (int i = 0; i < nrOf; i++) {
                    player.sendMessage("Reward added: " + reward[i].getId());
                    player.getBank().add(reward[i].getId(), reward[i].getAmount());
                    player.sendMessage(Color.COOL_BLUE.tag() + "You have claimed the DeviousPass reward " + reward[i].getDef().name + " for reaching level " + level + "!");
                }
                player.takenRewards.set(level, true);
            }
        }
    }

    public Item[] getRewards(int level) {
        Item[] rewardItems = new Item[getNrOfRewardItems(level)];
        int nr = 0;
        for (int i = 0; i < SeasonRewards.rewards.length; i++) {
            if (SeasonRewards.rewards[i].level == level) {
                rewardItems[nr++] = new Item(SeasonRewards.rewards[i].id, SeasonRewards.rewards[i].amount);
            }
        }
        return rewardItems;
    }

    public static void addExp(int exp, Player player) {
        if (player.getBattlePass().hasPaidFor && getCurrentLevel(player) < 56) {
            player.currentxp += exp;
            while (player.currentxp >= expNextLevel(player) && player.currentLevel < 56) {
                World.sendGraphics(1388, 50, 0, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
                levelUp(player);
                sendLevelMessage(player);
            }
        }
    }

    public void fix(Player player) {
        player.currentLevel = 0;
        player.currentxp = 0;
        player.levels.forEach(l -> l.setRewardState(0));
        updateInterface(player);
    }

    public static void levelUp(Player player) {
        player.currentLevel++;
        player.currentxp = 0;
        unlock(player, player.currentLevel);
        Config.varpbit(6347, false).set(player, getCurrentLevel(player));
    }

    public static void updateInterface(Player player) {
        String type = "iii";
        int[] params = new int[24];
        int paramsIndex = 0;
        if (player.levels == null) {
            initLevels(player);
        }
        for (int index = 0; index < player.levels.size(); index++) {
            if (index < (currentPage * 8) && index >= (currentPage * 8) - 8) {
                Level currentLvl = player.levels.get(index);
                type += "iii";
                params[paramsIndex] = currentLvl.getRewardItemId();
                params[paramsIndex + 1] = currentLvl.getRewardItemAmount();
                params[paramsIndex + 2] = currentLvl.getRewardState();
                paramsIndex += 3;
            }
        }

        type += "s";

        player.getPacketSender().sendClientScript(
                10189, type, player.currentLevel, player.currentxp, currentPage,
                params[0], params[1], params[2], params[3], params[4], params[5],
                params[6], params[7], params[8], params[9], params[10], params[11],
                params[12], params[13], params[14], params[15], params[16], params[17],
                params[18], params[19], params[20], params[21], params[22], params[23], endsIn
        );
    }

    public void open(Player player) {
        updateInterface(player);
        player.openInterface(InterfaceType.MAIN, 804);
    }

    public void nextPage(Player player) {
        if (currentPage == 7) {
            currentPage = 1;
        } else {
            currentPage++;
        }
        updateInterface(player);
    }

    public boolean rewardAvailible(Player player, int level) {
        return !player.takenRewards.get(level) && player.currentLevel >= level;
    }

    public boolean takenReward(Player player, int level) {
        return player.takenRewards.get(level);
    }

    public void previousPage(Player player) {
        if (currentPage == 1) {
            currentPage = 7;
        } else {
            currentPage--;
        }
        updateInterface(player);
    }

    static {
        ItemAction.registerInventory(757, 1, (player, item) -> {
            if (!player.getBattlePass().hasPaidFor) {
                player.getBattlePass().purchaseBattlePass();
                player.sendMessage(Color.COOL_BLUE, "You have just unlocked the Battlepass!, use the button in your quest tab to open the interface");
                item.remove();
            } else {
                player.sendMessage("You already own access to the current battle pass, save this for the next one!");
            }
        });
    }

    int getNrOfRewardItems(int level) {
        int nrOf = 0;
        for (int i = 0; i < SeasonRewards.rewards.length; i++) {
            if (SeasonRewards.rewards[i].level == level) {
                nrOf++;
            }
        }
        return nrOf;
    }
}