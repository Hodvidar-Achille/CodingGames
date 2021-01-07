package com.hodvidar.adventofcode.y2020;

import java.util.Map;
import java.util.Scanner;

public class _Day07_2 extends _Day07 {
    private static final String CONTAIN = " contain ";
    private static final String EMPTY = "no other bags";
    private static final String GOLDEN = "shiny gold bag";

    // not 10790 too low
    public static void main(String[] args) throws Exception {
        _Day07_2 me = new _Day07_2();
        int result = me.countBagInsideGoldenBag(me.getScanner());
        System.err.println("Expected '12128' - result='" + result + "'");
    }

    @Override
    protected int getDay() {
        return 7;
    }

    public int countBagInsideGoldenBag(Scanner sc) {
        Map<String, Bag> bags = BuildBagMap(sc);
        return countBagInsideBag(bags, GOLDEN);
    }

    @Override
    protected void incorporateBags(Map<String, Bag> bags, Bag currentBag, String[] incorporatedBags) {
        if (incorporatedBags[0].contains(EMPTY)) {
            return;
        }
        // 3 bright white bags, 4 muted yellow bags.
        for (String incorporatedBagName : incorporatedBags) {
            // 3 bright white bags -> bright white bag
            int numberOfChild = extractInteger(incorporatedBagName);
            incorporatedBagName = extractBagName(incorporatedBagName);
            Bag incorporatedBag = getBag(bags, incorporatedBagName);
            currentBag.addChildWithNumber(incorporatedBag, numberOfChild);
        }
    }

    private int extractInteger(String crudeBagName) {
        crudeBagName = crudeBagName.replaceAll("[^0-9]", "");
        return Integer.parseInt(crudeBagName);
    }

    public int countBagInsideBag(Map<String, Bag> bags, String bagName) {
        return bags.get(bagName).countAllChildrenBag();
    }
}
