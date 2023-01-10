package io.ruin.model.activities.wilderness.monsters;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.map.Projectile;
import io.ruin.model.map.route.routes.DumbRoute;
import io.ruin.model.stat.StatType;

import java.util.Arrays;

public class Dragons extends NPCCombat {

    private static final Projectile FIRE_PROJECTILE = new Projectile(393, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile POISON_PROJECTILE = new Projectile(394, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile SHOCK_PROJECTILE = new Projectile(395, 43, 31, 51, 56, 10, 15, 250);
    private static final Projectile FREEZE_PROJECTILE = new Projectile(396, 43, 31, 51, 56, 10, 15, 250);

    private static final StatType[] SHOCK_STATS = {
            StatType.Attack, StatType.Strength, StatType.Defence,
            StatType.Ranged, StatType.Magic
    };

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(13);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(16)) {
            DumbRoute.route(npc, target.getPosition().getX(), target.getPosition().getY());
            return false;
        }
        if (withinDistance(1) && Random.rollDie(10, 6)) {
            basicAttack();
        } else {
            if (Random.get() < 0.3 && target.player.getCombat().tbTicks <= 0) {
                TeleblockTarget(target.player);
            } else switch (Random.get(4)) {
                case 1:
                    fire(FREEZE_PROJECTILE, 10);
                    if (Random.rollDie(3, 1))
                        target.graphics(369, 0, 0);
                    target.privateSound(168);
                    target.freeze(5, npc);
                    target.player.sendMessage(Color.RED, "You have been frozen.");
                    target.player.getPacketSender().sendWidget(Widget.BARRAGE, 5);
                    break;
                case 2:
                    fire(SHOCK_PROJECTILE, 12);
                    if (target.player != null && Random.rollDie(3, 1))
                        Arrays.stream(SHOCK_STATS).forEach(s -> target.player.getStats().get(s).drain(2));
                    break;
                case 3:
                    fire(POISON_PROJECTILE, 10);
                    if (Random.rollDie(3, 1))
                        target.poison(8);
                    break;
                case 4:
                    fire(FIRE_PROJECTILE, 10);
                    break;
            }
        }
        return true;
    }

    public void TeleblockTarget(Player player) {
        player.graphics(345, 0, 0);
        player.privateSound(203);
        player.getCombat().teleblock();
    }


    private void fire(Projectile proj, int minMaxDamage) {
        npc.animate(81);
        minMaxDamage = (int) Math.max(65 * (1 - target.getCombat().getDragonfireResistance()), minMaxDamage);
        int delay = proj.send(npc, target);
        Hit hit = new Hit(npc, AttackStyle.DRAGONFIRE).randDamage(minMaxDamage).ignoreDefence().clientDelay(delay);
        if (minMaxDamage == 0)
            hit.block();
        target.hit(hit);
    }

}