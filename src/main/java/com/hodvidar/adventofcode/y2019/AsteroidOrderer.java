package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Segment;

import java.util.*;

public final class AsteroidOrderer implements Comparator<Point> {
    private final Point station;

    private final List<Point> asteroids;

    private final List<Point> orderedAsteroids;

    public AsteroidOrderer(final Point station, final List<Point> asteroids) {
        this.station = station;
        this.asteroids = asteroids;
        final Iterator<Point> ite = asteroids.iterator();
        while (ite.hasNext()) {
            final Point p = ite.next();
            if (p.equals(this.station)) {
                this.asteroids.remove(p);
                break;
            }
        }
        // Order asteroids (order of destruction)
        this.orderedAsteroids = new ArrayList<>(this.asteroids);
        Collections.sort(this.orderedAsteroids, this);
    }

    public static int numberOfDetection(final Point p, final List<Point> others) {
        int counter = 0;
        for (final Point o : others) {
            if (o.equals(p))
                continue;
            if (lineOfSightClear(p, o, others))
                counter++;
        }
        return counter;
    }

    private static boolean lineOfSightClear(final Point p1, final Point p2, final List<Point> others) {
        final Segment s = new Segment(p1, p2);
        boolean lineOfSightClear = true;
        for (final Point o2 : others) {
            if (o2.equals(p1) || o2.equals(p2))
                continue;
            if (GeometryServices.isOnSegment(o2, s)) {
                lineOfSightClear = false;
                break;
            }
        }
        return lineOfSightClear;
    }

    private static int numberOfBlocking(final Point p1, final Point p2, final List<Point> others) {
        final Segment s = new Segment(p1, p2);
        int counter = 0;
        for (final Point o2 : others) {
            if (o2.equals(p1) || o2.equals(p2))
                continue;
            if (GeometryServices.isOnSegment(o2, s)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public int compare(final Point o1, final Point o2) {
        final boolean o1Direct = lineOfSightClear(station, o1, asteroids);
        final boolean o2Direct = lineOfSightClear(station, o2, asteroids);

        // One of them is in sight of station and not the other
        if (o1Direct && !o2Direct)
            return -1;
        if (!o1Direct && o2Direct)
            return 1;

        // Both are hidden
        if (!o1Direct && !o2Direct) {
            final int o1NbBlockingAsteroids = numberOfBlocking(station, o1, asteroids);
            final int o2NbBlockingAsteroids = numberOfBlocking(station, o2, asteroids);

            if (o1NbBlockingAsteroids < o2NbBlockingAsteroids)
                return -1;
            if (o1NbBlockingAsteroids > o2NbBlockingAsteroids)
                return 1;

            // o1NbBlockingAsteroids == o2NbBlockingAsteroids
        }

        // Both are in sight or hidden as much
        // Compare position from Top of station in a clockWise manner
        // BUT here : remember 'Y is inverse'

        // TOP > TOP RIGHT > RIGHT > BOTTOM RIGHT
        // > BOTTOM > BOTTOM LEFT > LEFT > TOP LEFT
        final Point stationTop = new Point(station.x, 0);
        double o1Angle = GeometryServices.getAngle(station, stationTop, o1);
        double o2Angle = GeometryServices.getAngle(station, stationTop, o2);

        o1Angle = adaptAngle(o1, station, o1Angle);
        o2Angle = adaptAngle(o2, station, o2Angle);

        if (o1Angle < o2Angle)
            return -1;
        if (o1Angle > o2Angle)
            return 1;

        throw new IllegalStateException("Angle Exception for points " + o1 + " and " + o2);
    }

    // Remember Y is inverse
    private double adaptAngle(final Point o, final Point ref, final double angle) {
        if (o.x >= ref.x)
            return angle;
        return 360 - angle;
    }

    public List<Point> getOrderedAsteroids() {
        return this.orderedAsteroids;
    }

}
