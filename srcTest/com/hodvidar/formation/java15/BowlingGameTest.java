package com.hodvidar.formation.java15;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// https://kata-log.rocks/bowling-game-kata
public class BowlingGameTest {

	// TODO to continue

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
	void score_is_number_of_pins_knocked_down_after_one_roll(final int pinsKnockedDown, final int expectedScore) {
		final BowlingGame game = new BowlingGame();
		game.roll(pinsKnockedDown);
		assertThat(game.score()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "0 | 0 | 0",
	        "1 | 1 | 2",
	        "2 | 2 | 4",
	        "3 | 3 | 6",
	        "4 | 4 | 8",
	        "5 | 4 | 9",
	        "1 | 4 | 5",
	        "2 | 4 | 6",
	        "3 | 4 | 7",
	        "5 | 4 | 9",
	})
	void score_is_number_of_pins_knocked_down_after_two_roll(
			final int pinsKnockedDown1, final int pinsKnockedDown2,
			final int expectedScore) {
		final BowlingGame game = new BowlingGame();
		game.roll(pinsKnockedDown1);
		game.roll(pinsKnockedDown2);
		assertThat(game.score()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "0 | 0 | 0 | 0 | 0",
	        "1 | 1 | 1 | 1 | 4",
	        "2 | 2 | 1 | 1 | 6",
	        "3 | 3 | 1 | 1 | 8",
	        "4 | 4 | 1 | 1 | 10",
	        "5 | 4 | 1 | 1 | 11",
	        "1 | 4 | 1 | 1 | 7",
	        "2 | 4 | 1 | 1 | 8",
	        "3 | 4 | 1 | 1 | 9",
	        "5 | 4 | 1 | 1 | 11",
	})
	void score_is_number_of_pins_knocked_down_after_four_roll(
			final int pinsKnockedDown1,
			final int pinsKnockedDown2,
			final int pinsKnockedDown3,
			final int pinsKnockedDown4,
			final int expectedScore) {
		final BowlingGame game = new BowlingGame();
		game.roll(pinsKnockedDown1);
		game.roll(pinsKnockedDown2);
		game.roll(pinsKnockedDown3);
		game.roll(pinsKnockedDown4);
		assertThat(game.score()).isEqualTo(expectedScore);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        "5 | 5 | 0 | 0 | 10",
	        "5 | 5 | 1 | 0 | 12",
	        "5 | 5 | 0 | 1 | 12",
	        "5 | 5 | 5 | 0 | 20",
	        "5 | 5 | 0 | 5 | 15",
	})
	void score_is_number_of_pins_knocked_down_after_four_roll_with_spare(
			final int pinsKnockedDown1,
			final int pinsKnockedDown2,
			final int pinsKnockedDown3,
			final int pinsKnockedDown4,
			final int expectedScore) {
		final BowlingGame game = new BowlingGame();
		game.roll(pinsKnockedDown1);
		game.roll(pinsKnockedDown2);
		game.roll(pinsKnockedDown3);
		game.roll(pinsKnockedDown4);
		assertThat(game.score()).isEqualTo(expectedScore);
	}

	@Test
	void meets_error_if_wrong_number_range() {
		final BowlingGame game = new BowlingGame();
		assertThatThrownBy(() -> game.roll(11))
		        .isInstanceOf(UnsupportedOperationException.class)
		        .hasMessage("Wrong number of knocked down pins, "
		                    + "must be between 0 and 10. "
		                    + "It was '" + (11) + "'.");
		assertThatThrownBy(() -> game.roll(-10))
		        .isInstanceOf(UnsupportedOperationException.class)
		        .hasMessage("Wrong number of knocked down pins, "
		                    + "must be between 0 and 10. "
		                    + "It was '" + (-10) + "'.");
	}

	@Test
	void meets_error_if_wrong_number_range_after_second_roll() {
		final BowlingGame game = new BowlingGame();
		game.roll(5);
		assertThatThrownBy(() -> game.roll(6))
		        .isInstanceOf(UnsupportedOperationException.class)
		        .hasMessage("Wrong number of Knocked down pins, "
		                    + "must be between 0 and 10 "
		                    + "was '" + 11 + "'.");
		assertThatThrownBy(() -> game.roll(-10))
		        .isInstanceOf(UnsupportedOperationException.class)
		        .hasMessage("Wrong number of Knocked down pins, "
		                    + "must be between 0 and 10 "
		                    + "was '" + (-10) + "'.");
	}
}
