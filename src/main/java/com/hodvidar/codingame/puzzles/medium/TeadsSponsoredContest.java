package com.hodvidar.codingame.puzzles.medium;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/teads-sponsored-contest
 * by Hodvidar
 **/
class TeadsSponsoredContest {

    public static void main(final String[] args) {
        final TeadsSponsoredContest s = new TeadsSponsoredContest();
        s.test();
    }

    private void test() {
        final long t0 = System.currentTimeMillis();
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt(); // the number of adjacency relations
        final Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            final int xi = in.nextInt(); // the ID of a person which is adjacent to yi
            if (!nodes.containsKey(xi))
                nodes.put(xi, new Node(xi));
            final Node nodeX = nodes.get(xi);

            final int yi = in.nextInt(); // the ID of a person which is adjacent to xi
            if (!nodes.containsKey(yi))
                nodes.put(yi, new Node(yi));
            final Node nodeY = nodes.get(yi);

            // connect X with Y as child and Y with X as parent
            // System.err.println("Connect: "+nodeX.getValue()+" with: "+nodeY.getValue());
            nodeX.connect(nodeY);
        }
        final long t1 = System.currentTimeMillis();
        System.err.println("Creation of Nodes finished | took: " + (t1 - t0) + "ms");
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        int result = 0;
        for (; /* no stop */ ; result++) {
            if (nodes.values().size() <= 1) // only one node left
                break;
            final List<Integer> toRemove = new ArrayList<>();
            for (final Node node : new ArrayList<>(nodes.values())) {
                if (node.isLeaf()) {
                    final int v = node.getValue();
                    toRemove.add(v);
                }
            }
            for (final int x : toRemove) {
                final Node node = nodes.get(x);
                node.cut();
                nodes.remove(x);
            }
        }

        final long t2 = System.currentTimeMillis();
        System.err.println("Cutting Leaf of Nodes finished | took: " + (t2 - t1) + "ms");

        System.out.println(result);
        in.close();
    }

    // ---------------------- INTERNAL CLASSES --------------------------

    class Node {
        private final int value;
        private final List<Node> voisins;
        private final Map<Integer, Integer> levels;

        public Node(final int x) {
            this.value = x;
            this.voisins = new ArrayList<>();
            this.levels = new HashMap<>();
        }

        public void connect(final Node child) {
            this.voisins.add(child);
            child.getVoisins().add(this);
        }

        public void cut() {
            for (final Node v : new ArrayList<>(this.voisins)) {
                v.remove(this.value);
                this.remove(v.getValue());
            }
        }

        public void remove(final int value) {
            for (final Node v : new ArrayList<>(this.voisins)) {
                if (v.getValue() == value) {
                    this.voisins.remove(v);
                    break;
                }

            }
        }

        public int getMaxLevel() {
            int max = 0;
            for (final Integer level : this.levels.values()) {
                if (level > max)
                    max = level;
            }
            return max;
        }

        public int getValue() {
            return this.value;
        }

        public List<Node> getVoisins() {
            return this.voisins;
        }

        public boolean isLeaf() {
            return this.voisins.size() == 1;
        }
    }
}

