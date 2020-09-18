package com.hodvidar.formation.java11;

// https://codingdojo.org/kata/RPN/
public class RPNCalculator {

	public int rpnCalculator(String s) {
		if(!s.contains(" ")) {
			return Integer.parseInt(s);
		}

		var elements = s.split(" ");
		return 0;
	}
}
