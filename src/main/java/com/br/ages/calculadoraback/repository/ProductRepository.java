package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    ProductEntity findByIdProd(Long prodId);

    @Query("FROM ProductEntity p WHERE LOWER(p.name) like %:name% ")
    Page<ProductEntity> search(@Param("name") String name, Pageable pageable);
}
