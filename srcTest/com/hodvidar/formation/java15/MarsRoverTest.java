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

	@Test
	void should_startd_at_00N_and_support_null_input() {
		MarsRover r = new MarsRover();
		assertThat(r.execute(null)).isEqualTo("0 0 N");
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"L = 0 0 W",
			"LL = 0 0 S",
			"LLL = 0 0 E",
			"LLLL = 0 0 N",
			"LLLLL = 0 0 W"
	})
	void should_turn_left(String testedCommands, String expectedState) {
		MarsRover r = new MarsRover();
		assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"R = 0 0 E",
			"RR = 0 0 S",
			"RRR = 0 0 W",
			"RRRR = 0 0 N",
			"RRRRR = 0 0 E"
	})
	void should_turn_right(String testedCommands, String expectedState) {
		MarsRover r = new MarsRover();
		assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"LRLRLR = 0 0 N",
			"RRRLLL= 0 0 N",
			"RRRRLLL = 0 0 E",
			"LLLLRRR = 0 0 W"
	})
	void should_turn(String testedCommands, String expectedState) {
		MarsRover r = new MarsRover();
		assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
			"F = 0 1 N"
	})
	void should_moveForward(String testedCommands, String expectedState) {
		MarsRover r = new MarsRover();
		assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
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
