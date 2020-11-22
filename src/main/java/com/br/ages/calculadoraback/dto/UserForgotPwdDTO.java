package com.br.ages.calculadoraback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForgotPwdDTO {
    private String document;
    private String newPassword;
    private String confirmPassword;
}
