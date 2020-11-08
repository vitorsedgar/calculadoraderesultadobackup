package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.NonCoopProductDTO;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.CategoryEntity;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.repository.ProductRepository;
import com.br.ages.calculadoraback.utils.exceptions.ProductNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.ProductsNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CooperativeProductRepository coopProductRepository;
    private UserService userService;
    private  CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CooperativeProductRepository coopProductRepository, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.coopProductRepository = coopProductRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public ProductEntity save(ProductEntity product) {
        return productRepository.save(product);
    }

    public ProductEntity getProductById(Long idProd) {
        return productRepository.findByIdProd(idProd);
    }

    public Page<ProductEntity> getAllProducts(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        Page<ProductEntity> pageProduct = productRepository.search(name.toLowerCase(), pageRequest);

        if (pageProduct == null || pageProduct.getContent() == null || pageProduct.getContent().isEmpty()) {
            throw new ProductsNotFoundException();
        }

        return pageProduct;
    }
}
