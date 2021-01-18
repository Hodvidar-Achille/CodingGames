package com.hodvidar.adventofcode.y2020;

import com.hodvidar.utils.regex.NumberExtractor;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class _Day07_2 extends _Day07 {

    // not 10790 too low
    public static void main(String[] args) throws Exception {
        _Day07_2 me = new _Day07_2();
        int result = me.getResult(me.getScanner());
        System.err.println("Expected '12128' - result='" + result + "'");
    }

    @Override
    protected int getResult(Scanner sc) throws FileNotFoundException {
        return countBagInsideGoldenBag(sc);
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
            int numberOfChild = NumberExtractor.extractInteger(incorporatedBagName);
            incorporatedBagName = extractBagName(incorporatedBagName);
            Bag incorporatedBag = getBag(bags, incorporatedBagName);
            currentBag.addChildWithNumber(incorporatedBag, numberOfChild);
        }
    }

    public int countBagInsideBag(Map<String, Bag> bags, String bagName) {
        return bags.get(bagName).countAllChildrenBag();
    }
}
