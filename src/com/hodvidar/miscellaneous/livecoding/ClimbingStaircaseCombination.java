package com.hodvidar.miscellaneous.livecoding;

import static com.hodvidar.utils.number.ArithmeticServices.*;

/**
 * Starcase
 * Monter 1 marche ou 2 jusqu'à n marches.
 */
public class ClimbingStaircaseCombination {

	/**
	 * Optimized
	 *
	 * @param maxStepInOneTime - can be do 1 by 1, 2 by 2, 3 by 3 ?
	 * @param numberOfStep     - maximum number of step in the staircase
	 * @return
	 */
	public static int getNumberOfWaysToClimb_optimized(int maxStepInOneTime, int numberOfStep) {
		if (maxStepInOneTime == 1) {
			return 1;
		}

		if (numberOfStep <= maxStepInOneTime) {
			return numberOfStep;
		}
		int counter = 0;
		for (int i = 1; i <= maxStepInOneTime; i++) {
			counter += getNumberOfWaysToClimb_optimized(maxStepInOneTime, numberOfStep - i);
		}
		return counter;

	}

	/**
	 * Returns
	 *
	 * @param numberOfStep
	 * @return
	 */
	public static int getNumberOfWaysToClimb(int maxStepInOneTime, int numberOfStep) {
		if (maxStepInOneTime == 1) {
			return 1;
		}

		if (maxStepInOneTime != 2) {
			throw new UnsupportedOperationException("Not yet impl");
		}

		return 1;
		// 5 --> (1 + 4 + 3) = 8
		// 5x1 0x2 --> +1
		// 1 1 1 1 1

		// 3x1 1x2 --> +4
		// 1 1 1 2
		// 1 1 2 1
		// 1 2 1 1
		// 2 1 1 1

		// 1x1 2x2 --> +3
		// 2 2 1
		// 1 2 1
		// 1 2 2

		// 6 --> (1 + 5 + 3 + 1) = 13
		// 6x1  0x2 --> +1
		// 1 1 1 1 1 1

		// 4x1 1x2 --> +5
		// 2 1 1 1 1
		// 1 2 1 1 1
		// 1 1 2 1 1
		// 1 1 1 2 1
		// 1 1 1 1 2

		// 2x1 2x2 --> +3
		// 2 2 1
		// 2 1 2
		// 1 2 2

		// 0x1 3x2 --> +1
		// 2 2 2

		// 8
		// 4x 1 et 2x 2
		// 2 2 1 1 1 1
		// 2 1 2 1 1 1
	}

	public static int getPossibleSubsitutionsFor_optimized(int... numberOfNextElement) {
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

	public static int getPossibleSubsitutionsFor(int... numberOfNextElement) {
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
			counter += getPossibleSubsitutionsFor(numberOfOnes - i, numberOfTwos - 1);
		}
		return counter;
		//}
		//return 0;

		// 6
		// 1 1 1 1 1 1
		//
	}

}

