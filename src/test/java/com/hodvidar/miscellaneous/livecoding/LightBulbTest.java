package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LightBulbTest {

    @Test
    void test1() {
        final int[] input = new int[]{2, 1, 3, 5, 4};
        assertThat(LigthBulb.numTimesAllBlue(input)).isEqualTo(3);
    }

    @Test
    void test2() {
        final int[] input = new int[]{1};
        assertThat(LigthBulb.numTimesAllBlue(input)).isEqualTo(1);
    }

    @Test
    void test3() {
        final int[] input = new int[]{3, 2, 4, 1, 5};
        assertThat(LigthBulb.numTimesAllBlue(input)).isEqualTo(2);
    }

    @Test
    void test4() {
        final int[] input = new int[]{4, 1, 2, 3};
        assertThat(LigthBulb.numTimesAllBlue(input)).isEqualTo(1);
    }
}