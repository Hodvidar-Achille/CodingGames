package com.hodvidar.utils.geometry;

public class Rhombus extends Parallelogram {
    public Rhombus(Point... points) {
        super(points);

        if (!checkPoints(points))
            throw new IllegalArgumentException("Points do not form a rhombus.");
    }

    public static boolean checkPoints(Point... points) {
        double segmentLength = 0;
        int i = 0;
        boolean first = true;
        do {
            int next = (i + 1) % 4;
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
}