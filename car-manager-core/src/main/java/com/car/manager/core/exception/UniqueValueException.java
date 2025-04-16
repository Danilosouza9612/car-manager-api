package com.car.manager.core.exception;

public class UniqueValueException extends IllegalArgumentException{
    public UniqueValueException(String entity){
        super(entity+" already exists");
    }
}
