package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day09 extends AbstractAdventOfCode2023 {

    private EnvironmentalReport report;

    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        report = getReport();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return report.getSumOfExtrapolatedValues();
    }

    protected EnvironmentalReport getReport() {
        return new EnvironmentalReport();
    }

    @Override
    protected void digestLine(final String line) {
        report.addFirstSequence(Arrays.stream(Arrays.stream(line.trim().split("\\s+"))
                .mapToDouble(Double::parseDouble).toArray()).boxed().collect(Collectors.toList()));
    }

    protected class EnvironmentalReport {

        final List<List<Double>> firstSequences = new ArrayList<>();

        public void addFirstSequence(final List<Double> sequence) {
            firstSequences.add(sequence);
        }

        public double getSumOfExtrapolatedValues() {
            return firstSequences.stream().mapToDouble(this::getExtrapolatedValue).sum();
        }

        protected double getExtrapolatedValue(final List<Double> sequence) {
            final List<Double> subSequence = getSubSequenceOfDifferences(sequence);
            if (subSequence.stream().allMatch(d -> d == 0d)) {
                return sequence.getLast();
            }
            if (subSequence.stream().distinct().count() <= 1) {
                return sequence.getLast() + subSequence.getLast();
            }
            computeExtrapolatedValueUsingSubSequences(subSequence);
            return sequence.getLast() + subSequence.getLast();
        }

        protected void computeExtrapolatedValueUsingSubSequences(final List<Double> parentSequence) {
            final List<Double> subSequence = getSubSequenceOfDifferences(parentSequence);
            if (subSequence.stream().distinct().count() > 1) {
                computeExtrapolatedValueUsingSubSequences(subSequence);
            }
            parentSequence.add(parentSequence.getLast() + subSequence.getLast());
        }

        protected List<Double> getSubSequenceOfDifferences(final List<Double> parentSequence) {
            final List<Double> subSequence = new ArrayList<>();
            for (int i = 0; i < parentSequence.size() - 1; i++) {
                subSequence.add(parentSequence.get(i + 1) - parentSequence.get(i));
            }
            return subSequence;
        }
    }
}
