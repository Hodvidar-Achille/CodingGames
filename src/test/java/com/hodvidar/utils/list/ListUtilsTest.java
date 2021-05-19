package com.hodvidar.utils.list;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ListUtilsTest {

    private static final int[] sortedArrayInput = new int[]{-9801, -9216, -8649, -8100, -7569, -7056, -6561, -6084, -5625,
            -5184, -4761, -4356, -3969, -3600, -3249, -2916, -2601, -2304, -2025, -1764, -1521, -1296, -1089, -900,
            -729, -576, -441, -324, -225, -144, -81, -36, -9, 0, 1, 4, 16, 25, 49, 64, 100, 121, 169, 196, 256, 289,
            361, 400, 484, 529, 625, 676, 784, 841, 961, 1024, 1156, 1225, 1369, 1444, 1600, 1681, 1849, 1936, 2116,
            2209, 2401, 2500, 2704, 2809, 3025, 3136, 3364, 3481, 3721, 3844, 4096, 4225, 4489, 4624, 4900, 5041, 5329,
            5476, 5776, 5929, 6241, 6400, 6724, 6889, 7225, 7396, 7744, 7921, 8281, 8464, 8836, 9025, 9409, 9604};

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1225 | 57",
            "-10000 | -1",
            "9603 | -1",
            "9604 | 99",
            "-9801 | 0",
            "-9216 | 1",
            "0 | 33"
    })
    public void binarySearch(final int inputValue, final int expectedIndex) {
        Assertions.assertThat(ListUtils.binarySearch(sortedArrayInput, inputValue)).isEqualTo(expectedIndex);
    }
}
