package com.br.ages.calculadoraback.security;

import com.br.ages.calculadoraback.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;


public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = 860_000_000;
    static final String SECRET = "Calculadora";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse response, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority().split("/")[0];
        String allocation = authentication.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority().split("/")[1];
        String JWT = Jwts.builder()
            .claim("document", authentication.getName())
            .claim("role", authorities)
            .claim("coop", allocation)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static HttpServletResponse addAuthentication(HttpServletResponse response, UserEntity userEntity) {
        String JWT = Jwts.builder()
            .claim("document", userEntity.getDocument())
            .claim("role", userEntity.getRole())
            .claim("coop", userEntity.getCodCoop().getCodCoop())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
        return response;
    }

    static Authentication getAuthentication(HttpServletRequest http) {
        String token = http.getHeader(HEADER_STRING);
        try {
            if (token != null) {
                String user = (String) Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .get("document");

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                }
            }
        } catch (MalformedJwtException e) {

        }

        return null;
    }

}
