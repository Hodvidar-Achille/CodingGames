package com.hodvidar.adventofcode.y2019;

public final class GameSurface extends PaintedSurface {

    @Override
    public PaintedPoint getPaintedPointImpl(double x, double y) {
        return new GamePixel(x, y);
    }

}
