package com.hodvidar.codingame.puzzles.medium;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// https://www.codingame.com/contribute/view/130926c04bc8346fd292fbb7a6fd44bcaa7450
public class NumberWithOnlyTwoDistinctDigits {

    public static void main(final String[] args) {
        final Scanner sc = new Scanner(System.in);
        final int n = sc.nextInt();
        final int a = sc.nextInt();
        final int b = sc.nextInt();

        final var ans = findNumber(a, b, n);

        System.err.println("result: " + ans +
                " is divided by 2^" + n + " ? : " + isDivisibleByPowerOfTwo(ans, n));
        System.out.println(ans);
    }

    public static boolean isDivisibleByPowerOfTwo(final StringBuilder numberBuilder, final int n) {
        // Convert the digit string back to a BigInteger
        final BigInteger value = new BigInteger(numberBuilder.toString());

        // 2ⁿ = 1 << n  (as a BigInteger)
        final BigInteger divisor = BigInteger.ONE.shiftLeft(n);

        // Check remainder
        return value.mod(divisor).equals(BigInteger.ZERO);
    }

    public static StringBuilder findNumber(final int a, final int b, final int n) {

        final int odd = (a & 1) != 0 ? a : b;
        final int even = a + b - odd;

        final var reversedDigits = getReversedDigits(n, even, odd);

        // Reverse the collected digits to obtain the final string
        final StringBuilder ans = new StringBuilder(n);
        for (int i = reversedDigits.size() - 1; i >= 0; i--) {
            ans.append(reversedDigits.get(i));
        }
        return ans;
    }

    private static List<Character> getReversedDigits(final int n, final int even, final int odd) {
        BigInteger k = BigInteger.ZERO;
        // digits collected in reverse order
        final List<Character> revDigits = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            final int d = (k.testBit(0)) ? odd : even;
            revDigits.add((char) ('0' + d));
            final BigInteger fivePow = BigInteger.valueOf(5).pow(i - 1);
            final BigInteger term = BigInteger.valueOf(d).multiply(fivePow);
            // divide by 2 using a right shift
            k = term.add(k).shiftRight(1);
        }
        return revDigits;
    }
}
