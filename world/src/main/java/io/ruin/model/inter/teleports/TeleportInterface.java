package io.ruin.model.inter.teleports;

import io.ruin.model.activities.bosses.nex.Nex;
import io.ruin.model.activities.wilderness.Wilderness;
import io.ruin.model.diaries.pvm.PvMDiaryEntry;
import io.ruin.model.diaries.pvp.PvPDiaryEntry;
import io.ruin.model.diaries.skilling.SkillingDiaryEntry;
import io.ruin.model.diaries.devious.DeviousDiaryEntry;
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
import io.ruin.model.map.object.actions.ObjectAction;
import io.ruin.model.skills.magic.spells.modern.ModernTeleport;

import java.util.ArrayList;

public class TeleportInterface {
    public static final int INTERFACE_ID = 1039;

    public static final int BUTTON_OFFSET = 9;

    public static final int TELEPORTS = 21;

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
        if (player.lastTeleport1 != null) {
            player.getPacketSender().sendString(1039, 42, player.lastTeleport1.getName());
        }
        if (player.lastTeleport2 != null) {
            player.getPacketSender().sendString(1039, 43, player.lastTeleport2.getName());
        }
        if (player.lastTeleport3 != null) {
            player.getPacketSender().sendString(1039, 44, player.lastTeleport3.getName());
        }
        showTeleports(player, 1);
    }

    private static void showTeleports(Player player, int category) {
        if (category == player.teleportSelectedCategory && player.searchTeleports == null)
            return;

        player.searchTeleports = null;

        TeleportList.Category cat = TeleportList.categories[category];

        if (cat.getTeleports() == null) {
            return;
        }
        for (int index = 0; index < 21; index++) {
            player.getPacketSender().setHidden(INTERFACE_ID, (53 + (index * 5)), true);
        }

        int teleports = 0;
        if (category == 0) {
            for (int i = 0; i < player.favoriteTeleports.size(); i++) {
                TeleportList.Teleport teleport = TeleportList.getTeleportForId(player.favoriteTeleports.get(i));
                if (teleport == null) continue;
                player.getPacketSender().setHidden(INTERFACE_ID, (53 + (teleports * 5)), false);
                player.getPacketSender().sendString(INTERFACE_ID, (55 + (teleports * 5)), (teleport.isWild() ? "<col=ff0000>" : "") + teleport.getName());
                player.getPacketSender().setHidden(INTERFACE_ID, (57 + (teleports * 5)), true);
                teleports++;
            }
        } else {

            for (TeleportList.Teleport teleport : cat.getTeleports()) {
                player.getPacketSender().setHidden(INTERFACE_ID, (53 + (teleports * 5)), false);
                player.getPacketSender().sendString(INTERFACE_ID, (55 + (teleports * 5)), (teleport.isWild() ? "<col=ff0000>" : "") + teleport.getName());
                player.getPacketSender().setHidden(INTERFACE_ID, (57 + (teleports * 5)), player.favoriteTeleports.contains(teleport.getId()));
                teleports++;
            }
        }
        player.teleportSelectedCategory = category;
    }


    static void handleTeleport(Player player, int slot) {
        TeleportList.Teleport teleport = getTeleportForSlot(player, slot);

        if (teleport == null)
            return;

        TeleportList.Teleport lastTeleport1 = player.lastTeleport1;
        TeleportList.Teleport lastTeleport2 = player.lastTeleport2;
        if (player.lastTeleport1 != teleport) {
            player.lastTeleport1 = teleport;
            if (player.lastTeleport2 != lastTeleport1)
                player.lastTeleport2 = lastTeleport1;
            if (player.lastTeleport3 != lastTeleport2)
                player.lastTeleport3 = lastTeleport2;
        }


        if (teleport.equals(TeleportList.Teleport.ARDOUGNE)) {
            player.getDiaryManager().getPvpDiary().progress(PvPDiaryEntry.TELEPORT_ARDOUGNE);
        }

        if (teleport.equals(TeleportList.Teleport.DONATION_BOSS)) {
            if (!player.isADonator()) {
                player.sendMessage("You cannot teleport here without being a donator.");
                return;
            }
        }
        if (teleport.equals(TeleportList.Teleport.FALADOR)) {
            player.getDiaryManager().getSkillingDiary().progress(SkillingDiaryEntry.TELEPORT_FALADOR);
        }
        if (teleport.equals(TeleportList.Teleport.NEITIZNOT)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.TRAVEL_NEITIZNOT);
        }
        if (teleport.equals(TeleportList.Teleport.TROLLHEIM_TROLLS)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.TROLLHEIM_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.RELLEKA_SLAYER_CAVE)) {
            player.getDiaryManager().getPvmDiary().progress(PvMDiaryEntry.KILL_MARKET_GUARD);
        }
        if (teleport.equals(TeleportList.Teleport.WATERBIRTH_ISLAND)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.WATERBIRTH_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.CAMELOT)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CAMELOT_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.CATHERBY)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CATHERY_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.LUMBRIDGE)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.LUMBRIDGE_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.VARROCK)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.TELEPORT_VARROCK);
        }
        if (teleport.equals(TeleportList.Teleport.PEST_CONTROL)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.PEST_CONTROL_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.EDGEVILLE)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.EDGEVILLE_TELE);
        }
        if (teleport.equals(TeleportList.Teleport.APE_ATOLL)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.TELEPORT_APE_ATOLL);
        }
        if (teleport.equals(TeleportList.Teleport.CATACOMBS_OF_KOUREND)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.CATACOMBS);
        }
        if (teleport.equals(TeleportList.Teleport.AERIAL_FISHING)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.MOLCH_ISLAND);
        }
        if (teleport.equals(TeleportList.Teleport.GREAT_KOUREND)) {
            //player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.KOUREND);
        }
        if (teleport.equals(TeleportList.Teleport.TROLLHEIM_TROLLS)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.TROLLHEIM_TELEPORT);
        }
        if (teleport.equals(TeleportList.Teleport.WATERBIRTH_ISLAND)) {
            player.getDiaryManager().getDeviousDiary().progress(DeviousDiaryEntry.WATERBIRTH_TELEPORT);
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
        TeleportList.Category cat = TeleportList.categories[player.teleportSelectedCategory];
        int teleports = 0;
        for (TeleportList.Teleport t : cat.getTeleports()) {
            player.getPacketSender().setHidden(INTERFACE_ID, (53 + (teleports * 5)), false);
            player.getPacketSender().sendString(INTERFACE_ID, (55 + (teleports * 5)), (t.isWild() ? "<col=ff0000>" : "") + t.getName());
            player.getPacketSender().setHidden(INTERFACE_ID, (57 + (teleports * 5)), player.favoriteTeleports.contains(t.getId()));
            teleports++;
        }

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

        for (int index = 0; index < 21; index++) {
            player.getPacketSender().setHidden(INTERFACE_ID, (53 + (index * 5)), true);
        }

        int teleports = 0;
        for (TeleportList.Teleport teleport : player.searchTeleports) {
            player.getPacketSender().setHidden(INTERFACE_ID, (53 + (teleports * 5)), false);
            player.getPacketSender().sendString(INTERFACE_ID, (55 + (teleports * 5)), (teleport.isWild() ? "<col=ff0000>" : "") + teleport.getName());
            teleports++;
        }

    }

    static {
        ObjectAction.register(35075, "Use", (player, obj) -> TeleportInterface.open(player));
        NPCAction.register(7046, 1, (player, npc) -> TeleportInterface.open(player));
        NPCAction.register(7046, 2, (player, npc) -> ModernTeleport.teleport(player, player.previousTeleportX, player.previousTeleportY, player.previousTeleportZ));
        InterfaceHandler.register(INTERFACE_ID, h -> {
            h.actions[16] = (SlotAction) (player, slot) -> showTeleports(player, 0);//Favourites
            h.actions[19] = (SlotAction) (player, slot) -> showTeleports(player, 1);//Cities
            h.actions[22] = (SlotAction) (player, slot) -> showTeleports(player, 2);//Minigames
            h.actions[25] = (SlotAction) (player, slot) -> showTeleports(player, 3);//Skilling
            h.actions[28] = (SlotAction) (player, slot) -> showTeleports(player, 4);//Monsters
            h.actions[31] = (SlotAction) (player, slot) -> showTeleports(player, 5);//Dungeons
            h.actions[34] = (SlotAction) (player, slot) -> showTeleports(player, 6);//Bosses
            h.actions[37] = (SlotAction) (player, slot) -> showTeleports(player, 7);//Wilderness
            h.actions[42] = (SlotAction) (player, slot) -> {
                if (player.lastTeleport1 != null) {
                    teleportFinish(player, player.lastTeleport1.getPosition());
                }
            };
            h.actions[43] = (SlotAction) (player, slot) -> {
                if (player.lastTeleport2 != null) {
                    teleportFinish(player, player.lastTeleport2.getPosition());
                }
            };
            h.actions[44] = (SlotAction) (player, slot) -> {
                if (player.lastTeleport3 != null) {
                    teleportFinish(player, player.lastTeleport3.getPosition());
                }
            };
            h.actions[48] = (SimpleAction) (p) -> {
                p.stringInput("Search teleports (name, npc, location):", s -> {
                    search(p, s);
                });
            };
            h.actions[54] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 0);
            };
            h.actions[56] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 0);
            };
            h.actions[57] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 0);
            };


            h.actions[59] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 1);
            };
            h.actions[61] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 1);
            };
            h.actions[62] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 1);
            };

            h.actions[64] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 2);
            };
            h.actions[66] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 0);
            };
            h.actions[67] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 0);
            };


            h.actions[69] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 3);
            };
            h.actions[71] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 3);
            };
            h.actions[72] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 3);
            };


            h.actions[74] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 4);
            };
            h.actions[76] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 4);
            };
            h.actions[77] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 4);
            };


            h.actions[79] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 5);
            };
            h.actions[81] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 5);
            };
            h.actions[82] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 5);
            };


            h.actions[84] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 6);
            };
            h.actions[86] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 6);
            };
            h.actions[87] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 6);
            };


            h.actions[89] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 7);
            };
            h.actions[91] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 7);
            };
            h.actions[92] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 7);
            };


            h.actions[94] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 8);
            };
            h.actions[96] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 8);
            };
            h.actions[97] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 8);
            };


            h.actions[99] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 9);
            };
            h.actions[101] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 9);
            };
            h.actions[102] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 9);
            };


            h.actions[104] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 10);
            };
            h.actions[106] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 10);
            };
            h.actions[107] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 10);
            };


            h.actions[109] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 11);
            };
            h.actions[111] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 11);
            };
            h.actions[112] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 11);
            };


            h.actions[114] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 12);
            };
            h.actions[116] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 12);
            };
            h.actions[117] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 12);
            };


            h.actions[119] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 13);
            };
            h.actions[121] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 13);
            };
            h.actions[122] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 13);
            };


            h.actions[124] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 14);
            };
            h.actions[126] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 14);
            };
            h.actions[127] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 14);
            };


            h.actions[129] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 15);
            };
            h.actions[131] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 15);
            };
            h.actions[132] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 15);
            };


            h.actions[134] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 16);
            };
            h.actions[136] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 16);
            };
            h.actions[137] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 16);
            };


            h.actions[139] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 17);
            };
            h.actions[141] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 17);
            };
            h.actions[142] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 17);
            };


            h.actions[144] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 18);
            };
            h.actions[146] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 18);
            };
            h.actions[147] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 18);
            };


            h.actions[149] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 19);
            };
            h.actions[151] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 19);
            };
            h.actions[152] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 19);
            };


            h.actions[154] = (SlotAction) (player, slot) -> {
                handleTeleport(player, 20);
            };
            h.actions[156] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 20);
            };
            h.actions[157] = (SlotAction) (player, slot) -> {
                handleFavorite(player, 20);
            };


        });
    }
}
