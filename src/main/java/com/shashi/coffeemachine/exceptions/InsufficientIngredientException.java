package com.shashi.coffeemachine.exceptions;

import com.shashi.coffeemachine.models.BeverageIngredient;

import java.util.Set;

public class InsufficientIngredientException extends Exception {
    private Set<BeverageIngredient> insuffiecientBeverageIngredients;

    public InsufficientIngredientException(Set<BeverageIngredient> insuffiecientBeverageIngredients) {
        this.insuffiecientBeverageIngredients = insuffiecientBeverageIngredients;
    }
}
