package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.geometry.Point;

/**
 * A Point (2D) that also has a value (int) that can be related to a sign (to print).
 *
 * @author Hodvidar
 */
public abstract class PaintedPoint extends Point implements PaintedObject {
    public static final int DEFAULT = 0;

    /**
     * default value is 0
     **/
    protected int value = DEFAULT;

    /**
     * Number of times it has been painted
     **/
    protected int counter = 0;

    public PaintedPoint(final double x, final double y) {
        super(x, y);
    }

    public PaintedPoint(final double x, final double y, final int value) {
        super(x, y);
        this.value = value;
    }

    /**
     * Returns the color 0 is WHITE, 1 is DARK
     *
     * @return
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * Returns the number of time the panel
     * actually changed of color
     *
     * @return counter
     */
    public int getCounter() {
        return this.counter;
    }

    @Override
    public boolean paint(final int newValue) {
        if (newValue == this.value)
            return false;

        this.value = newValue;
        this.counter++;
        return true;
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "(" + this.x + "; " + this.y + ") " + "value:'" + this.value
                + "' " + "counter=" + this.counter;
    }
}
