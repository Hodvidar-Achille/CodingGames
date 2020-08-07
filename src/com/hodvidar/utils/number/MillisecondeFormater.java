package com.hodvidar.utils.number;

public final class MillisecondeFormater
{
	private static final long oneHour = 3600000;
	private static final long oneMinute = 60000;
	private static final long oneSeconde = 1000;

	public static final String asTime(long millisecondes)
	{
		String time = "";
		long hour = Math.floorDiv(millisecondes, oneHour);
		time += hour + " hour(s) ";
		millisecondes -= oneHour * hour;
		long minute = Math.floorDiv(millisecondes, oneMinute);
		time += minute + " minute(s) ";
		millisecondes -= oneMinute * minute;
		long seconde = Math.floorDiv(millisecondes, oneSeconde);
		time += seconde + " seconde(s) ";
		millisecondes -= oneSeconde * seconde;
		time += millisecondes + " ms";
		return time;
	}
}
