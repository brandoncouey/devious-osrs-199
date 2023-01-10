package io.ruin.model.activities.wilderness.monsters;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.combat.Hit;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.Widget;
import io.ruin.model.map.route.routes.DumbRoute;

public class DarkBeast extends NPCCombat {

    @Override
    public void init() {

    }

    @Override
    public void follow() {
        follow(20);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(11)) {
            DumbRoute.route(npc, target.getPosition().getX(), target.getPosition().getY());
            return false;
        }
        if (withinDistance(1) && Random.rollDie(4, 1)) {
            basicAttack();
        } else if (Random.get() < 0.3 && target.player.getCombat().tbTicks <= 0) {
            TeleblockTarget(target.player);
        } else if (Random.get() > 0.7 && !target.player.isFrozen()) {
            target.graphics(369, 0, 0);
            target.privateSound(168);
            target.freeze(5, npc);
            target.player.sendMessage(Color.RED, "You have been frozen.");
            target.player.getPacketSender().sendWidget(Widget.BARRAGE, 5);
            target.hit(new Hit(npc).randDamage(8));
        }
        return true;
    }

    public void TeleblockTarget(Player player) {
        player.graphics(345, 0, 0);
        player.privateSound(203);
        player.getCombat().teleblock();
    }

}
