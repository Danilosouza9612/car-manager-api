package com.car.manager.api.exception;

import com.car.manager.api.security.ErrorSerialization;
import com.car.manager.api.security.MethodArgumentNotValidErrorSerialization;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = InstanceNotFoundException.class)
    public ResponseEntity<Object> handleInstanceNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        return new ResponseEntity<Object>(new MethodArgumentNotValidErrorSerialization(exception).getListMap(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({UniqueValueException.class})
    public ResponseEntity<Object> uniqueValueExceptionHandler(UniqueValueException uniqueValueException){
        ErrorSerialization errorSerialization = new ErrorSerialization();
        errorSerialization.addError(uniqueValueException.getField(), uniqueValueException.getMessage());
        return new ResponseEntity<Object>(errorSerialization.getListMap(), HttpStatus.CONFLICT);
    }

}
