package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.UserDTO;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.UserRepository;
import com.br.ages.calculadoraback.security.MD5Crypt;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("unit-test")
public class UserServiceTest {

    @SpyBean
    private UserService userService;

    @MockBean
    private CooperativeService cooperativeService;

    @MockBean
    private UserRepository userRepository;

    private UserDTO associate1;

    private CooperativeEntity cooperativeEntityMock;

    private UserEntity user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        associate1 = UserDTO.builder()
                .codCoop("0101")
                .document("12345678910")
                .name("Associado_01")
                .password("12345")
                .build();
        cooperativeEntityMock = CooperativeEntity.builder().idCoop(1L).codCoop("0101").build();

        user = UserEntity.builder()
                .name(associate1.getName())
                .document(associate1.getDocument())
                .password(new MD5Crypt().encode(associate1.getPassword()))
                .codCoop(cooperativeEntityMock)
                .role("associate")
                .build();
    }

    @Test
    public void mustRegisterAssociate() {
        when(cooperativeService.getCoopByCodCoop(any())).thenReturn(cooperativeEntityMock);

        doReturn(Optional.empty()).when(userService).getUserByDocumentAndCodCoop(any(), any());

        when(userRepository.save(any())).thenReturn(user);

        UserEntity result = userService.registerAssociate(associate1);

        assertEquals(associate1.getCodCoop(), result.getCodCoop().getCodCoop());
        assertEquals(associate1.getDocument(), result.getDocument());
        assertEquals(associate1.getName(), result.getName());
        assertEquals(new MD5Crypt().encode(associate1.getPassword()), result.getPassword());
    }

    @Test
    public void mustReturnCooperativeNotFoundException() {
        when(cooperativeService.getCoopByCodCoop(any())).thenReturn(null);
        assertThrows(CooperativeNotFoundException.class, () -> {
            userService.registerAssociate(associate1);
        });
    }

    @Test
    public void mustReturnUsuarioJaCadastradoException() {
        when(cooperativeService.getCoopByCodCoop(any())).thenReturn(cooperativeEntityMock);
        doReturn(Optional.of(user)).when(userService).getUserByDocumentAndCodCoop(any(), any());
        assertThrows(UserException.class, () -> {
            userService.registerAssociate(associate1);
        });
    }
}
