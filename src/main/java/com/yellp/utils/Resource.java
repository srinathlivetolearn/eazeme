package com.yellp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public enum Resource {
    PROPERTIES(Paths.get("src","main","resources", "application.properties"));

    private Properties properties;

    private Logger LOGGER = LoggerFactory.getLogger(Resource.class);

    Resource(Path path) {
        this.properties = new Properties();
        try {
            properties.load(Files.newInputStream(path));
        } catch (IOException e) {
            LOGGER.error("Error loading properties.",e);
        }
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }
}
