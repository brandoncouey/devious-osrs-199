package io.ruin.utility;

import io.ruin.model.clan.Clan;

import java.io.*;

public class SerializableFilesManager {

    private static final String CLAN_PATH = "data/clans/";


    public synchronized static boolean containsClan(String name) {
        return new File(getClanPath() + name + ".c").exists();
    }

    public synchronized static Clan loadClan(String name) {
        try {
            return (Clan) loadSerializedFile(new File(getClanPath() + name + ".c"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized static void saveClan(Clan clan) {
        try {
            storeSerializableClass(clan, new File(getClanPath() + clan.getName() + ".c"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public synchronized static void deleteClan(Clan clan) {
        try {
            new File(getClanPath() + clan.getName() + ".c").delete();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }



    private static synchronized Object loadObject(String f) throws IOException, ClassNotFoundException {
        if (!(new File(f).exists()))
            return null;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        Object object = in.readObject();
        in.close();
        return object;
    }

    private static synchronized void storeObject(Serializable o, String f) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(o);
        out.close();
    }


    public static final Object loadSerializedFile(File f) throws IOException,
            ClassNotFoundException {
        if (!f.exists())
            return null;
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
        Object object = in.readObject();
        in.close();
        return object;
    }

    public static final void storeSerializableClass(Serializable o, File f)
            throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
        out.writeObject(o);
        out.close();
    }

    public static String getClanPath() {
        return CLAN_PATH;
    }

}
