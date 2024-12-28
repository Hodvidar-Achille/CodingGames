package com.hodvidar.adventofcode.y2024;

import java.util.Arrays;

public class Day07 extends AbstractAdventOfCode2024 {

    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.trim().split(":");
        final double expectedResult = Double.parseDouble(parts[0]);
        final String[] partsTwo = parts[1].trim().split("\\s+");
        final double[] numbers = Arrays.stream(partsTwo)
                .mapToDouble(Double::parseDouble)
                .toArray();

        // Check if the equation can produce the expected result
        final boolean isValid = canProduceResult(numbers, expectedResult);

        // Return the test value if valid, otherwise 0
        return isValid ? expectedResult : 0d;
    }

    private boolean canProduceResult(final double[] numbers, final double expectedResult) {
        return evaluate(numbers, 0, numbers[0], expectedResult);
    }

    protected boolean evaluate(final double[] numbers, final int index, final double currentResult, final double expectedResult) {
        // Base case: if we have processed all numbers
        if (index == numbers.length - 1) {
            return currentResult == expectedResult;
        }

        // Try adding the next number
        if (evaluate(numbers, index + 1, currentResult + numbers[index + 1], expectedResult)) {
            return true;
        }

        // Try multiplying by the next number
        if (evaluate(numbers, index + 1, currentResult * numbers[index + 1], expectedResult)) {
            return true;
        }
        // No valid combination found
        return otherEvaluation(numbers, index, (long) currentResult, expectedResult);
    }

    protected boolean otherEvaluation(final double[] numbers, final int index, final long currentResult, final double expectedResult) {
        return false;
    }
}
