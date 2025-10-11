package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Usuario;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha));
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return repository.findByLogin(login);
    }
}
