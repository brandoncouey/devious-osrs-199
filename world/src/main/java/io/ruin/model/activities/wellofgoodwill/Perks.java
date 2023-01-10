package io.ruin.model.activities.wellofgoodwill;

public enum Perks {

    DOUBLE_DROPS("doubledrops", 175_000_000),
    DOUBLE_SLAYER("doubleslayer", 150_000_000),
    DOUBLE_RAIDS("doubleraids", 500_000_000),
    double_pest("doublepest", 150_000_000),
    DOUBLE_XP("doublexp", 150_000_000),
    NULL("NULL", 0);


    public int cost;
    public String perkName;

    Perks(String perkname, int cost) {
        this.perkName = perkname;
        this.cost = cost;
    }
}
