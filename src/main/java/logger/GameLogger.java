package logger;

import model.PlayerTurnResult;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GameLogger {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_");

    public static String saveToLog(List<PlayerTurnResult> currentGameResults, int stepsCount) {
        String filename = String.format("%s%d.data", LocalDateTime.now().format(DATE_TIME_FORMAT), stepsCount);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            out.writeObject(currentGameResults);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Unable to write log");
        }
    }

    public static void readFromLog(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            System.out.println("LOG: " + filename);
            List<PlayerTurnResult> log = (List<PlayerTurnResult>) in.readObject();
            log.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to read from log");
        }
    }
}
