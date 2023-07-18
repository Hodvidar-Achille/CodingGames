package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayUnionTest {

    @Test
    void getResultTest1() {
        final int[] input1 = new int[]{7, 10, 15, 19, 23};
        final int[] input2 = new int[]{2, 4, 7, 10, 22, 23};
        final int[] expected = new int[]{7, 10, 23};
        final int[] actual = ArrayUnion.unionOfArraysSimple(input1, input2);
        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void getResultTest2() {
        final int[] input1 = new int[]{1, 2, 2};
        final int[] input2 = new int[]{1, 2, 2, 2};
        final int[] expected = new int[]{1, 2, 2};
        final int[] actual = ArrayUnion.unionOfArraysSimple(input1, input2);
        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void getResultTest1_faster() {
        final int[] input1 = new int[]{7, 10, 15, 19, 23};
        final int[] input2 = new int[]{2, 4, 7, 10, 22, 23};
        final int[] expected = new int[]{7, 10, 23};
        final int[] actual = ArrayUnion.unionOfArraysFaster(input1, input2);
        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void getResultTest2_faster() {
        final int[] input1 = new int[]{1, 2, 2};
        final int[] input2 = new int[]{1, 2, 2, 2};
        final int[] expected = new int[]{1, 2, 2};
        final int[] actual = ArrayUnion.unionOfArraysFaster(input1, input2);
        Assertions.assertArrayEquals(actual, expected);
    }

    @Test
    void getResultTest3_faster() {
        final int[] input1 = new int[]{1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final int[] input2 = new int[]{1, 2, 2, 2, 5, 10, 100, 1000, 10000, 10001, 10002, 10003, 10005, 10006};
        final int[] expected = new int[]{1, 2, 2, 5, 10};
        final int[] actual = ArrayUnion.unionOfArraysFaster(input1, input2);
        Assertions.assertArrayEquals(actual, expected);
    }
}
