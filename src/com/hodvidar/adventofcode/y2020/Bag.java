package com.hodvidar.adventofcode.y2020;

import java.util.HashMap;
import java.util.Map;

public class Bag {

    public final String name;

    public final Map<Bag, Integer> children = new HashMap<>();

    public Bag(String name) {
        this.name = name;
    }

    public void addChild(Bag aBag) {
        this.children.put(aBag, 1);
    }

    public void addChildWithNumber(Bag aBag, int numberOfChildrenBag) {
        this.children.put(aBag, numberOfChildrenBag);
    }

    public boolean containsBag(String bagName) {
        for(Bag b : this.children.keySet()) {
            if(b.name.equals(bagName)) {
                return true;
            }
            if(b.containsBag(bagName)) {
                return true;
            }
        }
        return false;
    }

    public int countAllChildrenBag() {
        int counter = 0;
        for(Map.Entry<Bag, Integer> bagAndNumber : this.children.entrySet()) {
            Bag b = bagAndNumber.getKey();
            Integer n = bagAndNumber.getValue();
            int numberOfChildrenInChild = b.countAllChildrenBag();
            if(numberOfChildrenInChild == 0) {
                counter += n;
            } else {
                counter += n * numberOfChildrenInChild;
            }
        }
        return counter;
    }
}
