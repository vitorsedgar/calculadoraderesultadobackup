package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AddProductRequestDTO;
import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.CooperativeProductDTO;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.*;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.utils.Validations;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeProductWeightOutOfBoundException;
import com.br.ages.calculadoraback.utils.exceptions.ProductException;
import com.br.ages.calculadoraback.utils.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CooperativeProductService {

    private final CooperativeProductRepository coopProdRepository;
    private final CooperativeService cooperativeService;
    private final ProductService productService;
    private final UserService userService;

    public CooperativeProductService(CooperativeProductRepository coopProdRepository, CooperativeService cooperativeService, UserService userService, ProductService productService) {
        this.coopProdRepository = coopProdRepository;
        this.cooperativeService = cooperativeService;
        this.productService = productService;
        this.userService = userService;
    }

    public CooperativeProductEntity save(CooperativeProductEntity entity) {
        return coopProdRepository.save(entity);
    }

    public Product addProduct(String authorization, AddProductRequestDTO productDTO) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();

        // Verifica se cooperativa não existe
        if (cooperativeEntity == null)
            throw new CooperativeNotFoundException();

        ProductEntity productEntity = productService.getProductById(productDTO.getIdProd());

        CoopProdPK coopProdPK = CoopProdPK.builder()
                .idProd(productEntity)
                .idCoop(cooperativeEntity)
                .build();

        CooperativeProductEntity cooperativeProductEntity = findByCoopProdPK(coopProdPK);
        // Verifica se produto já foi cadastrado nesta cooperativa.
        if (cooperativeProductEntity != null)
            throw new ProductException("Este produto já foi cadastrado nesta cooperativa");

        this.validateProductWeight(productDTO.getWeight(), cooperativeEntity.getCodCoop());

        cooperativeProductEntity = CooperativeProductEntity.builder()
                .coopProdPK(coopProdPK)
                .value(productDTO.getValue())
                .weight(productDTO.getWeight())
                .build();
        CooperativeProductEntity productSaved = this.save(cooperativeProductEntity);
        return parseToProduct(productSaved);
    }

    public Product editProduct(String authorization, AddProductRequestDTO productRequestDTO) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();

        // Verifica se cooperativa não existe
        if (cooperativeEntity == null)
            throw new CooperativeNotFoundException();

        ProductEntity productEntity = productService.getProductById(productRequestDTO.getIdProd());

        CooperativeProductEntity coopProductEntity = findByCoopProdPK(
                CoopProdPK.builder()
                        .idCoop(cooperativeEntity)
                        .idProd(productEntity)
                        .build()
        );

        if (coopProductEntity == null)
            throw new ProductNotFoundException(productEntity.getIdProd());

        this.validateProductWeight(productRequestDTO.getWeight(), cooperativeEntity.getCodCoop());

        Validations.validatePositiveNumber(productRequestDTO.getValue());
        Validations.validatePositiveNumber(productRequestDTO.getWeight());

        coopProductEntity.setValue(productRequestDTO.getValue());
        coopProductEntity.setWeight(productRequestDTO.getWeight());

        CooperativeProductEntity product = this.coopProdRepository.save(coopProductEntity);
        return parseToProduct(product);
    }

    @Transactional
    public void deleteProduct(String authorization, Long productId) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        ProductEntity productEntity = productService.getProductById(productId);
        CoopProdPK coopProdPK = CoopProdPK.builder()
                .idCoop(cooperativeEntity)
                .idProd(productEntity)
                .build();
        // Validate if prod coop exists
        CooperativeProductEntity cooperativeProductEntity = findByCoopProdPK(coopProdPK);
        if (cooperativeProductEntity == null)
            throw new ProductNotFoundException(productEntity.getIdProd());
        coopProdRepository.deleteByCoopProdPK(coopProdPK);
    }

    private void validateProductWeight(Double weight, String codCoop) {
        Double totalWeightCoop = this.coopProdRepository.getCoopTotalWeight(codCoop).orElse(0.0);
        Double totalWeight = totalWeightCoop + weight;
        if (totalWeight > 100.0) {
            throw new CooperativeProductWeightOutOfBoundException();
        }
    }

    private Product parseToProduct(CooperativeProductEntity cooperativeProductEntity) {
        return Product.builder()
                .idProd(cooperativeProductEntity.getCoopProdPK().getIdProd().getIdProd())
                .name(cooperativeProductEntity.getCoopProdPK().getIdProd().getName())
                .value(cooperativeProductEntity.getValue())
                .weight(cooperativeProductEntity.getWeight())
                .categoryName(cooperativeProductEntity.getCoopProdPK().getIdProd().getCategory().getName())
                .build();
    }

    public List<CooperativeProductEntity> findByIdCoop(long idCoop) {
        return this.coopProdRepository.findByCoopProdPK_IdCoop_IdCoop(idCoop);
    }

    public CooperativeProductEntity findByCoopProdPK(CoopProdPK coopProdPK) {
        return this.coopProdRepository.findByCoopProdPK(coopProdPK).orElse(null);
    }

    public List<CooperativeProductDTO> getCooperativeProducts(List<AssociateProductDTO> products, CooperativeEntity cooperativeEntity) {
        return products.stream().map(product -> this.getProduct(product, cooperativeEntity)).collect(Collectors.toList());
    }

    protected CooperativeProductDTO getProduct(AssociateProductDTO product, CooperativeEntity cooperative) {
        CooperativeProductEntity cooperativeProductEntity = this.findByCoopProdPK(
                CoopProdPK.builder()
                        .idCoop(cooperative)
                        .idProd(ProductEntity.builder().idProd(product.getId()).build())
                        .build()
        );

        if (cooperativeProductEntity.getValue().compareTo(product.getValue()) < 0) {
            throw new ProductException("Valor informado não pode ser maior que o todal do produto!");
        }

        return CooperativeProductDTO.builder()
                .coopProdPK(cooperativeProductEntity.getCoopProdPK())
                .weight(cooperativeProductEntity.getWeight())
                .value(cooperativeProductEntity.getValue())
                .associateProduct(product)
                .build();
    }

    public List<Product> findProductsByCodCoop(String codCoop, String authorization) {
        UserEntity user = userService.getUser(authorization);

        return coopProdRepository.findByCoopProdPK_IdCoop_CodCoop(codCoop)
                .stream()
                .map(it -> Product.builder()
                        .name(it.getCoopProdPK().getIdProd().getName())
                        .idProd(it.getCoopProdPK().getIdProd().getIdProd())
                        .categoryName(it.getCoopProdPK().getIdProd().getCategory().getName())
                        .weight(it.getWeight())
                        .value(it.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
