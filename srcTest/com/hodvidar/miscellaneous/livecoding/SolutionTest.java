package com.hodvidar.miscellaneous.livecoding;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class SolutionTest {

	@Test
	void do_stuff_return_null() {
		Solution r = new Solution();
		assertThat(r.doStuff(null)).isEqualTo(null);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"0 = 0",
			"1 = 1",
			"2 = 2",
			"3 = 3",
			"4 = 4",
			"5 = 5",
			"6 = 6",
			"7 = 7",
			"8 = 8",
			"9 = 9",
	})
	void do_stuff_return_given_value(int input, int expectedScore) {
		Solution s = new Solution();
		assertThat(s.doStuff(input)).isEqualTo(expectedScore);
	}

	@Test
	void should_throw_error_on_ill_formed_instruction(){
		Solution s = new Solution();
		assertThatThrownBy(() -> s.doStuff(-1))
				.isInstanceOf(UnsupportedOperationException.class)
				.hasMessage("");
	}
}
