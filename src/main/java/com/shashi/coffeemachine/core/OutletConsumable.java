package com.shashi.coffeemachine.core;

import com.shashi.coffeemachine.exceptions.BusyOutletException;
import com.shashi.coffeemachine.models.Outlet;

public interface OutletConsumable {
    Outlet acquireLockAndGetFreeOutlet() throws BusyOutletException;

    Outlet getWaitingFreeOutlet();
}
