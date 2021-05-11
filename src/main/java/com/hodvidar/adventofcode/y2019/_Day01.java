package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

// TODO replace all main method by a test

/**
 * Passed
 *
 * @author Hodvidar
 */
public final class _Day01 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 1;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final int result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
    }

    private static int test(final String inputFile) throws Exception {
        String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // for(int i = 0; i< input.length; i++)
        int totalFuel = 0;
        while (sc.hasNextLine()) {
            // line = input[i];
            line = sc.nextLine();
            final int mass = Integer.parseInt(line);
            final int fuel = calculateFuel(mass);
            totalFuel += fuel;
        }

        sc.close();

        return totalFuel;
    }

    /**
     * Calculates Fuel needed for the given mass. <br/>
     * divide by 3, round down, substract 2
     *
     * @param mass
     * @return
     */
    private static int calculateFuel(final int mass) {
        final double r = mass / 3d;
        int r2 = (int) Math.floor(r);
        r2 = r2 - 2;
        return r2;
    }
}
