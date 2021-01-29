package com.hodvidar.mdf.hackathon.y2019.h1800;

// ---- Do not Copy upper part ----

import java.io.File;
import java.util.Scanner;

/**
 * @author Hodvidar
 */
public final class Problem3 // <-- IsoConstest
{
    private static final boolean TESTING = true; // <-- false
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true; // <-- False
    private static final boolean ONE_TEST = false;
    private static final int ONE_TEST_NUMBER = 1;
    private static final int NUMBER_OF_TESTS = 6; // TO CHANGE
    private static final String INPUT_DIRECTORY = "xxx_input"; // TO CHANGE

    // Name of class to put back : IsoContest
    public static void main(String[] args) throws Exception {
        Problem3 r = new Problem3(); // <-- IsoContest
        r.doTests();
    }

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public void doTests() throws Exception {
        if (!TESTING) {
            test(null);
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
        System.err.println(" --- TEST class" + this.getClass().getSimpleName() + " ---");
        for (; i <= max; i++) {
            System.err.println("\n--- TEST n°" + i + " --");
            String result = test("resources" + File.separator + INPUT_DIRECTORY +  File.separator + "input" + i + ".txt");
            // --- CHECKING ---
            File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
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

    private String test(String inputFile) throws Exception {
        String line = "";
        Scanner sc;
        if (TESTING) {
            File file = new File(inputFile);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        // ----------------------------------------------------
        // --------------------- START ------------------------
        // ----------------------------------------------------
        printIfVerbose("Reading inputs...");
        int i = 0;
        while (sc.hasNextLine()) {
            i++;
            line = sc.nextLine();
            printIfVerbose("i=" + i + " line:" + line);
            if (i == 1) {
                // do stuff
                continue;
            }

            // do other stuff

        }
        printIfVerbose("Searching....");
        // Compute result

        String result = "";
        System.out.println(result);
        sc.close();
        return result;
        // ----------------------------------------------------
        // ------------------   END ---------------------------
        // ----------------------------------------------------
    }
}