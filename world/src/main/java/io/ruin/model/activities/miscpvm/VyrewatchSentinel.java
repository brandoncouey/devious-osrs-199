package io.ruin.model.activities.miscpvm;

import io.ruin.api.utils.Random;
import io.ruin.cache.Color;
import io.ruin.model.World;
import io.ruin.model.combat.Hit;
import io.ruin.model.combat.Killer;
import io.ruin.model.entity.npc.NPCCombat;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.DeathListener;
import io.ruin.model.entity.shared.listeners.HitListener;
import io.ruin.model.item.Item;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.utility.Broadcast;


public class VyrewatchSentinel extends NPCCombat {

    @Override
    public void init() {
        npc.hitListener = new HitListener()
                .postDefend(this::postDefend);
        npc.deathEndListener = (DeathListener.Simple) () -> {
            for (Killer k : npc.getCombat().killers.values()) {
            if (Random.rollDie(300, 1)) {
                new GroundItem(24777, 1).owner(k.player).position(npc.getPosition()).spawn();
                Broadcast.GLOBAL.sendNews(Color.ORANGE.wrap(k.player.getName() + " received a " + "Blood Shard from a Vyrewatch Sentinel"));
            }
            }
        };
    }

    @Override
    public void follow() {
        follow(1);
    }

    @Override
    public boolean attack() {
        if (!withinDistance(1))
            return false;
        if (Random.rollDie(4))
            basicAttack(info.attack_animation, info.attack_style, 5).ignorePrayer();
        else
            basicAttack();
        return true;
    }

    private boolean usingFlail(Player player) {
        return player.getEquipment().hasId(22398) || player.getEquipment().hasId(24699);
    }

    private void postDefend(Hit hit) {
        if (hit.attacker != null && hit.attacker.player != null && hit.attackStyle != null) {
            Item weapon = hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON);
            if (weapon == null || !usingFlail(hit.attacker.player)) {
                hit.damage *= 0.5;
                hit.attacker.player.sendMessage("The Sentinel resists your weapon.");
            }
        }
    }
}
