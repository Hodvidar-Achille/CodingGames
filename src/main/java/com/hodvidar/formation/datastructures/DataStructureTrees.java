package com.hodvidar.formation.datastructures;

import java.util.ArrayList;
import java.util.Collection;

public class DataStructureTrees {

    public class TreeNode<V> {
        private V value;
        private final Collection<TreeNode<V>> children;

        public TreeNode(V value) {
            this.value = value;
            children = new ArrayList<>();
        }

        public TreeNode(V value, TreeNode<V>... child) {
            this.value = value;
            this.children = new ArrayList<>();
            for (TreeNode<V> c : child) {
                this.children.add(c);
            }
        }

        public void addChild(TreeNode<V> treeNode) {
            this.children.add(treeNode);
        }

        public boolean isLeaf() {
            return this.children == null || this.children.isEmpty();
        }

        public V getValue() {
            return value;
        }
    }

    public class Tree<V> {
        private TreeNode<V> root;
    }

}
