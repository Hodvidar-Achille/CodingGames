package com.hodvidar.utils.number;

import static com.hodvidar.utils.number.ArithmeticServices.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class ArithmeticServicesTests {

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
	void should_return_factorial_of_number(int testedValue, int expectedResult) {
		assertThat(getFactorial(testedValue)).isEqualTo(expectedResult);
	}

	@Test
	void should_throw_exception_if_result_too_high() {
		assertThatThrownBy(() -> getFactorial(100)).isInstanceOf(ArithmeticException.class);
	}

}