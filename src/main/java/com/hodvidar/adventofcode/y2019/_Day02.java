package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * Passed (first value was 9581917)
 * <p>
 * <p>
 * https://adventofcode.com/2019/day/2
 *
 * @author Hodvidar
 */
public final class _Day02 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 3;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
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
        final int[] opCode = new int[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            final String s = opCodeStr[i];
            final int j = Integer.parseInt(s);
            opCode[i] = j;
        }
        // before running the program, replace position 1 with the value 12
        // and replace position 2 with the value 2
        opCode[1] = 12;
        opCode[2] = 2;

        for (int i = 0; i < opCode.length; /* empty increment do it yourself */) {
            final int code = opCode[i];
            if (code == 1) {
                final int p1 = opCode[i + 1];
                final int p2 = opCode[i + 2];
                final int p3 = opCode[i + 3];
                final int v1 = opCode[p1];
                final int v2 = opCode[p2];
                opCode[p3] = v1 + v2;
                i = i + 4;
                continue;
            }
            if (code == 2) {
                final int p1 = opCode[i + 1];
                final int p2 = opCode[i + 2];
                final int p3 = opCode[i + 3];
                final int v1 = opCode[p1];
                final int v2 = opCode[p2];
                opCode[p3] = v1 * v2;
                i = i + 4;
                continue;
            }
            if (code == 99) {
                return arrayToString(opCode);
            }
            throw new IllegalStateException("Should not be here encounter opCode:'" + code + "'");
        }

        return arrayToString(opCode);
    }

    private static String arrayToString(final int[] array) {
        final StringBuilder sb = new StringBuilder();
        final int f = array.length - 1;
        for (int i = 0; i <= f; i++) {
            if (i != f)
                sb.append(array[i]).append(",");
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }

}
