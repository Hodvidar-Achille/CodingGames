package com.hodvidar.adventofcode.y2023;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends AbstractAdventOfCode2023 {

    public static final int NUMBER_OF_RED = 12;
    protected static final String BLUE = "blue";
    protected static final String RED = "red";
    protected static final String GREEN = "green";
    private final CubeConditionChecker checker;

    public Day02() {
        this.checker = new CubeConditionChecker(NUMBER_OF_RED, 13, 14);
    }


    @Override
    public double getDigitFromLine(final String line) {
        final Pattern pattern = Pattern.compile("Game (\\d+)");
        final Matcher matcher = pattern.matcher(line);
        int gameId = 0;
        if (matcher.find()) {
            gameId = Integer.parseInt(matcher.group(1));
        }
        final String[] partsAfterGameNumber = line.split(": ")[1].split(";");
        return computeDigitLogic(partsAfterGameNumber, gameId);
    }

    protected int computeDigitLogic(final String[] partsAfterGameNumber, final int gameId) {
        final var x = computeGame(partsAfterGameNumber);
        return x == 0 ? 0 : gameId;
    }

    protected int computeGame(final String[] partsAfterGameNumber) {
        for (final String part : partsAfterGameNumber) {
            final String[] colorParts = part.split(",");
            for (String colorPart : colorParts) {
                colorPart = colorPart.trim(); // Remove leading and trailing spaces
                final String[] colorAndCount = colorPart.split(" ");
                // Assuming the format is always "<number> <color>"
                final int count = Integer.parseInt(colorAndCount[0]);
                final String color = colorAndCount[1];
                if (!isValid(color, count)) {
                    return 0;
                }
            }
        }
        return -1;
    }

    private boolean isValid(final String color, final int count) {
        return switch (color) {
            case BLUE -> checker.checkBlue(count);
            case RED -> checker.checkRed(count);
            case GREEN -> checker.checkGreen(count);
            default -> throw new IllegalStateException("Unexpected value: " + color);
        };
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
