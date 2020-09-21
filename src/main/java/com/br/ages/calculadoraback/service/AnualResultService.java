package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import com.br.ages.calculadoraback.repository.AnualResultRepository;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnualResultService {

		private final AnualResultRepository anualResultRepository;

		public AnualResultService(AnualResultRepository anualResultRepository) {
				this.anualResultRepository = anualResultRepository;
		}

		public void save(AnualResultEntity entity) {
				anualResultRepository.save(entity);
		}

		public AnualResultEntity findByAnualResultPK(AnualResultPK anualResultPK) {
				return this.anualResultRepository.findByAnualResultPK(anualResultPK);
		}
}
