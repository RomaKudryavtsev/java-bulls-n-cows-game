package game_managers;

import logs_utils.GameLogger;
import model.PlayerTurnResult;
import player_input_utils.PlayerInputReader;
import player_input_utils.PlayerTurnAnalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameSessionManager {
    private static final int SECRET_NUMBER_DIGITS = 4;
    private final int secretNumber;
    private final PlayerTurnAnalyzer inputAnalyzer;
    private boolean isRunning;
    private String gameLogDirName;


    public GameSessionManager(String gameLogDirName) {
        this.secretNumber = generateSecretNumber();
        this.inputAnalyzer = new PlayerTurnAnalyzer(secretNumber);
        this.gameLogDirName = gameLogDirName;
    }

    public void runGame() {
        isRunning = true;
        List<PlayerTurnResult> currentGameResults = new ArrayList<>();
        int stepsCount = 0;
        while (isRunning) {
            ++stepsCount;
            String playerInput = PlayerInputReader.readPlayerInput();
            if (playerInput.equals("exit")) {
                isRunning = false;
            }
            PlayerTurnResult playerTurnResult = inputAnalyzer.getBullNCowsCount(playerInput);
            currentGameResults.add(playerTurnResult);
            currentGameResults.forEach(System.out::println);
            if (playerTurnResult.getBulls() == SECRET_NUMBER_DIGITS) {
                endGame(stepsCount, currentGameResults);
            }
        }
    }

    public String getGameLogDirName() {
        return gameLogDirName;
    }

    public void setGameLogDirName(String gameLogDirName) {
        this.gameLogDirName = gameLogDirName;
    }

    private void endGame(int stepsCount, List<PlayerTurnResult> currentGameResults) {
        isRunning = false;
        GameLogger.saveToLog(currentGameResults, stepsCount, gameLogDirName);
        printFinalFlag();
    }

    private int generateSecretNumber() {
        Random random = new Random();
        return IntStream.range(0, 10)
                .map(i -> random.nextInt(10))
                .distinct()
                .limit(4)
                .reduce(0, (a, b) -> a * 10 + b);
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
