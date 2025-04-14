package com.car.manager.core.exception;

public class InstanceNotFoundException extends IllegalArgumentException{
    public InstanceNotFoundException(){
        super("instance not found");
    }
}
