package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * '13' not good
 * <p>
 * 2021 GOOD
 *
 * @author Hodvidar
 */
public final class _Day11 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 11;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '2021' - result='" + result + "'");
    }

    private static String test(final String inputFile) throws Exception {
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

        final RobotPainter robot = new RobotPainter(opCode);
        robot.paint();
        final int result = robot.getNumberOfPanelPainted();

        // Stuff
        return "" + result;
    }
}
