package com.hodvidar.adventofcode.y2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractTestForAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final String INPUT_DIRECTORY = "adventofcode_2020"; // input1

    protected static void printIfVerbose(String s) {
        if (VERBOSE) {
            System.err.println(s);
        }
    }

    private String getInputFilePath(int testNumber) {
        return "resources\\" + INPUT_DIRECTORY + "\\input" + getDay() + "-test" + testNumber + ".txt";
    }

    protected Scanner getScanner(int testNumber) throws FileNotFoundException {
        String inputFile = getInputFilePath(testNumber);
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        return sc;
    }

    protected abstract int getDay();


}
