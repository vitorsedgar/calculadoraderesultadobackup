package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AnnualResultDTO;
import com.br.ages.calculadoraback.entity.AnnualResultEntity;
import com.br.ages.calculadoraback.entity.AnnualResultPK;
import com.br.ages.calculadoraback.entity.CooperativeEntity;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.service.AnnualResultService;
import com.br.ages.calculadoraback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/annual-result")
public class AnnualResultApi {

    private final AnnualResultService annualResultService;
    private final UserService userService;

    public AnnualResultApi(AnnualResultService annualResultService, UserService userService) {
        this.annualResultService = annualResultService;
        this.userService = userService;
    }

    @PostMapping(path = "")
    public ResponseEntity addAnnualResult(@RequestHeader(name = "authorization") String authorization, @RequestBody AnnualResultDTO annualResultDTO) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        annualResultService.save(AnnualResultEntity.builder().annualResultPK(AnnualResultPK.builder().idCoop(cooperativeEntity).year(annualResultDTO.getYear()).build()).result(annualResultDTO.getResult()).build());
        return ResponseEntity.ok(Void.class);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<AnnualResultDTO>> findByAnnualResultPK(@RequestHeader(name = "authorization") String authorization) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        List<AnnualResultDTO> annualResultList = annualResultService.findByCodCoop(cooperativeEntity.getCodCoop());
        return ResponseEntity.ok(annualResultList);
    }

    @DeleteMapping(path = "/{year}")
    public ResponseEntity deleteAnnualResult(@RequestHeader(name = "authorization") String authorization, @PathVariable(name = "year") int year) {
        UserEntity user = userService.getUser(authorization);
        CooperativeEntity cooperativeEntity = user.getCodCoop();
        annualResultService.delete(AnnualResultPK.builder().idCoop(cooperativeEntity).year(year).build());
        return ResponseEntity.ok(Void.class);
    }

}
