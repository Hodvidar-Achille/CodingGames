package com.hodvidar.codingame.puzzles.medium;
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

/**
 * https://www.codingame.com/ide/puzzle/aneo by Hodvidar. (To finish, only 50% of tests pass,
 * because of rounding error...)
 **/
class Aneo {
    // ---- 9/10 tests OK for : 5 to 10 / 0.001 / 0.001 ----

    /**
     * /!\ Can't be more than 15 /!\ <br/>
     * Note : there is double floating point inaccuracy.
     */
    public static final int DECIMAL_ACCURACY = 10;

    /**
     * Minimum interval between minimum speed and maximum speed that will be considered. <br/>
     * (m/s).
     */
    public static final double MINIMUM_INTERVAL = 0.001;

    /**
     * Minimum time the car is allow to pass the green light before the red. <br/>
     * (second).
     */
    public static final double MINIMUM_TIME = 0.001;

    private static final boolean TESTING = true;
    private static final boolean VERBOSE = true;

    private static final JeuxDeTest[] tests = new JeuxDeTest[]{JeuxDeTest.FeuDuVillage,
            JeuxDeTest.FeuDuVillage2, JeuxDeTest.RouteDeCampagneTranquille,
            JeuxDeTest.RouteDeCampagneMoinsTranquille,
            JeuxDeTest.RouteDeCampagneTranquilleDereglee, JeuxDeTest.RouteDeCampagneSTPC,
            JeuxDeTest.AutorouteAllemande, JeuxDeTest.PluieDeFeux, JeuxDeTest.GuirlandeLumineuse,
            JeuxDeTest.FeuxRapides};

    public static void main(final String[] args) {
        System.err.println("Solution for: \n -DECIMAL_ACCURACY=" + DECIMAL_ACCURACY
                + " \n -MINIMUM_INTERVAL=" + MINIMUM_INTERVAL + "\n -MINIMUM_TIME=" + MINIMUM_TIME);

        final Aneo s = new Aneo();

        if (!TESTING)
            s.doSolution();
        else {
            for (final JeuxDeTest test : tests)
                s.doSolution(test);
        }
    }

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    private void doSolution(final JeuxDeTest jeuDeTest) {
        final int speed = jeuDeTest.speed;
        final int lightCount = jeuDeTest.lightCount;

        printIfVerbose("----------- Test '" + jeuDeTest + "' -----------");

        printIfVerbose("speed=" + speed);
        printIfVerbose("lightCount=" + lightCount);

        final SpeedCalculator calculator = new SpeedCalculator(speed);
        for (int i = 0; i < lightCount; i++) {
            final int distance = jeuDeTest.distances[i];
            final int duration = jeuDeTest.durations[i];
            printIfVerbose("distance=" + distance);
            printIfVerbose("duration= " + duration);
            final RoadSegment segment = new RoadSegment(distance, duration);
            calculator.addRoadSegment(segment);
            printIfVerbose("Possible max speed = " + calculator.getMaxSpeed());
        }

        final int expected = jeuDeTest.result;
        final int actual = calculator.getMaxSpeed();

        printIfVerbose("Final result expected : " + expected);
        printIfVerbose("Final result actual : " + actual);

        if (actual != expected)
            System.err.println("JeuxDeTest '" + jeuDeTest + " expected=" + expected + " actual="
                    + actual + " --> FAILURE");
        else
            printIfVerbose("JeuxDeTest '" + jeuDeTest + "' expected=" + expected
                    + " actual=" + actual + " -->  SUCCESS");
    }

    private void doSolution() {
        final Scanner in = new Scanner(System.in);
        final int speed = in.nextInt();
        final int lightCount = in.nextInt();

        final SpeedCalculator calculator = new SpeedCalculator(speed);
        for (int i = 0; i < lightCount; i++) {
            final int distance = in.nextInt();
            final int duration = in.nextInt();
            final RoadSegment segment = new RoadSegment(distance, duration);
            calculator.addRoadSegment(segment);
        }

        final int actual = calculator.getMaxSpeed();
        System.out.println(actual);
        in.close();
    }

    enum JeuxDeTest {
        FeuDuVillage(50, 1, new int[]{200}, new int[]{15}, 50),
        FeuDuVillage2(50, 1, new int[]{200}, new int[]{10}, 36),
        RouteDeCampagneTranquille(90, 3, new int[]{300, 1500, 3000}, new int[]{30, 30, 30}, 90),
        RouteDeCampagneMoinsTranquille(90, 3, new int[]{300, 1500, 3000}, new int[]{10, 10, 10}, 54),
        RouteDeCampagneTranquilleDereglee(90, 3, new int[]{300, 1500, 3000}, new int[]{30, 20, 10}, 67),
        RouteDeCampagneSTPC(80, 4, new int[]{700, 2200, 3000, 4000}, new int[]{25, 15, 10, 28}, 49),
        AutorouteAllemande(200,
                6,
                new int[]{1000, 3000, 4000, 5000, 6000, 7000},
                new int[]{15,
                        10, 30, 30, 5, 10},
                60),
        PluieDeFeux(130,
                100,
                new int[]{500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000,
                        5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000, 10500, 11000, 11500,
                        12000, 12500, 13000, 13500, 14000, 14500, 15000, 15500, 16000, 16500, 17000, 17500,
                        18000, 18500, 19000, 19500, 20000, 20500, 21000, 21500, 22000, 22500, 23000, 23500,
                        24000, 24500, 25000, 25500, 26000, 26500, 27000, 27500, 28000, 28500, 29000, 29500,
                        30000, 30500, 31000, 31500, 32000, 32500, 33000, 33500, 34000, 34500, 35000, 35500,
                        36000, 36500, 37000, 37500, 38000, 38500, 39000, 39500, 40000, 40500, 41000, 41500,
                        42000, 42500, 43000, 43500, 44000, 44500, 45000, 45500, 46000, 46500, 47000, 47500,
                        48000, 48500, 49000, 49500, 50000},
                new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15,
                        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                        15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,
                        15, 15, 15},
                60),
        GuirlandeLumineuse(130,
                100,
                new int[]{1100, 1150, 1200, 1250, 1300, 2100, 2150, 2200, 2250,
                        2300, 3100, 3150, 3200, 3250, 3300, 4100, 4150, 4200, 4250, 4300, 5100, 5150, 5200,
                        5250, 5300, 6100, 6150, 6200, 6250, 6300, 7100, 7150, 7200, 7250, 7300, 8100, 8150,
                        8200, 8250, 8300, 9100, 9150, 9200, 9250, 9300, 10100, 10150, 10200, 10250, 10300,
                        11100, 11150, 11200, 11250, 11300, 12100, 12150, 12200, 12250, 12300, 13100, 13150,
                        13200, 13250, 13300, 14100, 14150, 14200, 14250, 14300, 15100, 15150, 15200, 15250,
                        15300, 16100, 16150, 16200, 16250, 16300, 17100, 17150, 17200, 17250, 17300, 18100,
                        18150, 18200, 18250, 18300, 19100, 19150, 19200, 19250, 19300, 20100, 20150, 20200,
                        20250, 20300},
                new int[]{10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30,
                        10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10,
                        15,
                        20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20,
                        25,
                        30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30,
                        10,
                        15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30, 10, 15, 20, 25, 30},
                6),
        FeuxRapides(90,
                16,
                new int[]{1234, 2468, 3702, 6170, 8638, 13574, 16042, 20978, 23446,
                        28382, 35786, 38254, 45658, 50594, 53062, 57988},
                new int[]{5, 5, 5, 5, 5, 5, 5, 5,
                        5, 5, 5, 5, 5, 5, 5, 5},
                74);

        public final int speed;
        public final int lightCount;
        public final int[] distances;
        public final int[] durations;
        public final int result;

        JeuxDeTest(final int speed, final int lightCount, final int[] distances, final int[] durations, final int result) {
            this.speed = speed;
            this.lightCount = lightCount;
            this.distances = distances;
            this.durations = durations;
            this.result = result;
        }
    }

    // ------------------------- INTERNAL CLASSES ---------------------------------------
    class RoadSegment {
        public final int distance;
        public final int duration;

        public RoadSegment(final int distance, final int duration) {
            this.distance = distance;
            this.duration = duration;
        }
    }

    class Interval implements Comparable<Interval> {
        /**
         * in meter/s (must be > 0);
         */
        public final double min;
        /**
         * in meter/s
         */
        public final double max;

        private final SpeedCalculator speedCalculator;

        public Interval(final double min, final double max, final SpeedCalculator speedCalculator) {
            this.min = min;
            this.max = max;
            if (this.min > this.max)
                throw new IllegalArgumentException(
                        "The min value of an interval must be less (or equals) than its max value.");
            this.speedCalculator = speedCalculator;
        }

        public boolean isEmpty() {
            return 0d == this.min && this.min == this.max;
        }

        public boolean isOverlapping(final Interval o) {
            if (this.isEmpty() || o.isEmpty())
                return false;

            if (o.min > this.max)
                return false;
            return !(o.max < this.min);
        }

        @Override
        public int compareTo(final Interval o) {
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
        public String toString() {
            return "[" + speedCalculator.getSpeedInKmPerHour(this.min) + ", "
                    + speedCalculator.getSpeedInKmPerHour(this.max) + "]";
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof Interval o))
                return false;

            return this.min == o.min && this.max == o.max;
        }

        @Override
        public int hashCode() {
            return Objects.hash(Double.valueOf(this.min), Double.valueOf(this.max));
        }
    }

    final class EmptyInterval extends Interval {
        public EmptyInterval(final SpeedCalculator speedCalculator) {
            super(0d, 0d, speedCalculator);
        }
    }

    class IntervalHandler {
        private final SpeedCalculator speedCalculator;
        private final EmptyInterval EMPTY;

        private IntervalHandler(final SpeedCalculator speedCalculator) {
            this.speedCalculator = speedCalculator;
            EMPTY = new EmptyInterval(this.speedCalculator);
        }

        /**
         * [-20, 20] & [-5, 25] --> [-5, 20]. <br/>
         * [-20, 20] & [21, 25] --> [0, 0] (EMPTY). <br/>
         * [-20, 20] & [-5, 5] --> [-5, 5]. <br/>
         * [10, 10] & [-100, 100] --> [0, 0].(EMPTY) <br/>
         */
        public Interval getIntervalIntersection(final Interval i1, final Interval i2) {
            if (i1.isEmpty() || i2.isEmpty())
                return EMPTY;
            if (!i1.isOverlapping(i2))
                return EMPTY;

            final double min = Math.max(i1.min, i2.min);
            final double max = Math.min(i1.max, i2.max);

            if (min == max)
                return EMPTY;

            return new Interval(min, max, this.speedCalculator);
        }

        /**
         * [-20, 20] & [-5, 25] --> [-20, 25]. <br/>
         * [-20, 20] & [21, 25] --> [-20, 20] & [21, 25]. <br/>
         * [-20, 20] & [-5, 5] --> [-20, 20]. <br/>
         * [10, 10] & [-100, 100] --> [-100, 100]. <br/>
         */
        public List<Interval> getIntervalUnion(final Interval i1, final Interval i2) {
            if (i1.isEmpty())
                return toList(i2);
            if (i2.isEmpty())
                return toList(i1);
            if (!i1.isOverlapping(i2))
                return toList(i1, i2);

            final double min = Math.min(i1.min, i2.min);
            final double max = Math.max(i1.max, i2.max);

            return toList(new Interval(min, max, this.speedCalculator));
        }

        private List<Interval> toList(final Interval... intervals) {
            final List<Interval> newIntervals = new ArrayList<>();
            Collections.addAll(newIntervals, intervals);
            Collections.sort(newIntervals);
            return newIntervals;
        }

        /**
         * The return List can be only smaller or of the same size of the smallest given list. Note
         * : both list must be sorted.
         */
        public List<Interval> getIntervalIntersections(
                final List<Interval> intervals_1,
                final List<Interval> intervals_2) {
            printIfVerbose("getIntervalIntersections...");
            if (intervals_1.isEmpty() || intervals_2.isEmpty())
                return Collections.emptyList();

            final List<Interval> intersectionCollector = new ArrayList<>();

            // Collections.sort(intervals_1);
            // Collections.sort(intervals_2);

            // Could this be optimized ?
            for (final Interval i1 : intervals_1) {
                for (final Interval i2 : intervals_2) {
                    // Because the list are sorted, if first element is
                    // already too high, directly go to next i1.
                    if (i1.max < i2.min)
                        break;
                    // If i2 too little, skip.
                    if (i2.max < i1.min)
                        continue;
                    final Interval intersection = getIntervalIntersection(i1, i2);
                    if (!intersection.isEmpty())
                        printIfVerbose("i1" + i1 + " \u2229 i2" + i2
                                + " --> " + intersection);
                    intersectionCollector.add(intersection);
                }
            }

            Collections.sort(intersectionCollector);
            return intersectionCollector;
        }

        /**
         * to implement;
         */
        public List<Interval> getIntervalUnions(
                final List<Interval> intervals_1,
                final List<Interval> intervals_2) {
            return Collections.emptyList();
        }

        /**
         * Returns true if any interval, in the list, overlap with another.
         */
        public boolean isOverlapping(final List<Interval> intervals) {
            final int size = intervals.size();
            if (size == 0)
                return false;

            // Could this be optimized ?
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (j == i)
                        continue;
                    final Interval i1 = intervals.get(i);
                    final Interval i2 = intervals.get(j);
                    if (i1.isOverlapping(i2))
                        return true;
                }
            }

            return false;
        }

    }

    class SpeedCalculator {
        /**
         * in meter/s
         */
        public final double min = getSpeedInMeterPerSecond(1);

        /**
         * in meter/s
         */
        public final double max;

        private final IntervalHandler intervalHandler;

        private List<Interval> possibleSpeeds = new ArrayList<>();

        public SpeedCalculator(final int maximumSpeed) {
            printIfVerbose("NEW SpeedCalculator with max speed: " + maximumSpeed + " km/h");
            this.max = getSpeedInMeterPerSecond(maximumSpeed);
            final Interval firstInterval = new Interval(this.min, this.max, this);
            this.possibleSpeeds.add(firstInterval);
            this.intervalHandler = new IntervalHandler(this);
        }

        public int getMaxSpeed() {
            Collections.sort(this.possibleSpeeds);
            final int size = this.possibleSpeeds.size();
            if (size == 0)
                return 0;
            final Interval higherInterval = this.possibleSpeeds.get(this.possibleSpeeds.size() - 1);
            final double maxSpeedInMeterPerSecond = higherInterval.max;
            final int maxSpeedInKmPerHour = getSpeedInKmPerHour(maxSpeedInMeterPerSecond);
            return maxSpeedInKmPerHour;
        }

        /**
         * Adapt the possibleSpeed intervals to handle this new segment.
         */
        public void addRoadSegment(final RoadSegment aRoadSegment) {
            printIfVerbose("addRoadSegment(" + aRoadSegment.distance + ", "
                    + aRoadSegment.duration + ")");
            // This could probably be optimized.
            // 1) finds intervals possibles for this segment
            final List<Interval> newPossibleSpeeds = this.getIntervalsForRoadSegment(aRoadSegment);
            // 2) find the intersections of these intervals with previous intervals
            this.possibleSpeeds = intervalHandler.getIntervalIntersections(
                    this.possibleSpeeds,
                    newPossibleSpeeds);
        }

        private List<Interval> getIntervalsForRoadSegment(final RoadSegment aRoadSegment) {
            printIfVerbose("getIntervalsForRoadSegment...");
            // Start to look for possible speeds from the max speed to the minimum speed;
            final double distance = aRoadSegment.distance;
            double duration = aRoadSegment.duration;
            final double durationIncrement = duration + duration;
            final List<Interval> speedIntervalCollector = new ArrayList<>();

            double minSpeedToPass = 0d;
            double maxSpeedToPass = 0d;
            double minDuration = 0d;
            Interval previousInterval = null;
            while (true) {
                minSpeedToPass = distance / (duration - MINIMUM_TIME);
                maxSpeedToPass = distance / minDuration;

                minSpeedToPass = roundForAneonTest(minSpeedToPass);
                maxSpeedToPass = roundForAneonTest(maxSpeedToPass);

                if (maxSpeedToPass < this.min)
                    break;

                final double cappedMinSpeedToPass = Math.max(minSpeedToPass, this.min);
                final double cappedMaxSpeedToPass = Math.min(maxSpeedToPass, this.max);

                // Set parameters for next loop.
                duration += durationIncrement;
                minDuration += durationIncrement;
                // Ignore case where minimum speed is already too high.
                if (cappedMinSpeedToPass > cappedMaxSpeedToPass) {
                    final Interval speedInterval = new Interval(minSpeedToPass, maxSpeedToPass, this);
                    printIfVerbose("Discard illegal speed km/h : " + speedInterval
                            + " to travel " + distance + " meters in less than "
                            + roundTo2Decimals((duration - MINIMUM_TIME - durationIncrement))
                            + " seconds.");
                    continue;
                }

                // Check if interval is too narrow to be achieved.
                final double interval = cappedMaxSpeedToPass - cappedMinSpeedToPass;
                if (interval < MINIMUM_INTERVAL)
                    break;
                // In case we do not have interval check, stop at 0.
                if (cappedMinSpeedToPass == 0 && cappedMaxSpeedToPass == 0)
                    break;

                final Interval speedInterval = new Interval(cappedMinSpeedToPass, cappedMaxSpeedToPass, this);

                // Ignore identical results (but should this happen ?).
                if (speedInterval.equals(previousInterval))
                    continue;

                printIfVerbose("Possible speed km/h : " + speedInterval
                        + " to travel " + distance + " meters between "
                        + roundTo2Decimals((minDuration - durationIncrement)) + " and "
                        + roundTo2Decimals((duration - MINIMUM_TIME - durationIncrement))
                        + " seconds.");
                // Check if empty ?
                speedIntervalCollector.add(speedInterval);
                previousInterval = speedInterval;
            }

            Collections.sort(speedIntervalCollector);
            return speedIntervalCollector;
        }

        // ---- Converting methods ----
        public double getSpeedInMeterPerSecond(final int speedInKmPerHour) {
            // * (5/18)
            return roundForAneonTest(((speedInKmPerHour) / 3.6d));
        }

        public int getSpeedInKmPerHour(final double speedInMeterPersecond) {
            // * (18 / 5)
            return (int) roundForAneonTest((speedInMeterPersecond) * 3.6d);
        }

        public double getSpeedInMeterPerHour(final int speedInKmPerHour) {
            // * 10000
            return speedInKmPerHour * 1000.0;
        }

        public double getSpeedInKmPerHour(final int speedInMeterPerHour) {
            // / by 3600 seconds
            return roundForAneonTest(speedInMeterPerHour / 3600.0);
        }

        // ---- Rounding methods ----
        public double roundTo2Decimals(final double number) {
            return roundToXDecimals(number, 2);
        }

        public double roundToXDecimals(final double number, final int decimal) {
            final double x = Math.pow(10.0, decimal);
            return Math.round(number * x) / x;
        }

        public double roundByConvertingIntoInteger(final double number) {
            final int n = (int) number;
            return n;
        }

        public double roundForAneonTest(final double x) {
            return roundToXDecimals(x, DECIMAL_ACCURACY);
        }
    }
}
