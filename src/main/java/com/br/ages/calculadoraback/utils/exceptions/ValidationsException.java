package com.br.ages.calculadoraback.utils.exceptions;

public class ValidationsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ValidationsException(String message) {
        super(message);
    }
}