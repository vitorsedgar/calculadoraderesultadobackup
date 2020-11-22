package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.service.CalculateResultService;
import com.br.ages.calculadoraback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/associate")
public class AssociateApi {

    private final CalculateResultService calculateResultService;
    private final UserService userService;

    public AssociateApi(CalculateResultService calculateResultService, UserService userService) {
        this.calculateResultService = calculateResultService;
        this.userService = userService;
    }

    @PostMapping(path = "/calculate-result")
    public ResponseEntity<BigDecimal> getResult(@RequestBody List<AssociateProductDTO> associateProducts, @RequestHeader String authorization) {
        UserEntity user = userService.getUser(authorization);
        BigDecimal result = calculateResultService.calculate(user.getCodCoop(), associateProducts);
        return ResponseEntity.ok(result);
    }

}
