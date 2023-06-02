package model;

import java.io.Serializable;

public class PlayerTurnResult implements Serializable {
    private final int nCows;
    private final int nBulls;
    private final String playersInput;

    public PlayerTurnResult(int nCows, int nBulls, String playersInput) {
        this.nCows = nCows;
        this.nBulls = nBulls;
        this.playersInput = playersInput;
    }

    public int getBulls() {
        return nBulls;
    }

    public int getCows() {
        return nCows;
    }

    @Override
    public String toString() {
        return String.format("%s (%d cows, %d bulls)", playersInput, nCows, nBulls);
    }
}
