package com.hodvidar.adventofcode.y2020;

import java.util.HashMap;
import java.util.Map;

public class Bag {

    public final String name;

    public final Map<Bag, Integer> children = new HashMap<>();

    public Bag(final String name) {
        this.name = name;
    }

    public void addChild(final Bag aBag) {
        this.children.put(aBag, 1);
    }

    public void addChildWithNumber(final Bag aBag, final int numberOfChildrenBag) {
        this.children.put(aBag, numberOfChildrenBag);
    }

    public boolean containsBag(final String bagName) {
        for (final Bag b : this.children.keySet()) {
            if (b.name.equals(bagName)) {
                return true;
            }
            if (b.containsBag(bagName)) {
                return true;
            }
        }
        return false;
    }

    public int countAllChildrenBag() {
        int counter = 0;
        for (final Map.Entry<Bag, Integer> bagAndNumber : this.children.entrySet()) {
            final Bag b = bagAndNumber.getKey();
            final Integer n = bagAndNumber.getValue();
            final int numberOfChildrenInChild = b.countAllChildrenBag();
            counter += n + (n * numberOfChildrenInChild);
        }
        return counter;
    }
}
