package com.hodvidar.codingame.puzzles.hard;

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * https://www.codingame.com/ide/puzzle/code-vs-zombies
 */
public class CodeVsZombies {
    // empty
}

class Player {
    public static final int MAP_W = 16000;
    public static final int MAP_H = 9000;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            gameLoop(in);
        }
    }

    private static void gameLoop(Scanner in) {
        int x = in.nextInt();
        int y = in.nextInt();
        int humanCount = in.nextInt();

        // Inside your main loop, after reading all inputs:
        Point ashPos = new Point(x, y);               // Ash’s current coordinates

        // Build collections
        List<Human> humans = new ArrayList<>();
        for (int i = 0; i < humanCount; i++) {
            int id = in.nextInt();
            int hx = in.nextInt();
            int hy = in.nextInt();
            humans.add(new Human(id, hx, hy));
        }

        int zombieCount = in.nextInt();
        List<Zombie> zombies = new ArrayList<>();
        for (int i = 0; i < zombieCount; i++) {
            int zid = in.nextInt();
            int zx = in.nextInt();
            int zy = in.nextInt();
            int zxNext = in.nextInt();   // supplied by the problem input
            int zyNext = in.nextInt();
            zombies.add(new Zombie(zid, zx, zy, zxNext, zyNext));
        }

        // ------------------------------------------------------------------
        // 1️⃣ Generate candidates
        List<Point> candidates = DecisionHelper.generateCandidates(ashPos, zombies);

        // 2️⃣ Evaluate each candidate
        long bestScore = Long.MIN_VALUE;
        Point bestDest = ashPos;   // fallback: stay where you are

        for (Point cand : candidates) {
            TurnResult r = TurnSimulator.simulateTurn(ashPos, cand, humans, zombies);

            // Humans still alive after this turn (excluding Ash)
            int humansAlive = humans.size() - r.humansLost;

            // Score = points for zombies killed now + a crude look‑ahead
            long turnScore = ScoreHelper.multiKillScore(r.zombiesKilled, humansAlive);

            // OPTIONAL: add a tiny heuristic for proximity to remaining zombies
            // (encourage moving toward the biggest cluster)
            if (!zombies.isEmpty()) {
                int minDist = Integer.MAX_VALUE;
                for (Zombie z : zombies) {
                    minDist = Math.min(minDist, cand.dist(z.curPos()));
                }
                // closer = slightly higher score
                turnScore -= minDist;   // subtract distance (so smaller distance → larger score)
            }

            if (turnScore > bestScore) {
                bestScore = turnScore;
                bestDest = cand;
            }
        }

        // ------------------------------------------------------------------
        // 3️⃣ Output the chosen destination
        System.out.println(bestDest.x + " " + bestDest.y);
    }
}

class GameState {

    /** Ash’s current position. */
    private final Point ash;

    /** List of living non‑Ash humans. */
    private final List<Human> humans;

    /** List of living zombies (their current coordinates). */
    private final List<Zombie> zombies;

    /**
     * Creates a new immutable game state.
     *
     * @param ash     Ash’s position
     * @param humans  alive humans (non‑Ash)
     * @param zombies alive zombies
     */
    public GameState(Point ash, List<Human> humans, List<Zombie> zombies) {
        this.ash = ash;
        this.humans = List.copyOf(humans);   // defensive copy – immutable view
        this.zombies = List.copyOf(zombies);
    }

    /** @return Ash’s current location */
    public Point getAsh() {
        return ash;
    }

    /** @return an immutable list of the living humans */
    public List<Human> getHumans() {
        return humans;
    }

    /** @return an immutable list of the living zombies */
    public List<Zombie> getZombies() {
        return zombies;
    }

    /** Convenience: number of humans still alive (does NOT include Ash). */
    public int getAliveHumans() {
        return humans.size();
    }
}

class GameSimulator {
    /** Apply Ash's chosen destination and return the resulting state after ONE turn. */
    public static GameState applyMove(GameState state, Point ashTarget) {

        // ---------- 1️⃣ Zombies move ----------
        List<Zombie> movedZ = new ArrayList<>();
        for (Zombie z : state.getZombies()) {
            // Find nearest living human (including Ash at his *current* spot)
            Point nearest = state.getAsh();
            double bestDist = state.getAsh().dist(z.curPos());

            for (Human h : state.getHumans()) {
                double d = h.pos().dist(z.curPos());
                if (d < bestDist) {
                    bestDist = d;
                    nearest = h.pos();
                }
            }
            // Move up to 400 toward that point
            Point newPos = z.curPos().stepTowards(nearest, 400);
            movedZ.add(new Zombie(z.id, newPos.x, newPos.y, 0, 0));
        }

        // ---------- 2️⃣ Ash moves ----------
        Point ashNext = state.getAsh().stepTowards(ashTarget, 1000);

        // ---------- 3️⃣ Shooting ----------
        List<Zombie> survivors = new ArrayList<>();
        int zombiesKilled = 0;
        for (Zombie mz : movedZ) {
            if (ashNext.dist(mz.curPos()) <= 2000) {
                zombiesKilled++;                // killed instantly
            } else {
                survivors.add(mz);
            }
        }

        // ---------- 4️⃣ Humans eaten ----------
        List<Human> remainingHumans = new ArrayList<>(state.getHumans());
        for (Iterator<Human> it = remainingHumans.iterator(); it.hasNext(); ) {
            Human h = it.next();
            for (Zombie mz : survivors) {
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

class TurnResult {
    public final int zombiesKilled;
    public final int humansLost;          // number of non‑Ash humans dead after this turn
    public final Point ashNextPos;

    public TurnResult(int zombiesKilled,
                      int humansLost,
                      Point ashNextPos) {
        this.zombiesKilled = zombiesKilled;
        this.humansLost = humansLost;
        this.ashNextPos = ashNextPos;
    }
}
class TurnSimulator {
    /** Simulate a single turn given a candidate destination for Ash. */
    public static TurnResult simulateTurn(Point ashPos,
                                          Point ashTarget,
                                          List<Human> humans,
                                          List<Zombie> zombies) {

        // ----- 1️⃣ Zombies move -----
        List<Zombie> movedZombies = new ArrayList<>();
        for (Zombie z : zombies) {
            // Find closest living human (including Ash at his *current* location)
            Point best = ashPos;      // start with Ash
            double bestDist = ashPos.dist(z.curPos());

            for (Human h : humans) {
                Point hp = h.pos();
                double d = hp.dist(z.curPos());
                if (d < bestDist) {
                    bestDist = d;
                    best = hp;
                }
            }

            // Move up to 400 towards `best`
            Point newPos = z.curPos().stepTowards(best, 400);
            movedZombies.add(new Zombie(z.id, newPos.x, newPos.y,
                    0, 0)); // nextPos not needed here
        }

        // ----- 2️⃣ Ash moves -----
        Point ashNext = ashPos.stepTowards(ashTarget, 1000);

        // ----- 3️⃣ Kill zombies within 2000 -----
        int killed = 0;
        List<Zombie> survivors = new ArrayList<>();
        for (Zombie mz : movedZombies) {
            if (ashNext.dist(mz.curPos()) <= 2000) {
                killed++;
            } else {
                survivors.add(mz);
            }
        }

        // ----- 4️⃣ Humans eaten -----
        int humansLost = 0;
        for (Zombie mz : survivors) {
            for (Human h : humans) {
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

class DecisionHelper {
    /** Build a small set of plausible destinations for Ash.
     *  - staying where he is,
     *  - moving straight toward each zombie (one step of 1000 units),
     *  - moving toward the centroid of all zombies (helps herd them together).
     */
    static List<Point> generateCandidates(Point ashPos, List<Zombie> zombies) {
        List<Point> cand = new ArrayList<>();

        // 0. stay put
        cand.add(new Point(ashPos.x, ashPos.y));

        // 1. one‑step toward each zombie
        for (Zombie z : zombies) {
            Point target = z.curPos();
            cand.add(ashPos.stepTowards(target, 1000));
        }

        // 2. toward the centroid of all zombies (optional)
        if (!zombies.isEmpty()) {
            long sumX = 0, sumY = 0;
            for (Zombie z : zombies) {
                sumX += z.x;
                sumY += z.y;
            }
            Point centroid = new Point((int)(sumX / zombies.size()),
                    (int)(sumY / zombies.size()));
            cand.add(ashPos.stepTowards(centroid, 1000));
        }

        // Remove duplicates (same coordinates) – helps later loops.
        Set<String> seen = new HashSet<>();
        List<Point> uniq = new ArrayList<>();
        for (Point p : cand) {
            String key = p.x + "," + p.y;
            if (!seen.contains(key)) {
                uniq.add(p);
                seen.add(key);
            }
        }
        return uniq;
    }
}

class ScoreHelper {
    public static int killValue(int humansAlive) {
        return humansAlive * humansAlive * 10;
    }

    /** Fibonacci numbers (1‑based) – we only need the first few. */
    public static long fib(int n) {
        long a = 1, b = 2;               // F1=1, F2=2
        if (n == 1) return a;
        if (n == 2) return b;
        for (int i = 3; i <= n; i++) {
            long c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    /** Compute the total score for killing `k` zombies in the same turn. */
    public static long multiKillScore(int k, int humansAlive) {
        long base = killValue(humansAlive);
        long total = 0;
        for (int i = 1; i <= k; i++) {
            // multiplier = Fib(i+2)   because the spec says (n+2)th term
            long mult = fib(i + 2);
            total += base * mult;
        }
        return total;
    }
}

class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }

    /** Euclidean distance (rounded down to int) */
    int dist(Point o) {
        long dx = (long)x - o.x;
        long dy = (long)y - o.y;
        return (int)Math.sqrt(dx*dx + dy*dy);
    }

    /** Return a new point moved at most `step` towards `target`. */
    Point stepTowards(Point target, int step) {
        if (dist(target) <= step) return new Point(target.x, target.y);
        double ratio = step / (double)dist(target);
        int nx = x + (int)Math.round((target.x - x) * ratio);
        int ny = y + (int)Math.round((target.y - y) * ratio);
        return new Point(nx, ny);
    }
}

class Human {
    int id, x, y;
    Human(int id, int x, int y) { this.id=id; this.x=x; this.y=y; }
    Point pos() { return new Point(x, y); }
}

class Zombie {
    int id, x, y, nx, ny;          // current and next‑turn positions supplied by input
    Zombie(int id, int x, int y, int nx, int ny) {
        this.id=id; this.x=x; this.y=y; this.nx=nx; this.ny=ny;
    }
    Point curPos() { return new Point(x, y); }
    Point nextPos() { return new Point(nx, ny); }
}