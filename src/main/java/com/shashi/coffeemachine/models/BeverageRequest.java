package com.shashi.coffeemachine.models;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class BeverageRequest {
    private final String beverageName;

    public BeverageRequest(String beverageName) {
        checkNotNull(beverageName, "beverageName cannot be null.");
        this.beverageName = beverageName;
    }
}
