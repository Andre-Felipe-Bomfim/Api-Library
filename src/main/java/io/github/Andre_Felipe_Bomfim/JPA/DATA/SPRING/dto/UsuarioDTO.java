package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto;

import java.util.List;

public record UsuarioDTO(String login, String senha, List<String> roles) {
}
