package com.shashi.coffeemachine.core.impl;

import com.google.common.collect.Sets;
import com.shashi.coffeemachine.core.BeverageRequestable;
import com.shashi.coffeemachine.core.IngredientConsumable;
import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.models.Beverage;
import com.shashi.coffeemachine.models.BeverageIngredient;
import com.shashi.coffeemachine.models.BeverageRequest;
import com.shashi.coffeemachine.models.Outlet;
import com.shashi.coffeemachine.models.Quantity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class BeverageRequestableImpl implements BeverageRequestable {
    private final Map<String, Beverage> beveragesMap;
    private final IngredientConsumable ingredientConsumable;

    public BeverageRequestableImpl(Map<String, Map<String, Integer>> beverages, IngredientConsumable ingredientConsumable) {
        this.beveragesMap = initBeverages(beverages);
        this.ingredientConsumable = ingredientConsumable;
    }

    @Override
    public void serveBeverage(BeverageRequest beverageRequest, Outlet outlet) throws InsufficientIngredientException {
        checkNotNull(beverageRequest, "beverageRequest cannot be null.");
        checkArgument(beveragesMap.containsKey(beverageRequest.getBeverageName()), "The requested beverage name is not configured.");
        Beverage beverage = beveragesMap.get(beverageRequest.getBeverageName());
        ingredientConsumable.consumeIngredients(beverage.getBeverageIngredients());
        outlet.serveBeverage(beverage);
    }

    private Map<String, Beverage> initBeverages(Map<String, Map<String, Integer>> beverages) {
        Map<String, Beverage> beveragesMap = new HashMap<>();
        for (Map.Entry<String, Map<String, Integer>> entry : beverages.entrySet()) {
            String beverageName = entry.getKey();
            Map<String, Integer> beverageIngredientMap = entry.getValue();
            Set<BeverageIngredient> beverageIngredients = Sets.newHashSet();
            for (Map.Entry<String, Integer> ingredientEntry : beverageIngredientMap.entrySet()) {
                BeverageIngredient beverageIngredient = new BeverageIngredient(ingredientEntry.getKey(), new Quantity(ingredientEntry.getValue(), Quantity.DisplayType.ML));
                beverageIngredients.add(beverageIngredient);
            }
            beveragesMap.put(beverageName, new Beverage(beverageName, beverageIngredients));
        }
        return beveragesMap;
    }

}
