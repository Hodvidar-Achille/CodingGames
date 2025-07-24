package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/bender-episode-1
 * by Hodvidar
 **/
class BenderEpisode1 {

    public static void main(final String[] args) {
        final BenderEpisode1 b = new BenderEpisode1();
        b.test();
    }

    private void test() {
        final Scanner in = new Scanner(System.in);
        final int L = in.nextInt();
        final int C = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final char[][] grid = new char[L][C];
        for (int i = 0; i < L; i++) {
            final String row = in.nextLine();
            System.err.println(row);
            final char[] rowC = row.toCharArray();
            System.arraycopy(rowC, 0, grid[i], 0, rowC.length);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        // GO !
        final Bender bender = new Bender(grid, L, C);
        final ArrayList<String> movements = bender.getMovements();
        for (final String s : movements) {
            switch (s) {
                case "S":
                    System.out.println("SOUTH");
                    break;
                case "E":
                    System.out.println("EAST");
                    break;
                case "N":
                    System.out.println("NORTH");
                    break;
                case "W":
                    System.out.println("WEST");
                    break;
                case "LOOP":
                    System.out.println(s);
                default:
                    System.err.println("ERROR : direction has no match: " + s);
                    break;
            }
        }

        // System.out.println("answer");
        in.close();
    }

    // -------------------------------- INTERNAL CLASSES -----------------------------------

    class Bender {
        private final char[][] theGrid;
        private final int lines;
        private final int colums;
        private final ArrayList<Movement> pastActions;
        private final ArrayList<String> movements;
        private final char SUD = 'S';
        private final char EST = 'E';
        private final char NORD = 'N';
        private final char OUEST = 'W';
        private final char DEPART = '@';
        private final char WALL1 = '#';
        private final char WALL2 = 'X';
        private final char BEER = 'B';
        private final char INV = 'I'; // inverse
        private final char TP = 'T';
        private final char VIDE = ' ';
        private final char STOP = '$';
        private boolean bersekerMode;
        private boolean isInversed;

        public Bender(final char[][] aGrid, final int l, final int c) {
            this.theGrid = aGrid;
            this.lines = l;
            this.colums = c;
            this.bersekerMode = false;
            this.isInversed = false;
            this.pastActions = new ArrayList<>();
            this.movements = new ArrayList<>();
        }

        /**
         * Do the work here
         */
        public ArrayList<String> getMovements() {
            final char[][] grid = this.theGrid;
            final int l = this.lines; // l = lines = y
            final int c = this.colums; // c = colums = x
            int x0 = -1, y0 = -1;
            // find first point
            outer_loop0:
            for (int i = 0; i < l; i++) {
                for (int j = 0; j < c; j++) {
                    if (grid[i][j] == this.DEPART) {
                        y0 = i;
                        x0 = j;
                        break outer_loop0;
                    }
                }
            }
            grid[y0][x0] = this.VIDE;
            this.doOneMovement(grid, y0, x0, ' ');
            return this.movements;
        }

        private void doOneMovement(final char[][] grid, final int y, final int x, char d) {
            this.pastActions.add(this.getNewMovement(y, x, d));
            // try to detect a LOOP

            if (this.isLooping()) {
                this.movements.clear();
                this.movements.add("LOOP");
                return;
            }

            if (d == ' ')
                d = this.SUD;
            final char here = grid[y][x];
            if (here == this.STOP)
                return;
            d = this.adaptStateAndDirection(here, d);
            System.err.println("Try to move from [" + y + "][" + x + "] in d: " + d);
            this.doOneMovement2(grid, y, x, d);
        }

        private void doOneMovement2(final char[][] grid, final int y, final int x, char d) {
            d = this.findNext(grid, y, x, d);
            final int[] yx = this.directionToYX(d, y, x);
            final int y2 = yx[0];
            final int x2 = yx[1];
            final char next = grid[y2][x2];
            System.err.println("Go to [" + y2 + "][" + x2 + "] in d: " + d);
            this.movements.add("" + d);
            if (next == this.WALL2 && this.bersekerMode) {
                grid[y2][x2] = this.VIDE;
            }
            if (next == this.TP) {
                this.teleporte(grid, y2, x2, d);
                return;
            }
            this.doOneMovement(grid, y2, x2, d);
        }

        private char findNext(final char[][] grid, final int y, final int x, final char d) {
            final boolean s = this.okToGo(grid[y + 1][x]);
            final boolean e = this.okToGo(grid[y][x + 1]);
            final boolean n = this.okToGo(grid[y - 1][x]);
            final boolean w = this.okToGo(grid[y][x - 1]);
            final boolean i = this.isInversed;
            if (d == this.SUD && s)
                return d;
            if (d == this.EST && e)
                return d;
            if (d == this.NORD && n)
                return d;
            if (d == this.OUEST && w)
                return d;

            if (i) {
                if (w)
                    return this.OUEST;
                if (n)
                    return this.NORD;
                if (e)
                    return this.EST;
                if (s)
                    return this.SUD;
            }
            if (s)
                return this.SUD;
            if (e)
                return this.EST;
            if (n)
                return this.NORD;
            if (w)
                return this.OUEST;
            return ' ';
        }

        private int[] directionToYX(final char d, final int y, final int x) {
            if (d == this.SUD)
                return new int[]{y + 1, x};
            if (d == this.EST)
                return new int[]{y, x + 1};
            if (d == this.NORD)
                return new int[]{y - 1, x};
            if (d == this.OUEST)
                return new int[]{y, x - 1};
            return new int[]{-1, -1};
        }

        private boolean okToGo(final char c) {
            return !(c == this.WALL1 || (c == this.WALL2 && !this.bersekerMode));
        }

        private void teleporte(final char[][] grid, final int y, final int x, final char d) {
            // find other Teleporter
            int y2 = -1, x2 = -1;
            outer_loop1:
            for (int i = 0; i < this.lines; i++) {
                for (int j = 0; j < this.colums; j++) {
                    if (grid[i][j] == this.TP && (i != y || j != x)) {
                        y2 = i;
                        x2 = j;
                        break outer_loop1;
                    }
                }
            }
            System.err.println("Teleportation from [" + y + "][" + x + "] to [" + y2 + "][" + x2 + "] in d: " + d);
            this.doOneMovement(grid, y2, x2, d);
        }

        private char adaptStateAndDirection(final char c, final char d) {
            if (c == this.INV) {
                this.inverse();
                return d;
            }
            if (c == this.BEER) {
                this.takeBeer();
                return d;
            }
            if (c == this.SUD || c == this.EST || c == this.NORD || c == this.OUEST)
                return c;
            // not handling TP here
            return d;
        }

        private Movement getNewMovement(final int y, final int x, final char d) {
            return new Movement(y, x, this.isInversed, this.bersekerMode, d);
        }

        private void takeBeer() {
            this.bersekerMode = !this.bersekerMode;
        }

        private void inverse() {
            this.isInversed = !this.isInversed;
        }

        private boolean isLooping() {
            final ArrayList<Movement> list = this.pastActions;
            // not using a counter
            int c = 0;
            for (int i = 0; i < list.size(); i++) {
                c = 0;
                final Movement left = list.get(i);
                for (int j = i + 1; j < list.size(); j++) {
                    final Movement right = list.get(j);
                    if (left.isSame(right))
                        c++;
                    if (c > 10)
                        return true;
                }
            }
            return false;
        }
    }

    class Movement {
        private final int x;
        private final int y;
        private final boolean stateInverse;
        private final boolean stateBersek;
        private final char direction;

        public Movement(final int y, final int x, final boolean inverse, final boolean bersek, final char direction) {
            this.x = x;
            this.y = y;
            this.stateInverse = inverse;
            this.stateBersek = bersek;
            this.direction = direction;
        }

        public boolean isSame(final Movement other) {
            final boolean sameX = this.x == other.getX();
            final boolean sameY = this.y == other.getY();
            final boolean sameInv = this.stateInverse == other.isStateInverse();
            final boolean sameBer = this.stateBersek == other.isStateBersek();
            final boolean sameD = this.direction == other.getDirection();
            return sameX && sameY && sameInv && sameBer && sameD;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public boolean isStateInverse() {
            return this.stateInverse;
        }

        public boolean isStateBersek() {
            return this.stateBersek;
        }

        public char getDirection() {
            return this.direction;
        }

    }

}
