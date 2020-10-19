package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.AssociateProductDTO;
import com.br.ages.calculadoraback.dto.RegisterDTO;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.security.TokenAuthenticationService;
import com.br.ages.calculadoraback.service.CalculateResultService;
import com.br.ages.calculadoraback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/associate")
public class AssociateApi {

	private final CalculateResultService calculateResultService;
	private final UserService userService;

	public AssociateApi(CalculateResultService calculateResultService, UserService userService) {
		this.calculateResultService = calculateResultService;
		this.userService = userService;
	}

	@PostMapping(path = "/register")
	public void register(@RequestBody RegisterDTO registerDTO, HttpServletResponse response) {
		UserEntity user = userService.registerAssociate(registerDTO);
		response.setStatus(HttpServletResponse.SC_OK);
		TokenAuthenticationService.addAuthentication(response, user);
	}

	@PostMapping(path = "/calculate-result")
	public ResponseEntity<BigDecimal> getResult(@RequestBody List<AssociateProductDTO> associateProducts) {
		BigDecimal result = calculateResultService.calculate(associateProducts);
		return ResponseEntity.ok(result);
	}

}
