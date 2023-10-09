package com.hodvidar.leetcode.medium;

/**
 * <a href="https://leetcode.com/problems/reverse-integer/description/">Leetcode</a>
 * <p>
 * Given a signed 32-bit integer x, return x with its digits reversed. I
 * f reversing x causes the value to go outside the signed 32-bit integer
 * range [-2^31, 2^31 - 1], then return 0. <br/>
 * <p>
 * (-2147483648 to 2147483647)
 *
 * <b>Assume the environment does not allow you to store 64-bit integers
 * (signed or unsigned).</b>
 */
public class ReverseInteger {

    public static int reverse(int x) {
        if (x == Integer.MIN_VALUE) {
            return 0;
        }
        final boolean isNegative = x < 0;
        if (isNegative) {
            x = -x;
        }
        final int reversedInt;
        try {
            reversedInt = Integer.parseInt(
                    new StringBuilder(String.valueOf(x))
                            .reverse()
                            .toString());
        } catch (final NumberFormatException e) {
            return 0;
        }
        if (isNegative) {
            return -reversedInt;
        }
        return reversedInt;
    }
}
