/*
Vous entrez sur une portion de route et vous comptez vous reposer entièrement sur votre régulateur de vitesse pour traverser la zone sans devoir vous arrêter ni ralentir.

L'objectif est de trouver la vitesse maximale (hors excès de vitesse) qui vous permettra de franchir tous les feux au vert.

Attention : Vous ne pouvez pas franchir un feu à la seconde où il passe au rouge !

Votre véhicule entre directement dans la zone à la vitesse programmée sur le régulateur et ce dernier veille à ce qu'elle ne change plus par la suite.
Input
Ligne 1 : Un entier speed pour la vitesse maximale autorisée sur la portion de route (en km/h).

Ligne 2 : Un entier lightCount pour le nombre de feu de circulation sur la route.

lightCount prochaines lignes :
- Un entier distance représentant la distance du feu par rapport au point de départ (en mètre).
- Un entier duration représentant la durée du feu sur chaque couleur.

Un feu alterne une période de duration secondes au vert puis duration secondes au rouge.
Tous les feux passent au vert en même temps dès votre entrée dans la zone.
Output
Ligne 1 : La vitesse entière (km/h) la plus élevée possible qui permet de franchir tous les feux au vert sans commettre d'excès de vitesse.
*/

import java.util.*;
import java.io.*;
import java.math.*;

/**
 *      https://www.codingame.com/ide/puzzle/aneo
 * by Hodvidar. (TODO : finish)
 **/
class Solution
{
	// Autoroute Allemenande :
	//	private static final int maxSpeed = 200;
	//	private static final int lightCount = 6;
	//
	//	private static final int[] distances = new int[] { 1000, 3000, 4000, 5000, 6000, 7000 };
	//	private static final int[] durations = new int[] { 15, 10, 30, 30, 5, 10 };

	// Route de campagne tranquille
	private static final int maxSpeed = 90;
	private static final int lightCount = 3;

	private static final int[] distances = new int[] { 300, 1500, 3000 };
	private static final int[] durations = new int[] { 30, 30, 30 };

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		// int speed = in.nextInt();
		// int lightCount = in.nextInt();

		int speed = Solution.maxSpeed;
		int lightCount = Solution.lightCount;

		System.err.println("speed=" + speed);
		System.err.println("lightCount=" + lightCount);

		SpeedCalculator calculator = new SpeedCalculator(speed);
		int previousDistance = 0;
		for (int i = 0; i < lightCount; i++)
		{
			// int distance = in.nextInt();
			// int duration = in.nextInt();
			int distance = distances[i];
			int duration = durations[i];
			System.err.println("distance=" + distance);
			System.err.println("duration= " + duration);
			int diffDistance = distance - previousDistance;
			RoadSegment segment = new RoadSegment(diffDistance, duration);
			calculator.addRoadSegment(segment);
			previousDistance = distance;
			System.err.println("Possible max speed = " + calculator.getMaxSpeed());
		}

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		System.out.println(calculator.getMaxSpeed());
	}

}

// ============================================================================
// ============================================================================
// ============================================================================

class RoadSegment
{
	public final int distance;
	public final int duration;

	public RoadSegment(int distance, int duration)
	{
		this.distance = distance;
		this.duration = duration;
	}
}

class Interval implements Comparable<Interval>
{
	/**
	 * in meter/s (must be > 0);
	 */
	public final double min;
	/**
	 * in meter/s
	 */
	public final double max;

	public Interval(double min, double max)
	{
		this.min = min;
		this.max = max;
		if (this.min > this.max)
			throw new IllegalArgumentException(
				"The min value of an interval must be less (or equals) than its max value.");
	}

	public boolean isEmpty()
	{
		return 0d == this.min && this.min == this.max;
	}

	public boolean isOverlapping(Interval o)
	{
		if (this.isEmpty() || o.isEmpty())
			return false;

		if (o.min > this.max)
			return false;
		if (o.max < this.min)
			return false;

		return true;
	}

	@Override
	public int compareTo(Interval o)
	{
		if (this.min < o.min)
			return -1;
		if (this.min > o.min)
			return 1;

		// same min
		if (this.max < o.max)
			return -1;
		if (this.max > o.max)
			return 1;
		// same interval
		return 0;
	}

	@Override
	public String toString()
	{
		return "[" + SpeedCalculator.getSpeedInKmPerHour(this.min) + ", "
			+ SpeedCalculator.getSpeedInKmPerHour(this.max) + "]";
	}
}

final class EmptyInterval extends Interval
{
	public EmptyInterval()
	{
		super(0d, 0d);
	}
}

class IntervalHandler
{
	private IntervalHandler()
	{
		// nothing
	}

	private static final EmptyInterval EMPTY = new EmptyInterval();

	/**
	 * [-20, 20] & [-5, 25] --> [-5, 20]. <br/>
	 * [-20, 20] & [21, 25] --> [0, 0] (EMPTY). <br/>
	 * [-20, 20] & [-5, 5] --> [-5, 5]. <br/>
	 * [10, 10] & [-100, 100] --> [0, 0].(EMPTY) <br/>
	 */
	public static Interval getIntervalIntersection(Interval i1, Interval i2)
	{
		if (i1.isEmpty() || i2.isEmpty())
			return EMPTY;
		if (!i1.isOverlapping(i2))
			return EMPTY;

		double min = Math.max(i1.min, i2.min);
		double max = Math.min(i1.max, i2.max);

		if (min == max)
			return EMPTY;

		return new Interval(min, max);
	}

	/**
	 * [-20, 20] & [-5, 25] --> [-20, 25]. <br/>
	 * [-20, 20] & [21, 25] --> [-20, 20] & [21, 25]. <br/>
	 * [-20, 20] & [-5, 5] --> [-20, 20]. <br/>
	 * [10, 10] & [-100, 100] --> [-100, 100]. <br/>
	 */
	public static List<Interval> getIntervalUnion(Interval i1, Interval i2)
	{
		if (i1.isEmpty())
			return toList(i2);
		if (i2.isEmpty())
			return toList(i1);
		if (!i1.isOverlapping(i2))
			return toList(i1, i2);

		double min = Math.min(i1.min, i2.min);
		double max = Math.max(i1.max, i2.max);

		return toList(new Interval(min, max));
	}

	private static List<Interval> toList(Interval... intervals)
	{
		List<Interval> newIntervals = new ArrayList<>();
		for (Interval i : intervals)
			newIntervals.add(i);
		Collections.sort(newIntervals);
		return newIntervals;
	}

	/**
	 * The return List can be only smaller or of the same size of the smallest given list.
	 * Note : both list must be sorted.
	 */
	public static List<Interval> getIntervalIntersections(
		List<Interval> intervals_1,
		List<Interval> intervals_2)
	{
		System.err.println("getIntervalIntersections...");
		if (intervals_1.isEmpty() || intervals_2.isEmpty())
			return Collections.EMPTY_LIST;

		List<Interval> intersectionCollector = new ArrayList<>();

		// Collections.sort(intervals_1);
		// Collections.sort(intervals_2);

		// Could this be optimized ?
		for (Interval i1 : intervals_1)
		{
			for (Interval i2 : intervals_2)
			{
				// Because the list are sorted, if first element is
				// already too high, directly go to next i1.
				if (i1.max < i2.min)
					break;
				// If i2 too little, skip.
				if (i2.max < i1.min)
					continue;
				Interval intersection = getIntervalIntersection(i1, i2);
				// if (!intersection.isEmpty())
				System.err.println("i1" + i1.toString() + " \u2229 i2" + i2.toString() + " --> "
					+ intersection.toString());
				intersectionCollector.add(intersection);
			}
		}

		Collections.sort(intersectionCollector);
		return intersectionCollector;
	}
	/**
	 * TODO : to implement;
	 */
	public static List<Interval> getIntervalUnions(
		List<Interval> intervals_1,
		List<Interval> intervals_2)
	{
		// TODO
		return Collections.EMPTY_LIST;
	}

	/**
	 * Returns true if any interval, in the list, overlap with another.
	 */
	public static boolean isOverlapping(List<Interval> intervals)
	{
		int size = intervals.size();
		if (size == 0)
			return false;

		// Could this be optimized ?
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (j == i)
					continue;
				Interval i1 = intervals.get(i);
				Interval i2 = intervals.get(j);
				if (i1.isOverlapping(i2))
					return true;
			}
		}

		return false;
	}

}

class SpeedCalculator
{
	/**
	 * in meter/s
	 */
	public final double min = getSpeedInMeterPerSecond(1);

	/**
	 * in meter/s
	 */
	public final double max;

	private static final double LIMIT = 0.01;

	private List<Interval> possibleSpeeds = new ArrayList<>();

	public SpeedCalculator(int maximumSpeed)
	{
		System.err.println("NEW SpeedCalculator with max speed: " + maximumSpeed);
		this.max = getSpeedInMeterPerSecond(maximumSpeed);
		Interval firstInterval = new Interval(this.min, this.max);
		this.possibleSpeeds.add(firstInterval);
	}

	public int getMaxSpeed()
	{
		Collections.sort(this.possibleSpeeds);
		int size = this.possibleSpeeds.size();
		if (size == 0)
			return 0;
		Interval higherInterval = this.possibleSpeeds.get(this.possibleSpeeds.size() - 1);
		double maxSpeedInMeterPerSecond = higherInterval.max;
		int maxSpeedInKmPerHour = this.getSpeedInKmPerHour(maxSpeedInMeterPerSecond);
		return maxSpeedInKmPerHour;
	}

	/**
	 * Adapt the possibleSpeed intervals to handle this new segment.
	 */
	public void addRoadSegment(RoadSegment aRoadSegment)
	{
		System.err.println("addRoadSegment(" + aRoadSegment.distance + ", " + aRoadSegment.duration
			+ ")");
		// This could probably be optimized.
		// 1) finds intervals possibles for this segment
		List<Interval> newPossibleSpeeds = this.getIntervalsForRoadSegment(aRoadSegment);
		// 2) find the intersections of these intervals with previous intervals
		this.possibleSpeeds = IntervalHandler.getIntervalIntersections(
			this.possibleSpeeds,
			newPossibleSpeeds);
	}

	private List<Interval> getIntervalsForRoadSegment(RoadSegment aRoadSegment)
	{
		System.err.println("getIntervalsForRoadSegment...");
		// Start to look for possible speeds from the max speed to the minimum speed;
		double distance = aRoadSegment.distance;
		double duration = aRoadSegment.duration;
		double durationIncrement = duration + duration;
		List<Interval> speedIntervalCollector = new ArrayList<>();

		double minSpeedToPass = 0d;
		double maxSpeedToPass = 0d;
		double minDuration = 0d;
		while (true)
		{
			minSpeedToPass = distance / (duration - LIMIT);
			maxSpeedToPass = distance / minDuration;
			if (maxSpeedToPass < this.min)
				break;

			double cappedMinSpeedToPass = Math.max(minSpeedToPass, this.min);
			double cappedMaxSpeedToPass = Math.min(maxSpeedToPass, this.max);

			// Set parameters for next loop.
			duration += durationIncrement;
			minDuration += durationIncrement;
			// Ignore case where minimum speed is already too high.
			if (cappedMinSpeedToPass > cappedMaxSpeedToPass)
			{
				Interval speedInterval = new Interval(minSpeedToPass, maxSpeedToPass);
				System.err.println("Discard illegal speed km/h : " + speedInterval.toString()
					+ " to travel " + distance + " meters in less than "
					+ this.roundTo2Decimals((duration - LIMIT - durationIncrement)) + " seconds.");
				continue;
			}

			// Check if interval is too narrow to be achieved.
			double interval = cappedMaxSpeedToPass - cappedMinSpeedToPass;
			if (interval < this.min)
				break;

			Interval speedInterval = new Interval(cappedMinSpeedToPass, cappedMaxSpeedToPass);
			System.err.println("Possible speed km/h : " + speedInterval.toString() + " to travel "
				+ distance + " meters in between "
				+ this.roundTo2Decimals((minDuration - durationIncrement)) + " and "
				+ this.roundTo2Decimals((duration - LIMIT - durationIncrement)) + " seconds.");
			// Check if empty ?
			speedIntervalCollector.add(speedInterval);
		}

		Collections.sort(speedIntervalCollector);
		return speedIntervalCollector;
	}
	public static double getSpeedInMeterPerSecond(int speedInKmPerHour)
	{
		// Or just / by 3.6d.
		return (((speedInKmPerHour) * 5d) / 18d);
	}

	// risk of losing accuracy with double --> int.
	public static int getSpeedInKmPerHour(double speedInMeterPersecond)
	{
		// Or just * by 3.6d.
		return (int) Math.round(((speedInMeterPersecond) * 18d) / 5d);
	}

	private double roundTo2Decimals(double number)
	{
		return Math.round(number * 100.0) / 100.0;
	}

}
