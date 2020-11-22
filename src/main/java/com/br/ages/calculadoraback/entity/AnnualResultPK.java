package com.br.ages.calculadoraback.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Embeddable
public class AnnualResultPK implements Serializable {
    private static final long serialVersionUID = 1L;

		@JoinColumn(name = "id_coop")
		@ManyToOne(optional = false, cascade = CascadeType.REMOVE)
    private CooperativeEntity idCoop;

    @Column(name = "year")
    private int year;
}
