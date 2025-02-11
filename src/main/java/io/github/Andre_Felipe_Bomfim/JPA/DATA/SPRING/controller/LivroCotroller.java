package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers.LivroMapper;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.CadastroLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ResultadoLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
public class LivroCotroller implements GenericController {
    private final LivroService service;
    private final LivroMapper mapper;

    public LivroCotroller(LivroService service, LivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        //mapear dto para entidade
        Livro livro = mapper.toEntity(dto);
        //enviar a entidade para o service validar e slavar na base
        service.salvar(livro);
        //criar url para acesso dos dados do livro
        var url = gerarHeaderLocation(livro.getId());
        //retornar codigo created com header location
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoLivroDTO> obterDetalhes(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDto(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
