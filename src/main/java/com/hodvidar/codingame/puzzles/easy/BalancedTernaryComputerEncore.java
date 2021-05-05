package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/balanced-ternary-computer-encode by Hodvidar
 **/
public class BalancedTernaryComputerEncore {
    private static final char MinusOne = 'T';
    private static final char One = '1';
    private static final char Zero = '0';

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        System.err.println("N=" + N);
        final String result = convertToTernary(N);
        System.out.println(result);
        in.close();

    }

    private static String convertToTernary(int number) {
        String result = "";
        if (number == 0)
            return result += Zero;

        final boolean isNegatif = (number < 0);
        if (isNegatif)
            number *= -1;
        while (number != 0) {
            final int rest = number % 3;
            result = getTernaryForRest(rest, isNegatif) + result;
            number = (number + 1) / 3;
        }
        return result;
    }

    private static char getTernaryForRest(final int i, final boolean isNegatif) {
        if (i == 0)
            return Zero;
        if (i == 1)
            return (isNegatif) ? MinusOne : One;
        if (i == 2)
            return (isNegatif) ? One : MinusOne;
        throw new IllegalArgumentException("Only value '0', '1' and '2' are allowed");
    }
}
