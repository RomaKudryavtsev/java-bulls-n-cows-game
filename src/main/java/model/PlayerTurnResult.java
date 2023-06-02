package model;

import java.io.Serializable;

public class PlayerTurnResult implements Serializable {
    private final int nCows;
    private final int nBulls;
    private final int playersInput;

    public PlayerTurnResult(int nCows, int nBulls, int playersInput) {
        this.nCows = nCows;
        this.nBulls = nBulls;
        this.playersInput = playersInput;
    }

    public int getBulls() {
        return nBulls;
    }

    @Override
    public String toString() {
        return String.format("%d (%d cows, %d bulls)", playersInput, nCows, nBulls);
    }
}
