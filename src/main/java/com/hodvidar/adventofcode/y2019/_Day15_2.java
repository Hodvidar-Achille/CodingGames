package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * 306 too low (of course)
 * <p>
 * 384 good
 *
 * @author Hodvidar
 */
public final class _Day15_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 15;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '384' - result='" + result + "'");
    }

    private static String subTest(String inputFile) throws Exception {
        System.err.println("Computing on file:" + inputFile);
        String line;
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        String[] opCodeStr = line.split(",");
        sc.close();
        double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            String s = opCodeStr[i];
            double j = Double.parseDouble(s);
            opCode[i] = j;
        }

        RobotRepair robot = new RobotRepair(opCode, VERBOSE);
        int result = robot.fillWithOxygene();
        // Stuff
        return "" + result;
    }

    private static String test(String inputFile) throws Exception {
        return subTest(inputFile);
    }
}
