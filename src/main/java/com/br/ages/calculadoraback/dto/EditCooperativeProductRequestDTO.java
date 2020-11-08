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
public class EditCooperativeProductRequestDTO {
    private Long idCooperative;
    private Long idProduct;
    private BigDecimal value;
    private Double weight;
}

