package com.hodvidar.utils.geometry;

import java.util.Objects;

public class Polygon implements GeometricForm
{
	public final Point[] points;
	public final int numberOfPoints;
	/**
	 *  X coordinate of the point with the max X.
	 */
	public final double max_X;

	public Polygon(Point... points)
	{
		int l = points.length;

		if(!checkPoints(points))
			throw new IllegalArgumentException("Every points must be different in a Polygon"
					+ " and there must be at least 3 of them.");
		
		this.points = points;
		this.numberOfPoints = l;

		// Compute max_X needed for method isInside.
		double x = Double.MIN_VALUE;
		for (Point p : this.points)
		{
			if (p.x > x)
				x = p.x;
		}
		this.max_X = x;
	}
	
	public static boolean checkPoints(Point...points)
	{
		if(points.length < 3)
			return false;
		
		// Every point must be different.
		for(int i = 0; i < points.length; i++)
		{
			for(int j = 0; j < points.length; j++)
			{
				if(i == j)
					continue;
				if(Objects.equals(points[i], points[j]))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isInside(Point p)
	{
		// System.err.println("Polygon.isInside...");
		GeometryServices sh = new GeometryServices(this.max_X);
		return sh.isInside(this.points, this.numberOfPoints, p);
	}
}