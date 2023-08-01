package com.hodvidar.utils.geometry;

/**
 * 2D Point
 *
 * @author Hodvidar
 */
public class Point {
    public final double x;
    public final double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Point))
            return false;

        final Point p = (Point) obj;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return (int) (31 * this.x + 89 * this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + ")";
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}