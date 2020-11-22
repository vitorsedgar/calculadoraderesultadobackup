package com.br.ages.calculadoraback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private long idProd;
    private String name;
    private String categoryName;
    private Double weight;
    private BigDecimal value;
}
