package com.hodvidar.codingame.puzzles.hard;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/code-vs-zombies
 */
// CURRENT VERSION SCORE : 31760 (Goal: 40000+)
public class CodeVsZombies {
    // empty
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        while(true) {
            final Player.Point bestDest = Player.gameLoop(in);
            System.out.println(bestDest.x + " " + bestDest.y);
        }
    }
}

class Player {
    public static final int MAP_W = 16000;
    public static final int MAP_H = 9000;
    public static final int ASH_STEP = 1000;
    public static final int DEPTH_OF_SIMULATION = 0;
    public static final int SEARCH_COMPLEXITY = 0;
    public static final boolean DEBUG = false;

    private static void printlnErr(final String message) {
        if (DEBUG) {
            final java.time.LocalTime now = java.time.LocalTime.now();
            final String timeStr = String.format("%02d:%02d:%02d;%03d",
                    now.getHour(), now.getMinute(), now.getSecond(), now.getNano() / 1_000_000);
            System.err.println(timeStr + " " + message);
        }
    }

    public static Point gameLoop(final Scanner in) {
        // 1. Retrieve data from the Game system
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
        printlnErr("Read input position : \n Ash: " + ashPos.x + "," + ashPos.y);
        printlnErr(" Humans: " + humans.size() + " zombies: " + zombies.size());
        // 2. Simulate the game
        final GameState currentGameState = new GameState(ashPos, humans, zombies);
        final CandidateResult candidateResult = DecisionHelper.generateCandidates(currentGameState);
        final CandidatePoint bestCandidate = candidateResult.getBestCandidate(currentGameState);
        return bestCandidate.point;
    }

    public record CandidateResult(List<CandidatePoint> candidates) {

        public CandidatePoint getBestCandidate(final GameState gameState) {
                CandidatePoint best = null;
                int bestScore = Integer.MIN_VALUE;
                for (final CandidatePoint cp : candidates) {
                    final int score = cp.getBestCandidateScore();
                    if (score > bestScore) {
                        bestScore = score;
                        best = cp;
                    }
                }
                // Si aucun score > 0, retourne le candidat par défaut
                if (best != null && bestScore > 0) {
                    return best;
                }
                return getCandidatePointForSaveClosestHumanLogic(gameState);
            }

        private CandidatePoint getCandidatePointForSaveClosestHumanLogic(GameState gameState) {
            final Point ash = gameState.ash();
            final List<Human> humans = gameState.humans();
            final List<Zombie> zombies = gameState.zombies();

            if (!humans.isEmpty()) {
                for (Human human : humans.stream()
                        .sorted(Comparator.comparingInt(h -> ash.dist(h.pos())))
                        .toList()) {
                    final Point humanPos = human.pos();
                    final int ashDist = ash.dist(humanPos);
                    final int ashTurns = (int) Math.ceil((ashDist - 1999) / (double) Player.ASH_STEP);
                    // Trouver le zombie le plus proche de cet humain
                    final int zombieDist = zombies.stream()
                            .mapToInt(z -> z.curPos().dist(humanPos))
                            .min()
                            .orElse(Integer.MAX_VALUE);
                    final int zombieTurns = (int) Math.ceil(zombieDist / 400.0);
                    if (ashTurns < zombieTurns) {
                        final Point dest = ash.stepTowardsAndStopAt(humanPos, Player.ASH_STEP);
                        return new CandidatePoint(dest, 0, null);
                    }
                }
                // Aucun humain sauvable, on va vers le plus proche comme avant
                final Human closest = humans.stream()
                        .min(Comparator.comparingInt(h -> ash.dist(h.pos())))
                        .orElse(humans.getFirst());
                final Point dest = ash.stepTowardsAndStopAt(closest.pos(), Player.ASH_STEP);
                return new CandidatePoint(dest, 0, null);
            }
// Si aucun humain, rester sur place
            return new CandidatePoint(ash, 0, null);
        }
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
         * Convenience: number of humans still alive (does NOT include Ash).
         */
        public int getAliveHumans() {
            return humans.size();
        }
    }

    static class DecisionHelper {

        private DecisionHelper() {
        }

        /**
         * Build a small set of plausible destinations for Ash.
         * - staying where he is,
         * - moving straight toward each zombie (one step of 1000 units),
         * - moving toward the centroid of all zombies (helps herd them together).
         */
        static CandidateResult generateCandidates(final GameState gameState) {
            return generateCandidates(gameState, 1, 0);
        }

        static CandidateResult generateCandidates(final GameState gameState,
                                                  final int currentDepth,
                                                  final int currentScore) {
            if (currentDepth == DEPTH_OF_SIMULATION + 1) {
                return new CandidateResult(Collections.emptyList());
            }
            printlnErr("Testing depth " + currentDepth + " with score " + currentScore);
            printlnErr("Testing state: Ash " + gameState.ash.x + "," + gameState.ash.y +
                    " Humans " + gameState.humans.size() + " Zombies " + gameState.zombies.size());
            final List<CandidatePoint> candidates = new ArrayList<>();
            final List<Point> possiblePoints = generatePossiblePoints(gameState);
            for (final Point p : possiblePoints) {
                final GameState nextState = GameSimulator.applyMove(gameState, p);
                final int zombiesKilled = gameState.zombies().size() - nextState.zombies().size();
                final int humansAlive = nextState.getAliveHumans();
                final int score = (humansAlive == 0) ? 0 : currentScore + (int) ScoreHelper.multiKillScore(zombiesKilled, humansAlive);
                final CandidateResult subResult = generateCandidates(nextState, currentDepth + 1, score);
                final CandidatePoint cp = new CandidatePoint(p, score, subResult.candidates());
                candidates.add(cp);
            }
            return new CandidateResult(candidates);
        }

        static List<Point> generatePossiblePoints(final GameState gameState) {
            if(SEARCH_COMPLEXITY == 0) {
                return Collections.emptyList();
            }

            final Point ashPos = gameState.ash();
            final List<Human> humans = gameState.humans();
            final List<Zombie> zombies = gameState.zombies();

            final List<Point> cand = new ArrayList<>();

            // 0. stay put
            cand.add(new Point(ashPos.x, ashPos.y));

            if (SEARCH_COMPLEXITY >= 1) {
                // 1. toward each human (optional, but helps)
                for (final Human h : humans) {
                    final Point targetHuman = h.pos();
                    cand.add(ashPos.stepTowardsAndStopAt(targetHuman, ASH_STEP));
                }
            }

            if (SEARCH_COMPLEXITY >= 2) {
                // 2. one‑step toward each zombie
                for (final Zombie z : zombies) {
                    final Point targetZombie = z.curPos();
                    cand.add(ashPos.stepTowardsAndStopAt(targetZombie, ASH_STEP));
                }
            }

            if (SEARCH_COMPLEXITY >= 3) {
                // 3. toward the centroid of all zombies
                if (!zombies.isEmpty()) {
                    long sumX = 0, sumY = 0;
                    for (final Zombie z : zombies) {
                        sumX += z.x;
                        sumY += z.y;
                    }
                    final Point centroid = new Point((int) (sumX / zombies.size()),
                            (int) (sumY / zombies.size()));
                    cand.add(ashPos.stepTowardsAndStopAt(centroid, ASH_STEP));
                }
            }

            if (SEARCH_COMPLEXITY >= 4) {
                // 4. toward the closest point from Ash on the line between each human and closest zombies
                for (final Human h : humans) {
                    cand.addAll(generateDirectionsToLineBetweenHumanAndClosestZombies(ashPos, h, zombies));
                }
            }

            // Remove duplicates (same coordinates) – helps later loops.
            return removeDuplicates(cand);
        }

        private static List<Point> removeDuplicates(final List<Point> cand) {
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

        static List<Point> generateDirectionsToLineBetweenHumanAndClosestZombies(
                final Point ashPos,
                final Human human,
                final List<Zombie> zombies) {
            final List<Point> results = new ArrayList<>();
            final List<Zombie> closestZombies = zombies.stream()
                    .sorted(Comparator.comparingInt(z -> human.pos().dist(z.curPos())))
                    .limit(3)
                    .toList();
            for (final Zombie z : closestZombies) {
                final Point humanPos = human.pos();
                final Point zombiePos = z.curPos();

                final int x1 = humanPos.x;
                final int y1 = humanPos.y;
                final int x2 = zombiePos.x;
                final int y2 = zombiePos.y;
                final int x0 = ashPos.x;
                final int y0 = ashPos.y;

                final double dx = x2 - x1;
                final double dy = y2 - y1;
                final double lengthSquared = dx * dx + dy * dy;
                if (lengthSquared == 0) continue;

                double t = ((x0 - x1) * dx + (y0 - y1) * dy) / lengthSquared;
                t = Math.clamp(t, 0, 1);

                final int px = (int) Math.round(x1 + t * dx);
                final int py = (int) Math.round(y1 + t * dy);
                final Point closestPoint = new Point(px, py);

                results.add(ashPos.stepTowardsAndStopAt(closestPoint, Player.ASH_STEP));
            }
            return results;
        }
    }

    static class GameSimulator {


        private GameSimulator() {
        }

        /**
         * Apply Ash's chosen destination and return the resulting state after ONE turn.
         */
        public static GameState applyMove(final GameState state,
                                          final Point ashTarget) {
            // ---------- 1️⃣ Zombies move ----------
            final List<Zombie> movedZombies = moveZombies(state);
            // ---------- 2️⃣ Ash moves ----------
            final Point ashNext = state.ash().stepTowardsAndStopAt(ashTarget, ASH_STEP);
            // ---------- 3️⃣ Shooting ----------
            final List<Zombie> zombieSurvivors = ashKillZombies(movedZombies, ashNext);
            // ---------- 4️⃣ Humans eaten ----------
            final List<Human> remainingHumans = zombiesKillHumans(state, zombieSurvivors);
            // Return the new immutable snapshot
            return new GameState(ashNext, remainingHumans, zombieSurvivors);
        }

        private static List<Zombie> moveZombies(final GameState state) {
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
                final Point newPos = z.curPos().stepTowardsAndStopAt(nearest, 400);
                movedZ.add(new Zombie(z.id, newPos.x, newPos.y, 0, 0));
            }
            return movedZ;
        }

        private static List<Zombie> ashKillZombies(final List<Zombie> zombiesPresent, final Point ashPosition) {
            final List<Zombie> zombieSurvivors = new ArrayList<>();
            for (final Zombie currentZombie : zombiesPresent) {
                if (ashPosition.dist(currentZombie.curPos()) > 2000) {
                    zombieSurvivors.add(currentZombie);
                }
            }
            return zombieSurvivors;
        }

        private static List<Human> zombiesKillHumans(final GameState state, final List<Zombie> zombieSurvivors) {
            final List<Human> remainingHumans = new ArrayList<>(state.humans());
            for (final Iterator<Human> it = remainingHumans.iterator(); it.hasNext(); ) {
                final Human h = it.next();
                for (final Zombie mz : zombieSurvivors) {
                    if (mz.curPos().x == h.x && mz.curPos().y == h.y) {
                        // This human is eaten – remove him
                        it.remove();
                        break;
                    }
                }
            }
            return remainingHumans;
        }

    }


    static class ScoreHelper {

        /**
         * Le système de points fonctionne ainsi :
         * La valeur d'un zombie tué est égal au nombre d'humains encore en vie au carré et multiplié par 10, sans inclure Ash.
         * Si plusieurs zombies sont détruits pendant un même tour, la valeur du nème zombie tué est multiplié par le (n+2)ème
         * terme de la suite de Fibonacci (1, 2, 3, 5, 8, etc). Vous avez donc tout intérêt à tuer un maximum de zombies dans un même tour !
         */
        private ScoreHelper() {
        }

        static int killValue(final int humansAlive) {
            return (humansAlive * humansAlive) * 10;
        }

        /**
         * Fibonacci numbers (1‑based) – we only need the first few.
         */
        private static long fib(final int n) {
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
         * Compute the total score for killing `zombiesKilled` zombies in the same turn.
         */
        public static long multiKillScore(final int zombiesKilled, final int humansAlive) {
            if(zombiesKilled <= 0 || humansAlive <= 0) {
                return 0;
            }
            final long base = killValue(humansAlive);
            if(zombiesKilled == 1) {
                return base;
            }
            long total = 0;
            for (int i = 1; i <= zombiesKilled; i++) {
                // multiplier = Fib(i+2)   because the spec says (n+2)th term
                final long mult = fib(i);
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
        Point stepTowardsAndStopAt(final Point target, final int step) {
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

    static class CandidatePoint {
        Point point;
        int score;
        List<CandidatePoint> subCandidates;
        Integer bestCandidateScore; // null si non calculé ou invalidé

        CandidatePoint(final Point point, final int score, final List<CandidatePoint> subCandidates) {
            this.point = point;
            this.score = score;
            this.subCandidates = subCandidates != null ? subCandidates : new ArrayList<>();
        }

        public int getBestCandidateScore() {
            if (subCandidates == null || subCandidates.isEmpty()) {
                return score;
            }
            if (bestCandidateScore != null) {
                return bestCandidateScore;
            }
            // recalculer si bestCandidateScore est null ou la taille de la liste a changé
            int best = Integer.MIN_VALUE;
            for (final CandidatePoint cp : subCandidates) {
                final int subScore = cp.getBestCandidateScore();
                if (subScore > best) {
                    best = subScore;
                }
            }
            bestCandidateScore = best;
            return bestCandidateScore;
        }

        public void addSubCandidate(final CandidatePoint cp) {
            if (this.subCandidates == null) {
                this.subCandidates = new ArrayList<>();
            }
            this.subCandidates.add(cp);
            this.bestCandidateScore = null; // invalide le cache
        }

        public void addSubCandidates(final List<CandidatePoint> cps) {
            if (this.subCandidates == null) {
                this.subCandidates = new ArrayList<>();
            }
            this.subCandidates.addAll(cps);
            this.bestCandidateScore = null; // invalide le cache
        }
    }
}