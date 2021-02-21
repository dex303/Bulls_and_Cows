package bullscows;

import java.util.Scanner;

public class Game {
    private int codeLength;
    private  String secretCode;

    public Game() {
        takeCodeLength();
        generateSecreteCode();
        System.out.println("The random secret number is " + secretCode + ".");
    }

    private void takeCodeLength() {
        Scanner scanner = new Scanner(System.in);
        this.codeLength = scanner.nextInt();
        if (codeLength > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + codeLength +
                    " because there aren't enough unique digits.");
            System.exit(0);
        }
    }

    private void generateSecreteCode() {
        long pseudoRandomNumber;
        StringBuilder buildCode = new StringBuilder();
        boolean again;

        do {
            secretCode = "";
            pseudoRandomNumber = System.nanoTime();
            again = false;
            for (int i = 0; i < codeLength; i++) {
                buildCode.append(Character.getNumericValue(Long.toString(pseudoRandomNumber).charAt(i)));
            }
            buildCode.reverse();

            for (int i = 0; i < codeLength; i++) {
                secretCode += buildCode.charAt(i);
            }


            // check secret code: first digit can't be 0, all digits should be unique
            int repeatingDigits = 0;

            if (secretCode.charAt(0) == 0) {
                again = true;
            } else {
                for (int k = 0; k < secretCode.length(); k++) {
                    for (int l = 0; l < codeLength; l++) {
                        if (k != l && secretCode.charAt(k) == secretCode.charAt(l)) {
                            repeatingDigits++;
                        }
                    }
                    if (repeatingDigits > 0) {
                        again = true;
                        break;
                    }
                    repeatingDigits = 0;
                }
            }
        } while (again);
    }

    public String guessCode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your code:");
        String code = scanner.nextLine();

        // count bulls and cows
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < secretCode.length(); i++) {
            if (secretCode.charAt(i) == code.charAt(0)) {
                if (i == 0) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (secretCode.charAt(i) == code.charAt(1)) {
                if (i == 1) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (secretCode.charAt(i) == code.charAt(2)) {
                if (i == 2) {
                    bulls++;
                } else {
                    cows++;
                }
            }

            if (secretCode.charAt(i) == code.charAt(3)) {
                if (i == 3) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }

        String prefix;
        String suffix = "The secret code is " + secretCode + ".";
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
