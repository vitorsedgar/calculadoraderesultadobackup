package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.CooperativeDTO;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.repository.AnnualResultRepository;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.repository.CooperativeRepository;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeException;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CooperativeService {
    private final CooperativeRepository cooperativeRepository;
    private CooperativeProductRepository cooperativeProductRepository;
    private AnnualResultRepository annualResultRepository;

    public CooperativeService(CooperativeRepository cooperativeRepository, CooperativeProductRepository cooperativeProductRepository, AnnualResultRepository annualResultRepository) {
        this.cooperativeRepository = cooperativeRepository;
        this.cooperativeProductRepository = cooperativeProductRepository;
        this.annualResultRepository = annualResultRepository;
    }

    public CooperativeEntity saveCoop(Cooperative coop) {
        return cooperativeRepository.save(CooperativeEntity
            .builder()
                .codCoop(coop.getCodCoop())
                .idCoop(coop.getIdCoop())
                .name(coop.getName())
                .build());
    }

    public Cooperative registerCoop(CooperativeDTO coop){
        if(getCoopByCodCoop(coop.getCodCoop())!=null){
            throw new CooperativeException("Cooperativa já cadastrada.");
        }
        return mapper(cooperativeRepository.save(CooperativeEntity
                .builder()
                .codCoop(coop.getCodCoop())
                .name(coop.getName())
                .build()));
    }

    public CooperativeEntity getCoopByCodCoop(String codCoop) {
        return cooperativeRepository.findByCodCoop(codCoop);
    }

    public Cooperative mapper(CooperativeEntity cooperativeEntity){
        return Cooperative.builder().codCoop(cooperativeEntity.getCodCoop()).idCoop(cooperativeEntity.getIdCoop()).name(cooperativeEntity.getName()).build();
    }

    @Transactional
    public void deleteCoop(String codCoop) {
        CooperativeEntity coopByCodCoop = getCoopByCodCoop(codCoop);
        if (coopByCodCoop == null) {
            throw new CooperativeNotFoundException("Cooperativa não existente.");
        }
        cooperativeProductRepository.deleteByCoopProdPK_IdCoop(coopByCodCoop);
        annualResultRepository.deleteByAnnualResultPK_IdCoop(coopByCodCoop);
        cooperativeRepository.delete(coopByCodCoop);
    }

    public List<Cooperative> findAll() {
        List<Cooperative> result = new ArrayList<>();
        cooperativeRepository.findAll().forEach(it -> {
            result.add(mapper(it));
        });
        return result;
    }
}
