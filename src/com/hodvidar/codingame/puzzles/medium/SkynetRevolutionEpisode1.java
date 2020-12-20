package com.hodvidar.codingame.puzzles.medium;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/skynet-revolution-episode-1
 * 75%/100% for now.
 * by Hodvidar
 **/
class SkynetRevolutionEpisode1 {

    public static void main(String[] args) {
        SkynetRevolutionEpisode1 s = new SkynetRevolutionEpisode1();
        s.test();
    }

    private void test() {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        Map<Integer, Node> nodesMap = new HashMap<>();
        List<Integer> nodesList = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        System.err.println("    nb nodes = " + N + " nb links = " + L + " nb exits = " + E);
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            if (!nodesMap.containsKey(N1))
                nodesMap.put(N1, new Node(N1));
            if (!nodesMap.containsKey(N2))
                nodesMap.put(N2, new Node(N2));
            nodesMap.get(N1).connectDouble(nodesMap.get(N2), 1);
            if (!nodesList.contains(N1))
                nodesList.add(N1);
            if (!nodesList.contains(N2))
                nodesList.add(N2);
            Edge edge = new Edge(N1, N2);
            edges.add(edge);
            System.err.println(N1 + " <-> " + N2);
        }
        List<Integer> exists = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            exists.add(EI);
            System.err.println(EI + " --> ");
        }

        Node[] nodes = new Node[nodesMap.size()];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = nodesMap.get(i);

        List<Edge> edges2 = new ArrayList<>(edges);
        // build List of exists Edges
        List<Edge> existEdges = new ArrayList<>();

        for (Edge ed : edges2) {
            for (Integer ex : exists) {
                if (ed.getDestination() == ex || ed.getSource() == ex) {
                    existEdges.add(ed);
                    int n1 = ed.getSource();
                    int n2 = ed.getDestination();
                    System.err.println("existEdge : " + n1 + " - " + n2);
                }
            }
        }
        ArrayList<Node> path = null;
        DijkstraNode dijkstra = new DijkstraNode();
        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("XoX in " + SI);

            // 1) if SI near an exist :
            boolean doEmergencyCut = false;
            int n1 = -1;
            int n2 = -1;
            Edge edgeToRemove = null;
            for (Edge ed : existEdges) {
                if (ed.getSource() == SI || ed.getDestination() == SI) {
                    n1 = ed.getSource();
                    n2 = ed.getDestination();
                    edgeToRemove = ed;
                    doEmergencyCut = true;
                    break;
                }
            }
            if (doEmergencyCut) {
                System.err.println("### doEmergencyCut ! ####");
                nodes = removeAndCut(existEdges, nodesMap, edgeToRemove, nodes);
            }
            // 2) else find closest exist and close it
            else {
                System.err.println("### Computing path ####");
                long t1 = System.currentTimeMillis();
                final int[] pred = dijkstra.dijkstra(nodes, SI);
                List<ArrayList<Node>> paths = new ArrayList<>();
                for (int e : exists) {
                    path = dijkstra.getPath(nodes, pred, SI, e);
                    paths.add(path);
                    if (path.size() == 2)
                        break;
                }
                int minLength = Integer.MAX_VALUE;
                for (ArrayList<Node> aPath : paths) {
                    if (aPath.size() < minLength)
                        path = aPath;
                }
                // the 2 last elems are the 2 nodes/1 edge to remove
                Node node1 = path.get(path.size() - 1);
                Node node2 = path.get(path.size() - 2);
                nodes = removeAndCut(existEdges, node1, node2, nodes);
                n1 = node1.getValue();
                n2 = node2.getValue();
                long t2 = System.currentTimeMillis();
                System.err.println("Finding path finished | took: " + (t2 - t1) + "ms");
            }

            // if SI not in front of a exist : calculate

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // Example: 0 1 are the indices of the nodes you wish to sever the link between

            System.out.println(n1 + " " + n2);
        }
    }

    private Node[] removeAndCut(
            List<Edge> existEdges,
            Map<Integer, Node> nodesMap,
            Edge edgeToRemove,
            Node[] nodes) {
        existEdges.remove(edgeToRemove);
        // <-> consistency between edges and nodes
        int n1 = edgeToRemove.getSource();
        int n2 = edgeToRemove.getDestination();
        Node node1 = nodesMap.get(n1);
        Node node2 = nodesMap.get(n2);
        node1.cutCouble(node2);
        if (node1.isOprhan() || node2.isOprhan())
            nodes = cutFromGraphOrphanNodes(nodes);
        return nodes;
    }

    private Node[] removeAndCut(List<Edge> existEdges, Node node1, Node node2, Node[] nodes) {
        node1.cutCouble(node2);
        int n1 = node1.getValue();
        int n2 = node2.getValue();
        Edge edgeToRemove = null;
        for (Edge ed : existEdges) {
            if (ed.getSource() == n1 && ed.getDestination() == n2 || ed.getSource() == n2
                    && ed.getDestination() == n1) {
                edgeToRemove = ed;
                break;
            }
        }
        existEdges.remove(edgeToRemove);
        if (node1.isOprhan() || node2.isOprhan())
            nodes = cutFromGraphOrphanNodes(nodes);
        return nodes;
    }

    private Node[] cutFromGraphOrphanNodes(Node[] nodes) {
        boolean needReplace = false;
        int l = nodes.length;
        Node[] replacement = new Node[l - 1];
        int i = 0;
        int valueLost = -1;
        for (Node n : nodes) {
            if (n.isOprhan()) {
                needReplace = true;
                valueLost = n.getValueVar();
                continue;
            }
            replacement[i] = n;
            i++;
        }
        if (needReplace) {
            nodes = replacement;
            for (Node n : nodes) {
                if (n.getValueVar() > valueLost) {
                    n.setValueVar(n.getValueVar() - 1);
                }
            }
        }

        return nodes;
    }

    // ---------------------- INTERNAL CLASSES --------------------------------
    class Node {
        private final int value;
        private final Map<Node, Integer> edges;
        private int valueVar;

        public Node(int val) {
            this.value = val;
            this.valueVar = val;
            this.edges = new HashMap<>();
        }

        public void connectDouble(Node aNode, int weight) {
            this.edges.put(aNode, weight);
            aNode.connect(this, weight);
        }

        public void connect(Node aNode, int weight) {
            this.edges.put(aNode, weight);
        }

        public void cutCouble(Node aNode) {
            aNode.cut(this);
            this.edges.remove(aNode);
        }

        public void cut(Node aNode) {
            this.edges.remove(aNode);
        }

        public int getValue() {
            return this.value;
        }

        public int getValueVar() {
            return this.valueVar;
        }

        public void setValueVar(int var) {
            this.valueVar = var;
        }

        public Map<Node, Integer> getEdges() {
            return this.edges;
        }

        public int getWeight(Node dest) {
            return this.edges.get(dest);
        }

        public boolean isOprhan() {
            return this.edges.isEmpty();
        }
    }

    class DijkstraNode {
        public int[] dijkstra(Node[] nodes, int s) {
            final int[] dist = new int[nodes.length]; // shortest known distance from "s"
            final int[] pred = new int[nodes.length]; // preceeding node in path
            final boolean[] visited = new boolean[nodes.length];

            for (int i = 0; i < dist.length; i++) {
                dist[i] = Integer.MAX_VALUE;
            }
            dist[s] = 0;

            for (int i = 0; i < dist.length; i++) {
                final int next = minVertex(dist, visited);
                final Node nextN = nodes[next];
                visited[next] = true;

                final Map<Node, Integer> n = nextN.getEdges();
                for (Node v : n.keySet()) {
                    int vv = v.getValueVar();
                    final int d = dist[next] + nextN.getWeight(v);
                    if (dist[vv] > d) {
                        dist[vv] = d;
                        pred[vv] = next;
                    }
                }
            }

            // return array of nodes (source to end)
            return pred;
        }

        private int minVertex(int[] dist, boolean[] v) {
            int x = Integer.MAX_VALUE;
            int y = -1; // graph not connected, or no unvisited vertices
            for (int i = 0; i < dist.length; i++) {
                if (!v[i] && dist[i] < x) {
                    y = i;
                    x = dist[i];
                }
            }
            return y;
        }

        public ArrayList<Node> getPath(Node[] nodes, int[] pred, int s, int dest) {
            final ArrayList<Node> path = new ArrayList<>();
            int x = dest;
            while (x != s) {
                path.add(0, nodes[x]);
                x = pred[x];
            }
            path.add(0, nodes[s]);
            return path;
        }
    }

    class Edge {
        private final int source;
        private final int destination;

        public Edge(int aSource, int aDestination) {
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