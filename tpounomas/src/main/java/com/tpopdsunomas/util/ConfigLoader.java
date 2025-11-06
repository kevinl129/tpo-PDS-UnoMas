package com.tpopdsunomas.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private Properties props;
    private static ConfigLoader instancia;

    private ConfigLoader() {
        props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            
            if (input == null) {
                System.out.println("Error: No se pudo encontrar el archivo config.properties");
                return;
            }
            props.load(input);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigLoader getInstance() {
        if (instancia == null) {
            instancia = new ConfigLoader();
        }
        return instancia;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}