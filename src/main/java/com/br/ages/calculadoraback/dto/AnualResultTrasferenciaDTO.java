package com.br.ages.calculadoraback.dto;

import com.br.ages.calculadoraback.entity.CooperativeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnualResultTrasferenciaDTO {
    private BigDecimal result;
    private int year;
}