package io.ruin.model.entity.player.SeasonPass;

import com.google.gson.annotations.Expose;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.battlepass.Level;

import java.util.ArrayList;
import java.util.List;

public class SeasonPass {
    @Expose
    public int version = 1;
    @Expose
    private int maxLevel; // Max level in this battlepass period, i think we should do around 120 levels
    @Expose
    private final int startLevel = 0; // Start level in this battlepass period ( 0 )
    @Expose
    private int currentLevel; // Current level for saving players level
    @Expose
    private final int currentxp;
    @Expose
    private boolean unlocked;
    @Expose
    private List<Boolean> takenRewards;


    private final String endsIn;
    private int currentPage;
    private final List<Level> levels;

    public SeasonPass() {
        currentLevel = 0;
        currentxp = 0;
        endsIn = "";
        currentPage = 1;
        levels = new ArrayList<>();
        initLevels();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getXP() {
        return currentxp;
    }

    private void initLevels() {
        levels.add(new Level(1, 995, 2500000, 0));
        levels.add(new Level(2, 4151, 1, 0));
        levels.add(new Level(3, 6585, 1, 0));
        levels.add(new Level(4, 6199, 1, 0));
        levels.add(new Level(5, 6920, 1, 0));
        levels.add(new Level(6, 12006, 1, 0));
        levels.add(new Level(7, 537, 250, 0));
        levels.add(new Level(8, 6739, 1, 0));
        levels.add(new Level(9, 6585, 1, 0));
        levels.add(new Level(10, 6585, 1, 0));
        levels.add(new Level(11, 6585, 1, 0));
        levels.add(new Level(12, 6585, 1, 0));
        levels.add(new Level(13, 6585, 1, 0));
        levels.add(new Level(14, 6585, 1, 0));
        levels.add(new Level(15, 6585, 1, 0));
        levels.add(new Level(16, 6585, 1, 0));
        levels.add(new Level(17, 6585, 1, 0));
        levels.add(new Level(18, 6585, 1, 0));
        levels.add(new Level(19, 6585, 1, 0));
        levels.add(new Level(20, 6585, 1, 0));
        levels.add(new Level(21, 6585, 1, 0));
        levels.add(new Level(22, 6585, 1, 0));
        levels.add(new Level(23, 6585, 1, 0));
        levels.add(new Level(24, 6585, 1, 0));
        levels.add(new Level(25, 6585, 1, 0));
        levels.add(new Level(26, 6585, 1, 0));
        levels.add(new Level(27, 6585, 1, 0));
        levels.add(new Level(28, 6585, 1, 0));
        levels.add(new Level(29, 6585, 1, 0));
        levels.add(new Level(30, 6585, 1, 0));
        levels.add(new Level(31, 6585, 1, 0));
        levels.add(new Level(32, 6585, 1, 0));
        levels.add(new Level(33, 6585, 1, 0));
        levels.add(new Level(34, 6585, 1, 0));
        levels.add(new Level(35, 6585, 1, 0));
        levels.add(new Level(36, 6585, 1, 0));
        levels.add(new Level(37, 6585, 1, 0));
        levels.add(new Level(38, 6585, 1, 0));
        levels.add(new Level(39, 6585, 1, 0));
        levels.add(new Level(40, 6585, 1, 0));
        levels.add(new Level(41, 6585, 1, 0));
        levels.add(new Level(42, 6585, 1, 0));
        levels.add(new Level(43, 6585, 1, 0));
        levels.add(new Level(44, 6585, 1, 0));
        levels.add(new Level(45, 6585, 1, 0));
        levels.add(new Level(46, 6585, 1, 0));
        levels.add(new Level(47, 6585, 1, 0));
        levels.add(new Level(48, 6585, 1, 0));
        levels.add(new Level(49, 6585, 1, 0));
        levels.add(new Level(50, 6585, 1, 0));
        levels.add(new Level(51, 6585, 1, 0));
        levels.add(new Level(52, 6585, 1, 0));
        levels.add(new Level(53, 6585, 1, 0));
        levels.add(new Level(54, 6585, 1, 0));
        levels.add(new Level(55, 6585, 1, 0));
        levels.add(new Level(56, 6585, 1, 0));
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(int level) {
        return levels.get(level);
    }

    public void claim(Player player, int level) {
        int actualLevel = level + ((currentPage - 1) * 8);
        boolean claimed = false;
        Level clickedLevel = levels.stream().filter(l -> l.getRewardLevel() == actualLevel)
                .findFirst().orElse(null);
        if (clickedLevel == null) {
            System.err.println("Level " + level + " causes a null pointer!");
            return;
        }
        if (clickedLevel.getRewardState() == 1) {
            clickedLevel.setRewardState(2);
            levels.set(actualLevel - 1, clickedLevel);
            claimed = true;
        }
        if (claimed) {
            //Do your checks for inv
            player.getInventory().addOrDrop(clickedLevel.getRewardItemId(), clickedLevel.getRewardItemAmount());
            player.sendMessage("You successfully claimed your battle pass (lvl " + actualLevel + ") reward. ");
        }
        updateInterface(player);
    }

    public void unlock(Player player, int level) {
        Level clickedLevel = levels.stream().filter(l -> l.getRewardLevel() == level).findFirst().orElse(null);
        if (clickedLevel == null) {
            System.err.println("Level " + level + " causes a null pointer!");
            return;
        }
        if (clickedLevel.getRewardState() == 0) {
            clickedLevel.setRewardState(1);
            levels.set(level - 1, clickedLevel);
        }
        updateInterface(player);
    }

    public void levelUp(Player player) {
        currentLevel++;
        unlock(player, currentLevel);
    }

    public void updateInterface(Player player) {
        String type = "iii";
        int[] params = new int[24];
        int paramsIndex = 0;
        if (levels == null) {
            initLevels();
        }
        for (int index = 0; index < levels.size(); index++) {
            if (index < (currentPage * 8) && index >= (currentPage * 8) - 8) {
                Level currentLvl = levels.get(index);
                type += "iii";
                params[paramsIndex] = currentLvl.getRewardItemId();
                params[paramsIndex + 1] = currentLvl.getRewardItemAmount();
                params[paramsIndex + 2] = currentLvl.getRewardState();
                paramsIndex += 3;
            }
        }

        type += "s";


        player.getPacketSender().sendClientScript(
                10189, type, currentLevel, currentxp, currentPage,
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

    public void previousPage(Player player) {
        if (currentPage == 1) {
            currentPage = 7;
        } else {
            currentPage--;
        }
        updateInterface(player);
    }
}
/*

    */
/**
     *
     * Tasks!
     *//*


    @Expose public SeasonPassTasks tasks;

    public SeasonPass(Player player) {

        maxLevel = SeasonPassParameters.maxLevel;
        currentLevel = 0;
        currentxp = 0;
        takenRewards = new ArrayList<>();
        for(int i = 0; i < maxLevel ; i++){
            // rewards.add() add one reward per level.
            takenRewards.add(false);
        }
        tasks = new SeasonPassTasks(player);
    }
    public void update(Player player){
        if(tasks == null){
            tasks = new SeasonPassTasks(player);
        }
        tasks.update(player);
    }

    private int expNextLevel(){
        int exp = 0;
        for(int i = 0; i < currentLevel; i++){
            exp += 20 * Math.pow(2, i/10);
        }
        return exp;
    }

    private int expLevel(int level){
        return (int) (Math.pow(level,2) - level + 50); //Find a nice function
    }

    public void sendLevelMessage(Player plr){
        plr.sendMessage(Color.COOL_BLUE.tag() + "Congratulations on achieving RiftPass level " + currentLevel + " out of " + maxLevel + "!");
    }

    public void giveReward(int level, Player player){
        if(rewardAvailible(level)){
            int nrOf = getNrOfRewardItems(level);
            player.sendMessage("Reward available: " + nrOf);
            if(nrOf > 0) {
                Item[] reward = getRewards(level);
                for (int i = 0; i < nrOf; i++) {
                    player.sendMessage("Reward added: " + reward[i].getId());
                    player.getBank().add(reward[i].getId(), reward[i].getAmount());
                    player.sendMessage(Color.COOL_BLUE.tag() + "You have claimed the RiftPass reward " + reward[i].getDef().name + " for reaching level " + level+"!");

                }
                takenRewards.set(level, true);
            }
        }else{
            // Reward not achived yet, why called?? :)
        }
    }

    public boolean rewardAvailible(int level){
        return !takenRewards.get(level) && currentLevel >= level;
    }

    public boolean takenReward(int level){
        return takenRewards.get(level);
    }

    int getNrOfRewardItems(int level){
        int nrOf = 0;
        for(int i = 0; i < SeasonRewards.rewards.length;i++){
            if(SeasonRewards.rewards[i].level == level){
                nrOf++;
            }
        }
        return nrOf;
    }

    public Item[] getRewards(int level){
        Item[] rewardItems = new Item[getNrOfRewardItems(level)];
        int nr = 0;
        for(int i = 0; i < SeasonRewards.rewards.length;i++){
            if(SeasonRewards.rewards[i].level == level){
                rewardItems[nr++] = new Item(SeasonRewards.rewards[i].id,SeasonRewards.rewards[i].amount);
            }
        }
        return rewardItems;
    }

    public void AddExp(int exp,Player player){
            double donatorBonus = 1;
            if(player.isZenyte()){
                donatorBonus += 0.3;
            }else if(player.isOnyx()){
                donatorBonus += 0.25;
            }else if(player.isDragonStone()){
                donatorBonus += 0.2;
            }else if(player.isDiamond()){
                donatorBonus += 0.1;
            }
            currentxp += exp * donatorBonus;
            while(currentxp >= expNextLevel() && currentLevel < 120){
                World.sendGraphics(1388, 50, 0, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
                levelUp(player);
                sendLevelMessage(player);
            }
    }
    public void SetLevel(int level){
        currentLevel = level;
        for(int i = 0; i < currentLevel; i++){
            currentxp += expLevel(level);
        }
    }

    public void GiveExpNPC(NPC npc, Player player){
        int id = npc.getId();
        switch(id){
            case 1:
                break;
            default:
                break;
        }
    }

    public void fix(Player player) {
        // ill do a quick fix for now. You can do the rest yourself I guess.
        currentLevel = 0;
        currentxp = 0;
        levels.forEach(l -> l.setRewardState(0));
        updateInterface(player);
    }
}
*/


