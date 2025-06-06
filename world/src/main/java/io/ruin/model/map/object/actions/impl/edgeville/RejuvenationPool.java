package io.ruin.model.map.object.actions.impl.edgeville;

import io.ruin.cache.ObjectDef;
import io.ruin.model.World;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
import io.ruin.model.entity.npc.actions.edgeville.Nurse;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.map.object.GameObject;
import io.ruin.model.map.object.actions.ObjectAction;

public class RejuvenationPool {

    private static void drink(Player player, GameObject obj) {
        boolean delayCheck = obj.id != 40004;
        if (delayCheck && System.currentTimeMillis() - player.rejuvenationPool < 1000 * 30 && !player.isADonator()) {
            player.dialogue(new MessageDialogue("You can only drink from the " + ObjectDef.get(obj.id).name + " once every minute."));
            return;
        }
        if (World.isPVPWorld()) {
            if (delayCheck && System.currentTimeMillis() - player.rejuvenationPool < 1000 * 30) {
                player.dialogue(new MessageDialogue("You can only drink from the " + ObjectDef.get(obj.id).name + " once every minute."));
                return;
            }
        }
        player.startEvent(event -> {
            player.lock();
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.FOUNTAIN);
            player.animate(833);
            Nurse.heal(player, null);
            Config.POISONED.set(player, 0);
            player.poisonLevel = 0;
            player.poisonTicks = 0;
            player.poisonDamage = 0;
            player.getStats().restore(true);
            player.getMovement().restoreEnergy(100);
            player.curePoison(1);
            player.cureVenom(1);
            player.getCombat().restore();
            event.delay(1);
            if (delayCheck)
                player.rejuvenationPool = System.currentTimeMillis();
            player.unlock();
        });
    }

    static {
        ObjectAction.register(31380, "drink", RejuvenationPool::drink);
        ObjectAction.register(40848, "drink", RejuvenationPool::drink);
        ObjectAction.register(12941, "drink-from", RejuvenationPool::drink);
        ObjectAction.register(29241, "drink", RejuvenationPool::drink);
        ObjectAction.register(2654, "drink", RejuvenationPool::drink);
    }

}
