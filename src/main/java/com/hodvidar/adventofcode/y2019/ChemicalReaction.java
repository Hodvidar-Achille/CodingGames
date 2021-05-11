package com.hodvidar.adventofcode.y2019;

import java.util.HashMap;
import java.util.Map;

public final class ChemicalReaction {
    public final String finalElement;

    public final double quantity;

    public final Map<String, Double> requiredElementsAndQuantity;

    public ChemicalReaction(final String finalElement, final double quantity) {
        this.finalElement = finalElement;
        this.quantity = quantity;
        this.requiredElementsAndQuantity = new HashMap<>();
    }

    public void addRequiredElement(final String element, final double amount) {
        this.requiredElementsAndQuantity.put(element, amount);
    }

}
