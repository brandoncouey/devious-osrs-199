package io.ruin.model.content;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import io.ruin.model.World;
import io.ruin.model.entity.player.Player;
import io.ruin.model.entity.shared.listeners.LoginListener;
import io.ruin.model.inter.utils.Config;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static io.ruin.model.entity.player.PlayerFile.SAVE_EXECUTOR;

public class GIMRepository {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    public static void save() {
        SAVE_EXECUTOR.execute(() -> {
            File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
            gimData.stream().forEach(imData -> {
                File file = new File(saveDir, imData.leader + ".json");
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    } else {
                        if (file.exists())
                            System.out.println("Already exists");
                    }
                    try (FileWriter writer = new FileWriter(file)) {
                        GSON.toJson(imData, writer);
                    } catch (IOException e) {
                        System.out.println("Error with writer");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    public static void save(GroupIMData imData) {
        SAVE_EXECUTOR.execute(() -> {
            File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
            File file = new File(saveDir, imData.leader + ".json");
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    if (file.exists())
                        System.out.println("Already exists");
                }
                try (FileWriter writer = new FileWriter(file)) {
                    GSON.toJson(imData, writer);
                } catch (IOException e) {
                    System.out.println("Error with writer");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void load() {
        File saveDir = new File(Config.GROUP_SAVE_DIRECTORY);
        for (File file : saveDir.listFiles()) {
            try (FileReader fr = new FileReader(file)) {
                GroupIMData imData = GSON.fromJson(fr, new TypeToken<GroupIMData>() {
                }.getType());
                gimData.add(imData);
                imData.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void onLogin(Player player) {
        if (player.isGroupIronman()) {
            Optional<GroupIMData> team = gimData.stream().filter(data -> data.teamMembers != null).filter(data -> Arrays.stream(data.teamMembers).anyMatch(name -> name.equalsIgnoreCase(player.getName()))).findFirst();
            team.ifPresent(data -> {
                System.out.println("Set gim leader to " + data.leader);
                for (int i = 0; i < data.teamMembers.length; i++) {
                }
                data.players.add(player);
                player.setGIMStorage(data.GIMStorage);
                if (player.getGroupIronman() != null) {
                    player.getGroupIronman().setLeader(data.leader);
                    player.getGroupIronman().setTeam(data.teamMembers);
                    player.getGroupIronman().setLives(data.teamLives);
                    player.getGroupIronman().saveData = team;
                }
            });
            player.getGroupIronman();
        }
    }

    public static List<GroupIMData> gimData = Lists.newArrayList();

    public static void onlogout(Player player) {
        if (player.isGroupIronman()) {
            if (player.getGroupIronman() != null) {
                player.getGroupIronman().saveData.get().players.remove(player);
            }
        }
    }


    public static class GroupIMData {
        @Expose
        private String leader;
        @Expose
        private String[] teamMembers;
        @Expose
        private int teamLives;
        public Set<Player> players = new HashSet<>();

        @Expose
        @Getter
        public GIMStorage GIMStorage;

        // Gson does class.newInstance
        public GroupIMData() {
            init();
        }

        public void init() {
            if (GIMStorage == null) {
                GIMStorage = new GIMStorage();
            }
            GIMStorage.init(null, 80, -1, 63642, 659, true);
        }
    }


    public static void update(Player plr) {
        GroupIronman groupIronman = plr.getGroupIronman();
        Optional<GroupIMData> gimOpt = gimData.stream().filter(data -> data.leader.equalsIgnoreCase(groupIronman.getLeader())).findFirst();
        if (!gimOpt.isPresent()) {
            GroupIMData data = new GroupIMData();
            data.init();
            data.leader = groupIronman.getLeader();
            data.teamMembers = groupIronman.getTeamates().toArray(new String[0]);
            gimData.add(data);
            gimOpt = Optional.ofNullable(data);
        } else {
            gimOpt.get().leader = groupIronman.getLeader();
            gimOpt.get().teamMembers = groupIronman.getTeamates().toArray(new String[0]);
        }

        Optional<GroupIMData> finalGimOpt = gimOpt;
        World.getPlayersForNames(groupIronman.getTeamates()).forEach(p2 -> {
            p2.getGroupIronman().saveData = finalGimOpt;
            p2.setGIMStorage(finalGimOpt.get().getGIMStorage());
        });
        groupIronman.saveData.get().players.add(plr);
        propogate(gimOpt.get());
        for (int i = 0; i < gimOpt.get().teamMembers.length; i++) {
        }
        save(gimOpt.get());
    }

    public static void propogate(GroupIMData data) {
        World.getPlayersForNames(Arrays.asList(data.teamMembers)).forEach(plr -> {
            plr.getGroupIronman().setLeader(data.leader);
            plr.getGroupIronman().setTeam(data.teamMembers);
        });
    }

    static {
        LoginListener.register(GIMRepository::onLogin);

    }


}