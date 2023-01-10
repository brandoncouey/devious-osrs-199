package io.ruin.model.skills.mining;

public enum Rock {
    CLAY(434, "clay", 1, 15, 5.0, 3, 74160, 1.0 / 2),
    COPPER(436, "copper", 1, 50, 17.5, 5, 74160, 1),
    TIN(438, "tin", 1, 50, 17.5, 5, 74160, 1),
    IRON(440, "iron", 15, 150, 25.0, 10, 74160, 1),
    BLURITE(668, "blurite", 10, 100, 17.5, 25, 74160, 1),
    SILVER(442, "silver", 20, 150, 30.0, 15, 74160, 1),
    COAL(453, "coal", 30, 150, 35.0, 20, 29064, 1),
    GOLD(444, "gold", 40, 200, 65.0, 20, 29664, 1),
    MITHRIL(447, "mithril", 55, 225, 75.0, 120, 14832, 1),
    LOVAKITE(13356, "lovakite", 65, 350, 10.0, 40, 24556, 1),
    ADAMANT(449, "adamant", 70, 250, 85.0, 240, 9328, 1),
    RUNE(451, "rune", 85, 300, 100.0, 720, 4237, 1),
    AMETHYST(21347, "amethyst", 92, 1000, 246.0, 120, 2500, 0.25), //right here idiot
    SANDSTONE(new int[]{6971, 6973, 6975, 6977}, "sandstone", 35, 150, new int[]{30, 40, 50, 60}, 5, 74160, 1),
    GRANITE(new int[]{6979, 6981, 6983}, "granite", 45, 150, new int[]{50, 60, 75}, 5, 74160, 1),
    GEM_ROCK(new int[]{1625, 1627, 1629, 1623, 1621, 1619, 1617}, "gem", 40, 300, 65.0, 35, 9328, 1),
    BASALT(22603, "basalt", 72, 15, 5.0, 15, 29664, 1),
    TE_SALT(22593, "te salt", 72, 15, 5.0, 200, 29664, 1),
    EFH_SALT(22595, "efh salt", 72, 15, 5.0, 200, 29664, 1),
    URT_SALT(22597, "urt salt", 72, 15, 5.0, 200, 29664, 1),
    LIMESTONE(3211, "limestone", 10, 15, 26.5, 20, 74160, 1),
    ASHPILE(21622, "volcanic ash", 1, 20, 5, 80, 99999, 1),
    TEPHRA(23905, "Tephra", 70, 200, 5, 0, 99999, 0),
    GLOWING_TEPHRA(23905, "Tephra", 70, 100, 5, 0, 99999, 0);

    public final int ore, levelReq, difficulty, respawnTime, petOdds;
    public final String rockName;
    public final double experience;
    public final int[] multiOre;
    public final int[] multiExp;
    public final double depleteChance;

    Rock(int ore, String rockName, int levelReq, int difficulty, double experience, int respawnTime, int petOdds, double depleteChance) {
        this.ore = ore;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.difficulty = difficulty;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.multiOre = null;
        this.multiExp = null;
        this.depleteChance = depleteChance;
    }

    Rock(int[] multiOre, String rockName, int levelReq, int difficulty, int[] multiExp, int respawnTime, int petOdds, double depleteChance) {
        this.multiOre = multiOre;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.difficulty = difficulty;
        this.multiExp = multiExp;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.ore = -1;
        this.experience = -1;
        this.depleteChance = depleteChance;
    }

    Rock(int[] multiOre, String rockName, int levelReq, int difficulty, double experience, int respawnTime, int petOdds, double depleteChance) {
        this.multiOre = multiOre;
        this.rockName = rockName;
        this.levelReq = levelReq;
        this.difficulty = difficulty;
        this.experience = experience;
        this.respawnTime = respawnTime;
        this.petOdds = petOdds;
        this.ore = -1;
        multiExp = null;
        this.depleteChance = depleteChance;
    }

}
