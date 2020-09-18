package com.hodvidar.formation.java11;

// https://codingdojo.org/kata/RPN/
public class RPNCalculator {

	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String MULTIPLE = "*";
	private static final String DIVIDE = "/";

	public int calculate(String s) {
		if(!s.contains(" ")) {
			return Integer.parseInt(s);
		}

		var elements = s.split(" ");

		var i = Integer.parseInt(elements[0]);
		var ii = Integer.parseInt(elements[1]);
		var iii = elements[2];

		switch (iii) {
			case PLUS : return i + ii;
			case MINUS : return i - ii;
			case MULTIPLE : return i * ii;
			case DIVIDE : return i / ii;
			default: break;
		}
		throw new UnsupportedOperationException("");

	}
}
