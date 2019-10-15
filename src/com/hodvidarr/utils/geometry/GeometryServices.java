package com.hodvidarr.utils.geometry;

/**
* Improved Geometry service for point inside polygon.
* by Hodvidar
*/
public class GeometryServices
{
	private final double infinite;
	private final double stepToSwitchInfiniteY;
	
	public GeometryServices()
	{
		this(9000000000000000000d);
	}

	public GeometryServices(double maximumCoordinate)
	{
		double tooMuchToBeSafe = Math.round(Math.sqrt(Math.sqrt(Double.MAX_VALUE)));
		if (maximumCoordinate > tooMuchToBeSafe)
			throw new IllegalArgumentException(
				"Max X coordinate is too high, risk of double overflow during calculations.");

		this.infinite = maximumCoordinate * 10;
		this.stepToSwitchInfiniteY = this.infinite / 5;
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
		//		System.err.println("\tdoIntersect(a(" + a.x + "," + a.y + "), b(" + b.x + "," + b.y
		//			+ "), e(" + e.x + "," + e.y + "), f(" + f.x + "," + f.y + "))");
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
		// System.err.println("isInside(polygon, " + n + ", p(" + p.x + "," + p.y + "))");

		// There must be at least 3 vertices in polygon[]
		if (n < 3)
			return false;

		// Create a point for line segment from p to infinite (Infinite in the NE direction).
		Point pointToInfinite = new Point(this.infinite, this.infinite - this.stepToSwitchInfiniteY);

		return this.isInside_core(polygon, n, p, pointToInfinite);
	}

	private boolean isInside_core(Point[] polygon, int n, Point p, Point pointToInfinite)
	{
		int count = 0, i = 0;
		do
		{
			int next = (i + 1) % n;

			// Check if the line segment from 'p' to 'extreme' intersects
			// with the line segment from 'polygon[i]' to 'polygon[next]'
			if (doIntersect(polygon[i], polygon[next], p, pointToInfinite))
			{
				// System.err.println("\tdoIntersect --> true");
				// If the point 'p' is colinear with line segment 'i-next',
				// then check if it lies on segment. If it lies, return true,
				// otherwise continue
				if (orientation(polygon[i], polygon[next], p) == 0)
				{
					return onSegment(polygon[i], polygon[next], p);
				}

				// Handle when (p extreme) intersecting with polygon
				// exactly on one of its point.
				if (orientation(p, pointToInfinite, polygon[i]) == 0)
				{
					// Intersecting exactly a point of the polygon
					// can make the loop count useless to assert if the point p
					// is inside the polygon, we try again with a new infinite point.
					double newY = pointToInfinite.y - this.stepToSwitchInfiniteY;
					Point newPointToInfinite = new Point(this.infinite, newY);
					// Note could be done not recursively (with a higher level loop).
					return this.isInside_core(polygon, n, p, newPointToInfinite);
				}

				count++;
			}
			// System.err.println("\tdoIntersect --> false");
			i = next;
		}
		while (i != 0);

		// Return true if count is odd, false otherwise
		boolean countIsOdd = (count % 2 == 1);
		// System.err.println("\t count=" + count + " - countIsOdd=" + countIsOdd);
		return countIsOdd;
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
		if (!Circle.checkPoints(p1, p2, p3))
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

		return new Point(center_x, center_y);
	}

	@SuppressWarnings("unused")
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
	
	public static String getQuadrilateralType(Point...points)
	{
		String result = "Not a Quadrilateral";
		
		boolean isQuadrilateral = Quadrilateral.checkPoints(points);
		if(!isQuadrilateral)
			return result;
		result = Quadrilateral.class.getSimpleName();
		
		boolean isParralelogram = Parallelogram.checkPoints(points);
		if(!isParralelogram)
			return result;
		result = Parallelogram.class.getSimpleName();
		
		boolean isRhombus = Rhombus.checkPoints(points);
		boolean isRectangle = Rectangle.checkPoints(points);
		if(!isRhombus && !isRectangle)
			return result;
		
		if(isRectangle)
			result = Rectangle.class.getSimpleName();
		else
			return Rhombus.class.getSimpleName();
		
		
		if(!Square.checkPoints(points))
			return result;
		
		result = Square.class.getSimpleName();
		return result;
	}
	
	public static double getSphereVolume(double sphereRadius)
	{
		double sphereVolume = ( 4.0 / 3.0 ) * Math.PI * Math.pow(sphereRadius, 3 );
		return sphereVolume;
	}
	
	public static double getSphereArea(double sphereRadius)
	{
		double sphereArea = 4 * Math.PI * Math.pow(sphereRadius, 2);
		return sphereArea;
	}
	
	public static double getSphereRadius(double sphereVolume)
	{
		double intermediateResult = sphereVolume / (( 4.0 / 3.0 ) * Math.PI);
		double radius = Math.cbrt(intermediateResult);
		return radius;
	}
	
}