package com.hodvidarr.utils.number;

import java.text.NumberFormat;

public final class DoubleFormater
{
	public static final String asInteger(double d)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setGroupingUsed(false);
		return nf.format(d);
	}
}
