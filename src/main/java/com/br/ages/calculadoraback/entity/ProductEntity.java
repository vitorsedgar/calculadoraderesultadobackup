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
public class ProductEntity {
		@Id
		@SequenceGenerator(allocationSize = 1, name = "id_product_seq", sequenceName = "id_product_seq")
		@GeneratedValue(generator = "id_product_seq", strategy = GenerationType.SEQUENCE)
		@Column(name = "id_prod")
		private Long idProd;

		@Column
		private String name;

		@JoinColumn(name = "id_category")
		@ManyToOne(optional = true)
		private CategoryEntity category;
}