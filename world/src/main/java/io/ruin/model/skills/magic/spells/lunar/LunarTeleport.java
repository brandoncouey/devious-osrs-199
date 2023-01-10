package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;

public class LunarTeleport extends Spell {


    public static final LunarTeleport MOONCLAN_TELEPORT = new LunarTeleport(69, 35.0, new Bounds(2111, 3914, 2114, 3916, 0), Rune.LAW.toItem(1), Rune.ASTRAL.toItem(2), Rune.EARTH.toItem(2));
    public static final LunarTeleport OURIANA_TELEPORT = new LunarTeleport(71, 35.0, new Bounds(2466, 3245, 2468, 3248, 0), Rune.LAW.toItem(1), Rune.ASTRAL.toItem(2), Rune.EARTH.toItem(6));
    public static final LunarTeleport WATERBIRTH_TELEPORT = new LunarTeleport(72, 41.0, new Bounds(2544, 3753, 2547, 3757, 0), Rune.LAW.toItem(1), Rune.ASTRAL.toItem(2), Rune.WATER.toItem(1));
    public static final LunarTeleport BARBARIAN_TELEPORT = new LunarTeleport(75, 48.0, new Bounds(2541, 3567, 2544, 3572, 0), Rune.LAW.toItem(2), Rune.ASTRAL.toItem(2), Rune.FIRE.toItem(3));
    public static final LunarTeleport KHAZARD_TELEPORT = new LunarTeleport(78, 55.5, new Bounds(2636, 3165, 2637, 3168, 0), Rune.LAW.toItem(2), Rune.ASTRAL.toItem(2), Rune.WATER.toItem(4));
    public static final LunarTeleport FISHING_GUILD_TELEPORT = new LunarTeleport(85, 61.0, new Bounds(2608, 3389, 2610, 3391, 0), Rune.LAW.toItem(3), Rune.ASTRAL.toItem(3), Rune.WATER.toItem(10));
    public static final LunarTeleport CATHERBY_TELEPORT = new LunarTeleport(87, 68.0, new Bounds(2805, 3455, 2807, 3457, 0), Rune.LAW.toItem(3), Rune.ASTRAL.toItem(3), Rune.WATER.toItem(10));
    public static final LunarTeleport ICE_PLATEAU_TELEPORT = new LunarTeleport(89, 71.0, new Bounds(2971, 3917, 2975, 3922, 0), Rune.LAW.toItem(3), Rune.ASTRAL.toItem(3), Rune.ASTRAL.toItem(8));

    public int getLvlReq() {
        return lvlReq;
    }

    public double getXp() {
        return xp;
    }

    public Item[] getRunes() {
        return runes;
    }

    public LunarTeleport(int lvlReq, double xp, Bounds bounds, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> teleport(p, bounds));
    }

    public LunarTeleport(int lvlReq, double xp, Bounds[] bounds, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> teleport(p, bounds[i]));
    }

    public LunarTeleport(int lvlReq, double xp, int x, int y, int z, Item... runes) {
        this.lvlReq = lvlReq;
        this.xp = xp;
        this.runes = runes;
        registerClick(lvlReq, xp, true, runes, (p, i) -> teleport(p, x, y, z));
    }

    private final int lvlReq;
    private final double xp;
    private final Item[] runes;

    public static boolean teleport(Player player, Bounds bounds) {
        return teleport(player, bounds.randomX(), bounds.randomY(), bounds.z);
    }

    public static boolean teleport(Player player, Position position) {
        return teleport(player, position.getX(), position.getY(), position.getZ());
    }

    public static boolean teleport(Player player, int x, int y, int z) {
        return player.getMovement().startTeleport(e -> {
            player.animate(1816);
            player.graphics(747, 120, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(x, y, z);
        });
    }

}
