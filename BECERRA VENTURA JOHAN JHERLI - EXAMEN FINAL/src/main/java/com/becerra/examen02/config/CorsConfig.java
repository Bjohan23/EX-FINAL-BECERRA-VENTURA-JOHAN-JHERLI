package com.becerra.examen02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir cualquier origen
        config.addAllowedOrigin("*");
        // Permitir cualquier header
        config.addAllowedHeader("*");
        // Permitir cualquier método (GET, POST, etc.)
        config.addAllowedMethod("*");
        // Permitir cookies y credenciales
        config.setAllowCredentials(false);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}