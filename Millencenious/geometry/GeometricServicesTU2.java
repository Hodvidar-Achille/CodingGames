/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class GeometricServicesTU2
{

	public static void main(String args[])
	{
		// TEST
		System.err.println("TESTING");
		// Square in positive number, with side of, middle at (3,3);
		Point[] square1Points = new Point[4];
		/*
		 * | A    B
		 * |
		 * |   x
		 * |
		 * |_D____C_____
		 */
		square1Points[0] = new Point(1, 5);
		square1Points[1] = new Point(5, 5);
		square1Points[2] = new Point(5, 1);
		square1Points[3] = new Point(1, 1);
		System.err.println("Build square1 from points :");
		Square square1 = new Square(square1Points);
		printSquareInfos(square1);

		System.err.println("Build circle1 from points:");
		Circle circle1 = new Circle(square1Points[0], square1Points[1], square1Points[2]);
		System.err.println("Center(" + circle1.center.x + ", " + circle1.center.y + ") radius="
			+ circle1.radius);

		// +90°
		square1Points[3] = new Point(1, 5);
		square1Points[0] = new Point(5, 5);
		square1Points[1] = new Point(5, 1);
		square1Points[2] = new Point(1, 1);
		System.err.println("Build square2 (+90°) from points :");
		square1 = new Square(square1Points);
		printSquareInfos(square1);

		// Diamond
		square1Points[0] = new Point(8, 11);
		square1Points[1] = new Point(11, 8);
		square1Points[2] = new Point(8, 5);
		square1Points[3] = new Point(5, 8);
		System.err.println("Build square3 (diamond) from points :");
		square1 = new Square(square1Points);
		printSquareInfos(square1);

		square1Points[2] = new Point(8, 11);
		square1Points[3] = new Point(11, 8);
		square1Points[0] = new Point(8, 5);
		square1Points[1] = new Point(5, 8);
		System.err.println("Build square4 (diamond +180°) from points :");
		square1 = new Square(square1Points);
		printSquareInfos(square1);

		System.err.println("Build square5, from center point and side :");
		Point center = new Point(3, 3);
		double side = 4.0;
		double angle = 0.0;
		square1 = new Square(center, side, angle);
		printSquareInfos(square1);

		System.err.println("Build square6, (diamon) from center point and side :");
		center = new Point(8, 8);
		side = 4.242640687119285;
		angle = 45;
		square1 = new Square(center, side, angle);
		printSquareInfos(square1);
	}

	private static void printSquareInfos(Square sq)
	{
		System.err.println("Center(" + sq.center.x + ", " + sq.center.y + ") side=" + sq.side
			+ " angle=" + sq.angle);
		System.err.println("Points :(" + sq.points[0].x + ", " + sq.points[0].y + ")," + "("
			+ sq.points[1].x + ", " + sq.points[1].y + ")," + "(" + sq.points[2].x + ", "
			+ sq.points[2].y + ")," + "(" + sq.points[3].x + ", " + sq.points[3].y + ")");
	}
}

interface GeometricForm
{
	boolean isInside(Point p);
}

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
		double r = this.radius * this.radius;
		double x = Math.pow((p.x - this.center.x), 2);
		double y = Math.pow((p.y - this.center.y), 2);
		return x + y <= r;
	}
}

class Polygon implements GeometricForm
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
		if (l < 3)
			throw new IllegalArgumentException("Polygon must have at least 3 points");

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

	@Override
	public boolean isInside(Point p)
	{
		GeometryServices sh = new GeometryServices(this.max_X);
		return sh.isInside(this.points, this.numberOfPoints, p);
	}
}

class Square extends Polygon
{
	public final Point center;
	/**
	 *  Side length;
	 */
	public final double side;

	/**
	 * In degrees°, inclination of the square given by the first 2 points
	 * and the X axe. (Clockwise).
	 */
	public final double angle;

	public Square(Point... points)
	{
		super(points);
		int l = points.length;
		// Must have 4 points.
		if (l != 4)
			throw new IllegalArgumentException("Square must have 4 points");

		// All 4 angles must be 90°.
		// All sides must be of ame length.
		if (!this.checkPoints(points))
			throw new IllegalArgumentException("Points do not form a square");

		this.side = GeometryServices.getDistance(points[0], points[1]);
		this.center = GeometryServices.getCenter(points[0], points[1], points[2]);
		this.angle = this.getInclination();
	}

	public Square(Point center, double side, double angle)
	{
		super(GeometryServices.createSquarePoints(center, side, angle));
		this.center = center;
		this.side = side;
		this.angle = angle;
	}

	private boolean checkPoints(Point[] points)
	{
		double segmentLength = 0;
		int i = 0;
		boolean first = true;
		do
		{
			int next = (i + 1) % 4;
			int next2 = (next + 1) % 4;
			// For each segment check that they have the same length
			if (first)
			{
				segmentLength = GeometryServices.getDistance(points[i], points[next]);
			}
			else
			{
				if (segmentLength != GeometryServices.getDistance(points[i], points[next]))
					return false;
			}

			// for each corner, check that the angle is 90° (PI/4);
			if (!GeometryServices.isRightAngleCorner(points[i], points[next], points[next2]))
				return false;

			i = next;
			first = false;
		}
		while (i != 0);
		return true;
	}

	/**
	 * Given by the first 2 points.
	 */
	private double getInclination()
	{
		// TODO : can be improved...
		return GeometryServices.getAngleWithXLine(this.points[0], this.points[1]);
	}

	@Override
	public boolean isInside(Point p)
	{
		if (this.angle % 90 == 0)
		{
			double minY = Double.MAX_VALUE;
			double minX = Double.MAX_VALUE;
			double maxY = Double.MIN_VALUE;
			double maxX = Double.MIN_VALUE;
			for (Point c : this.points)
			{
				if (c.y < minY)
					minY = c.y;
				if (c.x < minX)
					minX = c.x;
				if (c.y > maxY)
					maxY = c.y;
				if (c.x > maxX)
					maxX = c.x;
			}
			boolean insideY = p.y >= minY && p.y <= maxY;
			boolean insideX = p.x >= minX && p.x <= maxX;
			return insideY && insideX;
		}

		return super.isInside(p);
	}
}

class Point
{
	public final double x;
	public final double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}

/**
* Improved Geometry service for point inside polygon.
* by Hodvidar
*/
class GeometryServices
{
	private final double max_X;
	public GeometryServices(double max_X)
	{
		this.max_X = max_X + 1;
	}
	/**
	 * When ALREADY KNOWING that E is on the line (AB)
	 * @param a
	 * @param b
	 * @param e
	 * @return true if e on segment [ab]
	 */
	public static boolean onSegment(Point a, Point b, Point e)
	{
		// System.err.println("\t\tonSegment()");

		boolean colinear = true;
		boolean betweenX = e.x <= Math.max(a.x, b.x) && e.x >= Math.min(a.x, b.x);
		boolean betweenY = e.y <= Math.max(a.y, b.y) && e.y >= Math.min(a.y, b.y);
		// System.err.println("\t\tbetweenX="+betweenX+"   betweenY="+betweenY);
		return colinear && betweenX && betweenY;
	}

	// To find orientation of ordered triplet
	// (p1, p2, p3). The function returns
	// following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// -1 --> Counterclockwise
	public static int orientation(Point p1, Point p2, Point p3)
	{
		// System.err.println("\t\torientation(p1("+p1.x+","+p1.y+"), p2("+p2.x+","+p2.y+"), p3("+p3.x+","+p3.y+"))");
		// Slope of line segment (p1, p2): σ = (y2 - y1)/(x2 - x1)
		// Slope of line segment (p2, p3): τ = (y3 - y2)/(x3 - x2)
		// If  σ > τ, the orientation is clockwise (right turn)

		// Using above values of σ and τ, we can conclude that,
		// the orientation depends on sign of  below expression:
		// (y2 - y1)*(x3 - x2) - (y3 - y2)*(x2 - x1)

		// Can work but risk of dividing by 0 !
		// int slopeP1toP2 = (p2.y - p1.y) / (p2.x - p1.x);
		// int slopeP2toP3 = (p3.y - p2.y) / (p3.x - p2.x);
		// int val = slopeP1toP2 - slopeP2toP3;

		double val = ((p2.y - p1.y) * (p3.x - p2.x)) - ((p2.x - p1.x) * (p3.y - p2.y));
		// System.err.println("\t\tval="+val);
		if (val == 0d)
			return 0; // colinear
		// clock or counterclock wise
		return (val > 0) ? 1 : -1;
	}

	/**
	 * Segment AB is intersecting with segment EF ?
	 * @param a
	 * @param b
	 * @param e
	 * @param f
	 * @return true if [ab] and [ef] intersected
	 */
	public static boolean doIntersect(Point a, Point b, Point e, Point f)
	{
		System.err.println("\tdoIntersect(a(" + a.x + "," + a.y + "), b(" + b.x + "," + b.y
			+ "), e(" + e.x + "," + e.y + "), f(" + f.x + "," + f.y + "))");
		// if o1 != o2 (ef) intersect (ab) (lines)
		int o1 = orientation(a, b, e);
		int o2 = orientation(a, b, f);
		// if also o3 != o4 [ef] intersect [ab] [segments]
		int o3 = orientation(e, f, a);
		int o4 = orientation(e, f, b);

		//simple segment intersection
		if (o1 != o2 && o3 != o4)
			return true;

		//special Cases
		// a, b, e colinear, e on segment [ab]
		if (o1 == 0 && onSegment(a, b, e))
			return true;
		// a, b, f colinear, f on segment [ab]
		if (o2 == 0 && onSegment(a, b, f))
			return true;
		// e, f, a colinear, a on segment [ef]
		if (o3 == 0 && onSegment(e, f, a))
			return true;
		// e, f, b colinear, b on segment [ef]
		if (o4 == 0 && onSegment(e, f, b))
			return true;

		return false; // Doesn't fall in any of the above cases
	}

	public boolean isInside(Point polygon[], int n, Point p)
	{
		System.err.println("isInside(polygon, " + n + " point(" + p.x + "," + p.y + "))");

		// There must be at least 3 vertices in polygon[]
		if (n < 3)
			return false;

		// Create a point for line segment from p to infinite
		Point extreme = new Point(this.max_X, p.y);

		// Count intersections of the above line with sides of polygon
		int count = 0, i = 0;
		boolean trueIfOdd = true;
		do
		{
			int next = (i + 1) % n;

			// Check if the line segment from 'p' to 'extreme' intersects
			// with the line segment from 'polygon[i]' to 'polygon[next]'
			if (doIntersect(polygon[i], polygon[next], p, extreme))
			{
				// System.err.println("\tdoIntersect --> true");
				// If the point 'p' is colinear with line segment 'i-next',
				// then check if it lies on segment. If it lies, return true,
				// otherwise continue
				if (orientation(polygon[i], polygon[next], p) == 0)
				{
					return onSegment(polygon[i], polygon[next], p);
				}

				// Handle case where 'e' is exactly at same X or Y
				// than a polygon point.
				// --> It will Intersect 2 times the polygon limit.
				if (orientation(p, extreme, polygon[i]) == 0)
				{
					trueIfOdd = !trueIfOdd;
				}

				count++;
			}
			// System.err.println("\tdoIntersect --> false");
			i = next;
		}
		while (i != 0);

		// Return true if count is odd, false otherwise
		boolean countIsOdd = count % 2 == 1;
		// System.err.println("\t countIsOdd="+countIsOdd+"  trueIfOdd="+trueIfOdd);
		return countIsOdd == trueIfOdd;
	}

	/**
	 * Returns a new point created at the distance of start point with given angle.
	 * @param angle : in degre°.
	 */
	public static Point createPoint(Point start, double distance, double angle)
	{
		double angleRad = Math.toRadians(angle);

		double cos = Math.cos(angleRad);
		double x = start.x + cos * distance;
		double sin = Math.sin(angleRad);
		double y = start.y + sin * distance;

		x = Math.round(x);
		y = Math.round(y);
		return new Point(x, y);
	}

	public static double getDistance(Point p1, Point p2)
	{
		double x = Math.pow((p1.x - p2.x), 2);
		double y = Math.pow((p1.y - p2.y), 2);

		return Math.sqrt(x + y);
	}

	/**
	 * Check if angle (degre°) form by intersection between (p1 p2) and (p2, p3) is 90°.
	 */
	public static boolean isRightAngleCorner(Point p1, Point p2, Point p3)
	{
		double p1_p2 = getDistance(p1, p2);
		double p2_p3 = getDistance(p2, p3);
		double p1_p3 = getDistance(p1, p3);
		double p1_p2Sq = Math.round(p1_p2 * p1_p2);
		double p2_p3Sq = Math.round(p2_p3 * p2_p3);
		double p1_p3Sq = Math.round(p1_p3 * p1_p3);
		return Math.round(p1_p3Sq) == Math.round(p1_p2Sq + p2_p3Sq);
	}

	/**
	 * Get the point at the center of the circle formed by the 3 given points.
	 * Note : the points cannot be all aligned.
	 */
	public static Point getCenter(Point p1, Point p2, Point p3)
	{
		// 1) Check that the points are no all align.
		if (orientation(p1, p2, p3) == 0)
			throw new IllegalArgumentException("Points can't be aligned");

		// 2) Arrange points to have 2 lines that are never perfectly vertical.
		double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;
		if (p1.x != p2.x && p2.x != p3.x)
		{
			x1 = p1.x;
			x2 = p2.x;
			x3 = p3.x;
			y1 = p1.y;
			y2 = p2.y;
			y3 = p3.y;
		}
		else if (p2.x != p3.x && p3.x != p1.x)
		{
			x1 = p2.x;
			x2 = p3.x;
			x3 = p1.x;
			y1 = p2.y;
			y2 = p3.y;
			y3 = p1.y;
		}
		else
		// (p3.x != p1.x && p1.x != p2.x)
		{
			x1 = p3.x;
			x2 = p1.x;
			x3 = p2.x;
			y1 = p3.y;
			y2 = p1.y;
			y3 = p2.y;
		}

		/*
		// 3) Find the center point from the intersection of the 2 perpendicular bisectors.
		// Should never meet the 'divided by 0' error.
		double m1 = (y2 - y1) / (x2 - x1);
		double m2 = (y3 - y2) / (x3 - x2);

		double center_x = (((m1 * m2) * (y1 - y3)) + (m2 * (x1 + x2)) - (m1 * (x2 + x3)))
			/ (2 * (m2 - m1));
		double center_y = ((1 / m1) * (center_x - ((x1 + x2) / 2))) + ((y1 + y2) / 2);
		if (isNaN(center_y))
			center_y = ((1 / m2) * (center_x - ((x2 + x3) / 2))) + ((y2 + y3) / 2);
		// XXX Fails sometimes...
		*/

		// ----------- TRY another method ------------------
		// https://www.geeksforgeeks.org/equation-of-circle-when-three-points-on-the-circle-are-given/
		double x12 = x1 - x2;
		double x13 = x1 - x3;

		double y12 = y1 - y2;
		double y13 = y1 - y3;

		double y31 = y3 - y1;
		double y21 = y2 - y1;

		double x31 = x3 - x1;
		double x21 = x2 - x1;

		// x1^2 - x3^2
		double sx13 = Math.pow(x1, 2) - Math.pow(x3, 2);

		// y1^2 - y3^2
		double sy13 = Math.pow(y1, 2) - Math.pow(y3, 2);

		double sx21 = Math.pow(x2, 2) - Math.pow(x1, 2);

		double sy21 = Math.pow(y2, 2) - Math.pow(y1, 2);

		double f = ((sx13) * (x12) + (sy13) * (x12) + (sx21) * (x13) + (sy21) * (x13))
			/ (2 * ((y31) * (x12) - (y21) * (x13)));
		double g = ((sx13) * (y12) + (sy13) * (y12) + (sx21) * (y13) + (sy21) * (y13))
			/ (2 * ((x31) * (y12) - (x21) * (y13)));

		double center_x = -g;
		double center_y = -f;

		//		double c = -Math.pow(x1, 2) - Math.pow(y1, 2) - 2 * g * x1 - 2 * f * y1;
		//		//		 eqn of circle be x^2 + y^2 + 2*g*x + 2*f*y + c = 0
		//		//		 where centre is (h = -g, k = -f) and radius r
		//		//		 as r^2 = h^2 + k^2 - c
		//		double sqr_of_r = center_x * center_x + center_y * center_y - c;
		//
		//		// r is the radius
		//		double r = Math.sqrt(sqr_of_r);
		//
		// ----------------------------------------------------

		return new Point(center_x, center_y);
	}

	private static boolean isNaN(double v)
	{
		return (v != v);
	}

	/**
	 * Creates 4 points (from Top-left (NW) to bottom-left (SW) : NW->NE->SE->SW),
	 * forming a square with the given center, side and inclined by the given angle.
	 */
	public static Point[] createSquarePoints(Point center, double side, double angle)
	{
		double halfSide = side / 2;
		double halfSideSq = halfSide * halfSide;
		double radius = Math.sqrt(halfSideSq + halfSideSq);
		Point[] points = new Point[4];
		points[0] = GeometryServices.createPoint(center, radius, angle + 135);
		points[1] = GeometryServices.createPoint(center, radius, angle + 45);
		points[2] = GeometryServices.createPoint(center, radius, angle - 45);
		points[3] = GeometryServices.createPoint(center, radius, angle - 135);
		return points;
	}

	/**
	 * Returns the angle in degrees° formed by the vector p1->p2 and the vector p1->X
	 */
	public static double getAngleWithXLine(Point p1, Point p2)
	{
		Point pX = new Point(p1.x + 100, p1.y);
		return getAngle(p1, p2, pX);
	}

	/**
	 * Angle formed by (p1 p2) and (p1 p3)
	 *
	 * Théorème du cosinus :
	 * 		oppose^2 = adjacent1^2 + adjacent2^2 - 2*adjacent1*p2_p3*adjacent2(angle);
	 * <-->
	 * 		cos(angle) = ( (adjacent1^2 + adjacent2^2) - (oppose^2) ) / ( 2*adjacent1*adjacent2 );
	 * <-->
	 * 		angle = arccors( ( (adjacent1^2 + adjacent2^2) - (oppose^2) ) / ( 2*adjacent1*adjacent2 ) );
	 */
	public static double getAngle(Point p1, Point p2, Point p3)
	{
		double adjacent1 = getDistance(p1, p2);
		double adjacent2 = getDistance(p1, p3);
		double oppose = getDistance(p2, p3);
		double adjacent1Sq = Math.round(adjacent1 * adjacent1);
		double adjacent2Sq = Math.round(adjacent2 * adjacent2);
		double opposeSq = Math.round(oppose * oppose);
		double nominator = (adjacent1Sq + adjacent2Sq - opposeSq);
		double denominator = (2 * adjacent1 * adjacent2);
		double cos = nominator / denominator;
		double angle = Math.toDegrees(Math.acos(cos));
		angle = Math.round(angle);
		return angle;
	}
}
