package com.br.ages.calculadoraback.dto;

import com.br.ages.calculadoraback.entity.CoopProdPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CooperativeProductDTO {
    private CoopProdPK coopProdPK;

    private Double weight;

    private BigDecimal value;

    private AssociateProductDTO associateProduct;
}
