package com.br.ages.calculadoraback.utils.exceptions;

public class CooperativeProductWeightOutOfBoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CooperativeProductWeightOutOfBoundException() {
        super("O total de pesos dos produtos não pode exceder 100%");
    }

}
