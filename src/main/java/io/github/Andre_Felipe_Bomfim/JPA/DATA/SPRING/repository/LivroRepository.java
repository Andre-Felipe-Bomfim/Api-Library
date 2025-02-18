package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {


    boolean existsByAutor(Autor autor);

    List<Livro> findByAutor(Autor autor);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByTituloAndIsbn(String titulo, String isbn);
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
}
