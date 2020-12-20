package com.hodvidar.codingame.puzzles.easy;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;

import java.util.Scanner;

/**
 *      https://www.codingame.com/ide/puzzle/dead-mens-shot
 *  by Hodvidar
 **/
public class DeadMenshots 
{

    private static final String HIT = "hit";
    private static final String MISS = "miss";
    
    public static void main(String[] args)
	{
    	DeadMenshots d = new DeadMenshots();
		d.test();
	}
	
	public void test()
	{
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        Point[] polygon = new Point[N];
        System.err.println("Polygon of N points : "+N);
        int max_X = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) 
        {
            int x = in.nextInt();
            int y = in.nextInt();
            System.err.println((i+1)+": x="+x+"  -  y="+y);
            polygon[i] = new Point(x, y);
            if(x > max_X)
                max_X = x;
        }
        int M = in.nextInt();
        
        System.err.println("max_X="+max_X);
        GeometryServices gs = new GeometryServices(max_X);
        
        System.err.println("Number of shoots : "+M);
        for (int i = 0; i < M; i++) 
        {
            int x = in.nextInt();
            int y = in.nextInt();
            
            System.err.println((i+1)+": x="+x+"  -  y="+y);
            Point shoot = new Point(x, y);
            if(gs.isInside(polygon, N, shoot))
                System.out.println(HIT);
            else
                System.out.println(MISS);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        // System.out.println("hit_or_miss");
        in.close();
    }

}

