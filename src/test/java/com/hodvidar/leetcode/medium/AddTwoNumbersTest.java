package com.hodvidar.leetcode.medium;

import com.hodvidar.leetcode.utils.ListNode;
import com.hodvidar.utils.list.IntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static com.hodvidar.leetcode.utils.ListNodeUtils.getListNode;
import static org.assertj.core.api.Assertions.assertThat;

class AddTwoNumbersTest {

    /**
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8 Explanation: 342 + 465 = 807.
     */
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[2, 4, 3] | [5, 6, 4] | [7, 0, 8]",
            "[2, 4, 3] | [5, 6, 4, 1] | [7, 0, 8, 1]",
            "[2, 4, 3, 1] | [5, 6, 4] | [7, 0, 8, 1]",
            "[2, 4, 3, 1] | [5, 6, 4, 1] | [7, 0, 8, 2]",
            "[2, 4, 3, 1] | [8, 5, 6, 8] | [0, 0, 0, 0, 1]",
            "[0] | [0] | [0]",
            "[9,9,9,9,9,9,9] | [9,9,9,9] | [8,9,9,9,0,0,0,1]"
    })
    void testMedianOfTwoSortedArrays(@ConvertWith(IntegerArrayConverter.class) final int[] nums1,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] nums2,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] expectedNumbers) {
        final ListNode l1 = getListNode(nums1);
        final ListNode l2 = getListNode(nums2);

        final AddTwoNumbers s = new AddTwoNumbers();

        ListNode expectedNode = s.addTwoNumbers(l1, l2);
        for (final int expected : expectedNumbers) {
            assertThat(expectedNode.val).isEqualTo(expected);
            expectedNode = expectedNode.next;
        }
    }
}
