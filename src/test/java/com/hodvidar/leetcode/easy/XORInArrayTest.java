package com.hodvidar.leetcode.easy;

import org.junit.jupiter.api.Test;

import static com.hodvidar.leetcode.easy.XORInArray.getBitwiseOfElements;
import static org.assertj.core.api.Assertions.assertThat;

class XORInArrayTest {

    @Test
    void should_return_0_for_list_of_1_element_0() {
        assertThat(getBitwiseOfElements(0, 1)).isEqualTo(0);
    }
}
