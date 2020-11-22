package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.dto.LoginDTO;
import com.br.ages.calculadoraback.dto.UserDTO;
import com.br.ages.calculadoraback.dto.UserForgotPwdDTO;
import com.br.ages.calculadoraback.entity.UserEntity;
import com.br.ages.calculadoraback.security.TokenAuthenticationService;
import com.br.ages.calculadoraback.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class UserApi {

    private UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "")
    private void login(@RequestBody LoginDTO user) { }

    @PostMapping(path = "/register-cooperative")
    private void registerCooperative(@RequestBody UserDTO user) {
        userService.registerCooperative(user);
    }

    @PostMapping(path = "/register-associate")
    public void registerAssociate(@RequestBody UserDTO userDTO, HttpServletResponse response) {
        UserEntity user = userService.registerAssociate(userDTO);
        response.setStatus(HttpServletResponse.SC_OK);
        TokenAuthenticationService.addAuthentication(response, user);
    }

    @PostMapping(path = "/forgot-password")
    public void resetPassword(@RequestBody UserForgotPwdDTO userDTO) {
        userService.resetUserPassword(userDTO);
    }

}
