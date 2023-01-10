package io.ruin.model.skills.prayer;

import io.ruin.cache.ItemDef;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;

public enum Ashes {

    FIENDISH_ASHES(25766, 5, PlayerCounter.FIENDISH_ASHES, PlayerCounter.FIENDISH_ASHES),
    VILE_ASHES(25769, 12.5, PlayerCounter.VILE_ASHES, PlayerCounter.VILE_ASHES),
    MALICIOUS_ASHES(25772, 32.5, PlayerCounter.MALICIOUS_ASHES, PlayerCounter.MALICIOUS_ASHES),
    ABYSSAL_ASHES(25775, 42.5, PlayerCounter.ABYSSAL_ASHES, PlayerCounter.ABYSSAL_ASHES),
    INFERNAL_ASHES(25778, 55, PlayerCounter.INFERNAL_ASHES, PlayerCounter.INFERNAL_ASHES);


    public final int id, notedId;
    public final double exp;
    public final PlayerCounter spreadCounter, altarCounter;

    Ashes(int id, double exp, PlayerCounter spreadCounter, PlayerCounter altarCounter) {
        this.id = id;
        this.notedId = ItemDef.get(id).notedId;
        this.exp = exp;
        this.spreadCounter = spreadCounter;
        this.altarCounter = altarCounter;
    }

    private static final int DRAGONBONE_NECKLACE = 22111;

    private void checkBeforeSpread(Player player, Item ash) {
        spread(player, ash);
    }

    private void spread(Player player, Item ash) {
        player.resetActions(true, false, true);
        player.startEvent(event -> {
            if (player.ashSpreadDelay.isDelayed())
                return;
            ItemDef neckDef = player.getEquipment().getDef(Equipment.SLOT_AMULET);
            if (neckDef != null && neckDef.id == DRAGONBONE_NECKLACE) {
                boneNecklaceEffect(player, ash);
            }
            ash.remove();
            player.animate(2295);
            player.getStats().addXp(StatType.Prayer, exp, true);
            player.privateSound(2444);

            spreadCounter.increment(player, 1);
            player.karamDelay.delay(2);
            player.sendMessage("You spread the ashes.");
        });
    }

    private void boneNecklaceEffect(Player player, Item ash) {
        int boneId = ash.getId();
        Stat prayer = player.getStats().get(StatType.Prayer);
        if (boneId == FIENDISH_ASHES.id)
            prayer.restore(1, 0);
        else if (boneId == VILE_ASHES.id)
            prayer.restore(2, 0);
        else if (boneId == MALICIOUS_ASHES.id)
            prayer.restore(3, 0);
        else if (boneId == INFERNAL_ASHES.id)
            prayer.restore(4, 0);
        else if (boneId == ABYSSAL_ASHES.id)
            prayer.restore(5, 0);
    }

    static {
        for (Ashes ash : values()) {
            if (ash.exp <= Ashes.MALICIOUS_ASHES.exp)
                ItemDef.get(ash.id).allowFruit = true;
            ItemAction.registerInventory(ash.id, "scatter", ash::checkBeforeSpread);
        }
    }

    public static Ashes get(int ashId) {
        for (Ashes b : values()) {
            if (ashId == b.id)
                return b;
        }
        return null;
    }

}
