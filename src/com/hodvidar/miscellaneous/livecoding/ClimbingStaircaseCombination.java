package com.hodvidar.miscellaneous.livecoding;

import static com.hodvidar.utils.number.ArithmeticServices.getFactorial;

/**
 * Starcase
 * Monter 1 marche ou 2 jusqu'à n marches.
 */
public class ClimbingStaircaseCombination {

	/**
	 * Optimized - <i>Algorithm when we already know the results of this problem
	 * follows the Fibonacci suite logic</i>
	 *
	 * @param maxStepInOneTime can be do 1 by 1, 2 by 2, 3 by 3 ? If 3 so 1, 2, 3 are possible
	 * @param numberOfStep     number of steps in the staircase
	 * @return
	 */
	public static int getNumberOfWaysToClimb_optimized(int maxStepInOneTime, int numberOfStep) {
		if (maxStepInOneTime == 1) {
			return 1;
		}
		if (numberOfStep == 1) {
			return 1;
		}
		if (numberOfStep == 2) {
			return 2;
		}
		if (numberOfStep < maxStepInOneTime) {
			return getNumberOfWaysToClimb_optimized(maxStepInOneTime-1, numberOfStep);
		}
		if (numberOfStep == maxStepInOneTime) {
			return getNumberOfWaysToClimb_optimized(maxStepInOneTime-1, numberOfStep) + 1;
		}
		int counter = 0;
		for (int i = 1; i <= maxStepInOneTime; i++) {
			counter += getNumberOfWaysToClimb_optimized(maxStepInOneTime, numberOfStep - i);
		}
		return counter;
	}

	/**
	 * Uses method getPossibleSubstitutionsFor_optimized for each possible
	 *
	 * @param numberOfStep
	 * @return
	 */
	public static int getNumberOfWaysToClimb_subOptimized(int maxStepInOneTime, int numberOfStep) {
		if (maxStepInOneTime == 1) {
			return 1;
		}

		if (maxStepInOneTime != 2) {
			throw new UnsupportedOperationException("Not yet impl");
		}

		return 1;
	}

	/**
	 * Optimized - <i>we already know the results of this problem
	 * follows this equation :
	 * (totalOfElements! / (numberofFirstElement! * numberOfSecondElement! * ...))</i>
	 * <br/>
	 * Example : receiving 3 and 2 --> result equals 5! / (3! * 2!) = 10
	 * @param numberOfNextElement
	 * @return
	 */
	public static int getPossibleSubstitutionsFor_optimized(int... numberOfNextElement) {
		if (numberOfNextElement.length == 1) {
			return 1;
		}

		if (numberOfNextElement.length != 2) {
			throw new UnsupportedOperationException("Not yet impl");
		}

		int numberOfOnes = numberOfNextElement[0];
		int numberOfTwos = numberOfNextElement[1];

		if (numberOfOnes == 0 || numberOfTwos == 0) {
			return 1;
		}

		int totalNumberOfElements = numberOfOnes + numberOfTwos;

		return (
				getFactorial(totalNumberOfElements)
						/ (
						getFactorial(numberOfOnes)
								* getFactorial(numberOfTwos)
				)
		);

	}

	/**
	 * 
	 * @param numberOfNextElement
	 * @return
	 */
	public static int getPossibleSubstitutionsFor(int... numberOfNextElement) {
		if (numberOfNextElement.length == 1) {
			return 1;
		}

		if (numberOfNextElement.length != 2) {
			throw new UnsupportedOperationException("Not yet impl");
		}

		int numberOfOnes = numberOfNextElement[0];
		int numberOfTwos = numberOfNextElement[1];

		if (numberOfOnes == 0 || numberOfTwos == 0) {
			return 1;
		}

		int totalNumberOfElements = numberOfTwos + numberOfOnes;
		/*if(numberOfTwos == 1) {
			return totalNumberOfElements;
		}*/
		//if(numberOfTwos == 2) {
		int counter = 0;
		for (int i = 0; i <= numberOfOnes; i++) {
			counter += getPossibleSubstitutionsFor(numberOfOnes - i, numberOfTwos - 1);
		}
		return counter;
		//}
		//return 0;

		// 6
		// 1 1 1 1 1 1
		//
	}

}

