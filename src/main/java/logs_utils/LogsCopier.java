package logs_utils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LogsCopier {

    public static void copyPrevLogsToDir(String dirName) {
        //TODO - to implement
        File dir = new File(dirName);
        dir.mkdir();
        Set<String> logsToBeCopied = getLogsInDefaultDir();

    }

    private static Set<String> getLogsInDefaultDir() {
        String defaultDirName = getDefaultDir();
        return Arrays.stream(new File(defaultDirName).listFiles())
                .filter(file -> !file.isDirectory())
                .filter(file -> file.getName().contains(".data"))
                .map(File::getName)
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
