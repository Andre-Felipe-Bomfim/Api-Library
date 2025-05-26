package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) //importante para segurança
                .httpBasic(Customizer.withDefaults()) // não é obrigatório
                .formLogin(configurer -> {
                    configurer.loginPage("/login").permitAll();
                }) //adiciona o formulario padrão, não é obrigatório
                .authorizeHttpRequests(authorize -> {
                    authorize.anyRequest().authenticated();})
                .build();
    }

    //para comparar senha digitada de senha em memória ou em um banco de dados
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);                                                                                                          //uma das mais seguras, quando você codifica a senha não é possível voltar atrás, ou seja, não tem descriptrografar
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        //em memória

        UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }
}