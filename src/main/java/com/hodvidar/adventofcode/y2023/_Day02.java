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
        final Pattern pattern = Pattern.compile("Game (\\d+)");
        final Matcher matcher = pattern.matcher(line);
        int gameId = 0;
        if(matcher.find()) {
            gameId = Integer.parseInt(matcher.group(1));
        }
        final String[] partsAfterGameNumber = line.split(": ")[1].split(";");
        for (final String part : partsAfterGameNumber) {
            final String[] colorParts = part.split(",");
            for (String colorPart : colorParts) {
                colorPart = colorPart.trim(); // Remove leading and trailing spaces
                final String[] colorAndCount = colorPart.split(" ");
                // Assuming the format is always "<number> <color>"
                final int count = Integer.parseInt(colorAndCount[0]);
                final String color = colorAndCount[1];
                if(!isValid(color, count)) {
                    return 0;
                }
            }
        }
        return gameId;
    }

    private boolean isValid(final String color, final int count) {
        return switch (color) {
            case BLUE -> checker.checkBlue(count);
            case RED -> checker.checkRed(count);
            case GREEN -> checker.checkGreen(count);
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
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

        public boolean checkRed(final int numberOfRed) {
            return this.numberOfRed >= numberOfRed;
        }

        public boolean checkGreen(final int numberOfGreen) {
            return this.numberOfGreen >= numberOfGreen;
        }

        public boolean checkBlue(final int numberOfBlue) {
            return this.numberOfBlue >= numberOfBlue;
        }
    }
}
