package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day07_2_Test extends AbstractTestForAdventOfCode {
    @Override
    protected int getDay() {
        return 7;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "4 = 2",
            "5 = 20",
            "3 = 126",
            "6 = 20"
    })
    void shouldFindResultInSmallNumberPool(int numberOfTheTest, int expectedResult) throws FileNotFoundException {
        Scanner sc = getScanner(numberOfTheTest);
        int result = new _Day07_2().countBagInsideGoldenBag(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldFindResultInSmallNumberPool() {
        Bag a1 = new Bag("a1");
        Bag b1 = new Bag("b1");
        Bag b2 = new Bag("b2");
        Bag c1 = new Bag("c1");
        Bag c2 = new Bag("c2");
        Bag c3 = new Bag("c3");
        Bag d1 = new Bag("d1");

        c3.addChildWithNumber(d1, 10);
        b2.addChildWithNumber(c3, 5); // 50
        b2.addChildWithNumber(c2, 100); // 150
        b1.addChildWithNumber(c1, 3); // 3
        a1.addChildWithNumber(b1, 2); // 6
        a1.addChildWithNumber(b2, 10); // 1506

        assertThat(1506).isEqualTo(a1.countAllChildrenBag());
    }

}