package com.hodvidar.adventofcode.y2023;

public class Day01 extends AbstractAdventOfCode2023 {

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final String onlyDigit = line.replaceAll("\\D+", "");
        if (onlyDigit.isEmpty()) {
            return 0;
        }
        final char[] eachDigits = onlyDigit.toCharArray();
        return Integer.parseInt(""
                + eachDigits[0] + eachDigits[eachDigits.length - 1]);
    }
}
