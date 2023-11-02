package com.truckcompany.example.TruckCompany.DataAbstraction;

public class MyException extends Exception {
    private String errorCode; 

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public MyException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}