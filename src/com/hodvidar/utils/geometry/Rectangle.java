package com.hodvidar.utils.geometry;

public class Rectangle extends Parallelogram
{
	// Make Rectangle get the Square methods ?
	public Rectangle(Point...points)
	{
		super(points);
		
		if(!checkPoints(points))
			throw new IllegalArgumentException("Points do not form a rectangle.");
	}
	
	public static boolean checkPoints(Point...points)
	{
		int i = 0;
		do
		{
			int next = (i + 1) % 4;
			int next2 = (next + 1) % 4;

			if (!GeometryServices.isRightAngleCorner(points[i], points[next], points[next2]))
				return false;

			i = next;
		} while (i != 0);
		return true;
	}
}