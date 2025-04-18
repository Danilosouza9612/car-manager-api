package com.car.manager.core.exception;

public class InvalidFileFormatException extends IllegalArgumentException{
    public InvalidFileFormatException(String msg){
        super(msg);
    }
}
