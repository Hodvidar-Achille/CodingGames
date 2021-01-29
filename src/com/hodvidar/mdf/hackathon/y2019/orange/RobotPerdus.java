package com.hodvidar.mdf.hackathon.y2019.orange;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * https://www.isograd.com/FR/solutionconcours.php?contest_id=50
 * <p>
 * NOT FINISHED
 * <p>
 * by Hodvidar
 */
public final class RobotPerdus {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final boolean ONE_TEST = false;
    private static final int ONE_TEST_NUMBER = 3;

    private static final int NUMBER_OF_TESTS = 10;
    private static final String INPUT_DIRECTORY = "robots_perdus_input";

    private static final int MAX_RECURSION_LEVEL = 68;
    private static final String F = "fail";
    private static final String G = "G";
    private static final String T = "T";
    private static final String D = "D";

    private static final String[] directions = new String[]{G, T, D};

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        RobotPerdus r = new RobotPerdus();
        int i;
        int max;
        if (ONE_TEST) {
            i = ONE_TEST_NUMBER;
            max = ONE_TEST_NUMBER;
        } else {
            i = 1;
            max = NUMBER_OF_TESTS;
        }
        for (; i <= max; i++) {
            System.err.println("\n--- TEST n°" + i + " --");
            String result = r.test("resources" + File.separator + INPUT_DIRECTORY +  File.separator + "input" + i + ".txt");
            // --- CHECKING ---
            File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
            // Scanner sc = new Scanner(System.in);
            Scanner sc2 = new Scanner(file2);
            String line2 = sc2.nextLine();
            System.err.println("Solution is: \n" + line2);
            if (result.equals(line2))
                System.err.println("SUCCESS!");
            else
                System.err.println("FAILURE! found: " + result);
            sc2.close();
        }
    }

    @SuppressWarnings("unused")
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
        Network network = new Network(stations);
        sc.close();
        printIfVerbose("Start of search...");

        String r = lookForPath(network, "", new Integer[]{stationValueOfAlgotron}, new Integer[]{stationValueOfPolynomialator}, 0);
        return r;
    }

    /**
     * Recursive with testPath
     **/
    private String lookForPath(Network network, String path, Integer[] algoStations, Integer[] polystation, int deepLevel) {
        if (deepLevel >= MAX_RECURSION_LEVEL)
            return F;

        for (String d : directions) {
            String r = testPath(network, path, d, algoStations, polystation, deepLevel + 1);
            if (F.equals(r))
                continue;
            // Only if correct Path has been found.
            return r;
        }

        // If none of the directions were correct.
        return F;
    }

    /**
     * Recursive with lookForPath
     **/
    private String testPath(Network network, String path, String newDirection, Integer[] algoStations, Integer[] polystation, int deepLevel) {
        if (algoStations.length == 0 || polystation.length == 0)
            throw new IllegalStateException("List should not be empty");

        String newPath = path + newDirection;
        printIfVerbose("testPath: " + newPath + " (deep=" + deepLevel + ")\n" + Arrays.toString(algoStations) + "\n" + Arrays.toString(polystation));

        LinkedHashSet<Integer> newAlgoStations = new LinkedHashSet<>();
        LinkedHashSet<Integer> newPolystations = new LinkedHashSet<>();

        for (Integer i : algoStations) {
            Node s = network.getStation(i);
            List<Integer> newS = s.getDestinationValue(newDirection);
            if (newS.size() == 0)
                return F;
            newAlgoStations.addAll(newS);
        }
        for (Integer i : polystation) {
            Node s = network.getStation(i);
            List<Integer> newS = s.getDestinationValue(newDirection);
            if (newS.size() == 0)
                return F;
            newPolystations.addAll(newS);
        }

        algoStations = newAlgoStations.toArray(new Integer[newAlgoStations.size()]);
        polystation = newPolystations.toArray(new Integer[newPolystations.size()]);

        if (areSharingOneStation(algoStations, polystation))
            return newPath;

        return lookForPath(network, newPath, algoStations, polystation, deepLevel);
    }

    /**
     * Returns true if one integer from first array exist in the second array
     **/
    private boolean areSharingOneStation(Integer[] algoStations, Integer[] polystation) {
        for (int i : algoStations) {
            for (int j : polystation) {
                if (i == j)
                    return true;
            }
        }
        return false;
    }


    class Path {
        private final String path;

        private List<Trail> algoTrails;
        private List<Trail> polyTrails;

        public Path(String directions) {
            this.path = directions;
        }

        /**
         * Must only be called once
         **/
        public Path(Trail algoDeparture, Trail polyDeparture) {
            this.path = "";
            this.algoTrails = new ArrayList<>();
            this.polyTrails = new ArrayList<>();
            this.algoTrails.add(algoDeparture);
            this.polyTrails.add(polyDeparture);
        }

        public void addTrailForAlgo(Trail t) {
            this.algoTrails.add(t);
        }

        public void addTrailForPoly(Trail t) {
            this.polyTrails.add(t);
        }

        /**
         * Returns a new Path build from this one, using the new direction.
         *
         * @param direction: added to previous directions.
         * @param network:   help to build the children trails.
         * @return <code>null</code> if the direction is not possible for one of the robot.
         */
        public Path goTo(String direction, Network network) {
            Path child = new Path(this.path + direction);
            // TODO : finish this.
            boolean algoCanMove = false;
            for (Trail t : this.algoTrails) {
                Node station = network.getStation(t.station);
                for (Link l : station.links) {
                    if (!(l.d.equals(direction)))
                        continue;
                    algoCanMove = true;
                    Trail childTrail = t.moveTo(l.n.v);
                    child.addTrailForAlgo(childTrail);
                }
            }
            if (!algoCanMove)
                return null;

            boolean polyCanMove = false;
            for (Trail t : this.polyTrails) {
                Node station = network.getStation(t.station);
                for (Link l : station.links) {
                    if (!(l.d.equals(direction)))
                        continue;
                    polyCanMove = true;
                    Trail childTrail = t.moveTo(l.n.v);
                    child.addTrailForPoly(childTrail);
                }
            }
            if (!polyCanMove)
                return null;

            // TODO : check if the Path is looping here ?
            // 1) For this unique Path

            // TODO : check if split Trails can be fused.

            return child;
        }

        public boolean checkPath() {
            if (this.algoTrails.size() > 1)
                return false;
            if (this.polyTrails.size() > 1)
                return false;

            Trail algoT = this.algoTrails.get(0);
            Trail polyT = this.polyTrails.get(0);
            return algoT.station == polyT.station;
        }

        public boolean isLooping() {
            // --- Might not always be true
            if (this.algoTrails.size() > 1)
                return false;
            if (this.polyTrails.size() > 1)
                return false;
            // ---

            // TODO : check if both trail are looping
            return false;
        }
    }


    class Trail {
        public final ArrayList<Integer> previousStations;
        public int station;

        public Trail(int station) {
            this.station = station;
            this.previousStations = new ArrayList<>();
        }

        public Trail(int station, ArrayList<Integer> previousStations) {
            this.station = station;
            this.previousStations = previousStations;
        }

        @Override
        public String toString() {
            return "(" + station + ") {" + previousStations + "}";
        }

        /**
         * Return a new Trail, from this one.
         *
         * @param newStation
         * @return
         */
        public Trail moveTo(int newStation) {
            ArrayList<Integer> newPreviousStations = new ArrayList<>(this.previousStations);
            newPreviousStations.add(this.station);
            return new Trail(newStation, newPreviousStations);
        }


    }

    /**
     * Holds all the Node and Link
     **/
    class Network {
        private final Map<Integer, Node> nodes;

        public Network(Map<Integer, Node> stations) {
            this.nodes = stations;
        }

        public Node getStation(int stationValue) {
            return nodes.get(stationValue);
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

        /**
         * Returns all stations for this direction or an empty list if none.
         **/
        public List<Integer> getDestinationValue(String direction) {
            List<Integer> destionationStatons = new ArrayList<>();
            for (Link l : links) {
                if (l.d.equals(direction))
                    destionationStatons.add(l.n.v);
            }
            return destionationStatons;
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
