package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractTestForAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;
    private static final String INPUT_DIRECTORY = "adventofcode_2020"; // input1
    protected final AbstractAdventOfCode testedClass;

    protected AbstractTestForAdventOfCode(AbstractAdventOfCode testedClass) {
        this.testedClass = testedClass;
    }

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
        Scanner sc = new Scanner(file);
        return sc;
    }

    protected Scanner getScannerForRealInputFile() throws FileNotFoundException {
        String inputFile = "resources\\" + INPUT_DIRECTORY + "\\input" + getDay() + ".txt";
        File file = new File(inputFile);
        Scanner sc = new Scanner(file);
        return sc;
    }

    @Test
    protected void checkGetResult() throws FileNotFoundException {
        Scanner sc = getScannerForRealInputFile();
        int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(getExpectedResult());
    }

    protected int getDay() {
        return testedClass.getDay();
    }

    /**
     * Expected result for the tested class with the original input file.
     */
    protected abstract int getExpectedResult();

}
