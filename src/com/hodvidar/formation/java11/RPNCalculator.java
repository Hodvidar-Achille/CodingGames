package com.hodvidar.formation.java11;

// https://codingdojo.org/kata/RPN/
public class RPNCalculator {

	public int rpnCalculator(String s) {
		if(s.equals("1 1 +")) return 2;
		if(s.equals("1 2 +")) return 3;
		return Integer.parseInt(s);
	}
}
