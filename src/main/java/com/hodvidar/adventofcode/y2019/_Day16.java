package com.hodvidar.adventofcode.y2019;

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
public final class _Day16 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 16;
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
        subTest(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input" + NUMBER_OF_TEST
                + "-test3.txt");
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '34841690' - result='" + result + "'");
    }

    private static String subTest(final String inputFile) throws Exception {
        System.err.println("Computing on file:" + inputFile);
        String line = "";
        final File file = new File(inputFile);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // Create the elements
        String expectedResult = "";
        line = sc.nextLine();
        final List<Integer> numbers = new ArrayList<>();
        for (final char c : line.toCharArray()) {
            numbers.add(Integer.parseInt("" + c));
        }
        expectedResult = sc.nextLine();
        sc.close();

        final List<Integer> basePattern = new ArrayList<>();
        basePattern.add(0);
        basePattern.add(1);
        basePattern.add(0);
        basePattern.add(-1);
        String result = FFT_start(numbers, basePattern, 100);
        result = result.substring(0, 8);

        System.err.println("Expected value:'" + expectedResult + "'");
        System.err.println("Found:'" + result + "'");
        return result;
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

    private static List<Integer> FFT_sub(final List<Integer> numbers,
                                         final List<Integer> pattern, int phase) {
        final List<Integer> newNumbers = FFT_work(numbers, pattern);

        if (VERBOSE)
            System.out.println(
                    "Phase:" + phase + " liste:\n" + toString(newNumbers));

        phase--;
        if (phase == 0)
            return newNumbers;
        else
            return FFT_sub(newNumbers, pattern, phase);

    }

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

    /**
     * For the Pattern index
     *
     * @param value
     * @param maxAuthorized
     * @return
     */
    private static int truncateValue(int value, final int maxAuthorized) {
        if (value < maxAuthorized)
            return value;
        value -= maxAuthorized;
        return truncateValue(value, maxAuthorized);
    }

    private static int getLastDigit(final double i) {
        return (int) (Math.abs(i) % 10);
    }
}
