package io.ruin.model.entity.player;

import io.ruin.api.utils.ListUtils;
import io.ruin.api.utils.StringUtils;
import io.ruin.cache.Color;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public enum SecondaryGroup {
    NONE(2, 0, 0, 0, "", 25),
    OPAL(11, 18, 43, 10, "<shad=000000><col=" + Color.OPAL + ">", 50),
    JADE(12, 19, 44, 20, "<shad=000000><col=" + Color.JADE + ">", 150),
    RED_TOPAZ(13, 16, 45, 30, "<shad=000000><col=" + Color.REDTOPAZ + ">", 350),
    SAPPHIRE(140, 15, 46, 40, "<shad=000000><col=" + Color.BLACK + ">", 700),
    EMERALD(150, 14, 47, 45, "<shad=000000><col=" + Color.GREEN + ">", 1500),
    RUBY(16, 13, 48, 50, "<shad=000000><col=" + Color.RED + ">", 2450),
    DIAMOND(17, 17, 49, 75, "<shad=000000><col=" + Color.WHITE + ">", 3500),
    DRAGONSTONE(180, 180, 53, 75, "<shad=000000><col=" + Color.PURPLE + ">", 4750),
    ONYX(190, 190, 54, 75, "<shad=000000><col=" + Color.BLACK + ">", 6500),
    ZENYTE(200, 200, 55, 75, "<shad=000000><col=" + Color.ORANGE + ">", 8250),
    ;

    public final int id;

    public final int clientId;

    public final int clientImgId;

    @Getter public final String color;

    public String title;
    @Getter
    public int doubleDropChance;

    public int dropBonus;

    public int dontationRequired;

    SecondaryGroup(int id, int clientId, int clientImgId, double dropBonus, String color, int donationRequired) {
        this(id, clientId, clientImgId, dropBonus, color);
        this.dontationRequired = donationRequired;
    }

    SecondaryGroup(int id, int clientId, int clientImgId, double dropBonus, String color) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.dropBonus = (int) dropBonus;
        this.title = StringUtils.getFormattedEnumName(name());
        this.color = color;
    }


    public void sync(Player player, String type) {
        sync(player, type, null);
    }

    public void sync(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
            map.put("groupId", id);
//            String result = XenPost.post("add_group", map);
//            if(successAction != null && "1".equals(result))
//                successAction.run();
        });
    }


    public String tag() {
        return "<img=" + clientImgId + ">";
    }

    public static final SecondaryGroup[] GROUPS_BY_ID;

    static {
        int highestGroupId = 0;
        for (SecondaryGroup group : values()) {
            if (group.id > highestGroupId)
                highestGroupId = group.id;
        }
        GROUPS_BY_ID = new SecondaryGroup[highestGroupId + 1];
        for (SecondaryGroup group : values())
            GROUPS_BY_ID[group.id] = group;
    }

    public static void getGroup(Player player) {
        if (player.amountDonated >= 1000) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.DIAMOND.id));
        } else if (player.amountDonated >= 500) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.RUBY.id));
        } else if (player.amountDonated >= 250) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.EMERALD.id));
        } else if (player.amountDonated >= 150) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.SAPPHIRE.id));
        } else if (player.amountDonated >= 100) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.RED_TOPAZ.id));
        } else if (player.amountDonated >= 50) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.JADE.id));
        } else if (player.amountDonated >= 10) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.OPAL.id));
        }
    }

}