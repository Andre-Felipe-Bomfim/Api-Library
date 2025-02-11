package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.CadastroLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.ResultadoLivroDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Livro;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)//o uses pode se usar para {} para coocar mais mappers para o auxilio da classe
public abstract class LivroMapper {
    //para injetar o id do autor com o livro use o abstract

    @Autowired
    AutorRepository autorRepository;
    
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoLivroDTO toDto(Livro livro);
}
