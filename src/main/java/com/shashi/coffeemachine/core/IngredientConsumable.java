package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.models.BeverageIngredient;

import java.util.Set;

public interface IngredientConsumable {
    public void consumeIngredients(Set<BeverageIngredient> beverageIngredients) throws InsufficientIngredientException;
}
