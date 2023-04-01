package io.ruin;

import io.ruin.api.database.Database;
import io.ruin.api.database.DatabaseUtils;
import io.ruin.api.database.DummyDatabase;
import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.NettyServer;
import io.ruin.api.process.ProcessWorker;
import io.ruin.api.utils.*;
import io.ruin.cache.*;
import io.ruin.data.DataFile;
import io.ruin.data.impl.login_set;
import io.ruin.data.yaml.YamlLoader;
import io.ruin.model.World;
import io.ruin.model.achievements.Achievement;
import io.ruin.model.activities.gambling.GambleManager;
import io.ruin.model.activities.wellofgoodwill.WellofGoodwill;
import io.ruin.model.clan.ClanManager;
import io.ruin.model.combat.special.Special;
import io.ruin.model.content.GIMRepository;
import io.ruin.model.entity.npc.actions.Lottery;
import io.ruin.model.entity.player.Player;
import io.ruin.model.item.actions.impl.pets.Pets;
import io.ruin.model.item.containers.TournamentSuppliesInterface;
import io.ruin.model.map.object.actions.ObjectExamine;
import io.ruin.model.shop.ShopManager;
import io.ruin.network.LoginDecoder;
import io.ruin.network.central.CentralClient;
import io.ruin.network.incoming.Incoming;
import io.ruin.process.CoreWorker;
import io.ruin.process.LoginWorker;
import io.ruin.process.event.EventWorker;
import io.ruin.services.Loggers;
import io.ruin.update.JS5Server;
import io.ruin.utility.Broadcast;
import io.ruin.utility.CharacterBackups;
import io.ruin.utility.OfflineMode;
import io.ruin.utility.ServerLog;
import kilim.tools.Kilim;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class Server extends ServerWrapper {

    public static final ProcessWorker worker = newWorker("server-worker", 600L, Thread.NORM_PRIORITY + 1);

    public static Database gameDb = new DummyDatabase();
    public static Database storeDB = new DummyDatabase();
    public static Database voteDB = new DummyDatabase();
    public static Database siteDb = new DummyDatabase();

    public static Database discordDb = new DummyDatabase();

    public static final Database dumpsDb = new DummyDatabase();

    public static Database hsDb = new DummyDatabase();

    public static Database forumDb = new DummyDatabase();

    public static List<Runnable> afterData = new ArrayList<>();

    public static FileStore fileStore;

    public static File dataFolder;

    public static CharacterBackups backups = new CharacterBackups();

    public static boolean dataOnlyMode = false;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Throwable {
        if (Kilim.trampoline(new Object(), true, args)) {
            return;
        }

        long startTime = System.currentTimeMillis();

        init(Server.class);

        println("Loading server settings...");
        Properties properties = new Properties();
        File systemProps = new File("server.properties");
        try (InputStream in = new FileInputStream(systemProps)) {
            properties.load(in);
        } catch (IOException e) {
            logError("Failed to load server settings!", e);
            throw e;
        }

        /*
         * World information
         */
        World.parse(properties);

        /*
         * Offline mode
         */
        if (OfflineMode.enabled = Boolean.valueOf(properties.getProperty("offline_mode"))) {
            OfflineMode.setPath();
            println("WARNING: Offline mode enabled!");
        }

        /*
         * Loading (Data from cache & databases)
         */
        println("Loading server data...");
        try {

            fileStore = new FileStore(properties.getProperty("cache_path"));
            dataFolder = FileUtils.get(properties.getProperty("data_path"));
            Varpbit.load();
            IdentityKit.load();
            AnimDef.load();
            GfxDef.load();
            ScriptDef.load();
            InterfaceDef.load();
            ItemDef.load();
            NPCDef.load();
            ObjectDef.load();
            ParamDef.load();
            VarClient.load();
            VarClientString.load();
            VarPlayer.load();
            DataFile.load();
            Pets.init();
            /*
             * The following must come after DataFile.load
             */
            login_set.setActive(null, properties.getProperty("login_set"));
        } catch (Throwable t) {
            logError("", t);
            return;
        }

        /*
         * Database connections
         */

        if (!OfflineMode.enabled) {
            println("Connecting to SQL databases...");
            storeDB = new Database(properties.getProperty("database_host"), "psdeviou_store", properties.getProperty("database_user"), properties.getProperty("database_password"));
            voteDB = new Database(properties.getProperty("database_host"), "psdeviou_vote", properties.getProperty("database_user"), properties.getProperty("database_password"));
            hsDb = new Database(properties.getProperty("database_host"), "psdeviou_scores", properties.getProperty("database_user"), properties.getProperty("database_password"));

            DatabaseUtils.connect(new Database[]{/*hsDb, */storeDB, voteDB}, errors -> {
                if (!errors.isEmpty()) {
                    for (Throwable t : errors)
                        logError("Database error", t);
                }
            });
        }
        ObjectExamine.load();
        GIMRepository.load();
        Achievement.staticInit();
        ShopManager.registerUI();
        WellofGoodwill.load();
        ServerLog.buildLogFiles();
        Lottery.load();
        ClanManager.init();
        TournamentSuppliesInterface.registerTournamentSupplies();

        /*
         * Loading (After data has been loaded!
         */
        for (Runnable r : afterData) {
            try {
                r.run();
            } catch (Throwable t) {
                logError("", t);
                return;
            }
        }
        afterData.clear();
        afterData = null;

        /*
         * Loading (Scripts & handlers)
         */
        println("Loading server scripts & handlers...");
        try {
            Special.load();
            Incoming.load();
            PackageLoader.load("io.ruin"); //ensures all static blocks load
            YamlLoader.initYamlFiles();
            backups.start();
        } catch (Throwable t) {
            logError("Error loading handlers", t);
        }

        /*
         * Processing
         */
        println("Starting server workers...");
        worker.queue(() -> {
            try {
                CoreWorker.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                EventWorker.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                GambleManager.process();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
        LoginWorker.start();
        NettyServer nettyServer = NettyServer.start(World.type.getWorldName() + " World (" + World.id + ") Server", World.port, pipeline -> new LoginDecoder(fileStore));
        CentralClient.start();
        ServerWrapper.println("Started server in " + (System.currentTimeMillis() - startTime) + "ms.");

        /*
         * Shutdown hook
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            System.out.println("Server shutting down with " + worker.getQueuedTaskCount() + " tasks queued.");
            System.out.println("Gracefully shutting down world server...");
            nettyServer.shutdown();

            int fails = 0;
            while (true) {
                try {
                    for (Player p : World.players) {
                        if (p.getChannel().id() == null)
                            p.logoutStage = -1;
                    }
                    if (Server.worker.getExecutor().submit(World::removePlayers).get())
                        break;

                    ThreadUtils.sleep(10L); //^ that will already be a big enough delay
                } catch (Throwable t) {
                    logError("ERROR: Removing online players", t);
                    if (++fails >= 5 && World.removePlayers())
                        break;
                    ThreadUtils.sleep(1000L);
                }
            }
        }));
    }

    /**
     * Timing
     */
    public static long currentTick() {
        return worker.getExecutions();
    }

    public static long getEnd(long ticks) {
        return currentTick() + ticks;
    }

    public static boolean isPast(long end) {
        return currentTick() >= end;
    }

    public static int tickMs() {
        return (int) Server.worker.getPeriod();
    }

    public static int toTicks(int seconds) {
        return (seconds * 1000) / tickMs();
    }

}