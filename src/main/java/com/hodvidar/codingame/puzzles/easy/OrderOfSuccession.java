package com.hodvidar.codingame.puzzles.easy;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/order-of-succession by Hodvidar
 **/
public class OrderOfSuccession {

    private static final String GENDER_MALE = "M";
    private static final String RELIGION_CATHOLIC = "Catholic";
    private static final String ALIVE = "-";

    public static void main(final String[] args) {
        final OrderOfSuccession o = new OrderOfSuccession();
        o.test();
    }

    public void test() {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt();
        // Could use 'n' to optimize familyTree lists.

        final FamilyTree familyTree = new FamilyTree();

        for (int i = 0; i < n; i++) {
            final String name = in.next();
            final String parent = in.next();
            final Person parentP = familyTree.getPerson(parent);

            final int birth = in.nextInt();
            final String death = in.next();
            final boolean dead = !ALIVE.equals(death);
            final String religion = in.next();
            final boolean catholic = RELIGION_CATHOLIC.equals(religion);
            final String gender = in.next();
            final boolean male = GENDER_MALE.equals(gender);
            final Person p = new Person(name, parentP, birth, dead, catholic, male);

            familyTree.addPerson(p);
        }
        in.close();

        final Collection<Person> succesors = familyTree.getSuccessors();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        for (final Person p : succesors)
            System.out.println(p.getName());
    }

    /**
     * Family Tree that automatically exclude Dead and Catholic.
     */
    class FamilyTree {
        private final Map<String, Person> personsFast = new HashMap<>();
        private final List<Person> persons = new ArrayList<>();
        private final Collection<Person> firstAncestors = new ArrayList<>();

        public FamilyTree() {
            // nothing
        }

        public void addPerson(final Person p) {
            this.personsFast.put(p.getName(), p);
            this.persons.add(p);
            if (p.getParent() == null)
                this.firstAncestors.add(p);
        }

        public Person getPerson(final String name) {
            return personsFast.get(name);
        }

        private void assignScores() {
            // 1) order children of each parent
            for (final Person p : this.firstAncestors) {
                p.sortDescendant();
            }

            // 2) Recursively assign score
            final ScoreHolder sh = new ScoreHolder();
            for (final Person p : this.firstAncestors) {
                p.assignScore(sh);
            }
        }

        private void removeDeadAndCatholic() {
            final Iterator<Person> ite = persons.iterator();
            while (ite.hasNext()) {
                final Person p = ite.next();
                if (p.isDead() || p.isCatholic())
                    ite.remove();
            }
        }

        public Collection<Person> getSuccessors() {
            this.assignScores();
            this.removeDeadAndCatholic();
            Collections.sort(this.persons, new SucessionComparator());
            return this.persons;
        }
    }

    class ScoreHolder {
        private int score = Integer.MIN_VALUE;

        /**
         * -1 each time
         */
        public int getScore() {
            this.score++;
            return score;
        }
    }

    class Person implements Comparable<Person> {
        private final String name;
        private final Person parent;
        private final int birth;
        private final boolean dead;
        private final boolean catholic;
        private final boolean male;

        private final List<Person> children = new ArrayList<>();

        private final int generation;

        private int score = 0;

        public Person(final String name, final Person parent, final int birth, final boolean dead, final boolean catholic, final boolean male) {
            this.name = name;
            this.parent = parent;
            this.birth = birth;
            this.dead = dead;
            this.catholic = catholic;
            this.male = male;

            if (this.parent != null)
                this.parent.addChildren(this);

            this.generation = this.computeGenerationNumber();
        }

        // Comparable impl
        @Override
        public int compareTo(final Person o) {
            // --- Generation ---
            if (this.generation < o.getGeneration())
                return -1;
            if (this.generation > o.getGeneration())
                return 1;
            // same generation

            // --- Gender ---
            if (this.male && !o.isMale())
                return -1;
            if (!this.male && o.isMale())
                return 1;
            // same gender

            // --- Age ---
            // negative value if this is older : 1900 - 1950 = -50
            return this.birth - o.getBirth();
        }

        /**
         * Recursively orders children and their children.
         */
        public void sortDescendant() {
            Collections.sort(this.children);
            for (final Person p : this.children) {
                p.sortDescendant();
            }
        }

        public void assignScore(final ScoreHolder sh) {
            this.score = sh.getScore();
            for (final Person p : this.children) {
                p.assignScore(sh);
            }
        }

        private int computeGenerationNumber() {
            if (this.parent == null)
                return 0;
            return 1 + this.parent.computeGenerationNumber();
        }

        public void addChildren(final Person p) {
            this.children.add(p);
        }

        // ---- Getters ----

        public String getName() {
            return this.name;
        }

        public Person getParent() {
            return this.parent;
        }

        public boolean isHigherAncestor() {
            return this.parent == null;
        }

        public int getBirth() {
            return this.birth;
        }

        public boolean isDead() {
            return this.dead;
        }

        public boolean isCatholic() {
            return this.catholic;
        }

        public boolean isMale() {
            return this.male;
        }

        public int getGeneration() {
            return this.generation;
        }

        public int getScore() {
            return this.score;
        }

    }

    class SucessionComparator implements Comparator<Person> {
        @Override
        public int compare(final Person x, final Person y) {
            return x.getScore() - y.getScore();
        }

    }
}
