package com.hodvidar.adventofcode.y2025;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.ParameterizedTest.ARGUMENTS_WITH_NAMES_PLACEHOLDER;

public class Day08Test extends AbstractTestForAdventOfCode2025 {

    protected Day08Test() {
        super(new Day08());
    }

    private Day08.ClosestPairsBlocks stringsToPoints(String... csvPoints) {
        final List<Day08.Point3D> list = new ArrayList<>();
        int id = 0;
        for (String line : csvPoints) {
            String[] parts = line.trim().split(",");
            final double x = Double.parseDouble(parts[0]);
            final double y = Double.parseDouble(parts[1]);
            final double z = Double.parseDouble(parts[2]);
            list.add(new Day08.Point3D(x, y, z, id++));
        }
        return new Day08.ClosestPairsBlocks(list);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            // points            | pairs | topBlocks | expectedProduct
            // closest pair merges two points → block sizes 2,1,1 → product of biggest 1 block = 2
            "0,0,0; 10,0,0; 20,0,0; 30,0,0 | 1 | 2 | 2", "0,0,0; 10,0,0; 25,0,0; 30,0,0 | 2 | 2 | 4", "0,0,0; 1,0,0; 2,10,0; 3,10,0 | 2 | 2 | 4",
            // after two merges we have blocks sizes 2,2,1 → product of three biggest = 2*2*1 = 4 (adjust expected accordingly)
            "0,0,0; 5,0,0; 10,0,0; 35,0,0; 40,0,0 | 3 | 2 | 6",
            // three merges create a single block of size 4 → product of biggest 1 = 4
            "0,0,0; 100,0,0; 200,0,0; 300,0,0 | 3 | 1 | 4", "162,817,812; 57,618,57; 425,690,689; 431,825,988; 906,360,560; 805,96,715 | 3 | 2 | 6",
            "162,817,812; 57,618,57; 425,690,689; 431,825,988; 906,360,560; 805,96,715 | 3 | 1 | 3",
            "162,817,812; 57,618,57; 425,690,689; 431,825,988; 906,360,560; 805,96,715 | 4 | 1 | 3",
            "162,817,812; 57,618,57; 906,360,560; 592,479,940; 352,342,300; 466,668,158; 542,29,236; 431,825,988; "
            + "739,650,466; 52,470,668; 16,146,977; 819,987,18; 117,168,530; 805,96,715; 346,949,466; 970,615,88; "
            + "941,993,340; 862,61,35; 984,92,344; 425,690,689 | 10 | 3 | 40" })
    void product_of_biggest_blocks(final String pointsCsv, final int pairs, final int topBlocks, final long expected) {
        final String[] pointDefs = pointsCsv.split(";");
        final Day08.ClosestPairsBlocks calc = stringsToPoints(pointDefs);
        final long result = calc.productOfBlockSizes(pairs, topBlocks);
        assertThat(result).isEqualTo(expected);
    }

    @Override
    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("getExpectedResults")
    protected void checkGetResult(final double expectedResult) throws FileNotFoundException {
        final Scanner sc = getScannerForRealInputFile();
        ((Day08) testedClass).setNumberOfPairsToBuild(1000);
        final double result = testedClass.getResultDouble(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    @ParameterizedTest(name = ARGUMENTS_WITH_NAMES_PLACEHOLDER)
    @MethodSource("getExpectedTestResults")
    protected void checkGetResultForTest(final int numberOfTheTest, final double expectedResult) throws FileNotFoundException {
        if (numberOfTheTest == 0) {
            return;
        }
        final Scanner sc = getScanner(numberOfTheTest);
        ((Day08) testedClass).setNumberOfPairsToBuild(10);
        final double result = testedClass.getResultDouble(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 153328d;
    }

    @Override
    protected int getExpectedTestResult() {
        return 40;
    }
}