package com.hodvidar.adventofcode.y2025;

import java.util.Scanner;

public class Day03 extends AbstractAdventOfCode2025 {

    @Override
    public double getDigitFromLine(final String line) {
        final char[] digits = line.toCharArray();
        final int length = digits.length;
        final int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = digits[i] - '0';
        }
        int max = 0;
        int indexOfDigit1 = 0;
        for (int i = 0; i < length - 1; i++) {
            if (ints[i] > max) {
                max = ints[i];
                indexOfDigit1 = i;
            }
        }
        max = 0;
        int indexOfDigit2 = 0;
        for (int i = indexOfDigit1 + 1; i < length; i++) {
            if (ints[i] > max) {
                max = ints[i];
                indexOfDigit2 = i;
            }
        }

        final String numberStr = digits[indexOfDigit1] + "" + digits[indexOfDigit2];
        return Integer.parseInt(numberStr);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        double counter = 0;
        while (sc.hasNextLine()) {
            counter += this.getDigitFromLine(sc.nextLine());
        }
        return counter;
    }
}
