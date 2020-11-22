package com.br.ages.calculadoraback.utils.handlers;

import com.br.ages.calculadoraback.utils.exceptions.AnnualResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class AnnualResultExceptionHandler {
    @ExceptionHandler(value = AnnualResultException.class)
    public ResponseEntity<Object> exception(AnnualResultException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}