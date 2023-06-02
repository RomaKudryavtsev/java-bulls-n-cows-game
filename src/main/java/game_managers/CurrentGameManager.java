package game_managers;

import logs_utils.GameLogger;
import model.PlayerTurnResult;
import player_input_utils.PlayerInputReader;
import player_input_utils.PlayerTurnAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CurrentGameManager {
    private static final int SECRET_NUMBER_DIGITS = 4;
    private static final int MAX_SECRET_NUMBER = 9999;
    private final int secretNumber;
    private boolean isRunning;
    private final PlayerTurnAnalyzer inputAnalyzer;
    private String gameLogFileName;
    private String gameLogDirName;

    public CurrentGameManager() {
        //this.secretNumber = generateSecretNumber();
        //TODO - change to random number
        this.secretNumber = 1224;
        this.inputAnalyzer = new PlayerTurnAnalyzer(secretNumber);
    }

    public CurrentGameManager(String dirName) {
        this();
        this.gameLogDirName = dirName;
    }

    public void runGame() {
        isRunning = true;
        List<PlayerTurnResult> currentGameResults = new ArrayList<>();
        int stepsCount = 0;
        while(isRunning) {
            ++stepsCount;
            int playerInput = PlayerInputReader.readPlayerInput();
            if(playerInput == -1) {
                isRunning = false;
            }
            PlayerTurnResult playerTurnResult = inputAnalyzer.getBullNCowsCount(playerInput);
            currentGameResults.add(playerTurnResult);
            currentGameResults.forEach(System.out::println);
            if(playerTurnResult.getBulls() == SECRET_NUMBER_DIGITS) {
                endGame(stepsCount, currentGameResults);
            }
        }
    }

    public String getGameLogFileName() {
        return gameLogFileName;
    }

    private void endGame(int stepsCount, List<PlayerTurnResult> currentGameResults) {
        isRunning = false;
        if (gameLogDirName == null) {
            gameLogFileName = GameLogger.saveToLog(currentGameResults, stepsCount);
        } else {
            gameLogFileName = GameLogger.saveToLog(currentGameResults, stepsCount, gameLogDirName);
        }
        printFinalFlag();
    }

    private int generateSecretNumber() {
        Random random = new Random();
        return random.nextInt(MAX_SECRET_NUMBER);
    }

    private void printFinalFlag() {
        System.out.println("\t\t%%%%CONGRATULATIONS!%%%%");
        System.out.println("\t\t\tSECRET NUMBER IS: " + secretNumber);
        System.out.println("_\n" +
                "\\'-,,.\n" +
                " \\    \\\n" +
                "  \\-..,\\\n" +
                "   \\\n" +
                "    \\\n");
    }
}
