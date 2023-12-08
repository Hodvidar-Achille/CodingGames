package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class _Day05_2 extends _Day05 {

    @Override
    protected SeedMapper getSeedMapper() {
        return new SeedMapperV2();
    }

    private static class SeedMapperV2 extends SeedMapper {

        private final List<Range> seedRanges = new ArrayList<>();

        @Override
        public void addSeeds(final List<Double> seeds) {
            // seeds are ranges:
            boolean first = true;
            double minRange = 0;
            for (final double seed : seeds) {
                if (first) {
                    minRange = seed;
                    first = false;
                    continue;
                }
                final var maxRange = minRange - 1 + seed;
                seedRanges.add(new Range(minRange, maxRange));
                first = true;
            }
        }

        @Override
        public double getLowestLastCategoryValueFromSeeds() {
            List<Range> ranges = seedRanges;
            for (final List<MappingRange> mappingRanges : mappingRangesCategories) {
                ranges = mapRangesToNewRanges(ranges, mappingRanges);
            }
            return ranges.stream().mapToDouble(r -> r.min).min().orElseThrow();
        }

        private List<Range> mapRangesToNewRanges(final List<Range> ranges, final List<MappingRange> mappingRanges) {
            final List<Range> newRanges = new ArrayList<>();
            for (final Range range : ranges) {
                List<MappingRange> relevantMappingRanges = mappingRanges.stream()
                        .filter(mr -> mr.isInRange(range.min) || mr.isInRange(range.max))
                        .collect(toList());
                if (relevantMappingRanges.isEmpty()) {
                    newRanges.add(range);
                } else {
                    newRanges.addAll(mapRangeToNewRanges(range, relevantMappingRanges));
                }
            }
            return newRanges;
        }


        private List<Range> mapRangeToNewRanges(final Range range, List<MappingRange> relevantMappingRanges) {
            relevantMappingRanges.sort(Comparator.comparingDouble(MappingRange::min));
            // Need to handle this case
            // mappingRange is fully inside range
            //                    [<-------MAP1------->]              [<-------MAP2------->]
            //       [<------------VALUES--------------------------------------------------------------->]
            // -->   [<--VALUES->][<----NEWVAL1------->][<--VALUES-->][<----NEWVAL2------->][<--VALUES-->]
            final List<Range> newRanges = new ArrayList<>();
            Range currentRange = range;
            // As relevantMappingRanges are sorted, we can iterate over them and apply transformation one by one
            // Step1:
            //                    [<-------MAP1------->]
            //       [<------------VALUES--------------------------------------------------------------->]
            // -->   [<--VALUES->][<----NEWVAL1------->][<--VALUES-------------------------------------->]
            // Step2:
            //                                                          [<-------MAP2------->]
            //                                          [<------------VALUES---------------------------->]
            // -->                                      [<--VALUES-->][<----NEWVAL2------->][<--VALUES-->]
            for(int i = 0; i < relevantMappingRanges.size(); i++) {
                final List<Range> intermediateRanges = mapRangeToNewRanges(currentRange, relevantMappingRanges.get(i));
                currentRange = intermediateRanges.get(intermediateRanges.size() - 1);
                newRanges.addAll(intermediateRanges.subList(0, intermediateRanges.size() - 1));
                if(i == relevantMappingRanges.size() - 1) {
                    newRanges.add(currentRange);
                }
            }
            return newRanges;
        }
        private List<Range> mapRangeToNewRanges(final Range range, MappingRange mappingRange) {
            final List<Range> newRanges = new ArrayList<>();
            if (mappingRange.min() <= range.min() && mappingRange.max() >= range.max()) {
                // range is fully inside mappingRange
                // [<-------MAP------->]
                //   [<--VALUES-->]
                // -->                [<--NEWVAL-->]
                newRanges.add(new Range(mappingRange.transform(range.min()), mappingRange.transform(range.max())));
                return newRanges;
            }
            if (mappingRange.min() > range.min() && mappingRange.max() < range.max()) {
                // mappingRange is fully inside range
                //              [<-------MAP------->]
                //       [<------------VALUES-------------->]
                // -->   [<--VALUES-->] [<--NEWVAL-->] [<--VALUES-->]
                newRanges.add(new Range(range.min(), mappingRange.min() - 1));
                newRanges.add(new Range(mappingRange.transform(mappingRange.min()), mappingRange.transform(mappingRange.max())));
                newRanges.add(new Range(mappingRange.max() + 1, range.max()));
                return newRanges;
            }
            if (mappingRange.min() <= range.min()) {
                // mappingRange is partially inside range, on the left
                //   [<-------MAP------->]
                //       [<------------VALUES-------------->]
                // -->   [<--NEWVAL----->] [<--VALUES-->]
                newRanges.add(new Range(mappingRange.transform(range.min()), mappingRange.transform(mappingRange.max())));
                newRanges.add(new Range(mappingRange.max() + 1, range.max()));
                return newRanges;
            }
            // mappingRange is partially inside range, on the right
            //                           [<-------MAP------->]
            //       [<------------VALUES-------------->]
            // -->   [<--VALUES------->] [<--NEWVAL----->]
            newRanges.add(new Range(range.min(), mappingRange.min() - 1));
            newRanges.add(new Range(mappingRange.transform(mappingRange.min()), mappingRange.transform(range.max())));
            return newRanges;
        }
    }

    private record Range(double min, double max) {
    }
}
