package com.hodvidar.adventofcode.y2023;

import java.io.FileNotFoundException;
import java.util.*;

public class Day05 extends AbstractAdventOfCode2023 {

    protected SeedMapper seedMapper;

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public double getResultDouble(final Scanner sc) throws FileNotFoundException {
        seedMapper = getSeedMapper();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return seedMapper.getLowestLastCategoryValueFromSeeds();
    }

    protected SeedMapper getSeedMapper() {
        return new SeedMapper();
    }

    @Override
    protected void digestLine(final String line) {
        if (line.trim().isBlank()) {
            return;
        }
        if (line.contains("map:")) {
            seedMapper.addNewMappingRangesCategory();
            return;
        }
        if (line.contains("seeds:")) {
            seedMapper.addSeeds(Arrays.stream(line.split(": ")[1].trim().split("\\s+"))
                    .mapToDouble(Double::parseDouble).boxed().toList());
            return;
        }
        final Double[] extractedAlmanacValues = Arrays.stream(line.trim().split("\\s+"))
                .mapToDouble(Double::parseDouble).boxed().toArray(Double[]::new);
        final double destinationRangeStart = extractedAlmanacValues[0];
        final double sourceRangeStart = extractedAlmanacValues[1];
        final double rangeSize = extractedAlmanacValues[2];
        seedMapper.addMappingRangeInCurrentCategory(destinationRangeStart, sourceRangeStart, rangeSize);
    }

    protected static class SeedMapper {
        protected final List<Double> initialSeed = new ArrayList<>();

        protected final List<List<MappingRange>> mappingRangesCategories = new ArrayList<>();

        private int currentMappingRangeCategoryIndex = -1;

        public void addSeeds(final List<Double> seeds) {
            initialSeed.addAll(seeds);
        }

        public double getLowestLastCategoryValueFromSeeds() {
            return initialSeed.stream().mapToDouble(this::mapSeed).min().orElseThrow();
        }

        private double mapSeed(final double seed) {
            double result = seed;
            for (final List<MappingRange> mappingRanges : mappingRangesCategories) {
                final var currentResult = result;
                final Optional<MappingRange> range = mappingRanges.stream().filter(m -> m.isInRange(currentResult)).findFirst();
                if (range.isPresent()) {
                    result = range.get().transform(result);
                }
            }
            return result;
        }

        public void addNewMappingRangesCategory() {
            currentMappingRangeCategoryIndex++;
        }

        public void addMappingRangeInCurrentCategory(final double destinationRangeStart,
                                                     final double sourceRangeStart,
                                                     final double rangeSize) {
            final var sourceRangeMax = sourceRangeStart - 1 + rangeSize;
            final var transformation = destinationRangeStart - sourceRangeStart;
            final var mappingRange = new MappingRange(sourceRangeStart, sourceRangeMax, transformation);
            addMappingRangeInCurrentCategory(mappingRange);
        }

        private void addMappingRangeInCurrentCategory(final MappingRange mappingRange) {
            if (mappingRangesCategories.size() <= currentMappingRangeCategoryIndex) {
                mappingRangesCategories.add(new ArrayList<>());
            }
            mappingRangesCategories.get(currentMappingRangeCategoryIndex).add(mappingRange);
        }
    }

    protected record MappingRange(double min, double max, double transformation) {

        public boolean isInRange(final double value) {
            return value >= min && value <= max;
        }

        public double transform(final double value) {
            return value + transformation;
        }
    }
}
