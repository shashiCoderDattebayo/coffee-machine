package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.InvalidOptionException;

public interface OptionsReadable {
    int readOptions() throws InvalidOptionException;
}
