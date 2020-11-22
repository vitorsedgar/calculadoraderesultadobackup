package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AnnualResultDTO;
import com.br.ages.calculadoraback.entity.AnnualResultEntity;
import com.br.ages.calculadoraback.entity.AnnualResultPK;
import com.br.ages.calculadoraback.repository.AnnualResultRepository;
import com.br.ages.calculadoraback.utils.exceptions.AnnualResultException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnnualResultService {

    private final AnnualResultRepository annualResultRepository;

    public AnnualResultService(AnnualResultRepository annualResultRepository) {
        this.annualResultRepository = annualResultRepository;
    }

    public void save(AnnualResultEntity entity) {
        annualResultRepository.save(entity);
    }

    public AnnualResultEntity findByAnnualResultPK(AnnualResultPK annualResultPK) {
        return annualResultRepository.findByAnnualResultPK(annualResultPK).orElseThrow(() -> new AnnualResultException("Nenhum resultado anual encontrado."));
    }

    public List<AnnualResultDTO> findByCodCoop(String codCoop) {
        return annualResultRepository.findByAnnualResultPK_idCoop_codCoop(codCoop, Sort.by(Sort.Direction.DESC, "AnnualResultPK_year"))
                .stream()
                .map(it -> AnnualResultDTO.builder()
                        .result(it.getResult())
                        .year(it.getAnnualResultPK().getYear())
                        .build())
                .collect(Collectors.toList());
    }

    public Long delete(AnnualResultPK annualResultPK) {
        return annualResultRepository.deleteByAnnualResultPK(annualResultPK);
    }
}
