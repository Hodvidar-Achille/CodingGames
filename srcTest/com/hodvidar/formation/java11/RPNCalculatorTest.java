package com.hodvidar.formation.java11;

import static com.hodvidar.formation.java11.RPNCalculator.calculate;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Kata : https://codingdojo.org/kata/RPN/
public class RPNCalculatorTest {

	@ParameterizedTest
	@CsvSource(value = {
			"1", "2", "3", "42"
	})
	void should_evaluate_a_constant(String constant){
		assertThat(calculate(constant)).isEqualTo(Integer.valueOf(constant));
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"2 = 1 1 +",
			"3 = 1 2 +",
			"84 = 42 42 +"
	})
	void should_add_two_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"2 = 3 1 -",
			"18 = 21 3 -",
			"0 = 42 42 -",
			"-10 = 100 110 -"
	})
	void should_subtract_two_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"3 = 3 1 *",
			"100 = 10 10 *",
			"69 = 23 3 *",
			"1500 = 100 15 *"
	})
	void should_multiple_two_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"1 = 1500 1500 /",
			"5 = 50 10 /",
			"21 = 42 2 /",
			"7 = 49 7 /",
			"5 = 52 10 /"
	})
	void should_divide_two_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"6 = 2 2 + 2 +",
			"6 = 1 2 + 3 +",
			"10 = 2 2 + 2 + 2 + 2 +"
	})
	void should_add_more_than_2_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"10 = 20 2 * 20 - 10 -",
			"100 = 100 2 / 50 +",
			"10 = 1000 2 / 2 / 50 + 290 -"
	})
	void should_handle_more_than_2_constants_with_different_operator(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"3 = 9 SQRT",
			"4 = 16 SQRT",
			"10 = 100 SQRT",
			"10 = 110 SQRT",
			"3 = 10 SQRT"
	})
	void should_squareRoot_a_constant(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"12 = 9 SQRT 9 +",
			"-6 = 16 SQRT 10 -",
			"50= 100 SQRT 5 * ",
			"3 = 110 SQRT 3 /",
			"3 = 100 SQRT SQRT",
			"9 = 12 6 * 9 + SQRT",
			"10 = 12 6 * 9 + SQRT 3 / 7 +"
	})
	void should_handle_squareRoot_and_operator(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"15 = 15 MAX",
			"115 = 15 4 42 115 MAX",
			"1000 = 1 10 100 1000 999 MAX"
	})
	void should_find_Max_in_constants(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"15 = 15 MAX MAX",
			"16 = 10 15 MAX 11 16 MAX",
			"0 = 15 4 42 115 MAX 115 - ",
			"100 = 1 10 100 1000 999 MAX 10 /",
			"10 = 1 10 100 1000 999 MAX 10 / SQRT",
			"46 = 10 10 15 MAX 22 16 31 MAX +",
			"15 = 5 7 9 10 MAX +"
	})
	void should_handle_max_and_other_operator(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"68 = 10 10 15 MAX 22 16 31 MAX + 15 16 22 MAX +",
			"-38 = 10 10 15 MAX 22 16 31 MAX - 15 16 22 MAX -",
			"100 = 10 10 150 MAX 22 16 30 MAX / 15 16 20 MAX *",
			"2525 = 10 10 100 MAX 22 16 25 MAX * 15 16 25 MAX +"
	})
	void should_handle_several_max_and_other_operator(int result, String expression){
		assertThat(calculate(expression)).isEqualTo(result);
	}

	@Test
	void should_throws_exception_for_unknown_operator() {
		assertThatThrownBy(() -> calculate("42 42 .")).isInstanceOf(UnsupportedOperationException.class).hasMessageEndingWith(".");
	}

	@ParameterizedTest
	@CsvSource(value = {
			"10 15 + +",
			"10 MAX +",
			"SQRT 10 10 +",
			"10 10 10 -"
	})
	void should_throw_error_on_ill_formed_instruction(String expression){
		assertThatThrownBy(() -> calculate(expression));
	}
}
