package com.hodvidar.adventofcode.y2019;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Segment;

public final class AsteroidOrderer implements Comparator<Point>
{
	private final Point station;

	private final List<Point> asteroids;

	private final List<Point> orderedAsteroids;

	public AsteroidOrderer(Point station, List<Point> asteroids)
	{
		this.station = station;
		this.asteroids = asteroids;
		Iterator<Point> ite = asteroids.iterator();
		while (ite.hasNext())
		{
			Point p = ite.next();
			if(p.equals(this.station))
			{
				this.asteroids.remove(p);
				break;
			}
		}
		// Order asteroids (order of destruction)
		this.orderedAsteroids = new ArrayList<>(this.asteroids);
		Collections.sort(this.orderedAsteroids, this);
	}

	@Override
	public int compare(Point o1, Point o2)
	{
		boolean o1Direct = lineOfSightClear(station, o1, asteroids);
		boolean o2Direct = lineOfSightClear(station, o2, asteroids);

		// One of them is in sight of station and not the other
		if(o1Direct && !o2Direct)
			return -1;
		if(!o1Direct && o2Direct)
			return 1;

		// Both are hidden
		if(!o1Direct && !o2Direct)
		{
			int o1NbBlockingAsteroids = numberOfBlocking(station, o1, asteroids);
			int o2NbBlockingAsteroids = numberOfBlocking(station, o2, asteroids);

			if(o1NbBlockingAsteroids < o2NbBlockingAsteroids)
				return -1;
			if(o1NbBlockingAsteroids > o2NbBlockingAsteroids)
				return 1;

			// o1NbBlockingAsteroids == o2NbBlockingAsteroids
		}

		// Both are in sight or hidden as much
		// Compare position from Top of station in a clockWise manner
		// BUT here : remember 'Y is inverse'

		// TOP > TOP RIGHT > RIGHT > BOTTOM RIGHT 
		// > BOTTOM > BOTTOM LEFT > LEFT > TOP LEFT
		Point stationTop = new Point(station.x, 0);
		double o1Angle = GeometryServices.getAngle(station, stationTop, o1);
		double o2Angle = GeometryServices.getAngle(station, stationTop, o2);

		o1Angle = adaptAngle(o1, station, o1Angle);
		o2Angle = adaptAngle(o2, station, o2Angle);

		if(o1Angle < o2Angle)
			return -1;
		if(o1Angle > o2Angle)
			return 1;

		throw new IllegalStateException("Angle Exception for points " + o1 + " and " + o2);
	}

	// Remember Y is inverse
	private double adaptAngle(Point o, Point ref, double angle)
	{
		if(o.x >= ref.x)
			return angle;
		return 360 - angle;
	}

	public List<Point> getOrderedAsteroids()
	{
		return this.orderedAsteroids;
	}

	public static int numberOfDetection(Point p, List<Point> others)
	{
		int counter = 0;
		for (Point o : others)
		{
			if(o.equals(p))
				continue;
			if(lineOfSightClear(p, o, others))
				counter++;
		}
		return counter;
	}

	private static boolean lineOfSightClear(Point p1, Point p2, List<Point> others)
	{
		Segment s = new Segment(p1, p2);
		boolean lineOfSightClear = true;
		for (Point o2 : others)
		{
			if(o2.equals(p1) || o2.equals(p2))
				continue;
			if(GeometryServices.isOnSegment(o2, s))
			{
				lineOfSightClear = false;
				break;
			}
		}
		return lineOfSightClear;
	}

	private static int numberOfBlocking(Point p1, Point p2, List<Point> others)
	{
		Segment s = new Segment(p1, p2);
		int counter = 0;
		for (Point o2 : others)
		{
			if(o2.equals(p1) || o2.equals(p2))
				continue;
			if(GeometryServices.isOnSegment(o2, s))
			{
				counter++;
			}
		}
		return counter;
	}

}
