package io.ruin.model.activities.wilderness;

import io.ruin.api.utils.Random;
import io.ruin.model.World;
import io.ruin.model.map.Position;
import lombok.Getter;

import java.util.Arrays;

public enum CrystalNpcSpawns {

    CRYSTAL_RAT(9026, 10),
    CRYSTAL_UNICORN(9029, 10),
    CRYSTAL_SCORPION(9030, 10),
    CRYSTAL_WOLF(9031, 5),
    CRYSTAL_SPIDER(9027, 10),
    CRYSTAL_BEAR(9032, 10),
    CRYSTAL_DRAGON(9033, 5),
    CRYSTAL_BAT(9028, 10),
    CRYSTAL_DARK_BEAST(9034, 5),
    CORRUPTED_DRAGON(9046, 1),
    CORRUPTED_DARK_BEAST(9048, 1),
    CORRUPTED_WOLF(9045, 1);


    public final int npcId, overworldSpawnWeight;

    CrystalNpcSpawns(int npcId, int overworldSpawnWeight) {
        this.npcId = npcId;
        this.overworldSpawnWeight = overworldSpawnWeight;
    }

    @Getter
    private static final int ACTIVE_OVERWORLD_CRYSTAL_NPCS = 0;

    private static final int OVERWORLD_SPAWN_DELAY = 120; // spawn time for random spawns in the wilderness (crystal npcs will attempt to spawn at this interval, if the active number is below maximum)
    private static final int OVERWORLD_MAX_CRYSTALNPCS = 40; // maximum number of wilderness spawns that can be active at one time
    private static final int OVERWORLD_TOTAL_SPAWN_WEIGHT = Arrays.stream(values()).mapToInt(crystal -> crystal.overworldSpawnWeight).sum();

    @Getter
    private static final int ACTIVE_WILD_CRYSTAL_NPCS = 0;

//    public static Position getRandomSpawn(Bounds bounds) {
//        Position spawn = bounds.randomPosition();
//        while(spawn.getTile().isWildySpawnFree() && !spawn.getTile().roofExists)
//            spawn = bounds.randomPosition();
//        return spawn;
//
//    }

    private static final Position[] WILD_RANDOM_SPAWN_POSITIONS = {
            new Position(3213, 3584, 0),
            new Position(3103, 3593, 0),
            new Position(3077, 3649, 0),
            new Position(3086, 3688, 0),
            new Position(3157, 3709, 0),
            new Position(3214, 3702, 0),
            new Position(3185, 3767, 0),
            new Position(3104, 3764, 0),
            new Position(3077, 3698, 0),
            new Position(3116, 3744, 0),
            new Position(3109, 3671, 0),
            new Position(3072, 3671, 0),
            new Position(3056, 3634, 0),
            new Position(3063, 3594, 0),
            new Position(2993, 3608, 0),
            new Position(2998, 3654, 0),
            new Position(3005, 3676, 0),
            new Position(3006, 3741, 0),
            new Position(3023, 3787, 0),
            new Position(2998, 3822, 0),
            new Position(2970, 3802, 0),
            new Position(3135, 3805, 0),
            new Position(3143, 3842, 0),
            new Position(3137, 3867, 0),
            new Position(3171, 3888, 0),
            new Position(3215, 3888, 0),
            new Position(3250, 3885, 0),
            new Position(3270, 3824, 0),
            new Position(3269, 3788, 0),
            new Position(3245, 3773, 0),
            new Position(3250, 3739, 0),
            new Position(3263, 3706, 0),
            new Position(3231, 3692, 0),
            new Position(3221, 3656, 0),
            new Position(3325, 3727, 0),
            new Position(3304, 3734, 0),
            new Position(3292, 3720, 0),
            new Position(3330, 3716, 0),
            new Position(3334, 3695, 0),
            new Position(3331, 3674, 0),
            new Position(3306, 3612, 0),
            new Position(3194, 3628, 0),
            new Position(3254, 3572, 0),
            new Position(3216, 3875, 0),
            new Position(3255, 3886, 0),
            new Position(3252, 3924, 0),
            new Position(3218, 3921, 0),
            new Position(3186, 3911, 0),
            new Position(3134, 3947, 0),
            new Position(3074, 3947, 0),
            new Position(3037, 3908, 0),
            new Position(3025, 3932, 0),
            new Position(3014, 3918, 0),
            new Position(2977, 3950, 0),
            new Position(3111, 3906, 0),
            new Position(3117, 3889, 0),
            new Position(3088, 3892, 0),
            new Position(3074, 3887, 0),
            new Position(3061, 3892, 0),
            new Position(3040, 3887, 0),
            new Position(3034, 3870, 0),
            new Position(3006, 3878, 0),
            new Position(2992, 3865, 0),
            new Position(2995, 3848, 0),
            new Position(3017, 3826, 0),
            new Position(3022, 3811, 0),
            new Position(3029, 3831, 0),
            new Position(3048, 3807, 0),
            new Position(3049, 3818, 0),
            new Position(3069, 3809, 0),
            new Position(3067, 3785, 0),
            new Position(3045, 3795, 0),
            new Position(3097, 3787, 0),
            new Position(3123, 3759, 0),
            new Position(3144, 3797, 0),
            new Position(3156, 3807, 0),
            new Position(3149, 3821, 0),
            new Position(3328, 3885, 0),
            new Position(3300, 3912, 0),
            new Position(3346, 3924, 0),
            new Position(3329, 3856, 0),
            new Position(3298, 3821, 0),
            new Position(3328, 3809, 0),
            new Position(3320, 3791, 0),
            new Position(3296, 3793, 0),
            new Position(3308, 3747, 0),
            new Position(3352, 3750, 0),
            new Position(3267, 3888, 0),
            new Position(3268, 3926, 0),
            new Position(3269, 3949, 0),
            new Position(3287, 3912, 0),
            new Position(2963, 3657, 0),
            new Position(2980, 3661, 0),
            new Position(2988, 3717, 0),
            new Position(2973, 3724, 0),
            new Position(2972, 3777, 0),
            new Position(3052, 3764, 0),
            new Position(3047, 3738, 0),
            new Position(3022, 3723, 0),
            new Position(3064, 3719, 0),
            new Position(3218, 3743, 0),
            new Position(3212, 3760, 0),
            new Position(3212, 3783, 0),
            new Position(3176, 3786, 0),
            new Position(3197, 3665, 0),
            new Position(3097, 3641, 0),
            new Position(2969, 3632, 0),
            new Position(3199, 3645, 0),
    };

    private static CrystalNpcSpawns getRandomOverwildSpawn() {
        int roll = Random.get(OVERWORLD_TOTAL_SPAWN_WEIGHT);
        for (CrystalNpcSpawns crystal : values()) {
            if (crystal.overworldSpawnWeight == 0)
                continue;
            roll -= crystal.overworldSpawnWeight;
            if (roll <= 0) {
                return crystal;
            }
        }
        return Random.get(values()); // should be unreachable
    }

//    private static void spawnRandomCrystalNpcOverworld() {
//        CrystalNpcSpawns type = getRandomOverwildSpawn();
//        Position spawnPosition = Random.get(WILD_RANDOM_SPAWN_POSITIONS);
//        NPC crystalnpc = new NPC(type.npcId).spawn(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ(), 16);
//        crystalnpc.getRouteFinder().routeAbsolute(crystalnpc.walkBounds.randomX(), crystalnpc.walkBounds.randomY());
//        ACTIVE_WILD_CRYSTAL_NPCS++;
//    }

    static {
        //wild spawns
        World.startEvent(event -> {
            //spawn a few on startup
            for (int i = 0; i < 30; i++)
                //     getRandomOverwildSpawn();
                while (true) {
                    if (ACTIVE_OVERWORLD_CRYSTAL_NPCS < OVERWORLD_MAX_CRYSTALNPCS)
                        //          spawnRandomCrystalNpcOverworld();
                        event.delay(OVERWORLD_SPAWN_DELAY);
                }
        });
    }
}
