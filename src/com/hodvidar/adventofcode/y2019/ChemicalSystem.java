package com.hodvidar.adventofcode.y2019;

import java.util.HashMap;
import java.util.Map;

public final class ChemicalSystem
{
	public static final String ORE = "ORE";
	public static final String FUEL = "FUEL";

	private double costInOreOfReaction = 0;
	private final boolean isQuantityOfOreDefined;

	private final Map<String, ChemicalReaction> reactions;

	private final Map<String, Double> available;

	public ChemicalSystem()
	{
		this(false, 0d);
	}

	public ChemicalSystem(boolean isQuantityOfOreDefined, double oreQuantity)
	{
		this.reactions = new HashMap<>();
		this.available = new HashMap<>();
		this.isQuantityOfOreDefined = isQuantityOfOreDefined;
		this.addQuantityAvailable(ORE, oreQuantity);
	}

	/**
	 * Parses the string into correct objects and add the reaction into the system.
	 * <p>
	 * <b>Note:</b> Add all the reactions to the system before using it.
	 * @param fullReaction - a String representing the reaction. <br/>
	 * Example: <br/>
	 * '7 ORE => 5 C' <br/>
	 * '3 A, 4 B => 1 AB' <br/>
	 */
	public void addReaction(String fullReaction)
	{
		String[] reaction = fullReaction.split("=>");
		String needed = reaction[0];
		String result = reaction[1];
		result = result.trim();
		String[] result2 = result.split(" ");
		double quantity = Double.parseDouble(result2[0]);
		String finalElement = result2[1];
		ChemicalReaction chemicReac = new ChemicalReaction(finalElement,
				quantity);
		String[] needed2 = needed.split(",");
		for (String neededElement : needed2)
		{
			neededElement = neededElement.trim();
			String[] neededElement2 = neededElement.split(" ");
			double quantity2 = Double.parseDouble(neededElement2[0]);
			String aNeededElement = neededElement2[1];
			chemicReac.addRequiredElement(aNeededElement, quantity2);
		}
		this.reactions.put(finalElement, chemicReac);
		this.available.put(finalElement, 0d);
	}

	public double getQuantityAvailable(String element)
	{
		Double q = this.available.get(element);
		if(q == null)
		{
			this.available.put(element, 0d);
			return 0d;
		}
		return q;
	}

	private void setQuantityAvailable(String element, double quantity)
	{
		this.available.put(element, quantity);
	}

	private void addQuantityAvailable(String element, double quantity)
	{
		setQuantityAvailable(element, getQuantityAvailable(element) + quantity);
	}

	private void deductQuantityAvailable(String element, double quantity)
	{
		double available = getQuantityAvailable(element);
		if(available < quantity)
			throw new IllegalStateException(
					"Cannot deduct more then what is available");
		setQuantityAvailable(element, available - quantity);
	}

	public boolean initReaction(String finalElement)
	{
		if(!isQuantityOfOreDefined && ORE.equals(finalElement))
		{
			costInOreOfReaction++;
			addQuantityAvailable(ORE, 1);
			return true;
		}

		ChemicalReaction finalReaction = this.reactions.get(finalElement);
		// For each element needed : 
		while (true)
		{
			boolean success = executeReaction(finalReaction);
			if(!success)
				return false;
			this.addQuantityAvailable(finalElement, finalReaction.quantity);
			if(isQuantityOfOreDefined && FUEL.equals(finalElement))
			{
				continue; // until success is false.
				// Can be really long, it should be optimized
			}
			return true;
		}
	}

	private boolean executeReaction(ChemicalReaction finalReaction)
	{
		for (String element : finalReaction.requiredElementsAndQuantity
				.keySet())
		{
			double quantityNeeded = finalReaction.requiredElementsAndQuantity
					.get(element);
			double quantityAvailable = getQuantityAvailable(element);
			// 1) Check if we have enough of it
			if(quantityNeeded <= quantityAvailable)
			{
				deductQuantityAvailable(element, quantityNeeded);
			}
			// 2) Or init a reaction to have more
			else
			{
				if(ORE.equals(element) && isQuantityOfOreDefined)
				{
					return false;
				}
				while (quantityNeeded > quantityAvailable)
				{
					boolean success = initReaction(element);
					if(!success)
						return false;
					quantityAvailable = getQuantityAvailable(element);
				}
				deductQuantityAvailable(element, quantityNeeded);
			}
		}
		return true;
	}

	public double getCostInOreOfReaction()
	{
		return this.costInOreOfReaction;
	}

}
