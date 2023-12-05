package com.hodvidar.adventofcode.y2023;

import java.util.Arrays;

public class AdventOfCode2023 {

    public static double getCalibrationValue(final String[] lines) {
        return Arrays.stream(lines).mapToDouble(AdventOfCode2023::getDigitFromLine).sum();
    }

    private static double getDigitFromLine(final String line) {
        final String onlyDigit = line.replaceAll("\\D+", "");
        if (onlyDigit.isEmpty()) {
            return 0d;
        }
        final char[] eachDigits = onlyDigit.toCharArray();
        return Double.parseDouble(""
                + eachDigits[0] + eachDigits[eachDigits.length - 1]);

    }
}
