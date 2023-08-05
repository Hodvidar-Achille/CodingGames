package com.hodvidar.utils.geometry.fordcircle;

import com.hodvidar.utils.geometry.Circle;
import com.hodvidar.utils.geometry.Point;

public class FordCircle extends Circle {

    private int position;


    public FordCircle(final Point center, final double radius) {
        super(center, radius);
    }

    public FordCircle(final double radius) {
        super(new Point(0d, 0d), radius);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }
}
