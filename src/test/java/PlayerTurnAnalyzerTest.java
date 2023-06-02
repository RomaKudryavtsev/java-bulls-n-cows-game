import model.PlayerTurnResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player_input_utils.PlayerTurnAnalyzer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTurnAnalyzerTest {
    private PlayerTurnAnalyzer analyzer;
    @BeforeEach
    void setUp() {
        analyzer = new PlayerTurnAnalyzer(1234);
    }

    @Test
    void test1Cow2Bulls() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("1235");
        assertEquals(3, result.getBulls());
        assertEquals(0, result.getCows());
    }

    @Test
    void test0Cows3Bulls() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("f234");
        assertEquals(3, result.getBulls());
        assertEquals(0, result.getCows());
    }

    @Test
    void testAllCows() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("4321");
        assertEquals(0, result.getBulls());
        assertEquals(4, result.getCows());
    }

    @Test
    void testAllBulls() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("1234");
        assertEquals(4, result.getBulls());
        assertEquals(0, result.getCows());
    }

    @Test
    void testNoCowsNoBulls() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("5678");
        assertEquals(0, result.getBulls());
        assertEquals(0, result.getCows());
    }

    @Test
    void testRepeatedDigitInInput() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("1224");
        assertEquals(3, result.getBulls());
        assertEquals(1, result.getCows());
    }

    @Test
    void testLongInputWithExtraDigits() {
        PlayerTurnResult result = analyzer.getBullNCowsCount("123456");
        assertEquals(4, result.getBulls());
        assertEquals(0, result.getCows());
    }
}
