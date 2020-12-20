package com.hodvidar.adventofcode.y2019;

public final class ScaffoldSurface extends PaintedSurface {

    @Override
    public PaintedPoint getPaintedPointImpl(double x, double y) {
        return new ScaffoldPanel(x, y);
    }

}
