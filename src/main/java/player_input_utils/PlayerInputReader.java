package player_input_utils;

import java.util.Scanner;

public class PlayerInputReader {
    public static String readPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String readPlayerLogDirName() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
