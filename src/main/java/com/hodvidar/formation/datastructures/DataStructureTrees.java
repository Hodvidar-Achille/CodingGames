package com.hodvidar.formation.datastructures;

import java.util.ArrayList;
import java.util.Collection;

public class DataStructureTrees {

    public class TreeNode<V> {
        private final Collection<TreeNode<V>> children;
        private final V value;

        public TreeNode(final V value) {
            this.value = value;
            children = new ArrayList<>();
        }

        public TreeNode(final V value, final TreeNode<V>... child) {
            this.value = value;
            this.children = new ArrayList<>();
            for (final TreeNode<V> c : child) {
                this.children.add(c);
            }
        }

        public void addChild(final TreeNode<V> treeNode) {
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
