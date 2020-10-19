package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AnualResultRepository extends CrudRepository<AnualResultEntity, Long> {
		AnualResultEntity findByAnualResultPK(AnualResultPK anualResultPK);

		Optional<AnualResultEntity> findByAnualResultPK_IdCoop_CodCoopAndAnualResultPK_Year(String codCoop, Long year);
}
