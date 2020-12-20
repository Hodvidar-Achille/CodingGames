package com.hodvidar.newbiecontest.logique;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.newbiecontest.org/index.php?page=epreuve&no=322
 * by Hodvidar
 */
/*
 * def problemathiques(n=100, k=6):
 l = [random.randint(0, 1) for i in xrange(n)]
 return not any( all( l[ ( (k-1)*i +j) % n ] for j in xrange(k)) for i in xrange(n/(k-1)))
 */
@SuppressWarnings("unused")
public class Problémathiques {

    private static final int oneBillion = 1000000000;
    private static final double tenBillion = 10000000000L;

    // public static void main(String[] args)
    // {
    //  System.out.println("---TestPy---");
    //  TestPy h = new TestPy();
    //  h.testPy();
    // }

    // public static void main(String[] args)
    // {
    //  System.out.println("Hello World");
    //  int count = 0;
    //  int sixIsZero = 0;
    //  for (int i1 = 0; i1 < 2; i1++)
    //  {
    //   for (int i2 = 0; i2 < 2; i2++)
    //   {
    //    for (int i3 = 0; i3 < 2; i3++)
    //    {
    //     for (int i4 = 0; i4 < 2; i4++)
    //     {
    //      for (int i5 = 0; i5 < 2; i5++)
    //      {
    //       for (int i6 = 0; i6 < 2; i6++)
    //       {
    //        count++;
    //        if (i6 == 0)
    //         sixIsZero++;
    //        System.out.print("\nn°" + count + " : "//
    //         + "[" + i1 //
    //         + "" + i2//
    //         + "" + i3//
    //         + "" + i4//
    //         + "" + i5//
    //         + "" + i6 + "]");
    //       }
    //      }
    //     }
    //    }
    //   }
    //  }
    //  System.out.println("End, sixIsZero =" + sixIsZero + " for total :" + count);
    // }

    // P(A) = probability of having six '1'
    // P(!A) = 1 - P(A) --> At least one '0'
    // P(!An | !An-1) =
    private static final NumberFormat fmt = NumberFormat.getInstance();
    // P(An | An-1) sauf pour n = 20
    private static final int numerateurOKsiOK = 1;
    private static final int denominateurOKsiOK = 32;

    // private static void writeProbaNotA(double numerator1, double denomitator1, int step)
    // {
    //  if (step > 20)
    //   return;
    //  double numerator2;
    //  double denomitator2;
    //  if (step == 20)
    //  {
    //   numerator2 = 62543;
    //   denomitator2 = 63504;
    //  }
    //  else
    //  {
    //   numerator2 = 1985;
    //   denomitator2 = 2016;
    //  }
    //  double numerator = numerator1 * numerator2;
    //  double denomitator = denomitator1 * denomitator2;
    //  System.out.print("\n\tP(!A" + step + " | !A1->n-1) = " + fmt.format(numerator) + "/"
    //   + fmt.format(denomitator));
    //  double[] simplefiedFraction = asFraction(numerator, denomitator);
    //  numerator = simplefiedFraction[0];
    //  denomitator = simplefiedFraction[1];
    //  System.out.print("\n\t[simplified] P(!A" + step + " | !A1->n-1) = " + fmt.format(numerator)
    //   + "/" + fmt.format(denomitator));
    //  step++;
    //  writeProbaNotA(numerator, denomitator, step);
    //  if (step > 20)
    //  {
    //   numerator = denomitator - numerator;
    //   System.out.print("\n\n[simplified] !P(!A" + step + " | !A1->n-1) = "
    //    + fmt.format(numerator) + "/" + fmt.format(denomitator));
    //  }
    // }
    // P(An | !An-1) sauf pour n = 20
    private static final int numerateurOKsiKO = 31;
    private static final int denominateurOKIfKO = 2016;

    public static void main(String[] args) {
        System.out.println("---TestPy---");
        fmt.setGroupingUsed(false);
        fmt.setMaximumIntegerDigits(999);
        fmt.setMaximumFractionDigits(999);

        System.out.print("\n Calculing P(!!A) (never a P(A)) : ");
        System.out.print("\n\tP(!A1) = 62543 / 63504");
        // writeProbNeverA(62543, 63504, 2);
        // writeProbaNeverA(63, 64, 1);
        // writeProbaEachAanyCase(1, 64, 63, 64, 1);

        //  BigInteger num = BigInteger.valueOf(62543);
        //  num = num.pow(20);
        //  BigInteger deno = BigInteger.valueOf(63504);
        //  deno = deno.pow(20);
        //  System.out.print("\n\t !!A : " + fmt.format(num.doubleValue()) + "/"
        //   + fmt.format(deno.doubleValue()));
        //  BigInteger[] simplefiedFraction = asFraction(num, deno);
        //  num = simplefiedFraction[0];
        //  deno = simplefiedFraction[1];
        //  System.out.print("\n\t[simplified]  !!A : " + fmt.format(num.doubleValue()) + "/"
        //   + fmt.format(deno.doubleValue()));

        writeProbNeverA(BigInteger.valueOf(62543), BigInteger.valueOf(63504), 2);
    }

    private static void writeProbNeverA(BigInteger numerator1, BigInteger denomitator1, int step) {
        if (step > 20)
            return;
        BigInteger numerator2;
        BigInteger denomitator2;

        numerator2 = BigInteger.valueOf(62543);
        denomitator2 = BigInteger.valueOf(63504);

        BigInteger numerator = numerator1.multiply(numerator2);
        BigInteger denomitator = denomitator1.multiply(denomitator2);
        //  System.out.print("\n\tP(!A" + step + " | !A1->n-1) = " + fmt.format(numerator) + "/"
        //   + fmt.format(denomitator));
        BigInteger[] simplefiedFraction = asFraction(numerator, denomitator);
        numerator = simplefiedFraction[0];
        denomitator = simplefiedFraction[1];
        System.out.print("\n\t[simplified] P(!A" + step + " | !A1->n-1) = " + numerator.toString()
                + "/" + denomitator.toString());
        step++;
        writeProbNeverA(numerator, denomitator, step);
        if (step > 20) {
            numerator = denomitator.min(numerator);
            System.out.print("\n\n[simplified] !P(!A" + step + " | !A1->n-1) = "
                    + fmt.format(numerator) + "/" + fmt.format(denomitator));
        }
    }

    // Look for probability Always have !An
    private static void writeProbaNeverA(double numerateurKO, double denominateurKO, int i) {
        i++;
        System.out.print("\n\tCalculing...");
        // P(An | !An-1) = 31/63 * 1/32 = 31/2016
        // P(!An | !An-1) = 1 - P(An | !An-1) = 1 - (31/2016) = 1985 / 2016
        System.out.print("\nP(!A" + i + " | !A" + (i - 1) + ") = " + fmt.format(numerateurKO) + "/"
                + fmt.format(denominateurKO));
    }
    //
    // private static void writeProbaEachAanyCase(
    //  double numerateurOK,
    //  double denominateurOK,
    //  double numerateurKO,
    //  double denominateurKO,
    //  int i)
    // {
    //  System.out.print("\nP(A" + i + ") = " + fmt.format(numerateurOK) + "/"
    //   + fmt.format(denominateurOK));
    //  System.out.print("\nP(!A" + i + ") = " + fmt.format(numerateurKO) + "/"
    //   + fmt.format(denominateurKO));
    //
    //  if (i == 19)
    //  {
    //   // TODO code for last case :
    //   System.out.print("\nP(A20) : TODO");
    //   return;
    //  }
    //  // Prepare next call  for next 'n' :
    //  System.out.print("\nCalcul for next P(A) :");
    //  i++;
    //  // P(An) = P(An | An-1) * P(An-1) + P(An | !An-1) * P(!An-1)
    //  // P(An) = P(A)1 + P(A)2
    //  // P(!An) = 1 - P(An)
    //
    //  // P(A)1 = a1 / b1
    //  double numPa1 = numerateurOKsiOK * numerateurOK;
    //  double denoPa1 = denominateurOKsiOK * denominateurOK;
    //  System.out.print("\n\tP(A" + i + ")1 :" + fmt.format(numPa1) + "/" + fmt.format(denoPa1));
    //  double[] simplefiedFraction = asFraction(numPa1, denoPa1);
    //  numPa1 = simplefiedFraction[0];
    //  denoPa1 = simplefiedFraction[1];
    //  System.out.print("\n\tP(A" + i + ")1 :" + fmt.format(numPa1) + "/" + fmt.format(denoPa1));
    //  // P(A)2 = a2 / b2
    //  double numPa2 = numerateurOKsiKO * numerateurKO;
    //  double denoPa2 = denominateurOKIfKO * denominateurKO;
    //  System.out.print("\n\tP(A" + i + ")2 :" + fmt.format(numPa2) + "/" + fmt.format(denoPa2));
    //  simplefiedFraction = asFraction(numPa2, denoPa2);
    //  numPa2 = simplefiedFraction[0];
    //  denoPa2 = simplefiedFraction[1];
    //  System.out.print("\n\tP(A" + i + ")2 :" + fmt.format(numPa2) + "/" + fmt.format(denoPa2));
    //
    //  // (a1*b2 + a2*b1 ) / b1*b2
    //  numPa1 = numPa1 * denoPa2;
    //  numPa2 = numPa2 * denoPa1;
    //
    //  double numPaX = numPa1 + numPa2;
    //  double denoPaX = denoPa1 * denoPa2;
    //  System.out.print("\nP(A" + i + ") = " + fmt.format(numPaX) + "/" + fmt.format(denoPaX));
    //  simplefiedFraction = asFraction(numPaX, denoPaX);
    //  numPaX = simplefiedFraction[0];
    //  denoPaX = simplefiedFraction[1];
    //  double numKO = denoPaX - numPaX;
    //  double denoKO = denoPaX;
    //
    //  writeProbaEachAanyCase(numPaX, denoPaX, numKO, denoKO, i);
    // }

    /**
     * @return the greatest common denominator
     */
    public static BigInteger gcm(BigInteger a, BigInteger b) {
        return b.intValue() == 0 ? a : gcm(b, a.mod(b)); // Not bad for one line of code :)
    }

    public static BigInteger[] asFraction(BigInteger a, BigInteger b) {
        BigInteger gcm = gcm(a, b);
        return new BigInteger[]{(a.divide(gcm)), (b.divide(gcm))};
    }

    /**
     * def problemathiques(n=100, k=6):
     * l = [random.randint(0, 1) for i in xrange(n)]
     * return not
     * any(
     * all(
     * l[ ( (k-1)*i +j) % n ]
     * for j in xrange(k))
     * for i in xrange(n/(k-1)))
     */
    private static boolean test(int n, int k) {
        int[] values = new int[n];
        for (int a = 0; a < n; a++)
            values[a] = (Math.random() < 0.5) ? 0 : 1;

        boolean result = false;
        for (int i = 0; i < (n / (k - 1)); i++) {
            boolean subResult = true;
            for (int j = 0; j < k; j++) {
                int index = (((k - 1) * i + j) % n);
                // (1/2)^6 --> 1/64
                if (values[index] == 0) {
                    subResult = false;
                    break;
                }
            }
            // 1/64 * 20 = 5/16
            // Only false : 6932512306094823/9451326062417812
            // 1 - 6932512306094823/9451326062417812
            if (subResult)
                result = true; // 1 - 6932512306094823/9451326062417812
        }

        // 11/16
        // 1 - (1 - 6932512306094823/9451326062417812)
        return !result;
    }

    private static boolean test2(int n, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= 100; i++)
            map.put(Integer.valueOf(i), Integer.valueOf(0));
        int[] values = new int[n];
        for (int a = 0; a < n; a++)
            values[a] = (Math.random() < 0.5) ? 0 : 1;

        boolean result = false;
        for (int i = 0; i < (n / (k - 1)); i++) {
            System.out.println(i);
            boolean subResult = true;
            for (int j = 0; j < k; j++) {
                System.out.print("\t" + j);
                int index = (((k - 1) * i + j) % n);
                System.out.print(" = " + index);
                int v = map.get(Integer.valueOf(index));
                v++;
                map.put(Integer.valueOf(index), Integer.valueOf(v));
	   /*
	   boolean subsubresult = values[index] == 1;
	   if (!subsubresult)
	   {
	    subResult = false;
	    break;
	   }
	   */
            }
            System.out.print("\n");
            if (subResult)
                result = true;
        }

        for (int i = 0; i <= 100; i++)
            System.out.println(i + " % : " + map.get(Integer.valueOf(i)));
        return !result;
    }

    public void testPy() {
        System.out.println("Start of analyse (with (Math.random() > 0.5) (was '<' before)");
        double i;
        double r = 0;
        for (i = 1; i <= tenBillion; i++) {
            if (test(100, 6))
                r++;
            if (i % oneBillion == 0) {
                System.out.println("Analyze at step n°" + i + " r=" + r);
            }
        }
        System.out.println("End of analyse");
        System.out.println("Analyze at step n°" + i + " r=" + r);
    }

}
