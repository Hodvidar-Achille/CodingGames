package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES_TEST;

/**
 * 379 or 378 too high
 * 366 good
 *
 * @author Hodvidar
 */
public final class _Day15 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 15;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final String result = test(RESOURCES_TEST + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '366' - result='" + result + "'");
    }

    private static String subTest(final String inputFile) throws Exception {
        System.err.println("Computing on file:" + inputFile);
        final String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        final String[] opCodeStr = line.split(",");
        sc.close();
        final double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            final String s = opCodeStr[i];
            final double j = Double.parseDouble(s);
            opCode[i] = j;
        }

        final RobotRepair robot = new RobotRepair(opCode, VERBOSE);
        final int result = robot.lookForOxygene();
        return "" + result;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }
}
