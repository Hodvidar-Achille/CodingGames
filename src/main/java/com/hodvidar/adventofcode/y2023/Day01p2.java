package com.hodvidar.adventofcode.y2023;

import java.util.Map;

public class Day01p2 extends Day01 {

    private static final Map<String, String> NUMBER = Map.of(
            "one", "o1e",
            "two", "t2o",
            "three", "t3e",
            "four", "f4r",
            "five", "f5e",
            "six", "s6x",
            "seven", "s7n",
            "eight", "e8t",
            "nine", "n9e");

    @Override
    public double getDigitFromLine(final String line) {
        return super.getDigitFromLine(transformString_dirtyWithOverLapping(line));
    }

    public static String transformString_dirtyWithOverLapping(final String input) {
        String result = input;
        for (final Map.Entry<String, String> entry : NUMBER.entrySet()) {
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        for (final Map.Entry<String, String> entry : NUMBER.entrySet()) {
            result = result.replaceAll(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
