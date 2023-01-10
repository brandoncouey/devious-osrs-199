package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

/**
 * @author Greco
 * @since 12/21/2021
 */
public class VarPlayer {

    public static VarPlayer[] LOADED;

    public int id;
    public int configType = 0;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int i = index.getLastFileId(16) + 1;
        LOADED = new VarPlayer[i];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(16, id);
            VarPlayer def = new VarPlayer();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static VarPlayer get(int id) {
        if (id < 0 || id >= LOADED.length)
            return null;
        return LOADED[id];
    }

    void decode(InBuffer stream) {
        for (; ; ) {
            int i = stream.readUnsignedByte();
            if (i == 0)
                break;
            readValues(stream, i);
        }
    }

    void readValues(InBuffer stream, int i) {
        if (i == 5) {
            configType = stream.readUnsignedShort();
        }
    }
}