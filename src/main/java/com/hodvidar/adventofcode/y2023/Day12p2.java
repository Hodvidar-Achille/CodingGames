package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12p2 extends Day12 {
    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.split("\\s+");
        final String record = unfoldCondition(parts[0], 5);
        final List<Integer> groups = unfoldGroups(Arrays.stream(parts[1].split(",")).map(Integer::valueOf)
                .toList(), 5);
        return countPermutations(record, groups);
    }

    private String unfoldCondition(final String condition, int times) {
        final StringBuilder sb = new StringBuilder();
        sb.append(".");
        for (int i = 0; i < times - 1; i++) {
            sb.append(condition);
            sb.append("?");
        }
        sb.append(condition);
        sb.append(".");
        return sb.toString();
    }

    private List<Integer> unfoldGroups(final List<Integer> groups, int times) {
        final List<Integer> unfoldedGroups = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            unfoldedGroups.addAll(groups);
        }
        return unfoldedGroups;
    }
}
