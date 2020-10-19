package com.br.ages.calculadoraback.utils.exceptions;

public class CooperativeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CooperativeNotFoundException() {
        super("Cooperativa não encontrada.");
    }

    public CooperativeNotFoundException(String codCoop) {
        super("A cooperativa " + codCoop + " não existe.");
    }
}
