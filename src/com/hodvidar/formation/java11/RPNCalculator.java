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

		if(s.contains(MAX)) {
			return handleMax(s);
		}

		var elements = s.split(" ");

		if(s.contains(SQRT) && elements[1].equals(SQRT)) {
			return handleSqrt(elements);
		}

		return handleOperators(elements);
	}

	private Integer handleMax(String s) {
		return handleMax("", s);
	}

	private Integer handleMax(String previousMax, String s) {
		int max = 0;
		var elements = s.split(" ");
		for(int i = 0; i < elements.length; i++) {
			if(MAX.equals(elements[i])) {
				if(i == elements.length-1)  {
					return max;
				}
				if(isThereAnotherMaxFollowedByAnOperator(elements, i)) {
					var remainingExpression = Arrays.stream(elements).skip(i+1).collect(joining(" "));
					return handleMax(String.valueOf(max)+" ", remainingExpression);
				}
				var remainingExpression = Arrays.stream(elements).skip(i+1).collect(joining(" "));
				return calculate(previousMax + max + " " + remainingExpression);
				// return max;
			}
			var num = Integer.parseInt(elements[i]);
			if(num > max) {
				max = num;
			}
		}
		throw new IllegalStateException("Should not happen");
	}

	boolean isThereAnotherMaxFollowedByAnOperator(String[] elements, int currentIndex) {
		for(int i = currentIndex+1; i < elements.length-1; i++) {
			if(isOperationOperator(elements[i])) {
				return false;
			}
			if(MAX.equals(elements[i]) && isOperationOperator(elements[i+1])) {
				return true;
			}
		}
		return false;
	}

	private int handleSqrt(String[] elements) {
		int result;
		var i = Integer.parseInt(elements[0]);
		result = (int) Math.sqrt(i);
		if(elements.length <= 2) {
			return result;
		}
		var remainingExpression = Arrays.stream(elements).skip(2).collect(joining(" "));
		return calculate(result + " " + remainingExpression);
	}

	private int handleOperators(String[] elements) {
		int result;
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
			var remainingExpression = Arrays.stream(elements).skip(3).collect(joining(" "));
			return calculate(result + " " + remainingExpression);
		}
		return result;
	}

	private boolean isOperationOperator(String operator) {
		switch (operator) {
			case PLUS :
			case MINUS :
			case DIVIDE :
			case MULTIPLE :
				return true;
			default: return false;
		}
	}
}
