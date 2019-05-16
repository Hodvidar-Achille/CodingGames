import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/skynet-revolution-episode-2
 * by Hodvidar
 **/
class Player
{

    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        Map<Integer, Node> nodesMap = new HashMap<>();
        System.err.println("    nb nodes = " + N + " nb links = " + L + " nb exits = " + E);
        for (int i = 0; i < L; i++)
        {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            if (!nodesMap.containsKey(N1))
                nodesMap.put(N1, new Node(N1));
            if (!nodesMap.containsKey(N2))
                nodesMap.put(N2, new Node(N2));
            nodesMap.get(N1).connectDouble(nodesMap.get(N2));
            //System.err.println(N1 + " <-> " + N2);
        }
        List<Integer> exists = new ArrayList<>();
        for (int i = 0; i < E; i++)
        {
            int EI = in.nextInt(); // the index of a gateway node
            exists.add(EI);
            nodesMap.get(EI).becomeExist();
            //System.err.println(EI + " --> ");
        }

        Map<Integer, Node> dangerousNode = new HashMap<>();
        for(Node n : nodesMap.values())
        {
            if(n.isNearExist())
                dangerousNode.put(n.getValue(), n);
        }
        // game loop
        outer: while (true)
        {
            for(Node n : new ArrayList<>(dangerousNode.values()))
            {
                if(n.getLevel() < 1)
                    dangerousNode.remove(n.getValue());
            }
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
            System.err.println("XoX in " + SI);
            Node nSI = nodesMap.get(SI);

            if(dangerousNode.containsKey(SI))
            {
                System.err.println("### EmergencyCut ###");
                nSI.cutExist();
                continue outer;
            }

            for(Node n : nSI.getVoisins())
            {
                if(n.getLevel() > 1)
                {
                    System.err.println("### Pre-EmergencyCut ###");
                    n.cutExist();
                    continue outer;
                }
            }

            //TODO a optimize dangerous cut
            // calculate the closest node of SI with level > 1
            // with node having contact with exist weight == 0
            // because of the emergency cut
            nSI.setAllDistance(0);
            int minDist = Integer.MAX_VALUE;
            for(Node n : dangerousNode.values()){
                int d = n.getDist();
                if(d != 0 && d < minDist)
                    minDist = d;
            }
            for(Node n : dangerousNode.values()){
                if(n.getDist() == minDist && n.getLevel() > 1)
                {
                    System.err.println("### Optimize cut of dangerous link ###");
                    n.cutExist();
                    continue outer;
                }
            }
            nSI.resetAllDistance();


            System.err.println("### Random cut of dangerous link ###");
            int max = 0;
            for(Node n : dangerousNode.values())
            {
                if(n.getLevel() > max)
                    max = n.getLevel();
            }
            for(Node n : dangerousNode.values())
            {
                if(n.getLevel() == max)
                {
                    n.cutExist();
                    continue outer;
                }
            }



            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // Example: 0 1 are the indices of the nodes you wish to sever the link between

            // System.out.println(n1 + " " + n2); // in doCut
        }
    }
}

class Node
{
    private int value;
    private int dist = Integer.MAX_VALUE;
    private int level = 0;
    private List<Node> voisins;
    private boolean isExist = false;

    public Node(int val)
    {
        this.value = val;
        this.voisins = new ArrayList<>();
    }


    public void setAllDistance(int d)
    {
       this.dist = d;
       for(Node child : this.voisins)
       {
            //if(child.getDist() != Integer.MAX_VALUE)
            //    continue;
            int dd;
            if(this.getLevel() > 0)
                dd = d;
            else
                dd = d+1;
                
            if(child.getDist() <= dd)
                continue;
            child.setAllDistance(dd);
                
        }
    }

    public void resetAllDistance()
    {
        this.dist = Integer.MAX_VALUE;
        for(Node child : this.voisins)
        {
            if(child.getDist() == Integer.MAX_VALUE)
                continue;
            child.resetAllDistance();
        }
    }

    public int getDist()
    {
        return this.dist;
    }

    public void setDist(int d)
    {
        this.dist = d;
    }

    public void connectDouble(Node aNode)
    {
        this.voisins.add(aNode);
        aNode.connect(this);
    }

    public void connect(Node aNode)
    {
        this.voisins.add(aNode);
    }

    public void cutDouble(Node aNode)
    {
        aNode.cut(this);
        this.voisins.remove(aNode);
    }

    public void cut(Node aNode)
    {
        this.voisins.remove(aNode);
    }

    public void cutExist()
    {
        int maxLevel = 0;
        for(Node n : this.voisins)
        {
            if(n.isExist())
            {
                this.cutDouble(n);
                this.removeLevel();
                System.err.println("---Cutting "+this.getValue()+" <-> "+n.getValue()+" ---");
                System.out.println(this.getValue() + " " + n.getValue());
                return;
            }
        }
    }

    public int getLevel()
    {
        return this.level;
    }

    public void addLevel()
    {
        this.level+=1;
    }

    public void removeLevel()
    {
        this.level-=1;
    }

    public int getValue()
    {
        return this.value;
    }

    public List<Node> getVoisins()
    {
        return this.voisins;
    }

    public boolean isExist()
    {
        return this.isExist;
    }

    public boolean isNearExist()
    {
        for(Node n : this.voisins)
            if(n.isExist())
                return true;
        return false;
    }

    public void becomeExist()
    {
        this.isExist = true;
        for(Node n : this.voisins)
            n.addLevel();
    }
}
