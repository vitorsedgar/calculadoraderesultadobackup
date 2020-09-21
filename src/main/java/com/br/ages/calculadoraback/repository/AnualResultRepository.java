package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnualResultRepository extends CrudRepository<AnualResultEntity, Long> {
		AnualResultEntity findByAnualResultPK(AnualResultPK anualResultPK);
}
