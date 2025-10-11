package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers.LivroMapper;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.CadastroLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ResultadoLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.GeneroLivro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service.LivroService;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Var;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable
                                        String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    service.deletar(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoLivroDTO>> pesquisar(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado = service.pesquisa(
                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

        Page<ResultadoLivroDTO> resultado = paginaResultado.map(mapper::toDto);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atulizar(@PathVariable("id") String id, @RequestBody @Valid CadastroLivroDTO dto){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    Livro entidadeAuxiliar = mapper.toEntity(dto);

                    livro.setDataPublicacao(entidadeAuxiliar.getDataPublicacao());
                    livro.setIsbn(entidadeAuxiliar.getIsbn());
                    livro.setPreco(entidadeAuxiliar.getPreco());
                    livro.setGenero(entidadeAuxiliar.getGenero());
                    livro.setTitulo(entidadeAuxiliar.getTitulo());
                    livro.setAutor(entidadeAuxiliar.getAutor());

                    service.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
