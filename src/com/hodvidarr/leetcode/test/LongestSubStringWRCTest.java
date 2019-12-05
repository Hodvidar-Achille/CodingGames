package com.hodvidarr.leetcode.test;

import com.hodvidarr.leetcode.LongestSubStringWRC;

public final class LongestSubStringWRCTest
{

	public static void main(String[] args)
	{
		LongestSubStringWRC s = new LongestSubStringWRC();

		String test = "";
		int result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 0, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "a";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 1, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "ab";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 2, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "abc";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 3, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "abcabc";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 3, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "abcdabc";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 4, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		test = "abcdefghijklmnopqurstvwxyz";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 26, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		// add a 'a' after 'm'
		test = "abcdefghijklmanopqurstvwxyz";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 26, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		// add a 'e' after 'o'
		test = "abcdefghijklmanoepqurstvwxyz";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 23, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");

		// add a 'a' again after 'u'
		test = "abcdefghijklmanoepquarstvwxyz";
		result = s.lengthOfLongestSubstring(test);
		checkTrue(result == 15, "failed for check " + test);
		checkTrue(result == s.lengthOfLongestSubstring_Faster(test), "F");
	}

	private static void checkTrue(boolean condition, String messageIfFalse)
	{
		if(!condition)
			System.err.println(messageIfFalse);
	}

}
