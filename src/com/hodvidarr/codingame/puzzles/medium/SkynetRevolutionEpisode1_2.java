package com.hodvidarr.codingame.puzzles.medium;
import java.util.*;

/**
 * 100%
 * by Hodvidar
 **/
class SkynetRevolutionEpisode1_2 
{

    public static void main(String args[]) 
    {
    	SkynetRevolutionEpisode1_2 s = new SkynetRevolutionEpisode1_2();
    	s.test();
    }
    
    private void test()
    {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        List<Integer> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        System.err.println("    nb nodes = "+N+" nb links = "+L+" nb exits = "+E);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            if(!nodes.contains(N1))
                nodes.add(N1);
            if(!nodes.contains(N2))
                nodes.add(N2);
            Edge edge = new Edge(N1, N2);
            edges.add(edge);
            System.err.println(N1+" <-> "+N2);
        }
        List<Integer> exists = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            exists.add(EI);
            System.err.println(EI+" --> ");
        }

        List<Edge> edges2 = new ArrayList<>(edges);
        // build List of exists Edges
        List<Edge> existEdges = new ArrayList<>();
        
        for(Edge ed : edges2)
        {
            for(Integer ex : exists)
            {
                if(ed.getDestination() == ex || ed.getSource() == ex)
                {
                    existEdges.add(ed);
                    int n1 = ed.getSource();
                    int n2 = ed.getDestination();
                    System.err.println("existEdge : "+n1+" - "+n2);
                }
            }
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("XoX in "+SI);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            // GO !
            // if XoX near a existEdge, cut it
            boolean doCut = false;
            int n1 = -1;
            int n2 = -1;
            Edge edgeToRemove = null;
            for(Edge ed : existEdges)
            {
                if(ed.getSource() == SI || ed.getDestination() == SI)
                {
                    n1 = ed.getSource();
                    n2 = ed.getDestination();
                    edgeToRemove = ed;
                    doCut = true;
                    break;
                }
            }
            
            // if not cut a edge
            if(doCut)
            {
                existEdges.remove(edgeToRemove);
            }
            else
            {
                for(Edge ed : existEdges)
                {
                    n1 = ed.getSource();
                    n2 = ed.getDestination();
                    existEdges.remove(ed);
                    break;
                }
            }
            
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            System.out.println(n1+" "+n2);
        }
    }
    
    // --------------------------- INTERNAL CLASSES ----------------------
    class Edge
    {
        private int source;
        private int destination;
        
        public Edge(int aSource, int aDestination)
        {
            this.source = aSource;
            this.destination = aDestination;
        }
        
        public int getSource()
        {
            return this.source;
        }
        
        public int getDestination()
        {
            return this.destination;
        }
        
    }
}

