package com.hodvidar.adventofcode.y2020;

import com.hodvidar.utils.regex.NumberExtractor;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class _Day07_2 extends _Day07 {

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        return countBagInsideGoldenBag(sc);
    }

    public int countBagInsideGoldenBag(final Scanner sc) {
        final Map<String, Bag> bags = BuildBagMap(sc);
        return countBagInsideBag(bags, GOLDEN);
    }

    @Override
    protected void incorporateBags(final Map<String, Bag> bags, final Bag currentBag, final String[] incorporatedBags) {
        if (incorporatedBags[0].contains(EMPTY)) {
            return;
        }
        // 3 bright white bags, 4 muted yellow bags.
        for (String incorporatedBagName : incorporatedBags) {
            // 3 bright white bags -> bright white bag
            final int numberOfChild = NumberExtractor.extractInteger(incorporatedBagName);
            incorporatedBagName = extractBagName(incorporatedBagName);
            final Bag incorporatedBag = getBag(bags, incorporatedBagName);
            currentBag.addChildWithNumber(incorporatedBag, numberOfChild);
        }
    }

    public int countBagInsideBag(final Map<String, Bag> bags, final String bagName) {
        return bags.get(bagName).countAllChildrenBag();
    }
}
