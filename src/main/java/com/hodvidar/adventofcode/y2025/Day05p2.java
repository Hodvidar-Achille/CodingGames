package com.hodvidar.adventofcode.y2025;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Day05p2 extends Day05 {

    private List<List<BigInteger>> mergeRanges(List<List<BigInteger>> original) {
        if (original.isEmpty()) {
            return Collections.emptyList();
        }

        // 1️⃣  Sort by the start value (ascending)
        List<List<BigInteger>> sorted = new ArrayList<>(original);
        sorted.sort(Comparator.comparing(pair -> pair.get(0)));

        // 2️⃣  Walk the sorted list and merge when possible
        List<List<BigInteger>> merged = new ArrayList<>();
        BigInteger curStart = sorted.get(0).get(0);
        BigInteger curEnd   = sorted.get(0).get(1);

        for (int i = 1; i < sorted.size(); i++) {
            BigInteger nextStart = sorted.get(i).get(0);
            BigInteger nextEnd   = sorted.get(i).get(1);

            // Overlap or touch?  (nextStart ≤ curEnd + 1)
            // The “+1” makes adjoining intervals like [5‑10] and [11‑15] merge
            // because the problem says “including the edges”.
            if (nextStart.compareTo(curEnd.add(BigInteger.ONE)) <= 0) {
                // Extend the current interval to the farthest end seen so far
                if (nextEnd.compareTo(curEnd) > 0) {
                    curEnd = nextEnd;
                }
            } else {
                // No overlap – store the finished interval and start a new one
                merged.add(List.of(curStart, curEnd));
                curStart = nextStart;
                curEnd   = nextEnd;
            }
        }

        // Add the last interval
        merged.add(List.of(curStart, curEnd));
        return merged;
    }


    private BigInteger totalCoveredSize(List<List<BigInteger>> merged) {
        BigInteger total = BigInteger.ZERO;
        for (List<BigInteger> interval : merged) {
            BigInteger start = interval.get(0);
            BigInteger end   = interval.get(1);
            // (end - start + 1)  because both ends are inclusive
            total = total.add(end.subtract(start).add(BigInteger.ONE));
        }
        return total;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        ids.clear();
        ranges.clear();
        while (sc.hasNextLine()) {
            this.digestLine(sc.nextLine());
        }
        final List<List<BigInteger>> mergedRanges = mergeRanges(ranges);
        return totalCoveredSize(mergedRanges).doubleValue();
    }
}
