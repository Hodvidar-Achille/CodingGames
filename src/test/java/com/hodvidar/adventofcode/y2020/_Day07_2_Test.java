package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day07_2_Test extends AbstractTestForAdventOfCode2020 {

    public _Day07_2_Test() {
        super(new _Day07_2());
    }

    @Override
    protected int getExpectedResult() {
        return 12128;
    }


    /* 5 =
    shiny gold bags contain 2 dark red bags. // 2 * 1 (2) | 2
    dark red bags contain 2 dark orange bags, 2 light orange bags. // + 2*2 + 2*2 (8) |10
    dark orange bags contain 2 dark yellow bags. //
    light orange bags contain 3 yellow bags.
     */
    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "4 = 2",
            "5 = 30",
            "3 = 126",
            "6 = 42"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = new _Day07_2().countBagInsideGoldenBag(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldFindResultInSmallNumberPool() {
        final Bag a1 = new Bag("a1");
        final Bag b1 = new Bag("b1");
        final Bag b2 = new Bag("b2");
        final Bag c1 = new Bag("c1");
        final Bag c2 = new Bag("c2");
        final Bag c3 = new Bag("c3");
        final Bag d1 = new Bag("d1");

        // a1   ( 2 + 6 + 10 + 1000 + 50 + 5000 = 6068)
        // 2 b1      (--> 2 b1)
        //   3 c1    (--> 6 c1)
        // 10 b2     (--> 10 b2)
        //    100 c2 (--> 1000 c2)
        //    5 c3   (--> 50 c3)
        //      100 d1 (--> 5000 d1)

        c3.addChildWithNumber(d1, 100); // 100 + 100*0 = 100
        b2.addChildWithNumber(c3, 5); // 5 + 100*5 = 505
        b2.addChildWithNumber(c2, 100); // (505) + 100 = 605
        b1.addChildWithNumber(c1, 3); // 3 + 3*0 = 3
        a1.addChildWithNumber(b1, 2); //
        a1.addChildWithNumber(b2, 10); // 1506

        assertThat(6068).isEqualTo(a1.countAllChildrenBag());
    }

    @Test
    void shouldCountBagWithOneChild() {
        final Bag a1 = new Bag("a1");
        final Bag b1 = new Bag("b1");
        final Bag c1 = new Bag("c1");
        final Bag d1 = new Bag("d1");

        c1.addChild(d1); // 1
        b1.addChild(c1); // 1 + 1 = 2
        a1.addChild(b1); // 2 +1 = 3

        assertThat(3).isEqualTo(a1.countAllChildrenBag());
    }

    @Test
    void shouldCountBagWithOneOrTwoChildren() {
        final Bag a1 = new Bag("a1");
        final Bag b1 = new Bag("b1");
        final Bag c1 = new Bag("c1");
        final Bag d1 = new Bag("d1");
        final Bag c2 = new Bag("c2");
        final Bag d2 = new Bag("d2");

        c1.addChild(d1); // 1
        b1.addChild(c1); // 1 + 1 = 2
        a1.addChild(b1); // 2 + 1 = 3
        b1.addChildWithNumber(c2, 2); // +2 = 5
        c2.addChild(d2); // +1 * 2 = 7

        assertThat(7).isEqualTo(a1.countAllChildrenBag());
    }

}