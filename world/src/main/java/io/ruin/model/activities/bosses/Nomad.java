package io.ruin.model.activities.bosses;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.map.Position;
import io.ruin.model.map.Projectile;
import io.ruin.utility.Misc;

public class Nomad extends NPCCombat {

    private static final Projectile BOULDER_DROP_PROJECTILE = new Projectile(1493, 150, 0, 0, 135, 0, 0, 127);
    private static final int BOULDER_HIT_GFX = 1494;

    private static final Projectile MAGIC_PROJECTILE = new Projectile(1577, 0, 0, 20, 15, 12, 0, 10);

    private boolean forceQuake = false;

    @Override
    public void init() {
        npc.hitsUpdate.hpBarType = 2;
        npc.hitListener = new HitListener().postDefend(this::postDefend);
    }

    private void postDefend(Hit hit) {
        if (hit.damage > 100 && !hit.isBlocked())
            hit.damage = 100;
    }

    @Override
    public void updateLastDefend(Entity attacker) {
        super.updateLastDefend(attacker);
        super.updateLastDefend(attacker);
        if (attacker.player != null && !attacker.player.getCombat().isSkulled()) {
            attacker.player.getCombat().skullNormal();
            attacker.player.sendMessage("<col=6f0000>You've been marked with a skull for attacking Glod!");
        }
    }

    @Override
    public void follow() {
        follow(6);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(8))
            return false;
        if (forceQuake || Random.rollDie(5, 1)) {
            earthquake();
            forceQuake = false;
        } else if (withinDistance(1) || Random.rollPercent(80)) {
            rangedAttack();
        } else {
            taunt();
        }
        return true;
    }

    private void earthquake() {
        npc.animate(8184);
        npc.graphics(1627);
        npc.forceText("Feel my wrath!");
        npc.addEvent(event -> {
            event.delay(1);
            npc.localPlayers().forEach(p -> {
                if (!canAttack(p))
                    return;
                int distance = Misc.getEffectiveDistance(npc, p);
                if (distance >= 8)
                    return;
                int damage = 50 - (distance * 5);
                int delay = MAGIC_PROJECTILE.send(npc, p);
                p.hit(new Hit(npc, AttackStyle.MAGIC).randDamage(info.max_damage).clientDelay(delay));
            });
        });
    }

    private void rangedAttack() {
        npc.animate(6147);
        npc.addEvent(event -> {
            event.delay(1);
            npc.localPlayers().forEach(p -> {
                if (!canAttack(p))
                    return;
                int distance = Misc.getEffectiveDistance(npc, p);
                if (distance >= 8)
                    return;
                p.getPrayer().deactivateAll();
                p.sendMessage("Nomad's slam disables your prayers!");
                Position pos = target.getPosition().copy();
                int clientDelay = BOULDER_DROP_PROJECTILE.send(npc.getAbsX(), npc.getAbsY(), pos.getX(), pos.getY());
                p.hit(new Hit(npc, null, null).fixedDamage(target.getHp() / 3).ignoreDefence().ignorePrayer());
                World.sendGraphics(BOULDER_HIT_GFX, 35, clientDelay, pos.getX(), pos.getY(), pos.getZ());
                for (int i = 0; i <= 2; i++)
                    p.getPacketSender().shakeCamera(0, 6);
                p.addEvent(camEvent -> {
                    camEvent.delay(2);
                    p.getPacketSender().resetCamera();
                });

            });
        });
    }

    private void taunt() {
        Player player = target.player;
        npc.animate(1056);
        npc.forceText("Kneel before your master!");
        delayAttack(-2);
        forceQuake = true;
        if (target.player != null)
            target.player.sendMessage(Color.RED.wrap("Nomad's taunt forces you to mindlessly run towards him!"));
        player.addEvent(event -> {
            player.lock();
            player.getCombat().reset();
            player.face(npc);
            player.getRouteFinder().routeEntity(npc);
            event.delay(2);
            player.unlock();
        });
    }
}