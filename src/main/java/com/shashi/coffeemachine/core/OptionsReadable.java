package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.InvalidOptionException;
import com.shashi.coffeemachine.models.BeverageRequest;

public interface OptionsReadable {
    BeverageRequest readOptions() throws InvalidOptionException;
}
