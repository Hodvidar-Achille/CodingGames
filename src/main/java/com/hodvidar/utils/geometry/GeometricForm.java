package com.hodvidar.utils.geometry;

interface GeometricForm {
    /**
     * @param points
     * @return {@code true} if the points are OK for the object type.
     */
    boolean checkPoints(final Point... points);

    boolean isInside(Point p);
}