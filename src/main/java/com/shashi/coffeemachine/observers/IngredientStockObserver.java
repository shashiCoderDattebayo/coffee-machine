package com.shashi.coffeemachine.observers;

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

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int base_quantity = 10;
        Map<String, IngredientStock> ingredientStocksMap = (Map<String, IngredientStock>) evt.getNewValue();
        StringBuilder stringBuilder = new StringBuilder().append("Ingredient Stocks: \n");
        for (IngredientStock ingredientStock : ingredientStocksMap.values()) {
            stringBuilder.append(ingredientStock.getName()).append(" - ").append(ingredientStock.getQuantity().getStdUnits()).append("\n");
            if (ingredientStock.getQuantity().getStdUnits() <= base_quantity) {
                System.out.println(ingredientStock.getName() + " is running low.");
            }
        }
        logger.debug(stringBuilder.toString());
    }
}
