package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

public final class _Day08 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 8;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final int WIDE = 25;
    private static final int HIGH = 6;
    private static final int TOTAL = WIDE * HIGH;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final int result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
    }

    private static int test(final String inputFile) throws Exception {
        final String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        sc.close();
        final Map<Integer, Layer> layers = new HashMap<>();
        int i = 0;
        for (final char c : line.toCharArray()) {
            final int digit = Integer.parseInt("" + c);
            final Integer layerNumber = (i / TOTAL) + 1;
            Layer layer = layers.get(layerNumber);
            if (layer == null) {
                layer = new Layer(WIDE, HIGH);
                layers.put(layerNumber, layer);
            }
            layer.addPixel(digit, i);
            i++;
        }

        int minZero = Integer.MAX_VALUE;
        Integer numberOfLayer = 0;
        for (int n = 1; n <= layers.size(); n++) {
            final Layer l = layers.get(n);
            final int nbZero = l.getNumberOfZero();
            if (nbZero < minZero) {
                minZero = nbZero;
                numberOfLayer = n;
            }
        }

        final Layer finalLayer = layers.get(numberOfLayer);

        return finalLayer.getNumberOfOne() * finalLayer.getNumberOfTwo();
    }

}
