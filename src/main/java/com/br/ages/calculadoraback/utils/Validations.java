package com.br.ages.calculadoraback.utils;

import com.br.ages.calculadoraback.utils.exceptions.ValidationsException;

import java.math.BigDecimal;

public class Validations {
    public static void validatePositiveNumber(Double value) {
        if (value < 0) {
            throw new ValidationsException("Não é permitido número negativo!");
        }
    }

    public static void validatePositiveNumber(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationsException("Não é permitido número negativo!");
        }
    }
}
