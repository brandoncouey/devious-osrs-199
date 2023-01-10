package io.ruin.data.impl;

import java.util.Arrays;

/**
 * @author Jire
 */
public final class XTEA {

    private final int archive;
    private final int group;
    private final long name_hash;
    private final int mapsquare;
    private final int[] key;

    public XTEA(int archive, int group, long name_hash, int mapsquare, int[] key) {
        this.archive = archive;
        this.group = group;
        this.name_hash = name_hash;
        this.mapsquare = mapsquare;
        this.key = key;
    }

    public int getArchive() {
        return archive;
    }

    public int getGroup() {
        return group;
    }

    public long getName_hash() {
        return name_hash;
    }

    public int getMapsquare() {
        return mapsquare;
    }

    public int[] getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "XTEA{" +
                "archive=" + archive +
                ", group=" + group +
                ", name_hash=" + name_hash +
                ", mapsquare=" + mapsquare +
                ", key=" + Arrays.toString(key) +
                '}';
    }

}
