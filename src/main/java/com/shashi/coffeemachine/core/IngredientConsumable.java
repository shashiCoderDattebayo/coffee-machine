package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.models.BeverageIngredient;
import com.shashi.coffeemachine.models.RefillRequest;

import java.util.Set;

public interface IngredientConsumable {
    void consumeIngredients(Set<BeverageIngredient> beverageIngredients) throws InsufficientIngredientException;
    void refillIngredient(RefillRequest refillRequest);
}
