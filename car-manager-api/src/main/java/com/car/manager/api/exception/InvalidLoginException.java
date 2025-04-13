package com.car.manager.api.exception;

public class InvalidLoginException extends IllegalArgumentException{
    public InvalidLoginException(){
        super("Invalid login or password");
    }
}
