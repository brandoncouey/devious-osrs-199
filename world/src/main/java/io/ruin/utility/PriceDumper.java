package io.ruin.utility;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Expose;
import io.ruin.Server;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.utils.JsonUtils;
import io.ruin.cache.ItemDef;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PriceDumper {

    public static Map<Integer, ItemDef> cached = Maps.newConcurrentMap();

    public static PriceDumper dumper = new PriceDumper();

    @RequiredArgsConstructor
    static class ItemPrice {
        @Expose
       public final int id;
        @Expose
        public final String name;
        @Expose
        public final int price;
    }

    @Expose
    public List<ItemPrice> prices = new ArrayList<>();

    public static transient FileStore fileStore;


    public static void main(String[] args) {
        load();
        /*Properties properties = new Properties();
        File systemProps = new File("server.properties");
        try (InputStream in = new FileInputStream(systemProps)) {
            properties.load(in);
        } catch (IOException e) {
        }
        try {
            fileStore = new FileStore(properties.getProperty("cache_path"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IndexFile index = fileStore.get(2);
            int size = index.getLastFileId(10) + 1;
            for (int id = 0; id < size; id++) {
                ItemDef def = new ItemDef();
                def.id = id;
                byte[] data = index.getFile(10, def.id);
                if (data != null) {
                    def.decode(new InBuffer(data));
                    if (def.stackable)
                        def.descriptiveName = "some " + def.name.toLowerCase();
                    else if (StringUtils.vowelStart(def.name))
                        def.descriptiveName = "an " + def.name;
                    else
                        def.descriptiveName = "a " + def.name;
                    cached.put(id, def);
                }
            }
            for (ItemDef def : cached.values()) {
                dumper.prices.add(new ItemPrice(def.id, def.name, def.value));
            }
            save();*/
    }

    public static void load() {
        File file = new File("data/items/", "prices.json");
        if (file.exists()) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String json = new String(bytes);
                dumper = JsonUtils.GSON_EXPOSE_PRETTY.fromJson(json, PriceDumper.class);
                Server.println("Successfully loaded " + dumper.prices.size()+ " prices.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void save() {
        try {
            String json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(dumper);
            if(!new File("data/items/").exists() && !new File("data/items/").mkdirs())
                throw new IOException("events directory could not be created!");
            Files.write(new File(new File("data/items/"), "prices.json").toPath(), json.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
