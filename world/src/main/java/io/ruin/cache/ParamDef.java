package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;
import io.ruin.api.utils.StringUtils;

/**
 * @author Greco
 * @since 12/21/2021
 */
public class ParamDef {

    public static ParamDef[] LOADED;

    public int id;
    public boolean autoDisable = true;
    public char stacKType;
    public int defaultInt;
    public String defaultString;

    public static void load() {
        IndexFile index = Server.fileStore.get(2);
        int i = index.getLastFileId(11) + 1;
        LOADED = new ParamDef[i];
        for (int id = 0; id < LOADED.length; id++) {
            byte[] data = index.getFile(11, id);
            ParamDef def = new ParamDef();
            def.id = id;
            def.decode(new InBuffer(data));
            LOADED[id] = def;
        }
    }

    public static ParamDef get(int id) {
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
        if (i == 1) {
            stacKType = StringUtils.getJagexChar(stream);
        } else if (i == 2) {
            defaultInt = stream.readInt();
        } else if (i == 4) {
            autoDisable = false;
        } else if (i == 5) {
            defaultString = stream.readString();
        }
    }


}
