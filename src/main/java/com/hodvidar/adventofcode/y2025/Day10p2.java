package com.hodvidar.adventofcode.y2025;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day10p2 extends Day10 {


    /**
     * Compare the current counter vector with the target.
     *
     * @return 0  → exact match,
     * -1  → still below the target (continue searching),
     * 1  → at least one counter exceeded the target (dead branch)
     */
    private static int checkSolution(final int[] searchedSolution,
                                     final int[] currentSolution) {
        for (int i = 0; i < searchedSolution.length; i++) {
            if (currentSolution[i] > searchedSolution[i]) {
                return 1;                 // overshoot → impossible
            } else if (currentSolution[i] < searchedSolution[i]) {
                return -1;                // still need more presses
            }
        }
        return 0;                         // perfect match
    }

    /**
     * Apply one press of the given button to the mutable counter array.
     */
    private static void clickButton(final List<Integer> button,
                                    final int[] currentSolution) {
        for (final Integer idx : button) {
            currentSolution[idx] += 1;
        }
    }

    /**
     * BFS search for the smallest number of button presses that reaches the
     * required joltage counters.
     *
     * @param pattern the target counters (as a List<Integer>)
     * @param groups  each inner list describes one button (list of counter indices)
     * @return minimal number of presses, or -1 if no solution exists
     */
    static int solveMinimalButtonForJoltageRequirement(final List<Integer> pattern,
                                                       final List<List<Integer>> groups) {

        final int nLights = pattern.size();          // number of counters
        final int nButtons = groups.size();           // number of distinct buttons

        // ----- 1️⃣  Transform inputs to primitive arrays for speed -----
        final int[] target = pattern.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        // buttons[i] = int[] of indices that button i touches
        final int[][] buttons = groups.stream()
                .map(g -> g.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);

        // ----- 2️⃣  BFS structures -----
        // each queue entry holds a snapshot of the counters and the depth (press count)
        record Node(int[] counters, int depth) {
        }

        final Queue<Node> queue = new ArrayDeque<>();
        // start from all‑zero counters
        queue.add(new Node(new int[nLights], 0));

        // visited set – we store a compact string key for each counter vector
        final Set<String> visited = new HashSet<>();
        visited.add(keyFromArray(new int[nLights]));

        while (!queue.isEmpty()) {
            final Node curNode = queue.poll();
            final int[] curCounters = curNode.counters;
            final int curDepth = curNode.depth;

            // ----- 3️⃣  Check whether we have solved the machine -----
            final int cmp = checkSolution(target, curCounters);
            if (cmp == 0) {
                // exact match → the first time we see it is the optimal depth
                return curDepth;
            }
            if (cmp == 1) {
                // overshoot – this branch is dead, nothing to expand
                continue;
            }
            // cmp == -1 → still below target → expand with every button

            // ----- 4️⃣  Expand: try each button (including repetitions) -----
            for (int b = 0; b < nButtons; b++) {
                // create a fresh copy of the current counters
                final int[] next = Arrays.copyOf(curCounters, nLights);
                // apply the button once
                for (final int idx : buttons[b]) {
                    next[idx] += 1;
                }

                // quick prune: if any counter already exceeds the target we can skip
                boolean bad = false;
                for (int i = 0; i < nLights; i++) {
                    if (next[i] > target[i]) {
                        bad = true;
                        break;
                    }
                }
                if (bad) {
                    continue;   // dead branch
                }

                // generate a hashable key; if we have seen this vector before we skip it
                final String key = keyFromArray(next);
                if (visited.add(key)) {   // add returns true only if it was not present
                    queue.add(new Node(next, curDepth + 1));
                }
            }
        }

        // If we exit the loop no solution was found (should not happen for valid input)
        return -1;
    }

    /**
     * Helper – turn an int[] into a comma‑separated string for the visited set.
     */
    private static String keyFromArray(final int[] arr) {
        // Using a StringBuilder avoids the overhead of Arrays.toString().
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------
    // The rest of the class (parsing, getDigitFromLine, etc.) can stay
    // exactly as you already have in Day10.  The only thing you need to
    // call from there is solveMinimalButtonForJoltageRequirement(...).
    // -----------------------------------------------------------------

    @Override
    public double getDigitFromLine(final String line) {
        final ParsedLine parsed = parseLine(line);
        return solveMinimalButtonForJoltageRequirement(parsed.numbers(), parsed.groups());
    }


}