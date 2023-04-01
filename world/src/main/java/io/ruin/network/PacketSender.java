package io.ruin.network;

import io.netty.channel.ChannelFutureListener;
import io.ruin.Server;
import io.ruin.api.buffer.OutBuffer;
import io.ruin.api.protocol.Protocol;
import io.ruin.api.protocol.login.LoginInfo;
import io.ruin.api.utils.ISAACCipher;
import io.ruin.cache.InterfaceDef;
import io.ruin.data.impl.teleports;
import io.ruin.model.World;
import io.ruin.model.entity.Entity;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.player.ai.AIPlayer;
import io.ruin.model.inter.*;
import io.ruin.model.inter.journal.JournalCategory;
import io.ruin.model.item.Item;
import io.ruin.model.map.Position;
import io.ruin.model.map.Region;
import io.ruin.model.map.ground.GroundItem;
import io.ruin.model.shop.ShopItem;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static io.ruin.network.MobileType.ANDROID;

@Slf4j
public class PacketSender {

    private final Player player;

    private final ISAACCipher cipher;

    public PacketSender(Player player, ISAACCipher cipher) {
        this.player = player;
        this.cipher = cipher;
    }

    public void setZoom(int value) {
        if (value < 128)
            value = 128;
        else if (value > 896)
            value = 896;
        sendClientScript(42, "ii", value, value);
    }

    public void write(OutBuffer out) { //this has to be called by main thread or else gg because of cipher
        if (player instanceof AIPlayer) {
            return;
        }
        player.getChannel().write(out.encode(cipher).toBuffer());
    }

    /**
     * Packets
     */

    public void sendLogin(LoginInfo info) {
        if (player instanceof AIPlayer) {
            return;
        }
        OutBuffer out = new OutBuffer(12);
        out.addByte(2);
        out.addByte(13);

        if (info.tfaTrust) {
            out.addByte(1);
            out.startEncrypt().addInt(info.tfaTrustValue).stopEncrypt();
            out.encrypt(cipher);
        } else {
            out.addByte(0);
            out.skip(4);
        }

        out.addByte((World.isDev() || player.isAdmin()) ? 2 : player.isModerator() ? 1 : 0) // staffModLevel
                .addByte(1) // playerMod
                .addShort(player.getIndex())
                .addByte(1); // field608
        player.getChannel().write(out.toBuffer()); //no encryption needed!

        sendRegion(true);
        Region.update(player);
    }

    public void sendLogout() {
        //System.err.println("SENDING LOGOUT FOR " + player.getName());
        OutBuffer out = new OutBuffer(1).sendFixedPacket(29);
        player.getChannel().writeAndFlush(out.encode(cipher).toBuffer())
                .addListener(ChannelFutureListener.CLOSE);
    }


    public void sendRegion(boolean login) {
        player.removeFromRegions();
        Position position = player.getPosition();
        Region region = player.lastRegion = position.getRegion();
        int chunkX = position.getChunkX();
        int chunkY = position.getChunkY();
        int depth = Region.CLIENT_SIZE >> 4;
        boolean dynamic = region.dynamicData != null;
        OutBuffer out = new OutBuffer(255);
        if (login || !dynamic) {
            /**
             * Regular map
             */
            out.sendVarShortPacket(player.confClientType > ANDROID ? 76 : 94);
            if (login)
                player.getUpdater().init(out);

            if (dynamic) {
                /**
                 * Dynamic region must be sent after regular region on login. //todo check if this is still the case in 171 since they changed how this packet is sent
                 */
                player.getUpdater().updateRegion = true;
                chunkX = chunkY = 0;
            }

            out.addShort(chunkY);
            out.addShort(chunkX);
            int countPos = out.position();
            out.addShort(0);
            boolean forceSend = (chunkX / 8 == 48 || chunkX / 8 == 49) && chunkY / 8 == 48; //Resets the landscape client sided
            if (chunkX / 8 == 48 && chunkY / 8 == 148)
                forceSend = true;
            int regionCount = 0;
            for (int xCalc = (chunkX - depth) / 8; xCalc <= (chunkX + depth) / 8; xCalc++) {
                for (int yCalc = (chunkY - depth) / 8; yCalc <= (chunkY + depth) / 8; yCalc++) {
                    int regionId = yCalc + (xCalc << 8);
                    if (!forceSend || (yCalc != 49 && yCalc != 149 && yCalc != 147 && xCalc != 50 && (xCalc != 49 || yCalc != 47))) {
                        Region r = Region.get(regionId);
                        if (r.keys == null)
                            out.skip(16);
                        else
                            out.addInt(r.keys[0]).addInt(r.keys[1]).addInt(r.keys[2]).addInt(r.keys[3]);
                        player.addRegion(r);
                        regionCount++;
                    }
                }
            }
            int curPos = out.position();
            out.position(countPos);
            out.addShort(regionCount);
            out.position(curPos);
        } else {
            /**
             * Dynamic map
             */
            out.sendVarShortPacket(79)
                    .addLEShortA(chunkY)
                    .addByte(0) //force refresh
                    .addShort(chunkX);

            int startPos = out.position();
            out.addShort(0);

            ArrayList<Integer> chunkRegionIds = new ArrayList<>();
            out.initBitAccess();
            for (int pointZ = 0; pointZ < 4; pointZ++) {
                for (int xCalc = (chunkX - depth); xCalc <= (chunkX + depth); xCalc++) {
                    for (int yCalc = (chunkY - depth); yCalc <= (chunkY + depth); yCalc++) {
                        Region r = Region.LOADED[(xCalc / 8) << 8 | (yCalc / 8)];
                        if (r == null || r.dynamicData == null || r.dynamicIndex != region.dynamicIndex) {
                            out.addBits(1, 0);
                            continue;
                        }
                        int[] chunkData = r.dynamicData[pointZ][xCalc % 8][yCalc % 8];
                        int chunkHash = chunkData[0];
                        int chunkRegionId = chunkData[1];
                        if (chunkHash == 0 || chunkRegionId == 0) {
                            out.addBits(1, 0);
                            continue;
                        }
                        out.addBits(1, 1);
                        out.addBits(26, chunkHash);
                        if (!chunkRegionIds.contains(chunkRegionId))
                            chunkRegionIds.add(chunkRegionId);
                        if (!player.getRegions().contains(r))
                            player.addRegion(r);
                    }
                }
            }
            out.finishBitAccess();

            int endPos = out.position();
            out.position(startPos);
            out.addShort(chunkRegionIds.size());
            out.position(endPos);

            for (int id : chunkRegionIds) {
                Region r = Region.LOADED[id];
                if (r.keys == null)
                    out.skip(16);
                else
                    out.addInt(r.keys[0]).addInt(r.keys[1]).addInt(r.keys[2]).addInt(r.keys[3]);
            }
        }
        write(out);
    }

    public void sendModelInformation(int parentId, int childId, int zoom, int rotationX, int rotationY) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendModelInformation " + parentId + ":" + childId);
            return;
        }
        OutBuffer out = new OutBuffer(11).sendFixedPacket(36)
                .addLEShort(rotationX)
                .addShortA(rotationY)
                .addLEInt(parentId << 16 | childId)
                .addLEShort(zoom);
        write(out);
    }

    public void sendGameFrame(int id) {
        player.setGameFrameId(id);
        OutBuffer out = new OutBuffer(3)
                .sendFixedPacket(player.confClientType > 1 ? 49 : 62)
                .addShort(id);
        write(out);
    }

    public void refreshGameFrame() {
        OutBuffer out = new OutBuffer(1).sendFixedPacket(33);
        write(out);
    }

    public void submitBugReport(short combinedValue, String string1, String string2, byte intStack) {
        OutBuffer out = new OutBuffer(-2).sendFixedPacket(71).addShort(combinedValue)
                .writeStringCp1252NullTerminated(string1)
                .writeStringCp1252NullTerminated(string2)
                .addByte(intStack);
        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendUrl(String url) {
        OutBuffer out = new OutBuffer(Protocol.strLen(url) + 3)
                .sendVarShortPacket(58)
                .startEncrypt()
                .writeStringCp1252NullTerminated(url)
                .stopEncrypt();
        write(out);
    }

    public void sendInterface(int interfaceId, int parentId, int childId, int overlayType) {
        if (!InterfaceDef.valid(interfaceId) || !InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendInterface " + interfaceId
                    + " -> " + parentId + ":" + childId
                    + " (overlayType=" + overlayType + ")");
            return;
        }
        player.setVisibleInterface(interfaceId, parentId, childId);
        OutBuffer out = new OutBuffer(8).sendFixedPacket(53)
                .addShortA(interfaceId)
                .addInt(parentId << 16 | childId)
                .addByteC(overlayType);
        write(out);
    }

    public void sendModel(int parentId, int childId, int modelId) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendModel " + parentId + ":" + childId + " (modelId=" + modelId + ")");
            return;
        }
        OutBuffer out = new OutBuffer(7).sendFixedPacket(74)
                .addInt1(parentId << 16 | childId)
                .addShortA(modelId);
        write(out);
    }

    public void removeInterface(int parentId, int childId) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID removeInterface " + parentId + ":" + childId);
            return;
        }
        player.removeVisibleInterface(parentId, childId);
        OutBuffer out = new OutBuffer(5).sendFixedPacket(78)
                .addInt(parentId << 16 | childId);
        write(out);
    }

    public void moveInterface(int fromParentId, int fromChildId, int toParentId, int toChildId) {
        if (!InterfaceDef.valid(fromParentId, fromChildId) || !InterfaceDef.valid(toParentId, toChildId)) {
            System.err.println("INVALID moveInterface " + fromParentId + "," + fromChildId + " -> " + toParentId + "," + toChildId);
            return;
        }
        player.moveVisibleInterface(fromParentId, fromChildId, toParentId, toChildId);
        OutBuffer out = new OutBuffer(9).sendFixedPacket(45)
                .addInt(toParentId << 16 | toChildId)
                .addInt(fromParentId << 16 | fromChildId);
        write(out);
    }

    public void sendString(int interfaceId, int childId, String string) {
        if (!InterfaceDef.valid(interfaceId, childId)) {
            
            System.err.println("INVALID sendString " + interfaceId + ":" + childId + " (\"" + string + "\")");
            return;
        }
        OutBuffer out = new OutBuffer(3 + 4 + Protocol.strLen(string))
                .sendVarShortPacket(23)
                .writeStringCp1252NullTerminated(string)
                .addInt(interfaceId << 16 | childId);
        write(out);
    }

    public void setHidden(int interfaceId, int childId, boolean hide) {
        if (!InterfaceDef.valid(interfaceId, childId)) {
            System.err.println("INVALID setHidden " + interfaceId + ":" + childId + " (hide=" + hide + ")");
            return;
        }
        OutBuffer out = new OutBuffer(6).sendFixedPacket(8)
                .addByteC(hide ? 1 : 0)
                .addInt(interfaceId << 16 | childId);
        write(out);
    }

    public void sendItem(int parentId, int childId, int itemId, int amount) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendItem " + parentId + ":" + childId + " (itemId=" + itemId + ", amount=" + amount + ")");
            return;
        }
        OutBuffer out = new OutBuffer(11).sendFixedPacket(11)
                .addInt1(parentId << 16 | childId)
                .addInt2(amount)
                .addLEShort(itemId);
        write(out);
    }

    public void setAlignment(int parentId, int childId, int x, int y) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID setAlignment " + parentId + ":" + childId + " (x=" + x + ", y=" + y + ")");
            return;
        }
        OutBuffer out = new OutBuffer(9).sendFixedPacket(68)
                .addShort(x)
                .addShortA(y)
                .addInt(parentId << 16 | childId);
        write(out);
    }

    public void sendAccessMask(WidgetInfo widgetInfo, int minChildID, int maxChildID, AccessMask... accessMask) {
        sendAccessMask(widgetInfo, minChildID, maxChildID, AccessMasks.combine(accessMask));
    }

    public void sendAccessMask(WidgetInfo widgetInfo, int minChildID, int maxChildID, int... masks) {
        sendAccessMask(widgetInfo.getGroupId(), widgetInfo.getChildId(), minChildID, maxChildID, masks);
    }

    public void sendAccessMask(boolean debug, int interfaceId, int childParentId, int minChildId, int maxChildId, AccessMask... accessMask) {
        sendAccessMask(debug, interfaceId, childParentId, minChildId, maxChildId, AccessMasks.combine(accessMask));
    }

    public void sendAccessMask(int interfaceId, int childParentId, int minChildId, int maxChildId, AccessMask... accessMask) {
        sendAccessMask(true, interfaceId, childParentId, minChildId, maxChildId, accessMask);
    }

    public void sendAccessMask(int interfaceId, int childParentId, int minChildId, int maxChildId, int... masks) {
        sendAccessMask(true, interfaceId, childParentId, minChildId, maxChildId, masks);
    }

    public void sendAccessMask(boolean debug, int interfaceId, int childParentId, int minChildId, int maxChildId, int... masks) {
        if (!InterfaceDef.valid(interfaceId, childParentId/*Math.max(childParentId, Math.max(minChildId, maxChildId))*/)) {
            if (debug)
                System.err.println("INVALID sendAccessMask " + interfaceId + ":" + childParentId + " (" + minChildId + ".." + maxChildId + ")");
            return;
        }
        int mask = AccessMasks.combine(masks);
        OutBuffer out = new OutBuffer(13).sendFixedPacket(91)
                .addInt(interfaceId << 16 | childParentId)
                .addLEInt(mask)
                .addLEShort(maxChildId)
                .addLEShortA(minChildId);
        write(out);
    }

    public void setSprite(int widgetId, int childId, int spriteId) {
        sendClientScript(11323, (widgetId << 16 | childId), spriteId);
    }

    public void sendClientScript(int id, Object... params) {
        OutBuffer out = new OutBuffer(1000).sendVarShortPacket(92);

        StringBuilder args = new StringBuilder();

        if (params != null) {
            for (int index = params.length - 1; index >= 0; index--) {
                if (params[index] instanceof String) {
                    args.append("s");
                } else {
                    args.append("i");
                }
            }
        }

        out.writeStringCp1252NullTerminated(args.toString());

        if (params != null) {
            for (int i = params.length - 1; i >= 0; i--) {
                Object param = params[i];
                if (param instanceof String)
                    out.addString((String) param);
                else
                    out.addInt((Integer) param);
            }
        }
        out.addInt(id);
        write(out);
    }

    public void sendClientScript(int id, String type, Object... params) {
        OutBuffer out = new OutBuffer(3 + Protocol.strLen(type) + (params.length * 4))
                .sendVarShortPacket(92)
                .writeStringCp1252NullTerminated(type);
        for (int i = type.length() - 1; i >= 0; i--) {
            Object param = params[i];
            if (param instanceof String)
                out.addString((String) param);
            else
                out.addInt((Integer) param);
        }
        out.addInt(id);
        write(out);
    }

    public void sendClientScriptNT(int id, Object... params) {
        String type = "";
        char[] chars = new char[params.length];
        for (int i = 0; i < params.length; i++) {
            chars[i] = params[i] instanceof String ? 's' : 'i';
            type += params[i] instanceof String ? params[i].toString().length() + 1 : 4;
        }
        type = new String(chars);
        OutBuffer out = new OutBuffer(3 + Protocol.strLen(type) + (params.length * 4))
                .sendVarShortPacket(92)
                .writeStringCp1252NullTerminated(type);
        for (int i = type.length() - 1; i >= 0; i--) {
            Object param = params[i];
            if (param instanceof String)
                out.addString((String) param);
            else
                out.addInt((Integer) param);
        }
        out.addInt(id);
        write(out);
    }

    public void sendSystemUpdate(int time) {
        OutBuffer out = new OutBuffer(3)
                .sendFixedPacket(20)
                .addLEShortA(time * 50 / 30);
        write(out);
    }

    public void setTextStyle(int parentId, int childId, int horizontalAlignment, int verticalAlignment, int lineHeight) {
        sendClientScript(600, "iiiI", horizontalAlignment, verticalAlignment, lineHeight, parentId << 16 | childId);
    }

    public void deathfadeIn() {
        sendClientScript(2894, "iiii", 41549825, 41549826, -1, -1);
        InterfaceType.SECONDARY_OVERLAY.close(player);
    }

    public void deathfadeOut() {
        int parentId = player.getGameFrameId();
        if (parentId == 164) {
            InterfaceType.FULL_SCREEN_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 4128927, -1, -1);
        } else if (parentId == 161) {
            InterfaceType.CLASSIC_SCREEN_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 4128927, -1, -1);
        } else if (parentId == 548) {
            InterfaceType.SECONDARY_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 4128927, -1, -1);
        }
    }

    public void nightmarefadeIn() {
        sendClientScript(2894, "iiii", 41549825, 41549826, 0, 200);
        InterfaceType.SECONDARY_OVERLAY.close(player);
    }

    public void nightmarefadeOut() {
        int parentId = player.getGameFrameId();
        if (parentId == 164) {
            InterfaceType.FULL_SCREEN_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 13109328, -1, -1);
        } else if (parentId == 161) {
            InterfaceType.CLASSIC_SCREEN_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 13109328, -1, -1);
        } else if (parentId == 548) {
            InterfaceType.SECONDARY_OVERLAY.open(player, 634);
            sendClientScript(2893, "iiiiii", 41549825, 41549826, 39504, 13109328, -1, -1);
        }
    }

    public void priffadeIn() {
        sendClientScript(2921);
        InterfaceType.SECONDARY_OVERLAY.close(player);
    }

    public void priffadeOut() {
        int parentId = player.getGameFrameId();
        if (parentId == 164) {
            InterfaceType.FULL_SCREEN_OVERLAY.open(player, 641);
            sendClientScript(2922);
        } else if (parentId == 161) {
            InterfaceType.CLASSIC_SCREEN_OVERLAY.open(player, 641);
            sendClientScript(2922);
        } else if (parentId == 548) {
            InterfaceType.SECONDARY_OVERLAY.open(player, 641);
            sendClientScript(2922);
        }
    }

    public void pohIn() {
        InterfaceType.SECONDARY_OVERLAY.close(player);
    }

    public void pohOut() {
        int parentId = player.getGameFrameId();
        if (parentId == 164) {
            player.openInterface(InterfaceType.FULL_SCREEN_OVERLAY, Interface.CONSTRUCTION_LOADING);
        } else if (parentId == 161) {
            player.openInterface(InterfaceType.CLASSIC_SCREEN_OVERLAY, Interface.CONSTRUCTION_LOADING);
        } else if (parentId == 548) {
            InterfaceType.SECONDARY_OVERLAY.open(player, Interface.CONSTRUCTION_LOADING);
        }
    }

    public void fadeIn() {
        sendClientScript(948, "iiiii", 0, 0, 0, 255, 50);
    }

    public void fadeOut() {
        int parentId = player.getGameFrameId();
        if (parentId == 164) {
            InterfaceType.FULL_SCREEN_OVERLAY.open(player, 174);
            sendClientScript(951, "");
        } else if (parentId == 161) {
            InterfaceType.CLASSIC_SCREEN_OVERLAY.open(player, 174);
            sendClientScript(951, "");
        } else if (parentId == 548) {
            InterfaceType.SECONDARY_OVERLAY.open(player, 174);
            sendClientScript(951, "");
        }
    }

    public void clearFade() {
        sendClientScript(948, "iiiii", 0, 0, 0, 255, 0);
    }

    public void sendMessage(String message, String extension, int type) {
        OutBuffer out = Protocol.messagePacket(message, extension, type);
        write(out);
    }

    public void sendVarp(int id, int value) {
        if (id >= 20000) {
            if (id != 20000 && id != 20002 && id != 20004) {
                return;
            }
            return; // ignore the custom varps.
        }
        OutBuffer out;
        if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE)
            out = new OutBuffer(7).sendFixedPacket(31).addLEShortA(id).addLEInt(value);
        else
            out = new OutBuffer(4).sendFixedPacket(44).addShort(id).addByteC(value);
        write(out);
    }

    public void sendItems(int containerId, Item... items) {
        sendItems(-1, containerId, items, items.length);
    }

    public void sendItems(int parentId, int childId, int containerId, Item... items) {
        sendItems(parentId << 16 | childId, containerId, items, items.length);
    }

    public void sendItems(int parentId, int childId, int containerId, Item[] items, int length) {
        sendItems(parentId << 16 | childId, containerId, items, length);
    }


    public void sendItems(WidgetInfo widgetInfo, int containerId, Item[] items, int length) {
        sendItems(widgetInfo.getPackedId(), containerId, items, length);
    }

    public void sendItems(int containerId, List<Item> items) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(13)
                .addInt(-1)
                .addShort(containerId)
                .addShort(items.size());
        for (Item item : items) {
            int amount = item == null || item.getId() < 0 ? 0 : item.getAmount();
            if (amount < 255) {
                out.addByteC(amount);
            } else {
                out.addByteC(255);
                out.addLEInt(amount);
            }
            out.addShortA(item == null || item.getId() < 0 ? 0 : item.getId() + 1);
        }
        write(out);
    }

    public void sendItems(int interfaceHash, int containerId, Item[] items, int length) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(13)
                .addInt(interfaceHash)
                .addShort(containerId)
                .addShort(length);
        for (int slot = 0; slot < length; slot++) {
            Item item = items[slot];
            int amount = item == null || item.getId() < 0 ? 0 : item.getAmount();
            if (amount < 255) {
                out.addByteC(amount);
            } else {
                out.addByteC(255);
                out.addLEInt(amount);
            }
            out.addShortA(item == null || item.getId() < 0 ? 0 : item.getId() + 1);
        }
        write(out);
    }

    public void sendShopItems(int interfaceHash, int containerId, ShopItem[] items, int length) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(13)
                .addInt(interfaceHash)
                .addShort(containerId)
                .addShort(length);
        for (int slot = 0; slot < length; slot++) {
            ShopItem item = items[slot];
            int amount = item == null || item.getDisplayId(player) < 0 ? 0 : item.getAmount();
            if (amount < 255) {
                out.addByteC(amount);
            } else {
                out.addByteC(255);
                out.addLEInt(amount);
            }
            out.addShortA(item == null || item.getDisplayId(player) < 0 ? 0 : item.getDisplayId(player) + 1);
        }
        write(out);
    }

    public void updateItems(int interfaceHash, int containerId, Item[] items, boolean[] updatedSlots, int updatedCount) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(69).addInt(interfaceHash).addShort(containerId);
        for (int slot = 0; slot < items.length; slot++) {
            if (updatedSlots[slot]) {
                Item item = items[slot];
                out.addSmart(slot);
                int id = item == null || item.getId() < 0 ? 0 : item.getId() + 1;
                out.addShort(id);
                if (id != 0) {
                    int amount = item.getId() < 0 ? 0 : item.getAmount();
                    if (amount < 255) {
                        out.addByte(amount);
                    } else {
                        out.addByte(255);
                        out.addInt(amount);
                    }
                }
            }
        }
        write(out);
    }

    public void updateItems(int interfaceHash, int containerId, ShopItem[] items, boolean[] updatedSlots, int updatedCount) {
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(69)
                .addInt(interfaceHash)
                .addShort(containerId);
        for (int slot = 0; slot < items.length; slot++) {
            if (updatedSlots[slot]) {
                ShopItem item = items[slot];
                out.addSmart(slot);
                int id = item == null || item.getDisplayId(player) < 0 ? 0 : item.getDisplayId(player) + 1;
                out.addShort(id);
                if (id != 0) {
                    int amount = item.getDisplayId(player) < 0 ? 0 : item.getAmount();
                    if (amount < 255) {
                        out.addByte(amount);
                    } else {
                        out.addByte(255);
                        out.addInt(amount);
                    }
                }
            }
        }
        write(out);
    }

    public void unlinkItems(int containerId) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(49)
                .addLEShortA(containerId);
        write(out);
    }

    public void sendStat(int id, int currentLevel, int experience) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(19)
                .addByteS(currentLevel)
                .addInt2(experience)
                .addByteS(id);
        write(out);
    }

    public void sendRunEnergy(int energy) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(73)
                .addByte(energy);
        write(out);
    }

    public void sendWeight(int weight) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(26)
                .addShort(weight);
        write(out);
    }

    public void sendPlayerAction(String name, boolean top, int option) {
        OutBuffer out = new OutBuffer(4 + Protocol.strLen(name)).sendVarBytePacket(2)
                .writeStringCp1252NullTerminated(name)
                .addByte(top ? 1 : 0)
                .addByte(option);
        write(out);
    }

    public void worldHop(String host, int id, int flags) {
        if (true) return;
        OutBuffer out = new OutBuffer(50).sendFixedPacket(76)
                .addString(host)
                .addShort(id)
                .addInt(flags);
        //todo@@ write(out);
    }

    public void resetMapFlag() {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(32)
                .addByte(-1)
                .addByte(-1);
        write(out);
    }
    public void setMapFlag(int x, int y) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(32)
                .addByte(Position.getLocal(x, player.getPosition().getFirstChunkX()))
                .addByte(Position.getLocal(y, player.getPosition().getFirstChunkY()));

        write(out);
    }

    public void clearChunks() {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(10)
                .addByteA(96) // was -1
                .addByteS(96); // was -1
        write(out);
    }

    public void clearChunk(int chunkAbsX, int chunkAbsY) {
        int x = Position.getLocal(chunkAbsX, player.getPosition().getFirstChunkX());
        int y = Position.getLocal(chunkAbsY, player.getPosition().getFirstChunkY());
        OutBuffer out = new OutBuffer(3).sendFixedPacket(10)
                .addByteA(y)
                .addByteS(x);
        write(out);
    }

    private void sendMapPacket(int x, int y, int z, Function<Integer, OutBuffer> write) {
        if (player.getHeight() != z)
            return;
        int chunkAbsX = (x >> 3) << 3;
        int chunkAbsY = (y >> 3) << 3;
        int targetLocalX = x - chunkAbsX;
        int targetLocalY = y - chunkAbsY;
        int playerLocalX = Position.getLocal(chunkAbsX, player.getPosition().getFirstChunkX());
        int playerLocalY = Position.getLocal(chunkAbsY, player.getPosition().getFirstChunkY());
        if (playerLocalX >= 0 && playerLocalX < 104 && playerLocalY >= 0 && playerLocalY < 104) {
            write(new OutBuffer(3).sendFixedPacket(18).addByteC(playerLocalX).addByteC(playerLocalY));
            write(write.apply((targetLocalX & 0x7) << 4 | (targetLocalY & 0x7)));
        }
    }

    public void sendGroundItem(GroundItem groundItem) {
        sendMapPacket(groundItem.x, groundItem.y, groundItem.z, offsetHash ->
                new OutBuffer(6).sendFixedPacket(86)
                        .addShort(groundItem.amount)
                        .addShort(groundItem.id)
                        .addByteA(offsetHash)
        );
    }

    public void sendRemoveGroundItem(GroundItem groundItem) {
        sendMapPacket(groundItem.x, groundItem.y, groundItem.z, offsetHash ->
                new OutBuffer(4).sendFixedPacket(61)
                        .addShortA(groundItem.id)
                        .addByte(offsetHash)
        );
    }

    public void sendCreateObject(int id, int x, int y, int z, int type, int dir) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(5).sendFixedPacket(0)
                        .addShort(id)
                        .addByteS(offsetHash)
                        .addByte(type << 2 | dir)
        );
    }

    public void sendRemoveObject(int x, int y, int z, int type, int dir) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(3).sendFixedPacket(52)
                        .addByteS(type << 2 | dir)
                        .addByte(offsetHash)
        );
    }

    public void sendObjectAnimation(int x, int y, int z, int type, int dir, int animationId) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(5).sendFixedPacket(75)
                        .addByteS(offsetHash)
                        .addLEShort(animationId)
                        .addByteA(type << 2 | dir)
        );
    }

    public void sendProjectile(int projectileId, int startX, int startY, int destX, int destY, int targetIndex, int startHeight, int endHeight, int delay, int duration, int curve, int something) {
        sendMapPacket(startX, startY, player.getHeight(), offsetHash ->
                new OutBuffer(16).sendFixedPacket(34)
                        .addByte(destX - startX)
                        .addLEShort(targetIndex)
                        .addByteA(curve)//slope
                        .addByte(offsetHash)
                        .addByteA(endHeight)
                        .addByteS(something)
                        .addShortA(duration)//cycleEnd
                        .addShortA(projectileId)
                        .addByteS(destY - startY)
                        .addByteA(startHeight)
                        .addShortA(delay)//cycleStart
        );
    }

    public void sendGraphics(int id, int height, int delay, int x, int y, int z) {
        sendMapPacket(x, y, z, offsetHash ->
                new OutBuffer(7).sendFixedPacket(80)
                        .addShort(delay)
                        .addShortA(id)
                        .addByteA(offsetHash)
                        .addByte(height)
        );
    }

    public void sendAreaSound(int id, int radius, int delay, int x, int y, int distance) {
        sendMapPacket(x, y, player.getHeight(), offsetHash ->
                new OutBuffer(6).sendFixedPacket(88)
                        .addShort(id)
                        .addByte(distance << 4 | radius)
                        .addByteS(offsetHash)
                        .addByteS(delay)
        );
    }

    public void sendSoundEffect(int id, int type, int delay) {
        OutBuffer out = new OutBuffer(6).sendFixedPacket(81)
                .addShort(id)
                .addByte(type)
                .addShort(delay);
        write(out);
    }

    public void sendMusic(int id) {
        OutBuffer out = new OutBuffer(3).sendFixedPacket(28)
                .addShortA(id);
        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendJournal(int type, List<JournalCategory> categories) {
        /*OutBuffer out = new OutBuffer(255).sendVarShortPacket(87)
                .addByte(type);
        for(JournalCategory category : categories) {
            out.addString(category.name);
            out.addSmart(category.count);
        }
        write(out);*/
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendJournalEntry(int childId, String text, int colorIndex) {
        /*OutBuffer out = new OutBuffer(2 + Protocol.strLen(text) + 1).sendVarBytePacket(88)
                .addSmart(childId)
                .addString(text)
                .addByte(colorIndex);
        write(out);*/
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendAccountManagement(String donatorStatus, String username, int unreadPMs) {
        /*OutBuffer out = new OutBuffer(2 + Protocol.strLen(donatorStatus) + 5).sendVarShortPacket(92)
                .addString(donatorStatus)
                .addString(username).
                addByte(unreadPMs);
        write(out);*/
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendSkillinterface(String donatorStatus) {
        /*OutBuffer out = new OutBuffer(2 + Protocol.strLen(donatorStatus)).sendVarShortPacket(95)
                .addString(donatorStatus);
        write(out);*/
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendDiscordPresence(String discordStatus) {
        /*OutBuffer out = new OutBuffer(2 + Protocol.strLen(discordStatus)).sendVarShortPacket(94)
                .addString(discordStatus);
        write(out);*/
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendTeleports(String title,
                              int selectedCategoryIndex, teleports.Category[] categories,
                              int selectedSubcategoryIndex, teleports.Subcategory[] subcategories,
                              teleports.Teleport[] teleports) {
        if (true) return;
        OutBuffer out = new OutBuffer(255).sendVarShortPacket(89);

        if (title != null)
            out.addString(title);
        else
            out.addByte(0);

        out.addByte(selectedCategoryIndex);
        if (categories != null) {
            out.addByte(categories.length);
            for (teleports.Category c : categories)
                out.addString(c.name);
        } else {
            out.addByte(0);
        }

        out.addByte(selectedSubcategoryIndex);
        if (subcategories != null) {
            out.addByte(subcategories.length);
            for (teleports.Subcategory c : subcategories)
                out.addString(c.name);
        } else {
            out.addByte(0);
        }

        if (teleports != null) {
            for (teleports.Teleport t : teleports)
                out.addString(t.name);
        }

        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendDropTable(String name, int petId, int petAverage, List<Integer[]> drops) {
        if (true) return;
        OutBuffer out = new OutBuffer(3 + Protocol.strLen(name) + 4 + (drops.size() * 13)).sendVarShortPacket(90)
                .addString(name)
                .addShort(petId)
                .addShort(petAverage);
        for (Integer[] drop : drops) {
            out.addShort(drop[0]);  //id
            out.addByte(drop[1]);   //broadcast
            out.addInt(drop[2]);    //min amount
            out.addInt(drop[3]);    //max amount
            out.addShort(drop[4]);  //average
        }
        write(out);
    }

    //TODO: 184 Revision Fix Custom Packet
    public void sendLoyaltyRewards(int dayReward, int currentSpree, int highestSpree, int totalClaimedRewards, Item... loyaltyRewards) {
        /*OutBuffer out = new OutBuffer(3 + 1 + 12 + (loyaltyRewards.length * 8)).sendVarShortPacket(93)
                .addByte(dayReward)
                .addInt(currentSpree)
                .addInt(highestSpree)
                .addInt(totalClaimedRewards);
        for(Item reward : loyaltyRewards) {
            out.addInt(reward.getId());
            out.addInt(reward.getAmount());
        }
        write(out);*/
    }
    //TODO: 184 Revision Fix Custom Packet
//    public void sendBuyCredits(String message, int discountPercent, int selectedCreditPack, int selectedPaymentMethod, BuyCredits... packs) {
//        /*OutBuffer out = new OutBuffer(3 + Protocol.strLen(message) + 3 + (packs.length * 12)).sendVarShortPacket(12)
//                .addString(message)
//                .addByte(discountPercent)
//                .addByte(selectedCreditPack)
//                .addByte(selectedPaymentMethod);
//        for(BuyCredits pack : packs) {
//            out.addInt(pack.purchasePrice);
//            out.addInt(pack.purchaseAmount);
//            out.addInt(pack.freeAmount);
//        }*/
//        //todo@@ write(out);
//    }

    public void sendWidget(Widget widget, int seconds) {
        if (true) return;
        OutBuffer out = new OutBuffer(4).sendVarShortPacket(91)
                .addByte(widget.getSpriteId())
                .addShort(seconds * 50)
                .addString(widget.getName())
                .addString(widget.getDescription());
        write(out);
    }

    public void sendPlayerHead(int parentId, int childId) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendPlayerHead " + parentId + ":" + childId);
            return;
        }
        OutBuffer out = new OutBuffer(5).sendFixedPacket(77)
                .addLEInt(parentId << 16 | childId);
        write(out);
    }

    public void sendNpcHead(int parentId, int childId, int npcId) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID sendNpcHead " + parentId + ":" + childId + " (npcId=" + npcId + ")");
            return;
        }
        OutBuffer out = new OutBuffer(7).sendFixedPacket(85)
                .addInt(parentId << 16 | childId)
                .addLEShortA(npcId);
        write(out);
    }

    public void animateInterface(int parentId, int childId, int animationId) {
        if (!InterfaceDef.valid(parentId, childId)) {
            System.err.println("INVALID animateInterface " + parentId + ":" + childId + " (animationId=" + animationId + ")");
            return;
        }
        OutBuffer out = new OutBuffer(7).sendFixedPacket(63)
                .addShort(animationId)
                .addInt1(parentId << 16 | childId);
        write(out);
    }

    public void sendMapState(int state) {
        OutBuffer out = new OutBuffer(2).sendFixedPacket(76)
                .addByte(state);
        write(out);
    }

    public void sendHintIcon(Entity target) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(25)
                .addByte(target.player == null ? 1 : 10)
                .addShort(target.getIndex())
                .skip(3);
        write(out);
    }

    public void sendHintIcon(int x, int y) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(25)
                .addByte(2)
                .addShort(x)
                .addShort(y)
                .addByte(1);
        write(out);
    }

    public void sendHintIcon(Position position) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(25)
                .addByte(2)
                .addShort(position.getX())
                .addShort(position.getY())
                .addByte(1);
        write(out);
    }

    public void resetHintIcon(boolean npcType) {
        OutBuffer out = new OutBuffer(7).sendFixedPacket(25)
                .addByte(npcType ? 1 : 10)
                .addShort(-1)
                .skip(3);
        write(out);
    }

    public void turnCameraToLocation(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 0);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(50)
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }

    public void moveCameraToLocation(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 0);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(39)
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }


    public void turnCameraToLocation_HOME(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 1);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(50)
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }

    public void moveCameraToLocation_HOME(int x, int y, int cameraHeight, int constantSpeed, int variableSpeed) {
        Position pos = new Position(x, y, 1);
        int posX = pos.getSceneX(player.getPosition());
        int posY = pos.getSceneY(player.getPosition());
        OutBuffer out = new OutBuffer(7).sendFixedPacket(39)
                .addByte(posX)
                .addByte(posY)
                .addShort(cameraHeight)
                .addByte(constantSpeed)
                .addByte(variableSpeed);
        write(out);
    }

    /**
     * @param shakeType 0 shakes X, 1 shakes Z, 2 shakes Y, 3 shakes Yaw, 4 shakes Pitch
     */
    public void shakeCamera(int shakeType, int intensity) {
        OutBuffer out = new OutBuffer(5).sendFixedPacket(24)
                .addByte(shakeType)
                .addByte(intensity)
                .addByte(intensity)
                .addByte(intensity);
        write(out);
    }

    public void sendDefaultMessage(String message) {
        sendClientScript(11324, "s", message);
    }

    public void resetCamera() {
        OutBuffer out = new OutBuffer(1).sendFixedPacket(87);
        write(out);
    }

}