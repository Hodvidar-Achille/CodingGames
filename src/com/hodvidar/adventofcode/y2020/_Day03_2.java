package com.hodvidar.adventofcode.y2020;

public class _Day03_2 extends _Day03 {

    public static void main(String[] args) throws Exception {
        _Day03 me = new _Day03();
        int result1 = me.countTrees(me.getScanner(), 1, 1);
        int result2 = me.countTrees(me.getScanner(), 1, 3);
        int result3 = me.countTrees(me.getScanner(), 1, 5);
        int result4 = me.countTrees(me.getScanner(), 1, 7);
        int result5 = me.countTrees(me.getScanner(), 2, 1);
        int result = result1 * result2 * result3 * result4 * result5;
        System.err.println("Expected '1744787392' - result='" + result + "'");
    }

}
