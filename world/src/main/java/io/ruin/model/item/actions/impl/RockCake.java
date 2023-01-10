package io.ruin.model.item.actions.impl;

import io.ruin.model.combat.Hit;
import io.ruin.model.item.actions.ItemAction;

public class RockCake {

    private static final int ROCK_CAKE = 7510;

    static {
        ItemAction.registerInventory(ROCK_CAKE, "eat", (player, item) -> {
            if (player.getHp() <= 1) {
                player.sendMessage("Your Health is to low to consume this right now");
                return;
            }
            if (player.getHp() >= 40 && !player.rockCakeDelay.isDelayed()){
                player.privateSound(1018);
                player.rockCakeDelay.delay(1);
                player.hit(new Hit().fixedDamage(10));
                player.animate(829);
            }
            else if (player.getHp() >= 20 && !player.rockCakeDelay.isDelayed()) {
                player.privateSound(1018);
                player.rockCakeDelay.delay(1);
                player.hit(new Hit().fixedDamage(5));
                player.animate(829);
            }
            else if (player.getHp() >= 12 && !player.rockCakeDelay.isDelayed()) {
                player.privateSound(1018);
                player.rockCakeDelay.delay(2);
                player.hit(new Hit().fixedDamage(3));
                player.animate(829);
            }
                else if (player.getHp() >= 2 && !player.rockCakeDelay.isDelayed()) {
                    player.privateSound(1018);
                player.rockCakeDelay.delay(2);
                    player.hit(new Hit().fixedDamage(1));
                    player.animate(829);
            }
        });
        ItemAction.registerInventory(ROCK_CAKE, "guzzle", (player, item) -> {
            if (player.getHp() <= 2) {
                player.sendMessage("Your Health is to low to consume this right now");
                return;
            }
            if (player.getHp() >= 11) {
                player.privateSound(1018);
                player.hit(new Hit().fixedDamage(10));
                player.animate(829);
            }
        });
    }
}
