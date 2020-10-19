package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CooperativeProductRepository coopProductRepository;
    private UserService userService;

    public ProductService(ProductRepository productRepository, CooperativeProductRepository coopProductRepository, UserService userService) {
        this.productRepository = productRepository;
        this.coopProductRepository = coopProductRepository;
        this.userService = userService;
    }

    public ProductEntity saveProduct(Product product) {
        ProductEntity entity = ProductEntity.builder()
                .name(product.getName())
                .category(null)
                .build();
        return productRepository.save(entity);
    }

    public ProductEntity getProductById(Long idProd) {
        return productRepository.findByIdProd(idProd);
    }

    public List<Product> findProductsByCodCoop(String codCoop, String authorization) {
        UserEntity user = userService.getUser(authorization);

        return coopProductRepository.findByCoopProdPK_IdCoop_CodCoop(codCoop)
                .stream()
                .map(it -> Product.builder()
                        .name(it.getCoopProdPK().getIdProd().getName())
                        .idProd(it.getCoopProdPK().getIdProd().getIdProd())
                        //TODO descomentar quando houver categorias
//								.categoryName(it.getCoopProdPK().getIdProd().getCategory().getName())
                        .weight(it.getWeight())
                        .categoryName("Cartão")
                        .build())
                .collect(Collectors.toList());
    }
}
