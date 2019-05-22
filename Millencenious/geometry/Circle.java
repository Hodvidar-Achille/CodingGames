/**
*	By Hodvidar.
*/
class Circle implements GeometricForm
{
	/**
	* Coordinate X,Y of the circle's center.
	*/
	public final Point center;

	public final double radius;

	public Circle(Point center, double radius)
	{
		this.center = center;
		this.radius = radius;
	}

	public Circle(double x, double y, double radius)
	{
		this.center = new Point(x, y);
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
		// 1) Check that the points are no all align.
		if((p1.x == p2.x && p1.x == p3.x) || (p1.y == p2.y && p1.y == p3.y))
			throw new IllegalArgumentException("Points can't be aligned");

		// 2) Arrange points to have 2 lines that are never perfectly vertical.
		double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;
		if(p1.x != p2.x && p2.x != p3.x)
		{
			x1 = p1.x; x2 = p2.x; x3 = p3.x; y1 = p1.y; y2 = p2.y; y3 = p3.y;
		}
		else if(p2.x != p3.x && p3.x != p1.x)
		{
			x1 = p2.x; x2 = p3.x; x3 = p1.x; y1 = p2.y; y2 = p3.y; y3 = p1.y;
		}
		else // (p3.x != p1.x && p1.x != p2.x)
		{
			x1 = p3.x; x2 = p1.x; x3 = p2.x; y1 = p3.y; y2 = p1.y; y3 = p2.y;
		}

		// 3) Find the center point from the intersection of the mediatrices.
		// Should never meet the 'divided by 0' error.
		double m1 = (y2 - y1) / (x2 - x1);
		double m2 = (y3 - y2) / (x3 - x2);

		double center_x = (((m1 * m2)*(y1 - y3)) + (m2 * (x1 + x2)) - (m1 * (x2 + x3))) / (2 * (m2 - m1));
		double center_y = ((1/m1) * (center_x - ((x1+x2)/2))) + ((y1+y2)/2);

		this.center = new Point(center_x, center_y);

		// 4) Calculate the radius by computing the average difference between the center and each of the 3 points.
		double r1 = this.hypothesus(center_x, x1, center_y, y1);
		// double r2 = hypothesus(center_x, x2, center_y, y2);
		// double r3 = hypothesus(center_x, x3, center_y, y3);
		// this.radius = (r1 + r2 + r3) / 3;
		this.radius = r1;
	}

	private double hypothesus(double x1, double x2, double y1, double y2)
	{
		return Math.sqrt( Math.pow((x1+x2), 2) + Math.pow((y1+y2), 2));
	}

	/**
	* Is point inside or on the circle.
	* Returns false if outside of the circle.
	*/
	@Override
	public boolean isInside(Point p)
	{
		double r = this.radius * this.radius;
		double x = Math.pow((p.x - this.center.x), 2);
		double y = Math.pow((p.y - this.center.y), 2);
		return x+y <= r;
	}
}
