package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Wire;
import com.hodvidar.utils.geometry.WireBuilder;

import java.io.File;
import java.util.List;
import java.util.Scanner;

/**
 * Passed 14228
 *
 * @author Hodvidar
 */
public final class _Day03_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 4;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        int result = test("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
    }

    private static int test(String inputFile) throws Exception {
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // 2 Lines
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        sc.close();

        String[] directions1 = line1.split(",");
        String[] directions2 = line2.split(",");

        Point origin = new Point(0, 0);

        WireBuilder wb1 = new WireBuilder(origin);
        WireBuilder wb2 = new WireBuilder(origin);

        for (String d : directions1)
            wb1.addInstruction(d);
        for (String d : directions2)
            wb2.addInstruction(d);

        Wire w1 = wb1.getWire();
        Wire w2 = wb2.getWire();

        List<Point> intersections = w1.getIntersections(w2);

        double minDistance = Double.MAX_VALUE;
        for (Point p : intersections) {
            if (p.equals(origin))
                continue;
            double d1 = w1.intersectDistance(p);
            double d2 = w2.intersectDistance(p);
            double dT = d1 + d2;
            printIfVerbose("Point: " + p + " d1=" + d1 + "  d2=" + d2 + "  \tdT=" + dT);
            // ---- There is a bug... (containing it) ----
            if (d1 < 0 || d2 < 0)
                throw new IllegalStateException("Point: '" + p + "' is not on both wire, should not happen.");
            // ----
            if (dT < minDistance)
                minDistance = dT;
        }

        return (int) minDistance;
    }

}
