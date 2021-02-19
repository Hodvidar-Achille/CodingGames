package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class _Day08_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 8;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final int WIDE = 25;
    private static final int HIGH = 6;
    private static final int TOTAL = WIDE * HIGH;

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        String result = test("resources" + File.separator + INPUT_DIRECTORY +  File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("result=\n" + result + "");
    }

    private static String test(String inputFile) throws Exception {
        String line;
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        sc.close();
        Map<Integer, Layer> layers = new HashMap<>();
        int i = 0;
        for (char c : line.toCharArray()) {
            int digit = Integer.parseInt("" + c);
            Integer layerNumber = (i / TOTAL) + 1;
            Layer layer = layers.get(layerNumber);
            if (layer == null) {
                layer = new Layer(WIDE, HIGH);
                layers.put(layerNumber, layer);
            }
            layer.addPixel(digit, i);
            i++;
        }

        Screen screen = new Screen(layers, WIDE, HIGH);
        printIfVerbose(screen.print());
        return screen.print2();
    }
}
