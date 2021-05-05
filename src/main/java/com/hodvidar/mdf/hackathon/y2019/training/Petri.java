package com.hodvidar.mdf.hackathon.y2019.training;

// DO not copy upper than this

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * https://www.isograd.com/FR/solutionconcours.php?contest_id=36 Done in 47min35sec... By Hodvidar
 */
public final class Petri {

    // Name of class to put back : IsoContest

    private static final boolean TESTING = false; // ### To change for submit ###

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final boolean ONE_TEST = false;
    private static final int ONE_TEST_NUMBER = 1;
    private static final int NUMBER_OF_TESTS = 6; // TO CHANGE
    private static final String INPUT_DIRECTORY = "petri_input"; // TO CHANGE

    public static void main(final String[] args) throws Exception {
        final Petri r = new Petri();
        if (!TESTING) {
            r.test(null);
            return;
        }
        int i;
        final int max;
        if (ONE_TEST) {
            i = ONE_TEST_NUMBER;
            max = ONE_TEST_NUMBER;
        } else {
            i = 1;
            max = NUMBER_OF_TESTS;
        }
        for (; i <= max; i++) {
            System.err.println("\n--- TEST n°" + i + " --");
            final String result = r
                    .test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + i + ".txt");
            // --- CHECKING ---
            final File file2 = new File(RESOURCES + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
            // Scanner sc = new Scanner(System.in);
            final Scanner sc2 = new Scanner(file2);
            final String line2 = sc2.nextLine();
            System.err.println("Solution is: \n" + line2);
            if (result.equals(line2))
                System.err.println("SUCCESS!");
            else
                System.err.println("FAILURE! found: " + result);
            sc2.close();
        }
    }

    private static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    private static boolean ignore(final String s) {
        if (s.equals("#"))
            return true;
        if (s.equals("."))
            return true;
        return s.startsWith("o");
    }

    private static void printTab(final String[][] tab, final int size) {
        if (!VERBOSE)
            return;
        for (int i = 0; i < size; i++) {
            printIfVerbose("" + Arrays.deepToString(tab[i]));
        }
    }

    private String test(final String inputFile) throws Exception {
        String line = "";
        final Scanner sc;
        if (TESTING) {
            final File file = new File(inputFile);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        // --- INPUT ---
        printIfVerbose("DEBUGGING");
        int i = 0;
        int size = 0;
        String[][] tab = null;
        while (sc.hasNextLine()) {
            i++;
            line = sc.nextLine();
            printIfVerbose("i=" + i + " line:" + line);
            if (i == 1) {
                // do stuff
                size = Integer.parseInt(line);
                tab = new String[size][size];
                continue;
            }

            // do other stuff
            int j = 0;
            for (final char c : line.toCharArray()) {
                j++;
                tab[i - 2][j - 1] = "" + c;
            }
        }

        printIfVerbose("Searching...");

        while (true) {
            // break if no "." anymore
            boolean stillPoint = false;
            for (i = 0; i < size; i++) {
                if (stillPoint)
                    break;
                for (int j = 0; j < size; j++) {
                    if (stillPoint)
                        break;
                    if (tab[i][j].equals("."))
                        stillPoint = true;
                }
            }
            if (!stillPoint)
                break;

            // 1) First propagation
            for (i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    final String s = tab[i][j];
                    if (ignore(s))
                        continue;

                    // propagate N
                    if (i > 0) {
                        if (tab[i - 1][j].equals("."))
                            tab[i - 1][j] = "o" + s;
                        else if (tab[i - 1][j].startsWith("o"))
                            if (!tab[i - 1][j].substring(1, 2).equals(s))
                                tab[i - 1][j] = "=";
                    }
                    // propagate W
                    if (j > 0) {
                        if (tab[i][j - 1].equals("."))
                            tab[i][j - 1] = "o" + s;
                        else if (tab[i][j - 1].startsWith("o"))
                            if (!tab[i][j - 1].substring(1, 2).equals(s))
                                tab[i][j - 1] = "=";
                    }
                    // propagate S
                    if (i < size - 1) {
                        if (tab[i + 1][j].equals("."))
                            tab[i + 1][j] = "o" + s;
                        else if (tab[i + 1][j].startsWith("o"))
                            if (!tab[i + 1][j].substring(1, 2).equals(s))
                                tab[i + 1][j] = "=";
                    }
                    // propagate N
                    if (j < size - 1) {
                        if (tab[i][j + 1].equals("."))
                            tab[i][j + 1] = "o" + s;
                        else if (tab[i][j + 1].startsWith("o"))
                            if (!tab[i][j + 1].substring(1, 2).equals(s))
                                tab[i][j + 1] = "=";
                    }
                }
            }

            printIfVerbose("--------------");
            printTab(tab, size);

            // 2) Confirm propagation or "="
            for (i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    final String s = tab[i][j];
                    if (s.startsWith("o"))
                        tab[i][j] = tab[i][j].substring(1, 2); // remove the "o";
                }
            }

            printIfVerbose("--------------");
            printTab(tab, size);
        }

        printIfVerbose("Tab:");
        printTab(tab, size);

        // count biggest colonie
        final Map<Integer, Integer> colonies = new HashMap<>();
        for (i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final String s = tab[i][j];
                try {
                    final Integer n = Integer.parseInt(s);
                    Integer val = colonies.get(n);
                    if (val == null)
                        val = 0;
                    val++;
                    colonies.put(n, val);
                } catch (final Throwable e) {
                    // nothing
                }
            }
        }
        int max = 0;
        for (final Integer n : colonies.values()) {
            if (n > max)
                max = n;
        }

        final String result = "" + max;
        System.out.println(result);
        sc.close();
        return result;
    }

}