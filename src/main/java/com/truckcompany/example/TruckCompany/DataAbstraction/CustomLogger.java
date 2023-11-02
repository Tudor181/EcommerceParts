package com.truckcompany.example.TruckCompany.DataAbstraction;

import java.util.logging.Logger;

public class CustomLogger {
    private static CustomLogger instance;
    private Logger logger;

    private CustomLogger() {
        logger = Logger.getLogger(CustomLogger.class.getName());
    }

    public static synchronized CustomLogger getInstance() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    public void log(String message) {
        logger.info(message);
    }

    public void logException(Throwable t) {
        logger.severe(t.getMessage());
    }
}