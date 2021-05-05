package com.hodvidar.utils.geometry;

public class Square extends Rectangle {
    public final Point center;
    /**
     * Side length;
     */
    public final double side;

    /**
     * In degrees°, inclination of the square given by the first 2 points and the X axe.
     * (Clockwise).
     */
    public final double angle;

    public Square(final Point... points) {
        super(points);

        if (!checkPoints(points))
            throw new IllegalArgumentException("Points do not form a square.");

        this.side = GeometryServices.getDistance(points[0], points[1]);
        this.center = GeometryServices.getCenter(points[0], points[1], points[2]);
        this.angle = this.getInclination();
    }

    public Square(final Point center, final double side, final double angle) {
        super(GeometryServices.createSquarePoints(center, side, angle));
        this.center = center;
        this.side = side;
        this.angle = angle;
    }

    /**
     * Similar to the checkPoints methods in Rectangle and in Rhombus.
     *
     * @param points
     * @return
     */
    public static boolean checkPoints(final Point[] points) {
        double segmentLength = 0;
        int i = 0;
        boolean first = true;
        do {
            final int next = (i + 1) % 4;
            final int next2 = (next + 1) % 4;

            if (!GeometryServices.isRightAngleCorner(points[i], points[next], points[next2]))
                return false;

            // For each segment check that they have the same length
            if (first) {
                segmentLength = GeometryServices.getDistance(points[i], points[next]);
            } else {
                if (segmentLength != GeometryServices.getDistance(points[i], points[next]))
                    return false;
            }
            i = next;
            first = false;
        } while (i != 0);
        return true;
    }

    /**
     * Given by the first 2 points.
     */
    private double getInclination() {
        return GeometryServices.getAngleWithXLine(this.points[0], this.points[1]);
    }

    @Override
    public boolean isInside(final Point p) {
        // System.err.println("Square.isInside...");
        if (this.angle % 90 == 0) {
            double minY = Double.MAX_VALUE;
            double minX = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;
            double maxX = Double.MIN_VALUE;
            for (final Point c : this.points) {
                if (c.y < minY)
                    minY = c.y;
                if (c.x < minX)
                    minX = c.x;
                if (c.y > maxY)
                    maxY = c.y;
                if (c.x > maxX)
                    maxX = c.x;
            }
            final boolean insideY = p.y >= minY && p.y <= maxY;
            final boolean insideX = p.x >= minX && p.x <= maxX;
            return insideY && insideX;
        }

        return super.isInside(p);
    }
}