package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.ClientProduct;
import com.br.ages.calculadoraback.service.CalculateResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/client")
public class ClientApi {

		private final CalculateResultService calculateResultService;

		public ClientApi(CalculateResultService calculateResultService) {
				this.calculateResultService = calculateResultService;
		}

		@PostMapping(path = "/calculate-result")
		public ResponseEntity getResult(@RequestBody List<ClientProduct> clientProducts) {
				BigDecimal result = calculateResultService.calculate(clientProducts);

				return ResponseEntity.ok(result);
		}

}
