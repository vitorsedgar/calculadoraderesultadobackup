package com.br.ages.calculadoraback.utils.exceptions;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException() {
        super("Produto inexistente");
    }

    public ProductNotFoundException(long prodId) {
        super("Produto de id " + prodId + " não existe");
    }
}
