package com.hodvidar.codingame.puzzles.easy;
import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;

import java.util.Scanner;
/**
 *    https://www.codingame.com/ide/puzzle/nature-of-quadrilaterals
 * by Hodvidar.
 **/
class NatureOfQuadrilaterals {

    public static void main(String[] args)
    {
        NatureOfQuadrilaterals d = new NatureOfQuadrilaterals();
        d.test();
    }

    public void test()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the name of the first point: ");
        String A = in.next();
        System.out.println("Enter its X coordinate (ex: 1 or 157.12 ): ");
        double xA = in.nextDouble();
        System.out.println("Enter its Y coordinate: ");
        double yA = in.nextDouble();

        System.out.println("Enter the name of the second point: ");
        String B = in.next();
        System.out.println("Enter its X coordinate: ");
        double xB = in.nextDouble();
        System.out.println("Enter its Y coordinate: ");
        double yB = in.nextDouble();

        System.out.println("Enter the name of the third point: ");
        String C = in.next();
        System.out.println("Enter its X coordinate: ");
        double xC = in.nextDouble();
        System.out.println("Enter its Y coordinate: ");
        double yC = in.nextDouble();

        System.out.println("Enter the name of the fourth point: ");
        String D = in.next();
        System.out.println("Enter its X coordinate: ");
        double xD = in.nextDouble();
        System.out.println("Enter its Y coordinate: ");
        double yD = in.nextDouble();

        Point a = new Point(xA, yA);
        Point b = new Point(xB, yB);
        Point c = new Point(xC, yC);
        Point d = new Point(xD, yD);
        String result = A+B+C+D +" is a " + GeometryServices.getQuadrilateralType(a, b, c, d).toLowerCase()+".";
        System.out.println(result);
        in.close();
    }

}

