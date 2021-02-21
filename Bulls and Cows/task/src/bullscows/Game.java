package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private int codeLength;
    private  String secretCode = "";
    private int turn = 1;
    private int symbols;

    public Game() {
        takeCodeLength();
        generateSecreteCode();
        System.out.println("Okay, let's start a game!");
        guessCode();
    }

    private void takeCodeLength() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String inputCodeLength = scanner.nextLine();

        for (int i = 0; i < inputCodeLength.length(); i++) {
            if (!Character.isDigit(inputCodeLength.charAt(i))) {
                System.out.println("Error: \"" + inputCodeLength + "\" isn't a valid number.");
                System.exit(0);
            }
        }

        codeLength = Integer.parseInt(inputCodeLength);
        if (codeLength > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
        if (codeLength <= 0) {
            System.out.println("Error: The length of code should be from 1 to 36.");
            System.exit(0);
        }


        System.out.println("Input the number of possible symbols in the code:");
        String inputSymbols = scanner.nextLine();

        for (int i = 0; i < inputSymbols.length(); i++) {
            if (!Character.isDigit(inputSymbols.charAt(i))) {
                System.out.println("Error: \"" + inputSymbols + "\" isn't a valid number.");
                System.exit(0);
            }
        }

        symbols = Integer.parseInt(inputSymbols);
        if (symbols < codeLength) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeLength +
                    " with " + symbols + " unique symbols.");
            System.exit(0);
        }
        if (symbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
    }

    private void generateSecreteCode() {
        Random random = new Random();
        int nextDigit;

        for (int i = 0; i < codeLength; i++) {
            nextDigit = random.nextInt(10);

            while (i == 0 && nextDigit == 0) {
                nextDigit = random.nextInt(10);
            }

            while (secretCode.contains(Integer.toString(nextDigit))) {
                nextDigit = random.nextInt(10);
            }
            secretCode += nextDigit;
        }

        StringBuilder msg = new StringBuilder();
        msg.append("The secret is prepared: ");
        msg.append("*".repeat(Math.max(0, codeLength)));
        if (symbols <= 10) {
            msg.append(" (0-" + (symbols - 1) + ").");
        } else {
            msg.append(" (0-9), (a-");
            char c = (char) (86 + symbols);
            msg.append(c + ").");
        }
        System.out.println(msg);

        // Stage 5 require new method (above) to generate secret code with using Math.random

        /*
        String[] digits = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        do {
            Collections.shuffle(Arrays.asList(digits));
        } while (digits[0].equals("0"));
        secretCode = String.join("", Arrays.copyOfRange(digits, 0, codeLength));

         */


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
            if (code.length() < codeLength || code.length() > codeLength) {
                System.out.println("Error: your code should consists from " + codeLength + " sights.");
                System.exit(0);
            }

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
}
