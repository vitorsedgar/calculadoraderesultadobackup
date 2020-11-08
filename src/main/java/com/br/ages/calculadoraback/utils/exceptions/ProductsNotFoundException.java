package com.br.ages.calculadoraback.utils.exceptions;

public class ProductsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductsNotFoundException() {
        super("Produtos não encontrados");
    }

}
