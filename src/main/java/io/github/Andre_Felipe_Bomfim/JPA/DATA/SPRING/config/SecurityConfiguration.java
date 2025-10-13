package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.config;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.security.CustomUserDetailService;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service.UsuarioService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)//habilita a utilização de especificações nos controllers para oq cada usuario pode entrar dentro de uma aplicação e fazer ex: um fncionario tem um acesso restrito já o gerente tem muito mais coisas liberadas
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable) //importante para segurança
                .httpBasic(Customizer.withDefaults()) // não é obrigatório
                .formLogin(configurer -> {
                    configurer.loginPage("/login");
                }) //adiciona o formulario padrão, não é obrigatório
                .authorizeHttpRequests(authorize -> {
                    //roles
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
                    //authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    //authorize.requestMatchers(HttpMethod.DELETE,"/autores").hasAuthority("CADASTRAR-AUTOR");
                    //authorize.requestMatchers(HttpMethod.DELETE,"/autores").hasRole("ADMIN"); exemplo de method para ser usado somente por um role, o mais comun é usar authority para isso, uma role pode ter várias authoritys
                    //authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
                    authorize.anyRequest().authenticated();})//deixar o anyRequest por último, pois todas aas demais abaixo dela vão ser ignoradas.
                .build();
    }

    //para comparar senha digitada de senha em memória ou em um banco de dados
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);                                                                                                          //uma das mais seguras, quando você codifica a senha não é possível voltar atrás, ou seja, não tem descriptrografar
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){
        //em memória
        /*UserDetails user1 = User.builder()
                .username("usuario")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        UserDetails user2 = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);*/

        return new CustomUserDetailService(usuarioService);
    }
}