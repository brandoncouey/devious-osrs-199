package io.ruin.model.item.actions.impl;

import io.ruin.api.utils.Random;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.ItemItemAction;
import io.ruin.model.skills.prayer.Ashes;
import io.ruin.model.skills.prayer.Bone;
import io.ruin.model.stat.Stat;
import io.ruin.model.stat.StatType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
public class BoneCrusher {

    private static final int BONE_CRUSHER = 13116;
    private static final int ECTO_TOKEN = 4278;
    private static final int MAX_CHARGE = 20000;
    private Player player;

    public void init(Player player) {
        this.player = player;
    }

    public static void handleBury(Player player, Item item) {
        if (player.boneCrusher && player.getInventory().contains(BONE_CRUSHER) && hasCharges(player)) {
            Bone bone = Bone.get(item.getId());
            if (Objects.nonNull(bone)) {
                player.getStats().addXp(StatType.Prayer, bone.exp / 2, true);
                if (Random.rollDie(50, 1)) {
                    player.getStats().get(StatType.Prayer).restore(1, 0.0);
                    player.getStats().get(StatType.Prayer).alter(player.getStats().get(StatType.Prayer).currentLevel);
                }
                player.boneCrusherChargers--;
                player.sendMessage("Your bone crusher instantly crushes a bone and turns it into XP for you.");
            } else {
                Ashes ash = Ashes.get(item.getId());
                if (Objects.nonNull(ash)) {
                    player.getStats().addXp(StatType.Prayer, ash.exp / 2, true);
                    Stat stat = player.getStats().get(StatType.Prayer);
                    if (Random.rollDie(50, 1)) {
                        stat.restore(1, 0.0);
                        player.getStats().get(StatType.Prayer).alter(stat.currentLevel);
                    }
                    player.boneCrusherChargers--;
                    player.sendMessage("Your bone crusher instantly scatters the ashes and turns them into xp for you.");
                }
            }
        }
    }

    public static void toggleActive(Player player) {
        if (player.boneCrusher) {
            player.sendMessage("Your bonecrusher is now deactivated.");
            player.boneCrusher = false;
        } else {
            player.sendMessage("Your bonecrusher is now activated.");
            player.boneCrusher = true;
        }
    }

    public static boolean hasCharges(Player player) {
        return player.boneCrusherChargers > 0;
    }

    public static void checkCharges(Player player) {
        player.sendMessage("Your bonecrusher has " + player.boneCrusherChargers + " charges remaining.");
    }

    static {
        ItemAction.registerInventory(BONE_CRUSHER, 2, (player, item) -> checkCharges(player));
        ItemAction.registerInventory(BONE_CRUSHER, 3, (player, item) -> toggleActive(player));
        ItemAction.registerInventory(BONE_CRUSHER, 4, (player, item) -> {
            player.getInventory().addOrDrop(ECTO_TOKEN, (player.boneCrusherChargers / 25));
            player.boneCrusherChargers = 0;
            player.sendMessage("You remove all the charges from your bonecrusher.");
        });
        ItemItemAction.register(BONE_CRUSHER, ECTO_TOKEN, (player, primary, secondary) -> {
            int charges = player.boneCrusherChargers;
            player.boneCrusherChargers += (charges + (1000 * 25));
            player.getInventory().remove(secondary.getId(), 1000);
            checkCharges(player);
        });
    }

}
