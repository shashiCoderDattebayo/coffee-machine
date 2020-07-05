package com.shashi.coffeemachine.models;

import java.util.Objects;

public class BeverageIngredient {
    private final String name;
    private final Quantity quantity;

    public BeverageIngredient(String name, Quantity quantity) {
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
        BeverageIngredient that = (BeverageIngredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
