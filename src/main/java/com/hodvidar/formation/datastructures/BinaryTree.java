package com.hodvidar.formation.datastructures;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private BinaryTreeNode root;

    public void insert(Integer value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
        } else {
            root.insert(value);
        }
    }

    public boolean contains(Integer value) {
        if (root == null) {
            return false;
        }
        return root.contains(value);
    }

    public int getMaxDepth() {
        if(this.root == null) {
            return 0;
        }
        return root.getMaxDepth();
    }

    // TODO to continue
    @Override
    public String toString() {
        if(this.root == null) {
            return "@null";
        }

        int maxDepth = this.getMaxDepth();
        String representation = "";
        for(int i = 0; i < maxDepth; i++){
            representation+="\t";
        }
        representation += "root="+this.root.getValue();

        List<BinaryTreeNode> currentLevelNodes = new ArrayList<>();
        currentLevelNodes.add(this.root);
        List<BinaryTreeNode> nextLevelNodes = generateNextLevelNodes(currentLevelNodes);
        while(!nextLevelNodes.isEmpty()) {
            representation += "\n";
            maxDepth-=1;
            currentLevelNodes = nextLevelNodes;
            for(int i = 0; i < maxDepth; i++){
                representation+="\t";
            }
            for(BinaryTreeNode node : currentLevelNodes) {
                representation += "n="+node.getValue()+"\t";
            }
            nextLevelNodes = generateNextLevelNodes(currentLevelNodes);
        }
        return representation;
    }

    private static List<BinaryTreeNode> generateNextLevelNodes(List<BinaryTreeNode> currentLevelNodes) {
        List<BinaryTreeNode> nextLevelNodes = new ArrayList<>();
        for(BinaryTreeNode node : currentLevelNodes) {
            if(node.getLeft() != null) {
                nextLevelNodes.add(node.getLeft());
            }
            if(node.getRight() != null) {
                nextLevelNodes.add(node.getRight());
            }
        }
        return nextLevelNodes;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }


}
