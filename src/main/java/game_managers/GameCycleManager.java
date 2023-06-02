package game_managers;

import logger.GameLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GameCycleManager {
    private boolean isRunning;
    private final CurrentGameManager gameManager = new CurrentGameManager();

    public void runCycle() {
        isRunning = true;
        while(isRunning) {
            printMenu();
            Scanner scanner = new Scanner(System.in);
            int playerChoice = scanner.nextInt();
            executePlayerChoice(playerChoice);
        }
    }

    private void executePlayerChoice(int playerChoice) {
        switch (playerChoice) {
            case 1:
                System.out.println("\n\t\t>>>>GAME STARTS HERE<<<<");
                System.out.println("\n\t\tMake your first guess!");
                gameManager.runGame();
                printFinalFlag();
                break;
            case 2:
                System.out.println("\n\t\t>>>>LAST GAME LOG<<<<");
                GameLogger.readFromLog(gameManager.getGameLogFilename());
                break;
            case 4:
                getRules();
                break;
            case 5:
                isRunning = false;
                break;
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
        try(BufferedReader in = new BufferedReader(new FileReader("rules.txt"))) {
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

    private void printFinalFlag() {
        System.out.println("_\n" +
                "\\'-,,.\n" +
                " \\    \\\n" +
                "  \\-..,\\\n" +
                "   \\\n" +
                "    \\\n");
    }
}
