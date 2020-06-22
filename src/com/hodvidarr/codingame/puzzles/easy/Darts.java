package com.hodvidarr.codingame.puzzles.easy;

import java.util.*;
import com.hodvidarr.utils.geometry.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
public class Darts
{
	private static final boolean VERBOSE = false;
	
	public static void printIfVerbose(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}
	
	public static void main(String[] args)
	{
		Darts d = new Darts();
		d.test();
	}
	
	public void test()
	{
		Scanner in = new Scanner(System.in);
		int SIZE = in.nextInt();
		printIfVerbose("SIZE: " + SIZE);
		int N = in.nextInt();
		printIfVerbose("N: " + N);
		Map<String, Player> playersFast = new HashMap<>();
		List<Player> players = new ArrayList<>();
		if (in.hasNextLine())
		{
			String nothing = in.nextLine();
			printIfVerbose("nothing: " + nothing);
		}
		for (int i = 0; i < N; i++)
		{
			String name = in.nextLine();
			printIfVerbose("name: " + name);
			Player p = new Player(name, i);
			playersFast.put(name, p);
			players.add(p);
		}

		Point center = new Point(0, 0);
		Target target = new Target(center, SIZE);
		printSquareInfos(target.square);
		printCircleInfos(target.circle);
		printSquareInfos(target.diamond);

		int T = in.nextInt();
		printIfVerbose("T: " + T);
		for (int i = 0; i < T; i++)
		{
			String throwName = in.next();
			int throwX = in.nextInt();
			int throwY = in.nextInt();
			printIfVerbose("throwName: " + throwName + " - throwX: " + throwX + " - throwY:"
				+ throwY);
			int points = target.getScore(new Point(throwX, throwY));
			playersFast.get(throwName).addScore(points);
		}

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");
		// Player list will be sorted because Player impl Comparable.
		Collections.sort(players);
		for (Player p : players)
			System.out.println(p.name + " " + p.getScore());

		in.close();
	}

	private static void printSquareInfos(Square sq)
	{
		printIfVerbose("printSquareInfos...");
		printIfVerbose("Center(" + sq.center.x + ", " + sq.center.y + ") side=" + sq.side
			+ " angle=" + sq.angle);
		printIfVerbose("Points :(" + sq.points[0].x + ", " + sq.points[0].y + ")," + "("
			+ sq.points[1].x + ", " + sq.points[1].y + ")," + "(" + sq.points[2].x + ", "
			+ sq.points[2].y + ")," + "(" + sq.points[3].x + ", " + sq.points[3].y + ")");
	}

	private static void printCircleInfos(Circle c)
	{
		printIfVerbose("printCircleInfos...");
		printIfVerbose("Center(" + c.center.x + ", " + c.center.y + ") radius=" + c.radius);
	}
	
	// ------------------ Internal Classes ------------------
	
	class Player implements Comparable<Player>
	{
		public final int order;
		public final String name;
		private int score = 0;

		public Player(String name, int order)
		{
			this.name = name;
			this.order = order;
		}

		public void addScore(int points)
		{
			this.score += points;
		}

		public int getScore()
		{
			return this.score;
		}

		@Override
		public int compareTo(Player o)
		{
			if (this.score < o.score)
				return 1;
			if (this.score > o.score)
				return -1;

			if (this.order < o.order)
				return -1;
			if (this.order > o.order)
				return 1;

			return 0;
		}
	}

	class Target
	{
		public final Point center;
		public final double side;

		public final Square square;
		public final Circle circle;
		public final Square diamond;

		public Target(Point p, double side)
		{
			this.center = p;
			this.side = side;

			this.square = new Square(this.center, this.side, 0d);
			double circleRadius = this.side / 2;
			this.circle = new Circle(this.center, circleRadius);
			double diamondSide = Math.sqrt((circleRadius * circleRadius)
				+ (circleRadius * circleRadius));
			this.diamond = new Square(this.center, diamondSide, 45);
		}

		public int getScore(Point dart)
		{
			printIfVerbose("Target.getScore...");
			if (this.diamond.isInside(dart))
				return 15;
			if (this.circle.isInside(dart))
				return 10;
			if (this.square.isInside(dart))
				return 5;
			return 0;
		}
	}

}

