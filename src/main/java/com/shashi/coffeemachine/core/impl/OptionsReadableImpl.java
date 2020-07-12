package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.OptionsReadable;
import com.shashi.coffeemachine.exceptions.InvalidOptionException;
import com.shashi.coffeemachine.models.BeverageRequest;

import java.util.Map;
import java.util.Scanner;

public class OptionsReadableImpl implements OptionsReadable {
    private final Map<Integer, String> beverageOptionsMap;

    public OptionsReadableImpl(Map<Integer, String> beverageOptionsMap) {
        this.beverageOptionsMap = beverageOptionsMap;
    }

    @Override
    public BeverageRequest readOptions() throws InvalidOptionException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an Option: ");
        int option = sc.nextInt();
        if ((option < 1) || (option > beverageOptionsMap.size())) {
            throw new InvalidOptionException();
        }
        String beverageName = beverageOptionsMap.get(option);
        System.out.println("You have selected: " + beverageName);
        return new BeverageRequest(beverageName);
    }
}
