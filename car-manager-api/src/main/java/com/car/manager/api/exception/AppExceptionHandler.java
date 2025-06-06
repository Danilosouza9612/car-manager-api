package com.car.manager.api.exception;

import com.car.manager.api.dto.exception.ErrorDTO;
import com.car.manager.core.exception.InstanceNotFoundException;
import com.car.manager.core.exception.InvalidFileFormatException;
import com.car.manager.core.exception.UniqueValueException;
import com.car.manager.core.storage.FileStorageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.Optional;

@ControllerAdvice
public class AppExceptionHandler {

    private static String NOT_BLANK = "NotBlank";

    @ExceptionHandler(value = {InstanceNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleInstanceNotFoundException(InstanceNotFoundException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FileStorageException.class)
    public ResponseEntity<ErrorDTO> handleInstanceNotFoundException(FileStorageException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorDTO> handleAuthenticationException(org.springframework.security.core.AuthenticationException authException){
        HttpStatus code = HttpStatus.UNAUTHORIZED;
        if(authException instanceof InternalAuthenticationServiceException || authException instanceof BadCredentialsException)
            return new ResponseEntity<>(new ErrorDTO("Invalid login or password", code.value()),code);
        else
            return new ResponseEntity<>(new ErrorDTO(authException.getLocalizedMessage(), code.value()),code);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
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
    public ResponseEntity<ErrorDTO> uniqueValueExceptionHandler(UniqueValueException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidFileFormatException.class})
    public ResponseEntity<ErrorDTO> invalidFileFormatExceptionHandler(InvalidFileFormatException ex){
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
