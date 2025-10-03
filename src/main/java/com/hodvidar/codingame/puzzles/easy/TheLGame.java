package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TheLGame {
}

class Solution {

    private static final long[][] C = new long[65][65];
    // ---------- all 8 orientations of the L ----------
    private static final int[][][] ORIENTATIONS = {
            // base (no flip)
            {{0, 0}, {1, 0}, {2, 0}, {2, 1}},
            // rotate 90°
            {{0, 0}, {0, 1}, {0, 2}, {1, 0}},
            // rotate 180°
            {{0, 0}, {0, 1}, {1, 1}, {2, 1}},
            // rotate 270°
            {{0, 2}, {1, 0}, {1, 1}, {1, 2}},
            // mirrored versions (horizontal flip of the base)
            {{0, 1}, {1, 1}, {2, 1}, {2, 0}},
            {{0, 0}, {0, 1}, {0, 2}, {1, 2}},
            {{0, 1}, {0, 0}, {1, 0}, {2, 0}},
            {{0, 0}, {1, 0}, {1, 1}, {1, 2}}
    };

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int height = in.nextInt();
        final int width = in.nextInt();
        final int n = in.nextInt();

        buildCombinations();

        final int totalCells = height * width;
        // quick impossibility test
        if (8 + n > totalCells) {
            System.out.println(0L);
            return;
        }

        final List<Long> placements = generatePlacements(height, width);
        long answer = 0L;

        for (final long redMask : placements) {
            for (final long blueMask : placements) {
                if ((redMask & blueMask) != 0L) continue;          // overlapping Ls
                final int occupied = Long.bitCount(redMask | blueMask); // 8 cells always
                final int free = totalCells - occupied;
                if (free < n) continue;
                answer += C[free][n];
            }
        }

        System.out.println(answer);
    }

    private static void buildCombinations() {
        for (int n = 0; n <= 64; n++) {
            C[n][0] = C[n][n] = 1L;
            for (int k = 1; k < n; k++) {
                final long v = C[n - 1][k - 1] + C[n - 1][k];
                C[n][k] = v;                 // fits into signed long for given limits
            }
        }
    }

    private static List<Long> generatePlacements(final int h, final int w) {
        final List<Long> list = new ArrayList<>();
        for (final int[][] shape : ORIENTATIONS) {
            // find bounding box of the shape to know how far we can shift it
            int maxR = 0, maxC = 0;
            for (final int[] p : shape) {
                maxR = Math.max(maxR, p[0]);
                maxC = Math.max(maxC, p[1]);
            }
            for (int r = 0; r + maxR < h; r++) {
                for (int c = 0; c + maxC < w; c++) {
                    long mask = 0L;
                    for (final int[] p : shape) {
                        final int rr = r + p[0];
                        final int cc = c + p[1];
                        final int idx = rr * w + cc;          // linear index 0 … h*w-1
                        mask |= (1L << idx);
                    }
                    list.add(mask);
                }
            }
        }
        return list;
    }
}
