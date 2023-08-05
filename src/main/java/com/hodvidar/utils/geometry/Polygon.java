package com.hodvidar.utils.geometry;

import java.util.Objects;

public class Polygon implements GeometricForm {
    public final Point[] points;
    public final int numberOfPoints;
    /**
     * X coordinate of the point with the max X.
     */
    public final double maxX;

    private final GeometryServices geometryServices;

    public Polygon(final Point... points) {
        if (!checkPoints(points)) {
            throw new IllegalArgumentException("Every points must be different in a Polygon"
                    + " and there must be at least 3 of them.");
        }

        this.points = points;
        this.numberOfPoints = points.length;

        // Compute max_X needed for method isInside.
        double x = Double.MIN_VALUE;
        for (final Point p : this.points) {
            if (p.getX() > x)
                x = p.getX();
        }
        this.maxX = x;
        this.geometryServices = new GeometryServices(this.maxX);
    }

    @Override
    public boolean checkPoints(final Point... points) {
        if (points.length < 3) {
            return false;
        }
        // Every point must be different.
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (Objects.equals(points[i], points[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isInside(final Point p) {
        return geometryServices.isInside(this.points, this.numberOfPoints, p);
    }
}