package com.hodvidar.adventofcode.y2019;

public final class ScaffoldSurface extends PaintedSurface {

    @Override
    public PaintedPoint getPaintedPointImpl(final double x, final double y) {
        return new ScaffoldPanel(x, y);
    }

}
