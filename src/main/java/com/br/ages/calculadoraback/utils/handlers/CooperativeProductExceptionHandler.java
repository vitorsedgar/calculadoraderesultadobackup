package com.br.ages.calculadoraback.utils.handlers;

import com.br.ages.calculadoraback.utils.exceptions.CooperativeProductWeightOutOfBoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CooperativeProductExceptionHandler {
    @ExceptionHandler(value = CooperativeProductWeightOutOfBoundException.class)
    public ResponseEntity<Object> weightOutOfBoundException(CooperativeProductWeightOutOfBoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}