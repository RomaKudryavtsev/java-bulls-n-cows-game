package logs_utils;

import model.PlayerTurnResult;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GameLogger {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_");

    public static String saveToLog(List<PlayerTurnResult> currentGameResults, int stepsCount, String logDirName) {
        String logFileName = String.format("%s%d.data", LocalDateTime.now().format(DATE_TIME_FORMAT), stepsCount);
        File logDir = new File(logDirName);
        if (!logDir.exists()) {
            if (!logDir.mkdir()) {
                System.out.println("Unable to create log dir");
            }
        }
        File logFile = new File(logDirName, logFileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(logFile)))) {
            out.writeObject(currentGameResults);
            return logFile.getName();
        } catch (IOException e) {
            throw new RuntimeException("Unable to write log");
        }
    }

    @SuppressWarnings("unchecked")
    public static void readFromLog(String filename, String dirName) {
        File logFile = new File(dirName, filename);
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(logFile)))) {
            System.out.println("LOG: " + filename);
            List<PlayerTurnResult> log = (List<PlayerTurnResult>) in.readObject();
            log.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to read from log");
        } catch (NullPointerException e) {
            System.out.println("No logs were found");
        }
    }

    public static String readLogsDirectoryName() {
        try (BufferedReader reader = new BufferedReader(new FileReader("dir_logs_name.txt"))) {
            return new File(reader.readLine()).getName();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read logs dir name");
        }
    }

    public static void writeLogsDirectoryName(String newLogsDirName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("dir_logs_name.txt"))) {
            writer.write(newLogsDirName);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write logs dir name");
        }
    }
}
