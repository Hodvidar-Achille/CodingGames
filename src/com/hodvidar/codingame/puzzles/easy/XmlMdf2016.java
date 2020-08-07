package com.hodvidar.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/xml-mdf-2016
 * by Hodvidar
 **/
class XmlMdf2016 {

    private static final char END_TAG = '-';
    
    public static void main(String[] args) {
    	XmlMdf2016 m = new XmlMdf2016();
    	m.test();
    }
    
    private void test()
    {
        Scanner in = new Scanner(System.in);
        String sequence = in.nextLine();
        System.err.println("sequence:"+sequence);
        
        Set<Character> letters = new HashSet<>();
        TagHolder fisrtTag = new TagHolder();
        Tag currentHolder = fisrtTag;
        boolean nextClose = false;
        for(char c : sequence.toCharArray())
        {
            if(c == END_TAG)
            {
                nextClose = true;
                continue;
            }
            if(nextClose)
            {
                currentHolder = currentHolder.parent;
                nextClose = false;
                continue;
            }
            letters.add(c);
            Tag newTag = new Tag(c, currentHolder);
            currentHolder = newTag;
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        char max = fisrtTag.getHeavierTag(letters);

        System.out.println(max);
        in.close();
    }
    
	
	class Tag
	{
	    private final char name;
	    private final double depth;
	    private final List<Tag> children = new ArrayList<>();
	    public final Tag parent;
	    
	    public Tag(char name, Tag parent)
	    {
	        this.name = name;
	        this.parent = parent;
	        if(this.parent == null)
	        {
	            this.depth = 0;
	        }
	        else
	        {
	            this.depth = 1 + this.parent.depth;
	            this.parent.children.add(this);
	        }
	    }
	    
	    // recursive
	    protected double getWeight(char aName)
	    {
	        // System.err.println("Tag("+this.name+")#getWeight("+aName+")");
	        double totalWeight = 0;
	        for(Tag child : this.children)
	        {
	            if(aName == child.name)
	                totalWeight += 1/child.depth;
	            totalWeight += child.getWeight(aName);
	        }
	        // System.err.println("totalWeight="+totalWeight);
	        return totalWeight;
	    }
	}
	
	class TagHolder extends Tag
	{
	    public TagHolder()
	    {
	        super('_', null);
	    }
	    
	    public char getHeavierTag(Set<Character> tags)
	    {
	        System.err.println("getHeavierTag...:");
	        char heavier = '_';
	        double maxWeight = 0;
	        for(Character c : tags)
	        {
	            double weight = this.getWeight(c);
	            System.err.println("c: "+c+"  weight="+weight);
	            
	            if(weight < maxWeight)
	                continue;
	                
	            if(weight > maxWeight)
	            {
	                maxWeight = weight;
	                heavier = c;
	                continue;
	            }
	            
	            if(c < heavier)
	            {
	                maxWeight = weight;
	                heavier = c;
	            }
	        }
	        return heavier;
	    }
	}
}