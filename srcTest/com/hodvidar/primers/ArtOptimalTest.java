package com.hodvidar.primers;

import static com.hodvidar.utils.number.ArithmeticServices.isNumeric;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Check ArtOptimal")
public class ArtOptimalTest {

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

}
