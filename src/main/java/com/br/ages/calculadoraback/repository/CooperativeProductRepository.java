package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CooperativeProductRepository extends CrudRepository<CooperativeProductEntity, Long> {
		List<CooperativeProductEntity> findByCoopProdPK_IdCoop_CodCoop(String codCoop);

		List<CooperativeProductEntity> findByCoopProdPK_IdCoop_IdCoop(long idCoop);

		Optional<CooperativeProductEntity> findByCoopProdPK(CoopProdPK coopProdPK);

		@Query("select sum(c.weight) from CooperativeProductEntity c where c.coopProdPK.idCoop.codCoop = ?1")
		Optional<Double> getCoopTotalWeight(String codCoop);

		void deleteByCoopProdPK_IdCoop(CooperativeEntity coop);

		void deleteByCoopProdPK(CoopProdPK coopProdPK);
}
