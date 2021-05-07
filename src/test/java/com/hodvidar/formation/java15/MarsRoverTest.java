package com.hodvidar.formation.java15;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

// https://kata-log.rocks/mars-rover-kata
public class MarsRoverTest {

    @Test
    void should_start_at_00N() {
        final MarsRover r = new MarsRover();
        assertThat(r.execute("")).isEqualTo("0 0 N");
    }

    @Test
    void should_start_at_00N_and_support_null_input() {
        final MarsRover r = new MarsRover();
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
    void should_turn_left(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
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
    void should_turn_right(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "LRLRLR = 0 0 N",
            "RRRLLL= 0 0 N",
            "RRRRLLL = 0 0 E",
            "LLLLRRR = 0 0 W"
    })
    void should_turn(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "F = 0 1 N",
            "FF = 0 2 N",
            "FFF = 0 3 N",
            "FFFF = 0 4 N",
            "FFFFFFFFF = 0 9 N", // 9x
            "FFFFFFFFFF = 0 0 N", // 10x
            "FFFFFFFFFFFFF = 0 3 N", // 13x
    })
    void should_moveForward(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "B = 0 9 N",
            "BB = 0 8 N",
            "BBB = 0 7 N",
            "BBBB = 0 6 N",
            "BBBBBBBBB = 0 1 N", // 9x
            "BBBBBBBBBB = 0 0 N", // 10x
            "BBBBBBBBBBBBB = 0 7 N", // 13x
    })
    void should_moveBackward(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "RF = 1 0 E",
            "RFF = 2 0 E",
            "RFFF = 3 0 E",
            "RFFFF = 4 0 E",
            "RFFFFFFFFF = 9 0 E", // 9x
            "RFFFFFFFFFF = 0 0 E", // 10x
            "RFFFFFFFFFFFFF = 3 0 E", // 13x
    })
    void should_moveForward_when_FacingEast(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "RB = 9 0 E",
            "RBB = 8 0 E",
            "RBBB = 7 0 E",
            "RBBBB = 6 0 E",
            "RBBBBBBBBB = 1 0 E", // 9x
            "RBBBBBBBBBB = 0 0 E", // 10x
            "RBBBBBBBBBBBBB = 7 0 E", // 13x
    })
    void should_moveBackward_FacingEast(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "LF = 9 0 W",
            "LFR = 9 0 N",
            "LFRFF = 9 2 N",
            "LFRFFLFF = 7 2 W",
            "LFRFFLFFLBBB = 7 5 S",
    })
    void should_return_position_after_using_commands(final String testedCommands, final String expectedState) {
        final MarsRover r = new MarsRover();
        assertThat(r.execute(testedCommands)).isEqualTo(expectedState);
    }

}
