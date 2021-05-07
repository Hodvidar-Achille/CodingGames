package com.hodvidar.formation.java15;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.hodvidar.formation.java15.BowlingGame.ERROR_MESSAGE_GAME_ENDED;
import static com.hodvidar.formation.java15.BowlingGame.ERROR_MESSAGE_NUMBER_PINS_DOWN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// https://kata-log.rocks/bowling-game-kata
public class BowlingGameTest {

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
            "5 | 5 | 0 | 1 | 11",
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

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "10 | 5 | 0 | 0 | 20",
            "10 | 5 | 1 | 0 | 22",
            "10 | 10 | 5 | 1 | 47",
            "10 | 0 | 5 | 0 | 20",
            "10 | 10 | 10 | 10 | 90",
    })
    void score_is_number_of_pins_knocked_down_after_four_roll_with_strike(
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
    void score_after_a_perfect_game() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) {
            game.roll(10);
        }
        game.roll(10);
        assertThat(game.score()).isEqualTo(300);
    }

    @Test
    void tries_more_than_11_strikes() {
        assertThatThrownBy(() -> play12strikes())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);
    }

    @Test
    void score_after_a_poor_game() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }
        assertThat(game.score()).isEqualTo(20);
    }

    @Test
    void tries_more_than_20_rolls() {
        assertThatThrownBy(() -> play21rolls())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);
    }

    @Test
    void score_after_a_game_with_only_spares() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(5);
        }
        game.roll(5);
        assertThat(game.score()).isEqualTo(155);
    }

    @Test
    void tries_more_than_22_rolls_with_spares() {
        assertThatThrownBy(() -> play22spares())
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);
    }

    @Test
    void score_after_a_game_with_only_lucky_spares() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            final int pinsDown = (i % 2) == 0 ? 9 : 1;
            game.roll(pinsDown);
        }
        game.roll(9);
        assertThat(game.score()).isEqualTo(199);
    }

    @Test
    void meets_error_if_wrong_number_range() {
        final BowlingGame game = new BowlingGame();
        assertThatThrownBy(() -> game.roll(11))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_NUMBER_PINS_DOWN);
        assertThatThrownBy(() -> game.roll(-10))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_NUMBER_PINS_DOWN);
    }

    @Test
    void meets_error_if_wrong_number_range_after_second_roll() {
        final BowlingGame game = new BowlingGame();
        game.roll(5);
        assertThatThrownBy(() -> game.roll(6))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_NUMBER_PINS_DOWN);
        assertThatThrownBy(() -> game.roll(-10))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_NUMBER_PINS_DOWN);
    }

    @Test
    void meets_error_if_tries_to_roll_after_ten_frames() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }
        assertThatThrownBy(() -> game.roll(1))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);

    }

    @Test
    void score_after_a_game_with_spare_and_strike() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 14; i++) {
            game.roll(1);
        } // 7
        assertThat(game.score()).isEqualTo(14);
        game.roll(10); // 8
        assertThat(game.score()).isEqualTo(24);
        game.roll(5); // 9a
        assertThat(game.score()).isEqualTo(34);
        game.roll(5); // 9b
        assertThat(game.score()).isEqualTo(44);
        game.roll(9); // 10a
        assertThat(game.score()).isEqualTo(62);
        game.roll(0); // 10b
        assertThat(game.score()).isEqualTo(62);
        // ended
        assertThatThrownBy(() -> game.roll(1))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);
    }

    @Test
    void score_after_a_game_with_spare_and_strike_and_bonus_roll() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 14; i++) {
            game.roll(1);
        } // 7
        assertThat(game.score()).isEqualTo(14);
        game.roll(10); // 8
        assertThat(game.score()).isEqualTo(24);
        game.roll(5); // 9a
        assertThat(game.score()).isEqualTo(34);
        game.roll(5); // 9b
        assertThat(game.score()).isEqualTo(44);
        game.roll(9); // 10a
        assertThat(game.score()).isEqualTo(62);
        game.roll(1); // 10b
        assertThat(game.score()).isEqualTo(63);
        game.roll(7); // 10c
        assertThat(game.score()).isEqualTo(77);
        // ended
        assertThatThrownBy(() -> game.roll(1))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage(ERROR_MESSAGE_GAME_ENDED);
    }

    private void play21rolls() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 21; i++) {
            game.roll(1);
        }
    }

    private void play12strikes() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }
    }

    private void play22spares() {
        final BowlingGame game = new BowlingGame();
        for (int i = 0; i < 22; i++) {
            game.roll(5);
        }
    }
}
