package io.ruin.model.activities.bosses.nightmare;

import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.Position;
import io.ruin.model.map.object.GameObject;

public class Spore extends GameObject {

    int state = -1;

    public Spore(Position location) {

        super(37739, location.getX(), location.getY(), 3, 10, 0);
        spawn();
        check();
    }

    private void check() {
        if (state == 2) {
            remove();
            return;
        } else if (state > -1) {
            state++;
        }
        World.startEvent(e -> {
            e.delay(1);
            if (state == -1) {
                for (Player p : World.players) {
                    if (p.getPosition().distance(new Position(x, y, z)) < 2) {
                        p.set("nightmare_spore", System.currentTimeMillis() + 30000);
                        Config.RUNNING.set(p, 0);
//						p.hit(new Hit().randDamage(35));
                        animate(getDef().animationID + 1);
                        state = 0;
                    }
                }
            }
            check();
        });
    }


}
