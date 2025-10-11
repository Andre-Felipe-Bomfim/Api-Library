package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByLogin(String login);
}
