package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.CadastroLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ErroResposta;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.RegistroDuplicadoException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.GeneroLivro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.LivroRepository;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.specs.LivroSpecs;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.validator.LivroValidator;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.specs.LivroSpecs.*;

@Service
public class LivroService {
    private final LivroRepository repository;
    private final LivroValidator validator;

    public LivroService(LivroRepository repository, LivroValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar (Livro livro){
        repository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina){
        //select * from livro where isbn = : isbn and nomeAutor = etc...

        //where isbn =isbn isso que queremos
        //Specification<Livro> isbnEqual = (root, query, cb) -> cb.equal(root.get("isbn"), isbn); //root dados que eu quero da pesquisa, query é o objeto, cb CreteriaBuilder

        //      Specification<Livro> specs = Specification.where(LivroSpecs.isbnEqual(isbn)
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero))
//        );
        //select * from livro where 0 = 0 " igual a conjunction"
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn!= null){
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if(anoPublicacao != null){
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null){
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Livro não encontrado, não iremos atualizar");
        }

        validator.validar(livro);
        repository.save(livro);
    }
}
