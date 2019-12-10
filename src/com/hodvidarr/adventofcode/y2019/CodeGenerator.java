package com.hodvidarr.adventofcode.y2019;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CodeGenerator
{
	private final int min;
	private final int max;

	public CodeGenerator(int min, int max)
	{
		this.min = min;
		this.max = max;
	}

	public Collection<String> generateUniqueDigitCode(int length)
	{
		if(length - 1 > max - min)
			throw new IllegalStateException("Length of unique digit code " + "cannot be more than the given interval."
					+ " length=" + length + " and interval: min=" + min + " max=" + max);

		List<String> codes = new ArrayList<>();
		generate(length, new ArrayList<>(), codes);
		return codes;
	}

	private void generate(int length, List<Integer> values, List<String> codes)
	{
		for (int i = min; i <= max; i++)
		{
			if(alredyExists(i, values))
				continue;
			if(length == 1)
			{
				codes.add(intToString(i, values));
			}
			else
			{
				List<Integer> valuesBis = new ArrayList<>(values);
				valuesBis.add(i);
				generate(length - 1, valuesBis, codes);
			}
		}
	}

	private boolean alredyExists(int i, List<Integer> values)
	{
		for (int v : values)
		{
			if(i == v)
				return true;
		}
		return false;
	}

	private String intToString(int i, List<Integer> values)
	{
		String s = i + "";
		for (int v : values)
		{
			s += v;
		}
		return s;
	}
}
