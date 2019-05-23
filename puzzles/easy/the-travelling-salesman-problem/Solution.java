import java.util.*;
import java.io.*;
import java.math.*;

/**
 * // NOT GOOD FOR NOW.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int sumDistance = 0;
        Point previous = null;
        Point first = null;
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            int Y = in.nextInt();
            Point actual = new Point(X, Y);
            if(first == null)
                first = actual;
            if(previous != null)
            {
                sumDistance += getDistance(previous, actual);
            }
            previous = actual;
        }

        sumDistance += getDistance(previous, first);
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(sumDistance);
    }
    
    public static double getDistance(Point p1, Point p2)
	{
		double x = Math.pow((p1.x - p2.x), 2);
		double y = Math.pow((p1.y - p2.y), 2);

		return Math.sqrt(x + y);
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

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;

		Point p = (Point) obj;
		return this.x == p.x && this.y == p.y;
	}

	@Override
	public int hashCode()
	{
		return (int) (31 * this.x + 89 * this.y);
	}
}

