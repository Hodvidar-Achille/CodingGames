package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Passed
 *
 *
 * @author Hodvidar
 *
 */
public final class _Day01 extends AbstractAdventOfCode
{
	@Override
	protected int getDay() {
		return 1;
	}

	public static void main(String[] args) throws Exception {
		AbstractAdventOfCode me = new _Day01();
		int result = getResult(me.getScanner());
		System.err.println("Expected '388075' - result='" + result + "'");
	}

	public static int getResult(Scanner sc) {
		String line;
		List<Integer> numbers = new ArrayList<>();
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			int number = Integer.parseInt(line);
			if(number >= 2020) {
				continue;
			}
			for(int n : numbers) {
				if( (n + number) == 2020) {
					return n * number;
				}
			}
			numbers.add(number);
		}
		return -1;
	}
}
