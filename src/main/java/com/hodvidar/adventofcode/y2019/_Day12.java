package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

public final class _Day12 {
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
        System.err.println("Expected '10055' - result='" + result + "'");
    }

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
        line = sc.nextLine();
        final Integer nbSteps = Integer.parseInt(line);
        system.doSteps(nbSteps);
        final double energy = system.getTotalEnergy();

        // WRITE SOLUTION
        line = sc.nextLine();
        sc.close();
        final String energyStr = DoubleFormater.asInteger(energy);
        System.err.println("Expected energy value:'" + line + "'");
        System.err.println("Found:'" + energyStr + "'");
        return energyStr;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }
}
