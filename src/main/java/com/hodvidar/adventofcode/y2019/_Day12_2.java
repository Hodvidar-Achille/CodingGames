package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * @author Hodvidar
 */
public final class _Day12_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 12;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test1.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test2.txt");
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println(
                "Expected '374307970285176' - result='" + result + "'");
    }

    @SuppressWarnings("unused")
    private static String subTest(final String inputFile) throws Exception {
        String line;
        final File file = new File(inputFile);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // Create the Moons
        line = sc.nextLine();
        List<Double> values = NumberExtractor.extractNumber(line);
        final Moon moonA = new Moon(values.get(0), values.get(1), values.get(2));
        line = sc.nextLine();
        values = NumberExtractor.extractNumber(line);
        final Moon moonB = new Moon(values.get(0), values.get(1), values.get(2));
        line = sc.nextLine();
        values = NumberExtractor.extractNumber(line);
        final Moon moonC = new Moon(values.get(0), values.get(1), values.get(2));
        line = sc.nextLine();
        values = NumberExtractor.extractNumber(line);
        final Moon moonD = new Moon(values.get(0), values.get(1), values.get(2));

        // Create the system and make it simulate the moves.
        final SpaceOrbitalSystem system = new SpaceOrbitalSystem(moonA, moonB, moonC,
                moonD);
        final String nbStep = sc.nextLine();
        final String expectedEnergy = sc.nextLine();

        // WRITE SOLUTION
        final String expectingNbStepToInitState = sc.nextLine();
        sc.close();
        final long before = System.currentTimeMillis();
        // Double value = system.getNumberOfStepForLoop();
        final Double value = system.getNumberOfStepForLoop_optimized();
        final long after = System.currentTimeMillis();
        final long diff = after - before;
        final String valueStr = DoubleFormater.asInteger(value);
        final String diffStr = DoubleFormater.asInteger(diff);

        System.err.println(
                "Expected nbSteps:'" + expectingNbStepToInitState + "'");
        System.err.println("Found:'" + valueStr + "' (in " + diffStr + "ms).");
        return "" + valueStr;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }
}
