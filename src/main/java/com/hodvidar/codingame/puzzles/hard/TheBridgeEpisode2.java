package com.hodvidar.codingame.puzzles.hard;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/the-bridge-episode-2 by Hodvidar
 * <p>
 * Genetic algorithm : the results are not always perfect.
 **/
class TheBridgeEpisode2 {

    private final static int MAX_NUMBER_TURN = 50;

    public static void main(final String[] args) {
        final TheBridgeEpisode2 t = new TheBridgeEpisode2();
        t.test();
    }

    private void test() {
        @SuppressWarnings("resource") final Scanner in = new Scanner(System.in);
        final int M = in.nextInt(); // the amount of motorbikes to control
        final int V = in.nextInt(); // the minimum amount of motorbikes that must survive
        final String L0 = in.next(); // L0 to L3 are lanes of the road. A dot character . represents a
        // safe space, a zero 0 represents a hole in the road.
        final String L1 = in.next();
        final String L2 = in.next();
        final String L3 = in.next();
        final IndividualBuilder individualBuilder;
        final FitnessCalculator fitnessCalculator = new FitnessCalculator();
        if (Objects.equals(L0, L1) && Objects.equals(L1, L2) && Objects.equals(L2, L3)) {
            // all lines are same, no nood for up or down
            individualBuilder = new IndividualBuilder(45, 15, 0, 40, 0, 0, fitnessCalculator);
        } else {
            individualBuilder = new IndividualBuilder(fitnessCalculator);
        }
        final String[] lines = new String[]{L0, L1, L2, L3};
        final byte[][] grid = new byte[4][L0.length()];
        for (int i = 0; i < 4; i++) {
            int j = 0;
            for (final char c : lines[i].toCharArray()) {
                if (c == '.')
                    grid[i][j] = 1;
                if (c == '0')
                    grid[i][j] = 0;
                j++;
            }
        }
        // ----- WRITE GRID
        System.err.println("M:" + M + " V:" + V + " LengthGrid:" + L0.length());
        System.err.println("Grid :");
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < L0.length(); x++) {
                System.err.print(grid[y][x]);
            }
            System.err.println();
        }
        // -----

        final Simulator simulator = new Simulator(M, V, grid, L0.length());

        int loop = 0;
        String[] orders = {"WAIT"};
        final Algorithm evolutionAlgorithm = new Algorithm(individualBuilder);
        // game loop
        while (true) {
            final int S = in.nextInt(); // the motorbikes' speed
            for (int i = 0; i < M; i++) {
                final int X = in.nextInt(); // x coordinate of the motorbike
                final int Y = in.nextInt(); // y coordinate of the motorbike
                final int A = in.nextInt(); // indicates whether the motorbike is activated "1" or
                // detroyed "0"
                if (loop == 0)
                    simulator.setMotorbike(i, X, Y, A, S);
                System.err.println("moto --> S:" + S + " X:" + X + " Y:" + Y + " A:" + A);
            }
            if (loop == 0) {
                fitnessCalculator.setMaxScore(simulator, L0.length(), V);
                // Create an initial population
                System.err.println("Create 1st Population (50 individuals)");
                Population myPop = new Population(50, true, MAX_NUMBER_TURN, individualBuilder);
                // Evolve our population until we reach an optimum solution
                int generationCount = 0;
                while (myPop.getFittest().getFitness() < fitnessCalculator.getMaxFitness()) {
                    generationCount++;
                    System.err.println("Generation: " + generationCount + " Fittest: "
                            + myPop.getFittest().getFitness());
                    myPop = evolutionAlgorithm.evolvePopulation(myPop);
                }
                generationCount++;

                System.err.println("Solution found!");
                System.err.println("Generation: " + generationCount + " Fittest: "
                        + myPop.getFittest().getFitness());
                orders = myPop.getFittest().getOrders();
                System.err.println("List of order to win :");
                for (final String s : orders) {
                    System.err.print(s + ", ");
                }
                System.err.println();
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // A single line containing one of 6 keywords: SPEED, SLOW, JUMP, WAIT, UP, DOWN.
            System.out.println(orders[loop]);
            loop++;
        }
    }

    // ------------------- INTERNAL CLASSES -----------------------------
    class Simulator {
        private final byte[][] grid;
        private final Motorbike[] motos;
        private final int minSurvive;
        private final int lengthGrid;

        public Simulator(final int numMoto, final int min, final byte[][] aGrid, final int maxLength) {
            this.grid = aGrid;
            this.motos = new Motorbike[numMoto];
            this.minSurvive = min;
            this.lengthGrid = maxLength;
        }

        public void setMotorbike(final int index, final int x, final int y, final int a, final int s) {
            final Motorbike m = new Motorbike();
            m.setY(y);
            m.setX(x);
            m.setState(a);
            m.setSpeed(s);
            this.motos[index] = m;
        }

        public int runSimulation(final String[] orders) {
            final Motorbike[] motosCopies = new Motorbike[this.motos.length];
            for (int i = 0; i < this.motos.length; i++)
                motosCopies[i] = new Motorbike(this.motos[i]);

            for (int i = 0; i < orders.length; i++) {
                final String order = orders[i];
                final boolean continueLoop = this.doOneTurn(motosCopies, order);
                // System.err.println("continueLoop " + continueLoop);
                if (!continueLoop)
                    break;
                if (this.isVictory(motosCopies))
                    break;
            }
            // TODO return score
            return this.getScore(motosCopies);
        }

        public boolean isVictory(final Motorbike[] motosCopies) {
            boolean isVictory = false;
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                if (moto.getX() >= this.lengthGrid) {
                    isVictory = true;
                    moto.setX(this.lengthGrid + 1);
                }
            }
            return isVictory;
        }

        // add the "minSurvive" best scores
        public int getScore(final Motorbike[] motosCopies) {
            int score = 0;
            final int numberToCount = this.minSurvive;
            final int l = motosCopies.length;
            final int[] xs = new int[l];
            for (int i = 0; i < l; i++) {
                xs[i] += motosCopies[i].getX();
            }
            Arrays.sort(xs);
            // only keep higher Xs of [numberToCount]
            for (int i = l - 1; i >= l - numberToCount; i--) {
                score += xs[i];
            }
            // System.err.println("Returning Score of " + score);
            return score;
        }

        // return false if fail or finish
        public boolean doOneTurn(final Motorbike[] motosCopies, final String order) {
            // System.err.println("doOneTurn: " + order);
            // do action then return boolean
            switch (order) {
                case "WAIT":
                    return this.doWait(motosCopies);
                case "SPEED":
                    return this.doSpeed(motosCopies);
                case "SLOW":
                    return this.doSlow(motosCopies);
                case "JUMP":
                    return this.doJump(motosCopies);
                case "UP":
                    return this.doUp(motosCopies);
                case "DOWN":
                    return this.doDown(motosCopies);
                default:
                    System.err.println("Order '" + order + "' is unknown !");
                    break;
            }
            // returning boolean :
            return false;
        }

        private boolean doWait(final Motorbike[] motosCopies) {
            int motoOK = 0;
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                this.moveMoto(moto);
                if (moto.getState() == 1)
                    motoOK += 1;
            }
            return motoOK >= this.minSurvive;
        }

        private boolean doSpeed(final Motorbike[] motosCopies) {
            int motoOK = 0;
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                moto.setSpeed(moto.getSpeed() + 1);
                this.moveMoto(moto);
                if (moto.getState() == 1)
                    motoOK += 1;
            }
            return motoOK >= this.minSurvive;
        }

        private boolean doSlow(final Motorbike[] motosCopies) {
            int motoOK = 0;
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                moto.setSpeed(moto.getSpeed() - 1);
                this.moveMoto(moto);
                if (moto.getState() == 1)
                    motoOK += 1;
            }
            return motoOK >= this.minSurvive;
        }

        private boolean doJump(final Motorbike[] motosCopies) {
            int motoOK = 0;
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                moto.jump();
                this.moveMoto(moto);
                if (moto.getState() == 1)
                    motoOK += 1;
            }
            return motoOK >= this.minSurvive;
        }

        // moto.y --> -1 if possible (from the upper (yMin) to the lower (yMax))
        private boolean doUp(final Motorbike[] motosCopies) {
            int motoOK = 0;
            final Motorbike[] motosByYorder = this.getMotosByYorder(motosCopies, true);
            Motorbike upperMoto = null;
            for (int i = 0; i < motosByYorder.length; i++) {
                final Motorbike moto = motosByYorder[i];
                final int y = moto.getY();
                if (i == 0) {
                    if (y > 0)
                        // move up with + speed (diagonal)
                        this.moveMoto(moto, y - 1);
                    else
                        this.moveMoto(moto);
                } else {
                    if (y > 0 && (upperMoto.getY() < y - 1))
                        // move up with + speed (diagonal)
                        this.moveMoto(moto, y - 1);
                    else
                        this.moveMoto(moto);
                }
                if (moto.getState() == 1)
                    motoOK += 1;
                upperMoto = moto;
            }
            return motoOK >= this.minSurvive;
        }

        private boolean doDown(final Motorbike[] motosCopies) {
            int motoOK = 0;
            final Motorbike[] motosByYorder = this.getMotosByYorder(motosCopies, true);
            Motorbike lowerMoto = null;
            for (int i = 0; i < motosByYorder.length; i++) {
                final Motorbike moto = motosByYorder[i];
                final int y = moto.getY();
                if (i == 0) {
                    if (y < 3)
                        // move down with + speed (diagonal)
                        this.moveMoto(moto, y + 1);
                    else
                        this.moveMoto(moto);
                } else {
                    if (y < 3 && (lowerMoto.getY() > y + 1))
                        // move down with + speed (diagonal)
                        this.moveMoto(moto, y + 1);
                    else
                        this.moveMoto(moto);
                }
                if (moto.getState() == 1)
                    motoOK += 1;
                lowerMoto = moto;
            }
            return motoOK >= this.minSurvive;
        }

        /**
         * return only moto with state == 1 (not dead) if up true: return array : array[0] with
         * minimum Y, array[length-1] maximum Y if up false: return array : array[0] with maximum Y,
         * array[length-1] minimum Y
         */
        private Motorbike[] getMotosByYorder(final Motorbike[] motosCopies, final boolean up) {
            final Motorbike[] motosOK = this.getMotosOKorKO(motosCopies, true);
            final int[] linePositions = new int[motosOK.length];
            for (int i = 0; i < motosOK.length; i++) {
                final Motorbike moto = motosOK[i];
                linePositions[i] = moto.getY();
            }
            Arrays.sort(linePositions); // 0, 1, 4 for example
            final Motorbike[] motosByYorder = new Motorbike[motosOK.length];
            for (int i = 0; i < motosOK.length; i++) {
                final Motorbike moto = motosOK[i];
                final int y = moto.getY();
                for (int i2 = 0; i2 < linePositions.length; i2++) {
                    if (y == linePositions[i2]) {
                        motosByYorder[i2] = moto;
                    }
                }
            }
            if (up)
                return motosByYorder; // moto[0].y == 0, moto[1].y == 1, moto[2].y ==4
            else
                return this.reverse(motosByYorder); // moto[0].y == 4, moto[1].y == 1, moto[2].y ==
            // 0
        }

        private Motorbike[] reverse(final Motorbike[] array) {
            for (int i = 0; i < array.length / 2; i++) {
                final Motorbike temp = array[i];
                array[i] = array[array.length - i - 1];
                array[array.length - i - 1] = temp;
            }
            return array;
        }

        private Motorbike[] getMotosOKorKO(final Motorbike[] motosCopies, final boolean isOK) {
            int motoOK = 0;
            final int state = ((isOK) ? 1 : 0);
            for (int i = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                if (moto.getState() == state)
                    motoOK += 1;
            }
            final Motorbike[] motosOK = new Motorbike[motoOK];
            for (int i = 0, j = 0; i < motosCopies.length; i++) {
                final Motorbike moto = motosCopies[i];
                if (moto.getState() == state) {
                    motosOK[j] = moto;
                    j++;
                }
            }
            return motosOK;
        }

        private void moveMoto(final Motorbike moto) {
            if (moto.getState() == 0)
                return;
            final int x = moto.getX();
            final int y = moto.getY();
            final int x2 = x + moto.getSpeed();
            final boolean j = moto.isJumping();
            final int hole = this.findFirstHole(x, x2, y);
            if (hole >= 0 && !j) {
                moto.setX(hole);
                moto.setState(0);
                return;
            }
            final boolean isArrivingOnaHole = this.isHole(x2, y);
            if (isArrivingOnaHole) {
                moto.setX(x2);
                moto.setState(0);
                return;
            }
            moto.setX(x2);
            moto.endJump();
        }

        private void moveMoto(final Motorbike moto, final int y2) {
            if (moto.getState() == 0)
                return;
            final int x = moto.getX();
            final int y = moto.getY();
            final int x2 = x + moto.getSpeed();
            final int hole = this.findFirstHole(x, x2, y);
            final int hole2 = this.findFirstHole(x, x2, y2);
            final boolean isHole = (hole != -1 || hole2 != -1); // at least one is not == -1
            if (isHole) {
                final int minHole;
                if (hole == -1 || hole2 == -1)
                    minHole = Math.max(hole, hole2);
                else
                    minHole = Math.min(hole, hole2);
                moto.setX(minHole);
                if (hole != hole2 && hole2 == minHole)
                    moto.setY(y2);
                moto.setState(0);
                return;
            }
            final boolean isArrivingOnaHole = this.isHole(x2, y2);
            if (isArrivingOnaHole) {
                moto.setX(x2);
                moto.setY(y2);
                moto.setState(0);
                return;
            }
            moto.setX(x2);
            moto.setY(y2);
            moto.endJump();
        }

        // -1 if no hole, either return x of first hole
        private int findFirstHole(final int x1, final int x2, final int y) {
            for (int i = x1 + 1; i < x2; i++) {
                if (i >= this.lengthGrid)
                    return -1;
                if (this.grid[y][i] == 0)
                    return i;
            }
            return -1;
        }

        private boolean isHole(final int x, final int y) {
            // System.err.println("x:" + x + " y:" + y);
            if (x >= this.lengthGrid)
                return false;
            return this.grid[y][x] == 0;
        }
    }

    class Motorbike {
        private int y; // line
        private int x; // where in line
        private int speed; // min is 0
        private int state; // 1 OK, 0 dead
        private boolean isJumping = false;

        public Motorbike() {
        }

        public Motorbike(final Motorbike aMotorbike) {
            this.setY(aMotorbike.getY());
            this.setX(aMotorbike.getX());
            this.setState(aMotorbike.getState());
            this.setSpeed(aMotorbike.getSpeed());
        }

        public int getY() {
            return this.y;
        }

        public void setY(final int j) {
            // System.err.println("Moto change y: " + this.y + " to " + j);
            this.y = j;
        }

        public int getX() {
            return this.x;
        }

        public void setX(final int i) {
            // System.err.println("Moto change x: " + this.x + " to " + i);
            this.x = i;
        }

        public int getSpeed() {
            return this.speed;
        }

        public void setSpeed(final int s) {
            if (s <= 0)
                this.speed = 0;
            else
                this.speed = s;
        }

        /**
         * 0 is dead
         */
        public int getState() {
            return this.state;
        }

        public void setState(final int a) {
            this.state = a;
            if (a == 0) {
                this.speed = 0;
                // System.err.println("Moto x:" + this.x + " y:" + this.y + " s:" + this.speed
                // + " just fall in a hole.");
            }
        }

        public boolean isJumping() {
            return this.isJumping;
        }

        public void jump() {
            this.isJumping = true;
        }

        public void endJump() {
            this.isJumping = false;
        }

    }

    class IndividualBuilder {
        private final FitnessCalculator fitnessCalculator;
        // % chance it is randomly choose (all must be ==100)
        private int SPEED = 30; // 30
        private int SLOW = 15; // 45
        private int WAIT = 0; // 45
        private int JUMP = 35; // 80
        private int UP = 10; // 90
        private int DOWN = 10; // 100

        public IndividualBuilder(final FitnessCalculator fitnessCalculator) {
            this.fitnessCalculator = fitnessCalculator;
        }

        /**
         * The sum of the params must be equals to 100.
         *
         * @param speed
         * @param slow
         * @param wait
         * @param jump
         * @param up
         * @param down
         */
        public IndividualBuilder(final int speed, final int slow, final int wait, final int jump, final int up, final int down,
                                 final FitnessCalculator fitnessCalculator) {
            final int sum = speed + slow + wait + jump + jump + up + down;
            if (sum != 100)
                throw new IllegalArgumentException("The sum of all parameters must be equals to 100%");

            SPEED = speed;
            SLOW = slow;
            WAIT = wait;
            JUMP = jump;
            UP = up;
            DOWN = down;

            this.fitnessCalculator = fitnessCalculator;
        }

        public Individual buildIndividual(final int lengthOfOrder) {
            return new Individual(SPEED, SLOW, WAIT, JUMP, UP, DOWN, lengthOfOrder, fitnessCalculator);
        }

    }

    class Individual {
        private final FitnessCalculator fitnessCalculator;
        // % chance it is randomly choose (all must be ==100)
        private int SPEED = 30; // 30
        private int SLOW = 15; // 45
        private int WAIT = 0; // 45
        private int JUMP = 35; // 80
        private int UP = 10; // 90
        private int DOWN = 10; // 100
        private int defaultOrderLength = 50;
        private String[] orders = new String[this.defaultOrderLength];

        // Cache
        private int fitness = -1;

        /**
         * Parameters sp, sl, wa, ju, up, down must be a total of 100.
         */
        public Individual(final int sp, final int sl, final int wa, final int ju, final int up, final int dn, final int lengthOfOrder,
                          final FitnessCalculator fitnessCalculator) {
            SPEED = sp;
            SLOW = sl;
            WAIT = wa;
            JUMP = ju;
            UP = up;
            DOWN = dn;

            this.defaultOrderLength = lengthOfOrder;
            this.orders = new String[this.defaultOrderLength];

            this.fitnessCalculator = fitnessCalculator;
        }

        // Create a random individual
        public void generateIndividual() {
            for (int i = 0; i < this.size(); i++) {
                this.orders[i] = generateOrder();
            }
        }

        public String generateOrder() {
            final int r = (int) Math.round(Math.random() * 100);
            int chance = SPEED;
            if (r <= chance)
                return "SPEED";
            chance += SLOW;
            if (r <= chance)
                return "SLOW";
            chance += WAIT;
            if (r <= chance)
                return "WAIT";
            chance += JUMP;
            if (r <= chance)
                return "JUMP";
            chance += UP;
            if (r <= chance)
                return "UP";
            chance += DOWN;
            if (r <= chance)
                return "DOWN";
            else
                return " ERROR WITH %";
        }

        /* Getters and setters */
        // Use this if you want to create individuals with different gene lengths
        public void setDefaultOrderLength(final int length) {
            this.defaultOrderLength = length;
            this.orders = new String[this.defaultOrderLength];
        }

        public String[] getOrders() {
            final String[] copy = new String[this.size()];
            for (int i = 0; i < this.size(); i++) {
                copy[i] = this.getOrder(i);
            }
            return this.orders;
        }

        public String getOrder(final int index) {
            return this.orders[index];
        }

        public void setOrder(final int index, final String value) {
            this.orders[index] = value;
            this.fitness = -1;
        }

        /* Public methods */
        public int size() {
            return this.orders.length;
        }

        public int getFitness() {
            if (this.fitness == -1) {
                // System.err.println("Calculing fitness of Individual n°" + this.number);
                this.fitness = fitnessCalculator.getFitness(this);
            }
            return this.fitness;
        }

        @Override
        public String toString() {
            String orderstring = "";
            for (int i = 0; i < this.size(); i++) {
                orderstring += this.getOrder(i) + ", ";
            }
            return orderstring;
        }
    }

    class Population {
        private final Individual[] individuals;
        private final int popOrderSize = 0;

        /*
         * Constructors
         */
        // Create a population
        public Population(final int populationSize, final boolean initialise, final int orderSize,
                          final IndividualBuilder individualBuilder) {
            this.individuals = new Individual[populationSize];
            // Initialise population
            if (initialise) {
                // Loop and create individuals
                for (int i = 0; i < this.size(); i++) {
                    final Individual newIndividual = individualBuilder.buildIndividual(orderSize);
                    newIndividual.generateIndividual();
                    this.saveIndividual(i, newIndividual);
                }
            }
        }

        public int getPopOrderSize() {
            return this.popOrderSize;
        }

        /* Getters */
        public Individual getIndividual(final int index) {
            return this.individuals[index];
        }

        public Individual getFittest() {
            Individual fittest = this.individuals[0];
            // Loop through individuals to find fittest
            for (int i = 1; i < this.size(); i++) {
                if (fittest.getFitness() <= this.getIndividual(i).getFitness()) {
                    fittest = this.getIndividual(i);
                }
            }
            return fittest;
        }

        /* Public methods */
        // Get population size
        public int size() {
            return this.individuals.length;
        }

        // Save individual
        public void saveIndividual(final int index, final Individual indiv) {
            this.individuals[index] = indiv;
        }
    }

    class Algorithm {
        /* GA parameters */
        private static final double uniformRate = 0.5;
        private static final double mutationRate = 0.015;
        private static final int tournamentSize = 5;
        private static final boolean elitism = true;
        private final IndividualBuilder individualBuilder;

        public Algorithm(final IndividualBuilder individualBuilder) {
            this.individualBuilder = individualBuilder;
        }
        /* Public methods */

        // Evolve a population
        public Population evolvePopulation(final Population pop) {
            final Population newPopulation = new Population(pop.size(), false, pop.getPopOrderSize(),
                    individualBuilder);

            // Keep our best individual
            if (elitism) {
                newPopulation.saveIndividual(0, pop.getFittest());
            }

            // Crossover population
            final int elitismOffset;
            if (elitism) {
                elitismOffset = 1;
            } else {
                elitismOffset = 0;
            }
            // Loop over the population size and create new individuals with
            // crossover
            for (int i = elitismOffset; i < pop.size(); i++) {
                final Individual indiv1 = tournamentSelection(pop);
                final Individual indiv2 = tournamentSelection(pop);
                final Individual newIndiv = crossover(indiv1, indiv2);
                newPopulation.saveIndividual(i, newIndiv);
            }

            // Mutate population
            for (int i = elitismOffset; i < newPopulation.size(); i++) {
                mutate(newPopulation.getIndividual(i));
            }

            return newPopulation;
        }

        // Crossover individuals
        private Individual crossover(final Individual indiv1, final Individual indiv2) {
            final Individual newSol = individualBuilder.buildIndividual(indiv1.size());
            // Loop through genes
            for (int i = 0; i < indiv1.size(); i++) {
                // Crossover
                if (Math.random() <= uniformRate) {
                    newSol.setOrder(i, indiv1.getOrder(i));
                } else {
                    newSol.setOrder(i, indiv2.getOrder(i));
                }
            }
            return newSol;
        }

        // Mutate an individual
        private void mutate(final Individual indiv) {
            // Loop through genes
            for (int i = 0; i < indiv.size(); i++) {
                if (Math.random() <= mutationRate) {
                    // Create random gene
                    final String order = indiv.generateOrder();
                    indiv.setOrder(i, order);
                }
            }
        }

        // Select individuals for crossover
        private Individual tournamentSelection(final Population pop) {
            // Create a tournament population
            final Population tournament = new Population(tournamentSize, false, pop.getPopOrderSize(), individualBuilder);
            // For each place in the tournament get a random individual
            for (int i = 0; i < tournamentSize; i++) {
                final int randomId = (int) (Math.random() * pop.size());
                tournament.saveIndividual(i, pop.getIndividual(randomId));
            }
            // Get the fittest
            return tournament.getFittest();
        }
    }

    /**
     * Will run a simulation here in order to give a fitness value
     */
    class FitnessCalculator {
        private int maxScore;
        private Simulator simulator;

        /* Public methods */
        // Set a candidate solution as a byte array
        public void setMaxScore(final Simulator aSimulator, final int lengthGrid, final int minSurvive) {
            simulator = aSimulator;
            maxScore = (lengthGrid + 1) * minSurvive;
            System.err.println("MAX SCORE TO WIN:" + maxScore);
        }

        // Calculate individual fitness by running its orders in simulator
        int getFitness(final Individual individual) {
            return simulator.runSimulation(individual.getOrders());
        }

        // Get optimum fitness
        int getMaxFitness() {
            return maxScore;
        }
    }

}
