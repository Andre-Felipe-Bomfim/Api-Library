package io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.controller.mappers;

import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.dto.UsuarioDTO;
import io.github.Andre_Felipe_Bomfim.JPA.DATA.SPRING.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
