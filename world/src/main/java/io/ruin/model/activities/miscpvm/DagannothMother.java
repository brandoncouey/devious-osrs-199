package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.map.Projectile;


public class DagannothMother extends NPCCombat {

    private static final Projectile FIRE_PROJECTILE = new Projectile(130, 30, 31, 15, 56, 10, 15, 32);
    private static final Projectile WATER_PROJECTILE = new Projectile(136, 30, 31, 15, 56, 10, 15, 32);
    private static final Projectile EARTH_PROJECTILE = new Projectile(139, 30, 31, 15, 56, 10, 15, 32);
    private static final Projectile AIR_PROJECTILE = new Projectile(133, 30, 31, 15, 56, 10, 15, 32);


    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (Random.rollDie(4, 1))
            fireAttack();
        else if (Random.rollDie(4, 1))
            airAttack();
        else if (Random.rollDie(4, 1))
            waterAttack();
        else if (Random.rollDie(4, 1))
            earthAttack();
        return true;
    }

    private void fireAttack() {
        int delay = FIRE_PROJECTILE.send(npc, target);
        int maxDamage = 4;
        Hit hit = new Hit(npc, null).randDamage(maxDamage).clientDelay(delay);
        target.hit(hit);
    }

    private void airAttack() {
        int delay = AIR_PROJECTILE.send(npc, target);
        int maxDamage = 4;
        Hit hit = new Hit(npc, null).randDamage(maxDamage).clientDelay(delay);
        target.hit(hit);
    }

    private void waterAttack() {
        int delay = WATER_PROJECTILE.send(npc, target);
        int maxDamage = 4;
        Hit hit = new Hit(npc, null).randDamage(maxDamage).clientDelay(delay);
        target.hit(hit);
    }

    private void earthAttack() {
        int delay = EARTH_PROJECTILE.send(npc, target);
        int maxDamage = 4;
        Hit hit = new Hit(npc, null).randDamage(maxDamage).clientDelay(delay);
        target.hit(hit);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public void init() {
    }
}
