package com.hodvidar.codingame.puzzles.medium;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/teads-sponsored-contest
 * by Hodvidar
 **/
class TeadsSponsoredContest {

    public static void main(String[] args) {
        TeadsSponsoredContest s = new TeadsSponsoredContest();
        s.test();
    }

    private void test() {
        long t0 = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of adjacency relations
        Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int xi = in.nextInt(); // the ID of a person which is adjacent to yi
            if (!nodes.containsKey(xi))
                nodes.put(xi, new Node(xi));
            Node nodeX = nodes.get(xi);

            int yi = in.nextInt(); // the ID of a person which is adjacent to xi
            if (!nodes.containsKey(yi))
                nodes.put(yi, new Node(yi));
            Node nodeY = nodes.get(yi);

            // connect X with Y as child and Y with X as parent
            // System.err.println("Connect: "+nodeX.getValue()+" with: "+nodeY.getValue());
            nodeX.connect(nodeY);
        }
        long t1 = System.currentTimeMillis();
        System.err.println("Creation of Nodes finished | took: " + (t1 - t0) + "ms");
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        int result = 0;
        for (; /* no stop */ ; result++) {
            if (nodes.values().size() <= 1) // only one node left
                break;
            List<Integer> toRemove = new ArrayList<>();
            for (Node node : new ArrayList<>(nodes.values())) {
                if (node.isLeaf()) {
                    int v = node.getValue();
                    toRemove.add(v);
                }
            }
            for (int x : toRemove) {
                Node node = nodes.get(x);
                node.cut();
                nodes.remove(x);
            }
        }

        long t2 = System.currentTimeMillis();
        System.err.println("Cutting Leaf of Nodes finished | took: " + (t2 - t1) + "ms");

        System.out.println(result);
        in.close();
    }

    // ---------------------- INTERNAL CLASSES --------------------------

    class Node {
        private final int value;
        private final List<Node> voisins;
        private final Map<Integer, Integer> levels;

        public Node(int x) {
            this.value = x;
            this.voisins = new ArrayList<>();
            this.levels = new HashMap<>();
        }

        public void connect(Node child) {
            this.voisins.add(child);
            child.getVoisins().add(this);
        }

        public void cut() {
            for (Node v : new ArrayList<>(this.voisins)) {
                v.remove(this.value);
                this.remove(v.getValue());
            }
        }

        public void remove(int value) {
            for (Node v : new ArrayList<>(this.voisins)) {
                if (v.getValue() == value) {
                    this.voisins.remove(v);
                    break;
                }

            }
        }

        public int getMaxLevel() {
            int max = 0;
            for (Integer level : this.levels.values()) {
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

