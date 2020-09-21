package com.br.ages.calculadoraback.entity;

import lombok.*;

import javax.persistence.Column;
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
public class AnualResultPK implements Serializable {
		private static final long serialVersionUID = 1L;

		@JoinColumn(name = "id_coop")
		@ManyToOne(optional = false)
		private CooperativeEntity idCoop;

		@Column(name = "year")
		private Long year;
}
