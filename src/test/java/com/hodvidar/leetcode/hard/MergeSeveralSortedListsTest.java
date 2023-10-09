package com.hodvidar.leetcode.hard;

import com.hodvidar.leetcode.utils.ListNode;
import com.hodvidar.utils.list.IntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static com.hodvidar.leetcode.utils.ListNodeUtils.getListNode;
import static org.assertj.core.api.Assertions.assertThat;

class MergeSeveralSortedListsTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[2, 3, 4, 4] | [1, 6, 7] | [0, 0, 0] | [0, 0, 0, 1, 2, 3, 4, 4, 6, 7]",
            "[] | [] | [] | []",
            "[] | [1, 6, 7] | [0, 0, 0] | [0, 0, 0, 1, 6, 7]"
    })
    void testMedianOfTwoSortedArrays(@ConvertWith(IntegerArrayConverter.class) final int[] nums1,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] nums2,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] nums3,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] expectedNumbers) {
        final ListNode l1 = getListNode(nums1);
        final ListNode l2 = getListNode(nums2);
        final ListNode l3 = getListNode(nums3);

        ListNode expectedNode = MergeSeveralSortedLists.mergeKLists(new ListNode[]{l1, l2, l3});
        for (final int expected : expectedNumbers) {
            assertThat(expectedNode.val).isEqualTo(expected);
            expectedNode = expectedNode.next;
        }
    }
}
