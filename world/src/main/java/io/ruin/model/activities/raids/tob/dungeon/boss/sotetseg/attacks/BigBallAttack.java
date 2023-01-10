package io.ruin.model.activities.raids.tob.dungeon.boss.sotetseg.attacks;

import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.map.Projectile;

public class BigBallAttack {

    private static final int START_HEIGHT = 35;
    private static final int END_HEIGHT = 30;
    private static final int DELAY = 30;
    private static final int DURATION_START = 46;
    private static final int DURATION_INCREMENT = 8;
    private static final int CURVE = 15;
    private static final int OFFSET = 255;
    private static final int TILE_OFFSET = 1;

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1609, START_HEIGHT, END_HEIGHT, DELAY, DURATION_START, DURATION_INCREMENT, CURVE, OFFSET).tileOffset(TILE_OFFSET);

    public static final int MAGIC_ANIM = 8139;

    public BigBallAttack(Entity target, NPC npc) {
        redBall(target, npc);
    }

    private void redBall(Entity target, NPC npc) {
        npc.animate(MAGIC_ANIM);
        int maxDamage = 87;

        Player closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (Player p : npc.getPosition().getRegion().players) {
            double distance = npc.getPosition().center(npc.getSize()).distance(p.getPosition());
            if (distance < closestDistance) {
                closest = p;
                closestDistance = distance;
            }
        }
        if (closest != null) {
            int delay = MAGIC_PROJECTILE.send(npc, target);
            npc.startEvent(event -> event.delay(3));
            Hit hit = new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay);
            target.hit(hit);
        }
    }
}