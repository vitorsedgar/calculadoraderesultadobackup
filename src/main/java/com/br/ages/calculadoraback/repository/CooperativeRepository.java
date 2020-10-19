package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CooperativeRepository extends CrudRepository<CooperativeEntity, Long> {
    CooperativeEntity findByCodCoop(String codCoop);
}
