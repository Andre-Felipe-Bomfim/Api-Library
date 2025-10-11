package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.GeneroLivro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Arthur");
        autor.setNacionalidade("Venuzuelano");
        autor.setDataNascimento(LocalDate.of(2008,10,20));

        var autorSalvo = autorRepository.save(autor);
        System.out.println("Autor Salvo " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("21d6c375-9f85-47c4-a647-200d827a000b");
        Optional<Autor> possivelAutor = autorRepository.findById(id);
        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados Autor: " + autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1900,6,20));
            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void deletarPorIdTest(){
        var id = UUID.fromString("21d6c375-9f85-47c4-a647-200d827a000b");
        autorRepository.deleteById(id);
    }

    @Test
    public void listarTodosOsAutoresTest(){
        List<Autor> list = autorRepository.findAll();
        list.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores " + autorRepository.count());
    }

    @Test
    public void deletarTodosOsAutores(){
        autorRepository.deleteAll();
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Jamile Gostosona");
        autor.setNacionalidade("Venuzuelana");
        autor.setDataNascimento(LocalDate.of(2008,10,20));

        Livro livro = new Livro();
        livro.setIsbn("18652354-123445");
        livro.setPreco(BigDecimal.valueOf(200));
        livro.setGenero(GeneroLivro.SUSPENSE);
        livro.setTitulo("O mistério da gostosa");
        livro.setDataPublicacao(LocalDate.of(2025,1,15));

        Livro livro2 = new Livro();
        livro2.setIsbn("18652354-123445");
        livro2.setPreco(BigDecimal.valueOf(200));
        livro2.setGenero(GeneroLivro.SUSPENSE);
        livro.setTitulo("O mistério da gostosa");
        livro.setDataPublicacao(LocalDate.of(2025,1,15));

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
    }
}
