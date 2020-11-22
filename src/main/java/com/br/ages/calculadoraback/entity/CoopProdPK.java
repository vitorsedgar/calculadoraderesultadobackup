package com.br.ages.calculadoraback.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Embeddable
public class CoopProdPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "id_coop")
    @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private CooperativeEntity idCoop;

    @JoinColumn(name = "id_prod")
    @ManyToOne(optional = false)
    private ProductEntity idProd;
}
