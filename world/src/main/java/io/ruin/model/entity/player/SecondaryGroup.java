package io.ruin.model.entity.player;

import io.ruin.api.utils.ListUtils;
import io.ruin.api.utils.StringUtils;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Descending order from highest priority group
 */
public enum SecondaryGroup {
    ZENYTE(200, 17, 53, 75),
    ONYX(190, 17, 52, 75),
    DRAGONSTONE(180, 17, 51, 75),
    DIAMOND(17, 17, 50, 75),
    RUBY(16, 13, 49, 50),
    EMERALD(150, 14, 48, 45),
    SAPPHIRE(140, 15, 47, 40),
    RED_TOPAZ(13, 16, 46, 30),
    JADE(12, 19, 45, 20),
    OPAL(11, 18, 44, 10),
    NONE(2, 0, 0, 0);

    public final int id;

    public final int clientId;

    public final int clientImgId;

    public String title;
    @Getter
    public int doubleDropChance;

    public int dropBonus;

    SecondaryGroup(int id, int clientId, int clientImgId, String title) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.title = title;
    }

    SecondaryGroup(int id, int clientId, int clientImgId, double dropBonus) {
        this.id = id;
        this.clientId = clientId;
        this.clientImgId = clientImgId;
        this.dropBonus = (int) dropBonus;
        this.title = StringUtils.getFormattedEnumName(name());
    }

    SecondaryGroup(int id, int clientId, int clientImgId) {
        this(id, clientId, clientImgId, "");
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

    public void removePKMode(Player player, String type) {
        removePKMode(player, type, null);
    }

    public void removePKMode(Player player, String type, Runnable successAction) {
        CompletableFuture.runAsync(() -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", player.getUserId());
            map.put("type", type);
//            String result = XenPost.post("remove_group", map);
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
        if (player.storeAmountSpent >= 1000) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.DIAMOND.id));
        } else if (player.storeAmountSpent >= 500) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.RUBY.id));
        } else if (player.storeAmountSpent >= 250) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.EMERALD.id));
        } else if (player.storeAmountSpent >= 150) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.SAPPHIRE.id));
        } else if (player.storeAmountSpent >= 100) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.RED_TOPAZ.id));
        } else if (player.storeAmountSpent >= 50) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.JADE.id));
        } else if (player.storeAmountSpent >= 10) {
            player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.OPAL.id));
        }
    }

}