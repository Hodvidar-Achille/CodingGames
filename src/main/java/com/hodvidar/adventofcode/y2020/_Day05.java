package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day05 extends AbstractAdventOfCode2020 {

    protected static final char FRONT = 'F';
    protected static final char BACK = 'B';
    protected static final char RIGHT = 'R';
    protected static final char LEFT = 'L';
    protected static final int MIN = 0;
    protected static final int MAX_ROW = 127;
    protected static final int MAX_COLUMN = 7;
    protected static final int ROW_CODE_FACTOR = 8;
    protected static final int MAX_CODE = calculateCode(MAX_ROW, MAX_COLUMN);

    public static int getFinalPositionCode(final String spacePartitioning) {
        final String rowSpacePartitioning = spacePartitioning.substring(0, 7);
        final String columnSpacePartitioning = spacePartitioning.substring(7);

        final int row = getFinalPosition(rowSpacePartitioning, MIN, MAX_ROW);
        final int column = getFinalPosition(columnSpacePartitioning, MIN, MAX_COLUMN);

        return calculateCode(row, column);
    }

    private static int calculateCode(final int row, final int column) {
        return (row * ROW_CODE_FACTOR) + column;
    }

    public static int getFinalPosition(final String spacePartitioning, final int min, final int max) {
        int[] range = new int[]{min, max};
        for (final char partition : spacePartitioning.toCharArray()) {
            range = getNewRange(partition, range);
        }
        return range[0];
    }

    public static int[] getNewRange(final char partition, final int[] range) {
        final int min = range[0];
        final int max = range[1];
        final int middle;
        switch (partition) {
            case BACK:
            case RIGHT:
                middle = getMiddle(min, max, true);
                return new int[]{middle, max};
            case FRONT:
            case LEFT:
                middle = getMiddle(min, max, false);
                return new int[]{min, middle};
            default:
                throw new IllegalStateException("Unknown partition code");
        }
    }

    public static int getMiddle(final int min, final int max, final boolean roundCeil) {
        final double middle = (min + max) / 2.0;
        return (int) ((roundCeil) ? Math.ceil(middle) : Math.floor(middle));
    }

    public static int getMaxPositionCode(final Scanner sc) {
        int maxFinalPositionCode = 0;
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            final int code = getFinalPositionCode(line);
            if (code == MAX_CODE) {
                return MAX_CODE;
            }
            if (code > maxFinalPositionCode) {
                maxFinalPositionCode = code;
            }
        }
        return maxFinalPositionCode;
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        return getMaxPositionCode(sc);
    }
}
