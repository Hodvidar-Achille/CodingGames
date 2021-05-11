package com.hodvidar.formation.datastructures;

import com.hodvidar.utils.regex.StringAligner;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private BinaryTreeNode root;

    private static int getNodeRepresentationSizeForLevel(final int maxDepth,
                                                         final int maxNodeRepresentationLength,
                                                         final int levelCounter) {
        return (int) (maxNodeRepresentationLength * Math.pow(2, (maxDepth - levelCounter - 1)));
    }

    private static String getNodeRepresentation(final BinaryTreeNode node) {
        if (node == null) {
            return " {-} ";
        }
        return " {" + node.getValue() + "} ";
    }

    private static List<BinaryTreeNode> generateNextLevelNodes(final List<BinaryTreeNode> currentLevelNodes) {
        return generateNextLevelNodes(currentLevelNodes, true);
    }

    private static List<BinaryTreeNode> generateNextLevelNodes(final List<BinaryTreeNode> currentLevelNodes,
                                                               final boolean keepNull) {
        final List<BinaryTreeNode> nextLevelNodes = new ArrayList<>();
        for (final BinaryTreeNode node : currentLevelNodes) {
            if (node == null) {
                if (keepNull) {
                    nextLevelNodes.add(null);
                    nextLevelNodes.add(null);
                }
                continue;
            }
            if (keepNull || node.getLeft() != null) {
                nextLevelNodes.add(node.getLeft());
            }
            if (keepNull || node.getRight() != null) {
                nextLevelNodes.add(node.getRight());
            }
        }
        return nextLevelNodes;
    }

    private static boolean isEmpty(final List<BinaryTreeNode> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        for (final BinaryTreeNode node : list) {
            if (node != null) {
                return false;
            }
        }
        return true;
    }

    public void insert(final Integer value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
        } else {
            root.insert(value);
        }
    }

    public boolean contains(final Integer value) {
        if (root == null) {
            return false;
        }
        return root.contains(value);
    }

    public int getMaxDepth() {
        if (this.root == null) {
            return 0;
        }
        return root.getMaxDepth();
    }

    // TODO to continue
    @Override
    public String toString() {
        if (this.root == null) {
            return "@null";
        }
        final int maxDepth = this.getMaxDepth();
        if (maxDepth >= 30) {
            return "TOO BIG";
        }
        final List<String> levels = new ArrayList<>();
        final int maxNodeRepresentationLength = getMaxNodeRepresentationLength();
        levels.add(" {" + this.root.getValue() + "} ");
        List<BinaryTreeNode> currentLevelNodes = new ArrayList<>();
        currentLevelNodes.add(this.root);
        List<BinaryTreeNode> nextLevelNodes = generateNextLevelNodes(currentLevelNodes, true);
        int levelCounter = 0;
        while (!isEmpty(nextLevelNodes)) {
            levelCounter++;
            currentLevelNodes = nextLevelNodes;
            String line = "";
            final int nodeRepresentationSizeForLevel = getNodeRepresentationSizeForLevel(maxDepth, maxNodeRepresentationLength, levelCounter);
            for (final BinaryTreeNode node : currentLevelNodes) {
                final String nodeRepresentation;
                nodeRepresentation = StringAligner.center(getNodeRepresentation(node), nodeRepresentationSizeForLevel);
                line += nodeRepresentation;
            }
            levels.add(line);
            nextLevelNodes = generateNextLevelNodes(currentLevelNodes);
        }

        final StringBuilder sb = new StringBuilder((int) (Math.pow(maxDepth, 2) * maxNodeRepresentationLength));
        final int lastLineSize = levels.get(levels.size() - 1).length();
        for (int i = 0; i < levels.size(); i++) {
            sb.append(StringAligner.center(levels.get(i), lastLineSize) + "\n");
        }
        return sb.toString();
    }

    private int getMaxNodeRepresentationLength() {
        int maxLength = 0;
        List<BinaryTreeNode> currentLevelNodes = new ArrayList<>();
        currentLevelNodes.add(this.root);
        List<BinaryTreeNode> nextLevelNodes = generateNextLevelNodes(currentLevelNodes, true);
        while (!isEmpty(nextLevelNodes)) {
            currentLevelNodes = nextLevelNodes;
            for (final BinaryTreeNode node : currentLevelNodes) {
                if (node == null) {
                    continue;
                }
                final int length = (" {" + node.getValue() + "} ").length();
                if (length > maxLength) {
                    maxLength = length;
                }
            }
            nextLevelNodes = generateNextLevelNodes(currentLevelNodes);
        }
        return maxLength;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }


}
