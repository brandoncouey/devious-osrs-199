package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

public class GfxDef {

    public static GfxDef[] LOADED;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        LOADED = new GfxDef[index.getLastFileId(13) + 1];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(13, id);
            GfxDef def = new GfxDef();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static GfxDef get(int id) {
        if (id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    /**
     * Cache data
     */

    public int id;
    public int animationId = -1;
    public short[] textureToReplace;
    public int modelId;
    public short[] recolorToFind;
    public short[] recolorToReplace;
    public int contrast = 0;
    public int resizeX = 128;
    public int resizeY = 128;
    public int rotation = 0;
    public int ambient = 0;
    public short[] textureToFind;

    void decode(InBuffer var1) {
        for (; ; ) {
            int var3 = var1.readUnsignedByte();
            if (var3 == 0)
                break;
            readValues(var1, var3);
        }
    }

    void readValues(InBuffer var1, int var2) {
        if (var2 == 1)
            modelId = var1.readUnsignedShort();
        else if (var2 == 2)
            animationId = var1.readUnsignedShort();
        else if (var2 == 4)
            resizeX = var1.readUnsignedShort();
        else if (var2 == 5)
            resizeY = var1.readUnsignedShort();
        else if (var2 == 6)
            rotation = var1.readUnsignedShort();
        else if (var2 == 7)
            ambient = var1.readUnsignedByte();
        else if (var2 == 8)
            contrast = var1.readUnsignedByte();
        else if (var2 == 40) {
            int var4 = var1.readUnsignedByte();
            recolorToFind = new short[var4];
            recolorToReplace = new short[var4];
            for (int var5 = 0; var5 < var4; var5++) {
                recolorToFind[var5] = (short) var1.readUnsignedShort();
                recolorToReplace[var5] = (short) var1.readUnsignedShort();
            }
        } else if (var2 == 41) {
            int var4 = var1.readUnsignedByte();
            textureToFind = new short[var4];
            textureToReplace = new short[var4];
            for (int var5 = 0; var5 < var4; var5++) {
                textureToFind[var5] = (short) var1.readUnsignedShort();
                textureToReplace[var5] = (short) var1.readUnsignedShort();
            }
        }
    }

}
