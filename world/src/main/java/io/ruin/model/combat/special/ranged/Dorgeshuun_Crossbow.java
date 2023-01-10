package io.ruin.model.combat.special.ranged;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.AttackStyle;
import io.ruin.model.combat.AttackType;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

public class Dorgeshuun_Crossbow implements Special {


    @Override
    public boolean accept(ItemDef def, String name) {
        return name.contains("dorgeshuun crossbow");
    }

    @Override
    public boolean handle(Player player, Entity victim, AttackStyle attackStyle, AttackType attackType, int maxDamage) {
//        player.graphics(0);
//        player.publicSound(0);
        player.sendMessage("You hit your target and drain there defence!");
        victim.player.getStats().get(StatType.Defence).drain(maxDamage);
        victim.player.sendMessage("Your defence was drained from being hit by a bone bolt!");
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 75;
    }
}
