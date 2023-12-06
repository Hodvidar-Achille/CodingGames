package com.hodvidar.adventofcode.y2023;

import org.assertj.core.util.Streams;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class _Day02 extends AbstractAdventOfCode2023 {

    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String GREEN = "green";
    private final CubeConditionChecker checker;

    public _Day02() {
        this.checker = new CubeConditionChecker(12, 13, 14);
    }

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        int counter = 0;
        while(sc.hasNext()) {
            final String line = sc.nextLine();
            counter += getDigitFromLine(line);
        }
        return counter;
    }

    protected int getDigitFromLine(final String line) {
        Pattern pattern = Pattern.compile("Game (\\d+)");
        Matcher matcher = pattern.matcher(line);
        int gameId = 0;
        if(matcher.find()) {
            gameId = Integer.parseInt(matcher.group(1));
        }
        final String[] partsAfterGameNumber = line.split(": ")[1].split(";");
        final Map<String, Integer> colorMaxCounts = new HashMap<>();
        colorMaxCounts.put(BLUE, 0);
        colorMaxCounts.put(RED, 0);
        colorMaxCounts.put(GREEN, 0);
        for (final String part : partsAfterGameNumber) {
            final String[] colorParts = part.split(",");
            for (String colorPart : colorParts) {
                colorPart = colorPart.trim(); // Remove leading and trailing spaces
                String[] colorAndCount = colorPart.split(" ");
                // Assuming the format is always "<number> <color>"
                final int count = Integer.parseInt(colorAndCount[0]);
                final String color = colorAndCount[1];
                if(colorMaxCounts.get(color) < count) {
                    colorMaxCounts.put(color, count);
                }
            }
        }
        return checker.checkCubeConditions(colorMaxCounts.get(RED), colorMaxCounts.get(GREEN), colorMaxCounts.get(BLUE)) ? gameId : 0;
    }

    public boolean checkCubeConditions(final int numberOfRed, final int numberOfGreen, final int numberOfBlue) {
        return checker.checkCubeConditions(numberOfRed, numberOfGreen, numberOfBlue);
    }

    @Override
    public int getDay() {
        return 2;
    }

    public static class CubeConditionChecker {
        private final int numberOfRed;
        private final int numberOfGreen;
        private final int numberOfBlue;

        public CubeConditionChecker(final int numberOfRed, final int numberOfGreen, final int numberOfBlue) {
            this.numberOfRed = numberOfRed;
            this.numberOfGreen = numberOfGreen;
            this.numberOfBlue = numberOfBlue;
        }

        public boolean checkCubeConditions(final int numberOfRed, final int numberOfGreen, final int numberOfBlue) {
            return this.numberOfRed >= numberOfRed
                    && this.numberOfGreen >= numberOfGreen
                    && this.numberOfBlue >= numberOfBlue;
        }
    }
}
