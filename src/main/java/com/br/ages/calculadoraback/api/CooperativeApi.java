package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.service.CooperativeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cooperative")
public class CooperativeApi {

		private CooperativeService cooperativeService;

		public CooperativeApi(CooperativeService cooperativeService) {
				this.cooperativeService = cooperativeService;
		}

		@GetMapping(path = "/")
		@ApiOperation(value = "Listar todas cooperativas")
		public ResponseEntity<List<CooperativeEntity>> findAllCoops() {
				log.info(" Start Find products by Cooperative");
				List<CooperativeEntity> coops = cooperativeService.findAll();
				log.info(" End Find products by Cooperative");
				return ResponseEntity.ok(coops);
		}

		@DeleteMapping(path = "/{codCoop}")
		@ApiOperation(value = "Deletar cooperativa por codigo")
		public ResponseEntity deleteCoop(@PathVariable(name = "codCoop") String codCoop) {
				log.info(" Start Find products by Cooperative");
				cooperativeService.deleteCoopByCodCoop(codCoop);
				log.info(" End Find products by Cooperative");
				return ResponseEntity.ok(Void.class);
		}
}
