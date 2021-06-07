package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.number.MillisecondFormatter;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * 2690795
 *
 * @author Hodvidar
 * 1000000000000
 */
public final class _Day14_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 14;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final double oneTrillion = 1000000000000d;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test3.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test4.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test5.txt");
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '2690795' - result='" + result + "'");
    }

    private static String subTest(final String inputFile) throws Exception {
        System.err.println("Computing on file:" + inputFile);
        String line = "";
        final File file = new File(inputFile);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // Create the elements
        String expectedResult = "";
        final ChemicalSystem system = new ChemicalSystem(true, oneTrillion);
        while (sc.hasNext()) {
            line = sc.nextLine();
            if (!line.contains("=>")) {
                break;
            }
            system.addReaction(line);

        }
        expectedResult = line; // Solution for Day14
        expectedResult = sc.nextLine(); // Solution for Day14_2
        sc.close();
        final long before = System.currentTimeMillis();
        system.initReaction(ChemicalSystem.FUEL);
        final double result = system.getQuantityAvailable(ChemicalSystem.FUEL);
        final String resultStr = DoubleFormater.asInteger(result);
        final long after = System.currentTimeMillis();
        final long diff = after - before;
        final String diffStr = MillisecondFormatter.asTime(diff);
        System.err.println("Expected value:'" + expectedResult + "'");
        System.err.println("Found:'" + resultStr + "'");
        System.err.println("Took:'" + diffStr + "'");
        return "" + resultStr;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }
}