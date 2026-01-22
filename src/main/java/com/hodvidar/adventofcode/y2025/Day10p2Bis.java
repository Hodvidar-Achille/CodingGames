package com.hodvidar.adventofcode.y2025;


import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Set;

public class Day10p2Bis extends Day10p2 {

    @Override
    public double getDigitFromLine(final String line) {
        final ParsedLine parsed = parseLine(line);
        return MinimalButtonSolver.solve(parsed.numbers(),
                parsed.groups());
    }

    /**
     * Minimal‑button solver for Part 2.
     *
     * <p>Two‑phase algorithm:
     * <ul>
     *   <li>A* search with a packed‑long state.  Fast for the majority of inputs.</li>
     *   <li>If the A* frontier grows beyond {@code MAX_NODES}, fall back to a
     *       branch‑and‑bound algorithm that uses an LP relaxation (Google OR‑Tools).</li>
     * </ul>
     *
     * <p>The class is deliberately self‑contained – you only need to add the
     * OR‑Tools dependency (e.g. Maven coordinates {@code com.google.ortools:ortools‑linux‑x86‑64:9.8} or the
     * platform‑specific jar) and call {@link #solve(List, List)}.
     */
    static class MinimalButtonSolver {

        /* ------------------------------------------------------------------ */
        /* 1️⃣  Configuration constants                                         */
        /* ------------------------------------------------------------------ */

        /**
         * Abort A* after this many expanded nodes and switch to the LP‑BB solver.
         */
        private static final long MAX_ASTAR_NODES = 20_000_000L;   // tune if necessary

        /**
         * Maximum value a single counter may hold for the packed‑long representation.
         * 8 bits → 0 .. 255, more than enough for Advent‑of‑Code data.
         */
        private static final int COUNTER_MAX = 255;

        /* ------------------------------------------------------------------ */
        /* 2️⃣  Public entry point                                               */
        /* ------------------------------------------------------------------ */

        /**
         * Solves the “minimum button presses” problem.
         *
         * @param targetCounters the list taken from the curly‑brace part (e.g. {3,5,4,7})
         * @param buttonDefs     each inner list describes one button (indices it touches)
         * @return the smallest number of presses needed, or -1 if no solution exists
         */
        public static int solve(final List<Integer> targetCounters,
                                final List<List<Integer>> buttonDefs) {

            final int[] target = targetCounters.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();

            final int[][] buttons = buttonDefs.stream()
                    .map(l -> l.stream()
                            .mapToInt(Integer::intValue)
                            .toArray())
                    .toArray(int[][]::new);

            // -----------------------------------------------------------------
            // Phase 1 – A* (fast path)
            // -----------------------------------------------------------------

            final OptionalInt aStarResult = aStarSearch(target, buttons);
            if (aStarResult.isPresent()) {
                return aStarResult.getAsInt();
            }


            // -----------------------------------------------------------------
            // Phase 2 – Branch‑and‑Bound with LP relaxation (fallback)
            // -----------------------------------------------------------------
            return branchAndBound(target, buttons);
        }

        /* ------------------------------------------------------------------ */
        /* 3️⃣  Phase 1 – A* implementation                                    */
        /* ------------------------------------------------------------------ */

        /**
         * Packed‑long helpers (8 bits per counter).
         */
        private static long pack(final int[] arr) {
            long v = 0L;
            for (int i = 0; i < arr.length; i++) {
                v |= ((long) arr[i] & 0xFFL) << (8 * i);
            }
            return v;
        }

        private static void unpack(final long packed, final int[] out) {
            for (int i = 0; i < out.length; i++) {
                out[i] = (int) ((packed >>> (8 * i)) & 0xFFL);
            }
        }

        /**
         * Heuristic = max remaining gap (admissible).
         */
        private static int heuristic(final int[] cur, final int[] target) {
            int max = 0;
            for (int i = 0; i < cur.length; i++) {
                final int diff = target[i] - cur[i];
                if (diff > max) max = diff;
            }
            return max;
        }

        /**
         * A* search – returns OptionalInt.empty() when node limit is exceeded.
         */
        private static OptionalInt aStarSearch(final int[] target,
                                               final int[][] buttons) {

            final int C = target.length;
            final int B = buttons.length;

            // ---- pre‑compute column masks (packed long) for fast addition ----
            final long[] colMask = new long[B];
            for (int j = 0; j < B; j++) {
                long mask = 0L;
                for (final int idx : buttons[j]) {
                    // each counter occupies one byte → add 1 to that byte
                    mask += (1L << (8 * idx));
                }
                colMask[j] = mask;
            }

            // ---- priority‑queue node definition ----
            record Node(long packed, int depth, int f) implements Comparable<Node> {
                @Override
                public int compareTo(final Node o) {
                    return Integer.compare(this.f, o.f);
                }
            }

            final PriorityQueue<Node> pq = new PriorityQueue<>();
            final Set<Long> visited = new HashSet<>();

            // start from the all‑zero state
            final long start = pack(new int[C]);          // all counters = 0
            pq.add(new Node(start, 0, heuristic(new int[C], target)));
            visited.add(start);

            long processed = 0;
            final int[] cur = new int[C];                 // reusable buffer for unpacking

            while (!pq.isEmpty()) {
                final Node curNode = pq.poll();
                processed++;

                // abort A* if we have explored too many nodes
                if (processed > MAX_ASTAR_NODES) {
                    return OptionalInt.empty();
                }

                unpack(curNode.packed, cur);
                if (Arrays.equals(cur, target)) {
                    return OptionalInt.of(curNode.depth);   // optimal solution found
                }

                // expand all buttons
                for (int j = 0; j < B; j++) {
                    final long nextPacked = curNode.packed + colMask[j];

                    // quick overflow test: any byte > target ?
                    boolean overshoot = false;
                    for (int i = 0; i < C; i++) {
                        final int val = (int) ((nextPacked >>> (8 * i)) & 0xFFL);
                        if (val > target[i]) {
                            overshoot = true;
                            break;
                        }
                    }
                    if (overshoot) continue;               // dead branch

                    if (visited.add(nextPacked)) {          // first time we see this state
                        final int nextDepth = curNode.depth + 1;
                        // recompute heuristic for the new state (still cheap)
                        for (int i = 0; i < C; i++) {
                            cur[i] = (int) ((nextPacked >>> (8 * i)) & 0xFFL);
                        }
                        final int h = heuristic(cur, target);
                        pq.add(new Node(nextPacked, nextDepth, nextDepth + h));
                    }
                }
            }

            // No solution reachable (should not happen for valid inputs)
            return OptionalInt.empty();
        }

        /* ------------------------------------------------------------------ */
        /* 4️⃣  Phase 2 – Branch‑and‑Bound with LP relaxation                */
        /* ------------------------------------------------------------------ */

        /**
         * Branch‑and‑Bound driver that uses an LP relaxation as a lower bound.
         *
         * @return minimal number of presses, or -1 if unsatisfiable
         */
        private static int branchAndBound(final int[] target,
                                          final int[][] buttons) {

            // Load native libraries for OR‑Tools (only needed once per JVM)
            Loader.loadNativeLibraries();

            final int C = target.length;
            final int B = buttons.length;

            // -------------------- 4.1  Build base LP model --------------------
            final MPSolver solver = MPSolver.createSolver("GLOP");          // GLOP = Google's LP solver
            if (solver == null) {
                throw new IllegalStateException("Failed to create OR‑Tools solver");
            }

            final MPVariable[] vars = new MPVariable[B];
            for (int j = 0; j < B; ++j) {
                vars[j] = solver.makeNumVar(0.0, Double.POSITIVE_INFINITY, "x" + j);
            }

            // Equality constraints: Σ A[c][j] * xj = target[c]
            for (int c = 0; c < C; ++c) {
                final MPConstraint ct = solver.makeConstraint(target[c], target[c]); // exact equality
                for (int j = 0; j < B; ++j) {
                    if (contains(buttons[j], c)) {
                        ct.setCoefficient(vars[j], 1.0);
                    }
                }
            }

            // Objective: minimise Σ xj
            final MPObjective obj = solver.objective();
            for (int j = 0; j < B; ++j) {
                obj.setCoefficient(vars[j], 1.0);
            }
            obj.setMinimization();

            // -------------------- 4.2  Global upper bound --------------------
            final int[] best = {Integer.MAX_VALUE};   // best integer solution found so far

            // -------------------- 4.3  Recursive BB function -----------------
            /**
             * @param lo lower bounds (null = default 0)
             * @param hi upper bounds (null = +∞)
             */
            record BBNode(double[] lo, double[] hi) {
            }

            final Deque<BBNode> stack = new ArrayDeque<>();
            stack.push(new BBNode(null, null));   // start with no extra bounds

            while (!stack.isEmpty()) {
                final BBNode node = stack.pop();

                // ---- apply the extra bounds for this branch -----------------
                for (int j = 0; j < B; ++j) {
                    final double lower = (node.lo != null && !Double.isNaN(node.lo[j])) ? node.lo[j] : 0.0;
                    final double upper = (node.hi != null && !Double.isNaN(node.hi[j])) ? node.hi[j] : Double.POSITIVE_INFINITY;
                    vars[j].setLb(lower);
                    vars[j].setUb(upper);
                }

                // ---- solve the LP relaxation -------------------------------
                final MPSolver.ResultStatus status = solver.solve();
                if (status != MPSolver.ResultStatus.OPTIMAL) {
                    continue;                       // infeasible branch → prune
                }

                final double lpValue = obj.value();       // lower bound (real)
                if (lpValue >= best[0]) {
                    continue;                       // cannot beat current best
                }

                // ---- check whether the LP solution is integral ------------
                boolean allIntegral = true;
                final int[] intVals = new int[B];
                for (int j = 0; j < B; ++j) {
                    final double val = vars[j].solutionValue();
                    final int rounded = (int) Math.round(val);
                    if (Math.abs(val - rounded) > 1e-9) {
                        allIntegral = false;
                    }
                    intVals[j] = rounded;
                }

                if (allIntegral) {
                    // Feasible integer solution found → update global upper bound
                    int candidate = 0;
                    for (final int v : intVals) candidate += v;
                    if (candidate < best[0]) {
                        best[0] = candidate;
                    }
                    continue;   // no need to branch further from an integral node
                }

                // ---- pick a fractional variable to branch on ---------------
                int branchIdx = -1;
                double fractionalVal = 0.0;
                for (int j = 0; j < B; ++j) {
                    final double val = vars[j].solutionValue();
                    if (Math.abs(val - Math.round(val)) > 1e-9) {
                        branchIdx = j;
                        fractionalVal = val;
                        break;
                    }
                }
                if (branchIdx == -1) {
                    // Should not happen – all variables integral but we missed it above
                    continue;
                }

                final int floor = (int) Math.floor(fractionalVal);
                final int ceil = (int) Math.ceil(fractionalVal);

                // ---- LEFT branch : x[branchIdx] ≤ floor --------------------
                final double[] loLeft = (node.lo == null) ? new double[B] : node.lo.clone();
                final double[] hiLeft = (node.hi == null) ? new double[B] : node.hi.clone();
                // lower bounds stay the same, only tighten the upper bound
                hiLeft[branchIdx] = floor;
                stack.push(new BBNode(loLeft, hiLeft));

                // ---- RIGHT branch : x[branchIdx] ≥ ceil --------------------
                final double[] loRight = (node.lo == null) ? new double[B] : node.lo.clone();
                final double[] hiRight = (node.hi == null) ? new double[B] : node.hi.clone();
                loRight[branchIdx] = ceil;
                stack.push(new BBNode(loRight, hiRight));
            }

            return (best[0] == Integer.MAX_VALUE) ? -1 : best[0];
        }

        /**
         * Utility – does the int[] contain the value?
         */
        private static boolean contains(final int[] arr, final int value) {
            for (final int v : arr) if (v == value) return true;
            return false;
        }
    }
}
