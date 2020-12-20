package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * '50', '203' too low
 * <p>
 * '3427035286' not good
 * <p>
 * '3460311188'- PASSED
 *
 * @author Hodvidar
 */
public final class _Day09 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 9;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '3460311188' - result='" + result + "'");
    }

    private static String test(String inputFile) throws Exception {
        String line;
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // TEST :
        // line = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"; // OK
        // line = "1102,34915192,34915192,7,4,7,99,0"; // OK
        // line = "104,1125899906842624,99"; // OK
        line = sc.nextLine(); // KO : 203 and 0
        String[] opCodeStr = line.split(",");
        sc.close();
        double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            String s = opCodeStr[i];
            double j = Double.parseDouble(s);
            opCode[i] = j;
        }

        Amplifier amp = new Amplifier(opCode);
        amp.setInput(1);
        double output = 0;
        while (!amp.isShutDown()) {
            amp.runProgram();
            output = amp.getOutput();
            printIfVerbose("Day9 while run : " + output);
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        String rounded = nf.format(output);

        // Stuff
        return rounded;
    }
}
