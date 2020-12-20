package com.hodvidar.mdf.hackathon.y2019.orange;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * https://www.isograd.com/FR/solutionconcours.php?contest_id=50
 * by Hodvidar
 * TODO : find another solution, this one take way too long (>1 x hours for 8+ paths).
 */
public final class RobotPerdus_tooSlow {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 10;
    private static final String INPUT_DIRECTORY = "robots_perdus_input";

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        RobotPerdus_tooSlow r = new RobotPerdus_tooSlow();
        for (int i = 1; i <= NUMBER_OF_TEST; i++) {
            printIfVerbose("\n\n TEST n°" + i);
            String result = r.test("resources\\" + INPUT_DIRECTORY + "\\input" + i + ".txt");
            // --- CHECKING ---
            File file2 = new File("resources\\" + INPUT_DIRECTORY + "\\output" + i + ".txt");
            // Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(file2);
            String line2 = sc2.nextLine();
            printIfVerbose("Solution is: \n" + line2);
            if (result.equals(line2))
                printIfVerbose("SUCCESS!");
            else
                System.err.println("FAILURE!");
            sc2.close();
        }
    }

    private String test(String inputFile) throws FileNotFoundException {
        File file = new File(inputFile);
        // --- INPUT ---

        String line = "";
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");
        int counter = 0;
        int numberOfStation = 0;
        int numberOfTunnel = 0;
        int stationValueOfAlgotron = 0;
        int stationValueOfPolynomialator = 0;
        Map<Integer, Node> stations = new HashMap<>();
        while (sc.hasNextLine()) {
            counter++;
            line = sc.nextLine();
            printIfVerbose("line=" + line);
            if (counter == 1) {
                String[] line2 = line.split("\\s+");
                numberOfStation = Integer.parseInt(line2[0]);
                numberOfTunnel = Integer.parseInt(line2[1]);
                continue;
            }
            if (counter == 2) {
                String[] line2 = line.split("\\s+");
                stationValueOfAlgotron = Integer.parseInt(line2[0]);
                stationValueOfPolynomialator = Integer.parseInt(line2[1]);
                continue;
            }

            // Build network of Station and Tunnel (Node & Link)
            String[] line2 = line.split("\\s+");
            Integer stationValue = Integer.parseInt(line2[0]);
            String tunnelDirection = line2[1];
            Integer destionationStatioNValue = Integer.parseInt(line2[2]);

            Node station = stations.get(stationValue);
            if (station == null) {
                station = new Node(stationValue);
                stations.put(station.v, station);
            }
            Node destinationStation = stations.get(destionationStatioNValue);
            if (destinationStation == null) {
                destinationStation = new Node(destionationStatioNValue);
                stations.put(destinationStation.v, destinationStation);
            }
            station.addLink(destinationStation, tunnelDirection);
        }

        List<Trail> algotronTrails = new ArrayList<>();
        List<Trail> polynomialatortrails = new ArrayList<>();

        algotronTrails.add(new Trail(0, "", stationValueOfAlgotron, ""));
        polynomialatortrails.add(new Trail(0, "", stationValueOfPolynomialator, ""));

        TrailFactory factory = new TrailFactory(stations);

        printIfVerbose("Start of search...");
        long timeBefore = System.currentTimeMillis();

        TrailDrawer drawer = new TrailDrawer(numberOfStation);
        drawer.addTrailsInAlgo(algotronTrails);
        drawer.addTrailsInPoly(polynomialatortrails);
        while (true) {
            // First, check If we are stuck in failure, might be long...
            Trail aTrail = drawer.getAllAlgoTrails().get(0);
            long timeNow = System.currentTimeMillis();
            long diff = timeNow - timeBefore;
            timeBefore = timeNow;
            int numberOfTrails = drawer.getAllAlgoTrails().size() + drawer.getAllPolyTrails().size();
            int killedTrail = factory.killedTrail;
            printIfVerbose("Generation of trails n°" + aTrail.g + " (took " + diff + " ms) and there are "
                    + numberOfTrails + " trails alive for " + killedTrail + " killed.");
            if (aTrail.g > numberOfTunnel * 2) {
                System.out.println("fail");
                sc.close();
                return "fail";
            }

            algotronTrails = new ArrayList<>(drawer.getAllAlgoTrails());
            polynomialatortrails = new ArrayList<>(drawer.getAllPolyTrails());
            drawer.cleanTrails();
            drawer.addTrailsInAlgo(factory.generateChildrenTrails(algotronTrails));
            drawer.addTrailsInPoly(factory.generateChildrenTrails(polynomialatortrails));
            Trail solution = drawer.findIdenticalTrails();
            if (solution != null) {
                System.out.println(solution.p);
                sc.close();
                return solution.p;
            }
        }
        // Never go here
    }


    class TrailFactory {
        public final Map<Integer, Node> stations;

        public int killedTrail = 0;

        public TrailFactory(Map<Integer, Node> stations) {
            this.stations = stations;
        }

        public List<Trail> generateChildrenTrails(List<Trail> parentTrails) {
            printIfVerbose("TrailFactory#generateChildrenTrails");
            List<Trail> childrenTrails = new ArrayList<>();
            List<String> alreadyDoneParent = new ArrayList<>();
            for (Trail t : parentTrails) {
                if (alreadyDoneParent.contains(t.toString()))
                    continue;
                alreadyDoneParent.add(t.toString());

                // Need to remove trail that are stuck, to avoid having too much trails
                if (isStuckInLoop(t))
                    continue; // no children for t, bad t !

                // Kill parent that are stuck in a loop

                Node currentStation = stations.get(t.s);
                for (Link l : currentStation.links) {
                    Trail child = new Trail(t.g + 1, t.p + l.d, l.n.v, t.ps + t.s);
                    childrenTrails.add(child);
                }
            }
            return childrenTrails;
        }

        private boolean isStuckInLoop(Trail t) {
            String pastStations = t.ps;
            if (pastStations.length() < 6)
                return false;
            for (int i = 3; i <= pastStations.length() / 2; i++) {
                if (pastStations.substring(0, i).equals(pastStations.substring(i, i * 2))) {
                    killedTrail++;
                    return true;
                }
            }
            if (pastStations.length() < 7)
                return false;

            for (int i = 4; ((i * 2) + 1) <= pastStations.length(); i++) {
                if (pastStations.substring(1, (i - 1)).equals(pastStations.substring(i, ((i * 2) - 1)))) {
                    killedTrail++;
                    return true;
                }
            }
            return false;
        }

    }

    class TrailDrawer {
        private final int numberOfStation;

        /**
         * AlgotronTrails
         **/
        private final Map<Integer, List<Trail>> algoTrailsByStation;
        private final List<Trail> allAlgoTrails;

        /**
         * Polynomialatortrails
         **/
        private final Map<Integer, List<Trail>> polyTrailsByStation;
        private final List<Trail> allPolyTrails;

        public TrailDrawer(int numberOfStation) {
            this.numberOfStation = numberOfStation;
            algoTrailsByStation = new HashMap<>();
            allAlgoTrails = new ArrayList<>();
            polyTrailsByStation = new HashMap<>();
            allPolyTrails = new ArrayList<>();
            for (int i = 0; i < numberOfStation; i++) {
                algoTrailsByStation.put(i, new ArrayList<>());
                polyTrailsByStation.put(i, new ArrayList<>());
            }
        }

        public void addTrailsInAlgo(List<Trail> trails) {
            printIfVerbose("TrailDrawer#addTrailsInAlgo");
            for (Trail t : trails) {
                algoTrailsByStation.get(t.s).add(t);
                allAlgoTrails.add(t);
            }
        }

        public void addTrailsInPoly(List<Trail> trails) {
            printIfVerbose("TrailDrawer#addTrailsInPoly");
            for (Trail t : trails) {
                polyTrailsByStation.get(t.s).add(t);
                allPolyTrails.add(t);
            }
        }

        public void cleanTrails() {
            printIfVerbose("TrailDrawer#cleanTrails");
            for (int i = 0; i < numberOfStation; i++) {
                algoTrailsByStation.get(i).clear();
                polyTrailsByStation.get(i).clear();
            }
            allAlgoTrails.clear();
            allPolyTrails.clear();
        }

        public Trail findIdenticalTrails() {
            printIfVerbose("TrailDrawer#findIdenticalTrails");
            // If its exist differents trail for same path, we cannot uses them
            // (It will be more optimized to remove theses trails)
            List<String> forbiddenPath = new ArrayList<>();
            for (int i = 0; i < allAlgoTrails.size(); i++) {
                for (int j = i + 1; j < allAlgoTrails.size(); j++) {
                    if (i == j)
                        continue;
                    Trail t1 = allAlgoTrails.get(i);
                    Trail t2 = allAlgoTrails.get(j);
                    if (t1.p.equals(t2.p) && (t1.s != t2.s))
                        if (!forbiddenPath.contains(t1.p))
                            forbiddenPath.add(t1.p);
                }
            }
            for (int i = 0; i < allPolyTrails.size(); i++) {
                for (int j = i + 1; j < allPolyTrails.size(); j++) {
                    if (i == j)
                        continue;
                    Trail t1 = allPolyTrails.get(i);
                    Trail t2 = allPolyTrails.get(j);
                    if (t1.p.equals(t2.p) && (t1.s != t2.s))
                        if (!forbiddenPath.contains(t1.p))
                            forbiddenPath.add(t1.p);
                }
            }
            System.err.print("");
            for (int i = 0; i < numberOfStation; i++) {
                List<Trail> AlgotronTrails = algoTrailsByStation.get(i);
                List<Trail> Polynomialatortrails = polyTrailsByStation.get(i);
                for (Trail t1 : AlgotronTrails) {
                    for (Trail t2 : Polynomialatortrails) {
                        if (t1.isSame(t2) && !forbiddenPath.contains(t1.p)) {
                            return t1;
                        }
                    }
                }
            }

            return null;
        }

        public List<Trail> getAllAlgoTrails() {
            return allAlgoTrails;
        }

        public List<Trail> getAllPolyTrails() {
            return allPolyTrails;
        }
    }

    class Trail {
        /**
         * generation
         **/
        public final int g;
        /**
         * path
         **/
        public final String p;
        /**
         * current station
         **/
        public final int s;
        /**
         * previous station
         **/
        public final String ps;

        public Trail(int generation, String path, int station, String previousStation) {
            this.g = generation;
            this.p = path;
            this.s = station;
            this.ps = previousStation;
        }

        public boolean isSame(Trail o) {
            return (this.s == o.s) && (this.g == o.g) && (this.p.equals(o.p));
        }

        @Override
        public String toString() {
            return "n°" + g + " [" + p + "] (" + s + ") {" + ps + "}";
        }


    }


    /**
     * Station
     **/
    class Node {
        /**
         * value
         **/
        public final int v;
        public final List<Link> links;

        public Node(int value) {
            this.v = value;
            this.links = new ArrayList<>();
        }

        public void addLink(Node destination, String direction) {
            links.add(new Link(destination, direction));
        }

        @Override
        public String toString() {
            String linksStr = "";
            for (Link l : links)
                linksStr += l.toString() + " ";
            return "(" + v + ")" + linksStr;

        }
    }

    /**
     * Tunnel
     **/
    class Link {
        /**
         * destination
         **/
        public final Node n;
        /**
         * direction
         **/
        public final String d;

        public Link(Node destination, String direction) {
            this.n = destination;
            this.d = direction;
        }

        @Override
        public String toString() {
            return "[" + d + "]->" + n.v;

        }
    }

}

/*
Objectif

Vos 2 nouveaux robots, Algotron 2000 et Polynomialator-ZX se sont perdus dans le métro de New York. 
Pour ne rien arranger, le dispositif qui vous permet de les commander à distance depuis le haut de 
votre building secret vient de tomber en panne : chaque commande envoyée sera transmise sans discernement 
à chacun des deux robots.

Si seulement ils étaient au même endroit, vous pourriez les faire revenir sans problèmes... Si pouviez 
trouver une séquence de commande telle que les deux robots se rejoignent en recevant les mêmes ordres 
depuis leurs positions de départ respectives, tout serait arrangé !

Vous décidez d'écrire un programme : celui-ci analysera le plan des tunnels et trouvera une suite de 
commande adéquates, si elle existe !

Données

Entrée

Ligne 1 : deux entiers N et M séparés par des espaces respectivement compris entre 1 et 100 et entre 
1 et 500. N et M représentent respectivement l'identifiant maximal des stations (chaque station est 
identifiée par un nombre) et le nombre de tunnels sur le plan.
Ligne 2 : deux entiers compris entre 0 et N et indiquant le numéro de la station où se trouvent 
respectivement Algotron 2000 et Polynomiator-ZX.
Lignes 3 à M+2 : une ligne composée de 3 éléments, séparés par des espaces représentant un tunnel :
- Un nombre entier indiquant le numéro d'une station (entre 0 et N)
- Un caractère G, D ou T (pour « gauche », « droite » ou « tout droit »)
- Un nombre entier, indiquant le numéro d'une autre station.
Ainsi, une ligne de la forme « 4 G 3 » indique qu'en tournant à gauche depuis la station 4, 
on emprunte un tunnel menant à la station 3. Certains tunnels peuvent boucler (ex. « 4 T 4 »).


Attention : Certains tunnels se dédoublent, et il est ainsi possible d'avoir à la fois 
« 4 G 3 » et « 4 G 2 ». Dans un tel cas, si un robot est situé par exemple dans la station 
4 reçoit la commande « G », on considère qu'il peut ensuite se trouver aussi bien en 3 qu'en 2.
 Il n'est pas nécessaire que votre programme indique dans quelle station il se trouve : pour que les 
 robots se retrouvent, il suffit qu'à l'issue de toutes les commandes il existe une station en commun 
 parmi les stations possibles dans lesquels pourront se trouver les robots. Autrement dit, il suffit 
 qu'il existe un chemin correspondant aux directions indiquées par votre programme permettant aux robots 
 de se retrouver.

Note : Il est dangereux de circuler dans le métro, et chaque tunnel est à sens unique !

Sortie

Une suite de caractères G, D ou T qui, si elle est suivie depuis l'emplacement initial de chaque robot, 
devrait les mener au même endroit, si elle existe. Si vous ne trouvez pas de telle séquence, renvoyez la 
chaîne de caractère fail.

Exemple

Entrée
5 6
3 2
3 D 4
2 G 1
3 G 3
1 D 4
4 T 1
4 T 4

Sortie

GDTT


Entrée
5 12
3 2
1 T 2
1 G 4
2 D 1
2 D 3
3 D 2
3 G 4
4 T 1
4 D 4
4 G 5
5 T 1
5 T 3
5 D 4

Sortie
fail
*/
