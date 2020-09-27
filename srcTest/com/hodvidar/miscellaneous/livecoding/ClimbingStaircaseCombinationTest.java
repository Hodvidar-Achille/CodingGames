package com.hodvidar.miscellaneous.livecoding;

import static com.hodvidar.miscellaneous.livecoding.ClimbingStaircaseCombination.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ClimbingStaircaseCombinationTest {

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"1 = 1",
			"2 = 1",
			"3 = 1",
			"4 = 1",
			"5 = 1",
			"6 = 1",
			"7 = 1",
			"10 = 1",
			"20 = 1",
			"30 = 1",
			"100 = 1"
	})
	void should_always_return_1_if_number_of_possible_ways_to_climb_is_1(int testedValue, int expectedResult) {
		assertThat(getNumberOfWaysToClimb_optimized(1, testedValue)).isEqualTo(expectedResult);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"1 = 1",
			"2 = 2",
			"3 = 3",
			"4 = 5",
			"5 = 8",
			"6 = 13",
			"7 = 21",
			"10 = 89",
			"20 = 10946",
			"30 = 1346269"
	})
	void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_2(int testedValue, int expectedResult) {
		assertThat(getNumberOfWaysToClimb_optimized(2, testedValue)).isEqualTo(expectedResult);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"1 = 1",
			"2 = 2",
			"3 = 3",
			"4 = 6",
			"5 = 11",
			"6 = 20",
			"7 = 37",
			"10 = 230",
			"20 = 101902",
			"30 = 45152016"
	})
	void should_return_fibonaci_suite_if_number_of_possible_ways_to_climb_is_3(int testedValue, int expectedResult) {
		assertThat(getNumberOfWaysToClimb_optimized(3, testedValue)).isEqualTo(expectedResult);
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
	void should_return_number_of_possible_substitutons_optimized(int numberOfOnes, int numberOfTwos, int expectedResult) {
		assertThat(getPossibleSubsitutionsFor_optimized(numberOfOnes, numberOfTwos)).isEqualTo(expectedResult);
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
	void should_return_number_of_possible_substitutons(int numberOfOnes, int numberOfTwos, int expectedResult) {
		assertThat(getPossibleSubsitutionsFor(numberOfOnes, numberOfTwos)).isEqualTo(expectedResult);
	}
}
