package com.br.ages.calculadoraback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CooperativeProductEntity {
    @EmbeddedId
    protected CoopProdPK coopProdPK;

    @Column
    private Double weight;

    @Column
    private BigDecimal value;
}