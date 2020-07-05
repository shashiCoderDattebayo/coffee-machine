package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.OutletConsumable;
import com.shashi.coffeemachine.exceptions.BusyOutletException;
import com.shashi.coffeemachine.models.Outlet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OutletConsumableImpl implements OutletConsumable {
    private final Map<Integer, Outlet> outletMap;

    public OutletConsumableImpl(int totalOutlets) {
        this.outletMap = initOutletMap(totalOutlets);
    }

    @Override
    public Outlet acquireLockAndGetFreeOutlet() throws BusyOutletException {
        return synchronizedAcquireLockAndGetFreeOutlet();
    }

    private synchronized Outlet synchronizedAcquireLockAndGetFreeOutlet() throws BusyOutletException {
        Optional<Outlet> outletOptional = Optional.empty();
        for (Outlet outlet : outletMap.values()) {
            if (Outlet.State.FREE.equals(outlet.getState())) {
                outletOptional = Optional.of(outlet);
                break;
            }
        }
        if (outletOptional.isPresent()) {
            Outlet outlet = outletOptional.get();
            outlet.acquireLock();
            return outlet;
        } else {
            throw new BusyOutletException();
        }
    }

    private Map<Integer, Outlet> initOutletMap(int totalOutlets) {
        Map<Integer, Outlet> outletMap = new HashMap<>();
        for (int i = 0; i < totalOutlets; i++) {
            outletMap.put(i, new Outlet(i));
        }
        return outletMap;
    }
}
