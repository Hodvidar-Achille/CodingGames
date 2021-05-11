package com.hodvidar.adventofcode.y2019;

public final class MazePoint extends PaintedPoint {
    public static final int WALL = 0;
    public static final int EMPTY = 1;
    public static final int OXYGEN = 2;
    public static final int UNKOWN = 3;
    public static final int START = 4;
    private int countFromStart = -1;

    public MazePoint(final double x, final double y) {
        super(x, y);
    }

    public MazePoint(final double x, final double y, final int value) {
        super(x, y, value);
    }

    public MazePoint(final double x, final double y, final int value, final int countFromStart) {
        super(x, y, value);
        this.countFromStart = countFromStart;
    }

    public int getCountFromStart() {
        return this.countFromStart;
    }

    /**
     * Only keeps lower value
     **/
    public void setCountFromStart(final int countFromStart) {
        if (countFromStart == -1) {
            this.countFromStart = -1;
            return;
        }

        if (this.countFromStart != -1 && countFromStart > this.countFromStart)
            return;

        this.countFromStart = countFromStart;
    }

    @Override
    public boolean paint(final int newValue) {
        if (this.value == START)
            return false;

        return super.paint(newValue);
    }

    @Override
    public char printPoint() {
        switch (value) {
            case WALL:
                return '#';
            case EMPTY:
                return '.';
            case OXYGEN:
                return 'O';
            case UNKOWN:
                return '?';
            case START:
                return 'S';
            default:
                throw new IllegalStateException("Unknown value:" + value);
        }
    }

}
