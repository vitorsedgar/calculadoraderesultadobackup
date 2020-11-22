package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AddProductRequestDTO;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.service.CooperativeProductService;
import com.br.ages.calculadoraback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductApi {

    private final ProductService productService;
    private final CooperativeProductService cooperativeProductService;

    public ProductApi(ProductService productService, CooperativeProductService cooperativeProductService) {
        this.productService = productService;
        this.cooperativeProductService = cooperativeProductService;
    }

    // TODO Mover lógica para CooperativeProductApi
    @PostMapping(path = "/add")
    public ResponseEntity<Product> addProduct(@RequestHeader(name = "authorization") String authorization,
                                              @RequestBody AddProductRequestDTO productDTO) {
        log.info(" Start Add product");
        Product product = this.cooperativeProductService.addProduct(authorization, productDTO);
        log.info(" End Add product");
        return ResponseEntity.ok(product);
    }

    // TODO Mover lógica para CooperativeProductApi
    @PutMapping(path = "/edit")
    public ResponseEntity<Product> editProduct(@RequestHeader(name = "authorization") String authorization,
                                               @RequestBody AddProductRequestDTO productDTO) {
        log.info(" Start Edit product");
        Product product = this.cooperativeProductService.editProduct(authorization, productDTO);
        log.info(" End Edit product");
        return ResponseEntity.ok(product);
    }

    // TODO Mover lógica para CooperativeProductApi
    @DeleteMapping(path = "/delete/{productId}")
    public void deleteProduct(@RequestHeader(name = "authorization") String authorization, @PathVariable(name = "productId") Long productId) {
        this.cooperativeProductService.deleteProduct(authorization, productId);
    }

    @GetMapping(path = "/{codCoop}")
    public ResponseEntity<List<Product>> findProductsByCoop(@RequestHeader(name = "authorization") String authorization,
                                                            @PathVariable(name = "codCoop") String codCoop) {
        log.info(" Start Find products by Cooperative");
        List<Product> products = cooperativeProductService.findProductsByCodCoop(codCoop, authorization);
        log.info(" End Find products by Cooperative");
        return ResponseEntity.ok(products);
    }

    // TODO: liberar rota somente a usuários de role "coop"
    @GetMapping(path = "")
    public ResponseEntity<Page<ProductEntity>> findProducts(@RequestHeader(name = "authorization") String authorization,
                                                            @RequestParam("name") String name,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info(" Start Find products by Cooperative");
        Page<ProductEntity> products = productService.getAllProducts(name, page, size);
        log.info(" End Find products by Cooperative");
        return ResponseEntity.ok(products);
    }
}
