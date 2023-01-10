package io.ruin.model.item.actions.impl;

import io.ruin.model.World;
import io.ruin.model.entity.shared.LockType;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.HomeTeleport;

public class RoyalSeedPod {

    static {
        ItemAction.registerInventory(19564, "commune", (player, item) -> {
            player.getMovement().startTeleport(30, event -> {
                player.lock(LockType.FULL_DELAY_DAMAGE);
                player.graphics(767);
                player.animate(4544);
                event.delay(3);
                player.getAppearance().setNpcId(716);
                event.delay(3);
                Position override = HomeTeleport.getHomeTeleportOverride(player);
                if (override != null) {
                    player.getMovement().teleport(override);
                } else {
                    player.getMovement().teleport(3087, 3503, 0);
                }
                event.delay(2);
                player.graphics(769);
                event.delay(2);
                player.getAppearance().setNpcId(-1);
                player.unlock();
            });
        });
    }

}