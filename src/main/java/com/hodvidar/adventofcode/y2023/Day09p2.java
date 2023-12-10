package com.hodvidar.adventofcode.y2023;

import java.util.List;

public class Day09p2 extends Day09 {

    @Override
    protected EnvironmentalReport getReport() {
        return new EnvironmentalReportV2();
    }

    private class EnvironmentalReportV2 extends EnvironmentalReport {

        @Override
        protected double getExtrapolatedValue(final List<Double> sequence) {
            final List<Double> subSequence = getSubSequenceOfDifferences(sequence);
            if (subSequence.stream().allMatch(d -> d == 0d)) {
                return sequence.getFirst();
            }
            if (subSequence.stream().distinct().count() <= 1) {
                return sequence.getFirst() - subSequence.getFirst();
            }
            computeExtrapolatedValueUsingSubSequences(subSequence);
            return sequence.getFirst() - subSequence.getFirst();
        }

        @Override
        protected void computeExtrapolatedValueUsingSubSequences(final List<Double> parentSequence) {
            final List<Double> subSequence = getSubSequenceOfDifferences(parentSequence);
            if (subSequence.stream().distinct().count() > 1) {
                computeExtrapolatedValueUsingSubSequences(subSequence);
            }
            parentSequence.addFirst(parentSequence.getFirst() - subSequence.getFirst());
        }
    }
}
