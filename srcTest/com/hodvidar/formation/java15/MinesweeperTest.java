package com.hodvidar.formation.java15;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


public class MinesweeperTest {

	@Test
	void should_compute_a_1_on_1_grid_without_mine() {
		var grid = """
 				1 1
 				.
 				""";
		var hintMap = Minesweeper.computeHintMap(grid);
		assertThat(hintMap).isEqualTo(
    			"""
				0
				""");
	}

	@Test
	void should_compute_a_1_on_2_grid_without_mine() {
		var grid = """
 				1 2
 				..
 				""";
		var hintMap = Minesweeper.computeHintMap(grid);
		assertThat(hintMap).isEqualTo(
				"""
				00
				""");
	}

	@Test
	void should_compute_a_2_on_2_grid_without_mine() {
		var grid = """
 				2 2
 				..
 				..
 				""";
		var hintMap = Minesweeper.computeHintMap(grid);
		assertThat(hintMap).isEqualTo(
				"""
				00
				00
				""");
	}
}
