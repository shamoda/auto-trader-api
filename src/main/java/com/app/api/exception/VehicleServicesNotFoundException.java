package com.app.api.exception;

public class VehicleServicesNotFoundException extends RuntimeException{
    public VehicleServicesNotFoundException(String id) {
        super("Could not find Service" + id);
    }
}
