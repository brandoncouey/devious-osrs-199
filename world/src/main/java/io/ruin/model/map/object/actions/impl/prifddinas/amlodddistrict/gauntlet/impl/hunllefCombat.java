package io.ruin.model.map.object.actions.impl.prifddinas.amlodddistrict.gauntlet.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.ProjectileRoute;
import io.ruin.model.skills.prayer.Prayer;

public class hunllefCombat extends NPCCombat {

    private static final Projectile STANDARD_PROJECTILE = new Projectile(995, 60, 40, 25, 70, 10, 15, 127);
    private static final Projectile FIRE_BURN = new Projectile(1512, 60, 31, 25, 70, 10, 16, 128);
    private static final Projectile FIRE_MAGIC = new Projectile(1535, 60, 31, 25, 70, 10, 16, 128);
    private static final Projectile DRAINER = new Projectile(1547, 60, 31, 25, 70, 10, 16, 128);

    @Override
    public void init() {
        npc.hitListener = new HitListener().postDefend(this::postDefend).preDefend(this::preDefend);
    }

    @Override
    public void follow() {
        follow(8);
    }

    @Override
    public boolean attack() {
        if (Random.rollPercent(30))
            MagicAttack();
        else if (Random.rollPercent(20))
            Drainer();
        else if (Random.rollPercent(10))
            FireBurn();
        else
            RangeAttack();
        return true;
    }

    private void RangeAttack() {
        int maxDamage = info.max_damage;
        npc.animate(info.attack_animation);
        for (Player p : npc.localPlayers()) {
            if (ProjectileRoute.allow(npc, p)) {
                if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MISSILES))
                    maxDamage = (maxDamage / 3);
                int delay = STANDARD_PROJECTILE.send(npc, p);
                p.hit(new Hit(npc, AttackStyle.RANGED).randDamage(maxDamage).clientDelay(delay));
            }
        }
    }

    private void MagicAttack() {
        int maxDamage = info.max_damage;
        npc.animate(info.attack_animation);
        for (Player p : npc.localPlayers()) {
            if (ProjectileRoute.allow(npc, p)) {
                if (p.getPrayer().isActive(Prayer.PROTECT_FROM_MAGIC))
                    maxDamage = (maxDamage / 3);
                int delay = FIRE_MAGIC.send(npc, p);
                p.graphics(1536, 124, 25);
                p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay));
            }
        }
    }

    private void FireBurn() {
        int maxDamage = info.max_damage;
        npc.animate(info.attack_animation);
        for (Player p : npc.localPlayers()) {
            if (ProjectileRoute.allow(npc, p)) {
                int delay = FIRE_BURN.send(npc, p);
                p.graphics(1617, 0, 25);
                p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(maxDamage).clientDelay(delay));
            }
        }
    }

    private void Drainer() {
        npc.animate(info.attack_animation);
        npc.graphics(25, 250, 0);
        for (Player p : npc.localPlayers()) {
            if (ProjectileRoute.allow(npc, p)) {
                DRAINER.send(npc, p);
                p.graphics(1548, 124, 25);
                p.getPrayer().drain(15);
            }
        }
    }

    private void preDefend(Hit hit) {
        if (npc.getId() == 9021 && hit.attackStyle.isMelee()) {
            hit.block();
        } else if (npc.getId() == 9023 && hit.attackStyle.isMagic()) {
            hit.block();
        } else if (npc.getId() == 9022 && hit.attackStyle.isCannon() || npc.getId() == 9022 && hit.attackStyle.isRanged()) {
            hit.block();
        }
    }

    private void postDefend(Hit hit) {
        if (Random.rollDie(10, 1)) {
            npc.transform(Random.get(9021, 9022, 9023));
        }
    }

}
