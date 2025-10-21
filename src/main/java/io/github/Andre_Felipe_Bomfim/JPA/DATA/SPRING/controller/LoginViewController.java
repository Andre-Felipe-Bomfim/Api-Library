package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller//usado para páginas web
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody//porque o controller espera uma página nao uma string colocando o responseBody ele joga o return para o body da aplicação.
    public String home(Authentication authentication){
        return "Hello" + authentication.getName();
    }
}
