package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.CooperativeProductDTO;
import com.br.ages.calculadoraback.entity.AnualResultEntity;
import com.br.ages.calculadoraback.entity.AnualResultPK;
import com.br.ages.calculadoraback.entity.CoopProdPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.utils.LocalDateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("unit-test")
public class CalculateResultServiceTest {

    @SpyBean
    private CalculateResultService calculateResultService;

    @MockBean
    private CooperativeProductService cooperativeProductService;

    @MockBean
    private AnualResultService anualResultService;

    @MockBean
    private LocalDateUtil localDateUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(localDateUtil.now()).thenReturn(LocalDateTime.of(1997, 10, 23, 0, 0));
    }

    //TODO testar melhor com virgulas
    @Test
    public void mustReturnCorrectCalculation() {
        List<AssociateProductDTO> products = Arrays.asList(
                AssociateProductDTO.builder().id(1L).value(BigDecimal.valueOf(100)).build(),
                AssociateProductDTO.builder().id(2L).value(BigDecimal.valueOf(200)).build()
        );

        CooperativeProductDTO cooperativeProductEntityMock1 = CooperativeProductDTO.builder()
                .coopProdPK(CoopProdPK.builder()
                        .idCoop(CooperativeEntity.builder()
                                .idCoop(1L)
                                .build())
                        .idProd(ProductEntity.builder()
                                .idProd(1L)
                                .build())
                        .build())
                .value(BigDecimal.valueOf(1000))
                .weight(30.0)
                .associateProduct(products.get(0))
                .build();

        CooperativeProductDTO cooperativeProductEntityMock2 = CooperativeProductDTO.builder()
                .coopProdPK(CoopProdPK.builder()
                        .idCoop(CooperativeEntity.builder()
                                .idCoop(1L)
                                .build())
                        .idProd(ProductEntity.builder()
                                .idProd(2L)
                                .build())
                        .build())
                .value(BigDecimal.valueOf(2000))
                .weight(70.0)
                .associateProduct(products.get(1))
                .build();

        List<CooperativeProductDTO> cooperativeProductEntitiesMock = Arrays.asList(
                cooperativeProductEntityMock1,
                cooperativeProductEntityMock2
        );
//        doReturn(cooperativeProductEntitiesMock).when(calculateResultService).toCooperativeProductEntities(any(), any());
        when(cooperativeProductService.getCooperativeProducts(any(), any())).thenReturn(cooperativeProductEntitiesMock);

        AnualResultEntity anualResultEntityMock = AnualResultEntity.builder()
                .anualResultPK(AnualResultPK.builder()
                        .idCoop(CooperativeEntity.builder().idCoop(1L).build())
                        .year(1997L)
                        .build())
                .result(BigDecimal.valueOf(10000))
                .build();
        when(anualResultService.findByAnualResultPK(any())).thenReturn(anualResultEntityMock);

        BigDecimal result = calculateResultService.calculate(products);

        assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.UNNECESSARY), result);
    }
}
