package com.hodvidar.utils.geometry;

/**
 * Improved Geometry service for point inside polygon. by Hodvidar
 */
public class GeometryServices {
    private final double infinite;
    private final double stepToSwitchInfiniteY;

    public GeometryServices() {
        this(9000000000000000000d);
    }

    public GeometryServices(final double maximumCoordinate) {
        final double tooMuchToBeSafe = Math.round(Math.sqrt(Math.sqrt(Double.MAX_VALUE)));
        if (maximumCoordinate > tooMuchToBeSafe)
            throw new IllegalArgumentException(
                    "Max X coordinate is too high, risk of double overflow during calculations.");

        this.infinite = maximumCoordinate * 10;
        this.stepToSwitchInfiniteY = this.infinite / 5;
    }

    /**
     * @param e the point E
     * @param s the segment [AB]
     * @return true if E is on [AB]
     */
    public static boolean isOnSegment(final Point e, final Segment s) {
        final boolean onSameLine = areColinear(e, s.p1, s.p2);
        final boolean betweenPoint = isInsideCoordinate(e, s.p1, s.p2);
        return onSameLine && betweenPoint;
    }

    /**
     * check if point E is inside a square form by : <br/>
     * P1(minX, minY) <br/>
     * P2(maxX, minY) <br/>
     * P3(maxX, maxY) <br/>
     * P4(minX, maxY) <br/>
     * With minX, minY, maxX, maxY taken from points A and B.
     *
     * @param e
     * @param a
     * @param b
     * @return true if e on segment [ab]
     */
    public static boolean isInsideCoordinate(final Point e, final Point a, final Point b) {
        // System.err.println("\t\tonSegment()");
        final boolean betweenX = e.x <= Math.max(a.x, b.x) && e.x >= Math.min(a.x, b.x);
        final boolean betweenY = e.y <= Math.max(a.y, b.y) && e.y >= Math.min(a.y, b.y);
        // System.err.println("\t\tbetweenX="+betweenX+" betweenY="+betweenY);
        return betweenX && betweenY;
    }

    public static boolean areColinear(final Point p1, final Point p2, final Point p3) {
        return orientation(p1, p2, p3) == 0;
    }

    /**
     * To find orientation of ordered triplet (p1, p2, p3). The function returns the following
     * values : <br/>
     * 0 --> p, q and r are colinear <br/>
     * 1 --> Clockwise <br/>
     * -1 --> Counterclockwise <br/>
     **/
    public static int orientation(final Point p1, final Point p2, final Point p3) {
        // System.err.println("\t\torientation(p1("+p1.x+","+p1.y+"), p2("+p2.x+","+p2.y+"),
        // p3("+p3.x+","+p3.y+"))");
        // Slope of line segment (p1, p2): σ = (y2 - y1)/(x2 - x1)
        // Slope of line segment (p2, p3): τ = (y3 - y2)/(x3 - x2)
        // If σ > τ, the orientation is clockwise (right turn)

        // Using above values of σ and τ, we can conclude that,
        // the orientation depends on sign of below expression:
        // (y2 - y1)*(x3 - x2) - (y3 - y2)*(x2 - x1)

        // Can work but risk of dividing by 0 !
        // int slopeP1toP2 = (p2.y - p1.y) / (p2.x - p1.x);
        // int slopeP2toP3 = (p3.y - p2.y) / (p3.x - p2.x);
        // int val = slopeP1toP2 - slopeP2toP3;

        final double val = ((p2.y - p1.y) * (p3.x - p2.x)) - ((p2.x - p1.x) * (p3.y - p2.y));
        // System.err.println("\t\tval="+val);
        if (val == 0d)
            return 0; // colinear
        // clock or counterclock wise
        return (val > 0) ? 1 : -1;
    }

    /**
     * Segment AB is intersecting with segment EF ?
     *
     * @param a
     * @param b
     * @param e
     * @param f
     * @return true if [ab] and [ef] intersected
     */
    public static boolean doIntersect(final Point a, final Point b, final Point e, final Point f) {
        // System.err.println("\tdoIntersect(a(" + a.x + "," + a.y + "), b(" + b.x + "," + b.y
        // + "), e(" + e.x + "," + e.y + "), f(" + f.x + "," + f.y + "))");
        // if o1 != o2 (ef) intersect (ab) (lines)
        final int o1 = orientation(a, b, e);
        final int o2 = orientation(a, b, f);
        // if also o3 != o4 [ef] intersect [ab] [segments]
        final int o3 = orientation(e, f, a);
        final int o4 = orientation(e, f, b);

        // simple segment intersection
        if (o1 != o2 && o3 != o4)
            return true;

        // special Cases
        // a, b, e colinear, e on segment [ab]
        if (o1 == 0 && isInsideCoordinate(e, a, b))
            return true;
        // a, b, f colinear, f on segment [ab]
        if (o2 == 0 && isInsideCoordinate(f, a, b))
            return true;
        // e, f, a colinear, a on segment [ef]
        if (o3 == 0 && isInsideCoordinate(a, e, f))
            return true;
        // e, f, b colinear, b on segment [ef]
        return o4 == 0 && isInsideCoordinate(b, e, f);// Doesn't fall in any of the above cases
    }

    /**
     * Returns the point of intersection between segment [AB] and [EF], can be <code>null</code> if
     * the segments do not intersect.
     * <p>
     * <b>Only vertical and horizontal segment for now</b>
     * <p>
     *
     * @param a First Point of segment [AB]
     * @param b Second Point of segment [AB]
     * @param e First Point of segment [EF]
     * @param f Second Point of segment [EF]
     * @return a <code>Point</code> or <code>null</code>.
     */
    public static Point getIntersect(final Point a, final Point b, final Point e, final Point f) {
        // if(a.toString().equals("(-1753.0; -115.0)") && e.toString().equals("(-1728.0; -271.0)"))
        // System.out.println("test");

        // (1) On point touch another (same point).
        if (a.equals(e))
            return a;
        if (a.equals(f))
            return a;
        if (b.equals(e))
            return b;
        if (b.equals(f))
            return b;

        // Code similar to 'doIntersect' method
        final int o1 = orientation(a, b, e);
        final int o2 = orientation(a, b, f);
        // if also o3 != o4 [ef] intersect [ab] [segments]
        final int o3 = orientation(e, f, a);
        final int o4 = orientation(e, f, b);

        // (2) One point inside the other segment/
        // a, b, e colinear, e on segment [ab]
        if (o1 == 0 && isInsideCoordinate(e, a, b))
            return e;
        // a, b, f colinear, f on segment [ab]
        if (o2 == 0 && isInsideCoordinate(f, a, b))
            return f;
        // e, f, a colinear, a on segment [ef]
        if (o3 == 0 && isInsideCoordinate(a, e, f))
            return a;
        // e, f, b colinear, b on segment [ef]
        if (o4 == 0 && isInsideCoordinate(b, e, f))
            return b;

        // No colinear

        // Look for simple segment intersection
        if (!(o1 != o2 && o3 != o4))
            return null;

        // (3) Segment intersect (BASIQUE)
        // We know one is Vertical, one is Horizontal
        final boolean abIsVertical = a.x == b.x;
        if (abIsVertical) {
            // Verification to block wrong uses
            if (e.y != f.y)
                throw new IllegalStateException("Case not supported");
            // AB | EF --
            return new Point(a.x, e.y);
        } else {
            // Verification to block wrong uses
            if (e.y == f.y)
                throw new IllegalStateException("Case not supported");
            // AB -- EF |
            return new Point(e.x, a.y);
        }
    }

    /**
     * Returns a new point created at the distance of start point with given angle.
     *
     * @param angle : in degre°.
     */
    public static Point createPoint(final Point start, final double distance, final double angle) {
        final double angleRad = Math.toRadians(angle);

        final double cos = Math.cos(angleRad);
        double x = start.x + cos * distance;
        final double sin = Math.sin(angleRad);
        double y = start.y + sin * distance;

        x = Math.round(x);
        y = Math.round(y);
        return new Point(x, y);
    }

    public static double getManhattanDistance(final Point p1, final Point p2) {
        final double x = Math.abs(p1.x - p2.x);
        final double y = Math.abs(p1.y - p2.y);

        return x + y;
    }

    public static double getDistance(final Point p1, final Point p2) {
        final double x = Math.pow((p1.x - p2.x), 2);
        final double y = Math.pow((p1.y - p2.y), 2);

        return Math.sqrt(x + y);
    }

    /**
     * Check if angle (degre°) form by intersection between (p1 p2) and (p2, p3) is 90°.
     */
    public static boolean isRightAngleCorner(final Point p1, final Point p2, final Point p3) {
        final double p1_p2 = getDistance(p1, p2);
        final double p2_p3 = getDistance(p2, p3);
        final double p1_p3 = getDistance(p1, p3);
        final double p1_p2Sq = Math.round(p1_p2 * p1_p2);
        final double p2_p3Sq = Math.round(p2_p3 * p2_p3);
        final double p1_p3Sq = Math.round(p1_p3 * p1_p3);
        return Math.round(p1_p3Sq) == Math.round(p1_p2Sq + p2_p3Sq);
    }

    /**
     * Get the point at the center of the circle formed by the 3 given points. Note : the points
     * cannot be all aligned.
     */
    public static Point getCenter(final Point p1, final Point p2, final Point p3) {
        // 1) Check that the points are no all align.
        if (!Circle.checkPoints(p1, p2, p3))
            throw new IllegalArgumentException("Points can't be aligned");

        // 2) Arrange points to have 2 lines that are never perfectly vertical.
        double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;
        if (p1.x != p2.x && p2.x != p3.x) {
            x1 = p1.x;
            x2 = p2.x;
            x3 = p3.x;
            y1 = p1.y;
            y2 = p2.y;
            y3 = p3.y;
        } else if (p2.x != p3.x && p3.x != p1.x) {
            x1 = p2.x;
            x2 = p3.x;
            x3 = p1.x;
            y1 = p2.y;
            y2 = p3.y;
            y3 = p1.y;
        } else
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
         * // 3) Find the center point from the intersection of the 2 perpendicular bisectors. //
         * Should never meet the 'divided by 0' error. double m1 = (y2 - y1) / (x2 - x1); double m2
         * = (y3 - y2) / (x3 - x2); double center_x = (((m1 * m2) * (y1 - y3)) + (m2 * (x1 + x2)) -
         * (m1 * (x2 + x3))) / (2 * (m2 - m1)); double center_y = ((1 / m1) * (center_x - ((x1 + x2)
         * / 2))) + ((y1 + y2) / 2); if (isNaN(center_y)) center_y = ((1 / m2) * (center_x - ((x2 +
         * x3) / 2))) + ((y2 + y3) / 2); // XXX Fails sometimes...
         */

        // ----------- TRY another method ------------------
        // https://www.geeksforgeeks.org/equation-of-circle-when-three-points-on-the-circle-are-given/
        final double x12 = x1 - x2;
        final double x13 = x1 - x3;

        final double y12 = y1 - y2;
        final double y13 = y1 - y3;

        final double y31 = y3 - y1;
        final double y21 = y2 - y1;

        final double x31 = x3 - x1;
        final double x21 = x2 - x1;

        // x1^2 - x3^2
        final double sx13 = Math.pow(x1, 2) - Math.pow(x3, 2);

        // y1^2 - y3^2
        final double sy13 = Math.pow(y1, 2) - Math.pow(y3, 2);

        final double sx21 = Math.pow(x2, 2) - Math.pow(x1, 2);

        final double sy21 = Math.pow(y2, 2) - Math.pow(y1, 2);

        final double f = ((sx13) * (x12) + (sy13) * (x12) + (sx21) * (x13) + (sy21) * (x13))
                / (2 * ((y31) * (x12) - (y21) * (x13)));
        final double g = ((sx13) * (y12) + (sy13) * (y12) + (sx21) * (y13) + (sy21) * (y13))
                / (2 * ((x31) * (y12) - (x21) * (y13)));

        final double center_x = -g;
        final double center_y = -f;

        return new Point(center_x, center_y);
    }

    @SuppressWarnings("unused")
    private static boolean isNaN(final double v) {
        return (v != v);
    }

    /**
     * Creates 4 points (from Top-left (NW) to bottom-left (SW) : NW->NE->SE->SW), forming a square
     * with the given center, side and inclined by the given angle.
     */
    public static Point[] createSquarePoints(final Point center, final double side, final double angle) {
        final double halfSide = side / 2;
        final double halfSideSq = halfSide * halfSide;
        final double radius = Math.sqrt(halfSideSq + halfSideSq);
        final Point[] points = new Point[4];
        points[0] = GeometryServices.createPoint(center, radius, angle + 135);
        points[1] = GeometryServices.createPoint(center, radius, angle + 45);
        points[2] = GeometryServices.createPoint(center, radius, angle - 45);
        points[3] = GeometryServices.createPoint(center, radius, angle - 135);
        return points;
    }

    /**
     * Returns the angle in degrees° formed by the vector p1->p2 and the vector p1->X
     */
    public static double getAngleWithXLine(final Point p1, final Point p2) {
        final Point pX = new Point(p1.x + 100, p1.y);
        return getAngle(p1, p2, pX);
    }

    /**
     * Angle formed by (p1 p2) and (p1 p3)
     * <p>
     * Théorème du cosinus : oppose^2 = adjacent1^2 + adjacent2^2 -
     * 2*adjacent1*p2_p3*adjacent2(angle); <--> cos(angle) = ( (adjacent1^2 + adjacent2^2) -
     * (oppose^2) ) / ( 2*adjacent1*adjacent2 ); <--> angle = arccors( ( (adjacent1^2 + adjacent2^2)
     * - (oppose^2) ) / ( 2*adjacent1*adjacent2 ) );
     */
    public static double getAngle(final Point p1, final Point p2, final Point p3) {
        final double adjacent1 = getDistance(p1, p2);
        final double adjacent2 = getDistance(p1, p3);
        final double oppose = getDistance(p2, p3);
        final double adjacent1Sq = Math.round(adjacent1 * adjacent1);
        final double adjacent2Sq = Math.round(adjacent2 * adjacent2);
        final double opposeSq = Math.round(oppose * oppose);
        final double nominator = (adjacent1Sq + adjacent2Sq - opposeSq);
        final double denominator = (2 * adjacent1 * adjacent2);
        final double cos = nominator / denominator;
        final double angle = Math.toDegrees(Math.acos(cos));
        // angle = Math.round(angle);
        return angle;
    }

    public static String getQuadrilateralType(final Point... points) {
        String result = "Not a Quadrilateral";

        final boolean isQuadrilateral = Quadrilateral.checkPoints(points);
        if (!isQuadrilateral)
            return result;
        result = Quadrilateral.class.getSimpleName();

        final boolean isParallelogram = Parallelogram.checkPoints(points);
        if (!isParallelogram)
            return result;
        result = Parallelogram.class.getSimpleName();

        final boolean isRhombus = Rhombus.checkPoints(points);
        final boolean isRectangle = Rectangle.checkPoints(points);
        if (!isRhombus && !isRectangle)
            return result;

        if (isRectangle)
            result = Rectangle.class.getSimpleName();
        else
            return Rhombus.class.getSimpleName();

        if (!Square.checkPoints(points))
            return result;

        result = Square.class.getSimpleName();
        return result;
    }

    public static double getSphereVolume(final double sphereRadius) {
        final double sphereVolume = (4.0 / 3.0) * Math.PI * Math.pow(sphereRadius, 3);
        return sphereVolume;
    }

    public static double getSphereArea(final double sphereRadius) {
        final double sphereArea = 4 * Math.PI * Math.pow(sphereRadius, 2);
        return sphereArea;
    }

    public static double getSphereRadius(final double sphereVolume) {
        final double intermediateResult = sphereVolume / ((4.0 / 3.0) * Math.PI);
        final double radius = Math.cbrt(intermediateResult);
        return radius;
    }

    public boolean isInside(final Point[] polygon, final int n, final Point p) {
        // System.err.println("isInside(polygon, " + n + ", p(" + p.x + "," + p.y + "))");

        // There must be at least 3 vertices in polygon[]
        if (n < 3)
            return false;

        // Create a point for line segment from p to infinite (Infinite in the NE direction).
        final Point pointToInfinite = new Point(this.infinite, this.infinite - this.stepToSwitchInfiniteY);

        return this.isInside_core(polygon, n, p, pointToInfinite);
    }

    private boolean isInside_core(final Point[] polygon, final int n, final Point p, final Point pointToInfinite) {
        int count = 0, i = 0;
        do {
            final int next = (i + 1) % n;

            // Check if the line segment from 'p' to 'extreme' intersects
            // with the line segment from 'polygon[i]' to 'polygon[next]'
            if (doIntersect(polygon[i], polygon[next], p, pointToInfinite)) {
                // System.err.println("\tdoIntersect --> true");
                // If the point 'p' is colinear with line segment 'i-next',
                // then check if it lies on segment. If it lies, return true,
                // otherwise continue
                if (orientation(polygon[i], polygon[next], p) == 0) {
                    return isInsideCoordinate(p, polygon[i], polygon[next]);
                }

                // Handle when (p extreme) intersecting with polygon
                // exactly on one of its point.
                if (orientation(p, pointToInfinite, polygon[i]) == 0) {
                    // Intersecting exactly a point of the polygon
                    // can make the loop count useless to assert if the point p
                    // is inside the polygon, we try again with a new infinite point.
                    final double newY = pointToInfinite.y - this.stepToSwitchInfiniteY;
                    final Point newPointToInfinite = new Point(this.infinite, newY);
                    // Note could be done not recursively (with a higher level loop).
                    return this.isInside_core(polygon, n, p, newPointToInfinite);
                }

                count++;
            }
            // System.err.println("\tdoIntersect --> false");
            i = next;
        } while (i != 0);

        // Return true if count is odd, false otherwise
        final boolean countIsOdd = (count % 2 == 1);
        // System.err.println("\t count=" + count + " - countIsOdd=" + countIsOdd);
        return countIsOdd;
    }

}