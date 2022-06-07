package com.example.oop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.File;

@Data
public class Properties {
    private String keycloakFile;
    private String databaseUrl;
    private String databaseUsername;
    private String databasePassword;

    private static Properties instance = null;

    @SneakyThrows
    public static Properties getInstance() {
        if (instance == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String pathToConfigFile = System.getenv("CONFIG_FILE");
            instance = objectMapper.readValue(new File(pathToConfigFile), Properties.class);
        }
        return instance;
    }
}
