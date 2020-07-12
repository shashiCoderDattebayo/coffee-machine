package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.OptionsDisplayable;

import java.util.Map;

public class SimpleOptionsDisplayableImpl implements OptionsDisplayable {
    private final Map<Integer, String> beverageOptionsMap;

    public SimpleOptionsDisplayableImpl(Map<Integer, String> beverageOptionsMap) {
        this.beverageOptionsMap = beverageOptionsMap;
    }

    @Override
    public void displayOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ready to serve Beverage. Please enter an option No.\n");
        for (int i = 1; i <= beverageOptionsMap.size(); i++) {
            stringBuilder.append(i).append(". ").append(beverageOptionsMap.get(i)).append("\n");
        }
        System.out.print(stringBuilder.toString());
    }
}
