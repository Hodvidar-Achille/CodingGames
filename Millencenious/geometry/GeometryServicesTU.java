public class GeometryServicesTU {

    @Test
    public void testOrientation(){
        GeometryServices gs = new GeometryServices();
        /*
        |           b
        |
        | e
        |
        | a
        |__________     */
        Point a = new Point(1d, 1d);
        Point b = new Point(5d, 5d);
        Point e = new Point(1d, 3d);
        // a --> e --> b  clockwise
        Assert.assertEquals(gs.orientation(a, b, e), 1);
        /*
        |           b
        |
        |                 e
        |
        | a
        |__________     */
        e = new Point(7d, 3d);
        // a --> e --> b  counterclockwise
        Assert.assertEquals(gs.orientation(a, b, e), -1);
        /*
        |           b
        |
        |      e
        |
        | a
        |__________     */
        e = new Point(3d, 3d);
        // a --> e --> b  aligned
        Assert.assertEquals(gs.orientation(a, b, e), 0);
        // and e in [ab]
        Assert.assertEquals(gs.onSegment(a, b, e), true);
        /*
        |             e
        |           b
        |
        |
        |
        | a
        |__________     */
        e = new Point(6d, 6d);
        // a --> e --> b  aligned
        Assert.assertEquals(gs.orientation(a, b, e), 0);
        // and e not in [ab]
        Assert.assertEquals(gs.onSegment(a, b, e), false);
    }

    @Test
    public void testIntersection(){
        GeometryServices gs = new GeometryServices();
        /*
        |           b
        |
        | e             f
        |
        | a
        |__________     */
        Point a = new Point(1d, 1d);
        Point b = new Point(5d, 5d);
        Point e = new Point(1d, 3d);
        Point f = new Point(7d, 3d);
        Assert.assertEquals(gs.doIntersect(a,b,e,f), true);
        /*
        |           b
        |
        |               f     e
        |
        | a
        |__________     */;
        e = new Point(10d, 3d);
        Assert.assertEquals(gs.doIntersect(a,b,e,f), false);
    }

    @Test
    public void testIsinside(){
        GeometryServices gs = new GeometryServices();
        /*
        |           b
        |
        | e      z       f
        |
        | a
        |__________     */
        Point a = new Point(1d, 1d);
        Point b = new Point(5d, 5d);
        Point e = new Point(1d, 3d);
        Point f = new Point(7d, 3d);
        Point z = new Point(3d, 3d);
        Point[] polygone = new Point[4];
        polygone[0] = a;
        polygone[1] = b;
        polygone[2] = e;
        polygone[3] = f;
        Assert.assertEquals(gs.isInside(polygone, 4, z), true);
       /*
        |           zb
        |
        | e             f
        |
        | a
        |__________     */
        z = new Point(4d, 5d);
        Assert.assertEquals(gs.isInside(polygone, 4, z), false);
        z = new Point(5d, 5d);
        Assert.assertEquals(gs.isInside(polygone, 4, z), true);
        z = new Point(1d, 3d);
        Assert.assertEquals(gs.isInside(polygone, 4, z), true);
    }
}
