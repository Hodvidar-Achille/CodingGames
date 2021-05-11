package com.hodvidar.adventofcode.y2019;

public final class PaintedPointAscii extends PaintedPoint {

    public static final int EMPTY = '.';

    public PaintedPointAscii(final double x, final double y) {
        this(x, y, EMPTY);
    }

    public PaintedPointAscii(final double x, final double y, final int value) {
        super(x, y, value);
    }

    @Override
    public char printPoint() {
        return (char) this.value;
    }

}
