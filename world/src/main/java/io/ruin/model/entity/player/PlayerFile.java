package io.ruin.model.entity.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import io.ruin.Configuration;
import io.ruin.Server;
import io.ruin.api.process.ProcessFactory;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.ListUtils;
import io.ruin.model.inter.utils.Config;
import io.ruin.model.skills.construction.RoomDefinition;
import io.ruin.model.skills.construction.room.Room;
import io.ruin.network.central.CentralClient;
import io.ruin.utility.OfflineMode;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayerFile {

    public static final ExecutorService SAVE_EXECUTOR = Executors.newSingleThreadExecutor(new ProcessFactory("save-worker", Thread.NORM_PRIORITY - 1));
    private static final Gson GSON_LOADER = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(Room.class, (JsonDeserializer<Room>) (jsonElement, type, jsonDeserializationContext) -> {
        JsonObject obj = jsonElement.getAsJsonObject();
        RoomDefinition definition = RoomDefinition.valueOf(obj.get("definition").getAsString());
        return jsonDeserializationContext.deserialize(jsonElement, definition.getHandler());
    }).create();

    public static void save(Player player, int logoutAttempt) {
        SAVE_EXECUTOR.execute(() -> {
            Config.save(player);
            String json;
            try {
                json = JsonUtils.GSON_EXPOSE_PRETTY.toJson(player);
            } catch (Exception e) {
                Server.logError("", e);
                return;
            }
            if (OfflineMode.savePlayer(player, logoutAttempt, json))
                return;
            CentralClient.sendSave(player.getUserId(), logoutAttempt, json);
        });
    }

    public static Player load(PlayerLogin login) {
        try {
            Player player;
            if (login.info.saved == null || login.info.saved.isEmpty())
                player = new Player();
            else
                player = GSON_LOADER.fromJson(login.info.saved, Player.class);

            if (player.storeAmountSpent >= 10) {
                SecondaryGroup.getGroup(player);
            }

            if (player.getSecondaryGroup() == null)
                player.setSecondaryGroups(ListUtils.toList(SecondaryGroup.NONE.id));
            else
                player.setSecondaryGroups(ListUtils.toList(player.getSecondaryGroup().id));

            if (player.getPrimaryGroup() == null)
                player.setGroups(ListUtils.toList(PlayerGroup.REGISTERED.id));
            else
                player.setGroups(ListUtils.toList(player.getPrimaryGroup().id));

            Config.load(player);
            return player;
        } catch (Throwable t) {
            Server.logError("", t);
            return null;
        }
    }

}