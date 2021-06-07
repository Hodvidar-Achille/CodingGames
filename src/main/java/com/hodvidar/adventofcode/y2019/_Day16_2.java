package com.hodvidar.adventofcode.y2019;

import com.hodvidar.utils.number.MillisecondFormatter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * 80757514 X
 * 34841690 good
 *
 * @author Hodvidar
 */
public final class _Day16_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 16;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final int dixMille = 10000;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test4.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test5.txt");
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test6.txt");
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
        final List<Integer> numbers = new ArrayList<>();
        for (final char c : line.toCharArray()) {
            numbers.add(Integer.parseInt("" + c));
        }
        expectedResult = sc.nextLine(); // Solution Day16_2
        sc.close();

        // The first seven digits of your initial input signal
        // also represent the message offset.
        final Integer offSet = Integer.parseInt(toString(numbers).substring(0, 7));
        // x1000
        final List<Integer> numbers2 = new ArrayList<>(numbers.size() * 1000);
        int counter = 1;
        while (counter <= dixMille) {
            numbers2.addAll(numbers);
            counter++;
        }
        final List<Integer> basePattern = new ArrayList<>();
        basePattern.add(0);
        basePattern.add(1);
        basePattern.add(0);
        basePattern.add(-1);
        printIfVerbose("Work start...");
        String result = FFT_start(numbers2, basePattern, 100);
        printIfVerbose("Work ended.");
        result = result.substring(offSet, offSet + 8);
        final long after = System.currentTimeMillis();
        final long diff = after - before;
        final String diffStr = MillisecondFormatter.asTime(diff);
        System.err.println("Expected value:'" + expectedResult + "'");
        System.err.println("Found:'" + result + "' took:" + diffStr);
        return "" + result;
    }

    private static String test(final String inputFile) throws Exception {
        return subTest(inputFile);
    }

    private static String FFT_start(final List<Integer> numbers,
                                    final List<Integer> pattern, final int phase) {
        final List<Integer> integers = FFT_sub(numbers, pattern, phase);
        return toString(integers);
    }

    private static String toString(final List<Integer> numbers) {
        String s = "";
        for (final Integer i : numbers) {
            s += i;
        }
        return s;
    }

    /**
     * FFT operates in repeated phases. In each phase,
     * a new list is constructed with the same length
     * as the input list. This new list is also used as
     * the input for the next phase.
     *
     * @param numbers
     * @param pattern
     * @param phase
     * @return
     */
    private static List<Integer> FFT_sub(final List<Integer> numbers,
                                         final List<Integer> pattern, int phase) {
        final List<Integer> newNumbers = FFT_work(numbers, pattern);

        if (VERBOSE && (phase % 20) == 0) {
            System.out.println("Phase:" + phase + " liste:\n"
                    + toString(newNumbers.subList(0, 7)));
        }
        phase--;
        if (phase == 0)
            return newNumbers;
        else
            return FFT_sub(newNumbers, pattern, phase);

    }

    /**
     * Each element in the new list is built by multiplying
     * every value in the input list by a value in a repeating
     * pattern and then adding up the results.
     * <p>
     * Then, only the ones digit is kept: 38 becomes 8, -17 becomes 7, and so on.
     *
     * @param numbers
     * @param pattern
     * @return
     */
    private static List<Integer> FFT_work(final List<Integer> numbers,
                                          final List<Integer> pattern) {
        final List<Integer> newNumbers = new ArrayList<>(numbers.size());
        final int maxI = numbers.size();
        for (int i = 0; i < maxI; i++) {
            newNumbers.add(
                    getNewElement(numbers, computePattern(pattern, i + 1), i));
        }
        return newNumbers;
    }

    private static List<Integer> computePattern(final List<Integer> pattern,
                                                final int index) {
        final List<Integer> newPattern = new ArrayList<>(pattern.size() * index);
        for (int i = 0; i < pattern.size(); i++) {
            final Integer p = pattern.get(i);
            for (int j = 0; j < index; j++) {
                newPattern.add(p);
            }
        }
        return newPattern;
    }

    /**
     * Each element in the new list is built by multiplying
     * every value in the input list by a value in a repeating
     * pattern and then adding up the results.
     *
     * @param numbers
     * @param pattern
     * @return
     */
    private static int getNewElement(final List<Integer> numbers,
                                     final List<Integer> pattern, final int index) {
        double result = 0;
        int j = 0;
        final int maxI = numbers.size();
        final int maxJ = pattern.size();
        if (index > (maxI / 2)) // optimized
        {
            for (int i = index; i < maxI; i++) {
                result += numbers.get(i);
            }
        } else // normal
        {
            for (int i = index; i < maxI; i++) {
                j = truncateValue(i + 1, maxJ);
                result += numbers.get(i) * pattern.get(j);
            }
        }
        return getLastDigit(result);
    }

    private static int truncateValue(int value, final int maxAuthorized) {
        while (value >= maxAuthorized) {
            value -= maxAuthorized;
        }
        return value;
    }

    /**
     * "Then, only the ones digit is kept: 38 becomes 8, -17 becomes 7, and so on."
     *
     * @param i
     * @return
     */
    private static int getLastDigit(final double i) {
        return (int) (Math.abs(i) % 10);
    }
}
