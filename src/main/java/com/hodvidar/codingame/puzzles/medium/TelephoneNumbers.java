package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/telephone-numbers
 * by Hodvidar
 **/
class TelephoneNumbers {

    public static void main(final String[] args) {
        final TelephoneNumbers s = new TelephoneNumbers();
        s.test();
    }

    private void test() {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        final Node rootNode = new Node(-1);
        for (int i = 0; i < N; i++) {
            final String telephone = in.next();
            final char[] numberChar = telephone.toCharArray();
            final int[] number = new int[numberChar.length];
            for (int j = 0; j < numberChar.length; j++) {
                number[j] = Integer.parseInt("" + numberChar[j]);
            }
            rootNode.addChildren(number, 0, number.length);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        final int answer = rootNode.numberOfChild();


        // The number of elements (referencing a number) stored in the structure.
        System.out.println(answer);
        in.close();
    }

    // ----------------------------- INTERNAL CLASSES ----------------------------------
    class Node {

        private final int value;

        private final List<Node> children;

        public Node(final int v) {
            this.value = v;
            children = new ArrayList<>();
        }

        public int getV() {
            return this.value;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void addChild(final Node child) {
            this.children.add(child);
        }

        public void addChildren(final int[] values, final int start, final int length) {
            if (start == length)
                return;

            final int v = values[start];
            Node child = this.getChildWithValue(v);
            if (child == null) {
                child = new Node(v);
                this.addChild(child);
            }
            child.addChildren(values, start + 1, length);
        }

        public Node getChildWithValue(final int value) {
            for (final Node c : this.children)
                if (c.getV() == value)
                    return c;
            return null;
        }

        public int numberOfChild() {
            int i = this.children.size();
            for (final Node n : this.children)
                i += n.numberOfChild();
            return i;
        }
    }
}

