package com.hodvidar.utils.geometry;
public class Tetraedre {

     public static void main(String []args){
        System.out.println("Hello World");
        /*
        Tetraedre DEFX de base DEF (côtés d, e et f) 
        et X surrélevé avec a, b et c les côté entre DX, EX, FX       
        Volume du tétraedre : 
        V = 1/12 * racine(P-Q+R)
        Avec
        P = 4 a² b² c²
        Q = a². E² + b². F² + c². D²
        R = D . E . F
        D = a² + b² - d²
        E = b² + c² - e²
        F = a² + c² - f²
        or si X est dans le triangle DEF, V = 0
        ainsi X dans le plan correspond à : P-Q+R = 0
        */
        double e = 273;
        double f = 273;
        double d = 273;
        double e2 = e*e;
        double f2 = f*f;
        double d2 = d*d;
        double a2 = 0;
        double b2 = 0;
        double c2 = 0;
        for(double a = 0; a < 273; a++)
        {
            if(a % 27 == 0)
                System.out.println("a = "+a+" calculating...");
            for(double b = 0; b < 273; b++)
            {
                for(double c = 0; c < 273; c++)
                {
                    a2 = a*a;
                    b2 = b*b;
                    c2 = c*c;
                    double p = 4*a2*b2*c2;
                    double q1 = a2*((b2+c2-e2)*(b2+c2-e2));
                    double q2 = b2*((a2+c2-f2)*(a2+c2-f2));
                    double q3 = c2*((a2+b2-d2)*(a2+b2-d2));
                    double q = q1+q2+q3;
                    double r1 = b2+c2-e2;
                    double r2 = a2+c2-f2;
                    double r3 = a2+b2-d2;
                    double r = r1*r2*r3;
                    double result = p - q + r;
                    if(result == 0)
                    {
                         System.out.println("--- TRUE a = "+a+" b = "+b+" c = "+c+" ---");
                    }
                }    
            }
        }
        System.out.println("Bye World");
     }
}
