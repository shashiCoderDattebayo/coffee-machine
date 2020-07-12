package com.shashi.coffeemachine;

import com.shashi.coffeemachine.core.BeverageRequestable;
import com.shashi.coffeemachine.core.IngredientConsumable;
import com.shashi.coffeemachine.models.RefillRequest;
import com.shashi.coffeemachine.observers.IngredientStockObserver;
import com.shashi.coffeemachine.core.OptionsDisplayable;
import com.shashi.coffeemachine.core.OptionsReadable;
import com.shashi.coffeemachine.core.impl.OptionsReadableImpl;
import com.shashi.coffeemachine.core.OutletConsumable;
import com.shashi.coffeemachine.core.impl.BeverageRequestableImpl;
import com.shashi.coffeemachine.core.impl.IngredientConsumableImpl;
import com.shashi.coffeemachine.core.impl.OutletConsumableImpl;
import com.shashi.coffeemachine.core.impl.BeverageOptionsDisplayableImpl;
import com.shashi.coffeemachine.data.MachineConfiguration;
import com.shashi.coffeemachine.exceptions.InsufficientIngredientException;
import com.shashi.coffeemachine.exceptions.InvalidOptionException;
import com.shashi.coffeemachine.models.BeverageRequest;
import com.shashi.coffeemachine.models.Outlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CoffeeMachineApp {
    private final OutletConsumable outletConsumable;
    private final BeverageRequestable beverageRequestable;
    private final OptionsDisplayable optionsDisplayable;
    private final OptionsReadable optionsReadable;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final Map<Integer, String> beverageOptionsMap;
    private final IngredientConsumable ingredientConsumable;

    private static final Logger logger = LoggerFactory.getLogger(CoffeeMachineApp.class);

    CoffeeMachineApp(MachineConfiguration machineConfiguration) {
        int nThreads = machineConfiguration.getOutlets();
        beverageOptionsMap = constructBeverageOptionsMap(machineConfiguration);
        PropertyChangeListener ingredientStockObserver = new IngredientStockObserver();
        ingredientConsumable = new IngredientConsumableImpl(machineConfiguration.getIngredientsStock(), ingredientStockObserver);
        this.outletConsumable = new OutletConsumableImpl(machineConfiguration.getOutlets());
        this.beverageRequestable = new BeverageRequestableImpl(machineConfiguration.getBeverages(), ingredientConsumable);
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        this.optionsDisplayable = new BeverageOptionsDisplayableImpl(beverageOptionsMap);
        this.optionsReadable = new OptionsReadableImpl(machineConfiguration.getBeverages().size());
    }

    void start() {
        logger.debug("Starting the app.");
        while (true) {
            Outlet finalOutlet = outletConsumable.getWaitingFreeOutlet();
            logger.debug("Got free outlet - {}.", finalOutlet.getId());
            BeverageRequest finalBeverageRequest = getBeverageRequest();
            logger.debug("Got Beverage request for - {}.", finalBeverageRequest.getBeverageName());
            threadPoolExecutor.execute(() -> {
                try {
                    logger.debug("Launching thread for beverage {} on outlet {}.", finalBeverageRequest.getBeverageName(), finalOutlet.getId());
                    beverageRequestable.serveBeverage(finalBeverageRequest, finalOutlet);
                }  catch (InsufficientIngredientException e) {
                    System.out.println("Beverage request - " + finalBeverageRequest.getBeverageName() + " on outlet " + finalOutlet.getId() + " could not be served due to insufficient ingredient " +
                            "quantity.");
                    System.out.println(e.getMessage());
                    logger.debug(e.getMessage());
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.debug("Thread sleep waiting for options got interrupted", e);
            }
        }
    }

    private BeverageRequest getBeverageRequest() {
        BeverageRequest beverageRequest;
        while (true) {
            try {
                optionsDisplayable.displayOptions();
                logger.debug("Options displayed.");
                logger.debug("Waiting for user to select an option");
                int optionNo = optionsReadable.readOptions();
                if (optionNo == 0) {
                    RefillRequest refillRequest = readRefillRequest(ingredientConsumable.getIngredients());
                    ingredientConsumable.refillIngredient(refillRequest);
                } else {
                    beverageRequest = new BeverageRequest(beverageOptionsMap.get(optionNo));
                    break;
                }
            } catch (InvalidOptionException e) {
                logger.debug("User selected an invalid option.");
                System.out.println("The option selected is Invalid. Please select a valid option.");
            }
        }
        return beverageRequest;
    }

    private RefillRequest readRefillRequest(Set<String> ingredients) {
        Scanner sc = new Scanner(System.in);
        for (String ingredient : ingredients) {
            System.out.println(ingredient);
        }
        while (true) {
            System.out.print("Enter an Ingredient to refill: ");
            String option = sc.nextLine();
            if (ingredients.contains(option)) {
                System.out.print("Please enter quantity to refill: ");
                int quantity = sc.nextInt();
                return new RefillRequest(option, quantity);
            } else {
                System.out.print("Invalid Ingredient");
            }
        }
    }

    private static Map<Integer, String> constructBeverageOptionsMap(MachineConfiguration machineConfiguration) {
        Set<String> beverages = machineConfiguration.getBeverages().keySet();
        Map<Integer, String> beverageMap = new HashMap<>();
        int i = 1;
        for (String beverage : beverages) {
            beverageMap.put(i, beverage);
            i++;
        }
        return beverageMap;
    }
}
