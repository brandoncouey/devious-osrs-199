package io.ruin.model.skills.fishing;

public enum FishingCatch {
    /**
     * Regular
     */
    SHRIMPS(317, 318, 1, 10.0, 0.6, 50000),
    SARDINE(327, 328, 5, 20.0, 0.6, 49000),
    HERRING(345, 345, 10, 30.0, 0.6, 47000),
    ANCHOVIES(321, 322, 15, 40.0, 0.6, 46000),
    MACKEREL(353, 354, 16, 20.0, 0.6, 45000),
    TROUT(335, 336, 20, 50.0, 0.7, 46000),
    COD(341, 342, 23, 45.0, 0.7, 37000),
    PIKE(349, 350, 25, 60.0, 0.7, 35000),
    SALMON(331, 332, 30, 70.0, 0.7, 30000),
    TUNA(359, 360, 35, 80.0, 0.6, 30000),
    LOBSTER(377, 378, 40, 90.0, 0.6, 28000),
    BASS(363, 364, 46, 100.0, 0.6, 27000),
    SWORDFISH(371, 372, 50, 100.0, 0.6, 24000),
    MONKFISH(7944, 7945, 62, 120.0, 0.6, 24000),
    SHARK(383, 384, 76, 110.0, 0.3, 20000),
    ANGLERFISH(13439, 13440, 82, 120.0, 0.18, 20000),
    DARK_CRAB(11934, 11935, 85, 130.0, 0.3, 20000),
    MINNOWS(21356, -1, 82, 26.0, 1.0, 100000),
    INFERNAL_EEL(21293, 21294, 80, 95.0, 0.3, 10000),
    SLIMY_EEL(3379, 3380, 80, 95.0, 0.3, 10000),
    /**
     * Barbarian
     */
    LEAPING_TROUT(11328, 11328, 48, 15, 15, 50.0, 5.0, 0.65, 50000),
    LEAPING_SALMON(11330, 11331, 58, 30, 30, 70.0, 6.0, 0.45, 50000),
    LEAPING_STURGEON(11332, 11333, 70, 45, 45, 80.0, 7.0, 0.35, 50000),
    /**
     * Barehand
     */
    BARBARIAN_TUNA(359, 350, 55, 15, 35, 80.0, 8.0, 0.6, 50000),
    BARBARIAN_SWORDFISH(371, 372, 70, 15, 50, 100.0, 10.0, 0.6, 50000),
    BARBARIAN_SHARK(383, 384, 96, 15, 76, 110.0, 11.0, 0.6, 50000),
    KARAMBWAN(3142, 3143, 65, 50.0, 0.4, 24000),
    MOLTEN_EEL(30086, 30087, 65, 50.0, 0.4, 24000),
    HARPOON_FISH(25564, 25565, 35, 10, 0.4, 24000);

    public final int id;

    public final int notedid;

    public final int levelReq;

    public final int agilityReq;

    public final int strengthReq;

    public final double xp;

    public final double barbarianXp;

    public final double baseChance;

    public final int petOdds;

    FishingCatch(int id, int notedid, int levelReq, double xp, double baseChance, int petOdds) {
        this(id, notedid, levelReq, 0, 0, xp, 0.0, baseChance, petOdds);
    }

    FishingCatch(int id, int notedid, int levelReq, int agilityReq, int strengthReq, double xp, double barbarianXp, double baseChance, int petOdds) {
        this.id = id;
        this.notedid = notedid;
        this.levelReq = levelReq;
        this.agilityReq = agilityReq;
        this.strengthReq = strengthReq;
        this.xp = xp;
        this.barbarianXp = barbarianXp;
        this.baseChance = baseChance;
        this.petOdds = petOdds;
    }

}
