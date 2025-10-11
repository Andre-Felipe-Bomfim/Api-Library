package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

//classe imutável
public record AutorDTO(
        UUID uuid,
        @NotBlank(message = "campo obrigatorio")//usar com String
        @Size(min = 5, max = 100, message = "por favor coloque o tamanho sugerido")
        String nome,
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 2, max = 50, message = "por favor coloque o tamanho sugerido")
        String nacionalidade,
        @NotNull(message = "campo obrigatorio")// é para campos que podem vir nulos
        @Past(message = "não pode ser uma data futura")
        LocalDate dataNascimento
){}
