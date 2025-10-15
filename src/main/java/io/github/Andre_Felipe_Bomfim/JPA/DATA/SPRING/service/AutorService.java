package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.OperacaoNaoPermitidaException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Usuario;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.AutorRepository;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.LivroRepository;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.security.SecurityService;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidator autorValidator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    public AutorService(AutorRepository autorRepository, AutorValidator autorValidator, LivroRepository livroRepository, SecurityService securityService) {
        this.autorRepository = autorRepository;
        this.autorValidator = autorValidator;
        this.livroRepository = livroRepository;
        this.securityService = securityService;
    }

    public Autor salvar(Autor autor){
        autorValidator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return autorRepository.save(autor);
    }

    public void atualizar(Autor autor){
        if (autor.getUuid() == null){
            throw new IllegalArgumentException("Autor não encontrado, não iremos atualizar");
        }
        autorValidator.validar(autor);
        autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Não é permitido excluir um autor com livros cadastrados");
        }
        autorRepository.delete(autor);
    }
    public List<Autor> pesquisar(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return autorRepository.findByNome(nome);
        }

        if (nacionalidade != null){
            return autorRepository.findByNacionalidade(nacionalidade);
        }

        return autorRepository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        //para pesquisas dinamicas com Query
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                //.withIgnorePaths("uuid") exemplo que da para ignorar alguns parametros passando o nome exato.
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, matcher);
        return autorRepository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
