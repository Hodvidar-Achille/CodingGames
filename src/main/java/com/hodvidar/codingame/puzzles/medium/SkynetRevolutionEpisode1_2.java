package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 100%
 * by Hodvidar
 **/
class SkynetRevolutionEpisode1_2 {

    public static void main(final String[] args) {
        final SkynetRevolutionEpisode1_2 s = new SkynetRevolutionEpisode1_2();
        s.test();
    }

    private void test() {
        @SuppressWarnings("resource") final Scanner in = new Scanner(System.in);
        final int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        final int L = in.nextInt(); // the number of links
        final int E = in.nextInt(); // the number of exit gateways
        final List<Integer> nodes = new ArrayList<>();
        final List<Edge> edges = new ArrayList<>();
        System.err.println("    nb nodes = " + N + " nb links = " + L + " nb exits = " + E);
        for (int i = 0; i < L; i++) {
            final int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            final int N2 = in.nextInt();
            if (!nodes.contains(N1))
                nodes.add(N1);
            if (!nodes.contains(N2))
                nodes.add(N2);
            final Edge edge = new Edge(N1, N2);
            edges.add(edge);
            System.err.println(N1 + " <-> " + N2);
        }
        final List<Integer> exists = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            final int EI = in.nextInt(); // the index of a gateway node
            exists.add(EI);
            System.err.println(EI + " --> ");
        }

        final List<Edge> edges2 = new ArrayList<>(edges);
        // build List of exists Edges
        final List<Edge> existEdges = new ArrayList<>();

        for (final Edge ed : edges2) {
            for (final Integer ex : exists) {
                if (ed.getDestination() == ex || ed.getSource() == ex) {
                    existEdges.add(ed);
                    final int n1 = ed.getSource();
                    final int n2 = ed.getDestination();
                    System.err.println("existEdge : " + n1 + " - " + n2);
                }
            }
        }

        // game loop
        while (true) {
            final int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("XoX in " + SI);
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // GO !
            // if XoX near a existEdge, cut it
            boolean doCut = false;
            int n1 = -1;
            int n2 = -1;
            Edge edgeToRemove = null;
            for (final Edge ed : existEdges) {
                if (ed.getSource() == SI || ed.getDestination() == SI) {
                    n1 = ed.getSource();
                    n2 = ed.getDestination();
                    edgeToRemove = ed;
                    doCut = true;
                    break;
                }
            }

            // if not cut a edge
            if (doCut) {
                existEdges.remove(edgeToRemove);
            } else {
                for (final Edge ed : existEdges) {
                    n1 = ed.getSource();
                    n2 = ed.getDestination();
                    existEdges.remove(ed);
                    break;
                }
            }

            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            System.out.println(n1 + " " + n2);
        }
    }

    // --------------------------- INTERNAL CLASSES ----------------------
    class Edge {
        private final int source;
        private final int destination;

        public Edge(final int aSource, final int aDestination) {
            this.source = aSource;
            this.destination = aDestination;
        }

        public int getSource() {
            return this.source;
        }

        public int getDestination() {
            return this.destination;
        }

    }
}

