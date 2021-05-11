package com.hodvidar.adventofcode.y2019;

public class PaintedSurfaceAscii extends PaintedSurface {
    @Override
    public PaintedPoint getPaintedPointImpl(final double x, final double y) {
        return new PaintedPointAscii(x, y);
    }

}
