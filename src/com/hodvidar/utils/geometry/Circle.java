package com.hodvidar.utils.geometry;

public class Circle implements GeometricForm
{
	/**
	* Coordinate X,Y of the circle's center.
	*/
	public final Point center;
	/**
	 * unit : u.
	 */
	public final double radius;
	
	public Circle(double radius)
	{
		this.center = new Point(0, 0);
		this.radius = radius;
	}

	public Circle(Point center, double radius)
	{
		this.center = center;
		this.radius = radius;
	}

	/**
	* Constructor that create a circle from 3 differents points.
	* Their coordinates must be different for each 3 points.
	*
	* From : http://paulbourke.net/geometry/circlesphere/
	*/
	public Circle(Point p1, Point p2, Point p3)
	{
		this.center = GeometryServices.getCenter(p1, p2, p3);
		this.radius = GeometryServices.getDistance(this.center, p1);
	}

	/**
	* Is point inside or on the circle.
	* Returns false if outside of the circle.
	*/
	@Override
	public boolean isInside(Point p)
	{
		// System.err.println("Circle.isInside...");
		double r = this.radius * this.radius;
		double x = Math.pow((p.x - this.center.x), 2);
		double y = Math.pow((p.y - this.center.y), 2);
		return x + y <= r;
	}
	
	public static boolean checkPoints(Point...points)
	{
		// Check that the points are no all align.
		return GeometryServices.orientation(points[0], points[1], points[2]) != 0;

	}
}