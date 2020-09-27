package com.hodvidar.utils.number;

public final class ArithmeticServices
{
	public static final boolean isNumeric(String strNum) {
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Greatest Common Divisor
	 * @param a - one number
	 * @param b - one number
	 * @return
	 */
	private static final double gcm_final(double a, double b)
	{
		return b == 0 ? a : gcm_final(b, a % b); // Not bad for one line of code :)
	}

	/**
	 * Greatest Common Divisor
	 * @param a - one number
	 * @param b - several numbers
	 * @return
	 */
	public static final double gcm(double a, double... b)
	{
		if(b == null || b.length == 0)
			throw new IllegalArgumentException("Need at least 2 values.");

		if(b.length == 1)
		{
			return gcm_final(a, b[0]);
		}
		else
		{
			double[] b2 = new double[b.length - 1];
			for (int i = 1; i < b.length; i++)
				b2[i - 1] = b[i];
			return gcm(gcm_final(a, b[0]), b2);
		}
	}

	/**
	 * Lowest Common Multiple
	 * @param a - one number
	 * @param b - one number
	 * @return
	 */
	private static final double lcm_final(double a, double b)
	{
		return (a * b) / gcm_final(a, b);
	}

	/**
	 * Lowest Common Multiple
	 * @param a - one number
	 * @param b - several numbers
	 * @return
	 */
	public static final double lcm(double a, double... b)
	{
		if(b == null || b.length == 0)
			throw new IllegalArgumentException("Need at least 2 values.");

		if(b.length == 1)
		{
			return lcm_final(a, b[0]);
		}
		else
		{
			double[] b2 = new double[b.length - 1];
			for (int i = 1; i < b.length; i++)
				b2[i - 1] = b[i];
			return lcm(lcm_final(a, b[0]), b2);
		}
	}

	public static int max(int... numbers) {
		int max = Integer.MIN_VALUE;
		for(int n : numbers) {
			max = Math.max(max, n);
		}
		return max;
	}

	public static int getFactorial(int aNumber) {
		double factorial = 1;
		for(int i = 1; i <= aNumber; i++) {
			factorial *= i;
		}

		if(factorial > Integer.MAX_VALUE) {
			throw new ArithmeticException("Factorial > Integer.MAX_VALUE");
		}
		return (int) factorial;
	}

	/**
	 * 105! / (100! * 2!) --> calculate this knowing that
	 * 100! cannot be computed (for integers).
	 *
	 * Should become : (105*104*103*102*101 / 2)
	 * @param numerator
	 * @param denominatorsMultiplied
	 * @return
	 */
	// TODO
	public static int getFactorialDivision(int numerator, int... denominatorsMultiplied) {
		throw new UnsupportedOperationException("Not yet impl");
	}
}
