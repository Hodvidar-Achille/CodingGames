package com.hodvidar.adventofcode.y2019;

public final class Panel extends PaintedPoint {
    public static final int DARK = 0;
    public static final int WHITE = 1;

    public Panel(double x, double y) {
        super(x, y);
    }

    public Panel(double x, double y, int color) {
        super(x, y, color);
    }

    @Override
    public char printPoint() {
        if (this.value == DARK)
            return '.';
        else
            return '#';
    }

}
