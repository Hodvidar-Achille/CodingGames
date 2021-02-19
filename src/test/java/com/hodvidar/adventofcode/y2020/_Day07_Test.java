package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day07_Test extends AbstractTestForAdventOfCode {

	public _Day07_Test() {
		super(new _Day07());
	}

	@Override
	protected int getExpectedResult() {
		return 192;
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
	        " bright white bags | bright white bag",
	        " 2 muted yellow bags. | muted yellow bag",
	        "4 muted yellow bags | muted yellow bag",
	        "6 dotted black bags | dotted black bag",
	        "111 green bags. | green bag"
	})
	void checkCountUniqueCharacters(String crudeBagName, String expectedResult) throws FileNotFoundException {
		String result = new _Day07().extractBagName(crudeBagName);
		assertThat(result).isEqualTo(expectedResult);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
	        "1 = 4",
	        "2 = 1"
	})
	void shouldFindResultInSmallNumberPool(int numberOfTheTest, int expectedResult) throws FileNotFoundException {
		Scanner sc = getScanner(numberOfTheTest);
		int result = new _Day07().countBagInsideGoldenBag(sc);
		assertThat(result).isEqualTo(expectedResult);
	}

}