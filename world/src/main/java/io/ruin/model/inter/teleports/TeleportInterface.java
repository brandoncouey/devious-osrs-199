package io.ruin.model.inter.teleports;

import io.ruin.model.activities.bosses.nex.Nex;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.diaries.ardougne.ArdougneDiaryEntry;
import io.ruin.model.diaries.falador.FaladorDiaryEntry;
import io.ruin.model.diaries.fremennik.FremennikDiaryEntry;
import io.ruin.model.diaries.kandarin.KandarinDiaryEntry;
import io.ruin.model.diaries.kourend.KourendDiaryEntry;
import io.ruin.model.diaries.lumbridge_draynor.LumbridgeDraynorDiaryEntry;
import io.ruin.model.diaries.varrock.VarrockDiaryEntry;
import io.ruin.model.diaries.western.WesternDiaryEntry;
import io.ruin.model.entity.npc.NPCAction;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.PlayerCounter;
import io.ruin.model.inter.InterfaceHandler;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.inter.actions.SimpleAction;
import io.ruin.model.inter.actions.SlotAction;
import io.ruin.model.inter.dialogue.OptionsDialogue;
import io.ruin.model.inter.utils.Option;
import io.ruin.model.map.Bounds;
import io.ruin.model.map.Position;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.ArrayList;

public class TeleportInterface {
    public static final int INTERFACE_ID = 803;

    public static void open(Player player) {
        Bounds tournamentLobby = new Bounds(3106, 3510, 3112, 3518, 2);
        Bounds tournamentLobby1 = new Bounds(3090, 3506, 3102, 3514, 1);
        Bounds duelArena = new Bounds(3325, 3200, 3391, 3286, -1);
        Bounds godwars = new Bounds(2816, 5249, 2943, 5375, -1);
        Bounds tournamentBounds = new Bounds(3564, 8886, 3735, 9165, -1);
        if (player.getPosition().inBounds(tournamentLobby)
                || player.getPosition().inBounds(duelArena) || player.getPosition().inBounds(godwars)
                || player.getPosition().inBounds(tournamentLobby1)) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.getPosition().inBounds(tournamentBounds)) {
            player.sendMessage("You cannot bank while inside a Tournament!");
            return;
        }
        if (player.joinedTournament) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.tournament != null) {
            player.sendMessage("You cannot use this within the tournaments!");
            return;
        }
        if (player.currentInstance != null) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.lmsQueue != null || player.lmsSession != null) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.raidsParty != null) {
            player.sendMessage("You cannot use this whilst in a party.");
            return;
        }
        if (player.fightCaves != null || player.inferno != null) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.wildernessLevel > 20) {
            player.sendMessage("You cannot use this here.");
            return;
        }
        if (player.pvpAttackZone) {
            player.sendMessage("You cannot use this in a PVP zone");
            return;
        }
        if (player.getCombat().isAttacking(10) || player.getCombat().isDefending(10)) {
            player.sendMessage("You cannot use this whilst in combat.");
            return;
        }
        if (player.isInOwnHouse() || player.getCurrentHouse() != null) {
            player.sendMessage("You cannot use this whilst inside a house.");
            return;
        }

        player.openInterface(InterfaceType.MAIN, INTERFACE_ID);

        player.teleportSelectedCategory = -1;

        player.searchTeleports = null;

        sendCategories(player);
        showTeleports(player, 0);
    }

    private static void sendCategories(Player player) {
        int index = 0;
        for (TeleportList.Category cat : TeleportList.categories) {
            player.getPacketSender().sendClientScript(10184, "iiiisi", index, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), 0);

            index++;
        }

        player.getPacketSender().sendClientScript(10186, "iii", (803 << 16) | 7, (803 << 16) | 8, index * 24);
        player.getPacketSender().sendAccessMask(INTERFACE_ID, 7, 0, index * 3, 1 << 1);
    }

    private static void showTeleports(Player player, int category) {
        if (category == player.teleportSelectedCategory && player.searchTeleports == null)
            return;

        player.searchTeleports = null;

        player.getPacketSender().sendClientScript(10187, "i", (803 << 16) | 13);

        TeleportList.Category cat = TeleportList.categories[category];

        if (player.teleportSelectedCategory != -1) {
            TeleportList.Category prev = TeleportList.categories[player.teleportSelectedCategory];

            player.getPacketSender().sendClientScript(10184, "iiiisi", prev.ordinal(), prev.getSpriteId(), prev.getSw(), prev.getSh(), prev.getName(), category);
        }

        player.getPacketSender().sendClientScript(10184, "iiiisi", category, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), category);

        if (category == 0) {
            if (player.favoriteTeleports.isEmpty()) {
                player.getPacketSender().sendString(803, 17, "Your favorite teleports are currently empty.");
            } else {
                for (int i = 0; i < player.favoriteTeleports.size(); i++) {
                    TeleportList.Teleport teleport = TeleportList.getTeleportForId(player.favoriteTeleports.get(i));
                    player.getPacketSender().sendClientScript(10185, "isiiii", i, teleport.getName(), teleport.isMulti() ? 1 : 0, teleport.isWild() ? 1 : 0, 0, player.favoriteTeleports.contains(teleport.getId()) ? 1 : 0);
                }

                player.getPacketSender().sendClientScript(10186, "iii", (803 << 16) | 13, (803 << 16) | 14, player.favoriteTeleports.size() * 24);
                player.getPacketSender().sendAccessMask(803, 13, 0, player.favoriteTeleports.size() * 5, 1 << 1);
                player.getPacketSender().sendString(803, 17, "");
            }
        } else {
            if (cat.getTeleports() == null) {
                return;
            }

            int index = 0;
            for (TeleportList.Teleport teleport : cat.getTeleports()) {
                player.getPacketSender().sendClientScript(10185, "isiiii", index, teleport.getName(), teleport.isMulti() ? 1 : 0, teleport.isWild() ? 1 : 0, 0, player.favoriteTeleports.contains(teleport.getId()) ? 1 : 0);
                index++;
            }

            player.getPacketSender().sendClientScript(10186, "iii", (803 << 16) | 13, (803 << 16) | 14, index * 24);
            player.getPacketSender().sendAccessMask(803, 13, 0, index * 5, 1 << 1);

            if (cat.getTeleports().length == 0) {
                player.getPacketSender().sendString(803, 17, "There are no teleports for this category.");
            } else {
                player.getPacketSender().sendString(803, 17, "");
            }

        }

        player.teleportSelectedCategory = category;
    }

    static void handleTeleport(Player player, int slot) {
        TeleportList.Teleport teleport = getTeleportForSlot(player, slot);

        if (teleport == null)
            return;
        if (teleport.equals(TeleportList.Teleport.ARDOUGNE)) {
            player.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
        }
        if (teleport.equals(TeleportList.Teleport.DONATION_BOSS)) {
            if (!player.isMember()) {
                player.sendMessage("You cannot teleport here without being a member.");
                return;
            }
        }
        if (teleport.equals(TeleportList.Teleport.FALADOR)) {
            player.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_FALADOR);
        }
        if (teleport.equals(TeleportList.Teleport.NEITIZNOT)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TRAVEL_NEITIZNOT);
        }
        if (teleport.equals(TeleportList.Teleport.TROLLHEIM_TROLLS)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TROLLHEIM_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.RELLEKA_SLAYER_CAVE)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.KILL_MARKET_GUARD);
        }
        if (teleport.equals(TeleportList.Teleport.WATERBIRTH_ISLAND)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.WATERBIRTH_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.CAMELOT)) {
            player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.CATHERBY)) {
            player.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CATHERY_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.LUMBRIDGE)) {
            player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.VARROCK)) {
            player.getDiaryManager().getVarrockDiary().progress(VarrockDiaryEntry.TELEPORT_VARROCK);
        }
        if (teleport.equals(TeleportList.Teleport.PEST_CONTROL)) {
            player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.PEST_CONTROL_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.EDGEVILLE)) {
            player.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.EDGEVILLE_TELE);
        }
        if (teleport.equals(TeleportList.Teleport.APE_ATOLL)) {
            player.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.TELEPORT_APE_ATOLL);
        }
        if (teleport.equals(TeleportList.Teleport.CATACOMBS_OF_KOUREND)) {
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.CATACOMBS);
        }
        if (teleport.equals(TeleportList.Teleport.AERIAL_FISHING)) {
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.MOLCH_ISLAND);
        }
        if (teleport.equals(TeleportList.Teleport.GREAT_KOUREND)) {
            player.getDiaryManager().getKourendDiary().progress(KourendDiaryEntry.KOUREND);
        }
        if (teleport.equals(TeleportList.Teleport.TROLLHEIM_TROLLS)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.TROLLHEIM_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.WATERBIRTH_ISLAND)) {
            player.getDiaryManager().getFremennikDiary().progress(FremennikDiaryEntry.WATERBIRTH_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.NEX) && Nex.deadplayers.contains(player)) {
            player.sendMessage("You cannot return to this fight until nex has been killed.");
            return;
        }
        if (teleport.isWild() && player.tpWildWarn) {
            int wildLvl = Wilderness.getLevel(teleport.getPosition());
            player.dialogue(new OptionsDialogue("Are you sure you wish to teleport to level " + wildLvl + " wilderness?", new Option("Yes", () -> {
                teleportFinish(player, teleport.getPosition());
            }), new Option("Yes, don't warn me again.", () -> {
                player.tpWildWarn = false;
                teleportFinish(player, teleport.getPosition());
            }), new Option("No")));
        } else {
            teleportFinish(player, teleport.getPosition());
        }
    }

    static void handleFavorite(Player player, int slot) {
        TeleportList.Teleport teleport = getTeleportForSlot(player, slot);

        if (teleport == null)
            return;

        if (player.favoriteTeleports.contains(teleport.getId())) {
            player.favoriteTeleports.remove((Object) teleport.getId());
            player.sendMessage("You have removed " + teleport.getName() + " from your favorite teleports.");
        } else {
            player.favoriteTeleports.add(teleport.getId());
            player.sendMessage("You have added " + teleport.getName() + " to your favorite teleports.");
        }

        if (player.teleportSelectedCategory == 0) {
            player.teleportSelectedCategory = -1;
            showTeleports(player, 0);
            return;
        }

        player.getPacketSender().sendClientScript(10185, "isiiii", slot, teleport.getName(), teleport.isMulti() ? 1 : 0, teleport.isWild() ? 1 : 0, 0, player.favoriteTeleports.contains(teleport.getId()) ? 1 : 0);
    }

    static TeleportList.Teleport getTeleportForSlot(Player player, int slot) {
        if (player.searchTeleports != null && slot >= 0 && slot < player.searchTeleports.size()) {
            return player.searchTeleports.get(slot);
        } else if (player.teleportSelectedCategory == 0 && slot >= 0 && slot < player.favoriteTeleports.size()) {
            return TeleportList.getTeleportForId(player.favoriteTeleports.get(slot));
        } else {
            if (player.teleportSelectedCategory >= 0 && player.teleportSelectedCategory < TeleportList.categories.length &&
                    slot >= 0 && slot < TeleportList.categories[player.teleportSelectedCategory].getTeleports().length) {
                return TeleportList.categories[player.teleportSelectedCategory].getTeleports()[slot];
            }
        }

        return null;
    }


    private static void teleportFinish(Player player, Position position) {
        player.previousTeleportX = position.getX();
        player.previousTeleportY = position.getY();
        player.previousTeleportZ = position.getZ();
        teleport(player, position);
    }

    public static void teleport(Player player, Position position) {
//        player.resetActions(false, true, true);
        player.closeInterface(InterfaceType.MAIN);
        player.lock(); //keep lock outside of event!
        player.startEvent(e -> {
            player.clearHits();
            player.getPacketSender().fadeOut();
            e.delay(1);
            player.getMovement().teleport(position);
//            player.getPacketSender().clearFade();
            player.lastTeleport = position;
            PlayerCounter.TELEPORT_PORTAL_USES.increment(player, 1);
            player.getPacketSender().fadeIn();
            player.unlock();
        });
    }

    public static void search(Player player, String input) {
        player.searchTeleports = new ArrayList<>();

        for (TeleportList.Teleport t : TeleportList.teleports) {
            if (t.getName().toLowerCase().contains(input.toLowerCase())) {
                player.searchTeleports.add(t);
                continue;
            }
            for (String tag : t.getTags()) {
                if (tag.toLowerCase().contains(input.toLowerCase())) {
                    player.searchTeleports.add(t);
                    break;
                }
            }
        }

        TeleportList.Category cat = TeleportList.categories[player.teleportSelectedCategory];
        player.getPacketSender().sendClientScript(10184, "iiiisi", player.teleportSelectedCategory, cat.getSpriteId(), cat.getSw(), cat.getSh(), cat.getName(), -1);

        player.getPacketSender().sendClientScript(10187, "i", (803 << 16) | 13);

        if (player.searchTeleports.isEmpty()) {
            player.getPacketSender().sendString(803, 17, "No results found!");
        } else {
            for (int i = 0; i < player.searchTeleports.size(); i++) {
                TeleportList.Teleport teleport = player.searchTeleports.get(i);

                player.getPacketSender().sendClientScript(10185, "isiiii", i, teleport.getName(), teleport.isMulti() ? 1 : 0, teleport.isWild() ? 1 : 0, 0, player.favoriteTeleports.contains(teleport.getId()) ? 1 : 0);
            }

            player.getPacketSender().sendString(803, 17, "");
        }

        player.getPacketSender().sendClientScript(10186, "iii", (803 << 16) | 13, (803 << 16) | 14, player.searchTeleports.size() * 24);
        player.getPacketSender().sendAccessMask(803, 13, 0, player.searchTeleports.size() * 5, 1 << 1);
    }

    static {
        NPCAction.register(7046, 1, (player, npc) -> TeleportInterface.open(player));
        NPCAction.register(7046, 2, (player, npc) -> ModernTeleport.teleport(player, player.previousTeleportX, player.previousTeleportY, player.previousTeleportZ));
        InterfaceHandler.register(INTERFACE_ID, h -> {
            h.actions[7] = (SlotAction) (player, slot) -> showTeleports(player, slot / 3);
            h.actions[13] = (SlotAction) (player, slot) -> {
                if ((slot - 1) % 5 == 0) {
                    handleTeleport(player, (slot - 1) / 5);
                } else if ((slot - 2) % 5 == 0) {
                    handleFavorite(player, (slot - 2) / 5);
                }
            };
            h.actions[15] = (SimpleAction) (p) -> {
                p.stringInput("Search teleports (name, npc, location):", s -> {
                    search(p, s);
                });
            };
        });
    }
}
