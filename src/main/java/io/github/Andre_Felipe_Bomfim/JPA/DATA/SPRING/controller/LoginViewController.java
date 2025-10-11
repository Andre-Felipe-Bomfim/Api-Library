package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//usado para páginas web
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }
}
