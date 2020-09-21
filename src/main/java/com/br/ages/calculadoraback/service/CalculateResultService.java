package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.ClientProduct;
import com.br.ages.calculadoraback.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.stream.Collectors;

@Service
public class CalculateResultService {

    @Autowired
    private CooperativeProductService cooperativeProductService;

    @Autowired
    private AnualResultService anualResultService;

    /**
     * Forma de cálculo:
     * Resultado a ser distribuído / Quantidade de produtos da cooperativa = Valor destinado a cada produto (avaliar proporcionalidade por %)
     * ((Valor informado pelo a ssociado / Valor total de cada produto) * 100) * (Resultado alocado a cada produto) = Valor a ser distribuído ao associado
     */
    public BigDecimal calculate(List<ClientProduct> products) {
        CooperativeEntity cooperativeEntity = CooperativeEntity.builder().idCoop(getIdCoopLoggedUser()).build();
        List<CooperativeProductEntity> allCoopProducts = cooperativeProductService.findByIdCoop(cooperativeEntity.getIdCoop());
        List<CooperativeProductEntity> cooperativeProductEntities = products.stream().map(it -> this.getProduct(it, cooperativeEntity)).collect(Collectors.toList());

        BigDecimal resultToBeDistribuited = getAnualResult();

        // Se tiver peso quebrado 0,5 vai dar erro
        // Confirmar se peso é só inteiro
        // Validar arredondamento
        BigDecimal percentEachProductConsideringWeight = resultToBeDistribuited.divide(
                BigDecimal.valueOf(allCoopProducts.stream().map(CooperativeProductEntity::getWeight).reduce(0.0, Double::sum)), 10, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100))
                .divide(resultToBeDistribuited, 10, RoundingMode.CEILING);

        return products.stream().map(it -> {
            CooperativeProductEntity productCoop = cooperativeProductEntities.stream().filter(i -> i.getCoopProdPK().getIdProd().getIdProd().equals(it.getId())).findFirst().get();
            return it.getValue().divide(productCoop.getValue(), 10, RoundingMode.CEILING)
                    .multiply(BigDecimal.valueOf(100))
                    .multiply(percentEachProductConsideringWeight.multiply(BigDecimal.valueOf(productCoop.getWeight())))
                    .divide(BigDecimal.valueOf(100), 10, RoundingMode.CEILING)
                    .multiply(resultToBeDistribuited)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN)
            ;
        }).reduce(BigDecimal.valueOf(0.0), BigDecimal::add);
    }

    private BigDecimal getAnualResult() {
        return anualResultService.findByAnualResultPK(
                AnualResultPK.builder()
                        .idCoop(CooperativeEntity.builder().idCoop(getIdCoopLoggedUser()).build())
                        .year(getYear())
                        .build())
                .getResult();
    }

    // TODO: Buscar ano atual
    private Long getYear() {
        return 2020L;
    }

    // TODO: Buscar idCoop na autenticacao
    private Long getIdCoopLoggedUser() {
        return 1L;
    }

    public CooperativeProductEntity getProduct(ClientProduct product, CooperativeEntity cooperative) {
        // TODO: fazer verificação: E se o prodId não existir?
        return this.cooperativeProductService.findByCoopProdPK(
                CoopProdPK.builder()
                        .idCoop(cooperative)
                        .idProd(ProductEntity.builder().idProd(product.getId()).build())
                        .build()
        );
    }

}
