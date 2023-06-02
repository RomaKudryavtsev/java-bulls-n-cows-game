package game_managers;

import logs_utils.GameLogger;
import logs_utils.LogsCopier;
import player_input_utils.PlayerInputReader;

import java.io.*;

public class GameCycleManager {
    private boolean isRunning;
    private String dirLogsName;
    private final CurrentGameManager gameManager;

    public GameCycleManager() {
        File dirNameFile = new File("dirname.txt");
        if(dirNameFile.exists()) {
            try(BufferedReader reader = new BufferedReader(new FileReader(dirNameFile))) {
                dirLogsName = reader.readLine();
                gameManager = new CurrentGameManager(dirLogsName);
            } catch (IOException e) {
                throw new RuntimeException("Unable to read dir name");
            }
        } else {
            gameManager = new CurrentGameManager();
        }
    }

    public void runCycle() {
        isRunning = true;
        while (isRunning) {
            printMenu();
            int playerChoice = PlayerInputReader.readPlayerInput();
            executePlayerChoice(playerChoice);
        }
    }

    private void executePlayerChoice(int playerChoice) {
        switch (playerChoice) {
            case 1:
                System.out.println("\n\t\t>>>>GAME STARTS HERE<<<<");
                System.out.println("\n\t\tMake your first guess!");
                System.out.println("\n\t\tTo exit game type -1");
                gameManager.runGame();
                break;
            case 2:
                System.out.println("\n\t\t>>>>LAST GAME LOG<<<<");
                GameLogger.readFromLog(gameManager.getGameLogFileName());
                break;
            case 3:
                System.out.println("\n\t\t>>>>TYPE NEW DIRECTORY NAMES FOR LOGS<<<<");
                String newLogsDir = PlayerInputReader.readPlayerLogDirName();
                saveDirLogsName(newLogsDir);
                LogsCopier.copyPrevLogsToDir(dirLogsName, newLogsDir);
                break;
            case 4:
                getRules();
                break;
            case 5:
                isRunning = false;
                break;
            default:
                System.out.println("Unknown command");
        }
    }

    private void saveDirLogsName(String dirLogsName) {
        this.dirLogsName = dirLogsName;
        File dirNameFile = new File("dirname.txt");
        try {
            dirNameFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create file with dir name");
        }
        try(FileWriter writer = new FileWriter(dirNameFile)) {
            writer.write(dirLogsName);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save dir name");
        }
    }

    private void printMenu() {
        System.out.println("\n\t####WELCOME TO BULLS 'N COWS GAME####\n");
        printLogo();
        System.out.println("\t\tPlease choose:");
        System.out.println("\t\t\t1 - Start new game");
        System.out.println("\t\t\t2 - See logs of last game played");
        System.out.println("\t\t\t3 - Change log directory");
        System.out.println("\t\t\t4 - See rules");
        System.out.println("\t\t\t5 - Exit");
    }

    private void getRules() {
        try (BufferedReader in = new BufferedReader(new FileReader("rules.txt"))) {
            while (in.ready()) {
                String line = in.readLine();
                System.out.printf("%s\n", line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read rules");
        }
    }

    private void printLogo() {
        System.out.println("         (__)\n" +
                "         /oo|\n" +
                "        (_\"_)*+++++++++*\n" +
                "         /   --   +++++++\n" +
                "        #!  +    ++++++\n" +
                "          \\ _    +++++\n" +
                "          |\\_\\   +   +\n" +
                "          |  \\ \\       \n" +
                "       (__/  (__/");
    }
}
