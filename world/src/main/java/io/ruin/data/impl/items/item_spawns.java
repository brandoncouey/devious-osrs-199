package io.ruin.data.impl.items;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.World;
import io.ruin.model.map.Region;
import io.ruin.model.map.ground.GroundItem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class item_spawns extends DataFile {

    public static Map<Integer, List<GroundItemSpawn>> allSpawns = Maps.newConcurrentMap();


    @Override
    public String path() {
        return "items/spawns/*.json";
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<GroundItemSpawn> spawns = JsonUtils.fromJson(json, List.class, GroundItemSpawn.class);
        spawns.forEach(spawn -> {
            int regionId = Region.getId(spawn.x, spawn.y);
            if (spawn.amount < 1)
                spawn.amount = 1;
            List<GroundItemSpawn> regionSpawns = allSpawns.computeIfAbsent(regionId, k -> Lists.newArrayList());
            regionSpawns.add(spawn);
        });
        spawns.forEach(item_spawns::spawnToWorld);
        return spawns;
    }

    public static void spawnToWorld(GroundItemSpawn spawn) {
        if (spawn.world != null && !World.type.name().equalsIgnoreCase(spawn.world))
            return;
        new GroundItem(spawn.id, spawn.amount).position(spawn.x, spawn.y, spawn.z).spawnWithRespawn(spawn.respawnSeconds);
    }

    public static void save(int regionId) {
        List<GroundItemSpawn> regionSpawns = allSpawns.get(regionId);
        if (regionSpawns != null) {
            try {
                JsonUtils.toFile(new File("./data/items/spawns/" + regionId + ".json"), JsonUtils.toPrettyJson(regionSpawns));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAll() {
        allSpawns.keySet().forEach(item_spawns::save);
    }
}