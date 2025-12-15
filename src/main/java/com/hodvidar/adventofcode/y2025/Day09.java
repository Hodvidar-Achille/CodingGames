package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day09 extends AbstractAdventOfCode2025 {

    /** All red‑tile positions that have been read so far. */
    private final List<Point> points = new ArrayList<>();

    @Override
    public double getDigitFromLine(final String line) {
        // each line looks like "7,1"
        final String[] parts = line.trim().split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid coordinate line: " + line);
        }
        final int x = Integer.parseInt(parts[0].trim());
        final int y = Integer.parseInt(parts[1].trim());
        points.add(new Point(x, y));
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        points.clear();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        long maxArea = 0L;
        final int n = points.size();
        // dumb brute forcing
        for (int i = 0; i < n - 1; i++) {
            final Point a = points.get(i);
            for (int j = i + 1; j < n; j++) {
                final Point b = points.get(j);
                final long width = Math.abs((long) a.x - b.x) + 1;
                final long height = Math.abs((long) a.y - b.y) + 1;
                final long area = width * height;
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return (double) maxArea;
    }

    private record Point(int x, int y) {
    }
}