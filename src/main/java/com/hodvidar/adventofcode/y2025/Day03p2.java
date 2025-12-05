package com.hodvidar.adventofcode.y2025;

public class Day03p2 extends Day03 {

    @Override
    public double getDigitFromLine(final String line) {
        if (line.length() <= 12) {
            return Double.parseDouble(line);
        }
        final char[] digits = line.toCharArray();
        final int length = digits.length;
        final int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = digits[i] - '0';
        }
        final Joltage joltage = new Joltage(ints);
        return joltage.toDouble();
    }

    static class Joltage {
        int[] batteryValues = new int[12];

        public Joltage(final int[] source) {
            final int K = 12;
            int startIdx = 0;

            for (int slot = 0; slot < K; slot++) {
                // The farthest index we may examine for this slot.
                // We need (K‑slot‑1) digits after the one we pick,
                // so the last admissible index is:
                final int endIdxInclusive = source.length - (K - slot - 1) - 1;

                // Scan the window [startIdx endIdxInclusive] and find the
                // largest digit if there are ties we keep the left‑most one.
                int bestIdx = startIdx;
                int bestVal = source[startIdx];
                for (int i = startIdx + 1; i <= endIdxInclusive; i++) {
                    final int cur = source[i];
                    if (cur > bestVal) {
                        bestVal = cur;
                        bestIdx = i;
                    }
                }

                // Store the chosen digit and its original position.
                batteryValues[slot] = bestVal;

                // Next window must start *after* the element we just took.
                startIdx = bestIdx + 1;
            }
        }

        double toDouble() {
            final StringBuilder sb = new StringBuilder();
            for (final int v : batteryValues) {
                sb.append(v);
            }
            return Double.parseDouble(sb.toString());
        }

    }
}
