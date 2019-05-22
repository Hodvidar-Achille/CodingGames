import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/darts
 * by Hodvidar
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int SIZE = in.nextInt();
        System.err.println("SIZE: "+SIZE);
        int N = in.nextInt();
        System.err.println("N: "+N);
        for (int i = 0; i < N; i++) {
            if (in.hasNextLine()) {
               in.nextLine();
            }
            String name = in.nextLine();
            System.err.println("name: "+name);
        }
        
        Point center = new Point(0, 0);
        
        
        int T = in.nextInt();
        System.err.println("T: "+T);
        for (int i = 0; i < T; i++) {
            String throwName = in.next();
            int throwX = in.nextInt();
            int throwY = in.nextInt();
            System.err.println("throwName: "+throwName+" - throwX: "+throwX+" - throwY:"+throwY);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("answer");
    }
}

interface GeometricForm
{
    boolean isInside(Point p);
}

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
        if(l < 3)
            throw new IllegalArgumentException("Polygon must have at least 3 points");
            
        this.points = points;
        this.numberOfPoints = l;
        
        // Compute max_X needed for method isInside.
        double x = Double.MIN_VALUE;
        for(Point p : this.points)
        {
            if(p.x > x)
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
     * In degres°, angle form by the square top point (North, p.y max).
     * Between 0° (include) and 90° (exclude).
     */
    public final double angle;
    
    public Square(Point... points)
    {
        super(points);
        int l = points.length;
        // Must have 4 points.
        if(l != 4)
            throw new IllegalArgumentException("Square must have 4 points");
        
        // All 4 angles must be 90°.
        // All sides must be of ame length.
        if(!checkPoints(points))
             throw new IllegalArgumentException("Points do not form a square");
        
        
        super(points);
    }
    
    public Square(Point center, double side, double angle)
    {
        super(this.createSquarePoints(center, side, angle));
        this.center = center;
        this.side = side;
        this.angle = angle;
    }
    
    private Point[] createSquarePoints(Point center, double side, double angle)
    {
        
    }
    
    private boolean checkPoints(Point[] points)
    {
        
    }
    
    private double getSide(Point[] points)
    {
        
    }
    
    private Point getCenter(Point[] points)
    {
        
    }
}

class Point 
{
    public final double x;
    public final double y;

    public Point(double x, double y) {
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
    public boolean onSegment(Point a, Point b, Point e) {
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
    public int orientation(Point p1, Point p2, Point p3) 
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
        if (val == 0d) return 0;  // colinear 
        // clock or counterclock wise 
        return (val > 0)? 1: -1;  
    } 

    /**
     * Segment AB is intersecting with segment EF ?
     * @param a
     * @param b
     * @param e
     * @param f
     * @return true if [ab] and [ef] intersected
     */
    public boolean doIntersect(Point a, Point b, Point e, Point f)
    {
        System.err.println("\tdoIntersect(a("+a.x+","+a.y+"), b("+b.x+","+b.y+"), e("+e.x+","+e.y+"), f("+f.x+","+f.y+"))");
        // if o1 != o2 (ef) intersect (ab) (lines)
        int o1 = orientation(a, b, e);
        int o2 = orientation(a, b, f);
        // if also o3 != o4 [ef] intersect [ab] [segments]
        int o3 = orientation(e, f, a);
        int o4 = orientation(e, f, b);

        //simple segment intersection
        if(o1 != o2 && o3 != o4) return true;

        //special Cases
        // a, b, e colinear, e on segment [ab]
        if(o1 == 0 && onSegment(a, b, e)) return true;
        // a, b, f colinear, f on segment [ab]
        if(o2 == 0 && onSegment(a, b, f)) return true;
        // e, f, a colinear, a on segment [ef]
        if(o3 == 0 && onSegment(e, f, a)) return true;
        // e, f, b colinear, b on segment [ef]
        if(o4 == 0 && onSegment(e, f, b)) return true;

        return false; // Doesn't fall in any of the above cases
    }

    public boolean isInside(Point polygon[], int n, Point p)
    {
        System.err.println("isInside(polygon, "+n+" point("+p.x+","+p.y+"))");
        
        // There must be at least 3 vertices in polygon[]
        if (n < 3)  return false;

        // Create a point for line segment from p to infinite
        Point extreme = new Point(this.max_X, p.y);

        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
        boolean trueIfOdd = true;
        do {
            int next = (i+1)%n;

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) 
            {
                System.err.println("\tdoIntersect --> true");
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
        } while (i != 0);

        // Return true if count is odd, false otherwise
        boolean countIsOdd = count%2 == 1;
        // System.err.println("\t countIsOdd="+countIsOdd+"  trueIfOdd="+trueIfOdd);
        return countIsOdd == trueIfOdd;
    }
}
