package io.ruin.model.activities.jail;

import io.ruin.model.inter.dialogue.PlayerDialogue;
import io.ruin.model.item.actions.ItemPlayerAction;
import io.ruin.model.map.Projectile;

public class RottenTomato {

    public static final int ROTTENTOMATO = 2518;

    public static final Projectile TOMATO_PROJ = new Projectile(29, 30, 12, 62, 50, 15, 11, 64);

    static {
        ItemPlayerAction.register(ROTTENTOMATO, (player, item, other) -> {
            player.startEvent(e -> {
                while (player.getInventory().hasId(ROTTENTOMATO)) {
                    if (!other.jailed) {
                        player.dialogue(new PlayerDialogue("I'm not going to throw fruit at that player, they might hit me!"));
                        break;
                    }
                    player.getPosition().isWithinDistance(other.getPosition(), 15);
                    // How the fuck am I supposed to skip reach -.- someone kill me now...
                    player.animate(385);
                    player.graphics(30, 40, 0);
                    player.forceText("Sit you rat bastard!");
                    player.getInventory().remove(ROTTENTOMATO, 1);
                    int delay = TOMATO_PROJ.send(player, other);
                    other.graphics(31, 0, delay - 50);
                    e.delay(5);
                }
            });
        });
    }
}
