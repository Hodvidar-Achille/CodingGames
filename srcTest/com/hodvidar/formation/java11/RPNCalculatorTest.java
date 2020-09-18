package com.hodvidar.formation.java11;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class RPNCalculatorTest {

	@Test
	void _1_return_1_() {
		RPNCalculator r = new RPNCalculator();
		Assert.assertEquals(1, r.rpnCalculator("1"));
	}

	@Test
	void _2_return_2_() {
		RPNCalculator r = new RPNCalculator();
		Assert.assertEquals(2, r.rpnCalculator("2"));
	}

	@Test
	void _42_return_42_() {
		RPNCalculator r = new RPNCalculator();
		Assert.assertEquals(42, r.rpnCalculator("42"));
	}

	@Test
	void _1_1_plus_return_2_() {
		RPNCalculator r = new RPNCalculator();
		Assert.assertEquals(3, r.rpnCalculator("1 1 +"));
	}
}
