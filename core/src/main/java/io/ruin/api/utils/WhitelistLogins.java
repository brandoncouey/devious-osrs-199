package io.ruin.api.utils;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WhitelistLogins {

    @Getter private static List<String> usernames = new ArrayList<>();

    private static final File store = new File("data/whitelist.txt");


    public static boolean isAllowed(String username) {
        for (String name : usernames) {
            if (username.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public static void load() {
        try {
            if (!store.exists()) {
                store.createNewFile();
            }
            String line;
            BufferedReader br = new BufferedReader(new FileReader(store));
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                usernames.add(line);
                System.out.println("Whitelisted:" + line);
            }
            System.out.println("Successfully whitelisted " + usernames.size());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
