package com.hodvidar.adventofcode.y2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {
    private final String name;
    private final Set<Node> children;
    private Integer level;
    private Node parent;

    public Node(final String name) {
        this.name = name;
        this.level = null;
        this.children = new HashSet<>();
    }

    public boolean connectToParent(final Node parent) {
        if (this.parent != null)
            return false;

        this.parent = parent;
        parent.children.add(this);
        return true;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void propagadeLevels(final int level) {
        this.level = level;
        for (final Node child : this.children) {
            child.propagadeLevels(level + 1);
        }
    }

    public Node findCommonAncestor(final Node other) {
        final Map<String, Node> myAncestors = this.getAncestors();

        Node p = other.parent;
        while (p != null) {
            final Node n = myAncestors.get(p.name);
            if (n != null)
                return n;
            p = p.parent;
        }
        return null;
    }

    private Map<String, Node> getAncestors() {
        final Map<String, Node> myAncestors = new HashMap<>();
        Node p = this.parent;
        while (p != null) {
            myAncestors.put(p.getName(), p);
            p = p.parent;
        }
        return myAncestors;
    }

    @Override
    public String toString() {
        final String p = (this.parent == null) ? "@null" : this.parent.getName();
        String c = "[";
        for (final Node child : this.children)
            c += child.getName() + ", ";
        c += "]";
        final String s = "Node '" + this.name + "' parent:'" + p + " children:" + c;
        return s;
    }
}
