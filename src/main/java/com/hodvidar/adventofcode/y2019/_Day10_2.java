package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.geometry.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * '709' too high
 * '608' PASSED (means Point '(6.0, 8.0)')
 *
 * @author Hodvidar
 */
public final class _Day10_2 {
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
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test5.txt", 99);
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + "-test4.txt", 199);
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '(6.0; 8.0)' - result='" + result + "'");
    }

    private static String subTest(final String inputFile, final int last) throws Exception {
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
            final int nbDetected = AsteroidOrderer.numberOfDetection(p, asteroids);
            if (nbDetected > maxAsteroid) {
                bestPlace = p;
                maxAsteroid = nbDetected;
            }
        }

        // WRITE SOLUTION 1
        line = sc.nextLine();
        line += " With total of " + sc.nextLine();
        System.err.println("Expected to find asteroid:" + line + " asteroids detected.");
        System.err.println("Found:" + bestPlace + " with:" + maxAsteroid);

        // STUDY n°2
        printIfVerbose("Starting the laser calculation");
        final AsteroidOrderer orderer = new AsteroidOrderer(bestPlace, asteroids);
        final List<Point> orderedAsteroidsToDestroy = orderer.getOrderedAsteroids();
        // WRITE SOLUTION n°2
        line = sc.nextLine();
        sc.close();
        final String result = "" + orderedAsteroidsToDestroy.get(last);
        System.err.println("Expected '" + line + "' and found: " + result);
        printList(orderedAsteroidsToDestroy);
        return result;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile, 199);
    }

    private static void printList(final List<Point> list) {
        if (!VERBOSE)
            return;
        int counter = 0;
        String s = "Astéroids ordered:\n[1 to 5]";
        for (final Point p : list) {
            counter++;
            s += " " + p + " |";
            if (counter % 5 == 0)
                s += "\n[" + (counter + 1) + " to " + (counter + 5) + "]";
        }
        printIfVerbose(s);
    }
}
