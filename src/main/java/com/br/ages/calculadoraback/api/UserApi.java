package com.br.ages.calculadoraback.api;

import com.br.ages.calculadoraback.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
public class UserApi {

    @PostMapping(path = "")
    private void login(@RequestBody UserEntity user) {

    }
}
