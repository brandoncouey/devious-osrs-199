package io.ruin.model.skills;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.dialogue.MessageDialogue;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.Item;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.MaxCape;
import io.ruin.model.item.actions.impl.jewellery.RingOfWealth;
import io.ruin.model.skills.magic.SpellBook;
import io.ruin.model.skills.magic.spells.modern.TeleportToHouse;

public class MaxCapePerks {

    /**
     * Max Cape Start
     *
     * @param player
     * @param item
     */


    private static void teleportToCraftingGuild(Player player, Item item) {
        teleport(player, 2933, 3287);
    }

    private static void teleportToBlackChins(Player player, Item item) {
        teleport(player, 3145, 3771);
    }

    private static void teleportToFarmingGuild(Player player, Item item) {
        player.sendMessage("Please note that the farming guild is not operated at this time.");
        teleport(player, 1249, 3725);
    }

    private static void teleportToFishingGuild(Player player, Item item) {
        teleport(player, 2590, 3417);
    }

    private static void teleportToOttosGrotto(Player player, Item item) {
        teleport(player, 2505, 3488);
    }

    private static void teleportToWarriorsGuild(Player player, Item item) {
        teleport(player, 2875, 3546);
    }

    public static boolean hasMaxCapeAndReqs(Player player) {
        return MaxCape.unlocked(player) && player.getInventory().contains(new Item(13342)) || player.getEquipment().contains(new Item(13342));
    }

    private static void maxCapeTeleports(Player player, Item item) {
        if (!MaxCape.unlocked(player)) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " fails to function as you no longer meet the requirements to wear it.");
            return;
        } else {
            OptionScroll.open(player, "Max cape teleports",
                    new Option("POH Portals", () -> houseDestinations(player, item)),
                    new Option("Teleport to Crafting guild", () -> teleportToCraftingGuild(player, item)),
                    new Option("Teleport to Farming guild", () -> teleportToFarmingGuild(player, item)),
                    new Option("Teleport to Fishing guild", () -> teleportToFishingGuild(player, item)),
                    new Option("Teleport to Otto's grotto", () -> teleportToOttosGrotto(player, item))
            );
        }
    }

    private static void othetTeleports(Player player, Item item) {
        if (!MaxCape.unlocked(player)) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " fails to function as you no longer meet the requirements to wear it.");
            return;
        } else {
            player.dialogue(new OptionsDialogue("Choose a Destination",
                    new Option("Teleport to Black Chinchompas", () -> teleportToBlackChins(player, item)),
                    new Option("Teleport to Crafting guild", () -> teleportToCraftingGuild(player, item)),
                    new Option("Teleport to Farming guild", () -> teleportToFarmingGuild(player, item)),
                    new Option("Teleport to Fishing guild", () -> teleportToFishingGuild(player, item)),
                    new Option("POH Portals", () -> player.dialogue(new OptionsDialogue("Choose a Destination",
                            new Option("Rimmington", () -> teleport(player, 2954, 3224)),
                            new Option("Taverley", () -> teleport(player, 2894, 3465)),
                            new Option("Pollnivneach", () -> teleport(player, 3340, 3004)),
                            new Option("Hosidius", () -> teleport(player, 1743, 3517)),
                            new Option("More...", () -> {
                                player.dialogue(new OptionsDialogue("Choose a Destination",
                                        new Option("Rellekka", () -> teleport(player, 2670, 3632)),
                                        new Option("Brimhaven", () -> teleport(player, 2758, 3178)),
                                        new Option("Yanille", () -> teleport(player, 2544, 3095)),
                                        new Option("Prifddinas", () -> teleport(player, 3239, 6076))
                                ));
                            })
                    )))));
        }
    }

    private static void maxCapeFeatures(Player player, Item item) {
        if (!MaxCape.unlocked(player)) {
            player.sendMessage("<col=aa0000>Your " + item.getDef().name + " fails to function as you no longer meet the requirements to wear it.");
            return;
        } else {
            OptionScroll.open(player, "Max cape features",
                    new Option("Toggle ring of wealth effect", () -> houseDestinations(player, item)),
                    new Option("Switch spell-books", () -> maxSpellBook(player, item)),
                    new Option("Toggle Coins collection (currently: " + (player.ROWAutoCollectGold ? "enabled" : "disabled") + ")", () -> RingOfWealth.toggleCoinCollect(player)),
                    new Option("Toggle Blood money collection (currently: " + (player.ROWAutoCollectBloodMoney ? "enabled" : "disabled") + ")", () -> RingOfWealth.toggleBMCollect(player)),
                    new Option("Toggle Ether collection (currently: " + (player.ROWAutoCollectEther ? "enabled" : "disabled") + ")", () -> RingOfWealth.toggleEtherCollect(player)),
                    new Option("Warriors guild", () -> teleportToWarriorsGuild(player, item))
            );
        }
    }

    public static void switchBook(Player player, SpellBook book) {
        if (book.isActive(player)) {
            player.dialogue(new MessageDialogue("You're already using this spellbook."));
            return;
        }
        book.setActive(player);
        player.sendMessage("You are now using the " + book.name + " spellbook.");
    }

    private static void maxSpellBook(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a book",
                new Option("Modern", () -> switchBook(player, SpellBook.MODERN)),
                new Option("Ancient", () -> switchBook(player, SpellBook.ANCIENT)),
                new Option("Lunar", () -> switchBook(player, SpellBook.LUNAR)),
                new Option("Arceuus", () -> switchBook(player, SpellBook.ARCEUUS))
        ));
    }

    private static void teleport(Player player, int x, int y) {
        player.getMovement().startTeleport(e -> {
            player.animate(714);
            player.graphics(111, 92, 0);
            player.publicSound(200);
            e.delay(3);
            player.getMovement().teleport(x, y, 0);
        });
    }

    private static void teleToHouse(Player player, Item item) {
        TeleportToHouse.teleport(player, 0);
    }

    private static void houseDestinations(Player player, Item item) {
        player.dialogue(new OptionsDialogue("Choose a Destination",
                new Option("Rimmington", () -> teleport(player, 2954, 3224)),
                new Option("Taverley", () -> teleport(player, 2894, 3465)),
                new Option("Pollnivneach", () -> teleport(player, 3340, 3004)),
                new Option("Hosidius", () -> teleport(player, 1743, 3517)),
                new Option("More...", () -> {
                    player.dialogue(new OptionsDialogue("Choose a Destination",
                            new Option("Rellekka", () -> teleport(player, 2670, 3632)),
                            new Option("Brimhaven", () -> teleport(player, 2758, 3178)),
                            new Option("Yanille", () -> teleport(player, 2544, 3095)),
                            new Option("Prifddinas", () -> teleport(player, 3239, 6076))
                    ));
                })
        ));
    }

    static {
        //inventory
        ItemAction.registerInventory(13342, "Teleports", MaxCapePerks::maxCapeTeleports);
        ItemAction.registerInventory(13342, "Features", MaxCapePerks::maxCapeFeatures);
        //equip
        ItemAction.registerEquipment(13342, "Tele to POH", (player, item) -> teleToHouse(player, item));
        ItemAction.registerEquipment(13342, "Other Teleports", (player, item) -> othetTeleports(player, item));
        ItemAction.registerEquipment(13342, "Spellbook", (player, item) -> maxSpellBook(player, item));
        ItemAction.registerEquipment(13342, "Features", MaxCapePerks::maxCapeFeatures);
    }
}
