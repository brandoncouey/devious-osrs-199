package io.ruin.model.devtools;

import io.ruin.cache.NPCDef;
import io.ruin.data.DataFile;
import io.ruin.data.impl.items.item_info;
import io.ruin.data.impl.items.shield_types;
import io.ruin.data.impl.items.weapon_types;
import io.ruin.data.impl.npcs.npc_combat;
import io.ruin.data.impl.npcs.npc_drops;
import io.ruin.data.impl.npcs.npc_spawns;
import io.ruin.data.yaml.YamlLoader;
import io.ruin.data.yaml.impl.ShopLoader;
import io.ruin.model.World;
import io.ruin.model.activities.raids.party.Party;
import io.ruin.model.activities.raids.xeric.ChambersOfXeric;
import io.ruin.model.activities.raids.xeric.chamber.Chamber;
import io.ruin.model.activities.raids.xeric.chamber.ChamberDefinition;
import io.ruin.model.entity.npc.NPC;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerFile;
import io.ruin.model.inter.handlers.OptionScroll;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.item.actions.ItemAction;
import io.ruin.model.item.actions.impl.ItemBreaking;
import io.ruin.model.item.actions.impl.ItemUpgrading;
import io.ruin.model.map.Position;
import io.ruin.model.map.dynamic.DynamicMap;
import io.ruin.model.skills.construction.actions.Costume;
import io.ruin.model.skills.construction.actions.CostumeStorage;
import io.ruin.services.LatestUpdate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RottenPotato {


    static {
        ItemAction.registerInventory(5733, "eat", (player, item) -> {
            if (!player.isAdmin()) {
                player.getInventory().remove(5733, 1);
                //Punishment.ban(player, player);
                player.sendMessage("Too late it's gone.");
                return;
            }

            return;
        });
    }

    /**
     * World Configuration
     */

    public static void toggleBloodMoney() {
        if (World.bmMultiplier == 1) {
            World.boostBM(2);
        }
        if (World.bmMultiplier == 2) {
            World.boostBM(1);
        }
    }

    public static void saveAllPlayers(Player player) {
        player.sendMessage("Saving online players...");
        for (Player p : World.players)
            PlayerFile.save(p, -1);
        player.sendMessage("DONE!");
    }

    static {
        ItemAction.registerInventory(5733, "slice", (player, item) -> {
            if (!player.isAdmin()) {
                player.getInventory().remove(5733, 1);
//                Punishment.ban(player, player);
                player.sendMessage("Too late it's gone.");
                return;
            }
            OptionScroll.open(player, "Configure Game Server",
                    new Option("Double drops - State: " + (World.doubleDrops ? "enabled" : "disabled") + "", () -> World.toggleDoubleDrops()),
                    new Option("Double PKP - State: " + (World.doublePkp ? "enabled" : "disabled") + "", () -> World.toggleDoublePkp()),
                    new Option("Double Slayer - State: " + (World.doubleSlayer ? "enabled" : "disabled") + "", () -> World.toggleDoubleSlayer()),
                    // new Option("Double Pc Points - State: " + (World.doublePest ? "enabled" : "disabled") + "", () -> World.toggleDoublePest()),
                    new Option("Weekend XP (50%)- State: " + (World.weekendExpBoost ? "enabled" : "disabled") + "", () -> World.toggleWeekendExpBoost()),
                    new Option("Double Blood Money - State: " + (World.bmMultiplier > 1 ? "enabled" : "disabled") + "", () -> toggleBloodMoney()),
                    new Option("DMM Key Event - State: " + (World.wildernessDeadmanKeyEvent ? "on" : "off") + "", () -> World.toggleWildernessKeyEvent()),
                    //  new Option("Staff Bounty (PvP Event) - State: " + (StaffBounty.EVENT_ACTIVE ? "on" : "off") + "", () -> toggleStaffBounty(player)),
                    new Option("Start 30 Minute update timer", () -> World.update(30)),
                    new Option("Start 5 Minute update timer", () -> World.update(5)),
                  //    new Option("Fetch Update", () -> LatestUpdate.fetch()),
                    new Option("Save Players", () -> saveAllPlayers(player))
            );
            return;
        });
    }

    /**
     * Peel Options
     */

    public static void toggleDebugMode(Player player) {
        player.sendMessage("Debug: " + ((player.debug = !player.debug) ? "ON" : "OFF"));
    }


    public static void giveItemUpgrades(Player player) {
        for (ItemUpgrading upgrade : ItemUpgrading.values()) {
            player.getInventory().add(upgrade.upgradeId, 1);
        }
    }

    public static void giveBreakableItems(Player player) {
        for (ItemBreaking breaking : ItemBreaking.values()) {
            player.getInventory().add(breaking.fixedId, 1);
        }
    }

    public static void fillCostumRoom(Player player) {
        for (CostumeStorage s : CostumeStorage.values()) {
            Map<Costume, int[]> stored = s.getSets(player);
            stored.clear();
        }
    }

    public static void clearCostumRoom(Player player) {
        for (CostumeStorage s : CostumeStorage.values()) {
            Map<Costume, int[]> stored = s.getSets(player);
            stored.clear();
        }
    }

    public static void testRaidRoom(Player player) {
        int rotation = 0;
        int layout = 0;
        int finalRotation = rotation;
        int finalLayout = layout;
        Consumer<ChamberDefinition> run = definition -> {
            Chamber chamber = definition.newChamber();
            if (chamber == null) {
                player.sendMessage("Failed to generate room");
                return;
            }
            ChambersOfXeric raid = new ChambersOfXeric();
            Party party = new Party(player);
            player.raidsParty = party;
            raid.setParty(party);
            party.setRaid(raid);
            chamber.setRaid(raid);
            chamber.setRotation(finalRotation);
            chamber.setLayout(finalLayout);
            chamber.setLocation(0, 0, 0);
            DynamicMap map = new DynamicMap().build(chamber.getChunks());
            raid.setMap(map);
            chamber.setBasePosition(new Position(map.swRegion.baseX, map.swRegion.baseY, 0));
            chamber.onBuild();
            chamber.onRaidStart();
            party.addPoints(50_000);
            player.getMovement().teleport(chamber.getPosition(30, 25));
        };
        OptionScroll.open(player, "Select a room type",
                Arrays.stream(ChamberDefinition.values()).map(cd -> new Option(cd.getName(), () -> run.accept(cd))).collect(Collectors.toList()));
    }

    public static void testTut(Player player) {
        player.newPlayer = true;
    }


    static {
        ItemAction.registerInventory(5733, "peel", (player, item) -> {
            if (!player.isAdmin()) {
                player.getInventory().remove(5733, 1);
                //Punishment.ban(player, player);
                player.sendMessage("Too late it's gone.");
                return;
            }
            OptionScroll.open(player, "Configure Character",
                    new Option("Debug Mode - State: " + (player.debug ? "enabled" : "disabled") + "", () -> toggleDebugMode(player)),
                    //         new Option("Unlock skins", () -> unlockCharacterSkins(player)),
                    new Option("Give Item Upgrades", () -> giveItemUpgrades(player)),
                    new Option("Give breakables", () -> giveBreakableItems(player)),
                    new Option("Fill Costume Room", () -> fillCostumRoom(player)),
                    new Option("Empty Costume Room", () -> clearCostumRoom(player)),
                    new Option("Test Raids Room", () -> testRaidRoom(player)),
                    new Option("Test Tutourial", () -> testTut(player)),
                    new Option("Bank", () -> player.getBank().open())
            );
            return;
        });
    }



    /**
     * Definitions
     */


    public static void refreshItemDefinitions(Player player) {
        new Thread(() -> {
            player.sendMessage("Reloading item info...");
            DataFile.reload(player, shield_types.class);
            DataFile.reload(player, weapon_types.class);
            DataFile.reload(player, item_info.class);
            player.sendMessage("Done!");
        }).start();
    }

    public static void refreshNpcCombatDefs(Player player) {
        new Thread(() -> {
            player.sendMessage("Reloading NPC Definitions");
            DataFile.reload(player, npc_combat.class);
        }).start();
    }

    public static void refreshNpcSpawns(Player player) {
        World.npcs.forEach(NPC::remove); //todo fix this
        DataFile.reload(player, npc_spawns.class);
    }

    public static void refreshNpcDrops(Player player) {
        NPCDef.forEach(def -> def.lootTable = null);
        DataFile.reload(player, npc_drops.class);
    }

    public static void refreshShops(Player player) {
        YamlLoader.load(Collections.singletonList(new ShopLoader()));
    }

    public static void reloadAll(Player player) {
        refreshItemDefinitions(player);
        refreshNpcCombatDefs(player);
        refreshNpcSpawns(player);
        refreshNpcDrops(player);
        refreshShops(player);
    }

    static {
        ItemAction.registerInventory(5733, "mash", (player, item) -> {
            if (!player.isAdmin()) {
                player.getInventory().remove(5733, 1);
                //Punishment.ban(player, player);
                player.sendMessage("Too late it's gone.");
                return;
            }
            OptionScroll.open(player, "Game Server Definitions",
                    new Option("Reload Item Definitions", () -> refreshItemDefinitions(player)),
                    new Option("Refresh NPC Combat Definitions", () -> refreshNpcCombatDefs(player)),
                    new Option("Refresh NPC Spawns Definitions", () -> refreshNpcSpawns(player)),
                    new Option("Refresh Drop Definitions", () -> refreshNpcDrops(player)),
                    new Option("Reload all", () -> reloadAll(player))
            );
            return;
        });
    }
}
