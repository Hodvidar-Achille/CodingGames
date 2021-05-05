package com.hodvidar.utils.geometry;

public class Sphere extends Circle {
    /**
     * = 4 * Math.PI * Math.pow(radius, 2) (u^2).
     */
    public final double area;
    /**
     * = = ( 4.0 / 3.0 ) * Math.PI * Math.pow( sphereRadius, 3 ) (u^3).
     */
    public final double volume;

    public Sphere(final double radius) {
        super(radius);
        this.area = GeometryServices.getSphereArea(this.radius);
        this.volume = GeometryServices.getSphereVolume(this.radius);
    }

    public Sphere(final Point center, final double radius) {
        super(center, radius);
        this.area = GeometryServices.getSphereArea(this.radius);
        this.volume = GeometryServices.getSphereVolume(this.radius);
    }

    // No Override of 'isInside' for now (point are only in 2D here).
}