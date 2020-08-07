package com.hodvidar.adventofcode.y2019;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node
{
	private final String name;
	private Integer level;
	private Node parent;
	private final Set<Node> children;

	public Node(String name)
	{
		this.name = name;
		this.level = null;
		this.children = new HashSet<>();
	}

	public boolean connectToParent(Node parent)
	{
		if(this.parent != null)
			return false;

		this.parent = parent;
		parent.children.add(this);
		return true;
	}

	public String getName()
	{
		return name;
	}

	public int getLevel()
	{
		return level;
	}

	public void propagadeLevels(int level)
	{
		this.level = level;
		for (Node child : this.children)
		{
			child.propagadeLevels(level + 1);
		}
	}

	public Node findCommonAncestor(Node other)
	{
		Map<String, Node> myAncestors = this.getAncestors();

		Node p = other.parent;
		while (p != null)
		{
			Node n = myAncestors.get(p.name);
			if(n != null)
				return n;
			p = p.parent;
		}
		return null;
	}

	private Map<String, Node> getAncestors()
	{
		Map<String, Node> myAncestors = new HashMap<>();
		Node p = this.parent;
		while (p != null)
		{
			myAncestors.put(p.getName(), p);
			p = p.parent;
		}
		return myAncestors;
	}

	@Override
	public String toString()
	{
		String p = (this.parent == null) ? "@null" : this.parent.getName();
		String c = "[";
		for (Node child : this.children)
			c += child.getName() + ", ";
		c += "]";
		String s = "Node '" + this.name + "' parent:'" + p + " children:" + c;
		return s;
	}
}
