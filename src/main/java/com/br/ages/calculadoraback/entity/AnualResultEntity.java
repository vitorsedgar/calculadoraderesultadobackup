package com.br.ages.calculadoraback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnualResultEntity {
    @EmbeddedId
    protected AnualResultPK anualResultPK;

    @Column(name = "result")
    private BigDecimal result;

}
