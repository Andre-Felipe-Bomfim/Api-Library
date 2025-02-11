package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.service;

import aj.org.objectweb.asm.commons.TryCatchBlockSorter;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.CadastroLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ErroResposta;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.RegistroDuplicadoException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }
}
