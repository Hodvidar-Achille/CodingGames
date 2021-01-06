package com.hodvidar.adventofcode.y2020;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class _Day07 extends AbstractAdventOfCode {
    @Override
    protected int getDay() {
        return 7;
    }

    private static final String CONTAIN = " contain ";
    private static final String EMPTY = "no other bags";
    private static final String GOLDEN = "shiny gold bag";

    public static void main(String[] args) throws Exception {
        _Day07 me = new _Day07();
        int result = me.countBagInsideGoldenBag(me.getScanner());
        System.err.println("Expected '192' - result='" + result + "'");
    }

    public int countBagInsideGoldenBag(Scanner sc) {
        Map<String, Bag> bags = BuildBagMap(sc);
        return countBagsContainingBagName(bags.values(), GOLDEN);
    }

    protected Map<String, Bag> BuildBagMap(Scanner sc) {
        String line;
        Map<String, Bag> bags = new HashMap<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            line = line.trim();
            // "dark orange bags contain 3 bright white bags, 4 muted yellow bags."
            String[] BagCharacteristics = line.split(CONTAIN);
            // dark orange bags
            String bagName = BagCharacteristics[0].trim();
            // remove the 's'
            // dark orange bag
            bagName = bagName.substring(0, bagName.length()-1);
            Bag bag = getBag(bags, bagName);
            String[] incorporatedBags = BagCharacteristics[1].split(",");
            // 3 bright white bags, 4 muted yellow bags.
            incorporateBags(bags, bag, incorporatedBags);
        }
        return bags;
    }

    protected void incorporateBags(Map<String, Bag> bags, Bag currentBag, String[] incorporatedBags) {
        if(incorporatedBags[0].contains(EMPTY)) {
            return;
        }
        // 3 bright white bags, 4 muted yellow bags.
        for(String incorporatedBagName : incorporatedBags) {
            // 3 bright white bags -> bright white bag
            incorporatedBagName = extractBagName(incorporatedBagName);
            Bag incorporatedBag = getBag(bags, incorporatedBagName);
            currentBag.addChild(incorporatedBag);
        }
    }

    /**
     * Ex :  3 bright white bags -> bright white bag
     * @param crudeBagName
     * @return bagName refined
     */
    public String extractBagName(String crudeBagName) {
        crudeBagName = crudeBagName.trim();
        while(Character.isDigit(crudeBagName.charAt(0))) {
            crudeBagName = crudeBagName.substring(1).trim();
        }
        if(crudeBagName.charAt(crudeBagName.length()-1) == '.') {
            crudeBagName = crudeBagName.substring(0, crudeBagName.length()-1);
        }
        if(crudeBagName.charAt(crudeBagName.length()-1) == 's') {
            crudeBagName = crudeBagName.substring(0, crudeBagName.length()-1);
        }
        return crudeBagName;
    }

    protected Bag getBag(Map<String, Bag> bags, String bagName) {
        Bag b;
        if(!bags.containsKey(bagName)) {
           b =  new Bag(bagName);
           bags.put(bagName, b);
        } else {
            b = bags.get(bagName);
        }
        return b;
    }

    public int countBagsContainingBagName(Collection<Bag> bags, String bagName) {
        int counter = 0;
        for(Bag b : bags) {
            if(b.containsBag(bagName)) {
                counter += 1;
            }
        }
        return counter;
    }

}
