package io.ruin.model.activities.raids.tob.dungeon.boss.xarpus.attacks;

import io.ruin.model.combat.Hit;
import io.ruin.model.combat.HitType;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.object.GameObject;

public class PoisonPoolAttack {

    private static final Projectile spitProjectile = new Projectile(1644, 35, 30, 30, 46, 8, 15, 255).tileOffset(1);

    public PoisonPoolAttack(Entity target, NPC npc) {
        poisonSpit(target, npc);
    }

    private void poisonSpit(Entity target, NPC npc) {
        npc.animate(8059);
        int acidPools = 1;
        for (int i = 0; i < acidPools; i++) {
            Position pos = target.getPosition().copy();
            spitProjectile.send(npc, pos);
            npc.addEvent(event -> {
                event.delay(3);
                GameObject pool = GameObject.spawn(32000, pos, 10, 0);
                event.delay(6);
                pool.remove();
                if (target.getAbsX() >= pool.x && target.getAbsX() <= pool.x + 1 && target.getAbsY() >= pool.y && target.getAbsY() <= pool.y + 1)
                    target.hit(new Hit(HitType.POISON).fixedDamage(20).ignoreDefence());
                event.delay(1);
            });
        }
    }
}
