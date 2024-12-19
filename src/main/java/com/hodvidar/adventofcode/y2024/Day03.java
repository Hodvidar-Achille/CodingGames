package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 extends AbstractAdventOfCode2024 {
    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final List<String> correctMultiplicationOrder = extractValidMulItems(line);
        return correctMultiplicationOrder.stream().mapToDouble(Day03::Multiply).sum();
    }

    private static double Multiply(final String multiplyOrder) {
        final String content = multiplyOrder.substring(multiplyOrder.indexOf('(') + 1, multiplyOrder.indexOf(')'));
        final String[] parts = content.split(",");
        final int number1 = Integer.parseInt(parts[0]);
        final int number2 = Integer.parseInt(parts[1]);
        return (double) number1 * (double) number2;
    }

    public static List<String> extractValidMulItems(final String input) {
        // Regex pattern to match valid mul(...) items
        final String regex = "mul\\(\\d+,\\d+\\)";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(input);
        final List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }
}
