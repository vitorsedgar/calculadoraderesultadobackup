package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.CooperativeProductDTO;
import com.br.ages.calculadoraback.entity.AnnualResultPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.utils.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalculateResultService {

    @Autowired
    private CooperativeProductService cooperativeProductService;

    @Autowired
    private AnnualResultService annualResultService;

    @Autowired
    private LocalDateUtil localDateUtil;

    /**
     * Forma de cálculo:
     * Resultado a ser distribuído / Quantidade de produtos da cooperativa = Valor destinado a cada produto (avaliar proporcionalidade por %)
     * ((Valor informado pelo a ssociado / Valor total de cada produto) * 100) * (Resultado alocado a cada produto) = Valor a ser distribuído ao associado
     */
    public BigDecimal calculate(CooperativeEntity cooperative, List<AssociateProductDTO> products) {
        List<CooperativeProductDTO> cooperativeProducts = this.cooperativeProductService.getCooperativeProducts(products, cooperative);

        BigDecimal resultToBeDistributed = getAnnualResult(cooperative);
        BigDecimal clientResult = new BigDecimal(0);

        for (CooperativeProductDTO product : cooperativeProducts) {
            BigDecimal toBeDistributed = resultToBeDistributed.multiply(BigDecimal.valueOf(product.getWeight())).divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING);

            BigDecimal clientPercentage = product.getAssociateProduct().getValue().divide(product.getValue(), 2, RoundingMode.CEILING);

            BigDecimal valueToAdd = toBeDistributed.multiply(clientPercentage);
            clientResult = clientResult.add(valueToAdd.setScale(2, RoundingMode.UNNECESSARY));
        }
        return clientResult;
    }

    private BigDecimal getAnnualResult(CooperativeEntity cooperative) {
        return annualResultService.findByAnnualResultPK(
                AnnualResultPK.builder()
                        .idCoop(cooperative)
                        .year(getYear())
                        .build())
                .getResult();
    }

    private int getYear() {
        return localDateUtil.now().getYear();
    }

}
