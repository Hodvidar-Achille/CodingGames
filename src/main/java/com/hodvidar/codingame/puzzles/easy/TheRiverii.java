package com.hodvidar.codingame.puzzles.easy;

import org.assertj.core.util.VisibleForTesting;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/the-river-ii- by Hodvidar
 **/
class TheRiverii {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int r1 = in.nextInt();

        System.err.println("r1:" + r1);
        final int nbOfRivers = findRivers(r1);
        System.err.println("nbOfRivers:" + nbOfRivers);
        final String result = (nbOfRivers >= 2) ? "YES" : "NO";

        System.out.println(result);
        in.close();
    }

    // ---- Easy to implement solution, not very optimized ----

    /**
     * Look for how many number that lead to number 'n' using the 'nextNumber(n)' method. Retuns 0
     * if no solution. Note : Optimization : don't look more than 2 solutions.
     */
    @VisibleForTesting
    static int findRivers(final long n) {
        int count = 0;
        for (int i = 1; i <= n; i++) {
            final long lower = n - i;
            final long next = nextNumber(lower);
            if (next == n) {
                // System.err.println("lower:"+lower);
                // System.err.println("next:"+next);
                count++;
                count += findRivers(lower);
            }
            if (count >= 2)
                return count;
        }

        return count;
    }

    /**
     * Returns n + the sum of its digits.
     */
    public static long nextNumber(final long n) {
        return n + sumDigits(n);
    }

    /**
     * Returns the sum of the digits of n. (Recursive)
     */
    public static long sumDigits(final long n) {
        return (n == 0L) ? 0L : (n % 10L) + sumDigits(n / 10L);
    }
}
