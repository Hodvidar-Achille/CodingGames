package com.hodvidar.formation.java15;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

// https://codingdojo.org/kata/Minesweeper/
/*
 * *...
 * ....
 * .*..
 * ....
 * -->
 * *100
 * 2210
 * 1*10
 * 1110
 */
public class MinesweeperTest {

	@Test
	void should_compute_a_1_on_1_grid_without_landmine() {
		checkHintMap("""
 				1 1
 				.
 				""", """
				 0
				 """);
	}

	@Test
	void should_compute_a_1_on_2_grid_without_landmine() {
		checkHintMap("""
 				1 2
 				..
 				""", """
				 00
				 """);
	}

	@Test
	void should_compute_a_1_on_3_grid_without_landmine() {
		checkHintMap("""
 				1 3
 				...
 				""", """
				 000
				 """);
	}

	@Test
	void should_compute_a_2_on_2_grid_without_landmine() {
		checkHintMap("""
 				2 2
 				..
 				..
 				""", """
				 00
				 00
				 """);
	}

	@Test
	void should_compute_a_5_on_4_grid_without_landmine() {
		checkHintMap("""
 				5 4
 				....
 				....
 				....
 				....
 				....
 				""", """
				 0000
				 0000
				 0000
				 0000
				 0000
				 """);
	}

	@Test
	void should_compute_a_1_on_1_grid_with_1_landmine() {
		checkHintMap("""
				1 1
				*
				""", """
				*
				""");
	}

	@Test
	void should_compute_a_1_on_2_grid_with_2_landmines() {
		checkHintMap("""
				1 2
				**
				""", """
				**
				""");
	}

	@Test
	void should_compute_a_2_on_2_grid_with_4_landmines() {
		checkHintMap("""
				2 2
				**
				**
				""",
    """
				**
				**
				""");
	}

	@Test
	void should_compute_a_1_on_2_grid_with_1_landmine1() {
		checkHintMap("""
				1 2
				*.
				""", """
				*1
				""");
	}

	private static void checkHintMap(String grid, String expectedHintMap) {
		var hintMap = Minesweeper.computeHintMap(grid);
		assertThat(hintMap).isEqualTo(
				expectedHintMap);
	}

}
