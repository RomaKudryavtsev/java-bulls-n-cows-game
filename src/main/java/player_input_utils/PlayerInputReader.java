package player_input_utils;

import java.util.Scanner;

public class PlayerInputReader {
    public static int readPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String readPlayerLogDirName() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
