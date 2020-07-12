package com.shashi.coffeemachine.models;

import lombok.Getter;

@Getter
public class RefillRequest {
    private final String ingredientName;
    private final int quantity;

    public RefillRequest(String ingredientName, int quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }
}
