package com.br.ages.calculadoraback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CooperativeEntity {
		@Id
		@SequenceGenerator(allocationSize = 1, name = "id_coop_seq", sequenceName = "id_coop_seq")
		@GeneratedValue(generator = "id_coop_seq", strategy = GenerationType.SEQUENCE)
		@Column(name = "id_coop")
		private Long idCoop;

		@Column(name = "cod_coop")
		private String codCoop;

		@Column(name = "name")
		private String name;

}
