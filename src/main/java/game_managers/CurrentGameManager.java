package game_managers;

import logger.GameLogger;
import model.PlayerTurnResult;
import turn_analyzer.PlayerTurnAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CurrentGameManager {
    private static final int SECRET_NUMBER_DIGITS = 4;
    private static final int MAX_SECRET_NUMBER = 9999;
    private final int secretNumber;
    private final PlayerTurnAnalyzer inputAnalyzer;
    private String gameLogFilename;

    public CurrentGameManager() {
        //this.secretNumber = generateSecretNumber();
        //TODO - change to random number
        this.secretNumber = 1224;
        this.inputAnalyzer = new PlayerTurnAnalyzer(secretNumber);
    }

    public void runGame() {
        boolean isRunning = true;
        List<PlayerTurnResult> currentGameResults = new ArrayList<>();
        int stepsCount = 0;
        while(isRunning) {
            ++stepsCount;
            int playerInput = getPlayerInput();
            PlayerTurnResult playerTurnResult = inputAnalyzer.getBullNCowsCount(playerInput);
            currentGameResults.add(playerTurnResult);
            currentGameResults.forEach(System.out::println);
            if(playerTurnResult.getBulls() == SECRET_NUMBER_DIGITS) {
                isRunning = false;
                gameLogFilename = GameLogger.saveToLog(currentGameResults, stepsCount);
                System.out.println("\t\t%%%%CONGRATULATIONS!%%%%");
                System.out.println("\t\t\tSECRET NUMBER IS: " + secretNumber);
            }
        }
    }

    public String getGameLogFilename() {
        return gameLogFilename;
    }

    private int getPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private int generateSecretNumber() {
        Random random = new Random();
        return random.nextInt(MAX_SECRET_NUMBER);
    }
}
