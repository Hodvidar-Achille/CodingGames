package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Passed for '5893654'
 *
 * @author Hodvidar
 */
public final class _Day05_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 5;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final List<Integer> outputs = new ArrayList<>();
    private static final int input = 5;

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
        Integer[] arr = new Integer[outputs.size()];
        arr = outputs.toArray(arr);
        result = arrayToString(arr);
        System.err.println("Expected '5893654' - result='" + result + "'");
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
            int j = Integer.parseInt(s);
            opCode[i] = j;
        }

        OpCodeReader cr = new OpCodeReader(opCode);
        cr.setInput(input);
        outputs.add((int) cr.getOutput());

        return arrayToString(opCode);
    }

    private static String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        int f = array.length - 1;
        for (int i = 0; i <= f; i++) {
            if (i != f)
                sb.append(array[i]).append(",");
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }

    private static String arrayToString(Integer[] array) {
        StringBuilder sb = new StringBuilder();
        int f = array.length - 1;
        for (int i = 0; i <= f; i++) {
            if (i != f)
                sb.append(array[i]).append(",");
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }
}
