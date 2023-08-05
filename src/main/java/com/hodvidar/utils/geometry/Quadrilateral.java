package com.hodvidar.utils.geometry;

public class Quadrilateral extends Polygon {
    public Quadrilateral(final Point... points) {
        super(points);

        // Must have 4 points.
        if (!checkPoints(points))
            throw new IllegalArgumentException("Quadrilateral must have 4 points");
    }

    @Override
    public boolean checkPoints(final Point... points) {
        return points.length == 4;
    }
}