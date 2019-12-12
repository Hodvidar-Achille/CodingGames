package com.hodvidarr.utils.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberExtractor
{

	public static final List<Double> extractNumber(String s)
	{
		List<Double> numbers = new ArrayList<>();
		Pattern p = Pattern.compile("-?\\d+");
		Matcher m = p.matcher(s);
		while (m.find())
		{
			String a = m.group();
			Double b = Double.parseDouble(a);
			numbers.add(b);
		}
		return numbers;
	}
}
