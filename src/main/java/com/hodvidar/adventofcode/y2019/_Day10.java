package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Segment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

public final class _Day10 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 10;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final char ASTEROID = '#';

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test1.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test2.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test3.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test4.txt");
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '260' - result='" + result + "'");
    }

    private static String subTest(final String inputFile) throws Exception {
        String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        int y = 0;
        final List<Point> asteroids = new ArrayList<>();
        // CREATE ASTEROIDS
        while (true) {
            line = sc.nextLine(); // KO : 203 and 0
            if (line.isEmpty())
                break;

            int x = 0;
            for (final char c : line.toCharArray()) {
                if (c == ASTEROID) {
                    asteroids.add(new Point(x, y));
                }
                x++;
            }
            y++;
        }

        // STUDY ASTEROIDS
        Point bestPlace = null;
        int maxAsteroid = 0;
        for (final Point p : asteroids) {
            final int nbDetected = numberOfDetection(p, asteroids);
            if (nbDetected > maxAsteroid) {
                bestPlace = p;
                maxAsteroid = nbDetected;
            }
        }

        // WRITE SOLUTION
        line = sc.nextLine();
        line += " With total of " + sc.nextLine();
        sc.close();
        System.err.println("Expected to find asteroid:" + line + " asteroids detected.");
        System.err.println("Found:" + bestPlace + " with:" + maxAsteroid);
        return "" + maxAsteroid;
    }

    public static int numberOfDetection(final Point p, final List<Point> others) {
        int counter = 0;
        for (final Point o : others) {
            if (o.equals(p))
                continue;
            if (lineOfSightClear(p, o, others))
                counter++;
        }
        return counter;
    }

    private static boolean lineOfSightClear(final Point p1, final Point p2, final List<Point> others) {
        final Segment s = new Segment(p1, p2);
        boolean lineOfSightClear = true;
        for (final Point o2 : others) {
            if (o2.equals(p1) || o2.equals(p2))
                continue;
            if (GeometryServices.isOnSegment(o2, s)) {
                lineOfSightClear = false;
                break;
            }
        }
        return lineOfSightClear;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }
}
