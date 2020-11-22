package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.AnnualResultEntity;
import com.br.ages.calculadoraback.entity.AnnualResultPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnnualResultRepository extends CrudRepository<AnnualResultEntity, Long> {
    Optional<AnnualResultEntity> findByAnnualResultPK(AnnualResultPK annualResultPK);

    List<AnnualResultEntity> findByAnnualResultPK_idCoop_codCoop(String codCoop, Sort sort);

    Long deleteByAnnualResultPK(AnnualResultPK annualResultPK);

    void deleteByAnnualResultPK_IdCoop(CooperativeEntity coop);
}
