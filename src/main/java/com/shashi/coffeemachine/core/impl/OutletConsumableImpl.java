package com.shashi.coffeemachine.core.impl;

import com.shashi.coffeemachine.core.OutletConsumable;
import com.shashi.coffeemachine.exceptions.BusyOutletException;
import com.shashi.coffeemachine.models.Outlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OutletConsumableImpl implements OutletConsumable {
    private final Map<Integer, Outlet> outletMap;
    private static final Logger logger = LoggerFactory.getLogger(OutletConsumableImpl.class);

    public OutletConsumableImpl(int totalOutlets) {
        this.outletMap = initOutletMap(totalOutlets);
    }

    @Override
    public Outlet acquireLockAndGetFreeOutlet() throws BusyOutletException {
        return synchronizedAcquireLockAndGetFreeOutlet();
    }

    @Override
    public Outlet getWaitingFreeOutlet() {
        Outlet outlet;
        while (true) {
            try {
                outlet = this.acquireLockAndGetFreeOutlet();
                logger.debug("Acquired lock and got free outlet-{}.", outlet.getId());
                break;
            } catch (BusyOutletException e) {
                logger.debug("All outlets busy.");
            }
            try {
                logger.debug("Waiting for 1 sec to find a free outlet.");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.debug("Sleep got interrupted.", e);
            }
        }
        return outlet;
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
