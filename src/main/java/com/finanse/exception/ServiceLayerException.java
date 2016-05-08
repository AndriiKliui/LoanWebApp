package com.finanse.exception;

public class ServiceLayerException extends RuntimeException {

    public ServiceLayerException() {
    }

    public ServiceLayerException(String message) {
        super(message);
    }
}
