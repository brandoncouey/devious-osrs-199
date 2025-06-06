package io.ruin.model.skills.farming.crop.impl;

import io.ruin.api.utils.TimeUtils;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.skills.farming.crop.TreeCrop;
import io.ruin.model.skills.woodcutting.Tree;

public enum CelastrusCrop implements TreeCrop {
    CELASTRUS(22869, -1, 22856, 22848, 22852, Tree.CELASTRUS, 50, 500, 5555, 6, new Item(995, 2555555), 8, PlayerCounter.GROWN_CELASTRUS);

    CelastrusCrop(int seedId, int roots, int sapling, int seedling, int wateredSeedling, Tree treeType, int levelReq, double plantXP, double harvestXP, int totalStages, Item payment, int containerIndex, PlayerCounter counter) {
        this.plantXP = plantXP;
        this.checkHealthXP = harvestXP;
        this.seedId = seedId;
        this.treeType = treeType;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.totalStages = totalStages;
        this.roots = roots;
        this.counter = counter;
        this.payment = payment;
        this.sapling = sapling;
        this.seedling = seedling;
        this.wateredSeedling = wateredSeedling;
    }

    private final double plantXP, checkHealthXP;
    private final Tree treeType;
    private final int seedId, levelReq, containerIndex, sapling, seedling, wateredSeedling;
    private final int totalStages;
    private final int roots;
    private final PlayerCounter counter;

    @Override
    public int getSeed() {
        return seedId;
    }

    @Override
    public int getLevelReq() {
        return levelReq;
    }

    @Override
    public long getStageTime() {
        return TimeUtils.getMinutesToMillis(1);
    }

    @Override
    public long getStageTimeMems() {
        return 1;
    }

    @Override
    public int getTotalStages() {
        return totalStages;
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
        return plantXP;
    }

    @Override
    public int getContainerIndex() {
        return containerIndex;
    }

    @Override
    public int getProduceId() {
        return treeType.log;
    }

    @Override
    public double getHarvestXP() {
        return checkHealthXP;
    }

    public Tree getTreeType() {
        return treeType;
    }

    public int getRoots() {
        return roots;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

    @Override
    public Item getPayment() {
        return payment;
    }

    private final Item payment;

    @Override
    public int getSapling() {
        return sapling;
    }

    @Override
    public int getSeedling() {
        return seedling;
    }

    @Override
    public int getWateredSeedling() {
        return wateredSeedling;
    }
}