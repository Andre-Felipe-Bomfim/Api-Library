package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoLivroDTO(
                                UUID id,
                                String isbn,
                                String titulo,
                                LocalDate dataPublicacao,
                                GeneroLivro genero,
                                BigDecimal preco,
                                AutorDTO autor
                                ) {
}
