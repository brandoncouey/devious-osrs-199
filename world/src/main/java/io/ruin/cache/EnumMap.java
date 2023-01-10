package io.ruin.cache;

import io.ruin.Server;
import io.ruin.api.buffer.InBuffer;
import io.ruin.api.filestore.IndexFile;

import java.util.HashMap;
import java.util.Map;

public class EnumMap {

    public static EnumMap get(int id) {
        IndexFile index = Server.fileStore.get(2);
        byte[] data = index.getFile(8, id);
        EnumMap map = new EnumMap(id);
        if (data != null)
            map.decode(new InBuffer(data));
        return map;
    }

    public final int id;

    public int[] keys;
    public char keyType;
    public String defaultString = "null";
    public int[] intValues;
    public int length = 0;
    public int defaultInt;
    public char valType;
    public String[] stringValues;

    public EnumMap(int id) {
        this.id = id;
    }

    void decode(InBuffer in) {
        for (; ; ) {
            int var3 = in.readUnsignedByte();
            if (var3 == 0)
                break;
            readValues(in, var3);
        }
    }

    void readValues(InBuffer in, int var2) {
        if (var2 == 1)
            keyType = (char) in.readUnsignedByte();
        else if (var2 == 2)
            valType = (char) in.readUnsignedByte();
        else if (var2 == 3)
            defaultString = in.readString();
        else if (var2 == 4)
            defaultInt = in.readInt();
        else if (var2 == 5) {
            length = in.readUnsignedShort();
            keys = new int[length];
            stringValues = new String[length];
            for (int i = 0; i < length; i++) {
                keys[i] = in.readInt();
                stringValues[i] = in.readString();
            }
        } else if (var2 == 6) {
            length = in.readUnsignedShort();
            keys = new int[length];
            intValues = new int[length];
            for (int i = 0; i < length; i++) {
                keys[i] = in.readInt();
                intValues[i] = in.readInt();
            }
        } else
            System.err.println("INVALID ENUM OPCODE " + var2 + " FOR ID " + id);
    }

    public Map<Integer, String> strings() {
        Map<Integer, String> map = new HashMap<>(length);
        for (int i = 0; i < length; i++)
            map.put(keys[i], stringValues[i]);
        return map;
    }

    public Map<Integer, Integer> ints() {
        Map<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++)
            map.put(keys[i], intValues[i]);
        return map;
    }

    public Map<Integer, Integer> valuesAsKeysInts() {
        Map<Integer, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++)
            map.put(intValues[i], keys[i]);
        return map;
    }

}