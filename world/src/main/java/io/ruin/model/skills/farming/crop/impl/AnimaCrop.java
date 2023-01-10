package io.ruin.model.skills.farming.crop.impl;

import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.skills.farming.crop.Crop;

//Attas 22881, Iasor 22883, Kronos 22885
public enum AnimaCrop implements Crop {
    ATTAS(22881, -1, 76, 100, 555, 1, 8, PlayerCounter.GROWN_ATTAS),
    IASOR(22883, -1, 76, 100, 555, 1, 17, PlayerCounter.GROWN_IASOR),
    KRONOS(22885, -1, 76, 100, 555, 1, 26, PlayerCounter.GROWN_KRONOS);


    AnimaCrop(int seedId, int produceId, int levelReq, double plantXP, double harvestXP, long stageTime,
              int containerIndex, PlayerCounter counter) {
        this.plantXP = plantXP;
        this.harvestXP = harvestXP;
        this.seedId = seedId;
        this.produceId = produceId;
        this.levelReq = levelReq;
        this.containerIndex = containerIndex;
        this.stageTime = stageTime;
        this.counter = counter;
    }

    private final double plantXP;
    private final double harvestXP;
    private final int seedId;
    private final int produceId;
    private final int levelReq;
    private final int containerIndex;
    private final long stageTime;
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
        return stageTime;
    }

    @Override
    public long getStageTimeMems() {
        return 1;
    }

    @Override
    public int getTotalStages() {
        return 4;
    }

    @Override
    public double getDiseaseChance(int compostType) {
        switch (compostType) {
            case 2:
                return 0.055;
            case 1:
                return 0.065;
            case 0:
            default:
                return 0.10;
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
        return produceId;
    }

    @Override
    public double getHarvestXP() {
        return harvestXP;
    }

    @Override
    public PlayerCounter getCounter() {
        return counter;
    }

}
