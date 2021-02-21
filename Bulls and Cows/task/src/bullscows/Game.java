package bullscows;

import java.util.Scanner;

public class Game {
    private final String SECRET_CODE = "9305";

    public Game() {
        // Scanner scanner = new Scanner(System.in);
        // this.SECRET_CODE = scanner.nextLine();
    }

    public String guessCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your code:");
        String code = scanner.nextLine();

        // count bulls and cows
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < SECRET_CODE.length(); i++) {
            if (SECRET_CODE.charAt(i) == code.charAt(0)) {
                if (i == 0) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (SECRET_CODE.charAt(i) == code.charAt(1)) {
                if (i == 1) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (SECRET_CODE.charAt(i) == code.charAt(2)) {
                if (i == 2) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (SECRET_CODE.charAt(i) == code.charAt(3)) {
                if (i == 3) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }

        String prefix;
        String suffix = "The secret code is " + SECRET_CODE + ".";
        if (bulls > 0 && cows > 0) {
            prefix = "Grade: " + bulls + " bull(s) and " + cows + " cow(s). ";
        } else if (bulls > 0) {
            prefix = "Grade: " + bulls + " bull(s). ";
        } else if (cows > 0) {
            prefix = "Grade: " + cows + " cow(s). ";
        } else {
            prefix = "None. ";
        }

        return prefix + suffix;
    }

    public void printLog() {
        System.out.println("The secret code is prepared: ****.\n" +
                "\n" +
                "Turn 1. Answer:\n" +
                "1234\n" +
                "Grade: None.\n" +
                "\n" +
                "Turn 2. Answer:\n" +
                "9876\n" +
                "Grade: 4 bulls.\n" +
                "Congrats! The secret code is 9876.");
    }
}
