package com.hodvidar.adventofcode;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Stream;

import static com.hodvidar.utils.file.Constance.RESOURCES_TEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTestForAdventOfCode {

    /**
     * If 'false' only response and Failure are written
     **/
    private static final String INPUT_DIRECTORY = "adventofcode_"; // input1
    protected final AbstractAdventOfCode testedClass;
    protected final String inputDirectory;


    protected AbstractTestForAdventOfCode(final AbstractAdventOfCode testedClass, final int year) {
        this.testedClass = testedClass;
        this.inputDirectory = INPUT_DIRECTORY + year;
    }

    private String getInputFilePath(final int testNumber) {
        return RESOURCES_TEST + File.separator + inputDirectory + File.separator + "input" + getDay() + "-test" + testNumber + ".txt";
    }

    protected Scanner getScanner(final int testNumber) throws FileNotFoundException {
        final String inputFile = getInputFilePath(testNumber);
        final File file = new File(inputFile);
        return new Scanner(file);
    }

    protected Scanner getScannerForRealInputFile() throws FileNotFoundException {
        final String inputFile = RESOURCES_TEST + File.separator + inputDirectory + File.separator + "input" + getDay() + ".txt";
        final File file = new File(inputFile);
        return new Scanner(file);
    }

    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("getExpectedResults")
    protected void checkGetResult(final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScannerForRealInputFile();
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    protected int getDay() {
        return testedClass.getDay();
    }

    /**
     * Expected result for the tested class with the original input file.
     */
    protected abstract int getExpectedResult();

    private Stream<Arguments> getExpectedResults() {
        return Stream.of(Arguments.of(getExpectedResult()));
    }

}
