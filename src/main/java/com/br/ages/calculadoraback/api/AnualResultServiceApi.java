package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AnualResultTrasferenciaDTO;
import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.entity.*;
import com.br.ages.calculadoraback.service.UserService;
import org.springframework.http.ResponseEntity;
import com.br.ages.calculadoraback.service.AnualResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/anualResult")
public class AnualResultServiceApi {

    private final AnualResultService anualResultService;
    private final UserService userService;

    public AnualResultServiceApi(AnualResultService anualResultService, UserService userService){
        this.anualResultService = anualResultService;
        this.userService = userService;
    }
    @PostMapping(path = "/addAnualResult")
    public ResponseEntity addAnualResult(@RequestHeader(name = "authorization") String authorization, @RequestBody AnualResultTrasferenciaDTO anualResultDTO) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        anualResultService.save(AnualResultEntity.builder().anualResultPK(AnualResultPK.builder().idCoop(cooperativeEntity).year(anualResultDTO.getYear()).build()).result(anualResultDTO.getResult()).build());
        return ResponseEntity.ok(Void.class);
    }

    @GetMapping(path = "/findAnualResult")
    public ResponseEntity<List<AnualResultTrasferenciaDTO>> findByAnualResultPK(@RequestHeader(name = "authorization") String authorization) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        List<AnualResultTrasferenciaDTO> anualResultList = anualResultService.findByCodCoop(cooperativeEntity.getCodCoop());
        return ResponseEntity.ok(anualResultList);
    }

    @DeleteMapping(path = "/{year}")
    public ResponseEntity deleteAnualResult(@RequestHeader(name = "authorization") String authorization, @PathVariable(name = "year") int year){
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        anualResultService.delete(AnualResultPK.builder().idCoop(cooperativeEntity).year(year).build());
        return ResponseEntity.ok(Void.class);
    }

}
