package io.ruin.model.activities.gambling;

import io.ruin.model.entity.player.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GambleLogger {
    public void createFile(Player playerOne, Player playerTwo) {
        try {
            Path path = Paths.get("data/gambling/" + playerOne.getName() + "_" + playerTwo.getName() + ".txt");
            if (Files.exists(path)) {
                Files.delete(path);
            }
            FileWriter fileWriter = new FileWriter("data/gambling/" + playerOne.getName() + "_" + playerTwo.getName() + ".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("Logs of gamble party between " + playerOne.getName() + " and " + playerTwo.getName());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToFile(Player playerOne, Player playerTwo, String message) {
        try {
            FileWriter fw = new FileWriter("data/gambling/" + playerOne.getName() + "_" + playerTwo.getName() + ".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write("-------------------------------------------------------------------------------------------");
            bw.newLine();
            bw.write(message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
