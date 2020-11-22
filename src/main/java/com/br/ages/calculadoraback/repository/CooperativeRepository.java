package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.CooperativeEntity;
import org.springframework.data.repository.CrudRepository;

public interface CooperativeRepository extends CrudRepository<CooperativeEntity, Long> {
    CooperativeEntity findByCodCoop(String codCoop);
}
