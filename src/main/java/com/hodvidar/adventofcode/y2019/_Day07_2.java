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

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final int result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '8754464' - result='" + result + "'");
    }

    private static int test(final String inputFile) throws Exception {
        final String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        final String[] opCodeStr = line.split(",");
        sc.close();
        final double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            final String s = opCodeStr[i];
            final int j = Integer.parseInt(s);
            opCode[i] = j;
        }

        int maxOutput = 0;
        int phaseCounter = 0;
        final Collection<String> phases = new CodeGenerator(5, 9)
                .generateUniqueDigitCode(5);
        for (final String phase : phases) {
            final int output = tryPhase(phase, opCode);
            if (output > maxOutput)
                maxOutput = output;
            phaseCounter++;
            if (phaseCounter % 10 == 0)
                printIfVerbose("" + phaseCounter);
        }

        return maxOutput;
    }

    private static int tryPhase(final String phase, final double[] opCode) {
        double p = getPhaseValue(phase, 0);
        final Amplifier A = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 1);
        final Amplifier B = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 2);
        final Amplifier C = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 3);
        final Amplifier D = new Amplifier(opCode.clone(), p, null);
        p = getPhaseValue(phase, 4);
        final Amplifier E = new Amplifier(opCode.clone(), p, null);
        final List<Amplifier> ampls = new ArrayList<>();
        ampls.add(A);
        ampls.add(B);
        ampls.add(C);
        ampls.add(D);
        ampls.add(E);

        double output = 0;
        int counter = 0;

        while (true) {
            final Amplifier amp = ampls.get(counter);
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

    private static int getPhaseValue(final String phase, final int index) {
        final String s = "" + phase.charAt(index);
        return Integer.parseInt(s);
    }

}
