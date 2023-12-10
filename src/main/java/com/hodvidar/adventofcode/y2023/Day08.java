package com.hodvidar.adventofcode.y2023;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day08 extends AbstractAdventOfCode2023 {

    protected Network network;

    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        network = getNetwork(sc.nextLine());
        sc.nextLine();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return network.getNumberOfStepsToFindEndNode();
    }

    protected Network getNetwork(final String navigationInstruction) {
        return new Network(navigationInstruction);
    }

    @Override
    protected void digestLine(final String line) {
        final Pattern pattern = Pattern.compile("^(\\w+) = \\((\\w+), (\\w+)\\)$");
        final Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            network.addNode(matcher.group(1), matcher.group(2), matcher.group(3));
        }
    }

    protected class Network {

        public static final String FIRST_NODE = "AAA";
        public static final String END_NODE = "ZZZ";
        protected final char[] navigationInstructions;
        protected final Map<String, Node> nodes = new HashMap<>();

        private Node firstNode;

        protected Network(final String navigationInstruction) {
            this.navigationInstructions = navigationInstruction.toCharArray();
        }

        public void addNode(final String name, final String left, final String right) {
            final var node = new Node(name, left, right);
            if (FIRST_NODE.equals(name)) {
                firstNode = node;
            }
            nodes.put(name, node);
        }

        public double getNumberOfStepsToFindEndNode() {
            return getNumberOfStepsToFindEndNode(firstNode, END_NODE);
        }

        protected double getNumberOfStepsToFindEndNode(final Node firstNode, final String endNodeEnding) {
            int counter = 0;
            int navigationInstructionIndex = 0;
            Node currentNode = firstNode;
            while (!currentNode.name.endsWith(endNodeEnding)) {
                if (navigationInstructionIndex >= navigationInstructions.length) {
                    navigationInstructionIndex = 0;
                }
                currentNode = getNextNode(currentNode, navigationInstructions[navigationInstructionIndex]);
                navigationInstructionIndex++;
                counter++;
            }
            return counter;

        }

        protected Node getNextNode(final Node currentNode, final char navigationInstruction) {
            return switch (navigationInstruction) {
                case 'L' -> nodes.get(currentNode.left);
                case 'R' -> nodes.get(currentNode.right);
                default ->
                        throw new IllegalArgumentException("Unknown navigation instruction : " + navigationInstruction);
            };
        }
    }

    protected class Node {
        public final String name;
        public final String left;
        public final String right;

        public Node(final String name, final String left, final String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }
}
