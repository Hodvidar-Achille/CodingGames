package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.number.ArithmeticServices;

import java.util.ArrayList;
import java.util.List;

public class Day08p2 extends Day08 {


    @Override
    protected Day08.Network getNetwork(final String navigationInstruction) {
        return new NetworkV2(navigationInstruction);
    }

    private class NetworkV2 extends Network {

        private static final String FIRST_NODE_ENDING = "A";
        private static final String END_NODE_ENDING = "Z";

        private final List<Node> firstNodes = new ArrayList<>();

        private NetworkV2(final String navigationInstruction) {
            super(navigationInstruction);
        }

        @Override
        public void addNode(final String name, final String left, final String right) {
            final var node = new Node(name, left, right);
            if (name.endsWith(FIRST_NODE_ENDING)) {
                firstNodes.add(node);
            }
            nodes.put(name, node);
        }

        @Override
        public double getNumberOfStepsToFindEndNode() {
            final double[] counters = firstNodes.stream()
                    .mapToDouble(n -> this.getNumberOfStepsToFindEndNode(n, END_NODE_ENDING))
                    .toArray();
            final double[] b2 = new double[counters.length - 1];
            System.arraycopy(counters, 1, b2, 0, counters.length - 1);
            return ArithmeticServices.lowerCommonMultiplier(counters[0], b2);
        }
    }
}
