package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day08 extends AbstractAdventOfCode2025 {

    private final List<Point3D> points = new ArrayList<>();
    private int numberOfPairsToBuild = 1;

    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.split(",");
        points.add(new Point3D(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), -1));
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        points.clear();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        final ClosestPairsBlocks cpb = new ClosestPairsBlocks(points);
        return cpb.productOfBlockSizes(numberOfPairsToBuild, 3);
    }

    public void setNumberOfPairsToBuild(final int numberOfPairsToBuild) {
        this.numberOfPairsToBuild = numberOfPairsToBuild;
    }

    /**
     * @param id stable index, used by DSU
     */
    record Point3D(double x, double y, double z, int id) {
    }

    static class ClosestPairsBlocks {

        private final KDNode root;               // built once in the ctor
        private final Point3D[] points;          // array indexed by id

        /* -------------------- ctor – builds the tree -------------------- */
        public ClosestPairsBlocks(final List<Point3D> pts) {
            if (pts == null || pts.size() < 2) {
                throw new IllegalArgumentException("need ≥2 points");
            }
            // assign a stable id to each point (required for DSU)
            this.points = new Point3D[pts.size()];
            for (int i = 0; i < pts.size(); i++) {
                final Point3D p = pts.get(i);
                this.points[i] = new Point3D(p.x, p.y, p.z, i);
            }
            this.root = buildKDTree(Arrays.asList(this.points), 0);
        }

        /* -------------------- KD‑Tree builder (static) -------------------- */
        private static KDNode buildKDTree(final List<Point3D> pts, final int depth) {
            if (pts.isEmpty()) {
                return null;
            }
            final int axis = depth % 3;
            pts.sort(Comparator.comparingDouble(p -> getCoord(p, axis)));
            final int mid = pts.size() / 2;
            final Point3D median = pts.get(mid);
            return new KDNode(median, buildKDTree(pts.subList(0, mid), depth + 1), buildKDTree(pts.subList(mid + 1, pts.size()), depth + 1), axis);
        }

        /* -------------------- geometry helpers -------------------- */
        private static double getCoord(final Point3D p, final int axis) {
            return switch (axis) {
                case 0 -> p.x;
                case 1 -> p.y;
                default -> p.z;
            };
        }

        /* -------------------- public API -------------------- */

        private static double distance(final Point3D a, final Point3D b) {
            final double dx = a.x - b.x;
            final double dy = a.y - b.y;
            final double dz = a.z - b.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }

        /**
         * Returns the product of the sizes of the blocks obtained after
         * connecting the {@code X} closest pairs (subject to the merging rule).
         *
         * @param numberOfPairsToCreate number of pairs to extract (must be ≤ points.length)
         * @return product of block sizes as a {@code long}
         */
        /* --------------------------------------------------------------- *
         *  PUBLIC API – the method that the caller uses                     *
         * --------------------------------------------------------------- */
        public long productOfBlockSizes(final int numberOfPairsToCreate, final int numberOfBiggerBlocks) {
            validateInput(numberOfPairsToCreate);
            final DSU dsu = new DSU(this.points.length);
            final PriorityQueue<Candidate> pq = initQueue(dsu);
            acceptPairs(pq, dsu, numberOfPairsToCreate);
            final int[] biggest = biggestBlocks(dsu, numberOfBiggerBlocks);
            return multiply(biggest);
        }

        /* --------------------------------------------------------------- *
         *  1️⃣  INPUT VALIDATION                                            *
         * --------------------------------------------------------------- */
        private void validateInput(final int pairs) {
            if (pairs < 1 || pairs > this.points.length) {
                throw new IllegalArgumentException("invalid X");
            }
        }

        /* --------------------------------------------------------------- *
         *  2️⃣  BUILD INITIAL PRIORITY QUEUE                               *
         * --------------------------------------------------------------- */
        private PriorityQueue<Candidate> initQueue(final DSU dsu) {
            final PriorityQueue<Candidate> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> c.dist));
            for (final Point3D p : this.points) {
                final Candidate c = nearestDifferentComponent(p, dsu);
                if (c != null) {
                    pq.offer(c);
                }
            }
            return pq;
        }

        /* --------------------------------------------------------------- *
         *  3️⃣  ACCEPT PAIRS                                               *
         * --------------------------------------------------------------- */
        private void acceptPairs(final PriorityQueue<Candidate> pq, final DSU dsu, final int maxPairs) {
            int accepted = 0;
            while (accepted < maxPairs && !pq.isEmpty()) {
                final Candidate cur = pq.poll();
                if (dsu.find(cur.a.id) == dsu.find(cur.b.id)) {
                    continue;
                }
                dsu.union(cur.a.id, cur.b.id);
                accepted++;
                //recomputeAll(pq, dsu);
                // replaced by :
                // the root of the newly created component (after the union)
                int mergedRoot = dsu.find(cur.a.id);   // both a and b now share this root
                recomputeOnlyMergedComponent(pq, dsu, mergedRoot);
            }
        }

        /* --------------------------------------------------------------- *
         *  4️⃣  RECOMPUTE CANDIDATES FOR EVERY POINT                      *
         * --------------------------------------------------------------- */
        private void recomputeAll(final PriorityQueue<Candidate> pq, final DSU dsu) {
            for (final Point3D p : this.points) {
                final Candidate upd = nearestDifferentComponent(p, dsu);
                if (upd != null) {
                    pq.offer(upd);
                }
            }
        }

        /**
         * Re‑computes the nearest‑different‑component candidate **only** for the
         * points that are members of the component that has just been enlarged
         * (the component whose root is {@code mergedRoot}).
         *
         * @param pq          the priority queue that stores the candidates
         * @param dsu         the disjoint‑set structure
         * @param mergedRoot  the representative of the component that resulted
         *                    from the latest union
         */
        private void recomputeOnlyMergedComponent(final PriorityQueue<Candidate> pq,
                                                  final DSU dsu,
                                                  final int mergedRoot) {
            for (final Point3D p : this.points) {
                if (dsu.find(p.id) != mergedRoot) {
                    continue;                     // point is not in the newly grown block
                }
                final Candidate upd = nearestDifferentComponent(p, dsu);
                if (upd != null) {
                    pq.offer(upd);
                }
            }
        }

        /* --------------------------------------------------------------- *
         *  5️⃣  GET SIZES OF THE BIGGEST BLOCKS                            *
         * --------------------------------------------------------------- */
        private int[] biggestBlocks(final DSU dsu, final int limit) {
            final Map<Integer, Integer> compCount = new HashMap<>();
            for (int i = 0; i < this.points.length; i++) {
                final int root = dsu.find(i);
                compCount.merge(root, 1, Integer::sum);
            }
            return compCount.values().stream().sorted(Comparator.reverseOrder()).limit(limit).mapToInt(Integer::intValue).toArray();
        }

        /* --------------------------------------------------------------- *
         *  6️⃣  MULTIPLY ARRAY ELEMENTS                                    *
         * --------------------------------------------------------------- */
        private long multiply(final int[] values) {
            long prod = 1L;
            for (final int v : values)
                prod *= v;
            return prod;
        }

        /* -------------------- helper: nearest neighbour in a *different* component -------------------- */
        private Candidate nearestDifferentComponent(final Point3D query, final DSU dsu) {
            return nearest(this.root, query, 0, null, Double.POSITIVE_INFINITY, dsu);
        }

        private Candidate nearest(final KDNode node, final Point3D target, final int depth, Point3D best, double bestDist, final DSU dsu) {
            if (node == null) {
                return best == null ? null : new Candidate(target, best, bestDist);
            }

            final double d = distance(node.point, target);
            if (d > 0 && d < bestDist && dsu.find(node.point.id) != dsu.find(target.id)) {
                bestDist = d;
                best = node.point;
            }

            final int axis = node.axis;
            final double diff = getCoord(target, axis) - getCoord(node.point, axis);
            final KDNode near = diff <= 0 ? node.left : node.right;
            final KDNode far = diff <= 0 ? node.right : node.left;

            Candidate cand = nearest(near, target, depth + 1, best, bestDist, dsu);
            if (cand != null) {
                best = cand.b;
                bestDist = cand.dist;
            }

            if (Math.abs(diff) < bestDist) {
                cand = nearest(far, target, depth + 1, best, bestDist, dsu);
                if (cand != null && cand.dist < bestDist) {
                    best = cand.b;
                    bestDist = cand.dist;
                }
            }
            return best == null ? null : new Candidate(target, best, bestDist);
        }

        /* -------------------- DSU (union‑find) -------------------- */
        private static final class DSU {
            private final int[] parent, size;

            DSU(final int n) {
                parent = IntStream.range(0, n).toArray();
                size = new int[n];
                Arrays.fill(size, 1);
            }

            int find(final int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);
                }
                return parent[x];
            }

            boolean union(final int a, final int b) {
                int ra = find(a), rb = find(b);
                if (ra == rb) {
                    return false;
                }
                if (size[ra] < size[rb]) {
                    final int t = ra;
                    ra = rb;
                    rb = t;
                }
                parent[rb] = ra;
                size[ra] += size[rb];
                return true;
            }

            int componentSize(final int x) {
                return size[find(x)];
            }
        }

        /**
         * @param axis 0 → X, 1 → Y, 2 → Z
         */ /* -------------------- KD‑Tree (static, read‑only) -------------------- */
        private record KDNode(Point3D point, KDNode left, KDNode right, int axis) {
        }

        private record Candidate(Point3D a, Point3D b, double dist) {
        }
    }
}

