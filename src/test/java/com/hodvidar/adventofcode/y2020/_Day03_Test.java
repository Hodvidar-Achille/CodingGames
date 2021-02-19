package com.hodvidar.adventofcode.y2020;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class _Day03_Test extends AbstractTestForAdventOfCode {

	public _Day03_Test() {
		super(new _Day03());
	}

	@Override
	protected int getExpectedResult() {
		return 257;
	}

	@ParameterizedTest
	@CsvSource(delimiter = '=', value = {
	        "1 = 7",
	        "2 = 1",
	        "3 = 3"
	})
	void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
		final Scanner sc = getScanner(numberOfTheTest);
		final int result = new _Day03().countTrees(sc, 1, 3);
		assertThat(result).isEqualTo(expectedResult);
	}
}
