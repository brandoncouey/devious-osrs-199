package io.ruin.model.skills.magic.spells.arceuus;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class ArceuusTeleport extends Spell {

    public static final ArceuusTeleport ARCEUUS_LIBRARY = new ArceuusTeleport(6, 48.0, new Bounds(1635, 3791, 1639, 3794, 0), Rune.LAW.toItem(1), Rune.EARTH.toItem(2));

    public ArceuusTeleport(int lvlReq, double xp, Bounds bounds, Item... runeItems) {
        registerClick(lvlReq, xp, true, runeItems, (p, i) -> teleport(p, bounds));
    }

    public ArceuusTeleport(int lvlReq, double xp, int x, int y, int z, Item... runeItems) {
        registerClick(lvlReq, xp, true, runeItems, (p, i) -> teleport(p, x, y, z));
    }

    public static boolean teleport(Player player, Bounds bounds) {
        return teleport(player, bounds.randomX(), bounds.randomY(), bounds.z);
    }

    public static boolean teleport(Player player, int x, int y, int z) {
        return player.getMovement().startTeleport(e -> {
            player.animate(3865);
            player.graphics(1296);
            e.delay(3);
            player.getMovement().teleport(x, y, z);
        });
    }

}
