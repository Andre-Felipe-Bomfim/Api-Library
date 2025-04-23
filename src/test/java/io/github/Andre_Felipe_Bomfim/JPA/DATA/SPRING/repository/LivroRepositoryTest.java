package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.GeneroLivro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("12324-123445");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Pinto molinho enfiado no cu do arthur");
        livro.setDataPublicacao(LocalDate.of(2025,1,15));

        Autor autor = autorRepository.findById(UUID.fromString("3bd416ef-1956-451c-b71c-bd041bd61fd2")).orElse(null);
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("12309-1234092");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Jamile Gorda");
        livro.setDataPublicacao(LocalDate.of(2026,10,15));

        Autor autor = new Autor();
        autor.setNome("Jamile");
        autor.setNacionalidade("Inglesa");
        autor.setDataNascimento(LocalDate.of(2007,10,20));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test //habilitar o cascade
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("12323214-1234092");
        livro.setPreco(BigDecimal.valueOf(120));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("André Pintudo");
        livro.setDataPublicacao(LocalDate.of(2025,1,15));

        Autor autor = new Autor();
        autor.setNome("André");
        autor.setNacionalidade("Venuzuelano");
        autor.setDataNascimento(LocalDate.of(2008,10,20));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarLivroTest(){
        UUID id =UUID.fromString("31863e52-d499-4082-82ae-77372aa44e7d");
        var livroAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("97db509b-8bdd-4691-b79e-ecaa14191c5d");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroAtualizar.setAutor(autor);

        livroRepository.save(livroAtualizar);
    }

    @Test
    void deletar(){
        UUID id =UUID.fromString("31863e52-d499-4082-82ae-77372aa44e7d");
        livroRepository.deleteById(id);
    }

    @Test //habilitar o cascade
    void deletarCascade(){
        UUID id =UUID.fromString("0e215063-cf56-4efd-8a9f-28f33a1242fc");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional //para carregar algo a mais que você precisa
     void buscarLivroPorId(){
        UUID id =UUID.fromString("31863e52-d499-4082-82ae-77372aa44e7d");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println(livro.getTitulo());
        //System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisarPorISBNTest(){
        Optional<Livro> livro = livroRepository.findByIsbn("978-0-73-521129-2");
        livro.ifPresent(System.out::println);
    }
}