package io.ruin.model.activities.gauntlet;

import com.google.common.base.Stopwatch;
import io.ruin.model.entity.player.Player;
import lombok.Data;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Greco
 * @since 04/08/2021
 */
@Data
public class TheGauntlet {

    private final Player player; //the player
    private GauntletDungeon dungeon;
    private GauntletType type = GauntletType.NORMAL; //the dungeon difficulty
    private int[] currentRoom; //the current room coordinates the player is in.
    private long startTime; //the time the dungeon was started

    //Time your allowed in the dungeon in seconds.
    private static final long TIMER_DURATION = TimeUnit.MINUTES.toSeconds(10);

    //Warning intervals in seconds after the start of the stopwatch.
    private static final int[] WARNING_TIMES = {300, 60};

    //Stopwatch instance for dungeon timer.
    public Stopwatch stopwatch = Stopwatch.createUnstarted();


    private static final int[] STARTING_ITEMS = {
            23861, //crystal sceptre
            23862, //crystal axe
            23863, //crystal pickaxe
            23864, //crystal harpoon
            23865, //pestle and mortar
            23904, //teleport crystal
    };

    public boolean isBoss(int id) {
        return IntStream.of(9021, 9022, 9023, 9024, 9035, 9036, 9037, 9038).anyMatch(bossId -> bossId == id);
    }

    private boolean isWarningTime(long time) {
        return IntStream.of(WARNING_TIMES).anyMatch(warning -> warning == time);
    }
}
