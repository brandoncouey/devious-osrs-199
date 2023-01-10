package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;

public class RedwoodTreeCrop implements TreeCrop {

    public static final RedwoodTreeCrop INSTANCE = new RedwoodTreeCrop();

    private RedwoodTreeCrop() {

    }

    @Override
    public int getSeed() {
        return 22871;
    }

    @Override
    public int getLevelReq() {
        return 90;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(5);
    }

    @Override
    public long getStageTimeMems() {
        return 1;
    }

    @Override
    public int getTotalStages() {
        return 11;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 2:
                return 0.2 / getTotalStages();
            case 1:
                return 0.3 / getTotalStages();
            case 0:
            default:
                return 0.4 / getTotalStages();
        }
    }

    @Override
    public double getPlantXP() {
        return 230;
    }

    @Override
    public int getContainerIndex() {
        return 8;
    }

    @Override
    public int getProduceId() {
        return Tree.REDWOOD.log;
    }

    @Override
    public double getHarvestXP() {
        return 22450;
    }

    @Override
    public PlayerCounter getCounter() {
        return null;
    }

    @Override
    public Item getPayment() {
        return TreeCrop.super.getPayment();
    }

    @Override
    public int getSapling() {
        return 22859;
    }

    @Override
    public int getSeedling() {
        return 22850;
    }

    @Override
    public int getWateredSeedling() {
        return 22854;
    }
}
