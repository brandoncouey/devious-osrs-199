package io.ruin.model.skills.farming.patch.impl;

import io.ruin.model.skills.farming.crop.Crop;
import io.ruin.model.skills.farming.crop.impl.AnimaCrop;
import io.ruin.model.skills.farming.patch.Patch;

public class AnimaPatch extends Patch {

    @Override
    public int getCropVarpbitValue() {
        int index = 0;
        if (isDiseased()) {
            index = (getStage() - 1) | (1 << 7);
        } else if (isDead()) {
            index = 170 + Math.min(getStage() - 1, 2);
        } else {
            index = getPlantedCrop().getContainerIndex() + getStage();
        }
        return index;
    }

    @Override
    public void cropInteract() {
        if (isDead()) {
            clear();
            return;
        }
    }

    @Override
    public boolean canPlant(Crop crop) {
        return crop instanceof AnimaCrop;
    }

    @Override
    public boolean isDiseaseImmune() {
        return true;
    }

    @Override
    public int calculateProduceAmount() {
        return 0;
    }

    @Override
    public boolean requiresCure() {
        return false;
    }

    @Override
    public String getPatchName() {
        return "a Anima";
    }

}