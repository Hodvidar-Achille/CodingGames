package com.hodvidar.leetcode.easy;

/**
 * https://leetcode.com/problems/roman-to-integer/
 */
public class RomanToInt {

    // Runtime 2ms  "Beats 100% of Java users"
    public static int romanToInt(String s) {
        if (s.length() == 1) return getRomanSingleNumber(s.charAt(0));
        int total = 0;
        final char[] romainLettersReversed = s.toCharArray();
        int length = romainLettersReversed.length;
        int previousNumber = 0;
        for (int i = length - 1; i >= 0; i--) {
            final char c = romainLettersReversed[i];
            final int number = getRomanSingleNumber(c);
            total += (number < previousNumber) ? -number : number;
            previousNumber = number;
        }
        return total;
    }

    private static int getRomanSingleNumber(final char c) {
        return switch (c) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }
}
