package com.shashi.coffeemachine.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JsonConfigurationReadableImpl implements ConfigurationReadable {
    private static String fileName = "/Users/shashikanth.rp/questions/coffee-machine/src/main/java/com/shashi/coffeemachine/data/sampleData.json";
    private final URL fileUri;

    public JsonConfigurationReadableImpl() {
        this.fileUri = getClass().getResource(fileName);
    }

    public Configuration getConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        Configuration configuration = null;
        try {
            configuration = mapper.readValue(new File(fileName), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
