/**
* Improved Geometry service for point inside polygon.
* by Hodvidar
*/
class GeometryServices
{
    /**
     * Used to check point intersections, must be the maximum X value for all given points.
     * Avoid to use number to close to Double.MAX_VALUE to avoid computation errors.
     */
    private final double max_X;
    
    /**
     * @param max_X : Don't use Double.MAX_VALUE.
     */
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
