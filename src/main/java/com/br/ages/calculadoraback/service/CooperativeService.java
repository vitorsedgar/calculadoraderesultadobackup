package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.repository.CooperativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CooperativeService {
		private final CooperativeRepository cooperativeRepository;

		public CooperativeService(CooperativeRepository cooperativeRepository) {
				this.cooperativeRepository = cooperativeRepository;
		}

		public CooperativeEntity saveCoop(Cooperative coop) {
				return cooperativeRepository.save(CooperativeEntity
						.builder()
						.codCoop(coop.getCodCoop())
						.idCoop(coop.getIdCoop())
						.name(coop.getName())
						.build());
		}

		public CooperativeEntity getCoopByCodCoop(String codCoop) {
				return cooperativeRepository.findByCodCoop(codCoop);
		}

		public List<CooperativeEntity> findAll() {
				return (List<CooperativeEntity>) cooperativeRepository.findAll();
		}
}
