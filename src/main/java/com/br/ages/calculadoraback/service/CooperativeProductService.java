package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CooperativeProductService {

		private final CooperativeProductRepository coopProdRepository;

		public CooperativeProductService(CooperativeProductRepository coopProdRepository) {
				this.coopProdRepository = coopProdRepository;
		}

		public void save(CooperativeProductEntity entity) {
				coopProdRepository.save(entity);
		}

		public List<CooperativeProductEntity> findByIdCoop(long idCoop) {
				return this.coopProdRepository.findByCoopProdPK_IdCoop_IdCoop(idCoop);
		}

		public CooperativeProductEntity findByCoopProdPK(CoopProdPK coopProdPK) {
				return this.coopProdRepository.findByCoopProdPK(coopProdPK);
		}
}
