package logs_utils;

import model.PlayerTurnResult;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LogsCopier {

    @SuppressWarnings("unchecked")
    public static void copyPrevLogsToDir(String srcDirName, String dstDirName) {
        File srcLogDir = new File(srcDirName);
        Set<File> logsInSrcDir = getLogsInDir(srcLogDir);
        File dstLogDir = new File(dstDirName);
        if (!dstLogDir.exists()) {
            if(!dstLogDir.mkdirs()) {
                System.out.println("Unable to create new log dir");
            }
        }
        logsInSrcDir.forEach(srcLog -> {
            File newLog = new File(dstLogDir, srcLog.getName());
            try {
                if(!newLog.createNewFile()) {
                    System.out.println("File already exists");
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create new log");
            }
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(srcLog)));
                 ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(newLog)))) {
                List<PlayerTurnResult> logContent = (List<PlayerTurnResult>) in.readObject();
                out.writeObject(logContent);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Unable to copy log content");
            }
            if(!srcLog.delete()) {
                throw new RuntimeException("Unable to delete previous log dir");
            }
        });
        srcLogDir.deleteOnExit();
    }

    private static Set<File> getLogsInDir(File srcLogDir) {
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
