package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.AutorDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")//transforma em componente
public interface AutorMapper {

    //@Mapping(source = "nome", target = "nomeAutor") para registrar algo que esteja diferente pois ele pega o source e quando voce muda no codigo deve-se mapear aqui para o mapstruct não bugar
    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);
}
