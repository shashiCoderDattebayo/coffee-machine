package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.models.BeverageRequest;
import com.shashi.coffeemachine.models.Outlet;

public interface BeverageRequestable {
    public void serveBeverage(BeverageRequest beverageRequest, Outlet outlet) throws InsufficientIngredientException;
}
