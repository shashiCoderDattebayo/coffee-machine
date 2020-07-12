package com.shashi.coffeemachine;

import com.shashi.coffeemachine.data.AppConfiguration;
import com.shashi.coffeemachine.data.ConfigurationReadable;
import com.shashi.coffeemachine.data.MachineConfiguration;
import com.shashi.coffeemachine.data.impl.JsonConfigurationReadableImpl;

public class App {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.err.println("Invalid command line, exactly one argument required");
            System.exit(1);
        }
        ConfigurationReadable configurationReadable = new JsonConfigurationReadableImpl(args[0]);
        AppConfiguration appConfiguration = configurationReadable.getConfiguration();
        MachineConfiguration machineConfiguration = appConfiguration.getMachineConfiguration();
        CoffeeMachineApp app = new CoffeeMachineApp(machineConfiguration);
        app.start();
    }
}
