package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.RegisterDTO;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.UserRepository;
import com.br.ages.calculadoraback.security.MD5Crypt;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.UserException;
import com.br.ages.calculadoraback.utils.exceptions.UserInternalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private ObjectMapper objectMapper;

    private CooperativeService cooperativeService;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper, CooperativeService cooperativeService) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.cooperativeService = cooperativeService;
    }

    public UserEntity getUser(String token) {
        Map mapClains = parseBodyJWT(token);
        String document = mapClains.get("document").toString();
        return userRepository.findByDocument(document).orElseThrow();
    }

    public Optional<UserEntity> getUserByDocumentAndCodCoop(String document, String codCoop){
        return userRepository.findByDocumentAndCodCoop_codCoop(document, codCoop);
    }

    public UserEntity save(UserEntity entity) {
        userRepository.findByDocument(entity.getDocument()).ifPresent(it -> entity.setId_user(it.getId_user()));
        return userRepository.save(entity);
    }


    public UserEntity registerAssociate(RegisterDTO associate) {
        CooperativeEntity coopEntity = cooperativeService.getCoopByCodCoop(associate.getCodCoop());

        // Validate associate cooperativa
        if (coopEntity == null)
            throw new CooperativeNotFoundException(associate.getCodCoop());

        // Validate if associate does not existis (document + codCoop)
        if (getUserByDocumentAndCodCoop(associate.getDocument(), coopEntity.getCodCoop()).isPresent()) {
            throw new UserException("Este usuário já está cadastrado nesta cooperativa.");
        }

        UserEntity newUser = UserEntity.builder()
            .name(associate.getName())
                .document(associate.getDocument())
                .password(new MD5Crypt().encode(associate.getPassword()))
                .codCoop(coopEntity)
                .role("associate")
                .build();

        UserEntity user = save(newUser);
        if (user == null)
            throw new UserInternalException("Não foi possível cadastrar seu usuário.");
        return user;
    }

    public UserEntity getLoggedUser() {
        String document =  SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return this.userRepository.findByDocument(document).orElseThrow();
    }

    public Map parseBodyJWT(String token) {
        try {
            String json = new String(BaseEncoding.base64().decode(token.split("\\.")[1]), StandardCharsets.UTF_8.name());
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
