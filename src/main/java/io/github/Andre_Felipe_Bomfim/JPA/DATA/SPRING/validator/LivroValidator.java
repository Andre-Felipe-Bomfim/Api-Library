package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.validator;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.AnoAcimaDoisMilEVinteException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.RegistroDuplicadoException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_PRECO_EXIGENCIA = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if(existeLivroComIsbn(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado no banco de dados");
        }

        if(isPrecoObrigatorioNulo(livro)){
            throw new AnoAcimaDoisMilEVinteException("preço", "Para livros com ano de publicação a partir de 2020 é obrigatório!");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_PRECO_EXIGENCIA;
    }

    private boolean existeLivroComIsbn(Livro livro){
        Optional<Livro>livroEncontrado = repository.findByIsbn(livro.getIsbn());

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(uuid -> !uuid.equals(livro.getId()));
    }
}
