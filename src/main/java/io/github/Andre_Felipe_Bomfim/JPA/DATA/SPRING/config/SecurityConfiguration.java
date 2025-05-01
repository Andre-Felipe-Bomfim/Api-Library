package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) //importante para segurança
                .formLogin(Customizer.withDefaults()) //adiciona o formulario padrão
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> {authorize.anyRequest().authenticated();})
                .build();
    }
}
