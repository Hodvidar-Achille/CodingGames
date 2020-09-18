package com.hodvidar.formation.java11;

import static java.util.stream.Collectors.joining;

import java.util.Arrays;


// https://codingdojo.org/kata/RPN/
public class RPNCalculator {

	private static final String PLUS = "+";
	private static final String MINUS = "-";
	private static final String MULTIPLE = "*";
	private static final String DIVIDE = "/";
	private static final String SQRT = "SQRT";
	private static final String MAX = "MAX";

	public int calculate(String s) {
		if(!s.contains(" ")) {
			return Integer.parseInt(s);
		}

		var result = 0;
		var elements = s.split(" ");

		if(s.contains(MAX)) {
			int max = 0;
			for(int i = 0; i < elements.length; i++) {
				if(MAX.equals(elements[i])) {
					if(i == elements.length-1)  {
						return max;
					}
					var remainningExpression = Arrays.stream(elements).skip(i+1).collect(joining(" "));
					return calculate(max + " " + remainningExpression);
					// return max;
				}
				var num = Integer.parseInt(elements[i]);
				if(num > max) {
					max = num;
				}
			}
		}

		if(s.contains(SQRT) && elements[1].equals(SQRT)) {
			var i = Integer.parseInt(elements[0]);
			result = (int) Math.sqrt(i);
			if(elements.length <= 2) {
				return result;
			}
			var remainningExpression = Arrays.stream(elements).skip(2).collect(joining(" "));
			return calculate(result + " " + remainningExpression);
		}

		var i = Integer.parseInt(elements[0]);
		var ii = Integer.parseInt(elements[1]);
		var operator = elements[2];

		switch (operator) {
			case PLUS : result = i + ii; break;
			case MINUS : result = i - ii; break;
			case MULTIPLE : result = i * ii; break;
			case DIVIDE : result = i / ii; break;
			default: throw new UnsupportedOperationException(operator);
		}
		if(elements.length > 3)  {
			var remainningExpression = Arrays.stream(elements).skip(3).collect(joining(" "));
			return calculate(result + " " + remainningExpression);
		}
		return result;
	}
}
