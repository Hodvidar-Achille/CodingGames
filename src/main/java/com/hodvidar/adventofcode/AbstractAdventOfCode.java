package com.hodvidar.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

public abstract class AbstractAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final String INPUT_DIRECTORY = "adventofcode_"; // input1

    private final String inputDirectory;
    protected AbstractAdventOfCode(final int year) {
        this.inputDirectory = INPUT_DIRECTORY + year;
    }

    protected static void printIfVerbose(final String s) {
        if (VERBOSE) {
            System.err.println(s);
        }
    }

    public String getInputFilePath() {
        return RESOURCES + File.separator + inputDirectory + File.separator + "input" + getDay() + ".txt";
    }

    public Scanner getScanner() throws FileNotFoundException {
        return new Scanner(new File(getInputFilePath()));
    }

    public abstract int getResult(final Scanner sc) throws FileNotFoundException;

    public abstract int getDay();

}
