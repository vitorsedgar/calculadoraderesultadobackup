package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.CooperativeDTO;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.repository.CooperativeRepository;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeException;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("unit-test")
public class CooperativeTest {

    @MockBean
    private CooperativeRepository cooperativeRepository;
    @Autowired
    private CooperativeService cooperativeService;

    @Test
    public void mustRegisterCooperative() {
        when(cooperativeRepository.findByCodCoop("0116")).thenReturn(null);
        CooperativeEntity expected = CooperativeEntity.builder().name("teste").build();
        CooperativeDTO cooperativeDTO = CooperativeDTO.builder().name("teste").codCoop("0116").build();
        when(cooperativeRepository.save(any())).thenReturn(expected);
        Cooperative result = cooperativeService.registerCoop(cooperativeDTO);
        assertEquals(expected.getName(), result.getName());
    }

    @Test
    public void mustReturnCooperativeException() {
        CooperativeEntity expected = CooperativeEntity.builder().name("teste").build();
        when(cooperativeRepository.findByCodCoop("0116")).thenReturn(expected);
        CooperativeDTO cooperativeDTO = CooperativeDTO.builder().name("teste").codCoop("0116").build();
        assertThrows(CooperativeException.class, () -> {
            cooperativeService.registerCoop(cooperativeDTO);
        });
    }

    @Test
    public void findAllCooperativesTest(){
        when(cooperativeRepository.findAll()).thenReturn(Arrays.asList(CooperativeEntity.builder().codCoop("0101").name("Pioneira").build()));
        Cooperative coop = Cooperative.builder().codCoop("0101").name("Pioneira").build();
        List<Cooperative> result = cooperativeService.findAll();
        assertEquals(1, result.size());
        assertEquals(result.get(0).getCodCoop(), coop.getCodCoop());
    }

    @Test
    public void findAllCooperativesEmptyTest(){
        when(cooperativeRepository.findAll()).thenReturn(new ArrayList<>());
        List<Cooperative> result = cooperativeService.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void deleteCooperativeTest(){
        when(cooperativeRepository.findByCodCoop("0116")).thenReturn(null);
        assertThrows(CooperativeNotFoundException.class, () -> {
            cooperativeService.deleteCoop("0116");
        });
    }
}
