package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.IngredientConsumable;
import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.exceptions.InsufficientQuantityException;
import com.google.common.collect.Sets;
import com.shashi.coffeemachine.models.BeverageIngredient;
import com.shashi.coffeemachine.models.IngredientStock;
import com.shashi.coffeemachine.models.Quantity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IngredientConsumableImpl implements IngredientConsumable {
    private final Map<String, IngredientStock> ingredientStocksMap;

    public IngredientConsumableImpl(Map<String, Integer> ingredientStocksMap) {
        this.ingredientStocksMap = initIngredientStocks(ingredientStocksMap);
    }

    @Override
    public synchronized void consumeIngredients(Set<BeverageIngredient> beverageIngredients) throws InsufficientIngredientException {
        Set<BeverageIngredient> insuffiecientBeverageIngredients = Sets.newHashSet();

        // Check feasibility
        for (BeverageIngredient beverageIngredient : beverageIngredients) {
            IngredientStock ingredientStock = ingredientStocksMap.get(beverageIngredient.getName());
            boolean isIngredientSufficient = ingredientStock.checkFeasibility(beverageIngredient.getQuantity());
            if (!isIngredientSufficient) {
                insuffiecientBeverageIngredients.add(beverageIngredient);
            }
        }
        if (!insuffiecientBeverageIngredients.isEmpty()) {
            throw new InsufficientIngredientException(insuffiecientBeverageIngredients);
        }

        // Consume the ingredients
        for (BeverageIngredient beverageIngredient : beverageIngredients) {
            IngredientStock ingredientStock = ingredientStocksMap.get(beverageIngredient.getName());
            try {
                ingredientStock.consumeQuantity(beverageIngredient.getQuantity());
            } catch (InsufficientQuantityException e) {
                throw new RuntimeException();
            }
        }
    }

    private Map<String, IngredientStock> initIngredientStocks(Map<String, Integer> ingredientStocks) {
        Map<String, IngredientStock> ingredientStocksMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : ingredientStocks.entrySet()) {
            IngredientStock ingredientStock = new IngredientStock(entry.getKey(), new Quantity(entry.getValue(), Quantity.DisplayType.ML));
            ingredientStocksMap.put(entry.getKey(), ingredientStock);
        }
        return ingredientStocksMap;
    }
}
