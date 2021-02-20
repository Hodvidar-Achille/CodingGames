package com.hodvidar.primers;

import static com.hodvidar.primers.artoptimal.ArtOptimal.fillMap;
import static com.hodvidar.primers.artoptimal.ArtOptimal.getInstructions;
import static com.hodvidar.primers.artoptimal.ArtOptimal.getScanner;
import static com.hodvidar.primers.artoptimal.ArtOptimal.getPixelMap;
import static com.hodvidar.primers.artoptimal.ArtOptimal.writeInstructions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Check ArtOptimal")
public class ArtOptimalTest {

	@DisplayName("Checks for 1 pixel to paint in 0 0")
	@Test
	void should_paint_one_pixel_in_0_0() {
		final boolean[][] pixelMap = new boolean[1][1];
		pixelMap[0][0] = true;
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(1, instructions.size());
		final String instruction = instructions.get(0);
		Assertions.assertEquals("FILL,0,0,1", instruction);
	}

	@DisplayName("Checks for 1 pixel to paint in 1 1")
	@Test
	void should_paint_one_pixel_in_1_1() {
		final boolean[][] pixelMap = new boolean[2][2];
		pixelMap[1][1] = true;
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(1, instructions.size());
		final String instruction = instructions.get(0);
		Assertions.assertEquals("FILL,1,1,1", instruction);
	}

	@DisplayName("Checks for 4 pixels to paint in 0 0")
	@Test
	void should_paint_4_pixel_in_0_0() {
		final boolean[][] pixelMap = new boolean[2][2];
		fillMap(pixelMap);
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(1, instructions.size());
		final String instruction = instructions.get(0);
		Assertions.assertEquals("FILL,0,0,2", instruction);
	}

	@DisplayName("Checks for 9 pixels to paint in 0 0")
	@Test
	void should_paint_9_pixel_in_0_0() {
		final boolean[][] pixelMap = new boolean[3][3];
		fillMap(pixelMap);
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(1, instructions.size());
		final String instruction = instructions.get(0);
		Assertions.assertEquals("FILL,0,0,3", instruction);
	}

	@DisplayName("Checks for 100 pixels to paint in 0 0")
	@Test
	void should_paint_100_pixel_in_0_0() {
		final boolean[][] pixelMap = new boolean[10][10];
		fillMap(pixelMap);
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(1, instructions.size());
		final String instruction = instructions.get(0);
		Assertions.assertEquals("FILL,0,0,10", instruction);
	}

	@DisplayName("Checks for 3 pixels to paint in 0 0")
	@Test
	void should_paint_3_pixel_in_0_0() {
		final boolean[][] pixelMap = new boolean[2][2];
		fillMap(pixelMap);
		pixelMap[1][1] = false;
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(2, instructions.size());
		Assertions.assertEquals("FILL,0,0,2", instructions.get(0));
		Assertions.assertEquals("ERASE,1,1", instructions.get(1));
	}

	@DisplayName("Checks for input1")
	@Test
	void should_paint_input_in_4_instructions() throws IOException {
		final boolean[][] pixelMap = getPixelMap(getScanner(1));
		final List<String> instructions = getInstructions(pixelMap);
		Assertions.assertEquals(4, instructions.size());
		Assertions.assertEquals("FILL,0,0,3", instructions.get(0));
		Assertions.assertEquals("FILL,5,0,1", instructions.get(1));
		Assertions.assertEquals("FILL,3,3,2", instructions.get(2));
		Assertions.assertEquals("ERASE,4,3", instructions.get(3));
		writeInstructions(instructions);
	}

	@Test
	void score_for_big_input_file() throws IOException {
		final boolean[][] pixelMap = getPixelMap(getScanner(2));
		final List<String> instructions = getInstructions(pixelMap);
		System.out.println("For file 600*800 score is '"+instructions.size()+"'");
		writeInstructions(instructions);
	}

}
