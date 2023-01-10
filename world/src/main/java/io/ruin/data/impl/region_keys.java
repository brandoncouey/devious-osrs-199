package io.ruin.data.impl;

import io.ruin.api.utils.JsonUtils;
import io.ruin.data.DataFile;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.Region;
import io.ruin.model.map.dynamic.DynamicMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class region_keys extends DataFile {

    public static final int[] NULL_KEYS = new int[4];

    @Override
    public String path() {
        return "region_keys.json";
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public Object fromJson(String fileName, String json) {
        List<XTEA> xteas = JsonUtils.fromJson(json, List.class, XTEA.class);
        Map<Integer, int[]> keys = new HashMap<>(xteas.size());
        for (XTEA xtea : xteas) {
            keys.put(xtea.getMapsquare(), xtea.getKey());
        }

        for (int regionId = 0; regionId < Region.LOADED.length; regionId++) {
            Region region = new Region(regionId);
            if ((region.keys = keys.get(regionId)) != null && !isValid(region.id, region.keys)) {
                System.err.println("Invalid Keys for Region (" + regionId + "): base=(" + region.baseX + ", " + region.baseY + ") keys=" + Arrays.toString(region.keys));
                region.keys = null;
            }
            Region.LOADED[regionId] = region;
        }

//        Map<Integer, int[]> key_map = Maps.newHashMap();
//        JsonParser parser = new JsonParser();
//        JsonArray array = (JsonArray) parser.parse(json);
//        Gson builder = new GsonBuilder().create();
//
//        //Load keys into map
//        for (int i = 0; i < array.size(); i++) {
//            JsonObject jObject = (JsonObject) array.get(i);
//            int regionId = jObject.get("mapsquare").getAsInt();
//            int[] keys = builder.fromJson(jObject.getAsJsonArray("key"), int[].class);
//            key_map.put(regionId, keys);
//        }
//        //Populate regions list
//        for (int i = 0; i < Region.LOADED.length; i++) {
//            Region region = new Region(i);
//            if ((region.keys = key_map.get(i)) != null && !isValid(region.id, region.keys)) {
//                region.keys = null; //Invalid keys
//            }
//            Region.LOADED[i] = region;
//        }
        //Load each region
        for (Region region : Region.LOADED) {
            try {
                region.init();
            } catch (Exception ex) {
                System.err.println("Error loading region " + region.id);
                ex.printStackTrace();
            }
        }

        MultiZone.load();
        DynamicMap.load();
        return keys;
    }

    private static boolean isValid(int id, int[] keys) {
        Region r = new Region(id);
        r.keys = keys;
        try {
            r.getLandscapeData();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

}