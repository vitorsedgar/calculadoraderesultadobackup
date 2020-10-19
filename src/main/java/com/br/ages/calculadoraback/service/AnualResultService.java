package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import com.br.ages.calculadoraback.repository.AnualResultRepository;
import org.springframework.stereotype.Service;

@Service
public class AnualResultService {

		private final AnualResultRepository anualResultRepository;

		public AnualResultService(AnualResultRepository anualResultRepository) {
				this.anualResultRepository = anualResultRepository;
		}

		public void save(AnualResultEntity entity) {
				anualResultRepository.findByAnualResultPK_IdCoop_CodCoopAndAnualResultPK_Year(entity.getAnualResultPK().getIdCoop().getCodCoop(), entity.getAnualResultPK().getYear())
						.ifPresent(it -> entity.setAnualResultPK(it.getAnualResultPK()));

				anualResultRepository.save(entity);
		}

		public AnualResultEntity findByAnualResultPK(AnualResultPK anualResultPK) {
				return this.anualResultRepository.findByAnualResultPK(anualResultPK);
		}

}
