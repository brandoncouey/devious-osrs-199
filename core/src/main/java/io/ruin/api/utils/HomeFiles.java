package io.ruin.api.utils;

import java.io.File;

/**
 * @author Jire
 */
public enum HomeFiles {
    ;

    public static final String DIR = "data/";
    public static final File DIR_FILE = new File(DIR);

    static {
        if (!DIR_FILE.exists()) {
            DIR_FILE.mkdirs();
        }
    }

    public static File get(String childFile) {
        return new File(DIR_FILE, childFile);
    }
}