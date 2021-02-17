package com.hodvidar.utils.number;

import static com.hodvidar.utils.number.ArithmeticServices.getFactorial;
import static com.hodvidar.utils.number.ArithmeticServices.getFactorialDivision;
import static com.hodvidar.utils.number.ArithmeticServices.greatestCommonDivisor;
import static com.hodvidar.utils.number.ArithmeticServices.isNumeric;
import static com.hodvidar.utils.number.ArithmeticServices.lowerCommonMultiplier;
import static com.hodvidar.utils.number.ArithmeticServices.max;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Check ArithmeticServices")
public class ArithmeticServicesTests {

	@DisplayName("Checks isNumeric")
	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
	        " = false",
	        "1 = true",
	        "10 = true",
	        "100_1 = false",
	        "trois = false",
	        "one = false",
	        "Hodvidar = false",
	        "258975555 = true"
	})
	void should_find_if_string_is_numeric(final String testedValue1, final boolean expectedResult) {
		assertThat(isNumeric(testedValue1)).isEqualTo(expectedResult);
	}

	@Test
	void should_gcd_find_exception_if_1_parameter() {
		assertThatThrownBy(() -> greatestCommonDivisor(1)).isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("Checks greater common divisor with 4 parameters")
	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "1 | 1 | 1 | 1 | 1",
	        "1000 | 100 | 100 | 10 | 10",
	        "120 | 30 | 60 | 90 | 30",
	        "1000 | 150 | 50 | 25 | 25",
	        "179854 | 14567 | 141 | 145 | 1",
	        "555555 | 45 | 175 | 87945 | 5"
	})
	void should_return_greater_common_divisor(
	                                          final double testedValue1, final double testedValue2,
	                                          final double testedValue3, final double testedValue4,
	                                          final double expectedResult) {
		assertThat(greatestCommonDivisor(testedValue1, testedValue2, testedValue3, testedValue4))
		        .isEqualTo(expectedResult);
	}

	@Test
	void should_lcm_find_exception_if_1_parameter() {
		assertThatThrownBy(() -> lowerCommonMultiplier(1)).isInstanceOf(IllegalArgumentException.class);
	}

	@DisplayName("Checks lowest common multiplier with 4 parameters")
	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "1 | 1 | 1 | 1 | 1",
	        "1000 | 100 | 100 | 10 | 1000",
	        "120 | 30 | 60 | 90 | 360",
	        "1000 | 150 | 50 | 25 | 3000",
	        "179854 | 14567 | 141 | 145 | 53564534642010",
	        "555555 | 45 | 175 | 87945 | 341666325"
	})
	void should_return_lowest_common_divisor(
	                                         final double testedValue1, final double testedValue2,
	                                         final double testedValue3, final double testedValue4,
	                                         final double expectedResult) {
		assertThat(lowerCommonMultiplier(testedValue1, testedValue2, testedValue3, testedValue4))
		        .isEqualTo(expectedResult);
	}

	@Test
	void should_find_max() {
		assertThat(max(278975615, 100, 1, -227892616, 0, 15789))
		        .isEqualTo(278975615);
	}

	@DisplayName("Checks getFactorial")
	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
	        "1 = 1",
	        "2 = 2",
	        "3 = 6",
	        "4 = 24",
	        "5 = 120",
	        "6 = 720",
	        "7 = 5040",
	        "8 = 40320",
	        "9 = 362880",
	        "10 = 3628800"
	})
	void should_return_factorial_of_number(final int testedValue, final int expectedResult) {
		assertThat(getFactorial(testedValue)).isEqualTo(expectedResult);
	}

	@DisplayName("Checks getFactorial for a number too high")
	@Test
	void should_throw_exception_if_result_too_high() {
		assertThatThrownBy(() -> getFactorial(100)).isInstanceOf(ArithmeticException.class);
	}

	@DisplayName("Checks getFactorial")
	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "10 | 9 | 1 | 10",
	})
	void should_return_result_from_1_factorial_divided_by_2_factorials(
	                                                                   final int nominatorFactorial,
	                                                                   final int denominatorFactorial1,
	                                                                   final int denominatorFactorial2,
	                                                                   final int expectedResult) {
		assertThat(getFactorialDivision(nominatorFactorial,
		                                denominatorFactorial1,
		                                denominatorFactorial2))
		                                        .isEqualTo(expectedResult);
	}
}