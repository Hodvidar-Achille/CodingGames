package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Day08 extends AbstractAdventOfCode2025 {

    protected final List<Point3D> points = new ArrayList<>();
    protected int numberOfPairsToBuild = 1;

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
    protected record Point3D(double x, double y, double z, int id) {
    }

    static class ClosestPairsBlocks {

        protected final KDNode root;               // built once in the ctor
        protected final Point3D[] points;          // array indexed by id

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

        private static double distance(final Point3D a, final Point3D b) {
            final double dx = a.x - b.x;
            final double dy = a.y - b.y;
            final double dz = a.z - b.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }

        /**
         * Encode an unordered pair of point‑ids into a single long.
         * The larger id is placed in the high 14 bits (ids < 10 000 → 14 bits are enough).
         * The smaller id occupies the low 14 bits.
         * This guarantees that (a,b) and (b,a) produce the same key.
         */
        private static long pairKey(final int idA, final int idB) {
            final int hi = Math.max(idA, idB);
            final int lo = Math.min(idA, idB);
            return ((long) hi << 14) | lo;          // 28‑bit key fits easily in a long
        }

        /* --------------------------------------------------------------- *
         *  1️⃣  INPUT VALIDATION                                            *
         * --------------------------------------------------------------- */
        private static void validateInput(final int pairs) {
            if (pairs < 1) {
                throw new IllegalArgumentException("invalid X");
            }
        }

        /* --------------------------------------------------------------- *
         *  2️⃣  BUILD INITIAL PRIORITY QUEUE                               *
         * --------------------------------------------------------------- */
        protected static PriorityQueue<Candidate> initQueue(final KDNode root, final Point3D[] points) {
            // Global queue that will hold *all* candidates from all points. ordered by distance min to max
            final PriorityQueue<Candidate> pq = new PriorityQueue<>(Comparator.comparingDouble(c -> c.dist));
            // We keep a Set of already‑seen unordered pair keys to avoid duplicates.
            final Set<Long> seenPairs = new HashSet<>();
            // Could be optimized ?
            final double coefficient = 1.0;
            final int numberOfPairsToCompute = Math.max(1, (int) Math.ceil(points.length * coefficient));
            for (final Point3D p : points) {
                final List<Candidate> candList = allNearestCandidates(root, p, numberOfPairsToCompute);
                for (final Candidate c : candList) {
                    // canonicalise the pair (store only a<b) and deduplicate
                    final long key = pairKey(c.a.id, c.b.id);
                    if (seenPairs.add(key)) {          // add returns false if already present
                        pq.offer(c);
                    }
                }
            }
            return pq;
        }

        /* --------------------------------------------------------------- *
         *  3️⃣  ACCEPT PAIRS                                               *
         * --------------------------------------------------------------- */
        private static void acceptPairs(final PriorityQueue<Candidate> pq, final DSU dsu, final int maxPairs) {
            int accepted = 0;
            while (accepted < maxPairs && !pq.isEmpty()) {
                final Candidate cur = pq.poll();
                dsu.union(cur.a.id, cur.b.id);
                accepted++;
            }
        }

        /* --------------------------------------------------------------- *
         *  5️⃣  GET SIZES OF THE BIGGEST BLOCKS                            *
         * --------------------------------------------------------------- */
        private static int[] biggestBlocks(final Day08.Point3D[] points, final DSU dsu, final int limit) {
            final Map<Integer, Integer> compCount = new HashMap<>();
            for (int i = 0; i < points.length; i++) {
                final int root = dsu.find(i);
                compCount.merge(root, 1, Integer::sum);
            }
            return compCount.values().stream().sorted(Comparator.reverseOrder()).limit(limit).mapToInt(Integer::intValue).toArray();
        }

        /* --------------------------------------------------------------- *
         *  6️⃣  MULTIPLY ARRAY ELEMENTS                                    *
         * --------------------------------------------------------------- */
        private static long multiply(final int[] values) {
            long prod = 1L;
            for (final int v : values)
                prod *= v;
            return prod;
        }

        private static List<Candidate> allNearestCandidates(final KDNode root, final Point3D query, final int k) {
            // a max‑heap that keeps the *k* best (smallest distance) candidates seen so far
            final PriorityQueue<Candidate> maxHeap = new PriorityQueue<>(Comparator.comparingDouble(c -> -c.dist));
            collectKNearest(root, query, 0, maxHeap, k);
            // turn the heap into a sorted list (closest first)
            final List<Candidate> result = new ArrayList<>(maxHeap);
            result.sort(Comparator.comparingDouble(c -> c.dist));
            return result;
        }

        /**
         * Walks the KD‑Tree and keeps the {@code k} closest points to {@code target}
         * in {@code heap}.  The heap is a *max*‑heap, therefore the element with the
         * largest distance among the current best candidates is at the head.
         *
         * @param node   current KD‑Tree node
         * @param target the query point
         * @param depth  current tree depth (used only to decide the splitting axis)
         * @param heap   max‑heap that stores at most {@code k} candidates
         * @param k      maximum number of neighbours we keep
         */
        private static void collectKNearest(final KDNode node, final Point3D target, final int depth, final PriorityQueue<Candidate> heap,
                                            final int k) {
            if (node == null) {
                return;
            }

            final double d = distance(node.point, target);
            if (d > 0) { // ignore the point itself (distance 0)
                if (heap.size() < k) {
                    heap.offer(new Candidate(target, node.point, d));
                } else {
                    assert heap.peek() != null;
                    if (d < heap.peek().dist) {
                        heap.poll(); // discard the farthest
                        heap.offer(new Candidate(target, node.point, d));
                    }
                }
            }

            // ----- decide which side of the splitting plane to explore first -----
            final int axis = node.axis;
            final double diff = getCoord(target, axis) - getCoord(node.point, axis);
            final KDNode near = diff <= 0 ? node.left : node.right;
            final KDNode far = diff <= 0 ? node.right : node.left;

            collectKNearest(near, target, depth + 1, heap, k);

            // If the hypersphere (radius = current worst distance in the heap)
            // intersects the splitting plane, we must also explore the far side.
            final double worstDist = heap.isEmpty() ? Double.POSITIVE_INFINITY : heap.peek().dist;
            if (Math.abs(diff) < worstDist) {
                collectKNearest(far, target, depth + 1, heap, k);
            }
        }

        /* -------------------- public API -------------------- */

        /**
         * Returns the product of the sizes of the blocks obtained after
         * connecting the {@code X} closest pairs (subject to the merging rule).
         *
         * @param numberOfPairsToCreate number of pairs to extract (must be ≤ points.length)
         * @return product of block sizes as a {@code long}
         */
        public long productOfBlockSizes(final int numberOfPairsToCreate, final int numberOfBiggerBlocks) {
            validateInput(numberOfPairsToCreate);
            final DSU dsu = new DSU(this.points.length);
            final PriorityQueue<Candidate> pq = initQueue(this.root, this.points);
            acceptPairs(pq, dsu, numberOfPairsToCreate);
            final int[] biggest = biggestBlocks(this.points, dsu, numberOfBiggerBlocks);
            return multiply(biggest);
        }

        /* -------------------- DSU (union‑find) -------------------- */
        protected static final class DSU {
            private final int[] parent, size;
            private int numberOfDistinctBlocks;

            DSU(final int n) {
                parent = IntStream.range(0, n).toArray();
                size = new int[n];
                Arrays.fill(size, 1);
                this.numberOfDistinctBlocks = n;                 // initially every element is alone
            }

            int find(final int x) {
                if (parent[x] != x) {
                    parent[x] = find(parent[x]);    // path‑compression
                }
                return parent[x];
            }

            /** Returns true if a merge really happened (i.e. the two sets were different). */
            boolean union(final int a, final int b) {
                int ra = find(a), rb = find(b);
                if (ra == rb) {
                    return false;                    // already in the same component
                }
                // union‑by‑size – attach the smaller tree under the larger one
                if (size[ra] < size[rb]) {
                    final int t = ra;
                    ra = rb;
                    rb = t;
                }
                parent[rb] = ra;
                size[ra] += size[rb];
                numberOfDistinctBlocks--;
                return true;
            }

            boolean isFullyUnified() {
                return numberOfDistinctBlocks == 1;
            }
        }
        }

        /**
         * @param axis 0 → X, 1 → Y, 2 → Z
         */ /* -------------------- KD‑Tree (static, read‑only) -------------------- */
        protected record KDNode(Point3D point, KDNode left, KDNode right, int axis) {
        }

    protected record Candidate(Point3D a, Point3D b, double dist) {
    }
}

