package io.ruin.model.map.object.actions;

import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectExamine {

    public static Map<Integer, String> examines = new HashMap<>();

    public static void load() {
        File file = new File("data/objects/", "object_examines.json");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String json = new String(bytes);
                List<Examine> examines = JsonUtils.fromJson(json, List.class, Examine.class);
                ObjectExamine.examines.clear();
                for (Examine ex : examines) {
                    ObjectExamine.examines.put(ex.id, ex.examine);
                }
                Server.println("Successfully loaded " + examines.size() + " object examines...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Examine {
        @Expose
        public int id;
        @Expose
        public String examine;
    }

}

