package io.ruin.model.achievements;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.teleports.TeleportInterface;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.map.Position;

/**
 * @author Greco
 * @since 12/02/2021
 */
public class DiaryMasters {

    /**
     * builds the diary masters interface with option handling
     **/
    public static void openDiaryMasters(Player player) {
        OptionScroll.open(player, "Diary Masters",
                new Option("Ardougne: Two-pints", () -> teleportPlayerToDiaryMaster(player, new Position(2574, 3324, 0))),
                new Option("Desert: Jarr", () -> teleportPlayerToDiaryMaster(player, new Position(3299, 3123, 0))),
                new Option("Falador: Sir Rebral", () -> teleportPlayerToDiaryMaster(player, new Position(2976, 3343, 0))),
                new Option("Fremennik: Thorodin", () -> teleportPlayerToDiaryMaster(player, new Position(2660, 3627, 0))),
                new Option("Kandarin: The 'Wedge'", () -> teleportPlayerToDiaryMaster(player, new Position(2758, 3475, 0))),
                new Option("Karamja: Pirate Jackie the Fruit", () -> teleportPlayerToDiaryMaster(player, new Position(2810, 3188, 0))),
                new Option("Kourend & Kebos: Elise", () -> teleportPlayerToDiaryMaster(player, new Position(1646, 3666, 0))),
                new Option("Lumbridge & Draynor: Hatius Cosaintus", () -> teleportPlayerToDiaryMaster(player, new Position(3236, 3217, 0))),
                new Option("Morytania: Le-sabre", () -> teleportPlayerToDiaryMaster(player, new Position(3465, 3476, 0))),
                new Option("Varrock: Toby", () -> teleportPlayerToDiaryMaster(player, new Position(3211, 3422, 0))),
                new Option("Wilderness: Lesser Fanatic", () -> teleportPlayerToDiaryMaster(player, new Position(3116, 3517, 0))),
                new Option("Western Provence: Elder Gnome child", () -> teleportPlayerToDiaryMaster(player, new Position(2467, 3460, 0))),
                new Option("Twiggy O'Korn", () -> teleportPlayerToDiaryMaster(player, new Position(3096, 3226, 0)))
        );

        return;
    }

    /**
     * builds the diary masters interface with buttons and used for skillcape options
     **/
    public static void openDiaryMastersViaItem(Player player, Item item) {
        if (item.getId() == 13069 || item.getId() == 19476) {
            OptionScroll.open(player, "Diary Masters",
                    new Option("Ardougne: Two-pints", () -> teleportPlayerToDiaryMaster(player, new Position(2574, 3324, 0))),
                    new Option("Desert: Jarr", () -> teleportPlayerToDiaryMaster(player, new Position(3299, 3123, 0))),
                    new Option("Falador: Sir Rebral", () -> teleportPlayerToDiaryMaster(player, new Position(2976, 3343, 0))),
                    new Option("Fremennik: Thorodin", () -> teleportPlayerToDiaryMaster(player, new Position(2660, 3627, 0))),
                    new Option("Kandarin: The 'Wedge'", () -> teleportPlayerToDiaryMaster(player, new Position(2758, 3475, 0))),
                    new Option("Karamja: Pirate Jackie the Fruit", () -> teleportPlayerToDiaryMaster(player, new Position(2810, 3188, 0))),
                    new Option("Kourend & Kebos: Elise", () -> teleportPlayerToDiaryMaster(player, new Position(1646, 3666, 0))),
                    new Option("Lumbridge & Draynor: Hatius Cosaintus", () -> teleportPlayerToDiaryMaster(player, new Position(3236, 3217, 0))),
                    new Option("Morytania: Le-sabre", () -> teleportPlayerToDiaryMaster(player, new Position(3465, 3476, 0))),
                    new Option("Varrock: Toby", () -> teleportPlayerToDiaryMaster(player, new Position(3211, 3422, 0))),
                    new Option("Wilderness: Lesser Fanatic", () -> teleportPlayerToDiaryMaster(player, new Position(3116, 3517, 0))),
                    new Option("Western Provence: Elder Gnome child", () -> teleportPlayerToDiaryMaster(player, new Position(2467, 3460, 0))),
                    new Option("Twiggy O'Korn", () -> teleportPlayerToDiaryMaster(player, new Position(3096, 3226, 0)))
            );
            return;
        } else {
            player.sendMessage("Closing Diary Masters Interface, Achievement Diary cape not active.");
        }

        return;
    }

    /**
     * teleports the player to the diary master position
     **/
    public static void teleportPlayerToDiaryMaster(Player player, Position position) {
        TeleportInterface.teleport(player, new Position(position.getX(), position.getY(), position.getZ()));
    }

    static {

        //TODO FIX THIS
        ItemAction.registerInventory(13069, "teleport", (player, item) -> openDiaryMastersViaItem(player, item));
        ItemAction.registerInventory(19476, "teleport", (player, item) -> openDiaryMastersViaItem(player, item));
    }

}
