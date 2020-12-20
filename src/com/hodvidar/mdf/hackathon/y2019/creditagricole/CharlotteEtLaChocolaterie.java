package com.hodvidar.mdf.hackathon.y2019.creditagricole;

import java.io.File;
import java.util.Scanner;

/**
 * https://www.isograd.com/FR/solutionconcours.php
 * Done in 11min20sec...
 * By Hodvidar
 */
public class CharlotteEtLaChocolaterie {
    // Name of class to put back : IsoContest

    private static final boolean TESTING = false; // ### To change for submit ###

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final boolean ONE_TEST = true;
    private static final int ONE_TEST_NUMBER = 6;
    private static final int NUMBER_OF_TESTS = 6; // TO CHANGE
    private static final String INPUT_DIRECTORY = "charlotte_chocolatterie_input"; // TO CHANGE

    public static void main(String[] args) throws Exception {
        CharlotteEtLaChocolaterie r = new CharlotteEtLaChocolaterie();
        if (!TESTING) {
            r.test(null);
            return;
        }
        int i;
        int max;
        if (ONE_TEST) {
            i = ONE_TEST_NUMBER;
            max = ONE_TEST_NUMBER;
        } else {
            i = 1;
            max = NUMBER_OF_TESTS;
        }
        for (; i <= max; i++) {
            System.err.println("\n--- TEST n°" + i + " --");
            String result = r.test("resources\\" + INPUT_DIRECTORY + "\\input" + i + ".txt");
            // --- CHECKING ---
            File file2 = new File("resources\\" + INPUT_DIRECTORY + "\\output" + i + ".txt");
            // Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(file2);
            String line2 = sc2.nextLine();
            System.err.println("Solution is: \n" + line2);
            if (result.equals(line2))
                System.err.println("SUCCESS!");
            else
                System.err.println("FAILURE! found: " + result);
            sc2.close();
        }
    }

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    private String test(String inputFile) throws Exception {
        String line = "";
        Scanner sc;
        if (TESTING) {
            File file = new File(inputFile);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        // --- INPUT ---
        printIfVerbose("DEBUGGING");
        int i = 0;
        int count = 0;
        while (sc.hasNextLine()) {
            i++;
            line = sc.nextLine();
            printIfVerbose("i=" + i + " line:" + line);
            if (i == 1) {
                // do stuff
                continue;
            }

            if (isSumOfDigitFourtyTwo(line))
                count++;
            // do other stuff

        }

        printIfVerbose("Searching...");

        String result = "" + count;
        System.out.println(result);
        sc.close();
        return result;
    }

    /**
     * Returns sum until number is between 10 and 99
     **/
    private boolean isSumOfDigitFourtyTwo(String aNumber) {
        if (aNumber.length() == 2 && "42".equals(aNumber))
            return true;

        int sum = 0;
        for (char c : aNumber.toCharArray()) {
            sum += Integer.parseInt("" + c);
        }
        String result = "" + sum;
        if (result.length() == 2 && "42".equals(result))
            return true;
        if (result.length() < 2)
            return false;

        return isSumOfDigitFourtyTwo(result);

    }

}