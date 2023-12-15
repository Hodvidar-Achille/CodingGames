package com.hodvidar.adventofcode.y2023;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 extends AbstractAdventOfCode2023 {
    @Override
    public int getDay() {
        return 15;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        return hashAlgorithmSum(sc.nextLine());
    }

    public int hashAlgorithmSum(final String initializationSequence) {
        return Arrays.stream(initializationSequence.split(",")).mapToInt(this::hashAlgorithm).sum();
    }

    public int hashAlgorithm(final String individualStep) {
        final AtomicInteger hash = new AtomicInteger();
        individualStep.chars().forEach(c -> hash.set((hash.addAndGet(c) * 17) % 256));
        return hash.get();
    }
}
