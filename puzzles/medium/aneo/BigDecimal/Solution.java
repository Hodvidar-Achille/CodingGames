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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

enum JeuxDeTest {
	FeuDuVillage(50, 1, new int[] { 200 }, new int[] { 15 }, 50),
	FeuDuVillage2(50, 1, new int[] { 200 }, new int[] { 10 }, 36),
	RouteDeCampagneTranquille(90, 3, new int[] { 300, 1500, 3000 }, new int[] { 30, 30, 30 }, 90),
	RouteDeCampagneMoinsTranquille(90, 3, new int[] { 300, 1500, 3000 }, new int[] { 10, 10, 10 }, 54),
	RouteDeCampagneTranquilleDereglee(90, 3, new int[] { 300, 1500, 3000 }, new int[] { 30, 20, 10 }, 67),
	RouteDeCampagneSTPC(80, 4, new int[] { 700, 2200, 3000, 4000 }, new int[] { 25, 15, 10, 28 }, 49),
	AutorouteAllemande(200, 6, new int[] { 1000, 3000, 4000, 5000, 6000, 7000 }, new int[] { 15,
			10, 30, 30, 5, 10 }, 60),
	PluieDeFeux(130, 100, new int[] { 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000,
			5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000, 10500, 11000, 11500,
			12000, 12500, 13000, 13500, 14000, 14500, 15000, 15500, 16000, 16500, 17000, 17500,
			18000, 18500, 19000, 19500, 20000, 20500, 21000, 21500, 22000, 22500, 23000, 23500,
			24000, 24500, 25000, 25500, 26000, 26500, 27000, 27500, 28000, 28500, 29000, 29500,
			30000, 30500, 31000, 31500, 32000, 32500, 33000, 33500, 34000, 34500, 35000, 35500,
			36000, 36500, 37000, 37500, 38000, 38500, 39000, 39500, 40000, 40500, 41000, 41500,
			42000, 42500, 43000, 43500, 44000, 44500, 45000, 45500, 46000, 46500, 47000, 47500,
			48000, 48500, 49000, 49500, 50000 }, new int[] { 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
			15, 15, 15 }, 60),
	GuirlandeLumineuse(130, 100, new int[] { 1100, 1150, 1200, 1250, 1300, 2100, 2150, 2200, 2250,
			2300, 3100, 3150, 3200, 3250, 3300, 4100, 4150, 4200, 4250, 4300, 5100, 5150, 5200,
			5250, 5300, 6100, 6150, 6200, 6250, 6300, 7100, 7150, 7200, 7250, 7300, 8100, 8150,
			8200, 8250, 8300, 9100, 9150, 9200, 9250, 9300, 10100, 10150, 10200, 10250, 10300,
			11100, 11150, 11200, 11250, 11300, 12100, 12150, 12200, 12250, 12300, 13100, 13150,
			13200, 13250, 13300, 14100, 14150, 14200, 14250, 14300, 15100, 15150, 15200, 15250,
			15300, 16100, 16150, 16200, 16250, 16300, 17100, 17150, 17200, 17250, 17300, 18100,
			18150, 18200, 18250, 18300, 19100, 19150, 19200, 19250, 19300, 20100, 20150, 20200,
			20250, 20300 }, new int[] { 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30,
			10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15,
			20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25,
			30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10,
			15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30 }, 6),
	FeuxRapides(90, 16, new int[] { 1234, 2468, 3702, 6170, 8638, 13574, 16042, 20978, 23446,
			28382, 35786, 38254, 45658, 50594, 53062, 57988 }, new int[] { 5, 5, 5, 5, 5, 5, 5, 5,
			5, 5, 5, 5, 5, 5, 5, 5 }, 74);

	public final int speed;
	public final int lightCount;
	public final int[] distances;
	public final int[] durations;
	public final int result;

	private JeuxDeTest(int speed, int lightCount, int[] distances, int[] durations, int result)
	{
		this.speed = speed;
		this.lightCount = lightCount;
		this.distances = distances;
		this.durations = durations;
		this.result = result;
	}
}

/**
 *      https://www.codingame.com/ide/puzzle/aneo
 * by Hodvidar. (To finish, only 70% of tests pass, at best, because of rounding error...)
 **/
class Solution
{
	/* 
	 * 9/10 tests OK for :  
	 * 5 -> 100
	 * CEILLING or HALF_DOWN or HALF_UP or UP
	 * 0.001 -> 10^-30
	 * 0.001 -> 10^-30
	 * 
	 * 6/10 tests OK for :  
	 * 5 to >
	 * DOWN or FLOOR
	 * 0.001
	 * 0.001
	 * 
	 * RoundingMode.UNNECESSARY --> ArithmeticException
	 */
	
	/**
	 * Number a maximum decimal we will consider. (We use BigDecimal).
	 */
	public static final int DECIMAL_ACCURACY = 100;
	
	public static final RoundingMode ROUNDING_MODE = RoundingMode.CEILING;

	/**
	 * Minimum interval between minimum speed and maximum speed that will be considered. <br/>
	 * (m/s).
	 */
	public static final BigDecimal MINIMUM_INTERVAL = new BigDecimal("0.000000000000000000000000000001");

	/**
	 * Minimum time the car is allow to pass the green light before the red. <br/>
	 * (second).
	 */
	public static final BigDecimal MINIMUM_TIME = new BigDecimal("0.000000000000000000000000000001");;

	private static final boolean TESTING = true;
	private static final boolean VERBOSE = false;

	private static final JeuxDeTest[] tests = new JeuxDeTest[] { JeuxDeTest.FeuDuVillage,
			JeuxDeTest.FeuDuVillage2, JeuxDeTest.RouteDeCampagneTranquille,
			JeuxDeTest.RouteDeCampagneMoinsTranquille,
			JeuxDeTest.RouteDeCampagneTranquilleDereglee, JeuxDeTest.RouteDeCampagneSTPC,
			JeuxDeTest.AutorouteAllemande, JeuxDeTest.PluieDeFeux, JeuxDeTest.GuirlandeLumineuse,
			JeuxDeTest.FeuxRapides };

	public static void main(String args[])
	{
		System.err.println("Solution for: "
				+ " \n -DECIMAL_ACCURACY=" + DECIMAL_ACCURACY
				+ " \n -ROUNDING_MODE=" + ROUNDING_MODE 
				+ " \n -MINIMUM_INTERVAL=" + MINIMUM_INTERVAL 
				+ " \n -MINIMUM_TIME=" + MINIMUM_TIME);

		if (!TESTING)
			doSolution();
		else
		{
			for (JeuxDeTest test : tests)
				doSolution(test);
		}
	}
	private static void doSolution(JeuxDeTest jeuDeTest)
	{
		int speed = jeuDeTest.speed;
		int lightCount = jeuDeTest.lightCount;

		Solution.printIfVerbose("----------- Test '" + jeuDeTest + "' -----------");

		printIfVerbose("speed=" + speed);
		printIfVerbose("lightCount=" + lightCount);

		SpeedCalculator calculator = new SpeedCalculator(speed);
		for (int i = 0; i < lightCount; i++)
		{
			int distance = jeuDeTest.distances[i];
			int duration = jeuDeTest.durations[i];
			printIfVerbose("distance=" + distance);
			printIfVerbose("duration= " + duration);
			RoadSegment segment = new RoadSegment(distance, duration);
			calculator.addRoadSegment(segment);
			printIfVerbose("Possible max speed = " + calculator.getMaxSpeed());
		}

		int expected = jeuDeTest.result;
		int actual = calculator.getMaxSpeed();

		Solution.printIfVerbose("Final result expected : " + expected);
		Solution.printIfVerbose("Final result actual : " + actual);

		if (actual != expected)
			System.err.println("JeuxDeTest '" + jeuDeTest + " expected=" + expected + " actual="
				+ actual + " --> FAILURE");
		else
			Solution.printIfVerbose("JeuxDeTest '" + jeuDeTest + "' expected=" + expected
				+ " actual=" + actual + " -->  SUCCESS");
	}
	public static void printIfVerbose(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}

	private static void doSolution()
	{
		Scanner in = new Scanner(System.in);
		int speed = in.nextInt();
		int lightCount = in.nextInt();

		SpeedCalculator calculator = new SpeedCalculator(speed);
		for (int i = 0; i < lightCount; i++)
		{
			int distance = in.nextInt();
			int duration = in.nextInt();
			RoadSegment segment = new RoadSegment(distance, duration);
			calculator.addRoadSegment(segment);
		}

		int actual = calculator.getMaxSpeed();
		System.out.println(actual);
		in.close();
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
	 */
	public final BigDecimal min;
	
	/**
	 */
	public final BigDecimal max;
	
	// [min:max] / ]min:max] / ]min:max[ / [min:max[
	public final boolean leftClose = false;
	public final boolean rightClose = false;
	
	protected static final BigDecimal zero = new BigDecimal("0.0");

	public Interval(BigDecimal min, BigDecimal max)
	{
		this.min = min;
		this.max = max;
		if(this.min == null || this.max == null)
			throw new IllegalArgumentException("Null values are not accepted for Interval");
		if (this.min.compareTo(this.max) == 1)
			throw new IllegalArgumentException(
				"The min value of an interval must be less (or equals) than its max value.");
	}

	public boolean isEmpty()
	{
		boolean isZero = this.min.intValue() == 0;
		boolean minEqualsMax = this.min.compareTo(this.max) == 0;
		return isZero && minEqualsMax;
	}

	public boolean isOverlapping(Interval o)
	{
		if (this.isEmpty() || o.isEmpty())
			return false;

		if(o.min.compareTo(this.max) == 1)
			return false;
		if(o.max.compareTo(this.min) == -1)
			return false;

		return true;
	}

	@Override
	public int compareTo(Interval o)
	{
		int min = this.min.compareTo(o.min);
		
		if(min != 0)
			return min;
			
		return this.max.compareTo(o.max);
	}

	@Override
	public String toString()
	{
		return "[" + SpeedCalculator.getSpeedInKmPerHour(this.min) + ", "
			+ SpeedCalculator.getSpeedInKmPerHour(this.max) + "]";
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (!(obj instanceof Interval))
			return false;

		Interval o = (Interval) obj;
		boolean sameMin = this.min.compareTo(o.min) == 0;
		boolean sameMax = this.max.compareTo(o.max) == 0;
		return sameMin && sameMax;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.min, this.max);
	}
}

final class EmptyInterval extends Interval
{
	public EmptyInterval()
	{
		super(zero, zero);
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

		BigDecimal min = i1.min.max(i2.min);
		BigDecimal max = i1.max.min(i2.max);

		// For now Interval is open, so not empty if min == max != 0.
		//		if (min.compareTo(max) == 0)
		//			return EMPTY;

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

		BigDecimal min = i1.min.min(i2.min);
		BigDecimal max = i1.max.max(i2.max);

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
		Solution.printIfVerbose("getIntervalIntersections...");
		if (intervals_1.isEmpty() || intervals_2.isEmpty())
			return Collections.emptyList();

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
				if (i1.max.compareTo(i2.min) == -1)
					break;
				// If i2 too little, skip.
				if (i2.max.compareTo(i1.min) == -1)
					continue;
				Interval intersection = getIntervalIntersection(i1, i2);
				if (!intersection.isEmpty())
					Solution.printIfVerbose("i1" + i1.toString() + " \u2229 i2" + i2.toString()
						+ " --> " + intersection.toString());
				intersectionCollector.add(intersection);
			}
		}

		Collections.sort(intersectionCollector);
		return intersectionCollector;
	}

	/**
	 * to implement;
	 */
	public static List<Interval> getIntervalUnions(
		List<Interval> intervals_1,
		List<Interval> intervals_2)
	{
		return Collections.emptyList();
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
	 * Maximum speed considered : 999 m/s.
	 */
	private final BigDecimal MaxValue = new BigDecimal("99999999");
	/**
	 * in meter/s, minimum considered speed;
	 */
	public final BigDecimal min = getSpeedInMeterPerSecond(1);

	/**
	 * in meter/s, maximum legal speed;
	 */
	public final BigDecimal max;

	private List<Interval> possibleSpeeds = new ArrayList<>();

	public SpeedCalculator(int maximumSpeed)
	{
		Solution.printIfVerbose("NEW SpeedCalculator with max speed: " + maximumSpeed + " km/h");
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
		BigDecimal maxSpeedInMeterPerSecond = higherInterval.max;
		int maxSpeedInKmPerHour = getSpeedInKmPerHour(maxSpeedInMeterPerSecond);
		return maxSpeedInKmPerHour;
	}

	/**
	 * Adapt the possibleSpeed intervals to handle this new segment.
	 */
	public void addRoadSegment(RoadSegment aRoadSegment)
	{
		Solution.printIfVerbose("addRoadSegment(" + aRoadSegment.distance + ", "
			+ aRoadSegment.duration + ")");
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
		Solution.printIfVerbose("getIntervalsForRoadSegment...");
		// Start to look for possible speeds from the max speed to the minimum speed;
		BigDecimal distance = new BigDecimal(Double.toString(aRoadSegment.distance));
		BigDecimal duration = new BigDecimal(Double.toString(aRoadSegment.duration));
		BigDecimal durationIncrement = duration.add(duration);
		List<Interval> speedIntervalCollector = new ArrayList<>();

		BigDecimal minDuration = new BigDecimal("0");
		Interval previousInterval = null;
		BigDecimal minimumTime = Solution.MINIMUM_TIME;
		BigDecimal minimumInterval= Solution.MINIMUM_INTERVAL;
		while (true)
		{
			BigDecimal durationLimit = duration.subtract(minimumTime);
			BigDecimal minSpeedToPass = distance.divide(durationLimit, Solution.DECIMAL_ACCURACY, Solution.ROUNDING_MODE);
			BigDecimal maxSpeedToPass;
			if(minDuration.doubleValue() != 0d)
				maxSpeedToPass = distance.divide(minDuration, Solution.DECIMAL_ACCURACY, Solution.ROUNDING_MODE);
			else // 
				maxSpeedToPass = this.MaxValue;

			// We have reached the minimum threshold.
			if (maxSpeedToPass.compareTo(this.min) == -1)
				break;

			BigDecimal cappedMinSpeedToPass = minSpeedToPass.max(this.min);
			BigDecimal cappedMaxSpeedToPass = maxSpeedToPass.min(this.max);

			// Set parameters for next loop.
			duration = duration.add(durationIncrement);
			minDuration = minDuration.add(durationIncrement);
			
			// Ignore case where minimum speed is already too high.
			if (cappedMinSpeedToPass.compareTo(cappedMaxSpeedToPass) == 1)
			{
				Interval speedInterval = new Interval(minSpeedToPass, maxSpeedToPass);
				Solution.printIfVerbose("Discard illegal speed km/h : " + speedInterval.toString()
					+ " to travel " + distance.intValue() + " meters in less than "
					+ roundTo2Decimals((duration.doubleValue() - Solution.MINIMUM_TIME.doubleValue() - durationIncrement.doubleValue()))
					+ " seconds.");
				continue;
			}

			// Check if interval is too narrow to be achieved.
			BigDecimal interval = cappedMaxSpeedToPass.subtract(cappedMinSpeedToPass);
			if (interval.compareTo(minimumInterval) == -1)
				break;
			// In case we do not have interval check, stop at 0.
			if (cappedMinSpeedToPass.intValue() == 0 && cappedMaxSpeedToPass.intValue() == 0)
				break;

			Interval speedInterval = new Interval(cappedMinSpeedToPass, cappedMaxSpeedToPass);

			// Ignore identical results (but should this happen ?).
			if (speedInterval.equals(previousInterval))
				continue;

			Solution.printIfVerbose("Possible speed km/h : " + speedInterval.toString()
				+ " to travel " + distance + " meters between "
				+ roundTo2Decimals((minDuration.intValue() - durationIncrement.intValue())) + " and "
				+ roundTo2Decimals((duration.doubleValue() - Solution.MINIMUM_TIME.doubleValue() - durationIncrement.doubleValue()))
				+ " seconds.");
			// Check if empty ?
			speedIntervalCollector.add(speedInterval);
			previousInterval = speedInterval;
		}

		Collections.sort(speedIntervalCollector);
		return speedIntervalCollector;
	}

	// ---- Converting methods ----
	private static final BigDecimal coefSpeed = new BigDecimal("3.6");
	public static BigDecimal getSpeedInMeterPerSecond(int speedInKmPerHour)
	{
		// * (5/18)
		BigDecimal speedToConvert = new BigDecimal(speedInKmPerHour);
		BigDecimal speedConverted = speedToConvert.divide(coefSpeed, Solution.DECIMAL_ACCURACY, Solution.ROUNDING_MODE);
		return speedConverted;
	}

	/**
	 * 
	 * @param speedInMeterPersecond
	 * @return speedInKmPerHour as an integer (possible lost of accuracy).
	 */
	public static int getSpeedInKmPerHour(BigDecimal speedInMeterPersecond)
	{
		// * (18 / 5)
		BigDecimal speedConverted = speedInMeterPersecond.multiply(coefSpeed);
		return speedConverted.intValue();
	}

	// ---- Rounding methods ----
	public static double roundTo2Decimals(double number)
	{
		return roundToXDecimals(number, 2);
	}

	public static double roundToXDecimals(double number, int decimal)
	{
		double x = Math.pow(10.0, decimal);
		return Math.round(number * x) / x;
	}

}
