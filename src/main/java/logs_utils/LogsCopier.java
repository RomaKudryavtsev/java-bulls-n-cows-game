package logs_utils;

import model.PlayerTurnResult;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LogsCopier {
    public static void copyPrevLogsToDir(String srcDirName, String dstDirName) {
        File srcLogDir = new File(srcDirName);
        File dstLogDir = new File(dstDirName);
        Set<File> logsInSrcDir = getLogsInSrcDir(srcLogDir);
        createDstLogDir(dstLogDir);
        logsInSrcDir.forEach(srcLog -> {
            File newLog = new File(dstLogDir, srcLog.getName());
            try {
                if(!newLog.createNewFile()) {
                    System.out.println("File already exists");
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create new log");
            }
            copyContent(srcLog, newLog);
            if(!srcLog.delete()) {
                System.out.println("Unable to delete previous log dir");
            }
        });
        srcLogDir.deleteOnExit();
    }

    private static void createDstLogDir(File dstLogDir) {
        if (!dstLogDir.exists()) {
            if(!dstLogDir.mkdirs()) {
                System.out.println("Unable to create new log dir");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void copyContent(File srcLog, File newLog) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(srcLog)));
             ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(newLog)))) {
            List<PlayerTurnResult> logContent = (List<PlayerTurnResult>) in.readObject();
            out.writeObject(logContent);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Unable to copy log content");
        }
    }

    private static Set<File> getLogsInSrcDir(File srcLogDir) {
        File[] fileArr = srcLogDir.listFiles();
        if (fileArr != null) {
            return Arrays.stream(fileArr)
                    .filter(file -> !file.isDirectory())
                    .filter(file -> file.getName().contains(".data"))
                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>(Set.of());
        }
    }
}
