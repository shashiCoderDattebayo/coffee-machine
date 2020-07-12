package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.OptionsReadable;
import com.shashi.coffeemachine.exceptions.InvalidOptionException;

import java.util.Scanner;

public class OptionsReadableImpl implements OptionsReadable {
    private final int beverageOptions;

    public OptionsReadableImpl(int beverageOptions) {
        this.beverageOptions = beverageOptions;
    }

    @Override
    public int readOptions() throws InvalidOptionException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an Option: ");
        int option = sc.nextInt();
        if ((option < 0) || (option > beverageOptions)) {
            throw new InvalidOptionException();
        }
        return option;
    }
}
