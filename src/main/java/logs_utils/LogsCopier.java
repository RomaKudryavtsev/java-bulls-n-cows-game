package logs_utils;

import model.PlayerTurnResult;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LogsCopier {

    @SuppressWarnings("unchecked")
    public static void copyPrevLogsToDir(String srcDirName, String dstDirName) {
        Set<File> logsInSrcDir = getLogsInDir(srcDirName);
        File dstDir = new File(dstDirName);
        if(!dstDir.mkdir()) {
            throw new RuntimeException("Unable to create destination dir");
        }
        logsInSrcDir.forEach(oldLog -> {
            File newLog = new File(dstDirName, oldLog.getName());
            try {
                newLog.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Unable to create new log");
            }
            try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(oldLog)));
                ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(newLog)))) {
                List<PlayerTurnResult> logContent = (List<PlayerTurnResult>) in.readObject();
                out.writeObject(logContent);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Unable to copy log content");
            }
            oldLog.delete();
        });
    }

    private static Set<File> getLogsInDir(String srcDirName) {
        String dirName = srcDirName == null ? getDefaultDir() : srcDirName;
        return Arrays.stream(new File(dirName).listFiles())
                .filter(file -> !file.isDirectory())
                .filter(file -> file.getName().contains(".data"))
                .collect(Collectors.toSet());
    }

    private static String getDefaultDir() {
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        try {
            return helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
        } catch (IOException e) {
            throw new RuntimeException("Unable to define current directory");
        }
    }
}
