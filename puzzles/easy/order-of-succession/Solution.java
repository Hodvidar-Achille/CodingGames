import java.util.*;
import java.io.*;
import java.math.*;

/**
 *          https://www.codingame.com/ide/puzzle/order-of-succession
 * by Hodvidar (TODO)
 **/
class Solution {

    private static final String GENDER_MALE = "M";
    private static final String RELIGION_CATHOLIC = "Catholic";
    private static final String ALIVE = "-";
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        FamilyTree familyTree = new FamilyTree();
        
        for (int i = 0; i < n; i++) {
            String name = in.next();
            String parent = in.next();
            Person parentP = familyTree.getPerson(parent);
            
            int birth = in.nextInt();
            String death = in.next();
            boolean dead = !ALIVE.equals(death);
            String religion = in.next();
            boolean catholic = RELIGION_CATHOLIC.equals(religion);
            String gender = in.next();
            boolean male = GENDER_MALE.equals(gender);
            Person p = new Person(name, parentP, birth, dead, catholic, male);
            
            familyTree.addPerson(p);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("orDeroFsucceSsion");
    }
}

/**
 * Family Tree that automatically exclude Dead and Catholic.
 */
class FamilyTree
{
    private final Map<String, Person> personsFast = new HashMap<>();
    private final Collection<Person> persons = new ArrayList<>();
    
    public FamilyTree()
    {
        // nothing
    }
    
    public void addPerson(Person p)
    {
        this.personsFast.put(p.getName(), p);
        this.persons.add(p);
    }
    
    public Person getPerson(String name)
    {
        return personsFast.get(name);
    }
}

class Person implements Comparable<Person>
{
    private final String name;
    private final Person parent;
    private final int birth;
    private final boolean dead;
    private final boolean catholic;
    private final boolean male;
    
    private List<Person> siblings = new ArrayList<>();
    private List<Person> children = new ArrayList<>();
    
    private final int generation;
    
    private int score = 0;
    
    public Person(String name, Person parent, int birth, boolean dead, boolean catholic, boolean male)
    {
        this.name = name;
        this.parent = parent;
        this.birth = birth;
        this.dead = dead;
        this.catholic = catholic;
        this.male = male;
        
        if(this.parent != null)
            this.parent.addChildren(this);
            
        this.generation = this.computeGenerationNumber();
    }
    
    // Comparable impl
    @Override
    public int compareTo(Person o)
    {
        // --- Generation ---
        if(this.generation < o.getGeneration())
            return -1;
        if(this.generation > o.getGeneration())
            return 1;
        // same generation
        
        // --- Gender ---
        if(this.male && !o.isMale())
            return -1;
        if(!this.male && o.isMale())
            return 1;
        // same gender
        
        // --- Age ---
        // negative value if this is older : 1900 - 1950 = -50
        return this.birth - o.getBirth();
    }
    
    private int computeGenerationNumber()
    {
        if(this.parent == null)
            return 0;
        return 1 + this.parent.computeGenerationNumber();
    }
    
    public void addChildren(Person p)
    {
        this.children.add(p);
    }
    
    
    // ---- Getters ----
    
    public String getName()
    {
        return this.name;
    }
    
    public Person getParent()
    {
        return this.parent;
    }
    
    public boolean isHigherAncestor()
    {
        return this.parent == null;
    }
    
    public int getBirth()
    {
        return this.birth;
    }
    
    public boolean isDead()
    {
        return this.dead;
    }
    
    public boolean isCatholic()
    {
        return this.catholic;
    }
    
    public boolean isMale()
    {
        return this.male;
    }
    
    public int getGeneration()
    {
        return this.generation;
    }
    
}

class SucessionComparator implements Comparator<Person> 
{
  @Override
  public int compare(Person x, Person y) 
  {
    // TODO
  }

}
