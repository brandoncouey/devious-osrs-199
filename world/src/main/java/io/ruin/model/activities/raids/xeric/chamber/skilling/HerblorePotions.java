package io.ruin.model.activities.raids.xeric.chamber.skilling;

public enum HerblorePotions {
    ELDER(20905, 20910, new int[]{70, 59, 47}, new int[]{20924, 20920, 20916}),
    TWISTED(20905, 20912, new int[]{70, 59, 47}, new int[]{20936, 20932, 20928}),
    KODAI(20905, 20911, new int[]{70, 59, 47}, new int[]{20948, 20944, 20940}),
    REVITALISATION(20908, 20910, new int[]{78, 65, 52}, new int[]{20960, 20956, 20952}),
    PRAYER_ENHANCE(20908, 20912, new int[]{78, 65, 52}, new int[]{20972, 20968, 20964}),
    XERIC_ACID(20908, 20911, new int[]{78, 65, 52}, new int[]{20984, 20980, 20976}),
    OVERLOAD(new int[][]{{20924, 20936, 20948}, {20920, 20932, 20944}, {20916, 20928, 20940}}, 20902, new int[]{90, 75, 60}, new int[]{20996, 20992, 20988});

    public int herbId;
    public int secondaryId;
    public int[] levelReqs;
    public int[] potionIds;
    public int[][] secondaryPotions;

    HerblorePotions(int herbId, int secondaryId, int[] levelReqs, int[] potionIds) {
        this.herbId = herbId;
        this.secondaryId = secondaryId;
        this.levelReqs = levelReqs;
        this.potionIds = potionIds;
    }

    HerblorePotions(int[][] secondaryPotions, int herbId, int[] levelReqs, int[] potionIds) {
        this.secondaryPotions = secondaryPotions;
        this.herbId = herbId;
        this.levelReqs = levelReqs;
        this.potionIds = potionIds;
    }
}