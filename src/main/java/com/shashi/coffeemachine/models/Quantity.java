package com.shashi.coffeemachine.models;

import com.shashi.coffeemachine.exceptions.InsufficientQuantityException;

public class Quantity implements Comparable<Quantity> {
    public enum DisplayType {
        ML(1), L(1000);
        private int conversionFactor;
        DisplayType(int conversionFactor) {
            this.conversionFactor = conversionFactor;
        }
    }

    private int stdUnits;
    private DisplayType displayDisplayType;

    public Quantity(int quantity, DisplayType displayDisplayType) {
        this.stdUnits = quantity*(displayDisplayType.conversionFactor);
        this.displayDisplayType = displayDisplayType;
    }

    public int getStdUnits() {
        return stdUnits;
    }

    public DisplayType getDisplayDisplayType() {
        return displayDisplayType;
    }


    @Override
    public int compareTo(Quantity that) {
        return (this.getStdUnits() - that.getStdUnits());
    }

    public synchronized void consumeQuantity(Quantity that) throws InsufficientQuantityException {
        if (this.getStdUnits() > that.getStdUnits()) {
            this.stdUnits = (this.getStdUnits() - that.getStdUnits());
        } else {
            throw new InsufficientQuantityException();
        }
    }
}
