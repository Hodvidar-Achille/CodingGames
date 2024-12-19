package com.hodvidar.leetcode.medium;

public class IntegerToRoman {

    // 1
    private static final String I = "I";

    // 5
    private static final String V = "V";

    // 10
    private static final String X = "X";

    // 50
    private static final String L = "L";

    // 100
    private static final String C = "C";

    // 500
    private static final String D = "D";

    // 1000
    private static final String M = "M";

    public static String intToRoman(final int num) {
        final StringBuilder romanNumber = new StringBuilder();
        int rest = num;
        int power = 0;
        while (rest > 0) {
            final int digit = rest % 10;
            romanNumber.insert(0, getRomanNumber(digit, power));
            rest = rest / 10;
            power++;
        }
        return romanNumber.toString();
    }

    private static String getRomanNumber(final int digit, final int power) {
        return switch (power) {
            case 0 -> getRomanNumberForDigit(digit, I, V, X);
            case 1 -> getRomanNumberForDigit(digit, X, L, C);
            case 2 -> getRomanNumberForDigit(digit, C, D, M);
            case 3 -> M.repeat(digit);
            default -> throw new IllegalArgumentException("Power must be between 0 and 3 included.");
        };
    }

    private static String getRomanNumberForDigit(final int digit, final String one, final String five, final String ten) {
        return switch (digit) {
            case 0 -> "";
            case 1 -> one;
            case 2 -> one + one;
            case 3 -> one + one + one;
            case 4 -> one + five;
            case 5 -> five;
            case 6 -> five + one;
            case 7 -> five + one + one;
            case 8 -> five + one + one + one;
            case 9 -> one + ten;
            default -> throw new IllegalArgumentException("Digit must be between 0 and 9 included.");
        };
    }


    public static String intToRomaOptimized(final int num) {
        final String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        final String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        final String[] hrns = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};

        String romanNumeral = "M".repeat(num / 1000) +
                hrns[(num % 1000) / 100] +
                tens[(num % 100) / 10] +
                ones[num % 10];

        return romanNumeral;
    }
}
