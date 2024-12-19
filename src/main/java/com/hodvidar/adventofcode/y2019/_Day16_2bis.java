package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.MillisecondFormatter;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

// FALSE
public final class _Day16_2bis {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 16;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final int dixMille = 10000;
    static int[] PATTERN = {0, 1, 0, -1};

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        // 61706040 // 61706040
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test4.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test5.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test6.txt");
        //49623910 false
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '???' - result='" + result + "'");
    }

    private static String subTest(final String inputFile) throws Exception {
        System.err.println("Computing on file:" + inputFile);
        String line = "";
        final File file = new File(inputFile);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // Create the elements
        final long before = System.currentTimeMillis();
        String expectedResult = "";
        line = sc.nextLine();
        expectedResult = sc.nextLine(); // Solution Day16_2
        sc.close();

        // The first seven digits of your initial input signal
        // also represent the message offset.
        final Integer offSet = Integer.parseInt(line.substring(0, 7));
        // x1000
        line = generateLongInput(line);

        printIfVerbose("Work start...");
        String result = faseAlgorithmBad(line);
        printIfVerbose("Work ended.");
        result = result.substring(offSet, offSet + 8);
        final long after = System.currentTimeMillis();
        final long diff = after - before;
        final String diffStr = MillisecondFormatter.asTime(diff);
        System.err.println("Expected value:'" + expectedResult + "'");
        System.err.println("Found:'" + result + "' took:" + diffStr);
        return result;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }

    private static String generateLongInput(final String s) {
        String input = "";
        for (int i = 0; i < dixMille; i++) {
            input = input + s;
        }
        System.out.println("Input generated " + input.length());
        return input;
    }

    private static String faseAlgorithmBad(final String inp) {
        final StringBuffer b = new StringBuffer();
        final char[] chars = inp.toCharArray();

        int acc = 0;
        for (int i = 0; i < chars.length; i++) {
            final int n = chars[chars.length - i - 1] - 48;
            acc += n;
            b.append(acc % 10);
        }
        b.reverse();
        return b.toString();

    }

    public static String faseAlgorithmGood(final String x) {
        final char[] chars = x.toCharArray();
        final StringBuffer res = new StringBuffer();
        for (int charIdxBeingGenerated = 0; charIdxBeingGenerated < chars.length; charIdxBeingGenerated++) {

            long acc = 0;
            for (int charIdx = charIdxBeingGenerated /* skip initial zeroes */; charIdx < chars.length; charIdx++) {
                final int n = chars[charIdx] - 48;
                final int val = getMultiplier(charIdxBeingGenerated, charIdx);

                if ((val == 0)) /* skip internal zero sequences*/ {
                    final int skip = (charIdxBeingGenerated);
                    // System.out.println("Skipping "+skip);
                    charIdx += skip;
                    continue;
                }
                acc = acc + (long) n * val;
            }
            res.append(Math.abs(acc) % 10);
        }
        return res.toString();
    }

    private static int getMultiplier(final int charIdxBeingGenerated, final int charIdx) {
        final int repetition = charIdxBeingGenerated + 1;
        final int idx = (charIdx + 1) / repetition;
        final int val = PATTERN[idx % 4];
        return val;
    }
}
