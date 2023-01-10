package io.ruin.cache;

public enum Icon {
    RED_INFO_BADGE(15),
    YELLOW_INFO_BADGE(15),
    MYSTERY_BOX(267),
    BLUE_INFO_BADGE(15),
    GREEN_INFO_BADGE(15),
    WILDERNESS(46),
    ANNOUNCEMENT(55),
    NORMAL(31),
    IRONMAN(2),
    UIM(3),
    HCIM(10),
    GIM(41),
    HGIM(42),
    HCIM_DEATH(32),
    DONATOR(142);

    public final int imgId;

    Icon(int imgId) {
        this.imgId = imgId;
    }


    public String tag() {
        return "<img=" + imgId + ">";
    }

}
