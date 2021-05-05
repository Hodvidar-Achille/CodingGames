package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/credit-card-verifier-luhns-algorithm by Hodvidar
 **/
public class CreditCardVerifierLuhnsAlgorithm {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < n; i++) {
            final String card = in.nextLine();
            final int[] cardNumbers = getCardNumbers(card);
            final boolean valid = checkCardNumbers(cardNumbers);
            if (valid)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
        in.close();
    }

    /**
     * Returns an array of 16 ints, ignore spaces ' '.
     *
     * @param card
     * @return
     */
    private static int[] getCardNumbers(final String card) {
        final int[] numbers = new int[16];
        int i = 0;
        for (final char c : card.toCharArray()) {
            if (c == ' ')
                continue;
            numbers[i] = Integer.valueOf("" + c);
            i++;
        }
        return numbers;
    }

    /**
     * @param cardNumbers must be an array of 16.
     * @return
     */
    private static boolean checkCardNumbers(final int[] cardNumbers) {
        // 1a) Double every second digit from right to left.
        // 1b) If this “doubling” results in a two-digit number, subtract 9 from it get a single
        // digit.
        // 2) Now add all single digit numbers from step 1.
        int sum = 0;
        for (int i = 14; i >= 0; i -= 2) {
            // 1a)
            int n = cardNumbers[i] * 2;
            // 1b)
            if (n > 9)
                n = n - 9;
            // 2)
            sum += n;
        }

        // 3) Add all digits in the odd places from right to left in the credit card number.
        int sum2 = 0;
        for (int i = 15; i >= 0; i -= 2)
            sum2 += cardNumbers[i];

        // 4) Sum the results from steps 2 & 3.
        sum += sum2;

        // 5) If the result from step 4 is divisible by 10, the card number is valid;
        return sum % 10 == 0;
    }
}
