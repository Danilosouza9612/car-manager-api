package com.car.manager.core.exception;

public class UniqueValueException extends IllegalArgumentException{
    private String field;
    public UniqueValueException(String field){
        super("Already exists");
        this.field = field;
    }

    public String getField(){
        return field;
    }
}
