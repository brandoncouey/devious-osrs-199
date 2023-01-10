package io.ruin.network.incoming.desktop.handlers;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.cache.EnumMap;
import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.AccessMasks;
import io.ruin.model.inter.Interface;
import io.ruin.model.inter.WidgetID;
import io.ruin.model.inter.battlepass.BattlePass;
import io.ruin.model.inter.utils.Config;
import io.ruin.network.PacketSender;
import io.ruin.network.incoming.Incoming;
import io.ruin.utility.IdHolder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
@IdHolder(ids = {24})
public class DisplayHandler implements Incoming {

    @Override
    public void handle(Player player, InBuffer in, int opcode) {
        int displayMode = in.readByte();
        int canvasWidth = in.readShort();
        int canvasHeight = in.readShort();
        if (!player.hasDisplay()) {
            sendDisplay(player, displayMode);
            player.start();
            player.setOnline(true);
        } else {
            setDisplayMode(player, displayMode);
        }
    }

    public static final int DEFAULT_SCREEN_CHILD_OFFSET = 7;

    public static void setDisplayMode(Player p, int displayMode) {
        if (p.getDisplayMode() == displayMode) return;
        p.setDisplayMode(displayMode);

        PacketSender ps = p.getPacketSender();

        Map<Integer, Integer> oldComponents = getToplevelComponents(p).ints();

        ps.sendGameFrame(getGameFrameFor(displayMode));
        ps.sendClientScript(3998, "i", displayMode - 1);
        switch (displayMode) {
            case 2:
                Config.SIDE_PANELS.set(p, 0);
                break;
            case 3:
                Config.SIDE_PANELS.set(p, 1);
                break;
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(p).ints();
        moveSubInterfaces(oldComponents, newComponents, p);
        ps.sendAccessMask(Interface.OPTIONS, 39, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 53, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 67, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 79, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 80, 1, 4, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 82, 1, 3, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 23, 0, 21, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.OPTIONS, 81, 1, 5, AccessMasks.ClickOp1);
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 50, AccessMasks.ClickOp1, AccessMasks.ClickOp2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, AccessMasks.ClickOp1, AccessMasks.ClickOp2);

        Config.varpbit(6347, false).set(p, BattlePass.getCurrentLevel(p)); // Battle pass current Level!
        Config.varpbit(11877, false).set(p, 150); // quests available

        ps.sendClientScript(3970, "IIi", 46661634, 46661635, (int) TimeUnit.MILLISECONDS.toMinutes(p.playTime * Server.tickMs())); // minutes played.
        Config.varpbit(12933, false).set(p, 1); // if 1 then it shows time played.

        // Config.COLLECTION_COUNT.set(p, CollectionLog.TOTAL_COLLECTABLES); // collections completed
        //Config.varp(2944, false).set(p, 1365); // collections available

        Config.varp(1780, true).set(p, 0); // Membership days left

        /* these masks need to be converted for the component shit with the enum... */
        for (int i = 63; i <= 67; i++)
            ps.sendAccessMask(161, i, -1, -1, AccessMasks.ClickOp1);
        ps.sendAccessMask(161, 68, -1, -1, AccessMasks.ClickOp1, AccessMasks.ClickOp2);
        for (int i = 46; i <= 52; i++)
            ps.sendAccessMask(161, i, -1, -1, AccessMasks.ClickOp1);
    }

    public static void sendDisplay(Player player, int displayMode) {

        Config.TELEPORTING_MINIMAP_ORB.set(player, 1);

        PacketSender ps = player.getPacketSender();
        ps.sendGameFrame(Interface.DEFAULT_SCREEN);
        ps.sendInterface(Interface.CHAT_BAR, Interface.DEFAULT_SCREEN, 1, 1);
        ps.sendInterface(WidgetID.PRIVATE_CHAT, Interface.DEFAULT_SCREEN, 2, 1);

        int tabChildOffset = DEFAULT_SCREEN_CHILD_OFFSET;
        if (Config.DATA_ORBS.get(player) == 0)
            ps.sendInterface(Interface.ORBS, Interface.DEFAULT_SCREEN, 24 + tabChildOffset, 1);

        if (Config.XP_COUNTER_SHOWN.get(player) == 1)
            ps.sendInterface(Interface.EXPERIENCE_COUNTER, 165, 4 + tabChildOffset, 1);

        //ps.sendInterface(378, 165, 28, 0); //welcome screen pt1
        //ps.sendInterface(50, 165, 27, 0); //welcome screen pt2

        ps.sendInterface(Interface.SKILLS, Interface.DEFAULT_SCREEN, 9 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST_TAB, Interface.DEFAULT_SCREEN, 10 + tabChildOffset, 1);
        ps.sendInterface(Interface.QUEST, Interface.QUEST_TAB, 33/*2*/, 1);
        ps.sendInterface(Interface.INVENTORY, Interface.DEFAULT_SCREEN, 11 + tabChildOffset, 1);
        ps.sendInterface(Interface.EQUIPMENT, Interface.DEFAULT_SCREEN, 12 + tabChildOffset, 1);
        ps.sendInterface(Interface.PRAYER, Interface.DEFAULT_SCREEN, 13 + tabChildOffset, 1);
        ps.sendInterface(Interface.MAGIC_BOOK, Interface.DEFAULT_SCREEN, 14 + tabChildOffset, 1);

        //Send Clan System
        ps.sendInterface(Interface.CLAN_CHAT, Interface.DEFAULT_SCREEN, 15 + tabChildOffset, 1);

        //Send GIM Group System
        if (player.getGameMode().isGroupIronman() || player.getGameMode().isHardcoreGroupIronman()) {
            ps.sendInterface(/*Interface.ACCOUNT_MANAGEMENT*/ 723, Interface.DEFAULT_SCREEN, 16 + tabChildOffset, 1);
        } else {
            ps.sendInterface(Interface.ACCOUNT_MANAGEMENT, Interface.DEFAULT_SCREEN, 16 + tabChildOffset, 1);
        }
        ps.sendInterface(Config.FRIENDS_AND_IGNORE_TOGGLE.get(player) == 0
                        ? Interface.FRIENDS_LIST : Interface.IGNORE_LIST,
                Interface.DEFAULT_SCREEN, 17 + tabChildOffset, 1);
        ps.sendInterface(Interface.LOGOUT, Interface.DEFAULT_SCREEN, 18 + tabChildOffset, 1);
        ps.sendInterface(Interface.OPTIONS, Interface.DEFAULT_SCREEN, 19 + tabChildOffset, 1);
        ps.sendInterface(Interface.EMOTE, Interface.DEFAULT_SCREEN, 20 + tabChildOffset, 1);
        ps.sendInterface(Interface.MUSIC_PLAYER, Interface.DEFAULT_SCREEN, 21 + tabChildOffset, 1);
        ps.sendInterface(Interface.COMBAT_OPTIONS, Interface.DEFAULT_SCREEN, 8 + tabChildOffset, 1);

        /**
         * Unlocks
         */
        ps.sendAccessMask(WidgetID.QUESTLIST_GROUP_ID, 6, 0, 22, 2); // free
        ps.sendAccessMask(WidgetID.QUESTLIST_GROUP_ID, 7, 0, 128, 2); // members
        ps.sendAccessMask(WidgetID.QUESTLIST_GROUP_ID, 8, 0, 14, 2); // miniquest
/*        ps.sendAccessMask(WidgetID.OPTIONS_GROUP_ID, 106, 1, 4, 2);
        ps.sendAccessMask(WidgetID.OPTIONS_GROUP_ID, 107, 1, 4, 2);*/
        ps.sendAccessMask(Interface.EMOTE, 1, 0, 50, 2);
        ps.sendAccessMask(Interface.MUSIC_PLAYER, 3, 0, 665, 6);
        ps.sendAccessMask(Interface.MAGIC_BOOK, 194, 0, 5, 2);

        setDisplayMode(player, displayMode);
    }

    public static void updateResizedTabs(Player player) {
        Map<Integer, Integer> oldComponents = getToplevelComponents(player).ints();
        PacketSender ps = player.getPacketSender();
        if (player.getGameFrameId() == Interface.RESIZED_SCREEN) {
            ps.sendGameFrame(Interface.RESIZED_STACKED_SCREEN);
        } else {
            ps.sendGameFrame(Interface.RESIZED_SCREEN);
        }

        Map<Integer, Integer> newComponents = getToplevelComponents(player).ints();
        moveSubInterfaces(oldComponents, newComponents, player);
    }

    public static void moveSubInterfaces(Map<Integer, Integer> oldComponents, Map<Integer, Integer> newComponents, Player player) {
        PacketSender ps = player.getPacketSender();

        for (Map.Entry<Integer, Integer> newComponent : newComponents.entrySet()) {
            int key = newComponent.getKey();
            int to = newComponent.getValue();

            if (to == -1) {
                continue;
            }

            if (oldComponents.containsKey(key)) {
                int from = oldComponents.get(key);

                if (from == -1) {
                    continue;
                }

                ps.moveInterface(from >> 16, from & 0xffff, to >> 16, to & 0xffff);
            }
        }
    }

    public static int getGameFrameFor(int displayMode) {
        switch (displayMode) {
            case 1:
                return Interface.FIXED_SCREEN;
            case 2:
                return Interface.RESIZED_SCREEN;
            case 3:
                return Interface.RESIZED_STACKED_SCREEN;
            default:
                throw new IllegalArgumentException("Unknown display mode " + displayMode);
        }
    }

    public static EnumMap getToplevelComponents(Player player) {
        switch (player.getGameFrameId()) {
            case Interface.FIXED_SCREEN:
                return EnumMap.get(1129);

            case Interface.RESIZED_SCREEN:
                return EnumMap.get(1130);

            case Interface.RESIZED_STACKED_SCREEN:
                return EnumMap.get(1131);

            case Interface.DEFAULT_SCREEN:
                return EnumMap.get(1132);
        }

        return null;
    }

}
