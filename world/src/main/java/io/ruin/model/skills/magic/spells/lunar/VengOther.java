package io.ruin.model.skills.magic.spells.lunar;

import io.ruin.model.activities.duelarena.DuelRule;
import io.ruin.model.inter.Widget;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.skills.magic.Spell;
import io.ruin.model.skills.magic.rune.Rune;
import io.ruin.model.stat.StatType;

public class VengOther extends Spell {

    public VengOther() {
        Item[] runes = {
                Rune.DEATH.toItem(2),
                Rune.ASTRAL.toItem(3),
                Rune.EARTH.toItem(10)
        };
        registerEntity(93, runes, (player, entity) -> {
            if (DuelRule.NO_MAGIC.isToggled(player)) {
                player.sendMessage("Magic has been disabled for this duel!");
                return false;
            }
            if (!entity.player.inMulti()) {
                player.sendMessage("This player must be in multi-zone for you to cast this spell.");
                return false;
            }
            if (entity.player.vengeanceActive) {
                player.sendMessage("This player already have this spell casted.");
                return false;
            }
            if (Config.VENG_COOLDOWN.get(entity.player) == 1) {
                player.sendMessage("Vengeance spells may only be cast every 30 seconds.");
                return false;
            }
            if (player.getStats().get(StatType.Defence).currentLevel < 40) {
                player.sendMessage("You need at least 40 defence to cast Vengeance.");
                return false;
            }
            player.resetActions(true, true, false);
            player.animate(6294);
            entity.player.graphics(725, 92, 0);
            player.publicSound(2907, 1, 0);
            entity.player.vengeanceActive = true;
            Config.VENG_COOLDOWN.set(entity.player, 1);
            entity.player.getPacketSender().sendWidget(Widget.VENGEANCE, 30);
            entity.player.addEvent(e -> {
                e.delay(50);
                Config.VENG_COOLDOWN.set(entity.player, 0);
            });
            return true;
        });
    }

}