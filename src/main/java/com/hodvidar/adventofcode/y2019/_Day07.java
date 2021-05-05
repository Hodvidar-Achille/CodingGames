package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Collection;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * '99376'
 *
 * @author Hodvidar
 */
public final class _Day07 {
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
        System.err.println("Expected '99376' - result='" + result + "'");
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

        double maxOutput = 0;

        // Replace by 'CodeGenerator'
        //		for (int i1 = 0; i1 <= 4; i1++)
        //		{
        //			for (int i2 = 0; i2 <= 4; i2++)
        //			{
        //				if(i2 == i1)
        //					continue;
        //				for (int i3 = 0; i3 <= 4; i3++)
        //				{
        //					if(i3 == i2 || i3 == i1)
        //						continue;
        //					for (int i4 = 0; i4 <= 4; i4++)
        //					{
        //						if(i4 == i3 || i4 == i2 || i4 == i1)
        //							continue;
        //						for (int i5 = 0; i5 <= 4; i5++)
        //						{
        //							if(i5 == i4 || i5 == i3 || i5 == i2 || i5 == i1)
        //								continue;
        //							String phase = i1 + "" + i2 + "" + i3 + "" + i4 + "" + i5;
        //							int output = tryPhase(phase, opCode);
        //							if(output > maxOutput)
        //								maxOutput = output;
        //						}
        //					}
        //				}
        //			}
        //		}

        Collection<String> codes = new CodeGenerator(0, 4)
                .generateUniqueDigitCode(5);
        for (String phase : codes) {
            double output = tryPhase(phase, opCode);
            if (output > maxOutput)
                maxOutput = output;
        }
        return (int) maxOutput;
    }

    @SuppressWarnings("deprecation")
    private static double tryPhase(String phase, double[] opCode) {
        printIfVerbose(arrayToString(opCode));
        double p = getPhaseValue(phase, 0);
        Amplifier A = new Amplifier(opCode.clone(), p, null);
        double output = A.runAndGetOutput(0);

        printIfVerbose(arrayToString(opCode));
        p = getPhaseValue(phase, 1);
        Amplifier B = new Amplifier(opCode.clone(), p, null);
        output = B.runAndGetOutput(output);

        printIfVerbose(arrayToString(opCode));
        p = getPhaseValue(phase, 2);
        Amplifier C = new Amplifier(opCode.clone(), p, null);
        output = C.runAndGetOutput(output);

        printIfVerbose(arrayToString(opCode));
        p = getPhaseValue(phase, 3);
        Amplifier D = new Amplifier(opCode.clone(), p, null);
        output = D.runAndGetOutput(output);

        printIfVerbose(arrayToString(opCode));
        p = getPhaseValue(phase, 4);
        Amplifier E = new Amplifier(opCode.clone(), p, null);
        output = E.runAndGetOutput(output);

        return output;
    }

    private static int getPhaseValue(String phase, int index) {
        String s = "" + phase.charAt(index);
        return Integer.parseInt(s);
    }

    private static String arrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        int f = array.length - 1;
        for (int i = 0; i <= f; i++) {
            if (i != f)
                sb.append(array[i]).append(",");
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }

}
