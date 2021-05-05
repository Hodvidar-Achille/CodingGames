package com.hodvidar.utils.geometry;

public class Tetraedre {

    public static void main(final String[] args) {
        System.out.println("Hello World");
        /*
         * Tetraedre DEFX de base DEF (côtés d, e et f) et X surrélevé avec a, b et c les côté entre
         * DX, EX, FX Volume du tétraedre : V = 1/12 * racine(P-Q+R) Avec P = 4 a² b² c² Q = a². E²
         * + b². F² + c². D² R = D . E . F D = a² + b² - d² E = b² + c² - e² F = a² + c² - f² or si
         * X est dans le triangle DEF, V = 0 ainsi X dans le plan correspond à : P-Q+R = 0
         */
        final double e = 273;
        final double f = 273;
        final double d = 273;
        final double e2 = e * e;
        final double f2 = f * f;
        final double d2 = d * d;
        double a2 = 0;
        double b2 = 0;
        double c2 = 0;
        for (double a = 0; a < 273; a++) {
            if (a % 27 == 0)
                System.out.println("a = " + a + " calculating...");
            for (double b = 0; b < 273; b++) {
                for (double c = 0; c < 273; c++) {
                    a2 = a * a;
                    b2 = b * b;
                    c2 = c * c;
                    final double p = 4 * a2 * b2 * c2;
                    final double q1 = a2 * ((b2 + c2 - e2) * (b2 + c2 - e2));
                    final double q2 = b2 * ((a2 + c2 - f2) * (a2 + c2 - f2));
                    final double q3 = c2 * ((a2 + b2 - d2) * (a2 + b2 - d2));
                    final double q = q1 + q2 + q3;
                    final double r1 = b2 + c2 - e2;
                    final double r2 = a2 + c2 - f2;
                    final double r3 = a2 + b2 - d2;
                    final double r = r1 * r2 * r3;
                    final double result = p - q + r;
                    if (result == 0) {
                        System.out.println("--- TRUE a = " + a + " b = " + b + " c = " + c + " ---");
                    }
                }
            }
        }
        System.out.println("Bye World");
    }
}
