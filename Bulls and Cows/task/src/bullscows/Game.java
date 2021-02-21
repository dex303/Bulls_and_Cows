package bullscows;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private int codeLength;
    private  String secretCode;
    private int turn = 1;

    public Game() {
        takeCodeLength();
        generateSecreteCode();
        System.out.println("Okay, let's start a game!");
        guessCode();
    }

    private void takeCodeLength() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");
        this.codeLength = scanner.nextInt();
        if (codeLength > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + codeLength +
                    " because there aren't enough unique digits.");
            System.exit(0);
        }
    }

    private void generateSecreteCode() {
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        do {
            Collections.shuffle(Arrays.asList(digits));
        } while (digits[0].equals("0"));
        secretCode = String.join("", Arrays.copyOfRange(digits, 0, codeLength));



        // the algorithm below complies with stage 3 requirements but is too slow
        // so program can't pass auto tests

        /*
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

         */
    }

    public void guessCode() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Turn " + turn + ":");
            turn++;
            String code = scanner.nextLine();

            // count bulls and cows
            int bulls = 0;
            int cows = 0;

            for (int i = 0; i < secretCode.length(); i++) {
                for (int j = 0; j < code.length(); j++) {
                    if (secretCode.charAt(i) == code.charAt(j)) {
                        if (i == j) {
                            bulls++;
                        } else {
                            cows++;
                        }
                    }
                }
            }

            String prefix;
            String bullMessage = "";
            String cowMessage = "";
            if (bulls > 0) {
                if (bulls == 1) {
                    bullMessage = "1 bull";
                } else {
                    bullMessage = bulls + " bulls";
                }
            }

            if (cows > 0) {
                if (cows == 1) {
                    cowMessage = "1 cow";
                } else {
                    cowMessage = cows + " cows";
                }
            }

            if (bulls > 0 && cows > 0) {
                prefix = "Grade: " + bullMessage + " and " + cowMessage;
            } else if (bulls > 0) {
                prefix = "Grade: " + bullMessage;
                if (bulls == codeLength) {
                    prefix += "\nCongratulations! You guessed the secret code.";
                    System.out.println(prefix);
                    System.exit(0);
                }
            } else if (cows > 0) {
                prefix = "Grade: " + cowMessage;
            } else {
                prefix = "None. ";
            }

            System.out.println(prefix);
        }
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
