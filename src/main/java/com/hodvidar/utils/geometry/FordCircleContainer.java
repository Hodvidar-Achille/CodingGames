package com.hodvidar.utils.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Ford Circles
 * All circles touch the axis y=0.
 * With the first circle touching the axis x=0.
 * With the following circles touching the previous one, never overlapping another circle.
 */
public class FordCircleContainer {

    private final List<Circle> circles;
    private final List<FordCircleInterval> intervals;

    public FordCircleContainer(final List<Circle> circles) {
        this.circles = circles;
        this.intervals = new ArrayList<>();
    }

    public double getMaxX() {
        if (circles.isEmpty()) {
            return 0;
        }
        return circles.stream().mapToDouble(c -> c.getEastPoint().getX()).max().getAsDouble();
    }
}
