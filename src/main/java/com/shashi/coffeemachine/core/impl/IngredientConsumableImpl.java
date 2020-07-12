package com.shashi.coffeemachine.core.impl;

import com.google.common.collect.Sets;
import com.shashi.coffeemachine.core.IngredientConsumable;
import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.exceptions.InsufficientQuantityException;
import com.shashi.coffeemachine.models.BeverageIngredient;
import com.shashi.coffeemachine.models.IngredientStock;
import com.shashi.coffeemachine.models.Quantity;
import com.shashi.coffeemachine.models.RefillRequest;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IngredientConsumableImpl implements IngredientConsumable  {
    private final Map<String, IngredientStock> ingredientStocksMap;
    private PropertyChangeSupport support;

    public IngredientConsumableImpl(Map<String, Integer> ingredientStocksMap, PropertyChangeListener ingredientStockObserver) {
        this.ingredientStocksMap = initIngredientStocks(ingredientStocksMap);
        support = new PropertyChangeSupport(this);
        support.addPropertyChangeListener(ingredientStockObserver);
    }

    @Override
    public synchronized void consumeIngredients(Set<BeverageIngredient> beverageIngredients) throws InsufficientIngredientException {
        Set<BeverageIngredient> insuffiecientBeverageIngredients = Sets.newHashSet();

        // Check feasibility
        for (BeverageIngredient beverageIngredient : beverageIngredients) {
            IngredientStock ingredientStock = ingredientStocksMap.get(beverageIngredient.getName());
            boolean isIngredientSufficient = (ingredientStock != null) && ingredientStock.checkFeasibility(beverageIngredient.getQuantity());
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
        support.firePropertyChange("ingredientStocksMap", null, ingredientStocksMap);
    }

    @Override
    public synchronized void refillIngredient(RefillRequest refillRequest) {
        IngredientStock ingredientStock = ingredientStocksMap.get(refillRequest.getIngredientName());
        if (ingredientStock != null) {
            Quantity quantity = new Quantity((-1) * refillRequest.getQuantity(), Quantity.DisplayType.ML);
            try {
                ingredientStock.consumeQuantity(quantity);
            } catch (InsufficientQuantityException e) {
                throw new RuntimeException("InsufficientQuantityException shouldn't occur on refill.");
            }
        } else {
            Quantity quantity = new Quantity(refillRequest.getQuantity(), Quantity.DisplayType.ML);
            ingredientStocksMap.put(refillRequest.getIngredientName(), new IngredientStock(refillRequest.getIngredientName(), quantity));
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
