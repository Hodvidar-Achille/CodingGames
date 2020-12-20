package com.hodvidar.utils.geometry;

interface GeometricForm {
    /**
     * @param points
     * @return {@code true} if the points are OK for the object type.
     */
    static boolean checkPoints(Point... points) {
        return true;
    }

    boolean isInside(Point p);
}