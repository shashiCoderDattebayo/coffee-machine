package com.shashi.coffeemachine.models;

import com.shashi.coffeemachine.core.BeverageServable;
import com.shashi.coffeemachine.exceptions.BusyOutletException;
import lombok.Getter;

@Getter
public class Outlet implements BeverageServable {
    public enum State {
        FREE, PROCESSING
    }

    private final int id;
    private State state;

    public Outlet(int id) {
        this.id = id;
        this.state = State.FREE;
    }

    private void setState(State state) {
        this.state = state;
    }

    @Override
    public void serveBeverage(Beverage beverage) {
        System.out.println(beverage.getName() + " is being served in Outlet- " + this.getId() + ".");
        try {
            Thread.sleep(beverage.getPreparationTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.releaseLock();
    }

    public synchronized void acquireLock() throws BusyOutletException {
        if (State.PROCESSING.equals(this.getState())) {
            throw new BusyOutletException();
        } else {
            this.setState(State.PROCESSING);
        }
    }

    private synchronized void releaseLock() {
        if (State.PROCESSING.equals(this.getState())) {
            this.setState(State.FREE);
        }
    }
}
