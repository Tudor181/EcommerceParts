package com.truckcompany.example.TruckCompany.DataAbstraction;

import java.util.logging.Logger;

public class MyException extends Exception {
    private String errorCode; 
    private static final CustomLogger logger = CustomLogger.getInstance();

    public MyException(String message) {
        super(message);
        logger.log(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
        logger.logException(cause);
    }

    public MyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        logger.log(message);
    }

    public MyException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        logger.logException(cause);
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}