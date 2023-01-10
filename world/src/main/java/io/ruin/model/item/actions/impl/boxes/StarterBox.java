package io.ruin.model.item.actions.impl.boxes;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.XpMode;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.containers.Equipment;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.stat.StatType;

import static io.ruin.cache.ItemID.COINS_995;
import static io.ruin.model.entity.player.XpMode.REALISTIC;
import static io.ruin.model.entity.player.XpMode.EASY;

public class StarterBox {

    /*static {
        ItemAction.registerInventory(12955, 1, (player, item) -> {
            player.getBank().add(COINS_995, 10000); // gp
            player.getBank().add(558, 500); // Mind Rune
            player.getBank().add(556, 1500); // Air Rune
            player.getBank().add(554, 1000); // Fire Rune
            player.getBank().add(555, 1000); // Water Rune
            player.getBank().add(557, 1000); // Earth Rune
            player.getBank().add(562, 1000); // Chaos Rune
            player.getBank().add(560, 500); // Death Rune
            player.getBank().add(1381, 1); // Air Staff
            player.getBank().add(362, 50); // Tuna
            player.getBank().add(863, 300); // Iron Knives
            player.getBank().add(867, 150); // Adamant Knives
            player.getBank().add(1169, 1); // Coif
            player.getBank().add(1129, 1); // Leather body
            player.getBank().add(1095, 1); // Leather Chaps
            player.getBank().add(13385, 1); // Xeric Hat
            player.getBank().add(12867, 1); // Blue d hide set
            player.getBank().add(13024, 1); // Rune set
            player.getBank().add(13387, 1); // Xerican Top
            player.getBank().add(1323, 1); // Iron scim
            player.getBank().add(1333, 1); // Rune scim
            player.getBank().add(4587, 1); // Dragon Scim
            if (XpMode.isXpMode(player, REALISTIC))
                player.getBank().add(30416, 1);
            switch (player.getGameMode()) {
                case IRONMAN:
                    player.getBank().add(12810, 1);
                    player.getBank().add(12811, 1);
                    player.getBank().add(12812, 1);
                    break;
                case ULTIMATE_IRONMAN:
                    player.getBank().add(12813, 1);
                    player.getBank().add(12814, 1);
                    player.getBank().add(12815, 1);
                    break;
                case HARDCORE_IRONMAN:
                    player.getBank().add(20792, 1);
                    player.getBank().add(20794, 1);
                    player.getBank().add(20796, 1);
                    player.getBank().add(25481, 1);
                    break;
                case GROUP_IRONMAN:
                    player.getBank().add(26156, 1);
                    player.getBank().add(26158, 1);
                    player.getBank().add(26166, 1);
                    player.getBank().add(26168, 1);
                    player.getMovement().teleport(3105, 3028, 0);
                    break;
                case HARDCORE_GROUP_IRONMAN:
                    player.getBank().add(26170, 1);
                    player.getBank().add(26172, 1);
                    player.getBank().add(26180, 1);
                    player.getBank().add(26182, 1);
                    player.getBank().add(25478, 1);
                    player.getMovement().teleport(3105, 3028, 0);
                    break;
                case STANDARD:
                    player.getBank().add(COINS_995, 115000);
                    break;
            }
            if (Random.get(1, 50) == 50) {
                player.getBank().add(290, 1);
                player.sendMessage("You feel a luxurious presence in your bank!");
            }
            item.remove();
        };
      });
}*/
}
