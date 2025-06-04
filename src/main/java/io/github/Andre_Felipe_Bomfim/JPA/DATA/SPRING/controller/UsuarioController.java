package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers.UsuarioMapper;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.UsuarioDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/cria")
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioDTO dto){
        var usuario = mapper.toEntity(dto);
        service.salvar(usuario);
    }
}
