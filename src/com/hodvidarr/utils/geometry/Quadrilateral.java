package com.hodvidarr.utils.geometry;

public class Quadrilateral extends Polygon
{
	public Quadrilateral(Point... points)
	{
		super(points);
		
		// Must have 4 points.
		if (!checkPoints(points))
			throw new IllegalArgumentException("Quadrilateral must have 4 points");
	}
	
	public static boolean checkPoints(Point...points)
	{
		return points.length == 4;
	}
}