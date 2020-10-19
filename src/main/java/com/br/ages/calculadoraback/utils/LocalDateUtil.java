package com.br.ages.calculadoraback.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateUtil {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

}
