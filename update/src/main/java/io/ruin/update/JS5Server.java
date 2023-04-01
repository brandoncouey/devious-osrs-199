package io.ruin.update;

import io.ruin.api.filestore.FileStore;
import io.ruin.api.netty.NettyServer;
import io.ruin.api.utils.ServerWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.WatchService;
import java.util.Properties;

@Slf4j
public class JS5Server extends ServerWrapper {

    public static FileStore fileStore;
    public static WatchService fileWatcher;

    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start() throws Exception {
        try {
            Properties properties = new Properties();
            File systemProps = new File("./server.properties");
            try (InputStream in = new FileInputStream(systemProps)) {
                properties.load(in);
            } catch (IOException e) {
                logError("Failed to load server settings!", e);
                throw e;
            }
            System.out.println("Initiating file store...");
            fileStore = new FileStore(properties.getProperty("cache_path"));
            NettyServer nettyServer = NettyServer.start("Update Server", 443, pipeline -> new HandshakeDecoder(fileStore));
            Runtime.getRuntime().addShutdownHook(new Thread(nettyServer::shutdown));
        } catch (Exception e) {
            JS5Server.logError("Error", e);
        }
    }

    static {
        JS5Server.init(JS5Server.class);
    }

}

