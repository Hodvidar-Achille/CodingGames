package com.hodvidar.adventofcode.y2025;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day05 extends AbstractAdventOfCode2025 {

    protected final List<List<BigInteger>> ranges = new ArrayList<>();
    protected final List<BigInteger> ids = new ArrayList<>();

    @Override
    public void digestLine(final String line) {
        if (line.isBlank()) {
            return;
        }
        if (line.contains("-")) {
            final String[] parts = line.split("-");
            final BigInteger start = BigInteger.valueOf(Long.parseLong(parts[0]));
            final BigInteger end = BigInteger.valueOf(Long.parseLong(parts[1]));
            this.ranges.add(List.of(start, end));
            return;
        }
        this.ids.add(BigInteger.valueOf(Long.parseLong(line)));
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        ids.clear();
        ranges.clear();
        while (sc.hasNextLine()) {
            this.digestLine(sc.nextLine());
        }
        double counter = 0;
        for (final BigInteger id : ids) {
            if (isInRange(id)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isInRange(final BigInteger id) {
        for (final List<BigInteger> range : ranges) {
            if (id.compareTo(range.get(0)) >= 0 && id.compareTo(range.get(1)) <= 0) {
                return true;
            }
        }
        return false;
    }

}
