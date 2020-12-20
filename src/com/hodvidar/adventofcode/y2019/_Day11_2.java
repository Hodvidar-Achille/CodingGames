package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

/**
 * ANSWER PRINT IN THE CONSOLE
 * 'LBJHEKLH'
 *
 * @author Hodvidar
 */
public final class _Day11_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 11;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        String result = test("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '??' - result='" + result + "'");
    }

    private static String test(String inputFile) throws Exception {
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

        RobotPainter robot = new RobotPainter(opCode, Panel.WHITE);
        robot.paint();
        int result = robot.getNumberOfPanelPainted();

        // ANSWER PRINT IN THE CONSOLE
        robot.printPanels();
        // Stuff
        return "" + result;
    }

}
