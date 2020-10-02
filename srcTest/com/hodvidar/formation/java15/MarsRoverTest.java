package com.hodvidar.formation.java15;

import static com.hodvidar.miscellaneous.livecoding.ClimbingStaircaseCombination.getNumberOfWaysToClimb;
import static com.hodvidar.miscellaneous.livecoding.ClimbingStaircaseCombination.getNumberOfWaysToClimb_optimized;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


// https://kata-log.rocks/mars-rover-kata
public class MarsRoverTest {

	@Test
	void should_startd_at_00N() {
		MarsRover r = new MarsRover();
		assertThat(r.execute("")).isEqualTo("0 0 N");
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			" = 0 0 N",
	})
	void should_return_position_after_using_commandes(String testedCommands, String expectedState) {
		MarsRover r = new MarsRover();
		assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
	}

}
