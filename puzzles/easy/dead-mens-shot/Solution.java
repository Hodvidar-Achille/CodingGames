import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution 
{

    private static final String HIT = "hit";
    private static final String MISS = "miss";
    
    public static void main(String args[]) 
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Point[] polygon = new Point[N];
        System.err.println("Polygon of N points : "+N);
        for (int i = 0; i < N; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            System.err.println((i+1)+": x="+x+"  -  y="+y);
            polygon[i] = new Point(x, y);
        }
        int M = in.nextInt();
        
        String[] results = new String[M];
        GeometryServices gs = new GeometryServices();
        
        System.err.println("Number of shoots : "+M);
        for (int i = 0; i < M; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            
            System.err.println((i+1)+": x="+x+"  -  y="+y);
            Point shoot = new Point(x, y);
            if(gs.isInside(polygon, N, shoot))
                System.out.println(HIT);
            else
                System.out.println(MISS);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        // System.out.println("hit_or_miss");
    }
}

class GeometryServices {

    /**
     * When ALREADY KNOWING that E is on the line (AB)
     * @param a
     * @param b
     * @param e
     * @return true if e on segment [ab]
     */
    public boolean onSegment(Point a, Point b, Point e) {
        return (e.getX() <= Math.max(a.getX(), b.getX()) && e.getX() >= Math.min(a.getX(), b.getX()) &&
                e.getY() <= Math.max(a.getY(), b.getY()) && e.getY() >= Math.min(a.getY(), b.getY())) ? true : false;
    }

    /**
     * Given the points A, B, E, check if A-->E-->B-->A is clockwise, counter clockwise, or aligned
     * @param a
     * @param b
     * @param e
     * @return 0 if aligned, 1 if clockwise, -1 if counterclockwise
     */
    public int orientation(Point a, Point b, Point e)
    {
        System.err.println("\t\t\torientation(a("+a.getX()+","+a.getY()+"),"
            +"b("+b.getX()+","+b.getY()+"),"
            +"e("+e.getX()+","+e.getY()+")");
        int val = (b.getY() - a.getY()) * (e.getX() - b.getX()) - (b.getX() - a.getX()) * (e.getY() - b.getY());
        System.err.println("\t\t\tval:"+val);
        return (val == 0) ? 0 : (val > 0) ? 1 : -1;
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
        System.err.println("\t\tdoIntersect(a("+a.getX()+","+a.getY()+"),"
            +"b("+b.getX()+","+b.getY()+"),"
            +"e("+e.getX()+","+e.getY()+"),"
            +"f("+f.getX()+","+f.getY()+"),");
            
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
        System.err.println("isInside(polygon, "+n+" point("+p.getX()+","+p.getY()+"))");
        
        // There must be at least 3 vertices in polygon[]
        if (n < 3)  return false;

        // Create a point for line segment from p to infinite
        Point extreme = new Point(Integer.MAX_VALUE, p.getY());

        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
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
                count++;
            }
             System.err.println("\tdoIntersect --> false");
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return count%2 == 1;  // Same as (count%2 == 1)
    }
}

class Point 
{

    private final int x;

    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
