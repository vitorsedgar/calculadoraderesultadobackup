package com.br.ages.calculadoraback.utils.handlers;

import com.br.ages.calculadoraback.utils.exceptions.ValidationsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationsExceptionHandler {

    @ExceptionHandler(value = ValidationsException.class)
    public ResponseEntity<Object> validationsrException(ValidationsException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}