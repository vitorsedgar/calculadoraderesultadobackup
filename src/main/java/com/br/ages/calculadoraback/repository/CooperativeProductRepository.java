package com.br.ages.calculadoraback.repository;

import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CooperativeProductRepository extends CrudRepository<CooperativeProductEntity, Long> {
		List<CooperativeProductEntity> findByCoopProdPK_IdCoop_CodCoop(String codCoop);

		List<CooperativeProductEntity> findByCoopProdPK_IdCoop_IdCoop(long idCoop);

		CooperativeProductEntity findByCoopProdPK(CoopProdPK coopProdPK);

		Optional<CooperativeProductEntity> findByCoopProdPK_IdCoop_CodCoopAndCoopProdPK_IdProd_IdProd(String codCoop, Long idProd);
}
