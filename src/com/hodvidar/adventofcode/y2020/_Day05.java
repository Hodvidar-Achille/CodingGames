package com.hodvidar.adventofcode.y2020;

import java.util.Scanner;

public class _Day05 extends AbstractAdventOfCode {

    protected static final char FRONT = 'F';
    protected static final char BACK = 'B';
    protected static final char RIGHT = 'R';
    protected static final char LEFT = 'L';
    protected static final int MIN = 0;
    protected static final int MAX_ROW = 127;
    protected static final int MAX_COLUMN = 7;
    protected static final int ROW_CODE_FACTOR = 8;
    protected static final int MAX_CODE = calculateCode(MAX_ROW, MAX_COLUMN);

    public static void main(String[] args) throws Exception {
        _Day05 me = new _Day05();
        int result = me.getMaxPositionCode(me.getScanner());
        System.err.println("Expected '922' - result='" + result + "'");
    }

    public static int getFinalPositionCode(String spacePartitioning) {
        String rowSpacePartitioning = spacePartitioning.substring(0, 7);
        String columnSpacePartitioning = spacePartitioning.substring(7);

        int row = getFinalPosition(rowSpacePartitioning, MIN, MAX_ROW);
        int column = getFinalPosition(columnSpacePartitioning, MIN, MAX_COLUMN);

        return calculateCode(row, column);
    }

    private static int calculateCode(int row, int column) {
        return (row * ROW_CODE_FACTOR) + column;
    }

    public static int getFinalPosition(String spacePartitioning, int min, int max) {
        int[] range = new int[]{min, max};
        for (char partition : spacePartitioning.toCharArray()) {
            range = getNewRange(partition, range);
        }
        return range[0];
    }

    public static int[] getNewRange(char partition, int[] range) {
        int min = range[0];
        int max = range[1];
        int middle;
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

    public static int getMiddle(int min, int max, boolean roundCeil) {
        double middle = (min + max) / 2.0;
        return (int) ((roundCeil) ? Math.ceil(middle) : Math.floor(middle));
    }

    @Override
    protected int getDay() {
        return 5;
    }

    public int getMaxPositionCode(Scanner sc) {
        int maxFinalPositionCode = 0;
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            int code = getFinalPositionCode(line);
            if (code == MAX_CODE) {
                return MAX_CODE;
            }
            if (code > maxFinalPositionCode) {
                maxFinalPositionCode = code;
            }
        }
        return maxFinalPositionCode;
    }
}
