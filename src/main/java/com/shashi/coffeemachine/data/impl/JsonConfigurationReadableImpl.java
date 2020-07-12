package com.shashi.coffeemachine.data.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.shashi.coffeemachine.data.AppConfiguration;
import com.shashi.coffeemachine.data.ConfigurationReadable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class JsonConfigurationReadableImpl implements ConfigurationReadable {
    private final String fileName;
    private static final Logger logger = LoggerFactory.getLogger(JsonConfigurationReadableImpl.class);

    public JsonConfigurationReadableImpl(String configurationFileName) {
        this.fileName = configurationFileName;
    }

    public AppConfiguration getConfiguration() {
        ObjectMapper mapper = new ObjectMapper();
        AppConfiguration appConfiguration;
        try {
            URL resource = JsonConfigurationReadableImpl.class.getClassLoader().getResource(fileName);
            Preconditions.checkNotNull(resource, "Couldn't find the configuration file.");
            File file = new File(resource.getFile());
            logger.debug(file.getAbsolutePath());
            FileInputStream src = new FileInputStream(fileName);
            appConfiguration = mapper.readValue(src, AppConfiguration.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Couldn't parse the given config file into internal app configuration.", e);
        }
        logger.debug("Parsed the configuration file values.");
        return appConfiguration;
    }
}
