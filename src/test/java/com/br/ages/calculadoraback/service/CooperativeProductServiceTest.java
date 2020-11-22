package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.CooperativeProductDTO;
import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.CooperativeProductEntity;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.utils.exceptions.ProductException;
import com.br.ages.calculadoraback.utils.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CooperativeProductServiceTest {

    @SpyBean
    private CooperativeProductService cooperativeProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mustReturnCorrectProduct() {
        CooperativeProductEntity cooperativeProductEntityMock = CooperativeProductEntity.builder().coopProdPK(
                CoopProdPK.builder().idCoop(CooperativeEntity.builder().idCoop(1L).build())
                        .idProd(ProductEntity.builder().idProd(1L).build()).build()
        ).value(BigDecimal.TEN).build();
        when(cooperativeProductService.findByCoopProdPK(any())).thenReturn(cooperativeProductEntityMock);

        AssociateProductDTO associateProductDTO = AssociateProductDTO.builder().id(1L).value(BigDecimal.ONE).build();
        CooperativeEntity cooperativeEntity = CooperativeEntity.builder().idCoop(1L).build();
        CooperativeProductDTO cooperativeProduct = cooperativeProductService.getProduct(associateProductDTO, cooperativeEntity);
        assertThat(cooperativeProduct.getCoopProdPK().getIdCoop().getIdCoop()).isEqualTo(1L);
        assertThat(cooperativeProduct.getCoopProdPK().getIdProd().getIdProd()).isEqualTo(1L);
        assertThat(cooperativeProduct.getValue()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    public void mustReturnProductNotFoundException() {
        when(cooperativeProductService.findByCoopProdPK(any())).thenReturn(null);
        AssociateProductDTO associateProductDTO = AssociateProductDTO.builder().id(1L).value(BigDecimal.ONE).build();
        CooperativeEntity cooperativeEntity = CooperativeEntity.builder().idCoop(1L).build();
        assertThrows(ProductNotFoundException.class, () -> {
            cooperativeProductService.getProduct(associateProductDTO, cooperativeEntity);
        });
    }

    @Test
    public void mustReturnProductException() {
        CooperativeProductEntity cooperativeProductEntityMock = CooperativeProductEntity.builder().coopProdPK(
                CoopProdPK.builder().idCoop(CooperativeEntity.builder().idCoop(1L).build())
                        .idProd(ProductEntity.builder().idProd(1L).build()).build()
        ).value(BigDecimal.TEN).build();
        when(cooperativeProductService.findByCoopProdPK(any())).thenReturn(cooperativeProductEntityMock);

        AssociateProductDTO associateProductDTO = AssociateProductDTO.builder().id(1L).value(BigDecimal.valueOf(20)).build();
        CooperativeEntity cooperativeEntity = CooperativeEntity.builder().idCoop(1L).build();
        assertThrows(ProductException.class, () -> {
            cooperativeProductService.getProduct(associateProductDTO, cooperativeEntity);
        });
    }
}
