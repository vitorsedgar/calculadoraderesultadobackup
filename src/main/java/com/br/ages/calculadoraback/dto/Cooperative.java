package com.br.ages.calculadoraback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cooperative {
		private Long idCoop;
		private String codCoop;
		private String name;
}
