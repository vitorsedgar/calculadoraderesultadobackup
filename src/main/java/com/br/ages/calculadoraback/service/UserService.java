package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.UserDTO;
import com.br.ages.calculadoraback.dto.UserForgotPwdDTO;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.repository.UserRepository;
import com.br.ages.calculadoraback.security.MD5Crypt;
import com.br.ages.calculadoraback.utils.exceptions.CooperativeNotFoundException;
import com.br.ages.calculadoraback.utils.exceptions.UserException;
import com.br.ages.calculadoraback.utils.exceptions.UserInternalException;
import com.br.ages.calculadoraback.utils.exceptions.UserNotFoundException;
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
    private final ObjectMapper objectMapper;

    private final CooperativeService cooperativeService;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper, CooperativeService cooperativeService) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.cooperativeService = cooperativeService;
    }

    public UserEntity save(UserEntity entity) {
        return userRepository.save(entity);
    }

    public UserEntity getUser(String token) {
        Map mapClains = parseBodyJWT(token);
        String document = mapClains.get("document").toString();
        return userRepository.findByDocument(document).orElseThrow();
    }

    public Optional<UserEntity> getUserByDocumentAndCodCoop(String document, String codCoop) {
        return userRepository.findByDocumentAndCodCoop_codCoop(document, codCoop);
    }

    public UserEntity getUserByDocument(String document) {
        return userRepository.findByDocument(document).orElseThrow(() -> new UserNotFoundException());
    }

    public UserEntity registerAssociate(UserDTO associate) {
        CooperativeEntity coopEntity = cooperativeService.getCoopByCodCoop(associate.getCodCoop());

        // Validate associate cooperativa
        if (coopEntity == null)
            throw new CooperativeNotFoundException(associate.getCodCoop());

        // Validate if associate does not existis (document + codCoop)
        if (getUserByDocumentAndCodCoop(associate.getDocument(), coopEntity.getCodCoop()).isPresent())
            throw new UserException("Este usuário já está cadastrado nesta cooperativa.");

        validatePwd(associate.getPassword());

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

    public void registerCooperative(UserDTO userDTO){
        CooperativeEntity coopEntity = cooperativeService.getCoopByCodCoop(userDTO.getCodCoop());
        // Validate cooperative cooperativa
        if (coopEntity == null)
            throw new CooperativeNotFoundException(userDTO.getCodCoop());

        // Validate if cooperative does not existis (document + codCoop)
        if (getUserByDocumentAndCodCoop(userDTO.getDocument(), coopEntity.getCodCoop()).isPresent())
            throw new UserException("Este usuário já está cadastrado nesta cooperativa.");

        validatePwd(userDTO.getPassword());

        userRepository.save(UserEntity.builder()
                .name(userDTO.getName())
                .document(userDTO.getDocument())
                .password(new MD5Crypt().encode(userDTO.getPassword()))
                .codCoop(coopEntity)
                .role("coop")
                .build());
    }

    public void resetUserPassword(UserForgotPwdDTO userDTO) {
        UserEntity user = getUserByDocument(userDTO.getDocument());
        validatePwd(userDTO.getNewPassword());
        if (!userDTO.getNewPassword().equals(userDTO.getConfirmPassword())) {
            throw new UserException("Nova senha e senha de confirmação não coincidem");
        }
        user.setPassword(new MD5Crypt().encode(userDTO.getNewPassword()));
        userRepository.save(user);
    }

    public UserEntity getLoggedUser() {
        String document = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return this.userRepository.findByDocument(document).orElseThrow();
    }

    public void validatePwd(String pwd) {
        if (pwd == null || pwd.length() < 5) {
            throw new UserException("Senha inválida! A senha deve conter no mínimo 5 caracteres.");
        }
    }

    public Map parseBodyJWT(String token) {
//        log.info(" Start Find products by Cooperative");
        try {
            String json = new String(BaseEncoding.base64().decode(token.split("\\.")[1]), StandardCharsets.UTF_8.name());
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
