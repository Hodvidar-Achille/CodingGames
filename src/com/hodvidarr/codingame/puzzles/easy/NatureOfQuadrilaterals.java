package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

import com.hodvidarr.utils.geometry.*;
/**
 *    https://www.codingame.com/ide/puzzle/nature-of-quadrilaterals
 * by Hodvidar.
 **/
class NatureOfQuadrilaterals {

	public static void main(String args[])
	{
		NatureOfQuadrilaterals d = new NatureOfQuadrilaterals();
		d.test();
	}
	
	public void test()
	{
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            String A = in.next();
            int xA = in.nextInt();
            int yA = in.nextInt();
            String B = in.next();
            int xB = in.nextInt();
            int yB = in.nextInt();
            String C = in.next();
            int xC = in.nextInt();
            int yC = in.nextInt();
            String D = in.next();
            int xD = in.nextInt();
            int yD = in.nextInt();
            
            Point a = new Point(xA, yA);
            Point b = new Point(xB, yB);
            Point c = new Point(xC, yC);
            Point d = new Point(xD, yD);
            String result = A+B+C+D +" is a " + GeometryServices.getQuadrilateralType(a, b, c, d).toLowerCase()+".";
            System.out.println(result);
        }
        in.close();
    }
	
}

