package com.hodvidar.adventofcode.y2019;

public final class GameSurface extends PaintedSurface {

    @Override
    public PaintedPoint getPaintedPointImpl(final double x, final double y) {
        return new GamePixel(x, y);
    }

}
