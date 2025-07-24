package com.hodvidar.adventofcode.y2024;

public class Day07p2 extends Day07 {

    @Override
    protected boolean otherEvaluation(final double[] numbers, final int index, final long currentResult, final double expectedResult) {
        final double concatenatedValue = Double.parseDouble("" + currentResult + (long) numbers[index + 1]);
        return evaluate(numbers, index + 1, concatenatedValue, expectedResult);
    }
}
