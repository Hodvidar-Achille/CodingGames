package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;
import static com.hodvidar.utils.file.Constance.RESOURCES_TEST;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractTestForAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final String INPUT_DIRECTORY = "adventofcode_2020"; // input1
    protected final AbstractAdventOfCode testedClass;

    protected AbstractTestForAdventOfCode(final AbstractAdventOfCode testedClass) {
        this.testedClass = testedClass;
    }

    private String getInputFilePath(final int testNumber) {
        return RESOURCES_TEST + File.separator + INPUT_DIRECTORY + File.separator + "input" + getDay() + "-test" + testNumber + ".txt";
    }

    protected Scanner getScanner(final int testNumber) throws FileNotFoundException {
        final String inputFile = getInputFilePath(testNumber);
        final File file = new File(inputFile);
        return new Scanner(file);
    }

    protected Scanner getScannerForRealInputFile() throws FileNotFoundException {
        final String inputFile = RESOURCES_TEST + File.separator + INPUT_DIRECTORY + File.separator + "input" + getDay() + ".txt";
        final File file = new File(inputFile);
        return new Scanner(file);
    }

    @Test
    protected void checkGetResult() throws FileNotFoundException {
        final Scanner sc = getScannerForRealInputFile();
        final int result = testedClass.getResult(sc);
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
