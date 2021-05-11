package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.hodvidar.miscellaneous.livecoding.ClimbingStaircaseCombination.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ClimbingStaircaseCombinationTest {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1", // 1
            "2 = 1", // 1 1
            "3 = 1", // 1 1 1
            "4 = 1", // 1 1 1 1
            "5 = 1", // 1 1 1 1 1
            "6 = 1", // 1 1 1 ...
            "7 = 1", // 1 1 1 ...
            "8 = 1", // 1 1 1 ...
            "9 = 1", // 1 1 1 ...
            "10 = 1", // 1 1 1 ...
            "20 = 1", // 1 1 1 ...
            "30 = 1", // 1 1 1 ...
            "100 = 1" // 1 1 1 ...
    })
    void should_always_return_1_if_number_of_possible_ways_to_climb_is_1(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(1, testedValue)).isEqualTo(expectedResult);
        assertThat(getNumberOfWaysToClimb_subOptimized(1, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1", // 1
            "2 = 2", // 1 1 | 2
            "3 = 3", // 1 1 1 | 2 1 | 1 2
            "4 = 5", // 1 1 1 1 | 2 2 | 2 1 1 | 1 2 1 | 1 1 2
            "5 = 8", // 1 1 1 1 1
            // | 2 1 1 1 | 1 2 1 1 | 1 1 2 1 | 1 1 1 2
            // | 2 2 1 | 2 1 2 | 1 2 2
            "6 = 13",
            "7 = 21",
            "8 = 34",
            "9 = 55",
            "10 = 89",
            "20 = 10946",
            "30 = 1346269"
    })
    void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_2(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(2, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1", // 1
            "2 = 2", // 1 1 | 2
            "3 = 4", // 1 1 1 | 2 1 | 1 2 | 3
            "4 = 7", // 1 1 1 1 | 2 1 1 | 1 2 1 | 1 1 2 | 2 2 | 3 1 | 1 3
            "5 = 13",// 1 1 1 1 1
            // 2 1 1 1 | 1 2 1 1 | 1 1 2 1 | 1 1 1 2
            // 2 2 1 | 2 1 2 | 2 2 1
            // 3 1 1 | 1 3 1 | 1 1 3
            // 3 2 | 2 3
            "6 = 24",
            "7 = 44",
            "8 = 81",
            "9 = 149",
            "10 = 274",
            "20 = 121415",
            "30 = 53798080"
    })
    void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_3(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(3, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1", // 1
            "2 = 2", // 1 1 | 2
            "3 = 4", // 1 1 1 | 2 1 | 1 2 | 3
            "4 = 8", // 1 1 1 1 | 2 1 1 | 1 2 1 | 1 1 2 | 2 2 | 3 1 | 1 3 | 4
            "5 = 15",// 1 1 1 1 1
            // 2 1 1 1 | 1 2 1 1 | 1 1 2 1 | 1 1 1 2
            // 2 2 1 | 2 1 2 | 2 2 1
            // 3 1 1 | 1 3 1 | 1 1 3
            // 3 2 | 2 3
            // 4 1 | 1 4
            "6 = 29",
            "7 = 56",
            "8 = 108",
            "9 = 208",
            "10 = 401",
            "20 = 283953",
            "30 = 201061985"
    })
    void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_4(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(4, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1", // 1
            "2 = 2", // 1 1 | 2
            "3 = 4", // 1 1 1 | 2 1 | 1 2 | 3
            "4 = 8", // 1 1 1 1 | 2 1 1 | 1 2 1 | 1 1 2 | 2 2 | 3 1 | 1 3 | 4
            "5 = 16",// 1 1 1 1 1
            // 2 1 1 1 | 1 2 1 1 | 1 1 2 1 | 1 1 1 2
            // 2 2 1 | 2 1 2 | 2 2 1
            // 3 1 1 | 1 3 1 | 1 1 3
            // 3 2 | 2 3
            // 4 1 | 1 4
            // 5
            "6 = 31",
            "7 = 61",
            "8 = 120",
            "9 = 236",
            "10 = 464",
            "20 = 400096",
            "30 = 345052351"
    })
    void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_5(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(5, testedValue)).isEqualTo(expectedResult);
        // assertThat(getNumberOfWaysToClimb(3, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 1",
            "2 = 2",
            "3 = 4",
            "4 = 8",
            "5 = 16",
            "6 = 32",
            "7 = 64",
            "8 = 128",
            "9 = 256",
            "10 = 512",
            "20 = 521472",
            "30 = 531372800"
    })
    void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_10(final int testedValue, final int expectedResult) {
        assertThat(getNumberOfWaysToClimb_optimized(10, testedValue)).isEqualTo(expectedResult);
        // assertThat(getNumberOfWaysToClimb(3, testedValue)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 0 = 1", // 1
            "100 = 0 = 1", // 1 1 1 1 1...
            "1 = 1 = 2", // 2 1 | 1 2
            "2 = 1 = 3", // 2 1 1 | 1 2 1 | 1 1 2
            "3 = 1 = 4",
            //"100 = 1 = 101",
            "1 = 2 = 3",
            "2 = 2 = 6",
            "3 = 2 = 10",
            //"100 = 2 = 5151",
            "3 = 3 = 20",
            "4 = 3 = 35",
            //"100 = 3 = 176851",
            "3 = 4 = 35",
            "4 = 4 = 70",
            //"100 = 4 = 4598126",
            "5 = 5 = 252",
            "6 = 5 = 462",
            //"100 = 5 = 96560646"
    })
    void should_return_number_of_possible_substitutons_optimized(final int numberOfOnes, final int numberOfTwos, final int expectedResult) {
        assertThat(getPossibleSubstitutionsFor_optimized(numberOfOnes, numberOfTwos)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 0 = 1", // 1
            "100 = 0 = 1", // 1 1 1 1 1...
            "1 = 1 = 2", // 2 1 | 1 2
            "2 = 1 = 3", // 2 1 1 | 1 2 1 | 1 1 2
            "3 = 1 = 4",
            "100 = 1 = 101",
            "1 = 2 = 3",
            "2 = 2 = 6",
            "3 = 2 = 10",
            "100 = 2 = 5151",
            "3 = 3 = 20",
            "4 = 3 = 35",
            "100 = 3 = 176851",
            "3 = 4 = 35",
            "4 = 4 = 70",
            "100 = 4 = 4598126",
            "5 = 5 = 252",
            "6 = 5 = 462",
            "100 = 5 = 96560646"
    })
    void should_return_number_of_possible_substitutons(final int numberOfOnes, final int numberOfTwos, final int expectedResult) {
        assertThat(getPossibleSubstitutionsFor(numberOfOnes, numberOfTwos)).isEqualTo(expectedResult);
    }
}
