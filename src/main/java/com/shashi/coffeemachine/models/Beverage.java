package com.shashi.coffeemachine.models;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
public class Beverage {
    private final String name;
    private final Set<BeverageIngredient> beverageIngredients;
    private final long preparationTime = 10000;

    public Beverage(String name, Set<BeverageIngredient> beverageIngredients) {
        this.name = name;
        this.beverageIngredients = Sets.newHashSet(beverageIngredients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Beverage beverage = (Beverage) o;
        return Objects.equals(name, beverage.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
