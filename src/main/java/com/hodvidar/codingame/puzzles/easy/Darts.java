package com.hodvidar.codingame.puzzles.easy;

import com.hodvidar.utils.geometry.Circle;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Square;

import java.util.*;

/**
 * Auto-generated code below aims at helping you parse the standard input according to the problem
 * statement.
 **/
public class Darts {
    private static final boolean VERBOSE = false;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) {
        final Darts d = new Darts();
        d.test();
    }

    private static void printSquareInfos(final Square sq) {
        printIfVerbose("printSquareInfos...");
        printIfVerbose("Center(" + sq.center.x + ", " + sq.center.y + ") side=" + sq.side
                + " angle=" + sq.angle);
        printIfVerbose("Points :(" + sq.points[0].x + ", " + sq.points[0].y + ")," + "("
                + sq.points[1].x + ", " + sq.points[1].y + ")," + "(" + sq.points[2].x + ", "
                + sq.points[2].y + ")," + "(" + sq.points[3].x + ", " + sq.points[3].y + ")");
    }

    private static void printCircleInfos(final Circle c) {
        printIfVerbose("printCircleInfos...");
        printIfVerbose("Center(" + c.center.x + ", " + c.center.y + ") radius=" + c.radius);
    }

    public void test() {
        final Scanner in = new Scanner(System.in);
        final int SIZE = in.nextInt();
        printIfVerbose("SIZE: " + SIZE);
        final int N = in.nextInt();
        printIfVerbose("N: " + N);
        final Map<String, Player> playersFast = new HashMap<>();
        final List<Player> players = new ArrayList<>();
        if (in.hasNextLine()) {
            final String nothing = in.nextLine();
            printIfVerbose("nothing: " + nothing);
        }
        for (int i = 0; i < N; i++) {
            final String name = in.nextLine();
            printIfVerbose("name: " + name);
            final Player p = new Player(name, i);
            playersFast.put(name, p);
            players.add(p);
        }

        final Point center = new Point(0, 0);
        final Target target = new Target(center, SIZE);
        printSquareInfos(target.square);
        printCircleInfos(target.circle);
        printSquareInfos(target.diamond);

        final int T = in.nextInt();
        printIfVerbose("T: " + T);
        for (int i = 0; i < T; i++) {
            final String throwName = in.next();
            final int throwX = in.nextInt();
            final int throwY = in.nextInt();
            printIfVerbose("throwName: " + throwName + " - throwX: " + throwX + " - throwY:"
                    + throwY);
            final int points = target.getScore(new Point(throwX, throwY));
            playersFast.get(throwName).addScore(points);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        // Player list will be sorted because Player impl Comparable.
        Collections.sort(players);
        for (final Player p : players)
            System.out.println(p.name + " " + p.getScore());

        in.close();
    }

    // ------------------ Internal Classes ------------------

    class Player implements Comparable<Player> {
        public final int order;
        public final String name;
        private int score = 0;

        public Player(final String name, final int order) {
            this.name = name;
            this.order = order;
        }

        public void addScore(final int points) {
            this.score += points;
        }

        public int getScore() {
            return this.score;
        }

        @Override
        public int compareTo(final Player o) {
            if (this.score < o.score)
                return 1;
            if (this.score > o.score)
                return -1;

            if (this.order < o.order)
                return -1;
            if (this.order > o.order)
                return 1;

            return 0;
        }
    }

    class Target {
        public final Point center;
        public final double side;

        public final Square square;
        public final Circle circle;
        public final Square diamond;

        public Target(final Point p, final double side) {
            this.center = p;
            this.side = side;

            this.square = new Square(this.center, this.side, 0d);
            final double circleRadius = this.side / 2;
            this.circle = new Circle(this.center, circleRadius);
            final double diamondSide = Math.sqrt((circleRadius * circleRadius)
                    + (circleRadius * circleRadius));
            this.diamond = new Square(this.center, diamondSide, 45);
        }

        public int getScore(final Point dart) {
            printIfVerbose("Target.getScore...");
            if (this.diamond.isInside(dart))
                return 15;
            if (this.circle.isInside(dart))
                return 10;
            if (this.square.isInside(dart))
                return 5;
            return 0;
        }
    }

}
