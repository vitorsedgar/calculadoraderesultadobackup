package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AnualResultTrasferenciaDTO;
import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import com.br.ages.calculadoraback.repository.AnualResultRepository;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnualResultService {

		private final AnualResultRepository anualResultRepository;

		public AnualResultService(AnualResultRepository anualResultRepository) {
			this.anualResultRepository = anualResultRepository;
		}

		public void save(AnualResultEntity entity) {
				anualResultRepository.save(entity);
		}

		public AnualResultEntity findByAnualResultPK(AnualResultPK anualResultPK) {
			return anualResultRepository.findByAnualResultPK(anualResultPK);
		}

		public List<AnualResultTrasferenciaDTO> findByCodCoop(String codCoop) {
			return anualResultRepository.findByAnualResultPK_idCoop_codCoop(codCoop, Sort.by(Sort.Direction.DESC, "AnualResultPK_year"))
					.stream()
					.map(it -> AnualResultTrasferenciaDTO.builder()
							.result(it.getResult())
							.year(it.getAnualResultPK().getYear())
							.build())
					.collect(Collectors.toList());
		}

		public Long delete(AnualResultPK anualResultPK) {
			return anualResultRepository.deleteByAnualResultPK(anualResultPK);
		}
}
