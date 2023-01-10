package io.ruin.utility;

import io.ruin.api.protocol.world.WorldStage;
import io.ruin.model.World;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ReverendDread on 6/27/2020
 * https://www.rune-server.ee/members/reverenddread/
 * @project Kronos
 */
@Slf4j
public class CharacterBackups {

    private static final String BACKUP_PATH = "data//backups/";
    private static String CHARACTER_SAVES_PATH;
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> backup;
    private final long backup_period = TimeUnit.MINUTES.toMillis(15);

    public void start() {
        if (World.stage == WorldStage.LIVE) {
            backup = service.scheduleAtFixedRate(this::backup, backup_period, backup_period, TimeUnit.MILLISECONDS);
            CHARACTER_SAVES_PATH = "data//_saved/players/" + World.stage.name().toLowerCase() + "/eco" + "/";
        }
    }

    @SneakyThrows
    public void backup() {

        try {
            log.info("Performing character backup...");

            File folder = new File(BACKUP_PATH);

            if (!folder.exists())
                folder.mkdirs();

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            File backup = new File(folder, "players-" + dateFormat.format(date) + ".zip");

            FileOutputStream fos = new FileOutputStream(backup);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File characters = new File(CHARACTER_SAVES_PATH);
            File[] saves = characters.listFiles();

            assert saves != null;

            for (File file : saves) {
                writeSaveToZip(file, zos);
            }

            zos.close();

            log.info("Successfully backed up {} character file(s).", saves.length);

            DeleteFiles deleteFiles = new DeleteFiles();
            deleteFiles.delete(1, ".zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeSaveToZip(File file, ZipOutputStream zos) {
        try {

            //Create entry
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            //Write the data to the entry
            byte[] data = Files.readAllBytes(file.toPath());
            zos.write(data, 0, data.length);
            zos.closeEntry();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class DeleteFiles {

        public static void main(String[] args) {
            DeleteFiles deleteFiles = new DeleteFiles();
            deleteFiles.delete(1, ".zip");
        }

        public void delete(long days, String fileExtension) {

            File folder = new File(BACKUP_PATH);

            if (folder.exists()) {

                File[] listFiles = folder.listFiles();

                long eligibleForDeletion = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000L);

                if (listFiles != null) {
                    for (File listFile : listFiles) {

                        if (listFile.getName().endsWith(fileExtension) &&
                                listFile.lastModified() < eligibleForDeletion) {
                            log.info("Found files that are older than 1 day, Purging them...");
                            if (!listFile.delete()) {
                                System.out.println("Sorry Unable to Delete Files..");
                            }
                        }
                    }
                }
            }
        }

    }
}
