/**
 * 
 */
public class GeometryServices {

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
        double val = (e.getY() - a.getY()) * (b.getX() - e.getX()) -
                (e.getX() - a.getX()) * (b.getY() - e.getY());
        return (val == 0) ?  0 : (val > 0) ? 1 : -1 ;
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
        // There must be at least 3 vertices in polygon[]
        if (n < 3)  return false;

        // Create a point for line segment from p to infinite
        Point extreme = new Point(Double.MAX_VALUE, p.getY());

        // Count intersections of the above line with sides of polygon
        int count = 0, i = 0;
        do {
            int next = (i+1)%n;

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, extreme)) {
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise continue
                if (orientation(polygon[i], polygon[next], p) == 0) {
                    return onSegment(polygon[i], polygon[next], p);
                }
                count++;
            }
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        return count%2 == 1;  // Same as (count%2 == 1)
    }
}
