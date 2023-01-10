package io.ruin.model.skills.fishing;

import io.ruin.api.utils.Random;
import io.ruin.model.activities.cluescrolls.ClueType;
import io.ruin.model.entity.player.Player;

public enum FishingClueBottle {

    BEGINNER(24361, ClueType.BEGINNER, 1),
    EASY(24362, ClueType.EASY, 1),
    MEDIUM(24363, ClueType.MEDIUM, 20),
    HARD(24364, ClueType.HARD, 60),
    ELITE(24365, ClueType.ELITE, 80);

    public final int bottleId, levelThreshold;
    public final ClueType clueType;

    FishingClueBottle(int bottleId, ClueType clueType, int levelThreshold) {
        this.bottleId = bottleId;
        this.clueType = clueType;
        this.levelThreshold = levelThreshold;
    }

    public static void roll(Player player, FishingCatch fish, boolean barehand) {
        FishingClueBottle bottle = null;
        int fishLevel = fish.levelReq;
        if (barehand)
            fishLevel -= 20;

        for (FishingClueBottle cb : values()) {
            if (fishLevel >= cb.levelThreshold)
                bottle = cb;
        }
        if (bottle == null)
            return;
        double chance = (0.25 / fish.baseChance) / 160.0;
        if (Random.get() < chance) {
            player.getInventory().addOrDrop(bottle.bottleId, 1);
            player.sendMessage("You catch a scroll!");
        }
    }

/*    static {
        for (FishingClueBottle clueBottle : values())
            ItemAction.registerInventory(clueBottle.bottleId, "open", (player, item) -> {
                item.setId(clueBottle.clueType.clueId);
                player.sendMessage("You crack the bottle and find a clue scroll inside!");
            });
    }*/

}
