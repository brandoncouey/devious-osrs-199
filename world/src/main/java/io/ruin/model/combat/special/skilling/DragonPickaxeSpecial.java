package io.ruin.model.combat.special.skilling;

import io.ruin.cache.ItemDef;
import io.ruin.model.combat.special.Special;
import io.ruin.model.entity.player.Player;
import io.ruin.model.stat.StatType;

//Rock Knocker: Increase your Mining level by 3. (100%)
public class DragonPickaxeSpecial implements Special {

    @Override
    public boolean accept(ItemDef def, String name) {
        return name.equals("dragon pickaxe") || name.equals("infernal pickaxe") || name.equals("crystal pickaxe");
    }

    @Override
    public boolean handleActivation(Player player) {
        if (!player.getCombat().useSpecialEnergy(100))
            return false;

        player.animate(7138);
        player.forceText("Smashing!");
        player.getStats().get(StatType.Mining).boost(3, 0);
        player.dragonPickaxeSpecial = 200;
        return true;
    }

    @Override
    public int getDrainAmount() {
        return 100;
    }

}