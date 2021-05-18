package com.hodvidar.formation.interview;

/**
 * https://www.youtube.com/watch?v=7HgsS8bRvjo
 * <p>
 * Universal value tree : (unival)
 * 3       1      2      null                 1
 * / \     / \    / \
 * 3   3   1   1  2   1
 * yes      yes    no      yes (but empty)    yes (all values are equal, even if only one value)
 * 3
 * /   \
 * 3     3
 * / \   / \
 * 3   3 3  3
 * Yes and there are 4 unival of 1x 3, 3 unival of 3x 3 and 1 unival of 7x 3 so total of 8 unival trees
 * --> values in the tree are all equals.
 */
public class UniversalTreeProblem {

    /**
     * Complexity O(n)
     * Returns the number of non -empty universal value trees (tree where value == all values in child nodes, and
     * sub-child to n levels), a leaf count for a universal value tree.
     */
    public static int numberOfNonEmptyUniversalValueTree(final UniversalTreeProblem.Tree<Integer> tree) {
        if (tree == null || tree.root == null) {
            return 0;
        }
        return (int) numberOfNonEmptyUniversalValueTree(tree.root)[0];
    }

    private static Object[] numberOfNonEmptyUniversalValueTree(final UniversalTreeProblem.TreeNode<Integer> node) {
        if (node == null || node.value == null) {
            return new Object[]{0, true};
        }
        int numberOfUnivalTree = 0;
        final Object[] leftResult = numberOfNonEmptyUniversalValueTree(node.left);
        final Object[] rightResult = numberOfNonEmptyUniversalValueTree(node.right);
        numberOfUnivalTree += (int) leftResult[0];
        numberOfUnivalTree += (int) rightResult[0];
        final boolean isLeftUnival = (boolean) leftResult[1];
        final boolean isRightUnival = (boolean) rightResult[1];
        boolean isUnival = isLeftUnival && isRightUnival;
        if (node.left != null && !node.left.value.equals(node.value)) {
            isUnival = false;
        }
        if (node.right != null && !node.right.value.equals(node.value)) {
            isUnival = false;
        }
        if (isUnival) {
            return new Object[]{numberOfUnivalTree + 1, true};
        }
        return new Object[]{numberOfUnivalTree, false};
    }


    public static class TreeNode<V> {
        private final V value;
        private UniversalTreeProblem.TreeNode<V> left;

        private UniversalTreeProblem.TreeNode<V> right;

        public TreeNode(final V value) {
            this.value = value;
        }

        public TreeNode(final V value,
                        final UniversalTreeProblem.TreeNode<V> left,
                        final UniversalTreeProblem.TreeNode<V> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        public V getValue() {
            return value;
        }

        public TreeNode<V> getLeft() {
            return left;
        }

        public void setLeft(final TreeNode<V> left) {
            this.left = left;
        }

        public TreeNode<V> getRight() {
            return right;
        }

        public void setRight(final TreeNode<V> right) {
            this.right = right;
        }
    }

    public static class Tree<V> {
        private final UniversalTreeProblem.TreeNode<V> root;

        public Tree(final UniversalTreeProblem.TreeNode<V> root) {
            this.root = root;
        }

        public UniversalTreeProblem.TreeNode<V> getRoot() {
            return this.root;
        }
    }
}
