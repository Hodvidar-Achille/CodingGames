package com.hodvidar.utils.geometry;

public class Parallelogram extends Quadrilateral {
    public Parallelogram(Point... points) {
        super(points);

        if (!checkPoints(points))
            throw new IllegalArgumentException("Points do not form a parallelogram.");

    }

    /**
     * line a is point 0 to point 1. <br/>
     * line b is point 1 to point 2. <br/>
     * line c is point 3 to point 2 (to be in the same direction than line a). <br/>
     * line d is point 0 to point 3 (to be in the same direction than line b).
     *
     * @param points
     * @return {@code true} if a // c and b // d.
     */
    public static boolean checkPoints(Point... points) {
        Line a = new Line(points[0], points[1]);
        Line b = new Line(points[1], points[2]);
        Line c = new Line(points[3], points[2]);
        Line d = new Line(points[0], points[3]);

        return a.isParallel(c) && b.isParallel(d);
    }
}