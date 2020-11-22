package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.CooperativeDTO;
import com.br.ages.calculadoraback.service.CooperativeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cooperative")
public class CooperativeApi {

		private CooperativeService cooperativeService;

		public CooperativeApi(CooperativeService cooperativeService) {
				this.cooperativeService = cooperativeService;
    }

		@PostMapping(path = "")
		@ApiOperation("Registra uma cooperativa.")
		private Cooperative registerCoop(@RequestBody CooperativeDTO coop, @RequestHeader String authorization) {
				return cooperativeService.registerCoop(coop);
		}

		@DeleteMapping(path = "/{codCoop}")
		@ApiOperation("Deleta uma cooperativa passando seu código.")
		private void deleteCoop(@PathVariable("codCoop") String codCoop, @RequestHeader String authorization) {
				cooperativeService.deleteCoop(codCoop);
		}

		@GetMapping(path = "")
		@ApiOperation("Retorna todas as cooperativas cadastradas")
		public ResponseEntity<List<Cooperative>> findAllCooperatives(@RequestHeader String authorization) {
				return ResponseEntity.ok(cooperativeService.findAll());
		}
}
