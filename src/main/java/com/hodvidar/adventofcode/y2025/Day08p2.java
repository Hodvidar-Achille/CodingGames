package com.hodvidar.adventofcode.y2025;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Day08p2 extends Day08 {

    @Override
    public double getResultDouble(final Scanner sc) {
        points.clear();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        final UniqueBlock cpb = new UniqueBlock(points);
        return cpb.getProductOfLastJunction();
    }

    static class UniqueBlock extends ClosestPairsBlocks {

        public UniqueBlock(final List<Point3D> pts) {
            super(pts);
        }

        private static Candidate acceptPairsUntilFindLastPair(final PriorityQueue<Candidate> pq, final DSU dsu) {
            while (!pq.isEmpty()) {
                final Candidate cur = pq.poll();
                dsu.union(cur.a().id(), cur.b().id());
                if (dsu.isFullyUnified()) {
                    return cur;
                }
            }
            return null;
        }

        public double getProductOfLastJunction() {
            final DSU dsu = new DSU(this.points.length);
            final PriorityQueue<Candidate> pq = initQueue(this.root, this.points);
            final Candidate lastConnection = acceptPairsUntilFindLastPair(pq, dsu);
            if (lastConnection == null) {
                return -42;
            }
            return lastConnection.a().x() * lastConnection.b().x();
        }
    }

}