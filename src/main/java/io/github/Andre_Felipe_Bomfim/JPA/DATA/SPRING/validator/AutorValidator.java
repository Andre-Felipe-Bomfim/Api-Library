package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.validator;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.exceptions.RegistroDuplicadoException;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public void validar(Autor autor){
           if (existeAutorCadastrado(autor)){
               throw new RegistroDuplicadoException("Autor já cadastrado");
           }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getUuid() == null){
            return autorEncontrado.isPresent();
        }

        //return !autor.getUuid().equals(autorEncontrado.get().getUuid()) && autorEncontrado.isPresent();
        return !autorEncontrado
                .map(foundAutor -> !autor.getUuid().equals(foundAutor.getUuid())).orElse(true);
    }
}
