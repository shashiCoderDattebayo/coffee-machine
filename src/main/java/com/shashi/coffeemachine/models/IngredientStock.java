package com.shashi.coffeemachine.models;

import com.shashi.coffeemachine.exceptions.InsufficientQuantityException;

import java.util.Objects;

public class IngredientStock {
    private String name;
    private Quantity quantity;

    public IngredientStock(String name, Quantity quantity) {
        this.name = name;
        this.quantity = new Quantity(quantity.getStdUnits(), quantity.getDisplayDisplayType());
    }

    public String getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IngredientStock that = (IngredientStock) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean checkFeasibility(Quantity quantity) {
        return (this.quantity.compareTo(quantity) >= 0);
    }

    public synchronized void consumeQuantity(Quantity that) throws InsufficientQuantityException {
        this.quantity.consumeQuantity(that);
    }
}
