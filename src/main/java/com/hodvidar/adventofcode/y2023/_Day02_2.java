package com.hodvidar.adventofcode.y2023;

import java.util.HashMap;
import java.util.Map;

public class _Day02_2 extends _Day02 {

    @Override
    protected int computeDigitLogic(final String[] partsAfterGameNumber, final int gameId) {
        return computeGame(partsAfterGameNumber);
    }

    @Override
    protected int computeGame(final String[] partsAfterGameNumber) {
        final Map<String, Integer> maxColors =  new HashMap<>(Map.of(RED, 0, GREEN, 0, BLUE, 0));
        for (final String part : partsAfterGameNumber) {
            final String[] colorParts = part.split(",");
            for (String colorPart : colorParts) {
                colorPart = colorPart.trim(); // Remove leading and trailing spaces
                final String[] colorAndCount = colorPart.split(" ");
                // Assuming the format is always "<number> <color>"
                final int count = Integer.parseInt(colorAndCount[0]);
                final String color = colorAndCount[1];
                switch (color) {
                    case BLUE -> maxColors.put(BLUE, Math.max(maxColors.get(BLUE), count));
                    case RED -> maxColors.put(RED, Math.max(maxColors.get(RED), count));
                    case GREEN -> maxColors.put(GREEN, Math.max(maxColors.get(GREEN), count));
                    default -> throw new IllegalStateException("Unexpected value: " + color);
                }
            }
        }
        return maxColors.get(RED) * maxColors.get(GREEN) * maxColors.get(BLUE);
    }
}
