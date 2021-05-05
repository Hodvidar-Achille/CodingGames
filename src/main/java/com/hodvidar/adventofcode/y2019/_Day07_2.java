package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * 546 too low
 * 1145324612 too high
 * <p>
 * '8754464' PASSED
 *
 * @author Hodvidar
 */
public final class _Day07_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 7;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        int result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '8754464' - result='" + result + "'");
    }

    private static int test(String inputFile) throws Exception {
        String line;
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        String[] opCodeStr = line.split(",");
        sc.close();
        double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            String s = opCodeStr[i];
            int j = Integer.parseInt(s);
            opCode[i] = j;
        }

        int maxOutput = 0;
        int phaseCounter = 0;
        Collection<String> phases = new CodeGenerator(5, 9)
                .generateUniqueDigitCode(5);
        for (String phase : phases) {
            int output = tryPhase(phase, opCode);
            if (output > maxOutput)
                maxOutput = output;
            phaseCounter++;
            if (phaseCounter % 10 == 0)
                printIfVerbose("" + phaseCounter);
        }

        return maxOutput;
    }

    private static int tryPhase(String phase, double[] opCode) {
        double p = getPhaseValue(phase, 0);
        Amplifier A = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 1);
        Amplifier B = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 2);
        Amplifier C = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 3);
        Amplifier D = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 4);
        Amplifier E = new Amplifier(opCode.clone(), p, null);
        List<Amplifier> ampls = new ArrayList<>();
        ampls.add(A);
        ampls.add(B);
        ampls.add(C);
        ampls.add(D);
        ampls.add(E);

        double output = 0;
        int counter = 0;

        while (true) {
            Amplifier amp = ampls.get(counter);
            amp.setInput(output);
            amp.runProgram();
            if (amp.isShutDown())
                break;
            output = amp.getOutput();
            counter++;
            if (counter >= ampls.size())
                counter = 0;
        }

        return (int) output;
    }

    private static int getPhaseValue(String phase, int index) {
        String s = "" + phase.charAt(index);
        return Integer.parseInt(s);
    }

}
