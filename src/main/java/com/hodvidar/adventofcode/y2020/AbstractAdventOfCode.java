package com.hodvidar.adventofcode.y2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final String INPUT_DIRECTORY = "adventofcode_2020"; // input1

    protected static void printIfVerbose(final String s) {
        if (VERBOSE) {
            System.err.println(s);
        }
    }

    private String getInputFilePath() {
        return "resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + getDay() + ".txt";
    }

    protected Scanner getScanner() throws FileNotFoundException {
        return new Scanner(new File(getInputFilePath()));
    }

    protected abstract int getResult(Scanner sc) throws FileNotFoundException;

    protected abstract int getDay();

}
