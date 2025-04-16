package com.car.manager.api.exception;

import com.car.manager.api.dto.exception.ErrorDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.UniqueValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class AppExceptionHandler {

    private static String NOT_BLANK = "NotBlank";

    @ExceptionHandler(value = InstanceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleInstanceNotFoundException(InstanceNotFoundException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        Optional<?> someBlankError = exception.getFieldErrors().stream().filter(item -> Objects.equals(item.getCode(), NOT_BLANK)).findFirst();
        if(someBlankError.isPresent())
            return new ResponseEntity<>(
                    new ErrorDTO("Missing fields", status.value()),
                    status
            );

        return new ResponseEntity<>(
                new ErrorDTO("Invalid fields", status.value()),
                status
        );
    }

    @ExceptionHandler({UniqueValueException.class})
    public ResponseEntity<Object> uniqueValueExceptionHandler(UniqueValueException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }
}
