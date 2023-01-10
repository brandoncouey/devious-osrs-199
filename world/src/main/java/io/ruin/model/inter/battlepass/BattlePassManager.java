package io.ruin.model.inter.battlepass;

import io.ruin.api.utils.HomeFiles;
import io.ruin.model.World;
import io.ruin.model.entity.player.SeasonPass.SeasonPassParameters;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Luke
 */

@Slf4j
public class BattlePassManager {

    static {
        World.startEvent(event -> {
            while (true) {
                try {
                    loadDate();
                } catch (IOException e) {
                    System.out.println("[BATTLEPASS] " + e.getMessage());
                    e.printStackTrace();
                }
                event.delay(72000);
            }
        });
    }

    public static void writeDate() {
        try {
            Date date = new Date();
            File folder = HomeFiles.get("battlepass" + File.separatorChar + "battle_pass_date_log.txt");
            if (!folder.exists()) folder.createNewFile();

            FileWriter fw = new FileWriter(folder);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(new SimpleDateFormat("yyyy-MM-dd").format(date));
            bw.close();

            System.out.println("[BATTLEPASS] Date for battle pass written to file");
        } catch (IOException e) {
            System.out.println("[BATTLEPASS] Error: Something went wrong. Please try again");
        }
    }

    public static int daysleft;

    public static void loadDate() throws IOException {
        File folder = HomeFiles.get("battlepass");
        if (!folder.exists()) {
            try {
                folder.mkdir();
                log.info("[BATTLEPASS] Creating Battle Pass Directory.");
                writeDate();
                seasonFile();
                new BattlePass();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File date_file = HomeFiles.get("battlepass" + File.separatorChar + "battle_pass_date_log.txt");
            BufferedReader br = new BufferedReader(new FileReader(date_file));
            String bpDate;
            while ((bpDate = br.readLine()) != null && !bpDate.isEmpty()) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateThen = LocalDate.parse(bpDate, dtf);
                LocalDate datenow = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), dtf);
                long noOfDaysBetween = ChronoUnit.DAYS.between(dateThen, datenow);
                daysleft = (int) (ChronoUnit.DAYS.between(datenow, dateThen) + 30);
                System.out.println("[BATTLEPASS] Checking StartDate: " + dateThen.format(dtf) + ", DateNow: " + datenow.format(dtf));
                System.out.println("[BATTLEPASS] Days left: " + daysleft);
                File seasons = HomeFiles.get("battlepass" + File.separatorChar + "battle_pass_season.txt");
                BufferedReader brs = new BufferedReader(new FileReader(seasons));
                SeasonPassParameters.version = Integer.parseInt(brs.readLine());
                if (noOfDaysBetween >= 30) {
                    System.out.println("[BATTLEPASS] 30 days have passed time for a new battle pass!");
                    new BattlePass();
                    clearFile();
                }
            }
        }
    }

    public static void seasonFile() throws IOException {
        File seasons = HomeFiles.get("battlepass" + File.separatorChar + "battle_pass_season.txt");
        File seasontmp = HomeFiles.get("battlepass" + File.separatorChar + "battle_pass_season_tmp.txt");
        if (!seasons.exists()) {
            seasons.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(seasons));
            bw.write("1");
            bw.close();
        } else {
            BufferedReader br = new BufferedReader(new FileReader(seasons));
            BufferedWriter bw = new BufferedWriter(new FileWriter(seasontmp));
            String current = br.readLine();
            bw.write(current);
            bw.close();
            br.close();
            BufferedReader brs = new BufferedReader(new FileReader(seasontmp));
            int season = Integer.parseInt(brs.readLine());
            BufferedWriter bws = new BufferedWriter(new FileWriter(seasons));
            season += 1;
            SeasonPassParameters.version = season;
            bws.write(String.valueOf(season));
            bws.close();
            brs.close();
        }

    }

    public static void clearFile() throws IOException {
        File file = new File("data//battlepass/battle_pass_date_log.txt");
        if (!file.exists()) {
            System.err.println("[BATTLEPASS] File Does Not Exist!");
        }
        file.delete();
        file.createNewFile();
        writeDate();
        seasonFile();
    }


}
