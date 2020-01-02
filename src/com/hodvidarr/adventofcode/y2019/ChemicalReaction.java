package com.hodvidarr.adventofcode.y2019;

import java.util.HashMap;
import java.util.Map;

public final class ChemicalReaction
{
	public final String finalElement;

	public final double quantity;

	public final Map<String, Double> requiredElementsAndQuantity;

	public ChemicalReaction(String finalElement, double quantity)
	{
		this.finalElement = finalElement;
		this.quantity = quantity;
		this.requiredElementsAndQuantity = new HashMap<>();
	}

	public void addRequiredElement(String element, double amount)
	{
		this.requiredElementsAndQuantity.put(element, amount);
	}

}
