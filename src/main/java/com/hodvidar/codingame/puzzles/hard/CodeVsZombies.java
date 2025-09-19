package com.hodvidar.codingame.puzzles.hard;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/code-vs-zombies
 */
public class CodeVsZombies {
    // empty
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        Player.gameLoop(in);
    }
}

class Player {
    public static final int MAP_W = 16000;
    public static final int MAP_H = 9000;

    public static void gameLoop(final Scanner in) {
        final int x = in.nextInt();
        final int y = in.nextInt();
        final int humanCount = in.nextInt();

        final Point ashPos = new Point(x, y);
        final List<Human> humans = new ArrayList<>();
        for (int i = 0; i < humanCount; i++) {
            final int id = in.nextInt();
            final int hx = in.nextInt();
            final int hy = in.nextInt();
            humans.add(new Human(id, hx, hy));
        }

        final int zombieCount = in.nextInt();
        final List<Zombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombieCount; i++) {
            final int zid = in.nextInt();
            final int zx = in.nextInt();
            final int zy = in.nextInt();
            final int zxNext = in.nextInt();
            final int zyNext = in.nextInt();
            zombies.add(new Zombie(zid, zx, zy, zxNext, zyNext));
        }

        final List<Point> candidates = DecisionHelper.generateCandidates(ashPos, zombies);

        long bestScore = Long.MIN_VALUE;
        Point bestDest = ashPos;

        for (Point cand : candidates) {
            GameState state = new GameState(ashPos, humans, zombies);
            long totalScore = 0;
            final int turns = 3; // profondeur de simulation

            for (int t = 0; t < turns; t++) {
                state = GameSimulator.applyMove(state, cand);
                final int zombiesKilled = zombies.size() - state.zombies().size();
                final int humansAlive = state.getAliveHumans();
                totalScore += ScoreHelper.multiKillScore(zombiesKilled, humansAlive);

                // Préparer le prochain coup : recalculer les candidats
                if (!state.zombies().isEmpty()) {
                    final List<Point> nextCandidates = DecisionHelper.generateCandidates(state.ash(), state.zombies());
                    cand = nextCandidates.get(0); // stratégie simple : prendre le premier candidat
                }
            }

            if (totalScore > bestScore) {
                bestScore = totalScore;
                bestDest = candidates.get(candidates.indexOf(cand));
            }
        }

        System.out.println(bestDest.x + " " + bestDest.y);
    }

    /**
     * @param ash     Ash’s current position.
     * @param humans  List of living non‑Ash humans.
     * @param zombies List of living zombies (their current coordinates).
     */
    record GameState(Point ash, List<Human> humans, List<Zombie> zombies) {

            /**
             * Creates a new immutable game state.
             *
             * @param ash     Ash’s position
             * @param humans  alive humans (non‑Ash)
             * @param zombies alive zombies
             */
            GameState(final Point ash, final List<Human> humans, final List<Zombie> zombies) {
                this.ash = ash;
                this.humans = List.copyOf(humans);   // defensive copy – immutable view
                this.zombies = List.copyOf(zombies);
            }

            /**
             * @return Ash’s current location
             */
            @Override
            public Point ash() {
                return ash;
            }

            /**
             * @return an immutable list of the living humans
             */
            @Override
            public List<Human> humans() {
                return humans;
            }

            /**
             * @return an immutable list of the living zombies
             */
            @Override
            public List<Zombie> zombies() {
                return zombies;
            }

            /**
             * Convenience: number of humans still alive (does NOT include Ash).
             */
            public int getAliveHumans() {
                return humans.size();
            }
        }

    static class GameSimulator {
        /**
         * Apply Ash's chosen destination and return the resulting state after ONE turn.
         */
        public static GameState applyMove(final GameState state, final Point ashTarget) {

            // ---------- 1️⃣ Zombies move ----------
            final List<Zombie> movedZ = new ArrayList<>();
            for (final Zombie z : state.zombies()) {
                // Find nearest living human (including Ash at his *current* spot)
                Point nearest = state.ash();
                double bestDist = state.ash().dist(z.curPos());

                for (final Human h : state.humans()) {
                    final double d = h.pos().dist(z.curPos());
                    if (d < bestDist) {
                        bestDist = d;
                        nearest = h.pos();
                    }
                }
                // Move up to 400 toward that point
                final Point newPos = z.curPos().stepTowards(nearest, 400);
                movedZ.add(new Zombie(z.id, newPos.x, newPos.y, 0, 0));
            }

            // ---------- 2️⃣ Ash moves ----------
            final Point ashNext = state.ash().stepTowards(ashTarget, 1000);

            // ---------- 3️⃣ Shooting ----------
            final List<Zombie> survivors = new ArrayList<>();
            int zombiesKilled = 0;
            for (final Zombie mz : movedZ) {
                if (ashNext.dist(mz.curPos()) <= 2000) {
                    zombiesKilled++;                // killed instantly
                } else {
                    survivors.add(mz);
                }
            }

            // ---------- 4️⃣ Humans eaten ----------
            final List<Human> remainingHumans = new ArrayList<>(state.humans());
            for (final Iterator<Human> it = remainingHumans.iterator(); it.hasNext(); ) {
                final Human h = it.next();
                for (final Zombie mz : survivors) {
                    if (mz.curPos().x == h.x && mz.curPos().y == h.y) {
                        // This human is eaten – remove him
                        it.remove();
                        break;
                    }
                }
            }

            // Return the new immutable snapshot
            return new GameState(ashNext, remainingHumans, survivors);
        }
    }

    /**
     * @param humansLost number of non‑Ash humans dead after this turn
     */
    record TurnResult(int zombiesKilled, int humansLost, Point ashNextPos) {
    }

    static class TurnSimulator {
        /**
         * Simulate a single turn given a candidate destination for Ash.
         */
        public static TurnResult simulateTurn(final Point ashPos,
                                              final Point ashTarget,
                                              final List<Human> humans,
                                              final List<Zombie> zombies) {

            // ----- 1️⃣ Zombies move -----
            final List<Zombie> movedZombies = new ArrayList<>();
            for (final Zombie z : zombies) {
                // Find closest living human (including Ash at his *current* location)
                Point best = ashPos;      // start with Ash
                double bestDist = ashPos.dist(z.curPos());

                for (final Human h : humans) {
                    final Point hp = h.pos();
                    final double d = hp.dist(z.curPos());
                    if (d < bestDist) {
                        bestDist = d;
                        best = hp;
                    }
                }

                // Move up to 400 towards `best`
                final Point newPos = z.curPos().stepTowards(best, 400);
                movedZombies.add(new Zombie(z.id, newPos.x, newPos.y,
                        0, 0)); // nextPos not needed here
            }

            // ----- 2️⃣ Ash moves -----
            final Point ashNext = ashPos.stepTowards(ashTarget, 1000);

            // ----- 3️⃣ Kill zombies within 2000 -----
            int killed = 0;
            final List<Zombie> survivors = new ArrayList<>();
            for (final Zombie mz : movedZombies) {
                if (ashNext.dist(mz.curPos()) <= 2000) {
                    killed++;
                } else {
                    survivors.add(mz);
                }
            }

            // ----- 4️⃣ Humans eaten -----
            int humansLost = 0;
            for (final Zombie mz : survivors) {
                for (final Human h : humans) {
                    if (mz.curPos().x == h.x && mz.curPos().y == h.y) {
                        humansLost++;                 // this human dies now
                        // remove him from future considerations
                        // (we’ll filter the list after the loop)
                    }
                }
            }

            // Build the filtered human list for the caller (if needed later)
            // (not strictly required for a single‑turn decision)
            return new TurnResult(killed, humansLost, ashNext);
        }
    }

    static class DecisionHelper {
        /**
         * Build a small set of plausible destinations for Ash.
         * - staying where he is,
         * - moving straight toward each zombie (one step of 1000 units),
         * - moving toward the centroid of all zombies (helps herd them together).
         */
        static List<Point> generateCandidates(final Point ashPos, final List<Zombie> zombies) {
            final List<Point> cand = new ArrayList<>();

            // 0. stay put
            cand.add(new Point(ashPos.x, ashPos.y));

            // 1. one‑step toward each zombie
            for (final Zombie z : zombies) {
                final Point target = z.curPos();
                cand.add(ashPos.stepTowards(target, 1000));
            }

            // 2. toward the centroid of all zombies (optional)
            if (!zombies.isEmpty()) {
                long sumX = 0, sumY = 0;
                for (final Zombie z : zombies) {
                    sumX += z.x;
                    sumY += z.y;
                }
                final Point centroid = new Point((int) (sumX / zombies.size()),
                        (int) (sumY / zombies.size()));
                cand.add(ashPos.stepTowards(centroid, 1000));
            }

            // Remove duplicates (same coordinates) – helps later loops.
            final Set<String> seen = new HashSet<>();
            final List<Point> uniq = new ArrayList<>();
            for (final Point p : cand) {
                final String key = p.x + "," + p.y;
                if (!seen.contains(key)) {
                    uniq.add(p);
                    seen.add(key);
                }
            }
            return uniq;
        }
    }

    static class ScoreHelper {
        public static int killValue(final int humansAlive) {
            return humansAlive * humansAlive * 10;
        }

        /**
         * Fibonacci numbers (1‑based) – we only need the first few.
         */
        public static long fib(final int n) {
            long a = 1, b = 2;               // F1=1, F2=2
            if (n == 1) return a;
            if (n == 2) return b;
            for (int i = 3; i <= n; i++) {
                final long c = a + b;
                a = b;
                b = c;
            }
            return b;
        }

        /**
         * Compute the total score for killing `k` zombies in the same turn.
         */
        public static long multiKillScore(final int k, final int humansAlive) {
            final long base = killValue(humansAlive);
            long total = 0;
            for (int i = 1; i <= k; i++) {
                // multiplier = Fib(i+2)   because the spec says (n+2)th term
                final long mult = fib(i + 2);
                total += base * mult;
            }
            return total;
        }
    }

    static class Point {
        int x, y;

        Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Euclidean distance (rounded down to int)
         */
        int dist(final Point o) {
            final long dx = (long) x - o.x;
            final long dy = (long) y - o.y;
            return (int) Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * Return a new point moved at most `step` towards `target`.
         */
        Point stepTowards(final Point target, final int step) {
            if (dist(target) <= step) return new Point(target.x, target.y);
            final double ratio = step / (double) dist(target);
            final int nx = x + (int) Math.round((target.x - x) * ratio);
            final int ny = y + (int) Math.round((target.y - y) * ratio);
            return new Point(nx, ny);
        }
    }

    static class Human {
        int id, x, y;

        Human(final int id, final int x, final int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        Point pos() {
            return new Point(x, y);
        }
    }

    static class Zombie {
        int id, x, y, nx, ny;          // current and next‑turn positions supplied by input

        Zombie(final int id, final int x, final int y, final int nx, final int ny) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.nx = nx;
            this.ny = ny;
        }

        Point curPos() {
            return new Point(x, y);
        }

        Point nextPos() {
            return new Point(nx, ny);
        }
    }
}