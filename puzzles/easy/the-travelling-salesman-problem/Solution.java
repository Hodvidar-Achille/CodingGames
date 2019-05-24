import java.util.*;
import java.io.*;
import java.math.*;

/*
 * Note  : "Greedy algorithm"
 * "Starts at the first input given and always chooses the nearest point from the current point"
 * "Not the best algorith for this problem but given a reasonnable answer".
 */
/**
 * 	https://www.codingame.com/ide/puzzle/the-travelling-salesman-problem
 * by Hodvidar.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        System.err.println("N: "+N);
        int sumDistance = 0;
        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) 
        {
            int X = in.nextInt();
            int Y = in.nextInt();
            System.err.println("X: "+X+"  Y: "+Y);
            nodes[i] = new Node(new Point(X, Y), i);
        }
        
        Graph graph = new Graph(nodes);
        double totalDistance = 0;
        Node n1 = nodes[0];
        do
        {
            // System.err.println("Searching closest for node("+n1.p.x+", "+n1.p.y+")");
            Node closestNode = null;
            double minDistance = Double.MAX_VALUE;
            for (int j = 0; j < N; j++) 
            {
                Node n2 = nodes[j];
                // skip itself
                if(n2.equals(n1))
                    continue;
                // skip nodes that have already been connected
                if(n2.isAlreadyConnected())
                    continue;
                // skip latest node (XXX : bug)
                // if(n2.equals(n1.getPrevious()));
                //    continue;
                // skip latest node ancestor except if its finish the loop
                if(!graph.canNodesConnect(n1, n2))
                    continue;
                // System.err.println("j: "+j);
                double distance = Node.getDistance(n1.p, n2.p);
                if(graph.acceptNode(closestNode, minDistance, n2, distance))
                {
                    minDistance = distance;
                    closestNode = n2;
                }
            }
            n1.connect(closestNode);
            totalDistance += minDistance;
            n1 = closestNode;
        } while(!n1.equals(nodes[0]));

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
         System.err.println("Exact totalDistance (double): "+totalDistance);
        System.out.println((int) Math.round(totalDistance));
    }
    
}

class Graph
{
    public final Node[] nodes;
    public final int size;
    
    public Graph(Node[] nodes)
    {
        this.nodes = nodes;
        this.size = nodes.length;
    }
    
    public boolean canNodesConnect(Node n1, Node n2)
    {
        int ancestorLevel = n1.isInPreviousAncestors(n2);
        // System.err.println("canNodesConnect... ancestorLevel="+ancestorLevel);
        // -1 : not in ancestor
        // size -2 : it will end the loop formed by connected nodes.
        return ancestorLevel == -1 || ancestorLevel == this.size-2;
    }
    
    public boolean acceptNode(Node closedtNode, double minDistance, Node newNode, double newDistance)
    {
        if(closedtNode == null)
            return true;
     
        if(newDistance > minDistance)
            return false;
        
        if(newDistance < minDistance)
            return true;
            
        // equals
        return newNode.position < closedtNode.position;
        
    }
}

class Node
{
    public final Point p;
    public final int position;
    private Node previous;
    private Node next;
    private double distanceToNext = 0;
    
    public Node(Point p, int position)
    {
        this.p = p;
        this.position = position;
    }
    
    public Node getPrevious()
    {
        return this.previous;
    }
    
    public void setPrevious(Node n)
    {
        this.previous = n;
    }
    
    public void connect(Node n)
    {
        this.next = n;
        this.distanceToNext = getDistance(this.p, n.p);
        n.setPrevious(this);
        System.err.println("Node("+this.p.x+", "+this.p.y+") is connected with node("+n.p.x+", "+n.p.y+") with distance="+this.distanceToNext);
    }
    
    public boolean isAlreadyConnected()
    {
        return this.previous != null;
    }
    
    /**
     *  Returns -1 if Node n is not in previous ancestors, if it is, given at which level it is.
     * Returns 0 if the first node has node 'n' has ancestor;
     */
    public int isInPreviousAncestors(Node n)
    {
        if(this.previous == null)
            return -1;
        if(n.equals(this.previous))
            return 0;
        int ancestorCount = this.previous.isInPreviousAncestors(n);
        if(ancestorCount == -1)
            return -1;
        return 1 + ancestorCount;
    }
    
    public static double getDistance(Point p1, Point p2)
	{
		double deltaX = (p1.x - p2.x);
		double deltaY = (p1.y - p2.y);
		
		deltaX = Math.pow(deltaX, 2);
		deltaY = Math.pow(deltaY, 2);

		return Math.sqrt(deltaX + deltaY);
    }
    
    @Override
	public boolean equals(Object obj)
	{
		// System.err.println("Node.equals(obj)...");
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;

		Node n = (Node) obj;
		if(this.position != n.position)
		    return false;
		return this.p.equals(n.p);
	}

	@Override
	public int hashCode()
	{
		return (int) (31 * this.p.x + 89 * this.p.y);
	}
}

class Point
{
	public final double x;
	public final double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj)
	{
		// System.err.println("Point.equals(obj)...");
		if (obj == null)
			return false;
		if (!(obj instanceof Point))
			return false;

		Point p = (Point) obj;
		// System.err.println("x1:"+this.x+" y1:"+this.y+"   x2:"+p.x+" y2:"+p.y);
		return this.x == p.x && this.y == p.y;
	}

	@Override
	public int hashCode()
	{
		return (int) (31 * this.x + 89 * this.y);
	}
}
