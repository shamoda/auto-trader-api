package com.app.api.exception;

public class VehicleNotFoundException extends RuntimeException{
    public VehicleNotFoundException(String id){
        super("Vehicle " +id+ " is not available");
    }
}
