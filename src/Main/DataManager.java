package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    public static GameFileSaveData saveData = new GameFileSaveData();
    public String separator = ",";

    public void StoreData() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(saveData.playerName1);
        stringBuilder.append(separator);
        stringBuilder.append(saveData.playerName2);
        stringBuilder.append(separator);
        stringBuilder.append(saveData.lvl);

        File file = new File(System.getenv("APPDATA") + "\\Datei.save");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadData() {
        System.out.println(System.getenv("APPDATA") + "\\Datei.save");
        File file = new File(System.getenv("APPDATA") + "\\Datei.save");

        if(!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(separator);
                if (parts.length == 3) {
                    saveData.playerName1 = parts[0];
                    saveData.playerName2 = parts[1];
                    saveData.lvl = Integer.parseInt(parts[2]);
                } else {
                    System.err.println("Invalid data format in the file.");
                }
            } else {
                System.err.println("Empty file.");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
