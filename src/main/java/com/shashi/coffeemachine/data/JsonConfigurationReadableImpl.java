package com.shashi.coffeemachine.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonConfigurationReadableImpl implements ConfigurationReadable {
    private static String fileName = "/Users/shashikanth.rp/questions/coffee-machine/src/main/java/com/shashi/coffeemachine/data/sampleData.json";

    public JsonConfigurationReadableImpl() {
    }

    public AppConfiguration getConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        AppConfiguration appConfiguration = null;
        try {
            appConfiguration = mapper.readValue(new File(fileName), AppConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appConfiguration;
    }
}
