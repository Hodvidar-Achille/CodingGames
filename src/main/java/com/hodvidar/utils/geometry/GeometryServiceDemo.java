package com.hodvidar.utils.geometry;

/**
 * Demo class with below utils classes and used object's classes. by Hodvidar
 **/
public final class GeometryServiceDemo {

    public static void main(final String[] args) {
        // TEST
        System.err.println("Start GeometricServicesDemo main...");
        // Square in positive number, with side of, middle at (3,3);
        final Point[] square1Points = new Point[4];
        /*
         * | A B | | x | |_D____C_____
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

        center = new Point(0, 0);
        side = 20;
        square1 = new Square(center, side, 0);
        printSquareInfos(square1);
        circle1 = new Circle(center, side / 2);
        side = 14.142135623730951;
        final Square diamond = new Square(center, side, -45);
        printSquareInfos(square1);
        printCircleInfos(circle1);
        printSquareInfos(diamond);
        final Point p1 = new Point(-10, 10);
        final Point p2 = new Point(-5, -7);

        final boolean p1InSquare = square1.isInside(p1);
        final boolean p1InCircle = circle1.isInside(p1);
        final boolean p1InDiamond = diamond.isInside(p1);
        final boolean p2InSquare = square1.isInside(p2);
        final boolean p2InCircle = circle1.isInside(p2);
        final boolean p2InDiamond = diamond.isInside(p2);
        System.err.println("p1InSquare=" + p1InSquare);
        System.err.println("p1InCircle=" + p1InCircle);
        System.err.println("p1InDiamond=" + p1InDiamond);
        System.err.println("p2InSquare=" + p2InSquare);
        System.err.println("p2InCircle=" + p2InCircle);
        System.err.println("p2InDiamond=" + p2InDiamond);

        System.err.println("--- Test for Sphere services ---");
        final double radius = 10;
        final double area = GeometryServices.getSphereArea(radius);
        final double volume = GeometryServices.getSphereVolume(radius);
        System.err.println("radius =" + radius);
        System.err.println("area =" + area);
        System.err.println("volume =" + volume);
        final double radiusBis = GeometryServices.getSphereRadius(volume);
        System.err.println("radiusBis =" + radiusBis);

        System.err.println("End of GeometricServicesDemo main.");
    }

    private static void printSquareInfos(final Square sq) {
        System.err.println("printSquareInfos...");
        System.err.println("Center(" + sq.center.x + ", " + sq.center.y + ") side=" + sq.side
                + " angle=" + sq.angle);
        System.err.println("Points :(" + sq.points[0].x + ", " + sq.points[0].y + ")," + "("
                + sq.points[1].x + ", " + sq.points[1].y + ")," + "(" + sq.points[2].x + ", "
                + sq.points[2].y + ")," + "(" + sq.points[3].x + ", " + sq.points[3].y + ")");
    }

    private static void printCircleInfos(final Circle c) {
        System.err.println("printCircleInfos...");
        System.err.println("Center(" + c.center.x + ", " + c.center.y + ") radius=" + c.radius);
    }
}