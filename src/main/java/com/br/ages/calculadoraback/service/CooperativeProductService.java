package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AddProductRequestDTO;
import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.CooperativeProductDTO;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.*;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.ProductException;
import com.br.ages.calculadoraback.utils.exceptions.ProductNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        // FIXME: Descomentar e testar com usuário de role cooperative
        // TODO: avaliar se não existe um limite para o peso
        // UserEntity user = this.userService.getLoggedUser();
        // Long idCoop = this.cooperativeService.getCoopByCodCoop(user.getCodCoop().getCodCoop()).getIdCoop();

        CooperativeEntity cooperativeEntity = cooperativeService.getCoopByCodCoop("0101");
        // Verifica se cooperativa não existe
        if (cooperativeEntity == null)
            throw new CooperativeNotFoundException();

        ProductEntity productEntity = productService.getProductById(productDTO.getIdProd());
        // Verifica se produto não existe
        if (productEntity == null)
            throw new ProductNotFoundException(productDTO.getIdProd());

        CooperativeProductEntity cooperativeProductEntity = CooperativeProductEntity.builder()
            .coopProdPK(
                CoopProdPK.builder()
                    .idProd(productEntity)
                    .idCoop(cooperativeEntity)
                    .build())
            .value(productDTO.getValue())
            .weight(productDTO.getWeight())
            .build();
        CooperativeProductEntity productSaved = this.save(cooperativeProductEntity);
        return parseToProduct(productSaved);
    }

    private Product parseToProduct(CooperativeProductEntity cooperativeProductEntity) {
        return Product.builder()
            .idProd(cooperativeProductEntity.getCoopProdPK().getIdProd().getIdProd())
            .name(cooperativeProductEntity.getCoopProdPK().getIdProd().getName())
            .value(cooperativeProductEntity.getValue())
            .weight(cooperativeProductEntity.getWeight())
            // Descomentar após ter categoria / Lucas Martins
            //.categoryName(cooperativeProductEntity.getCoopProdPK().getIdProd().getCategory().getName())
            .build();
    }

    public List<CooperativeProductEntity> findByIdCoop(long idCoop) {
        return this.coopProdRepository.findByCoopProdPK_IdCoop_IdCoop(idCoop);
    }

    public CooperativeProductEntity findByCoopProdPK(CoopProdPK coopProdPK) {
        return this.coopProdRepository.findByCoopProdPK(coopProdPK);
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
        if (cooperativeProductEntity == null) {
            throw new ProductNotFoundException(product.getId());
        }
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
}
