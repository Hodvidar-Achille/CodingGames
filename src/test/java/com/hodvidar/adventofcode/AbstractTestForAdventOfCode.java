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

    private static final String INPUT_DIRECTORY = "adventofcode_";
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
    protected void checkGetResult(final double expectedResult) throws FileNotFoundException {
        final Scanner sc = getScannerForRealInputFile();
        final double result = testedClass.getResultDouble(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("getExpectedTestResults")
    protected void checkGetResultForTest(final int numberOfTheTest, final double expectedResult) throws FileNotFoundException {
        if (numberOfTheTest == 0) {
            return;
        }
        final Scanner sc = getScanner(numberOfTheTest);
        final double result = testedClass.getResultDouble(sc);
        assertThat(result).isEqualTo(expectedResult);
    }


    protected int getDay() {
        return testedClass.getDay();
    }

    /**
     * Expected result for the tested class with the original input file.
     */
    protected int getExpectedResult() {
        return Integer.MIN_VALUE;
    }

    protected double getExpectedResultDouble() {
        return getExpectedResult();
    }

    protected abstract int getNumberOfTheTest();

    /**
     * Expected result for the tested class with the given test input file.
     */
    protected int getExpectedTestResult() {
        return Integer.MIN_VALUE;
    }

    protected double getExpectedTestResultDouble() {
        return getExpectedTestResult();
    }

    private Stream<Arguments> getExpectedResults() {
        return Stream.of(Arguments.of(getExpectedResultDouble()));
    }


    private Stream<Arguments> getExpectedTestResults() {
        return Stream.of(Arguments.of(getNumberOfTheTest(), getExpectedTestResultDouble()));
    }

}
