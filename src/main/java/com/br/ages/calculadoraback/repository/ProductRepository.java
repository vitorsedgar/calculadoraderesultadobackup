package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    ProductEntity findByIdProd(Long prodId);

    Optional<ProductEntity> findByName(String name);
}
