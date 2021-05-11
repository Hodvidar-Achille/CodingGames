package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * Passed : 9219874
 *
 * @author Hodvidar
 */
public final class _Day05 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 5;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final int POSITION_MODE = 0;
    private static final int IMMEDIATE_MODE = 1;
    private static final List<Integer> outputs = new ArrayList<>();
    private static final int input = 1;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
        Integer[] arr = new Integer[outputs.size()];
        arr = outputs.toArray(arr);
        result = arrayToString(arr);
        System.err.println("Expected '9219874' - result='" + result + "'");
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

        for (int i = 0; i < opCode.length; /* empty increment do it yourself */) {
            final int code = opCode[i];
            printIfVerbose("code: " + code);
            printIfVerbose("" + arrayToString(opCode));
            if (code == 1 || code == 2) {
                opCode1_2(code, opCode, i, POSITION_MODE, POSITION_MODE);
                i = i + 4;
                continue;
            }
            if (code == 3) {
                final int p1 = opCode[i + 1];
                opCode[p1] = input;
                i = i + 2;
                continue;
            }
            if (code == 4) {
                opCode4(opCode, i, POSITION_MODE);
                i = i + 2;
                continue;
            }
            if (code == 99) {
                return arrayToString(opCode);
            }
            if (code > 99) {
                final int subCode = code % 100;
                if (subCode != 1 && subCode != 2 && subCode != 4)
                    throw new IllegalStateException("This case should heppen ? code=" + code);

                final int modeFor3 = code / 10000;
                if (modeFor3 == 1)
                    throw new IllegalStateException("Should not happen opCode:'" + code + "'");

                final int modeFor2 = (code / 1000) % 10;
                final int modeFor1 = (code / 100) % 10;
                if (subCode == 1 || subCode == 2) {
                    opCode1_2(subCode, opCode, i, modeFor1, modeFor2);
                    i = i + 4;
                    continue;
                }
                if (subCode == 4) {
                    opCode4(opCode, i, modeFor1);
                    i = i + 2;
                    continue;
                }
                throw new IllegalStateException("Should not be here (2) encounter opCode:'" + code + "'");
            }
            throw new IllegalStateException("Should not be here (1) encounter opCode:'" + code + "'");
        }

        return arrayToString(opCode);
    }

    private static void opCode1_2(final int code, final int[] opCode, final int i, final int parameterMode1, final int parameterMode2) {
        final int p3 = opCode[i + 3];
        final int v1 = getValue(opCode, i + 1, parameterMode1);
        final int v2 = getValue(opCode, i + 2, parameterMode2);
        opCode[p3] = (code == 1) ? v1 + v2 : v1 * v2;
    }

    private static void opCode4(final int[] opCode, final int i, final int parameterMode1) {
        final int v1 = getValue(opCode, i + 1, parameterMode1);
        outputs.add(v1);
    }

    private static int getValue(final int[] opCode, final int pos, final int paramMode) {
        if (paramMode == IMMEDIATE_MODE) {
            return opCode[pos];
        }
        if (paramMode != POSITION_MODE)
            throw new IllegalStateException("This case should not happen paramMode=" + paramMode);
        // paramMode == POSITION_MODE
        return getValue(opCode, opCode[pos], IMMEDIATE_MODE);
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

    private static String arrayToString(final Integer[] array) {
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
