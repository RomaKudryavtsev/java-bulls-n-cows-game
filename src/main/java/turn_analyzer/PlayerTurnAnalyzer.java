package turn_analyzer;

import model.PlayerTurnResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerTurnAnalyzer {
    private final Map<Integer, List<Integer>> numbersAndIndexesMap;

    public PlayerTurnAnalyzer(Integer secretNumber) {
        String numStr = secretNumber.toString();
        final int[] index = {0};
        numbersAndIndexesMap = numStr.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toMap(c -> Integer.parseInt(String.valueOf(c)),
                        c -> {
                            List<Integer> indexes = new ArrayList<>();
                            indexes.add(index[0]);
                            ++index[0];
                            return indexes;
                        },
                        (oldVal, newVal) -> {
                            oldVal.addAll(newVal);
                            return oldVal;
                        }));
    }

    public PlayerTurnResult getBullNCowsCount(int playersInput) {
        int[] nCows = {0};
        int[] nBulls = {0};
        int[] index = {0};
        String.valueOf(playersInput).chars()
                .mapToObj(i -> (char) i)
                .map(Character::getNumericValue)
                .forEach(i -> {
                    if (numbersAndIndexesMap.get(i) != null) {
                        if (numbersAndIndexesMap.get(i).contains(index[0])) {
                            ++nBulls[0];
                        } else {
                            ++nCows[0];
                        }
                    }
                    ++index[0];
                });
        return new PlayerTurnResult(nCows[0], nBulls[0], playersInput);
    }
}
