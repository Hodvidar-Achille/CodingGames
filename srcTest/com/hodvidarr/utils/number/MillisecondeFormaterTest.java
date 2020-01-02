package com.hodvidarr.utils.number;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MillisecondeFormaterTest
{
	@Test
	public void checkTime()
	{
		String r = MillisecondeFormater.asTime(999);
		Assert.assertEquals("0 hour(s) 0 minute(s) 0 seconde(s) 999 ms", r);

		r = MillisecondeFormater.asTime(59999);
		Assert.assertEquals("0 hour(s) 0 minute(s) 59 seconde(s) 999 ms", r);

		r = MillisecondeFormater.asTime(3599999);
		Assert.assertEquals("0 hour(s) 59 minute(s) 59 seconde(s) 999 ms", r);

		r = MillisecondeFormater.asTime(3600000);
		Assert.assertEquals("1 hour(s) 0 minute(s) 0 seconde(s) 0 ms", r);

		r = MillisecondeFormater.asTime(10799999);
		Assert.assertEquals("2 hour(s) 59 minute(s) 59 seconde(s) 999 ms", r);
	}
}
