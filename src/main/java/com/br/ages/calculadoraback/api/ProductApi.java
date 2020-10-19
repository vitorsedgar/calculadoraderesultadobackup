package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AddProductRequestDTO;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.service.CooperativeProductService;
import com.br.ages.calculadoraback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductApi {

    private final ProductService productService;
    private final CooperativeProductService cooperativeProductService;

    public ProductApi(ProductService productService, CooperativeProductService cooperativeProductService) {
        this.productService = productService;
        this.cooperativeProductService = cooperativeProductService;
    }

    // FIXME: Descomentar codigo e enviar authorization para funcao addProduct
    @PostMapping(path = "add")
    public ResponseEntity<Product> addProduct(/* @RequestHeader(name = "authorization") String authorization, */
                                                           @RequestBody AddProductRequestDTO productDTO) {
        log.info(" Start Add product");
        Product product = this.cooperativeProductService.addProduct("", productDTO);
        log.info(" End Add product");
        return ResponseEntity.ok(product);
    }

    @GetMapping(path = "/{codCoop}")
    public ResponseEntity<List<Product>> findProductsByCoop(@RequestHeader(name = "authorization") String authorization,
                                                            @PathVariable(name = "codCoop") String codCoop) {
        log.info(" Start Find products by Cooperative");
        List<Product> products = productService.findProductsByCodCoop(codCoop, authorization);
        log.info(" End Find products by Cooperative");
        return ResponseEntity.ok(products);
    }
}
