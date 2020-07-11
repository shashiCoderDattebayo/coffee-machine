package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.models.IngredientStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class IngredientStockObserver implements PropertyChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(IngredientStockObserver.class);

    public IngredientStockObserver() {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int base_quantity = 5;
        Map<String, IngredientStock> ingredientStocksMap = (Map<String, IngredientStock>) evt.getNewValue();
        for (IngredientStock ingredientStock : ingredientStocksMap.values()) {
            System.out.println("Quantity: " + ingredientStock.getName() + " - " + ingredientStock.getQuantity().getStdUnits());
            if (ingredientStock.getQuantity().getStdUnits() < base_quantity) {
                System.out.println(ingredientStock.getName() + " is running low.");
            }
        }
    }
}
