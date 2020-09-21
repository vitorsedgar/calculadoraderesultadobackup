package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
}
