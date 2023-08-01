package com.hodvidar.utils.geometry;

public class Circle implements GeometricForm {
    /**
     * Coordinate X,Y of the circle's center.
     */
    public final Point center;
    /**
     * unit : u.
     */
    public final double radius;

    public Circle(final double radius) {
        this.center = new Point(0, 0);
        this.radius = radius;
    }

    public Circle(final Point center, final double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Constructor that create a circle from 3 different points. Their coordinates must be
     * different for each 3 points.
     * <p>
     * From : http://paulbourke.net/geometry/circlesphere/
     */
    public Circle(final Point p1, final Point p2, final Point p3) {
        this.center = GeometryServices.getCenter(p1, p2, p3);
        this.radius = GeometryServices.getDistance(this.center, p1);
    }

    public static boolean checkPoints(final Point... points) {
        // Check that the points are no all align.
        return GeometryServices.orientation(points[0], points[1], points[2]) != 0;

    }

    /**
     * Is point inside or on the circle. Returns false if outside of the circle.
     */
    @Override
    public boolean isInside(final Point p) {
        // System.err.println("Circle.isInside...");
        final double r = this.radius * this.radius;
        final double x = Math.pow((p.getX() - this.center.getX()), 2);
        final double y = Math.pow((p.getY()- this.center.getY()), 2);
        return x + y <= r;
    }


    public boolean isOverLapping(final Circle c) {
        return GeometryServices.getDistance(this.getCenter(), c.getCenter())
                < this.getRadius() + c.getRadius();
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Point getNorthPoint() {
        return new Point(this.center.x, this.center.y + this.radius);
    }

    public Point getEastPoint() {
        return new Point(this.center.x + this.radius, this.center.y);
    }

    public Point getSouthPoint() {
        return new Point(this.center.x, this.center.y - this.radius);
    }

    public Point getWestPoint() {
        return new Point(this.center.x - this.radius, this.center.y);
    }
}