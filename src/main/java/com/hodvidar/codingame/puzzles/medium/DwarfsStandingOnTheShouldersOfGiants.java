package com.hodvidar.codingame.puzzles.medium;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/dwarfs-standing-on-the-shoulders-of-giants
 * by Hodvidar
 **/
class DwarfsStandingOnTheShouldersOfGiants {

    public static void main(final String[] args) {
        final DwarfsStandingOnTheShouldersOfGiants d = new DwarfsStandingOnTheShouldersOfGiants();
        d.test();
    }

    private void test() {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt(); // the number of relationships of influence
        final Map<Integer, Node> nodes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // Parent
            final int x = in.nextInt(); // a relationship of influence between two people (x influences y)
            if (!nodes.containsKey(x))
                nodes.put(x, new Node(x));
            final Node nodeX = nodes.get(x);

            // Child  
            final int y = in.nextInt();
            if (!nodes.containsKey(y))
                nodes.put(y, new Node(y));
            final Node nodeY = nodes.get(y);

            // connect X with Y as child and Y with X as parent
            nodeX.connect(nodeY);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        // GO !
        Node root = null;
        for (final Node node : nodes.values()) {
            if (node.isRoot()) {
                root = node;
                break;
            }
        }
        root.setLevel(0);

        int depth = 0;
        for (final Node node : nodes.values()) {
            if (node.getLevel() > depth) {
                depth = node.getLevel();
            }
        }

        // The number of people involved in the longest succession of influences
        System.out.println(depth + 1);
        in.close();
    }

    // ---------------------- INTERNAL CLASSES -------------------------------
    class Node {
        private final int value;
        private final List<Node> children;
        private final List<Node> parents;
        private int level;

        public Node(final int x) {
            this.value = x;
            this.children = new ArrayList<>();
            this.parents = new ArrayList<>();
            this.level = -1;
        }

        public void connect(final Node child) {
            this.children.add(child);
            child.getParents().add(this);
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(final int level2) {
            if (level2 > this.level)
                this.level = level2;
            for (final Node child : this.children)
                child.setLevel(level2 + 1);
        }

        public int getValue() {
            return this.value;
        }

        public List<Node> getChildren() {
            return this.children;
        }

        public List<Node> getParents() {
            return this.parents;
        }

        public boolean isLeaf() {
            return this.children.size() == 0;
        }

        public boolean isRoot() {
            return this.parents.size() == 0;
        }
    }
}
