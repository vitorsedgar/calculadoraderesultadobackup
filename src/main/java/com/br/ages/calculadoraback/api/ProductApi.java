package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductApi {

		private final ProductService productService;

		public ProductApi(ProductService productService) {
				this.productService = productService;
		}

		@GetMapping(path = "/{codCoop}")
		public ResponseEntity<List<Product>> findProductsByCoop(@PathVariable(name = "codCoop") String codCoop) {
				log.info(" Start Find products by Cooperative");
				List<Product> products = productService.findProductsByCodCoop(codCoop);
				log.info(" End Find products by Cooperative");
				return ResponseEntity.ok(products);
		}
}
