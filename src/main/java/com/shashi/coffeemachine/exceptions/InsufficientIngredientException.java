package com.shashi.coffeemachine.exceptions;

import com.shashi.coffeemachine.models.BeverageIngredient;
import lombok.Getter;

import java.util.Set;

@Getter
public class InsufficientIngredientException extends Exception {
    private Set<BeverageIngredient> insuffiecientBeverageIngredients;

    public InsufficientIngredientException(Set<BeverageIngredient> insuffiecientBeverageIngredients) {
        this.insuffiecientBeverageIngredients = insuffiecientBeverageIngredients;
    }

    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BeverageIngredient insuffiecientBeverageIngredient : insuffiecientBeverageIngredients) {
            stringBuilder.append("Insufficient Ingredient:").append(insuffiecientBeverageIngredient.getName()).append(" - ").append(insuffiecientBeverageIngredient.getQuantity().getStdUnits()).append("\n");
        }
        return stringBuilder.toString();
    }
}
