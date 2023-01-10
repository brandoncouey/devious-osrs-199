package io.ruin.model.skills.construction;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.DefaultAction;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.Getter;
import lombok.Setter;

public class HouseAdvertisement {

    /* housing advertisement interface id */
    public static int INTERFACE_ID = 52;

    @Getter
    @Setter
    public static int advertisingIndex = 0;

    /* handles interface opening */
    public static void open(Player player) {

        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);

        player.getPacketSender().sendClientScript(2524, "ii", -1, -1);

        /*player.getPacketSender().sendClientScript(3110, "isii",
                1, player.getName() +
                        "|1|"
                        + player.getStats().get(StatType.Construction).currentLevel
                        +"|Y|3|3|5|4|Y|27277678|", 1, 10);*/

        player.getPacketSender().sendClientScript(3110, "isii", 1, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 2, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 3, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 4, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 5, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 6, "", 1, 0);

        player.getPacketSender().sendClientScript(3110, "isii", 7, "", 1, 0);

    }


    /* handles adding house to interface */
    public static void handleAddHouse(Player player) {
        player.sendMessage("adding house...");
    }

    /* handles adding and removing of the house via button */
    public static void handleAddRemoveHouse(Player player) {
        player.sendMessage("adding house...");

        player.sendMessage("removing house...");
    }

    /* view last added house via object option */
    public static void viewLastHouse(Player player) {
        player.sendMessage("Viewing last added player owned house...");
    }

    /* refreshing the housing advertisement data */
    public static void refreshHouseData(Player player) {
        player.sendMessage("Refreshing house data...");
    }


    static {

        /* handling of the house advertisement interface */
        InterfaceHandler.register(INTERFACE_ID, h -> {

            h.actions[23] = (DefaultAction) (player, option, slot, itemId) -> handleAddRemoveHouse(player);

            h.actions[30] = (DefaultAction) (player, option, slot, itemId) -> refreshHouseData(player);

        });

        /* handles the object option - view */
        ObjectAction.register(29091, "view", (player, obj) -> {
            HouseAdvertisement.open(player);
        });

        /* handles the object option - add-house */
        ObjectAction.register(29091, "add-house", (player, obj) -> {
            handleAddHouse(player);
        });

        /* handles the object option - view-last */
        ObjectAction.register(29091, "view-last", (player, obj) -> {
            viewLastHouse(player);
        });

    }

}
