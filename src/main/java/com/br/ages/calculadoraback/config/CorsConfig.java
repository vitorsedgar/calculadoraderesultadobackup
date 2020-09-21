package com.br.ages.calculadoraback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
		public CorsConfig() {
		}

		public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT").allowedHeaders("Content-Type", "Access-Control-Allow-Headers", "Authorization", "perfis", "X-Requested-With");
		}
}
